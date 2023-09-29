package uz.fastfood.dashboard.service;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.fastfood.dashboard.config.ApplicationProperties;
import uz.fastfood.dashboard.dto.FileDTO;
import uz.fastfood.dashboard.entity.FileUpload;
import uz.fastfood.dashboard.repository.FileUploadRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class FileUploadService {
    private final FileUploadRepository fileUploadRepository;
    private final ApplicationProperties applicationProperties;
    private final MinioClient minioClient;

    public FileUploadService(FileUploadRepository fileUploadRepository, ApplicationProperties applicationProperties, MinioClient minioClient) {
        this.fileUploadRepository = fileUploadRepository;
        this.applicationProperties = applicationProperties;
        this.minioClient = minioClient;
    }


    public void removeObject(FileUpload fileUpload) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(applicationProperties.getMinio().getApplicationName()).object(fileUpload.getObjectName()).build());
            fileUploadRepository.deleteById(fileUpload.getId());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error(e.getMessage(), e);
        }
    }

    public FileUpload fileUpload(MultipartFile file, boolean isPublic) {
        this.validateFile(file);

        FileDTO fileDTO;
        try {
            fileDTO = uploadToStorageServer(file.getBytes(), file.getOriginalFilename(), file.getContentType(), isPublic);
        } catch (Exception e) {
            log.error("An unaccepted error has occurred while uploading file: ", e);
            throw new RuntimeException("An unaccepted error has occurred while uploading file");
        }
        FileUpload fileUpload = wrap(file, fileDTO);
        fileUploadRepository.save(fileUpload);
        return fileUpload;
    }

    private FileUpload wrap(MultipartFile file, FileDTO fileDTO) {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setContentType(file.getContentType());
        fileUpload.setSize(file.getSize());
        fileUpload.setUrl(fileDTO.getUrl());
        fileUpload.setObjectName(fileDTO.getObjectName());
        return fileUpload;
    }


    public FileDTO uploadToStorageServer(byte[] file, String fileName, String contentType, boolean isPublic) {
        String objectName = this.getObjectName(fileName, isPublic);

        ByteArrayInputStream stream = new ByteArrayInputStream(file);

        log.info("FILENAME: {} -------------------------------------", fileName);
        log.info("objectName: {} -------------------------------------", objectName);
        log.info("contentType: {} -------------------------------------", contentType);
        try {
            this.uploadWithPutObject(
                    PutObjectArgs
                            .builder()
                            .bucket(applicationProperties.getMinio().getApplicationName())
                            .object(objectName)
                            .stream(stream, file.length, -1)
                            .contentType(contentType)
                            .build()
            );

            FileDTO uploadDTO = new FileDTO();
            uploadDTO.setUrl(StringUtils.join(applicationProperties.getMinio().getHost(), "/", applicationProperties.getMinio().getApplicationName(), "/", objectName));
            uploadDTO.setObjectName(fileName);
            log.info("Image uploaded successfully");
            return uploadDTO;
        } catch (Exception e) {
            log.error("Close uploaded file error: {}", e.getMessage());
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
                log.error("Close uploaded file error: {}", e.getMessage());
            }
        }
        return null;
    }

    private void uploadWithPutObject(PutObjectArgs objectArgs) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(objectArgs.bucket()).build())) {
                throw new RuntimeException("Bucket does not exist");
            }
            Optional.ofNullable(this.minioClient.putObject(objectArgs)).map(ObjectWriteResponse::etag);
        } catch (Exception e) {
            log.error("Error upload file: {}", e.getMessage());
            throw new RuntimeException("Error upload file");
        }
    }

    private String getObjectName(final String fileName, final boolean isPublic) {
        final UUID uuid = UUID.randomUUID();
        final String extension = FilenameUtils.getExtension(fileName);
        return StringUtils.join(isPublic ? "public/" : "", uuid.toString(), StringUtils.isEmpty(extension) ? "" : '.' + extension);
    }

    private String encodeFileName(String originalFilename) {
        String fileName = null;
        try {
            fileName = URLEncoder.encode(originalFilename, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return fileName;
    }

    protected void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File required");
        }

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new RuntimeException("FileName required");
        }
    }
}

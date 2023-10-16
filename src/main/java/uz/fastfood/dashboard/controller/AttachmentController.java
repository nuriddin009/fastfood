package uz.fastfood.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.fastfood.dashboard.entity.FileUpload;
import uz.fastfood.dashboard.service.FileUploadService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/file")
public class AttachmentController {


    private final FileUploadService fileUploadService;

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        FileUpload fileUpload = fileUploadService.fileUpload(file, true);
        return ResponseEntity.ok(fileUpload);
    }


}

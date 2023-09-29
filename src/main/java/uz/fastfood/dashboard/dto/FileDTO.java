package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.fastfood.dashboard.entity.FileUpload;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {
    UUID id;
    String url;
    String objectName;

    public FileDTO(UUID id) {
        this.id = id;
    }

    public FileDTO(FileUpload fileUpload) {
        this.url = fileUpload.getUrl();
        this.objectName = fileUpload.getObjectName();
    }
}

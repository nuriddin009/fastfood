package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachmentDTO {
    private String filePath;
    private LocalDateTime createdAt;


}

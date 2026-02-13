package com.raincheck.finalyearproj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("uploaded_files")
public class UploadedFile {
    @TableId(type = IdType.AUTO)
    private Integer fileId;
    private Integer projectId;
    private String fileName;
    private String fileUrl; // OSS 存储链接
    private Float sizeMb;
    private LocalDateTime uploadedAt;
}
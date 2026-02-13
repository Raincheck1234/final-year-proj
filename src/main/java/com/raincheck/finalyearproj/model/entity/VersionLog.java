package com.raincheck.finalyearproj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("version_logs")
public class VersionLog {
    @TableId(type = IdType.AUTO)
    private Integer logId;
    private Integer projectId;
    private String logType; // COMMIT, UPLOAD, NOTE
    private String commitLink;
    private String description;
    private LocalDateTime timestamp;
}
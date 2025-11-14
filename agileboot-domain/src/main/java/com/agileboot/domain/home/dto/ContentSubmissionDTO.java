package com.agileboot.domain.home.dto;

import com.agileboot.infrastructure.home.contentsubmission.ContentSubmissionEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 内容投稿DTO
 *
 * @author agileboot
 */
@Data
public class ContentSubmissionDTO {

    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private String coverImage;
    private String platformType;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledPublishTime;
    
    private Integer status;
    private String rejectReason;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedTime;
    
    private Long auditorId;

    public ContentSubmissionDTO() {
    }

    public ContentSubmissionDTO(ContentSubmissionEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.userId = entity.getUserId();
            this.username = entity.getUsername();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.coverImage = entity.getCoverImage();
            this.platformType = entity.getPlatformType();
            this.scheduledPublishTime = entity.getScheduledPublishTime();
            this.status = entity.getStatus();
            this.rejectReason = entity.getRejectReason();
            this.createTime = entity.getCreatedAt();
            this.updateTime = entity.getUpdatedAt();
            this.publishedTime = entity.getPublishedAt();
            this.auditorId = entity.getAuditorId();
        }
    }
}

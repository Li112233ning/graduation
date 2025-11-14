package com.agileboot.domain.home.dto;

import cn.hutool.json.JSONUtil;
import com.agileboot.infrastructure.home.influencerapplication.InfluencerApplicationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 达人申请DTO
 *
 * @author agileboot
 */
@Data
public class InfluencerApplicationDTO {

    private Long id;
    private Long userId;
    private String username;
    private String platformAccount;
    private Integer fansCount;
    private String contentField;
    private List<String> portfolioLinks;
    private Integer status;
    private String rejectReason;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedTime;
    
    private Long auditorId;

    public InfluencerApplicationDTO() {
    }

    public InfluencerApplicationDTO(InfluencerApplicationEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.userId = entity.getUserId();
            this.username = entity.getUsername();
            this.platformAccount = entity.getPlatformAccount();
            this.fansCount = entity.getFansCount();
            this.contentField = entity.getContentField();
            
            // 解析JSON数组
            if (entity.getPortfolioLinks() != null && !entity.getPortfolioLinks().isEmpty()) {
                try {
                    this.portfolioLinks = JSONUtil.toList(entity.getPortfolioLinks(), String.class);
                } catch (Exception e) {
                    this.portfolioLinks = Collections.emptyList();
                }
            } else {
                this.portfolioLinks = Collections.emptyList();
            }
            
            this.status = entity.getStatus();
            this.rejectReason = entity.getRejectReason();
            this.createTime = entity.getCreatedAt();
            this.updateTime = entity.getUpdatedAt();
            this.approvedTime = entity.getApprovedAt();
            this.auditorId = entity.getAuditorId();
        }
    }
}

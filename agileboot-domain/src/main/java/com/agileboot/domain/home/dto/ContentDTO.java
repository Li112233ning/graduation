package com.agileboot.domain.home.dto;

import cn.hutool.json.JSONUtil;
import com.agileboot.domain.home.content.db.HomeContentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 内容DTO
 *
 * @author agileboot
 */
@Data
public class ContentDTO {

    public ContentDTO(HomeContentEntity entity) {
        if (entity != null) {
            this.id = entity.getContentId();
            this.title = entity.getTitle();
            this.categoryId = entity.getCategoryId();
            this.image = entity.getImageUrl();
            // 解析多图片列表
            if (entity.getImages() != null && !entity.getImages().isEmpty()) {
                this.images = JSONUtil.toList(entity.getImages(), String.class);
            } else if (entity.getImageUrl() != null) {
                // 如果没有images，使用imageUrl作为单图片
                this.images = Collections.singletonList(entity.getImageUrl());
            }
            this.likes = formatCount(entity.getLikesCount());
            this.comments = formatCount(entity.getCommentsCount());
            this.players = formatCount(entity.getViewsCount());
            this.contentType = entity.getContentType();
            this.platform = entity.getPlatform();
            this.status = entity.getStatus();
            this.description = entity.getDescription();
            this.publishTime = entity.getCreateTime();
            this.shares = formatCount(entity.getSharesCount());
            this.category = entity.getCategoryName();
            this.influencerId = entity.getInfluencerId();

            // 设置默认作者信息（实际应该从关联表获取）
            this.author = new AuthorInfo();
            this.author.setName("优质创作者");
            this.author.setFollowers("10万+");
        }
    }

    private Long id;
    private String title;
    private Long categoryId;
    private String image;
    private List<String> images; // 多图片列表
    private List<String> tags; // 标签列表
    private Boolean isLiked; // 当前用户是否已点赞
    private Boolean isCollected; // 当前用户是否已收藏
    private String likes;
    private String comments;
    private String players;
    private Integer contentType;
    private String platform;
    private Integer status;
    private String update; // 更新标签
    private String description; // 内容描述
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date publishTime; // 发布时间
    
    private String shares; // 分享数
    private String category; // 分类名称
    private AuthorInfo author; // 作者信息
    private Long influencerId; // 所属达人ID

    /**
     * 作者信息内部类
     */
    @Data
    public static class AuthorInfo {
        private String name;
        private String avatar;
        private String followers;
    }

    /**
     * 格式化数字为万单位
     */
    private String formatCount(Integer count) {
        if (count == null) {
            return "0";
        }
        if (count >= 10000) {
            return String.format("%.1f万", count / 10000.0);
        }
        return count.toString();
    }
}


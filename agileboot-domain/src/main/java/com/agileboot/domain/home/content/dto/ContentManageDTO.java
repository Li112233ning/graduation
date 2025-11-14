package com.agileboot.domain.home.content.dto;

import com.agileboot.domain.home.content.db.HomeContentEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 内容管理DTO
 *
 * @author agileboot
 */
@Data
@Schema(description = "内容管理DTO")
public class ContentManageDTO {

    @Schema(description = "内容ID")
    private Long contentId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "封面图片URL")
    private String imageUrl;

    @Schema(description = "图片列表（JSON数组）")
    private String images;

    @Schema(description = "点赞数")
    private Integer likesCount;

    @Schema(description = "评论数")
    private Integer commentsCount;

    @Schema(description = "浏览数")
    private Integer viewsCount;

    @Schema(description = "分享数")
    private Integer sharesCount;

    @Schema(description = "内容类型（1精选 2最新 3热门）")
    private Integer contentType;

    @Schema(description = "发布平台")
    private String platform;

    @Schema(description = "内容描述")
    private String description;

    @Schema(description = "状态（0停用 1启用）")
    private Integer status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    public ContentManageDTO(HomeContentEntity entity) {
        if (entity != null) {
            this.contentId = entity.getContentId();
            this.title = entity.getTitle();
            this.categoryId = entity.getCategoryId();
            this.categoryName = entity.getCategoryName();
            this.imageUrl = entity.getImageUrl();
            this.images = entity.getImages();
            this.likesCount = entity.getLikesCount();
            this.commentsCount = entity.getCommentsCount();
            this.viewsCount = entity.getViewsCount();
            this.sharesCount = entity.getSharesCount();
            this.contentType = entity.getContentType();
            this.platform = entity.getPlatform();
            this.description = entity.getDescription();
            this.status = entity.getStatus();
            this.createTime = entity.getCreateTime();
            this.updateTime = entity.getUpdateTime();
        }
    }
}
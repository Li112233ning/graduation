package com.agileboot.domain.home.content.db;

import com.agileboot.common.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 内容稿件表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_content")
@Schema(description = "内容稿件实体")
public class HomeContentEntity extends BaseEntity<HomeContentEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "内容ID")
    @TableId(value = "content_id", type = IdType.AUTO)
    private Long contentId;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "分类ID")
    @TableField("category_id")
    private Long categoryId;

    @Schema(description = "封面图片URL")
    @TableField("image_url")
    private String imageUrl;

    @Schema(description = "图片列表（JSON数组）")
    @TableField("images")
    private String images;

    @Schema(description = "点赞数")
    @TableField("likes_count")
    private Integer likesCount;

    @Schema(description = "评论数")
    @TableField("comments_count")
    private Integer commentsCount;

    @Schema(description = "浏览数")
    @TableField("views_count")
    private Integer viewsCount;

    @Schema(description = "内容类型（1精选 2最新 3热门）")
    @TableField("content_type")
    private Integer contentType;

    @Schema(description = "发布平台")
    @TableField("platform")
    private String platform;

    @Schema(description = "所属达人ID")
    @TableField("influencer_id")
    private Long influencerId;

    @Schema(description = "状态（0停用 1启用）")
    @TableField("status")
    private Integer status;

    @Schema(description = "审核状态（0待审核 1审核通过 2审核拒绝）")
    @TableField("audit_status")
    private Integer auditStatus;

    @Schema(description = "审核备注")
    @TableField("audit_remark")
    private String auditRemark;

    @Schema(description = "内容描述")
    @TableField("description")
    private String description;

    @Schema(description = "分享数")
    @TableField("shares_count")
    private Integer sharesCount;

    @Schema(description = "分类名称")
    @TableField(exist = false)
    private String categoryName;

    @Schema(description = "达人名称")
    @TableField(exist = false)
    private String influencerName;

    @Override
    public Serializable pkVal() {
        return this.contentId;
    }
}


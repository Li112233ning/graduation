package com.agileboot.infrastructure.home.contentsubmission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 内容投稿实体
 *
 * @author agileboot
 */
@Data
@TableName("content_submission")
@Schema(description = "内容投稿实体")
public class ContentSubmissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "投稿ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "投稿用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "正文")
    @TableField("content")
    private String content;

    @Schema(description = "封面图URL")
    @TableField("cover_image")
    private String coverImage;

    @Schema(description = "平台类型")
    @TableField("platform_type")
    private String platformType;

    @Schema(description = "预计发布时间")
    @TableField("scheduled_publish_time")
    private LocalDateTime scheduledPublishTime;

    @Schema(description = "状态：0-草稿，1-待审核，2-已通过，3-已驳回，4-已发布")
    @TableField("status")
    private Integer status;

    @Schema(description = "驳回原因")
    @TableField("reject_reason")
    private String rejectReason;

    @Schema(description = "创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @Schema(description = "发布时间")
    @TableField("published_at")
    private LocalDateTime publishedAt;

    @Schema(description = "审核人ID")
    @TableField("auditor_id")
    private Long auditorId;

    @Schema(description = "用户名（非数据库字段）")
    @TableField(exist = false)
    private String username;
}

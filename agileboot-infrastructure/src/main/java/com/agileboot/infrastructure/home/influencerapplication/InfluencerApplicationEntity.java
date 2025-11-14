package com.agileboot.infrastructure.home.influencerapplication;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 达人申请实体
 *
 * @author agileboot
 */
@Data
@TableName("influencer_application")
@Schema(description = "达人申请实体")
public class InfluencerApplicationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "申请ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "平台账号")
    @TableField("platform_account")
    private String platformAccount;

    @Schema(description = "粉丝量")
    @TableField("fans_count")
    private Integer fansCount;

    @Schema(description = "内容领域")
    @TableField("content_field")
    private String contentField;

    @Schema(description = "代表作品链接（JSON数组）")
    @TableField("portfolio_links")
    private String portfolioLinks;

    @Schema(description = "状态：0-待审核，1-已通过，2-已驳回")
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

    @Schema(description = "审核通过时间")
    @TableField("approved_at")
    private LocalDateTime approvedAt;

    @Schema(description = "审核人ID")
    @TableField("auditor_id")
    private Long auditorId;

    @Schema(description = "用户名（非数据库字段）")
    @TableField(exist = false)
    private String username;
}

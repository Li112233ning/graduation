package com.agileboot.domain.home.comment.db;

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
 * 内容评论表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_comment")
@Schema(description = "内容评论实体")
public class HomeCommentEntity extends BaseEntity<HomeCommentEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "评论ID")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @Schema(description = "内容ID")
    @TableField("content_id")
    private Long contentId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "父评论ID（用于回复）")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "评论内容")
    @TableField("content")
    private String content;

    @Schema(description = "点赞数")
    @TableField("likes_count")
    private Integer likesCount;

    @Schema(description = "状态（0停用 1启用）")
    @TableField("status")
    private Integer status;

    @Override
    public Serializable pkVal() {
        return this.commentId;
    }
}


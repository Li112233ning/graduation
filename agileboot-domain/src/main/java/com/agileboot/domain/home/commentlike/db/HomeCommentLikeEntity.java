package com.agileboot.domain.home.commentlike.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评论点赞表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_comment_like")
@Schema(description = "评论点赞实体")
public class HomeCommentLikeEntity extends Model<HomeCommentLikeEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "点赞ID")
    @TableId(value = "like_id", type = IdType.AUTO)
    private Long likeId;

    @Schema(description = "评论ID")
    @TableField("comment_id")
    private Long commentId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "状态（0取消 1有效）")
    @TableField("status")
    private Integer status;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "逻辑删除")
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;

    @Override
    public Serializable pkVal() {
        return this.likeId;
    }
}
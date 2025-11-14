package com.agileboot.domain.home.interaction.db;

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
 * 用户互动表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_user_interaction")
@Schema(description = "用户互动实体")
public class HomeUserInteractionEntity extends Model<HomeUserInteractionEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "互动ID")
    @TableId(value = "interaction_id", type = IdType.AUTO)
    private Long interactionId;

    @Schema(description = "内容ID")
    @TableField("content_id")
    private Long contentId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "互动类型（1点赞 2收藏）")
    @TableField("interaction_type")
    private Integer interactionType;

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
        return this.interactionId;
    }
}


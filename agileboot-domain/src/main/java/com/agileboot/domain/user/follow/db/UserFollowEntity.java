package com.agileboot.domain.user.follow.db;

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
 * 用户关注表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("user_follow")
@Schema(description = "用户关注实体")
public class UserFollowEntity extends Model<UserFollowEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "关注ID")
    @TableId(value = "follow_id", type = IdType.AUTO)
    private Long followId;

    @Schema(description = "关注者ID（粉丝）")
    @TableField("follower_id")
    private Long followerId;

    @Schema(description = "被关注者ID（博主）")
    @TableField("followee_id")
    private Long followeeId;

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
        return this.followId;
    }
}
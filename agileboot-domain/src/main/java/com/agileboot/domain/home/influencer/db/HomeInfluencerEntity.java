package com.agileboot.domain.home.influencer.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 优质达人表
 * </p>
 *
 * @author agileboot
 */
@Data
@TableName("home_influencer")
@Schema(description = "优质达人实体")
public class HomeInfluencerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "达人ID")
    @TableId(value = "influencer_id", type = IdType.AUTO)
    private Long influencerId;

    @Schema(description = "达人名称")
    @TableField("name")
    private String name;

    @Schema(description = "所属平台")
    @TableField("platform")
    private String platform;

    @Schema(description = "粉丝数量")
    @TableField("followers")
    private String followers;

    @Schema(description = "专业领域")
    @TableField("field")
    private String field;

    @Schema(description = "封面图片URL")
    @TableField("image_url")
    private String imageUrl;

    @Schema(description = "排序顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @Schema(description = "状态（0停用 1启用）")
    @TableField("status")
    private Integer status;
}


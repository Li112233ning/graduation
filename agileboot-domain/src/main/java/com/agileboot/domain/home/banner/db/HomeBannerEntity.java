package com.agileboot.domain.home.banner.db;

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
 * 首页轮播图表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_banner")
@Schema(description = "首页轮播图实体")
public class HomeBannerEntity extends BaseEntity<HomeBannerEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "轮播图ID")
    @TableId(value = "banner_id", type = IdType.AUTO)
    private Long bannerId;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "描述")
    @TableField("description")
    private String description;

    @Schema(description = "图片URL")
    @TableField("image_url")
    private String imageUrl;

    @Schema(description = "跳转链接")
    @TableField("link_url")
    private String linkUrl;

    @Schema(description = "排序顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @Schema(description = "状态（0停用 1启用）")
    @TableField("status")
    private Integer status;

    @Override
    public Serializable pkVal() {
        return this.bannerId;
    }
}


package com.agileboot.domain.home.category.db;

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
 * 内容分类表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_category")
@Schema(description = "内容分类实体")
public class HomeCategoryEntity extends BaseEntity<HomeCategoryEntity> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类ID")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    @Schema(description = "分类名称")
    @TableField("category_name")
    private String categoryName;

    @Schema(description = "图标名称")
    @TableField("icon_name")
    private String iconName;

    @Schema(description = "颜色样式类")
    @TableField("color_class")
    private String colorClass;

    @Schema(description = "排序顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @Schema(description = "状态（0停用 1启用）")
    @TableField("status")
    private Integer status;

    @Override
    public Serializable pkVal() {
        return this.categoryId;
    }
}


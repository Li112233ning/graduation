package com.agileboot.domain.home.dto;

import com.agileboot.domain.home.category.db.HomeCategoryEntity;
import lombok.Data;

/**
 * 分类DTO
 *
 * @author agileboot
 */
@Data
public class CategoryDTO {

    public CategoryDTO(HomeCategoryEntity entity) {
        if (entity != null) {
            this.id = entity.getCategoryId();
            this.name = entity.getCategoryName();
            this.icon = entity.getIconName();
            this.color = entity.getColorClass();
            this.sortOrder = entity.getSortOrder();
            this.status = entity.getStatus();
        }
    }

    private Long id;
    private String name;
    private String icon;
    private String color;
    private Integer sortOrder;
    private Integer status;
}


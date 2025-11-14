package com.agileboot.domain.home.dto;

import com.agileboot.domain.home.platform.db.HomePlatformEntity;
import lombok.Data;

/**
 * 平台DTO
 *
 * @author agileboot
 */
@Data
public class PlatformDTO {

    public PlatformDTO(HomePlatformEntity entity) {
        if (entity != null) {
            this.id = entity.getPlatformId();
            this.name = entity.getPlatformName();
            this.sortOrder = entity.getSortOrder();
            this.status = entity.getStatus();
        }
    }

    private Long id;
    private String name;
    private Integer sortOrder;
    private Integer status;
}


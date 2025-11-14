package com.agileboot.domain.home.dto;

import com.agileboot.domain.home.influencer.db.HomeInfluencerEntity;
import lombok.Data;

/**
 * 达人DTO
 *
 * @author agileboot
 */
@Data
public class InfluencerDTO {

    public InfluencerDTO(HomeInfluencerEntity entity) {
        if (entity != null) {
            this.id = entity.getInfluencerId();
            this.name = entity.getName();
            this.platform = entity.getPlatform();
            this.followers = entity.getFollowers();
            this.field = entity.getField();
            this.image = entity.getImageUrl();
            this.sortOrder = entity.getSortOrder();
            this.status = entity.getStatus();
        }
    }

    private Long id;
    private String name;
    private String platform;
    private String followers;
    private String field;
    private String image;
    private Integer sortOrder;
    private Integer status;
}


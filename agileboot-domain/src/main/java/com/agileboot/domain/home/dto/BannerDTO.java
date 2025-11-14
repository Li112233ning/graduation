package com.agileboot.domain.home.dto;

import cn.hutool.json.JSONUtil;
import com.agileboot.domain.home.banner.db.HomeBannerEntity;
import lombok.Data;
import java.util.List;

/**
 * 轮播图DTO
 *
 * @author agileboot
 */
@Data
public class BannerDTO {

    public BannerDTO(HomeBannerEntity entity) {
        if (entity != null) {
            this.id = entity.getBannerId();
            this.title = entity.getTitle();
            this.description = entity.getDescription();
            this.image = entity.getImageUrl();
            this.linkUrl = entity.getLinkUrl();
            this.sortOrder = entity.getSortOrder();
            this.status = entity.getStatus();
        }
    }

    private Long id;
    private String title;
    private String description;
    private String image;
    private String linkUrl;
    private Integer sortOrder;
    private Integer status;
}


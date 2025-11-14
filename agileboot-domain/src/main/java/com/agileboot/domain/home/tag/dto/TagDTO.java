package com.agileboot.domain.home.tag.dto;

import com.agileboot.domain.home.tag.db.HomeTagEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 标签DTO
 *
 * @author agileboot
 */
@Data
@Schema(description = "标签DTO")
public class TagDTO {

    @Schema(description = "标签ID")
    private Long tagId;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "排序号")
    private Integer sortOrder;

    @Schema(description = "状态（0禁用 1启用）")
    private Integer status;

    public TagDTO(HomeTagEntity entity) {
        if (entity != null) {
            this.tagId = entity.getTagId();
            this.tagName = entity.getTagName();
            this.sortOrder = entity.getSortOrder();
            this.status = entity.getStatus();
        }
    }
}
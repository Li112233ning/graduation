package com.agileboot.domain.home.content.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 更新内容命令
 *
 * @author agileboot
 */
@Data
@Schema(description = "更新内容命令")
public class UpdateContentCommand {

    @Schema(description = "内容ID")
    @NotNull(message = "内容ID不能为空")
    private Long contentId;

    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    @Size(max = 256, message = "标题长度不能超过256个字符")
    private String title;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "封面图片URL")
    @NotBlank(message = "封面图片不能为空")
    @Size(max = 512, message = "图片URL长度不能超过512个字符")
    private String imageUrl;

    @Schema(description = "图片列表（JSON数组）")
    private String images;

    @Schema(description = "内容类型（1精选 2最新 3热门）")
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

    @Schema(description = "发布平台")
    private String platform;

    @Schema(description = "内容描述")
    private String description;

    @Schema(description = "状态（0停用 1启用）")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
package com.agileboot.domain.home.tag.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 更新标签命令
 *
 * @author agileboot
 */
@Data
@Schema(description = "更新标签命令")
public class UpdateTagCommand {

    @Schema(description = "标签ID")
    @NotNull(message = "标签ID不能为空")
    private Long tagId;

    @Schema(description = "标签名称")
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称长度不能超过50个字符")
    private String tagName;

    @Schema(description = "排序号")
    private Integer sortOrder;

    @Schema(description = "状态（0禁用 1启用）")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
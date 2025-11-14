package com.agileboot.domain.home.influencerapplication.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 提交达人申请命令
 *
 * @author agileboot
 */
@Data
@Schema(description = "提交达人申请命令")
public class SubmitApplicationCommand {

    @Schema(description = "平台账号")
    @NotBlank(message = "平台账号不能为空")
    @Size(max = 100, message = "平台账号长度不能超过100个字符")
    private String platformAccount;

    @Schema(description = "粉丝量")
    @NotNull(message = "粉丝量不能为空")
    private Integer fansCount;

    @Schema(description = "内容领域")
    @NotBlank(message = "内容领域不能为空")
    @Size(max = 100, message = "内容领域长度不能超过100个字符")
    private String contentField;

    @Schema(description = "代表作品链接")
    @NotNull(message = "代表作品链接不能为空")
    @Size(min = 1, message = "至少需要一个代表作品链接")
    private List<String> portfolioLinks;
}

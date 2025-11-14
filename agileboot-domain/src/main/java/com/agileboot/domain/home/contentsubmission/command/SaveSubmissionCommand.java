package com.agileboot.domain.home.contentsubmission.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 保存内容投稿命令
 *
 * @author agileboot
 */
@Data
@Schema(description = "保存内容投稿命令")
public class SaveSubmissionCommand {

    @Schema(description = "投稿ID（更新时传入）")
    private Long id;

    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;

    @Schema(description = "正文")
    @NotBlank(message = "正文不能为空")
    private String content;

    @Schema(description = "封面图URL")
    @Size(max = 500, message = "封面图URL长度不能超过500个字符")
    private String coverImage;

    @Schema(description = "平台类型")
    @Size(max = 50, message = "平台类型长度不能超过50个字符")
    private String platformType;

    @Schema(description = "预计发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledPublishTime;

    @Schema(description = "状态：0-草稿，1-待审核")
    private Integer status;
}

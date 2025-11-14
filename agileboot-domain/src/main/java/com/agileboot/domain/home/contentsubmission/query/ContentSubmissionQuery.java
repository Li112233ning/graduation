package com.agileboot.domain.home.contentsubmission.query;

import cn.hutool.core.util.StrUtil;
import com.agileboot.common.core.page.AbstractPageQuery;
import com.agileboot.infrastructure.home.contentsubmission.ContentSubmissionEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内容投稿查询对象
 *
 * @author agileboot
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "内容投稿查询对象")
public class ContentSubmissionQuery extends AbstractPageQuery<ContentSubmissionEntity> {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "平台类型")
    private String platformType;

    @Schema(description = "状态：0-草稿，1-待审核，2-已通过，3-已驳回，4-已发布")
    private Integer status;

    @Override
    public QueryWrapper<ContentSubmissionEntity> addQueryCondition() {
        QueryWrapper<ContentSubmissionEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(title), "title", title)
                .eq(userId != null, "user_id", userId)
                .eq(StrUtil.isNotBlank(platformType), "platform_type", platformType)
                .eq(status != null, "status", status)
                .orderByDesc("created_at");

        return queryWrapper;
    }
}

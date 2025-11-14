package com.agileboot.domain.home.content.query;

import com.agileboot.common.core.page.AbstractPageQuery;
import com.agileboot.domain.home.content.db.HomeContentEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内容查询对象
 *
 * @author agileboot
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "内容查询对象")
public class ContentQuery extends AbstractPageQuery<HomeContentEntity> {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "内容类型（1精选 2最新 3热门）")
    private Integer contentType;

    @Schema(description = "发布平台")
    private String platform;

    @Schema(description = "状态（0停用 1启用）")
    private Integer status;

    @Override
    public QueryWrapper<HomeContentEntity> addQueryCondition() {
        QueryWrapper<HomeContentEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(title), "title", title)
                .eq(categoryId != null, "category_id", categoryId)
                .eq(contentType != null, "content_type", contentType)
                .eq(StrUtil.isNotBlank(platform), "platform", platform)
                .eq(status != null, "status", status)
                .eq("deleted", 0)
                .orderByDesc("create_time");

        return queryWrapper;
    }
}
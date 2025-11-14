package com.agileboot.domain.home.tag.query;

import cn.hutool.core.util.StrUtil;
import com.agileboot.common.core.page.AbstractPageQuery;
import com.agileboot.domain.home.tag.db.HomeTagEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签查询对象
 *
 * @author agileboot
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "标签查询对象")
public class TagQuery extends AbstractPageQuery<HomeTagEntity> {

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "状态（0禁用 1启用）")
    private Integer status;

    @Override
    public QueryWrapper<HomeTagEntity> addQueryCondition() {
        QueryWrapper<HomeTagEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(tagName), "tag_name", tagName)
                .eq(status != null, "status", status)
                .eq("deleted", 0)
                .orderByAsc("sort_order")
                .orderByDesc("create_time");

        return queryWrapper;
    }
}
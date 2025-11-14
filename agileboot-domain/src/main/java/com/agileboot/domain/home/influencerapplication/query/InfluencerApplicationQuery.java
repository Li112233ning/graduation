package com.agileboot.domain.home.influencerapplication.query;

import cn.hutool.core.util.StrUtil;
import com.agileboot.common.core.page.AbstractPageQuery;
import com.agileboot.infrastructure.home.influencerapplication.InfluencerApplicationEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 达人申请查询对象
 *
 * @author agileboot
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "达人申请查询对象")
public class InfluencerApplicationQuery extends AbstractPageQuery<InfluencerApplicationEntity> {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "平台账号")
    private String platformAccount;

    @Schema(description = "内容领域")
    private String contentField;

    @Schema(description = "状态：0-待审核，1-已通过，2-已驳回")
    private Integer status;

    @Override
    public QueryWrapper<InfluencerApplicationEntity> addQueryCondition() {
        QueryWrapper<InfluencerApplicationEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(platformAccount), "platform_account", platformAccount)
                .like(StrUtil.isNotBlank(contentField), "content_field", contentField)
                .eq(status != null, "status", status)
                .orderByDesc("created_at");

        return queryWrapper;
    }
}

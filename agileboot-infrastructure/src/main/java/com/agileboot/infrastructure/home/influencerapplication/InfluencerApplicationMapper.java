package com.agileboot.infrastructure.home.influencerapplication;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 达人申请Mapper
 *
 * @author agileboot
 */
@Mapper
public interface InfluencerApplicationMapper extends BaseMapper<InfluencerApplicationEntity> {

    /**
     * 查询申请列表（带用户名）
     */
    @Select("SELECT ia.*, u.username " +
            "FROM influencer_application ia " +
            "LEFT JOIN sys_user u ON ia.user_id = u.user_id " +
            "WHERE 1=1 " +
            "${ew.customSqlSegment}")
    List<InfluencerApplicationEntity> selectListWithUsername(@Param("ew") Object wrapper);
}

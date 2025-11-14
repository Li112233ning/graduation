package com.agileboot.infrastructure.home.contentsubmission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 内容投稿Mapper
 *
 * @author agileboot
 */
@Mapper
public interface ContentSubmissionMapper extends BaseMapper<ContentSubmissionEntity> {

    /**
     * 查询投稿列表（带用户名）
     */
    @Select("SELECT cs.*, u.username " +
            "FROM content_submission cs " +
            "LEFT JOIN sys_user u ON cs.user_id = u.user_id " +
            "WHERE 1=1 " +
            "${ew.customSqlSegment}")
    List<ContentSubmissionEntity> selectListWithUsername(@Param("ew") Object wrapper);
}

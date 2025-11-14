package com.agileboot.domain.home.content.db;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 稿件标签关联Service
 *
 * @author agileboot
 */
public interface HomeContentTagService extends IService<HomeContentTagEntity> {

    /**
     * 根据内容ID获取标签ID列表
     */
    List<Long> getTagIdsByContentId(Long contentId);

    /**
     * 保存内容标签关联
     */
    void saveContentTags(Long contentId, List<Long> tagIds);

    /**
     * 删除内容的所有标签关联
     */
    void deleteByContentId(Long contentId);
}
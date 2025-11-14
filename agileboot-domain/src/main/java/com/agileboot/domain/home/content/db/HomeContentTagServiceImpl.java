package com.agileboot.domain.home.content.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 稿件标签关联Service实现
 *
 * @author agileboot
 */
@Service
public class HomeContentTagServiceImpl extends ServiceImpl<HomeContentTagMapper, HomeContentTagEntity>
        implements HomeContentTagService {

    @Override
    public List<Long> getTagIdsByContentId(Long contentId) {
        LambdaQueryWrapper<HomeContentTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeContentTagEntity::getContentId, contentId);

        return list(wrapper).stream()
                .map(HomeContentTagEntity::getTagId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveContentTags(Long contentId, List<Long> tagIds) {
        // 先删除旧的关联
        deleteByContentId(contentId);

        // 保存新的关联
        if (tagIds != null && !tagIds.isEmpty()) {
            List<HomeContentTagEntity> entities = new ArrayList<>();
            for (Long tagId : tagIds) {
                HomeContentTagEntity entity = new HomeContentTagEntity();
                entity.setContentId(contentId);
                entity.setTagId(tagId);
                entities.add(entity);
            }
            saveBatch(entities);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByContentId(Long contentId) {
        LambdaQueryWrapper<HomeContentTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeContentTagEntity::getContentId, contentId);
        remove(wrapper);
    }
}
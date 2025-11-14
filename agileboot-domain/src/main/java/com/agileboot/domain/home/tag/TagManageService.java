package com.agileboot.domain.home.tag;

import cn.hutool.core.bean.BeanUtil;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.home.tag.command.AddTagCommand;
import com.agileboot.domain.home.tag.command.UpdateTagCommand;
import com.agileboot.domain.home.tag.db.HomeTagEntity;
import com.agileboot.domain.home.tag.db.HomeTagService;
import com.agileboot.domain.home.tag.dto.TagDTO;
import com.agileboot.domain.home.tag.query.TagQuery;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签管理服务
 *
 * @author agileboot
 */
@Service
@RequiredArgsConstructor
public class TagManageService {

    private final HomeTagService tagService;

    /**
     * 获取标签列表
     */
    public PageDTO<TagDTO> getTagList(TagQuery query) {
        Page<HomeTagEntity> page = tagService.page(query.toPage(), query.toQueryWrapper());

        List<TagDTO> dtoList = page.getRecords().stream()
                .map(TagDTO::new)
                .collect(Collectors.toList());

        return new PageDTO<>(dtoList, page.getTotal());
    }

    /**
     * 获取所有标签（不分页）
     */
    public List<TagDTO> getAllTags() {
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeTagEntity::getStatus, 1)
                .orderByAsc(HomeTagEntity::getSortOrder);

        return tagService.list(wrapper).stream()
                .map(TagDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 获取标签详情
     */
    public TagDTO getTagDetail(Long tagId) {
        HomeTagEntity entity = tagService.getById(tagId);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, tagId, "标签");
        }
        return new TagDTO(entity);
    }

    /**
     * 新增标签
     */
    @Transactional(rollbackFor = Exception.class)
    public void addTag(AddTagCommand command) {
        // 检查标签名称是否已存在
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeTagEntity::getTagName, command.getTagName());
        if (tagService.count(wrapper) > 0) {
            throw new ApiException(ErrorCode.FAILED, "标签名称已存在：" + command.getTagName());
        }

        HomeTagEntity entity = new HomeTagEntity();
        BeanUtil.copyProperties(command, entity);

        boolean success = tagService.save(entity);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "创建标签失败");
        }
    }

    /**
     * 更新标签
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(UpdateTagCommand command) {
        HomeTagEntity entity = tagService.getById(command.getTagId());
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, command.getTagId(), "标签");
        }

        // 检查标签名称是否已存在（排除自己）
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeTagEntity::getTagName, command.getTagName())
                .ne(HomeTagEntity::getTagId, command.getTagId());
        if (tagService.count(wrapper) > 0) {
            throw new ApiException(ErrorCode.FAILED, "标签名称已存在：" + command.getTagName());
        }

        BeanUtil.copyProperties(command, entity);

        boolean success = tagService.updateById(entity);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "更新标签失败");
        }
    }

    /**
     * 删除标签
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long tagId) {
        HomeTagEntity entity = tagService.getById(tagId);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, tagId, "标签");
        }

        boolean success = tagService.removeById(tagId);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "删除标签失败");
        }
    }

    /**
     * 批量删除标签
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteTag(List<Long> tagIds) {
        boolean success = tagService.removeByIds(tagIds);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "批量删除标签失败");
        }
    }
}
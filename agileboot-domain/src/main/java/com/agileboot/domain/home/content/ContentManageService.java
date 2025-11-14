package com.agileboot.domain.home.content;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.home.category.db.HomeCategoryEntity;
import com.agileboot.domain.home.category.db.HomeCategoryService;
import com.agileboot.domain.home.content.command.AddContentCommand;
import com.agileboot.domain.home.content.command.UpdateContentCommand;
import com.agileboot.domain.home.content.db.HomeContentEntity;
import com.agileboot.domain.home.content.db.HomeContentService;
import com.agileboot.domain.home.content.db.HomeContentTagService;
import com.agileboot.domain.home.content.dto.ContentManageDTO;
import com.agileboot.domain.home.content.query.ContentQuery;
import com.agileboot.domain.home.tag.db.HomeTagEntity;
import com.agileboot.domain.home.tag.db.HomeTagService;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内容管理服务
 *
 * @author agileboot
 */
@Service
@RequiredArgsConstructor
public class ContentManageService {

    private final HomeContentService contentService;
    private final HomeCategoryService categoryService;
    private final HomeContentTagService contentTagService;
    private final HomeTagService tagService;

    /**
     * 获取内容列表
     */
    public PageDTO<ContentManageDTO> getContentList(ContentQuery query) {
        Page<HomeContentEntity> page = contentService.page(query.toPage(), query.toQueryWrapper());

        // 获取所有分类信息
        List<Long> categoryIds = page.getRecords().stream()
                .map(HomeContentEntity::getCategoryId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, HomeCategoryEntity> categoryMap = categoryIds.isEmpty() ?
                new HashMap<>() :
                categoryService.listByIds(categoryIds).stream()
                        .collect(Collectors.toMap(HomeCategoryEntity::getCategoryId, Function.identity()));

        // 设置分类名称
        page.getRecords().forEach(content -> {
            if (content.getCategoryId() != null && categoryMap.containsKey(content.getCategoryId())) {
                content.setCategoryName(categoryMap.get(content.getCategoryId()).getCategoryName());
            }
        });

        List<ContentManageDTO> dtoList = page.getRecords().stream()
                .map(ContentManageDTO::new)
                .collect(Collectors.toList());

        return new PageDTO<>(dtoList, page.getTotal());
    }

    /**
     * 获取内容详情
     */
    public ContentManageDTO getContentDetail(Long contentId) {
        HomeContentEntity entity = contentService.getById(contentId);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, contentId, "内容");
        }

        // 设置分类名称
        if (entity.getCategoryId() != null) {
            HomeCategoryEntity category = categoryService.getById(entity.getCategoryId());
            if (category != null) {
                entity.setCategoryName(category.getCategoryName());
            }
        }

        return new ContentManageDTO(entity);
    }

    /**
     * 新增内容
     */
    @Transactional(rollbackFor = Exception.class)
    public void addContent(AddContentCommand command) {
        HomeContentEntity entity = new HomeContentEntity();
        BeanUtil.copyProperties(command, entity);

        // 设置初始值
        entity.setLikesCount(0);
        entity.setCommentsCount(0);
        entity.setViewsCount(0);
        entity.setSharesCount(0);
        entity.setCreatorId(AuthenticationUtils.getUserId());

        boolean success = contentService.save(entity);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "创建内容失败");
        }
    }

    /**
     * 更新内容
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateContent(UpdateContentCommand command) {
        HomeContentEntity entity = contentService.getById(command.getContentId());
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, command.getContentId(), "内容");
        }

        BeanUtil.copyProperties(command, entity);
        entity.setUpdaterId(AuthenticationUtils.getUserId());

        boolean success = contentService.updateById(entity);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "更新内容失败");
        }
    }

    /**
     * 删除内容
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteContent(Long contentId) {
        HomeContentEntity entity = contentService.getById(contentId);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, contentId, "内容");
        }

        boolean success = contentService.removeById(contentId);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "删除内容失败");
        }
    }

    /**
     * 批量删除内容
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteContent(List<Long> contentIds) {
        boolean success = contentService.removeByIds(contentIds);
        if (!success) {
            throw new ApiException(ErrorCode.FAILED, "批量删除内容失败");
        }
    }
}
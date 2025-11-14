package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.home.content.db.HomeContentEntity;
import com.agileboot.domain.home.content.db.HomeContentService;
import com.agileboot.domain.home.influencer.db.HomeInfluencerEntity;
import com.agileboot.domain.home.influencer.db.HomeInfluencerService;
import com.agileboot.common.enums.common.BusinessTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 稿件管理控制器
 *
 * @author agileboot
 */
@Tag(name = "稿件管理", description = "后台稿件管理接口")
@RestController
@RequestMapping("/admin/home/content")
@RequiredArgsConstructor
public class HomeContentAdminController extends BaseController {

    private final HomeContentService contentService;
    private final HomeInfluencerService influencerService;

    /**
     * 获取稿件列表（分页）
     */
    @Operation(summary = "获取稿件列表")
    @PreAuthorize("@permission.has('home:content:list')")
    @GetMapping("/list")
    public ResponseDTO<Page<HomeContentEntity>> getContentList(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) Integer contentType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer auditStatus) {

        Page<HomeContentEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<HomeContentEntity> wrapper = new LambdaQueryWrapper<>();

        // 标题模糊查询
        if (title != null && !title.isEmpty()) {
            wrapper.like(HomeContentEntity::getTitle, title);
        }

        // 分类筛选
        if (categoryId != null) {
            wrapper.eq(HomeContentEntity::getCategoryId, categoryId);
        }

        // 平台筛选
        if (platform != null && !platform.isEmpty()) {
            wrapper.eq(HomeContentEntity::getPlatform, platform);
        }

        // 内容类型筛选
        if (contentType != null) {
            wrapper.eq(HomeContentEntity::getContentType, contentType);
        }

        // 状态筛选
        if (status != null) {
            wrapper.eq(HomeContentEntity::getStatus, status);
        }

        // 审核状态筛选
        if (auditStatus != null) {
            wrapper.eq(HomeContentEntity::getAuditStatus, auditStatus);
        }

        // 按创建时间倒序
        wrapper.orderByDesc(HomeContentEntity::getCreateTime);

        Page<HomeContentEntity> result = contentService.page(page, wrapper);
        
        // 关联查询达人信息
        if (result.getRecords() != null && !result.getRecords().isEmpty()) {
            // 获取所有达人ID
            java.util.Set<Long> influencerIds = result.getRecords().stream()
                    .map(HomeContentEntity::getInfluencerId)
                    .filter(id -> id != null)
                    .collect(java.util.stream.Collectors.toSet());
            
            if (!influencerIds.isEmpty()) {
                // 批量查询达人信息
                java.util.Map<Long, String> influencerMap = influencerService.listByIds(influencerIds)
                        .stream()
                        .collect(Collectors.toMap(
                                HomeInfluencerEntity::getInfluencerId,
                                HomeInfluencerEntity::getName,
                                (v1, v2) -> v1
                        ));
                
                // 设置达人名称
                result.getRecords().forEach(content -> {
                    if (content.getInfluencerId() != null) {
                        content.setInfluencerName(influencerMap.get(content.getInfluencerId()));
                    }
                });
            }
        }
        
        return ResponseDTO.ok(result);
    }

    /**
     * 获取稿件详情
     */
    @Operation(summary = "获取稿件详情")
    @PreAuthorize("@permission.has('home:content:query')")
    @GetMapping("/{id}")
    public ResponseDTO<HomeContentEntity> getContentDetail(@PathVariable Long id) {
        HomeContentEntity content = contentService.getById(id);
        return ResponseDTO.ok(content);
    }

    /**
     * 创建稿件
     */
    @Operation(summary = "创建稿件")
    @PreAuthorize("@permission.has('home:content:add')")
    @PostMapping
    public ResponseDTO<Void> createContent(@RequestBody HomeContentEntity content) {
        // 初始化统计数据
        if (content.getLikesCount() == null) {
            content.setLikesCount(0);
        }
        if (content.getCommentsCount() == null) {
            content.setCommentsCount(0);
        }
        if (content.getViewsCount() == null) {
            content.setViewsCount(0);
        }
        if (content.getStatus() == null) {
            content.setStatus(1);
        }
        
        // 新建稿件默认状态为待审核
        if (content.getAuditStatus() == null) {
            content.setAuditStatus(0);
        }

        boolean success = contentService.save(content);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "创建失败");
    }

    /**
     * 更新稿件
     */
    @Operation(summary = "更新稿件")
    @PreAuthorize("@permission.has('home:content:edit')")
    @PutMapping("/{id}")
    public ResponseDTO<Void> updateContent(@PathVariable Long id, @RequestBody HomeContentEntity content) {
        content.setContentId(id);
        boolean success = contentService.updateById(content);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新失败");
    }

    /**
     * 删除稿件
     */
    @Operation(summary = "删除稿件")
    @PreAuthorize("@permission.has('home:content:remove')")
    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteContent(@PathVariable Long id) {
        boolean success = contentService.removeById(id);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "删除失败");
    }

    /**
     * 批量删除稿件
     */
    @Operation(summary = "批量删除稿件")
    @PreAuthorize("@permission.has('home:content:remove')")
    @DeleteMapping("/batch")
    public ResponseDTO<Void> batchDeleteContent(@RequestBody Long[] ids) {
        boolean success = contentService.removeBatchByIds(java.util.Arrays.asList(ids));
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "批量删除失败");
    }

    /**
     * 更新稿件状态
     */
    @Operation(summary = "更新稿件状态")
    @PreAuthorize("@permission.has('home:content:edit')")
    @PutMapping("/{id}/status")
    public ResponseDTO<Void> updateContentStatus(@PathVariable Long id, @RequestParam Integer status) {
        HomeContentEntity content = new HomeContentEntity();
        content.setContentId(id);
        content.setStatus(status);
        boolean success = contentService.updateById(content);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新状态失败");
    }

    /**
     * 获取稿件统计数据
     */
    @Operation(summary = "获取稿件统计数据")
    @PreAuthorize("@permission.has('home:content:query')")
    @GetMapping("/{id}/stats")
    public ResponseDTO<Map<String, Object>> getContentStats(@PathVariable Long id) {
        HomeContentEntity content = contentService.getById(id);
        if (content == null) {
            return ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "稿件不存在");
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("likesCount", content.getLikesCount());
        stats.put("commentsCount", content.getCommentsCount());
        stats.put("viewsCount", content.getViewsCount());

        return ResponseDTO.ok(stats);
    }

    /**
     * 审核稿件
     */
    @Operation(summary = "审核稿件")
    @PreAuthorize("@permission.has('home:content:audit')")
    @PutMapping("/{id}/audit")
    public ResponseDTO<Void> auditContent(
            @PathVariable Long id,
            @RequestParam Integer auditStatus,
            @RequestParam(required = false) String auditRemark) {
        HomeContentEntity content = new HomeContentEntity();
        content.setContentId(id);
        content.setAuditStatus(auditStatus);
        if (auditRemark != null) {
            content.setAuditRemark(auditRemark);
        }
        boolean success = contentService.updateById(content);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "审核失败");
    }
}
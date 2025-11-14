package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.home.tag.db.HomeTagEntity;
import com.agileboot.domain.home.tag.db.HomeTagService;
import com.agileboot.common.enums.common.BusinessTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理控制器
 *
 * @author agileboot
 */
@Tag(name = "标签管理", description = "后台标签管理接口")
@RestController
@RequestMapping("/admin/home/tag")
@RequiredArgsConstructor
public class HomeTagAdminController extends BaseController {

    private final HomeTagService tagService;

    /**
     * 获取标签列表（分页）
     */
    @Operation(summary = "获取标签列表")
    @PreAuthorize("@permission.has('home:tag:list')")
    @GetMapping("/list")
    public ResponseDTO<Page<HomeTagEntity>> getTagList(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) Integer status) {

        Page<HomeTagEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();

        // 标签名称模糊查询
        if (tagName != null && !tagName.isEmpty()) {
            wrapper.like(HomeTagEntity::getTagName, tagName);
        }

        // 状态筛选
        if (status != null) {
            wrapper.eq(HomeTagEntity::getStatus, status);
        }

        // 按排序号升序
        wrapper.orderByAsc(HomeTagEntity::getSortOrder);

        Page<HomeTagEntity> result = tagService.page(page, wrapper);
        return ResponseDTO.ok(result);
    }

    /**
     * 获取所有标签（不分页）
     */
    @Operation(summary = "获取所有标签")
    @PreAuthorize("@permission.has('home:tag:list')")
    @GetMapping("/all")
    public ResponseDTO<List<HomeTagEntity>> getAllTags() {
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeTagEntity::getStatus, 1)
                .orderByAsc(HomeTagEntity::getSortOrder);

        List<HomeTagEntity> list = tagService.list(wrapper);
        return ResponseDTO.ok(list);
    }

    /**
     * 获取标签详情
     */
    @Operation(summary = "获取标签详情")
    @PreAuthorize("@permission.has('home:tag:query')")
    @GetMapping("/{id}")
    public ResponseDTO<HomeTagEntity> getTagDetail(@PathVariable Long id) {
        HomeTagEntity tag = tagService.getById(id);
        return ResponseDTO.ok(tag);
    }

    /**
     * 创建标签
     */
    @Operation(summary = "创建标签")
    @PreAuthorize("@permission.has('home:tag:add')")
    @PostMapping
    public ResponseDTO<Void> createTag(@RequestBody HomeTagEntity tag) {
        // 检查标签名称是否已存在
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeTagEntity::getTagName, tag.getTagName());
        if (tagService.count(wrapper) > 0) {
            return ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "标签名称已存在");
        }

        if (tag.getStatus() == null) {
            tag.setStatus(1);
        }

        boolean success = tagService.save(tag);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "创建失败");
    }

    /**
     * 更新标签
     */
    @Operation(summary = "更新标签")
    @PreAuthorize("@permission.has('home:tag:edit')")
    @PutMapping("/{id}")
    public ResponseDTO<Void> updateTag(@PathVariable Long id, @RequestBody HomeTagEntity tag) {
        // 检查标签名称是否已被其他标签使用
        LambdaQueryWrapper<HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeTagEntity::getTagName, tag.getTagName())
                .ne(HomeTagEntity::getTagId, id);
        if (tagService.count(wrapper) > 0) {
            return ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "标签名称已存在");
        }

        tag.setTagId(id);
        boolean success = tagService.updateById(tag);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新失败");
    }

    /**
     * 删除标签
     */
    @Operation(summary = "删除标签")
    @PreAuthorize("@permission.has('home:tag:remove')")
    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteTag(@PathVariable Long id) {
        boolean success = tagService.removeById(id);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "删除失败");
    }

    /**
     * 批量删除标签
     */
    @Operation(summary = "批量删除标签")
    @PreAuthorize("@permission.has('home:tag:remove')")
    @DeleteMapping("/batch")
    public ResponseDTO<Void> batchDeleteTag(@RequestBody Long[] ids) {
        boolean success = tagService.removeBatchByIds(java.util.Arrays.asList(ids));
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "批量删除失败");
    }

    /**
     * 更新标签状态
     */
    @Operation(summary = "更新标签状态")
    @PreAuthorize("@permission.has('home:tag:edit')")
    @PutMapping("/{id}/status")
    public ResponseDTO<Void> updateTagStatus(@PathVariable Long id, @RequestParam Integer status) {
        HomeTagEntity tag = new HomeTagEntity();
        tag.setTagId(id);
        tag.setStatus(status);
        boolean success = tagService.updateById(tag);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新状态失败");
    }

    /**
     * 批量更新排序
     */
    @Operation(summary = "批量更新排序")
    @PreAuthorize("@permission.has('home:tag:edit')")
    @PutMapping("/sort")
    public ResponseDTO<Void> updateTagSort(@RequestBody List<HomeTagEntity> tags) {
        boolean success = tagService.updateBatchById(tags);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新排序失败");
    }
}
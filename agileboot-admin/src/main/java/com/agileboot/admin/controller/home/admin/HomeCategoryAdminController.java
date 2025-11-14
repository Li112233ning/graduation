package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.home.category.db.HomeCategoryEntity;
import com.agileboot.domain.home.category.db.HomeCategoryService;

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
 * 分类管理控制器
 *
 * @author agileboot
 */
@Tag(name = "分类管理", description = "后台分类管理接口")
@RestController
@RequestMapping("/admin/home/category")
@RequiredArgsConstructor
public class HomeCategoryAdminController extends BaseController {

    private final HomeCategoryService categoryService;

    /**
     * 获取分类列表（分页）
     */
    @Operation(summary = "获取分类列表")
    @PreAuthorize("@permission.has('home:category:list')")
    @GetMapping("/list")
    public ResponseDTO<Page<HomeCategoryEntity>> getCategoryList(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer status) {

        Page<HomeCategoryEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<HomeCategoryEntity> wrapper = new LambdaQueryWrapper<>();

        // 分类名称模糊查询
        if (categoryName != null && !categoryName.isEmpty()) {
            wrapper.like(HomeCategoryEntity::getCategoryName, categoryName);
        }

        // 状态筛选
        if (status != null) {
            wrapper.eq(HomeCategoryEntity::getStatus, status);
        }

        // 按排序号升序
        wrapper.orderByAsc(HomeCategoryEntity::getSortOrder);

        Page<HomeCategoryEntity> result = categoryService.page(page, wrapper);
        return ResponseDTO.ok(result);
    }

    /**
     * 获取所有分类（不分页）
     */
    @Operation(summary = "获取所有分类")
    @PreAuthorize("@permission.has('home:category:list')")
    @GetMapping("/all")
    public ResponseDTO<List<HomeCategoryEntity>> getAllCategories() {
        LambdaQueryWrapper<HomeCategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCategoryEntity::getStatus, 1)
                .orderByAsc(HomeCategoryEntity::getSortOrder);

        List<HomeCategoryEntity> list = categoryService.list(wrapper);
        return ResponseDTO.ok(list);
    }

    /**
     * 获取分类详情
     */
    @Operation(summary = "获取分类详情")
    @PreAuthorize("@permission.has('home:category:query')")
    @GetMapping("/{id}")
    public ResponseDTO<HomeCategoryEntity> getCategoryDetail(@PathVariable Long id) {
        HomeCategoryEntity category = categoryService.getById(id);
        return ResponseDTO.ok(category);
    }

    /**
     * 创建分类
     */
    @Operation(summary = "创建分类")
    @PreAuthorize("@permission.has('home:category:add')")
    @PostMapping
    public ResponseDTO<Void> createCategory(@RequestBody HomeCategoryEntity category) {
        // 检查分类名称是否已存在
        LambdaQueryWrapper<HomeCategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCategoryEntity::getCategoryName, category.getCategoryName());
        if (categoryService.count(wrapper) > 0) {
            return ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "分类名称已存在");
        }

        if (category.getStatus() == null) {
            category.setStatus(1);
        }

        boolean success = categoryService.save(category);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "创建失败");
    }

    /**
     * 更新分类
     */
    @Operation(summary = "更新分类")
    @PreAuthorize("@permission.has('home:category:edit')")
    @PutMapping("/{id}")
    public ResponseDTO<Void> updateCategory(@PathVariable Long id, @RequestBody HomeCategoryEntity category) {
        // 检查分类名称是否已被其他分类使用
        LambdaQueryWrapper<HomeCategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCategoryEntity::getCategoryName, category.getCategoryName())
                .ne(HomeCategoryEntity::getCategoryId, id);
        if (categoryService.count(wrapper) > 0) {
            return ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "分类名称已存在");
        }

        category.setCategoryId(id);
        boolean success = categoryService.updateById(category);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新失败");
    }

    /**
     * 删除分类
     */
    @Operation(summary = "删除分类")
    @PreAuthorize("@permission.has('home:category:remove')")
    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteCategory(@PathVariable Long id) {
        // TODO: 检查是否有稿件使用该分类，如果有则不允许删除
        boolean success = categoryService.removeById(id);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "删除失败");
    }

    /**
     * 批量删除分类
     */
    @Operation(summary = "批量删除分类")
    @PreAuthorize("@permission.has('home:category:remove')")
    @DeleteMapping("/batch")
    public ResponseDTO<Void> batchDeleteCategory(@RequestBody Long[] ids) {
        boolean success = categoryService.removeBatchByIds(java.util.Arrays.asList(ids));
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "批量删除失败");
    }

    /**
     * 更新分类状态
     */
    @Operation(summary = "更新分类状态")
    @PreAuthorize("@permission.has('home:category:edit')")
    @PutMapping("/{id}/status")
    public ResponseDTO<Void> updateCategoryStatus(@PathVariable Long id, @RequestParam Integer status) {
        HomeCategoryEntity category = new HomeCategoryEntity();
        category.setCategoryId(id);
        category.setStatus(status);
        boolean success = categoryService.updateById(category);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新状态失败");
    }

    /**
     * 批量更新排序
     */
    @Operation(summary = "批量更新排序")
    @PreAuthorize("@permission.has('home:category:edit')")
    @PutMapping("/sort")
    public ResponseDTO<Void> updateCategorySort(@RequestBody List<HomeCategoryEntity> categories) {
        boolean success = categoryService.updateBatchById(categories);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新排序失败");
    }
}
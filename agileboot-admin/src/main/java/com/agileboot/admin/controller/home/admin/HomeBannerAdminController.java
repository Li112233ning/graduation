package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.home.banner.db.HomeBannerEntity;
import com.agileboot.domain.home.banner.db.HomeBannerService;
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
 * 轮播图管理控制器
 *
 * @author agileboot
 */
@Tag(name = "轮播图管理", description = "后台轮播图管理接口")
@RestController
@RequestMapping("/admin/home/banner")
@RequiredArgsConstructor
public class HomeBannerAdminController extends BaseController {

    private final HomeBannerService bannerService;

    /**
     * 获取轮播图列表（分页）
     */
    @Operation(summary = "获取轮播图列表")
    @PreAuthorize("@permission.has('home:banner:list')")
    @GetMapping("/list")
    public ResponseDTO<Page<HomeBannerEntity>> getBannerList(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer status) {

        Page<HomeBannerEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<HomeBannerEntity> wrapper = new LambdaQueryWrapper<>();

        // 标题模糊查询
        if (title != null && !title.isEmpty()) {
            wrapper.like(HomeBannerEntity::getTitle, title);
        }

        // 状态筛选
        if (status != null) {
            wrapper.eq(HomeBannerEntity::getStatus, status);
        }

        // 按排序号升序
        wrapper.orderByAsc(HomeBannerEntity::getSortOrder);

        Page<HomeBannerEntity> result = bannerService.page(page, wrapper);
        return ResponseDTO.ok(result);
    }

    /**
     * 获取所有轮播图（不分页）
     */
    @Operation(summary = "获取所有轮播图")
    @PreAuthorize("@permission.has('home:banner:list')")
    @GetMapping("/all")
    public ResponseDTO<List<HomeBannerEntity>> getAllBanners() {
        LambdaQueryWrapper<HomeBannerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeBannerEntity::getStatus, 1)
                .orderByAsc(HomeBannerEntity::getSortOrder);

        List<HomeBannerEntity> list = bannerService.list(wrapper);
        return ResponseDTO.ok(list);
    }

    /**
     * 获取轮播图详情
     */
    @Operation(summary = "获取轮播图详情")
    @PreAuthorize("@permission.has('home:banner:query')")
    @GetMapping("/{id}")
    public ResponseDTO<HomeBannerEntity> getBannerDetail(@PathVariable Long id) {
        HomeBannerEntity banner = bannerService.getById(id);
        return ResponseDTO.ok(banner);
    }

    /**
     * 创建轮播图
     */
    @Operation(summary = "创建轮播图")
    @PreAuthorize("@permission.has('home:banner:add')")
    @PostMapping
    public ResponseDTO<Void> createBanner(@RequestBody HomeBannerEntity banner) {
        if (banner.getStatus() == null) {
            banner.setStatus(1);
        }

        boolean success = bannerService.save(banner);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "创建失败");
    }

    /**
     * 更新轮播图
     */
    @Operation(summary = "更新轮播图")
    @PreAuthorize("@permission.has('home:banner:edit')")
    @PutMapping("/{id}")
    public ResponseDTO<Void> updateBanner(@PathVariable Long id, @RequestBody HomeBannerEntity banner) {
        banner.setBannerId(id);
        boolean success = bannerService.updateById(banner);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新失败");
    }

    /**
     * 删除轮播图
     */
    @Operation(summary = "删除轮播图")
    @PreAuthorize("@permission.has('home:banner:remove')")
    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteBanner(@PathVariable Long id) {
        boolean success = bannerService.removeById(id);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "删除失败");
    }

    /**
     * 批量删除轮播图
     */
    @Operation(summary = "批量删除轮播图")
    @PreAuthorize("@permission.has('home:banner:remove')")
    @DeleteMapping("/batch")
    public ResponseDTO<Void> batchDeleteBanner(@RequestBody Long[] ids) {
        boolean success = bannerService.removeBatchByIds(java.util.Arrays.asList(ids));
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "批量删除失败");
    }

    /**
     * 更新轮播图状态
     */
    @Operation(summary = "更新轮播图状态")
    @PreAuthorize("@permission.has('home:banner:edit')")
    @PutMapping("/{id}/status")
    public ResponseDTO<Void> updateBannerStatus(@PathVariable Long id, @RequestParam Integer status) {
        HomeBannerEntity banner = new HomeBannerEntity();
        banner.setBannerId(id);
        banner.setStatus(status);
        boolean success = bannerService.updateById(banner);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新状态失败");
    }

    /**
     * 批量更新排序
     */
    @Operation(summary = "批量更新排序")
    @PreAuthorize("@permission.has('home:banner:edit')")
    @PutMapping("/sort")
    public ResponseDTO<Void> updateBannerSort(@RequestBody List<HomeBannerEntity> banners) {
        boolean success = bannerService.updateBatchById(banners);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新排序失败");
    }
}
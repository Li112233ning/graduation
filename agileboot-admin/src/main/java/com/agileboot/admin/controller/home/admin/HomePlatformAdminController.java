package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.home.platform.db.HomePlatformEntity;
import com.agileboot.domain.home.platform.db.HomePlatformService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台管理控制器
 *
 * @author agileboot
 */
@Tag(name = "平台管理", description = "后台平台管理接口")
@RestController
@RequestMapping("/admin/home/platform")
@RequiredArgsConstructor
public class HomePlatformAdminController extends BaseController {

    private final HomePlatformService platformService;

    /**
     * 获取平台列表（分页）
     */
    @Operation(summary = "获取平台列表")
    @PreAuthorize("@permission.has('home:platform:list')")
    @GetMapping("/list")
    public ResponseDTO<Page<HomePlatformEntity>> getPlatformList(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String platformName,
            @RequestParam(required = false) Integer status) {

        Page<HomePlatformEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<HomePlatformEntity> wrapper = new LambdaQueryWrapper<>();

        // 平台名称模糊查询
        if (platformName != null && !platformName.isEmpty()) {
            wrapper.like(HomePlatformEntity::getPlatformName, platformName);
        }

        // 状态筛选
        if (status != null) {
            wrapper.eq(HomePlatformEntity::getStatus, status);
        }

        // 按排序号升序
        wrapper.orderByAsc(HomePlatformEntity::getSortOrder);

        Page<HomePlatformEntity> result = platformService.page(page, wrapper);
        return ResponseDTO.ok(result);
    }

    /**
     * 获取所有平台（不分页）
     */
    @Operation(summary = "获取所有平台")
    @PreAuthorize("@permission.has('home:platform:list')")
    @GetMapping("/all")
    public ResponseDTO<List<HomePlatformEntity>> getAllPlatforms() {
        LambdaQueryWrapper<HomePlatformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomePlatformEntity::getStatus, 1)
                .orderByAsc(HomePlatformEntity::getSortOrder);

        List<HomePlatformEntity> list = platformService.list(wrapper);
        return ResponseDTO.ok(list);
    }

    /**
     * 获取平台详情
     */
    @Operation(summary = "获取平台详情")
    @PreAuthorize("@permission.has('home:platform:query')")
    @GetMapping("/{id}")
    public ResponseDTO<HomePlatformEntity> getPlatformDetail(@PathVariable Long id) {
        HomePlatformEntity platform = platformService.getById(id);
        return ResponseDTO.ok(platform);
    }

    /**
     * 创建平台
     */
    @Operation(summary = "创建平台")
    @PreAuthorize("@permission.has('home:platform:add')")
    @PostMapping
    public ResponseDTO<Void> createPlatform(@RequestBody HomePlatformEntity platform) {
        // 检查平台名称是否已存在
        LambdaQueryWrapper<HomePlatformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomePlatformEntity::getPlatformName, platform.getPlatformName());
        if (platformService.count(wrapper) > 0) {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "平台名称已存在");
        }

        if (platform.getStatus() == null) {
            platform.setStatus(1);
        }

        boolean success = platformService.save(platform);
        if (success) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "创建失败");
        }
    }

    /**
     * 更新平台
     */
    @Operation(summary = "更新平台")
    @PreAuthorize("@permission.has('home:platform:edit')")
    @PutMapping("/{id}")
    public ResponseDTO<Void> updatePlatform(@PathVariable Long id, @RequestBody HomePlatformEntity platform) {
        // 检查平台名称是否已被其他平台使用
        LambdaQueryWrapper<HomePlatformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomePlatformEntity::getPlatformName, platform.getPlatformName())
                .ne(HomePlatformEntity::getPlatformId, id);
        if (platformService.count(wrapper) > 0) {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "平台名称已存在");
        }

        platform.setPlatformId(id);
        boolean success = platformService.updateById(platform);
        if (success) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "更新失败");
        }
    }

    /**
     * 删除平台
     */
    @Operation(summary = "删除平台")
    @PreAuthorize("@permission.has('home:platform:remove')")
    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deletePlatform(@PathVariable Long id) {
        boolean success = platformService.removeById(id);
        if (success) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "删除失败");
        }
    }

    /**
     * 批量删除平台
     */
    @Operation(summary = "批量删除平台")
    @PreAuthorize("@permission.has('home:platform:remove')")
    @DeleteMapping("/batch")
    public ResponseDTO<Void> batchDeletePlatform(@RequestBody Long[] ids) {
        boolean success = platformService.removeBatchByIds(java.util.Arrays.asList(ids));
        if (success) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "批量删除失败");
        }
    }

    /**
     * 更新平台状态
     */
    @Operation(summary = "更新平台状态")
    @PreAuthorize("@permission.has('home:platform:edit')")
    @PutMapping("/{id}/status")
    public ResponseDTO<Void> updatePlatformStatus(@PathVariable Long id, @RequestParam Integer status) {
        HomePlatformEntity platform = new HomePlatformEntity();
        platform.setPlatformId(id);
        platform.setStatus(status);
        boolean success = platformService.updateById(platform);
        if (success) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "更新状态失败");
        }
    }

    /**
     * 批量更新排序
     */
    @Operation(summary = "批量更新排序")
    @PreAuthorize("@permission.has('home:platform:edit')")
    @PutMapping("/sort")
    public ResponseDTO<Void> updatePlatformSort(@RequestBody List<HomePlatformEntity> platforms) {
        boolean success = platformService.updateBatchById(platforms);
        if (success) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.build(null, ErrorCode.FAILED.code(), "更新排序失败");
        }
    }
}
package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
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

import java.util.List;

/**
 * 达人管理控制器
 *
 * @author agileboot
 */
@Tag(name = "达人管理", description = "后台达人管理接口")
@RestController
@RequestMapping("/admin/home/influencer")
@RequiredArgsConstructor
public class HomeInfluencerAdminController extends BaseController {

    private final HomeInfluencerService influencerService;

    /**
     * 获取达人列表（分页）
     */
    @Operation(summary = "获取达人列表")
    @PreAuthorize("@permission.has('home:influencer:list')")
    @GetMapping("/list")
    public ResponseDTO<Page<HomeInfluencerEntity>> getInfluencerList(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) Integer status) {

        Page<HomeInfluencerEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<HomeInfluencerEntity> wrapper = new LambdaQueryWrapper<>();

        // 达人名称模糊查询
        if (name != null && !name.isEmpty()) {
            wrapper.like(HomeInfluencerEntity::getName, name);
        }

        // 平台筛选
        if (platform != null && !platform.isEmpty()) {
            wrapper.eq(HomeInfluencerEntity::getPlatform, platform);
        }

        // 领域筛选
        if (field != null && !field.isEmpty()) {
            wrapper.like(HomeInfluencerEntity::getField, field);
        }

        // 状态筛选
        if (status != null) {
            wrapper.eq(HomeInfluencerEntity::getStatus, status);
        }

        // 按排序号升序
        wrapper.orderByAsc(HomeInfluencerEntity::getSortOrder);

        Page<HomeInfluencerEntity> result = influencerService.page(page, wrapper);
        return ResponseDTO.ok(result);
    }

    /**
     * 获取所有达人（不分页）
     */
    @Operation(summary = "获取所有达人")
    @PreAuthorize("@permission.has('home:influencer:list')")
    @GetMapping("/all")
    public ResponseDTO<List<HomeInfluencerEntity>> getAllInfluencers() {
        LambdaQueryWrapper<HomeInfluencerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeInfluencerEntity::getStatus, 1)
                .orderByAsc(HomeInfluencerEntity::getSortOrder);

        List<HomeInfluencerEntity> list = influencerService.list(wrapper);
        return ResponseDTO.ok(list);
    }

    /**
     * 获取达人详情
     */
    @Operation(summary = "获取达人详情")
    @PreAuthorize("@permission.has('home:influencer:query')")
    @GetMapping("/{id}")
    public ResponseDTO<HomeInfluencerEntity> getInfluencerDetail(@PathVariable Long id) {
        HomeInfluencerEntity influencer = influencerService.getById(id);
        return ResponseDTO.ok(influencer);
    }

    /**
     * 创建达人
     */
    @Operation(summary = "创建达人")
    @PreAuthorize("@permission.has('home:influencer:add')")
    @PostMapping
    public ResponseDTO<Void> createInfluencer(@RequestBody HomeInfluencerEntity influencer) {
        if (influencer.getStatus() == null) {
            influencer.setStatus(1);
        }

        boolean success = influencerService.save(influencer);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "创建失败");
    }

    /**
     * 更新达人
     */
    @Operation(summary = "更新达人")
    @PreAuthorize("@permission.has('home:influencer:edit')")
    @PutMapping("/{id}")
    public ResponseDTO<Void> updateInfluencer(@PathVariable Long id, @RequestBody HomeInfluencerEntity influencer) {
        influencer.setInfluencerId(id);
        boolean success = influencerService.updateById(influencer);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新失败");
    }

    /**
     * 删除达人
     */
    @Operation(summary = "删除达人")
    @PreAuthorize("@permission.has('home:influencer:remove')")
    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteInfluencer(@PathVariable Long id) {
        boolean success = influencerService.removeById(id);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "删除失败");
    }

    /**
     * 批量删除达人
     */
    @Operation(summary = "批量删除达人")
    @PreAuthorize("@permission.has('home:influencer:remove')")
    @DeleteMapping("/batch")
    public ResponseDTO<Void> batchDeleteInfluencer(@RequestBody Long[] ids) {
        boolean success = influencerService.removeBatchByIds(java.util.Arrays.asList(ids));
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "批量删除失败");
    }

    /**
     * 更新达人状态
     */
    @Operation(summary = "更新达人状态")
    @PreAuthorize("@permission.has('home:influencer:edit')")
    @PutMapping("/{id}/status")
    public ResponseDTO<Void> updateInfluencerStatus(@PathVariable Long id, @RequestParam Integer status) {
        HomeInfluencerEntity influencer = new HomeInfluencerEntity();
        influencer.setInfluencerId(id);
        influencer.setStatus(status);
        boolean success = influencerService.updateById(influencer);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新状态失败");
    }

    /**
     * 批量更新排序
     */
    @Operation(summary = "批量更新排序")
    @PreAuthorize("@permission.has('home:influencer:edit')")
    @PutMapping("/sort")
    public ResponseDTO<Void> updateInfluencerSort(@RequestBody List<HomeInfluencerEntity> influencers) {
        boolean success = influencerService.updateBatchById(influencers);
        return success ? ResponseDTO.ok() : ResponseDTO.build(null, com.agileboot.common.exception.error.ErrorCode.FAILED.code(), "更新排序失败");
    }
}
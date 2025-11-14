package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.home.dto.InfluencerApplicationDTO;
import com.agileboot.domain.home.influencerapplication.InfluencerApplicationManageService;
import com.agileboot.domain.home.influencerapplication.query.InfluencerApplicationQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "达人申请管理API")
@RestController
@RequestMapping("/admin/influencer/application")
@RequiredArgsConstructor
public class InfluencerApplicationAdminController extends BaseController {

    private final InfluencerApplicationManageService applicationManageService;

    @Operation(summary = "查询申请列表")
    @GetMapping("/list")
    @PreAuthorize("@permission.hasPermission('home:application:list')")
    public ResponseDTO<PageDTO<InfluencerApplicationDTO>> getApplicationList(InfluencerApplicationQuery query) {
        PageDTO<InfluencerApplicationDTO> pageDTO = applicationManageService.getApplicationList(query);
        return ResponseDTO.ok(pageDTO);
    }

    @Operation(summary = "查询申请详情")
    @GetMapping("/{id}")
    @PreAuthorize("@permission.hasPermission('home:application:query')")
    public ResponseDTO<InfluencerApplicationDTO> getApplicationDetail(@PathVariable Long id) {
        // 可以复用查询逻辑或单独实现
        return ResponseDTO.ok();
    }

    @Operation(summary = "审核申请")
    @PutMapping("/{id}/audit")
    @PreAuthorize("@permission.hasPermission('home:application:audit')")
    public ResponseDTO<Void> auditApplication(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestParam(required = false) String rejectReason) {
        applicationManageService.auditApplication(id, status, rejectReason);
        return ResponseDTO.ok();
    }
}

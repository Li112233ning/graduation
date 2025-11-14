package com.agileboot.api.controller.app;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.home.dto.InfluencerApplicationDTO;
import com.agileboot.domain.home.influencerapplication.InfluencerApplicationManageService;
import com.agileboot.domain.home.influencerapplication.command.SubmitApplicationCommand;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "达人申请API")
@RestController
@RequestMapping("/api/influencer")
@RequiredArgsConstructor
public class InfluencerApplicationController extends BaseController {

    private final InfluencerApplicationManageService applicationManageService;

    @Operation(summary = "提交达人申请")
    @PostMapping("/application")
    public ResponseDTO<Void> submitApplication(@Validated @RequestBody SubmitApplicationCommand command) {
        applicationManageService.submitApplication(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "查询我的申请")
    @GetMapping("/application/my")
    public ResponseDTO<InfluencerApplicationDTO> getMyApplication() {
        InfluencerApplicationDTO dto = applicationManageService.getMyApplication();
        return ResponseDTO.ok(dto);
    }

    @Operation(summary = "查询用户角色信息")
    @GetMapping("/role")
    public ResponseDTO<Map<String, Boolean>> getInfluencerRole() {
        Long userId = AuthenticationUtils.getUserId();
        boolean isInfluencer = applicationManageService.checkIsInfluencer(userId);
        
        Map<String, Boolean> result = new HashMap<>();
        result.put("isInfluencer", isInfluencer);
        
        return ResponseDTO.ok(result);
    }
}

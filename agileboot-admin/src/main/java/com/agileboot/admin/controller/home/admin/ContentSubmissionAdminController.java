package com.agileboot.admin.controller.home.admin;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.home.dto.ContentSubmissionDTO;
import com.agileboot.domain.home.contentsubmission.ContentSubmissionManageService;
import com.agileboot.domain.home.contentsubmission.query.ContentSubmissionQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "内容投稿管理API")
@RestController
@RequestMapping("/admin/content/submission")
@RequiredArgsConstructor
public class ContentSubmissionAdminController extends BaseController {

    private final ContentSubmissionManageService submissionManageService;

    @Operation(summary = "查询投稿列表")
    @GetMapping("/list")
    @PreAuthorize("@permission.hasPermission('home:submission:list')")
    public ResponseDTO<PageDTO<ContentSubmissionDTO>> getSubmissionList(ContentSubmissionQuery query) {
        PageDTO<ContentSubmissionDTO> pageDTO = submissionManageService.getSubmissionList(query);
        return ResponseDTO.ok(pageDTO);
    }

    @Operation(summary = "查询投稿详情")
    @GetMapping("/{id}")
    @PreAuthorize("@permission.hasPermission('home:submission:query')")
    public ResponseDTO<ContentSubmissionDTO> getSubmissionDetail(@PathVariable Long id) {
        ContentSubmissionDTO dto = submissionManageService.getSubmissionDetail(id);
        return ResponseDTO.ok(dto);
    }

    @Operation(summary = "审核投稿")
    @PutMapping("/{id}/audit")
    @PreAuthorize("@permission.hasPermission('home:submission:audit')")
    public ResponseDTO<Void> auditSubmission(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestParam(required = false) String rejectReason) {
        submissionManageService.auditSubmission(id, status, rejectReason);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除投稿")
    @DeleteMapping("/{id}")
    @PreAuthorize("@permission.hasPermission('home:submission:remove')")
    public ResponseDTO<Void> deleteSubmission(@PathVariable Long id) {
        submissionManageService.deleteSubmission(id);
        return ResponseDTO.ok();
    }

    @Operation(summary = "批量删除投稿")
    @DeleteMapping("/batch")
    @PreAuthorize("@permission.hasPermission('home:submission:remove')")
    public ResponseDTO<Void> batchDeleteSubmission(@RequestBody List<Long> ids) {
        submissionManageService.batchDeleteSubmission(ids);
        return ResponseDTO.ok();
    }
}

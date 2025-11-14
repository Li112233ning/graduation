package com.agileboot.api.controller.app;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.home.dto.ContentSubmissionDTO;
import com.agileboot.domain.home.contentsubmission.ContentSubmissionManageService;
import com.agileboot.domain.home.contentsubmission.command.SaveSubmissionCommand;
import com.agileboot.domain.home.contentsubmission.query.ContentSubmissionQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "内容投稿API")
@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentSubmissionController extends BaseController {

    private final ContentSubmissionManageService submissionManageService;

    @Operation(summary = "保存稿件")
    @PostMapping("/submission")
    public ResponseDTO<ContentSubmissionDTO> saveSubmission(@Validated @RequestBody SaveSubmissionCommand command) {
        ContentSubmissionDTO dto = submissionManageService.saveSubmission(command);
        return ResponseDTO.ok(dto);
    }

    @Operation(summary = "提交稿件审核")
    @PutMapping("/submission/{id}/submit")
    public ResponseDTO<Void> submitForReview(@PathVariable Long id) {
        submissionManageService.submitForReview(id);
        return ResponseDTO.ok();
    }

    @Operation(summary = "查询我的稿件列表")
    @GetMapping("/submission/my")
    public ResponseDTO<PageDTO<ContentSubmissionDTO>> getMySubmissions(ContentSubmissionQuery query) {
        PageDTO<ContentSubmissionDTO> pageDTO = submissionManageService.getMySubmissions(query);
        return ResponseDTO.ok(pageDTO);
    }

    @Operation(summary = "查询稿件详情")
    @GetMapping("/submission/{id}")
    public ResponseDTO<ContentSubmissionDTO> getSubmissionDetail(@PathVariable Long id) {
        ContentSubmissionDTO dto = submissionManageService.getSubmissionDetail(id);
        return ResponseDTO.ok(dto);
    }

    @Operation(summary = "删除稿件")
    @DeleteMapping("/submission/{id}")
    public ResponseDTO<Void> deleteSubmission(@PathVariable Long id) {
        submissionManageService.deleteSubmission(id);
        return ResponseDTO.ok();
    }
}

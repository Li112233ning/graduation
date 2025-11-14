package com.agileboot.admin.controller.home;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.home.content.ContentManageService;
import com.agileboot.domain.home.content.command.AddContentCommand;
import com.agileboot.domain.home.content.command.UpdateContentCommand;
import com.agileboot.domain.home.content.dto.ContentManageDTO;
import com.agileboot.domain.home.content.query.ContentQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内容管理控制器
 *
 * @author agileboot
 */
@Tag(name = "内容管理API", description = "内容管理相关接口")
@RestController
@RequestMapping("/admin/content")
@RequiredArgsConstructor
public class ContentManageController extends BaseController {

    private final ContentManageService contentManageService;

    /**
     * 获取内容列表
     */
    @Operation(summary = "内容列表")
    @GetMapping("/list")
    @PreAuthorize("@permission.has('home:content:list')")
    public ResponseDTO<PageDTO<ContentManageDTO>> list(ContentQuery query) {
        PageDTO<ContentManageDTO> pageDTO = contentManageService.getContentList(query);
        return ResponseDTO.ok(pageDTO);
    }

    /**
     * 获取内容详情
     */
    @Operation(summary = "内容详情")
    @GetMapping("/{contentId}")
    @PreAuthorize("@permission.has('home:content:query')")
    public ResponseDTO<ContentManageDTO> getDetail(@PathVariable Long contentId) {
        ContentManageDTO dto = contentManageService.getContentDetail(contentId);
        return ResponseDTO.ok(dto);
    }

    /**
     * 新增内容
     */
    @Operation(summary = "新增内容")
    @PostMapping
    @PreAuthorize("@permission.has('home:content:add')")
    public ResponseDTO<Void> add(@Validated @RequestBody AddContentCommand command) {
        contentManageService.addContent(command);
        return ResponseDTO.ok();
    }

    /**
     * 更新内容
     */
    @Operation(summary = "更新内容")
    @PutMapping
    @PreAuthorize("@permission.has('home:content:edit')")
    public ResponseDTO<Void> update(@Validated @RequestBody UpdateContentCommand command) {
        contentManageService.updateContent(command);
        return ResponseDTO.ok();
    }

    /**
     * 删除内容
     */
    @Operation(summary = "删除内容")
    @DeleteMapping("/{contentId}")
    @PreAuthorize("@permission.has('home:content:remove')")
    public ResponseDTO<Void> delete(@PathVariable Long contentId) {
        contentManageService.deleteContent(contentId);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除内容
     */
    @Operation(summary = "批量删除内容")
    @DeleteMapping("/batch")
    @PreAuthorize("@permission.has('home:content:remove')")
    public ResponseDTO<Void> batchDelete(@RequestBody List<Long> contentIds) {
        contentManageService.batchDeleteContent(contentIds);
        return ResponseDTO.ok();
    }
}
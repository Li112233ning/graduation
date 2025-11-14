package com.agileboot.admin.controller.home;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.home.tag.TagManageService;
import com.agileboot.domain.home.tag.command.AddTagCommand;
import com.agileboot.domain.home.tag.command.UpdateTagCommand;
import com.agileboot.domain.home.tag.dto.TagDTO;
import com.agileboot.domain.home.tag.query.TagQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理控制器
 *
 * @author agileboot
 */
@Tag(name = "标签管理API", description = "标签管理相关接口")
@RestController
@RequestMapping("/admin/tag")
@RequiredArgsConstructor
public class TagManageController extends BaseController {

    private final TagManageService tagManageService;

    /**
     * 获取标签列表
     */
    @Operation(summary = "标签列表")
    @GetMapping("/list")
    @PreAuthorize("@permission.has('home:tag:list')")
    public ResponseDTO<PageDTO<TagDTO>> list(TagQuery query) {
        PageDTO<TagDTO> pageDTO = tagManageService.getTagList(query);
        return ResponseDTO.ok(pageDTO);
    }

    /**
     * 获取所有标签（不分页）
     */
    @Operation(summary = "获取所有标签")
    @GetMapping("/all")
    public ResponseDTO<List<TagDTO>> getAllTags() {
        List<TagDTO> list = tagManageService.getAllTags();
        return ResponseDTO.ok(list);
    }

    /**
     * 获取标签详情
     */
    @Operation(summary = "标签详情")
    @GetMapping("/{tagId}")
    @PreAuthorize("@permission.has('home:tag:query')")
    public ResponseDTO<TagDTO> getDetail(@PathVariable Long tagId) {
        TagDTO dto = tagManageService.getTagDetail(tagId);
        return ResponseDTO.ok(dto);
    }

    /**
     * 新增标签
     */
    @Operation(summary = "新增标签")
    @PostMapping
    @PreAuthorize("@permission.has('home:tag:add')")
    public ResponseDTO<Void> add(@Validated @RequestBody AddTagCommand command) {
        tagManageService.addTag(command);
        return ResponseDTO.ok();
    }

    /**
     * 更新标签
     */
    @Operation(summary = "更新标签")
    @PutMapping
    @PreAuthorize("@permission.has('home:tag:edit')")
    public ResponseDTO<Void> update(@Validated @RequestBody UpdateTagCommand command) {
        tagManageService.updateTag(command);
        return ResponseDTO.ok();
    }

    /**
     * 删除标签
     */
    @Operation(summary = "删除标签")
    @DeleteMapping("/{tagId}")
    @PreAuthorize("@permission.has('home:tag:remove')")
    public ResponseDTO<Void> delete(@PathVariable Long tagId) {
        tagManageService.deleteTag(tagId);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除标签
     */
    @Operation(summary = "批量删除标签")
    @DeleteMapping("/batch")
    @PreAuthorize("@permission.has('home:tag:remove')")
    public ResponseDTO<Void> batchDelete(@RequestBody List<Long> tagIds) {
        tagManageService.batchDeleteTag(tagIds);
        return ResponseDTO.ok();
    }
}
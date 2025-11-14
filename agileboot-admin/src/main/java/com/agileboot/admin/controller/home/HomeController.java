package com.agileboot.admin.controller.home;

import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.home.HomeApplicationService;
import com.agileboot.domain.home.dto.BannerDTO;
import com.agileboot.domain.home.dto.CategoryDTO;
import com.agileboot.domain.home.dto.ContentDTO;
import com.agileboot.domain.home.dto.InfluencerDTO;
import com.agileboot.domain.home.dto.PlatformDTO;
import com.agileboot.domain.home.dto.CommentDTO;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 首页API控制器
 *
 * @author agileboot
 */
@Tag(name = "首页API", description = "首页相关数据接口")
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController extends BaseController {

    private final HomeApplicationService homeApplicationService;

    /**
     * 获取轮播图列表
     */
    @Operation(summary = "轮播图列表")
    @GetMapping("/banners")
    public ResponseDTO<List<BannerDTO>> getBannerList() {
        List<BannerDTO> bannerList = homeApplicationService.getBannerList();
        return ResponseDTO.ok(bannerList);
    }

    /**
     * 获取分类列表
     */
    @Operation(summary = "分类列表")
    @GetMapping("/categories")
    public ResponseDTO<List<CategoryDTO>> getCategoryList() {
        List<CategoryDTO> categoryList = homeApplicationService.getCategoryList();
        return ResponseDTO.ok(categoryList);
    }

    /**
     * 获取精选内容列表
     */
    @Operation(summary = "精选内容列表")
    @GetMapping("/featured-contents")
    public ResponseDTO<List<ContentDTO>> getFeaturedContentList() {
        List<ContentDTO> contentList = homeApplicationService.getFeaturedContentList();
        return ResponseDTO.ok(contentList);
    }

    /**
     * 获取最新内容列表
     */
    @Operation(summary = "最新内容列表")
    @GetMapping("/latest-contents")
    public ResponseDTO<List<ContentDTO>> getLatestContentList() {
        List<ContentDTO> contentList = homeApplicationService.getLatestContentList();
        return ResponseDTO.ok(contentList);
    }

    /**
     * 获取优质达人列表
     */
    @Operation(summary = "优质达人列表")
    @GetMapping("/influencers")
    public ResponseDTO<List<InfluencerDTO>> getInfluencerList() {
        List<InfluencerDTO> influencerList = homeApplicationService.getInfluencerList();
        return ResponseDTO.ok(influencerList);
    }

    /**
     * 获取平台列表
     */
    @Operation(summary = "平台列表")
    @GetMapping("/platforms")
    public ResponseDTO<List<PlatformDTO>> getPlatformList() {
        List<PlatformDTO> platformList = homeApplicationService.getPlatformList();
        return ResponseDTO.ok(platformList);
    }

    /**
     * 获取内容详情
     */
    @Operation(summary = "内容详情")
    @GetMapping("/content/{id}")
    public ResponseDTO<ContentDTO> getContentDetail(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        ContentDTO content = homeApplicationService.getContentDetail(id, userId);
        if (content != null) {
            // 增加浏览量
            homeApplicationService.incrementViews(id);
        }
        return ResponseDTO.ok(content);
    }

    /**
     * 获取评论列表
     */
    @Operation(summary = "获取评论列表")
    @GetMapping("/content/{id}/comments")
    public ResponseDTO<List<CommentDTO>> getCommentList(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        List<CommentDTO> comments = homeApplicationService.getCommentList(id, userId);
        return ResponseDTO.ok(comments);
    }

    /**
     * 添加评论
     */
    @Operation(summary = "添加评论")
    @PostMapping("/content/{id}/comments")
    public ResponseDTO<CommentDTO> addComment(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseDTO.build(null, 401, "请先登录");
        }
        String content = request.get("content");
        if (content == null || content.trim().isEmpty()) {
            return ResponseDTO.build(null, 400, "评论内容不能为空");
        }
        Long parentId = request.get("parentId") != null && !request.get("parentId").isEmpty() 
                ? Long.parseLong(request.get("parentId")) : null;
        CommentDTO comment = homeApplicationService.addComment(id, userId, content, parentId);
        return ResponseDTO.ok(comment);
    }

    /**
     * 点赞/取消点赞内容
     */
    @Operation(summary = "点赞/取消点赞内容")
    @PostMapping("/content/{id}/like")
    public ResponseDTO<Map<String, Object>> toggleLike(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseDTO.build(null, 401, "请先登录");
        }
        boolean isLiked = homeApplicationService.toggleLike(id, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("isLiked", isLiked);
        // 获取更新后的点赞数
        ContentDTO content = homeApplicationService.getContentDetail(id, userId);
        if (content != null) {
            result.put("likes", content.getLikes());
        }
        return ResponseDTO.ok(result);
    }

    /**
     * 收藏/取消收藏内容
     */
    @Operation(summary = "收藏/取消收藏内容")
    @PostMapping("/content/{id}/collect")
    public ResponseDTO<Map<String, Object>> toggleCollect(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseDTO.build(null, 401, "请先登录");
        }
        boolean isCollected = homeApplicationService.toggleCollect(id, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("isCollected", isCollected);
        return ResponseDTO.ok(result);
    }

    /**
     * 安全地获取当前用户ID（如果未登录返回null）
     */
    private Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() 
                    && !"anonymousUser".equals(authentication.getPrincipal().toString())) {
                return AuthenticationUtils.getUserId();
            }
        } catch (Exception e) {
            // 用户未登录，返回null
        }
        return null;
    }

    /**
     * 获取内容列表（支持分页和筛选）
     */
    @Operation(summary = "内容列表")
    @GetMapping("/contents")
    public ResponseDTO<List<ContentDTO>> getContentList(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String platform,
        @RequestParam(required = false) String tag) {
        List<ContentDTO> contentList = homeApplicationService.getHomeContentList(type, category, platform, tag);
        return ResponseDTO.ok(contentList);
    }

    /**
     * 点赞/取消点赞评论
     */
    @Operation(summary = "点赞/取消点赞评论")
    @PostMapping("/comment/{id}/like")
    public ResponseDTO<Map<String, Object>> toggleCommentLike(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseDTO.build(null, 401, "请先登录");
        }
        boolean isLiked = homeApplicationService.toggleCommentLike(id, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("isLiked", isLiked);
        return ResponseDTO.ok(result);
    }

    /**
     * 关注/取消关注用户
     */
    @Operation(summary = "关注/取消关注用户")
    @PostMapping("/user/{id}/follow")
    public ResponseDTO<Map<String, Object>> toggleFollow(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseDTO.build(null, 401, "请先登录");
        }
        boolean isFollowed = homeApplicationService.toggleFollow(userId, id);
        Map<String, Object> result = new HashMap<>();
        result.put("isFollowed", isFollowed);
        return ResponseDTO.ok(result);
    }

    /**
     * 检查是否已关注某用户
     */
    @Operation(summary = "检查是否已关注某用户")
    @GetMapping("/user/{id}/follow/status")
    public ResponseDTO<Map<String, Object>> checkFollowStatus(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        boolean isFollowed = false;
        if (userId != null) {
            isFollowed = homeApplicationService.checkIsFollowed(userId, id);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("isFollowed", isFollowed);
        return ResponseDTO.ok(result);
    }

    /**
     * 根据达人ID获取稿件列表
     */
    @Operation(summary = "根据达人ID获取稿件列表")
    @GetMapping("/influencer/{id}/contents")
    public ResponseDTO<List<ContentDTO>> getContentListByInfluencerId(@PathVariable Long id) {
        List<ContentDTO> contentList = homeApplicationService.getContentListByInfluencerId(id);
        return ResponseDTO.ok(contentList);
    }
}


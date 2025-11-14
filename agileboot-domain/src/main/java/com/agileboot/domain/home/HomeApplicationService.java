package com.agileboot.domain.home;

import com.agileboot.domain.home.banner.db.HomeBannerEntity;
import com.agileboot.domain.home.banner.db.HomeBannerService;
import com.agileboot.domain.home.category.db.HomeCategoryEntity;
import com.agileboot.domain.home.category.db.HomeCategoryService;
import com.agileboot.domain.home.content.db.HomeContentEntity;
import com.agileboot.domain.home.content.db.HomeContentService;
import com.agileboot.domain.home.dto.BannerDTO;
import com.agileboot.domain.home.dto.CategoryDTO;
import com.agileboot.domain.home.dto.ContentDTO;
import com.agileboot.domain.home.dto.InfluencerDTO;
import com.agileboot.domain.home.dto.PlatformDTO;
import com.agileboot.domain.home.influencer.db.HomeInfluencerEntity;
import com.agileboot.domain.home.influencer.db.HomeInfluencerService;
import com.agileboot.domain.home.platform.db.HomePlatformEntity;
import com.agileboot.domain.home.platform.db.HomePlatformService;
import com.agileboot.domain.home.comment.db.HomeCommentEntity;
import com.agileboot.domain.home.comment.db.HomeCommentService;
import com.agileboot.domain.home.interaction.db.HomeUserInteractionEntity;
import com.agileboot.domain.home.interaction.db.HomeUserInteractionService;
import com.agileboot.domain.home.commentlike.db.HomeCommentLikeEntity;
import com.agileboot.domain.home.commentlike.db.HomeCommentLikeService;
import com.agileboot.domain.home.dto.CommentDTO;
import com.agileboot.domain.system.user.db.SysUserEntity;
import com.agileboot.domain.system.user.db.SysUserService;
import com.agileboot.domain.user.follow.db.UserFollowEntity;
import com.agileboot.domain.user.follow.db.UserFollowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

/**
 * 首页应用服务
 *
 * @author agileboot
 */
@Service
@RequiredArgsConstructor
public class HomeApplicationService {

    private final HomeBannerService bannerService;
    private final HomeCategoryService categoryService;
    private final HomeContentService contentService;
    private final HomeInfluencerService influencerService;
    private final HomePlatformService platformService;
    private final HomeCommentService commentService;
    private final HomeUserInteractionService interactionService;
    private final HomeCommentLikeService commentLikeService;
    private final UserFollowService userFollowService;
    private final SysUserService userService;
    private final com.agileboot.domain.home.content.db.HomeContentTagService contentTagService;
    private final com.agileboot.domain.home.tag.db.HomeTagService tagService;

    /**
     * 批量获取达人信息映射
     */
    private Map<Long, HomeInfluencerEntity> getInfluencerMap(List<Long> influencerIds) {
        if (influencerIds.isEmpty()) {
            return new HashMap<>();
        }

        List<HomeInfluencerEntity> influencers = influencerService.lambdaQuery()
                .in(HomeInfluencerEntity::getInfluencerId, influencerIds)
                .list();

        return influencers.stream()
                .collect(Collectors.toMap(HomeInfluencerEntity::getInfluencerId, influencer -> influencer));
    }

    /**
     * 批量获取内容的标签信息
     */
    private Map<Long, List<String>> getContentTagsMap(List<Long> contentIds) {
        if (contentIds.isEmpty()) {
            return new HashMap<>();
        }

        // 获取所有内容标签关联
        List<com.agileboot.domain.home.content.db.HomeContentTagEntity> contentTags = contentTagService.lambdaQuery()
                .in(com.agileboot.domain.home.content.db.HomeContentTagEntity::getContentId, contentIds)
                .list();

        if (contentTags.isEmpty()) {
            return new HashMap<>();
        }

        // 获取所有标签ID
        List<Long> tagIds = contentTags.stream()
                .map(com.agileboot.domain.home.content.db.HomeContentTagEntity::getTagId)
                .distinct()
                .collect(Collectors.toList());

        // 获取所有标签信息
        List<com.agileboot.domain.home.tag.db.HomeTagEntity> tags = tagService.listByIds(tagIds);
        Map<Long, String> tagMap = tags.stream()
                .collect(Collectors.toMap(com.agileboot.domain.home.tag.db.HomeTagEntity::getTagId,
                        com.agileboot.domain.home.tag.db.HomeTagEntity::getTagName));

        // 构建内容ID到标签名称列表的映射
        Map<Long, List<String>> contentTagsMap = new HashMap<>();
        for (com.agileboot.domain.home.content.db.HomeContentTagEntity contentTag : contentTags) {
            Long contentId = contentTag.getContentId();
            Long tagId = contentTag.getTagId();
            String tagName = tagMap.get(tagId);

            if (tagName != null) {
                contentTagsMap.computeIfAbsent(contentId, k -> new ArrayList<>()).add(tagName);
            }
        }

        return contentTagsMap;
    }

    /**
     * 获取轮播图列表
     */
    public List<BannerDTO> getBannerList() {
        LambdaQueryWrapper<HomeBannerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeBannerEntity::getStatus, 1)
                .orderByAsc(HomeBannerEntity::getSortOrder);
        
        List<HomeBannerEntity> entities = bannerService.list(wrapper);
        return entities.stream()
                .map(BannerDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 获取分类列表
     */
    public List<CategoryDTO> getCategoryList() {
        LambdaQueryWrapper<HomeCategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCategoryEntity::getStatus, 1)
                .orderByAsc(HomeCategoryEntity::getSortOrder);
        
        List<HomeCategoryEntity> entities = categoryService.list(wrapper);
        return entities.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 获取精选内容列表
     */
    public List<ContentDTO> getFeaturedContentList() {
        LambdaQueryWrapper<HomeContentEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeContentEntity::getStatus, 1)
                .eq(HomeContentEntity::getAuditStatus, 1) // 只返回审核通过的稿件
                .orderByDesc(HomeContentEntity::getCreateTime)
                .last("LIMIT 8");

        List<HomeContentEntity> entities = contentService.list(wrapper);

        // 获取所有内容的标签信息
        Map<Long, List<String>> contentTagsMap = getContentTagsMap(entities.stream()
                .map(HomeContentEntity::getContentId)
                .collect(Collectors.toList()));

        // 获取所有达人信息
        List<Long> influencerIds = entities.stream()
                .map(HomeContentEntity::getInfluencerId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, HomeInfluencerEntity> influencerMap = getInfluencerMap(influencerIds);

        return entities.stream()
                .map(entity -> {
                    ContentDTO dto = new ContentDTO(entity);
                    // 设置标签信息
                    dto.setTags(contentTagsMap.getOrDefault(entity.getContentId(), new ArrayList<>()));
                    // 设置达人信息
                    if (entity.getInfluencerId() != null) {
                        HomeInfluencerEntity influencer = influencerMap.get(entity.getInfluencerId());
                        if (influencer != null) {
                            ContentDTO.AuthorInfo author = new ContentDTO.AuthorInfo();
                            author.setName(influencer.getName());
                            author.setAvatar(influencer.getImageUrl());
                            author.setFollowers(influencer.getFollowers());
                            dto.setAuthor(author);
                        }
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取最新内容列表（最近一周）
     */
    public List<ContentDTO> getLatestContentList() {
        // 计算一周前的时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date oneWeekAgo = calendar.getTime();

        LambdaQueryWrapper<HomeContentEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeContentEntity::getStatus, 1)
                .eq(HomeContentEntity::getAuditStatus, 1) // 只返回审核通过的稿件
                .ge(HomeContentEntity::getCreateTime, oneWeekAgo) // 最近一周的内容
                .orderByDesc(HomeContentEntity::getCreateTime)
                .last("LIMIT 8");

        List<HomeContentEntity> entities = contentService.list(wrapper);

        // 获取所有内容的标签信息
        Map<Long, List<String>> contentTagsMap = getContentTagsMap(entities.stream()
                .map(HomeContentEntity::getContentId)
                .collect(Collectors.toList()));

        List<ContentDTO> dtoList = entities.stream()
                .map(entity -> {
                    ContentDTO dto = new ContentDTO(entity);
                    // 设置标签信息
                    dto.setTags(contentTagsMap.getOrDefault(entity.getContentId(), new ArrayList<>()));
                    return dto;
                })
                .collect(Collectors.toList());

        // 为最新内容添加更新标签
        dtoList.forEach(dto -> dto.setUpdate("最新发布"));

        return dtoList;
    }

    /**
     * 获取优质达人列表
     */
    public List<InfluencerDTO> getInfluencerList() {
        LambdaQueryWrapper<HomeInfluencerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeInfluencerEntity::getStatus, 1)
                .orderByAsc(HomeInfluencerEntity::getSortOrder);
        
        List<HomeInfluencerEntity> entities = influencerService.list(wrapper);
        return entities.stream()
                .map(InfluencerDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 获取平台列表
     */
    public List<PlatformDTO> getPlatformList() {
        LambdaQueryWrapper<HomePlatformEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomePlatformEntity::getStatus, 1)
                .orderByAsc(HomePlatformEntity::getSortOrder);
        
        List<HomePlatformEntity> entities = platformService.list(wrapper);
        return entities.stream()
                .map(PlatformDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 获取内容详情（不包含用户互动状态，用于向后兼容）
     */
    public ContentDTO getContentDetail(Long id) {
        return getContentDetail(id, null);
    }

    /**
     * 获取内容列表（支持分页和筛选）
     */
    public List<ContentDTO> getHomeContentList(String type, String category, String platform, String tag) {
        LambdaQueryWrapper<HomeContentEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeContentEntity::getStatus, 1)
                .eq(HomeContentEntity::getAuditStatus, 1); // 只返回审核通过的稿件

        // 去掉 contentType 字段的筛选，不再按内容类型筛选

        if (category != null && !category.isEmpty()) {
            Long categoryId = getCategoryId(category);
            if (categoryId != null) {
                wrapper.eq(HomeContentEntity::getCategoryId, categoryId);
            }
        }

        if (platform != null && !platform.isEmpty()) {
            wrapper.eq(HomeContentEntity::getPlatform, platform);
        }

        if (tag != null && !tag.isEmpty()) {
            // 根据标签名称查找标签ID
            Long tagId = getTagIdByName(tag);
            if (tagId != null) {
                // 查找包含该标签的所有内容ID
                List<Long> contentIds = contentTagService.lambdaQuery()
                        .eq(com.agileboot.domain.home.content.db.HomeContentTagEntity::getTagId, tagId)
                        .list()
                        .stream()
                        .map(com.agileboot.domain.home.content.db.HomeContentTagEntity::getContentId)
                        .collect(Collectors.toList());

                if (!contentIds.isEmpty()) {
                    wrapper.in(HomeContentEntity::getContentId, contentIds);
                } else {
                    // 如果没有找到任何内容，返回空结果
                    wrapper.eq(HomeContentEntity::getContentId, -1);
                }
            }
        }

        // 排序：热门榜单按点赞数排序，其他按创建时间排序
        if (type != null && type.equals("热门榜单")) {
            wrapper.orderByDesc(HomeContentEntity::getLikesCount);
        } else {
            wrapper.orderByDesc(HomeContentEntity::getCreateTime);
        }

        List<HomeContentEntity> entities = contentService.list(wrapper);

        // 获取所有内容的标签信息
        Map<Long, List<String>> contentTagsMap = getContentTagsMap(entities.stream()
                .map(HomeContentEntity::getContentId)
                .collect(Collectors.toList()));

        return entities.stream()
                .map(entity -> {
                    ContentDTO dto = new ContentDTO(entity);
                    // 设置标签信息
                    dto.setTags(contentTagsMap.getOrDefault(entity.getContentId(), new ArrayList<>()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据达人ID获取稿件列表
     */
    public List<ContentDTO> getContentListByInfluencerId(Long influencerId) {
        LambdaQueryWrapper<HomeContentEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeContentEntity::getStatus, 1)
                .eq(HomeContentEntity::getAuditStatus, 1) // 只返回审核通过的稿件
                .eq(HomeContentEntity::getInfluencerId, influencerId)
                .orderByDesc(HomeContentEntity::getCreateTime);
        
        List<HomeContentEntity> entities = contentService.list(wrapper);

        // 获取所有内容的标签信息
        Map<Long, List<String>> contentTagsMap = getContentTagsMap(entities.stream()
                .map(HomeContentEntity::getContentId)
                .collect(Collectors.toList()));

        return entities.stream()
                .map(entity -> {
                    ContentDTO dto = new ContentDTO(entity);
                    // 设置标签信息
                    dto.setTags(contentTagsMap.getOrDefault(entity.getContentId(), new ArrayList<>()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取内容类型代码
     * 1: 精选 (featured)
     * 2: 最新 (latest)
     */
    private Integer getContentTypeCode(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        switch (type.toLowerCase()) {
            case "featured":
            case "精选":
                return 1; // 精选
            case "latest":
            case "最新":
                return 2; // 最新
            default:
                return null;
        }
    }

    /**
     * 根据分类名称获取分类ID
     */
    private Long getCategoryId(String category) {
        if (category == null || category.isEmpty()) {
            return null;
        }
        // 根据分类名称查询分类表
        LambdaQueryWrapper<HomeCategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCategoryEntity::getCategoryName, category)
                .eq(HomeCategoryEntity::getStatus, 1)
                .last("LIMIT 1");

        HomeCategoryEntity categoryEntity = categoryService.getOne(wrapper);
        if (categoryEntity != null) {
            return categoryEntity.getCategoryId();
        }
        return null;
    }

    /**
     * 根据标签名称获取标签ID
     */
    private Long getTagIdByName(String tagName) {
        if (tagName == null || tagName.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<com.agileboot.domain.home.tag.db.HomeTagEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(com.agileboot.domain.home.tag.db.HomeTagEntity::getTagName, tagName)
                .eq(com.agileboot.domain.home.tag.db.HomeTagEntity::getStatus, 1)
                .last("LIMIT 1");

        com.agileboot.domain.home.tag.db.HomeTagEntity tagEntity = tagService.getOne(wrapper);
        if (tagEntity != null) {
            return tagEntity.getTagId();
        }
        return null;
    }

    /**
     * 获取单个内容的标签信息
     */
    private List<String> getContentTags(Long contentId) {
        // 获取内容的标签关联
        List<com.agileboot.domain.home.content.db.HomeContentTagEntity> contentTags = contentTagService.lambdaQuery()
                .eq(com.agileboot.domain.home.content.db.HomeContentTagEntity::getContentId, contentId)
                .list();

        if (contentTags.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取标签ID列表
        List<Long> tagIds = contentTags.stream()
                .map(com.agileboot.domain.home.content.db.HomeContentTagEntity::getTagId)
                .collect(Collectors.toList());

        // 获取标签名称
        List<com.agileboot.domain.home.tag.db.HomeTagEntity> tags = tagService.listByIds(tagIds);
        return tags.stream()
                .map(com.agileboot.domain.home.tag.db.HomeTagEntity::getTagName)
                .collect(Collectors.toList());
    }

    /**
     * 获取内容详情（包含用户互动状态）
     */
    public ContentDTO getContentDetail(Long contentId, Long userId) {
        HomeContentEntity entity = contentService.getById(contentId);
        if (entity == null) {
            return null;
        }
        ContentDTO dto = new ContentDTO(entity);

        // 获取并设置标签信息
        List<String> contentTags = getContentTags(contentId);
        dto.setTags(contentTags);
        
        // 如果用户已登录，查询用户互动状态
        if (userId != null) {
            LambdaQueryWrapper<HomeUserInteractionEntity> likeWrapper = new LambdaQueryWrapper<>();
            likeWrapper.eq(HomeUserInteractionEntity::getContentId, contentId)
                    .eq(HomeUserInteractionEntity::getUserId, userId)
                    .eq(HomeUserInteractionEntity::getInteractionType, 1) // 1=点赞
                    .eq(HomeUserInteractionEntity::getStatus, 1)
                    .eq(HomeUserInteractionEntity::getDeleted, false);
            dto.setIsLiked(interactionService.count(likeWrapper) > 0);
            
            LambdaQueryWrapper<HomeUserInteractionEntity> collectWrapper = new LambdaQueryWrapper<>();
            collectWrapper.eq(HomeUserInteractionEntity::getContentId, contentId)
                    .eq(HomeUserInteractionEntity::getUserId, userId)
                    .eq(HomeUserInteractionEntity::getInteractionType, 2) // 2=收藏
                    .eq(HomeUserInteractionEntity::getStatus, 1)
                    .eq(HomeUserInteractionEntity::getDeleted, false);
            dto.setIsCollected(interactionService.count(collectWrapper) > 0);
        }
        
        return dto;
    }

    /**
     * 获取评论列表
     */
    public List<CommentDTO> getCommentList(Long contentId, Long userId) {
        LambdaQueryWrapper<HomeCommentEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCommentEntity::getContentId, contentId)
                .eq(HomeCommentEntity::getStatus, 1)
                .eq(HomeCommentEntity::getDeleted, false)
                .isNull(HomeCommentEntity::getParentId) // 只查询顶级评论
                .orderByDesc(HomeCommentEntity::getCreateTime);
        
        List<HomeCommentEntity> comments = commentService.list(wrapper);
        List<CommentDTO> result = new ArrayList<>();
        
        for (HomeCommentEntity comment : comments) {
            SysUserEntity user = userService.getById(comment.getUserId());
            CommentDTO dto = new CommentDTO(comment, user);
            
            // 查询回复
            LambdaQueryWrapper<HomeCommentEntity> replyWrapper = new LambdaQueryWrapper<>();
            replyWrapper.eq(HomeCommentEntity::getParentId, comment.getCommentId())
                    .eq(HomeCommentEntity::getStatus, 1)
                    .eq(HomeCommentEntity::getDeleted, false)
                    .orderByAsc(HomeCommentEntity::getCreateTime);
            List<HomeCommentEntity> replies = commentService.list(replyWrapper);
            
            List<CommentDTO> replyDTOs = replies.stream()
                    .map(reply -> {
                        SysUserEntity replyUser = userService.getById(reply.getUserId());
                        return new CommentDTO(reply, replyUser);
                    })
                    .collect(Collectors.toList());
            dto.setReplies(replyDTOs);
            
            result.add(dto);
        }
        
        return result;
    }

    /**
     * 添加评论
     */
    @Transactional(rollbackFor = Exception.class)
    public CommentDTO addComment(Long contentId, Long userId, String content, Long parentId) {
        HomeCommentEntity entity = new HomeCommentEntity();
        entity.setContentId(contentId);
        entity.setUserId(userId);
        entity.setParentId(parentId);
        entity.setContent(content);
        entity.setLikesCount(0);
        entity.setStatus(1);
        commentService.save(entity);
        
        // 更新内容的评论数
        HomeContentEntity contentEntity = contentService.getById(contentId);
        if (contentEntity != null) {
            contentEntity.setCommentsCount(contentEntity.getCommentsCount() + 1);
            contentService.updateById(contentEntity);
        }
        
        SysUserEntity user = userService.getById(userId);
        return new CommentDTO(entity, user);
    }

    /**
     * 点赞/取消点赞内容
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(Long contentId, Long userId) {
        LambdaQueryWrapper<HomeUserInteractionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeUserInteractionEntity::getContentId, contentId)
                .eq(HomeUserInteractionEntity::getUserId, userId)
                .eq(HomeUserInteractionEntity::getInteractionType, 1); // 1=点赞
        
        HomeUserInteractionEntity interaction = interactionService.getOne(wrapper);
        HomeContentEntity contentEntity = contentService.getById(contentId);
        if (contentEntity == null) {
            return false;
        }
        
        boolean isLiked;
        if (interaction != null && interaction.getDeleted() == false) {
            // 取消点赞
            interaction.setStatus(0);
            interaction.setDeleted(true);
            interactionService.updateById(interaction);
            contentEntity.setLikesCount(Math.max(0, contentEntity.getLikesCount() - 1));
            isLiked = false;
        } else {
            // 点赞
            if (interaction == null) {
                interaction = new HomeUserInteractionEntity();
                interaction.setContentId(contentId);
                interaction.setUserId(userId);
                interaction.setInteractionType(1);
                interaction.setStatus(1);
                interactionService.save(interaction);
            } else {
                interaction.setStatus(1);
                interaction.setDeleted(false);
                interactionService.updateById(interaction);
            }
            contentEntity.setLikesCount(contentEntity.getLikesCount() + 1);
            isLiked = true;
        }
        
        contentService.updateById(contentEntity);
        return isLiked;
    }

    /**
     * 收藏/取消收藏内容
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleCollect(Long contentId, Long userId) {
        LambdaQueryWrapper<HomeUserInteractionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeUserInteractionEntity::getContentId, contentId)
                .eq(HomeUserInteractionEntity::getUserId, userId)
                .eq(HomeUserInteractionEntity::getInteractionType, 2); // 2=收藏
        
        HomeUserInteractionEntity interaction = interactionService.getOne(wrapper);
        boolean isCollected;
        
        if (interaction != null && interaction.getDeleted() == false) {
            // 取消收藏
            interaction.setStatus(0);
            interaction.setDeleted(true);
            interactionService.updateById(interaction);
            isCollected = false;
        } else {
            // 收藏
            if (interaction == null) {
                interaction = new HomeUserInteractionEntity();
                interaction.setContentId(contentId);
                interaction.setUserId(userId);
                interaction.setInteractionType(2);
                interaction.setStatus(1);
                interactionService.save(interaction);
            } else {
                interaction.setStatus(1);
                interaction.setDeleted(false);
                interactionService.updateById(interaction);
            }
            isCollected = true;
        }
        
        return isCollected;
    }

    /**
     * 增加浏览量
     */
    @Transactional(rollbackFor = Exception.class)
    public void incrementViews(Long contentId) {
        HomeContentEntity entity = contentService.getById(contentId);
        if (entity != null) {
            entity.setViewsCount(entity.getViewsCount() + 1);
            contentService.updateById(entity);
        }
    }

    /**
     * 点赞/取消点赞评论
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleCommentLike(Long commentId, Long userId) {
        LambdaQueryWrapper<HomeCommentLikeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeCommentLikeEntity::getCommentId, commentId)
                .eq(HomeCommentLikeEntity::getUserId, userId);

        HomeCommentLikeEntity commentLike = commentLikeService.getOne(wrapper);
        HomeCommentEntity commentEntity = commentService.getById(commentId);
        if (commentEntity == null) {
            return false;
        }

        boolean isLiked;
        if (commentLike != null && commentLike.getDeleted() == false) {
            // 取消点赞
            commentLike.setStatus(0);
            commentLike.setDeleted(true);
            commentLikeService.updateById(commentLike);
            commentEntity.setLikesCount(Math.max(0, commentEntity.getLikesCount() - 1));
            isLiked = false;
        } else {
            // 点赞
            if (commentLike == null) {
                commentLike = new HomeCommentLikeEntity();
                commentLike.setCommentId(commentId);
                commentLike.setUserId(userId);
                commentLike.setStatus(1);
                commentLikeService.save(commentLike);
            } else {
                commentLike.setStatus(1);
                commentLike.setDeleted(false);
                commentLikeService.updateById(commentLike);
            }
            commentEntity.setLikesCount(commentEntity.getLikesCount() + 1);
            isLiked = true;
        }

        commentService.updateById(commentEntity);
        return isLiked;
    }

    /**
     * 关注/取消关注用户
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleFollow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            // 不能关注自己
            return false;
        }

        LambdaQueryWrapper<UserFollowEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollowEntity::getFollowerId, followerId)
                .eq(UserFollowEntity::getFolloweeId, followeeId);

        UserFollowEntity follow = userFollowService.getOne(wrapper);
        boolean isFollowed;

        if (follow != null && follow.getDeleted() == false) {
            // 取消关注
            follow.setStatus(0);
            follow.setDeleted(true);
            userFollowService.updateById(follow);
            isFollowed = false;
        } else {
            // 关注
            if (follow == null) {
                follow = new UserFollowEntity();
                follow.setFollowerId(followerId);
                follow.setFolloweeId(followeeId);
                follow.setStatus(1);
                userFollowService.save(follow);
            } else {
                follow.setStatus(1);
                follow.setDeleted(false);
                userFollowService.updateById(follow);
            }
            isFollowed = true;
        }

        return isFollowed;
    }

    /**
     * 检查是否已关注某用户
     */
    public boolean checkIsFollowed(Long followerId, Long followeeId) {
        if (followerId == null || followeeId == null) {
            return false;
        }

        LambdaQueryWrapper<UserFollowEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollowEntity::getFollowerId, followerId)
                .eq(UserFollowEntity::getFolloweeId, followeeId)
                .eq(UserFollowEntity::getStatus, 1)
                .eq(UserFollowEntity::getDeleted, false);

        return userFollowService.count(wrapper) > 0;
    }
}


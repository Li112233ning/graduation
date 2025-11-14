package com.agileboot.domain.home.dto;

import cn.hutool.core.util.StrUtil;
import com.agileboot.domain.home.comment.db.HomeCommentEntity;
import com.agileboot.domain.system.user.db.SysUserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 评论DTO
 *
 * @author agileboot
 */
@Data
public class CommentDTO {

    public CommentDTO(HomeCommentEntity entity, SysUserEntity user) {
        if (entity != null) {
            this.id = entity.getCommentId();
            this.contentId = entity.getContentId();
            this.userId = entity.getUserId();
            this.parentId = entity.getParentId();
            this.content = entity.getContent();
            this.likesCount = entity.getLikesCount() != null ? entity.getLikesCount() : 0;
            this.createTime = entity.getCreateTime();
            
            // 设置用户信息
            if (user != null) {
                this.userName = StrUtil.isNotBlank(user.getNickname()) ? user.getNickname() : user.getUsername();
                this.userAvatar = user.getAvatar();
            }
        }
    }

    private Long id;
    private Long contentId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer likesCount;
    private Boolean isLiked; // 当前用户是否已点赞该评论
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    
    private String userName; // 用户名
    private String userAvatar; // 用户头像
    
    private List<CommentDTO> replies; // 回复列表
}


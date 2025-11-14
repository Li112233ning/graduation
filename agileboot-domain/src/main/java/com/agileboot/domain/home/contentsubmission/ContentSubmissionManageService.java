package com.agileboot.domain.home.contentsubmission;

import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.home.dto.ContentSubmissionDTO;
import com.agileboot.domain.home.contentsubmission.command.SaveSubmissionCommand;
import com.agileboot.domain.home.contentsubmission.query.ContentSubmissionQuery;
import com.agileboot.infrastructure.home.contentsubmission.ContentSubmissionEntity;
import com.agileboot.infrastructure.home.contentsubmission.ContentSubmissionMapper;
import com.agileboot.infrastructure.home.contentsubmission.ContentSubmissionService;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentSubmissionManageService {

    private final ContentSubmissionService submissionService;
    private final ContentSubmissionMapper submissionMapper;

    @Transactional(rollbackFor = Exception.class)
    public ContentSubmissionDTO saveSubmission(SaveSubmissionCommand command) {
        Long userId = AuthenticationUtils.getUserId();
        
        ContentSubmissionEntity entity;
        if (command.getId() != null) {
            entity = submissionService.getById(command.getId());
            if (entity == null) {
                throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, "稿件不存在");
            }
            if (!entity.getUserId().equals(userId)) {
                throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "无权操作");
            }
        } else {
            entity = new ContentSubmissionEntity();
            entity.setUserId(userId);
        }
        
        entity.setTitle(command.getTitle());
        entity.setContent(command.getContent());
        entity.setCoverImage(command.getCoverImage());
        entity.setPlatformType(command.getPlatformType());
        entity.setScheduledPublishTime(command.getScheduledPublishTime());
        entity.setStatus(command.getStatus() != null ? command.getStatus() : 0);
        
        if (command.getId() != null) {
            submissionService.updateById(entity);
        } else {
            submissionService.save(entity);
        }
        
        return new ContentSubmissionDTO(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void submitForReview(Long id) {
        Long userId = AuthenticationUtils.getUserId();
        
        ContentSubmissionEntity entity = submissionService.getById(id);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, "稿件不存在");
        }
        if (!entity.getUserId().equals(userId)) {
            throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "无权操作");
        }
        
        entity.setStatus(1);
        submissionService.updateById(entity);
    }

    public PageDTO<ContentSubmissionDTO> getMySubmissions(ContentSubmissionQuery query) {
        Long userId = AuthenticationUtils.getUserId();
        query.setUserId(userId);
        
        Page<ContentSubmissionEntity> page = submissionService.page(query.toPage(), query.toQueryWrapper());
        
        List<ContentSubmissionDTO> dtoList = page.getRecords().stream()
                .map(ContentSubmissionDTO::new)
                .collect(Collectors.toList());
        
        return new PageDTO<>(dtoList, page.getTotal());
    }

    public ContentSubmissionDTO getSubmissionDetail(Long id) {
        ContentSubmissionEntity entity = submissionService.getById(id);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, "稿件不存在");
        }
        return new ContentSubmissionDTO(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSubmission(Long id) {
        Long userId = AuthenticationUtils.getUserId();
        
        ContentSubmissionEntity entity = submissionService.getById(id);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, "稿件不存在");
        }
        if (!entity.getUserId().equals(userId)) {
            throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "无权操作");
        }
        
        submissionService.removeById(id);
    }

    public PageDTO<ContentSubmissionDTO> getSubmissionList(ContentSubmissionQuery query) {
        QueryWrapper<ContentSubmissionEntity> wrapper = query.toQueryWrapper();
        Page<ContentSubmissionEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        
        List<ContentSubmissionEntity> list = submissionMapper.selectListWithUsername(wrapper);
        page.setRecords(list);
        page.setTotal(submissionMapper.selectCount(wrapper));
        
        List<ContentSubmissionDTO> dtoList = page.getRecords().stream()
                .map(ContentSubmissionDTO::new)
                .collect(Collectors.toList());
        
        return new PageDTO<>(dtoList, page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditSubmission(Long id, Integer status, String rejectReason) {
        ContentSubmissionEntity submission = submissionService.getById(id);
        if (submission == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, "稿件不存在");
        }
        
        if (submission.getStatus() != 1) {
            throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "该稿件不在待审核状态");
        }
        
        submission.setStatus(status);
        submission.setAuditorId(AuthenticationUtils.getUserId());
        
        if (status == 3) {
            submission.setRejectReason(rejectReason);
        }
        
        submissionService.updateById(submission);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteSubmission(List<Long> ids) {
        submissionService.removeByIds(ids);
    }
}

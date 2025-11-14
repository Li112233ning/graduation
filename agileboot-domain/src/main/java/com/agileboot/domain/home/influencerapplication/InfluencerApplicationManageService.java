package com.agileboot.domain.home.influencerapplication;

import cn.hutool.json.JSONUtil;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.home.dto.InfluencerApplicationDTO;
import com.agileboot.domain.home.influencerapplication.command.SubmitApplicationCommand;
import com.agileboot.domain.home.influencerapplication.query.InfluencerApplicationQuery;
import com.agileboot.infrastructure.home.influencerapplication.InfluencerApplicationEntity;
import com.agileboot.infrastructure.home.influencerapplication.InfluencerApplicationMapper;
import com.agileboot.infrastructure.home.influencerapplication.InfluencerApplicationService;
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
public class InfluencerApplicationManageService {

    private final InfluencerApplicationService applicationService;
    private final InfluencerApplicationMapper applicationMapper;

    @Transactional(rollbackFor = Exception.class)
    public void submitApplication(SubmitApplicationCommand command) {
        Long userId = AuthenticationUtils.getUserId();
        
        QueryWrapper<InfluencerApplicationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        InfluencerApplicationEntity existing = applicationService.getOne(wrapper);
        
        if (existing != null && existing.getStatus() == 0) {
            throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "您已有待审核的申请");
        }
        
        if (existing != null && existing.getStatus() == 1) {
            throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "您已是达人用户");
        }
        
        InfluencerApplicationEntity entity = new InfluencerApplicationEntity();
        entity.setUserId(userId);
        entity.setPlatformAccount(command.getPlatformAccount());
        entity.setFansCount(command.getFansCount());
        entity.setContentField(command.getContentField());
        entity.setPortfolioLinks(JSONUtil.toJsonStr(command.getPortfolioLinks()));
        entity.setStatus(0);
        
        applicationService.save(entity);
    }

    public InfluencerApplicationDTO getMyApplication() {
        Long userId = AuthenticationUtils.getUserId();
        
        QueryWrapper<InfluencerApplicationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        InfluencerApplicationEntity entity = applicationService.getOne(wrapper);
        
        return entity != null ? new InfluencerApplicationDTO(entity) : null;
    }

    public PageDTO<InfluencerApplicationDTO> getApplicationList(InfluencerApplicationQuery query) {
        QueryWrapper<InfluencerApplicationEntity> wrapper = query.toQueryWrapper();
        Page<InfluencerApplicationEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        
        List<InfluencerApplicationEntity> list = applicationMapper.selectListWithUsername(wrapper);
        page.setRecords(list);
        page.setTotal(applicationMapper.selectCount(wrapper));
        
        List<InfluencerApplicationDTO> dtoList = page.getRecords().stream()
                .map(InfluencerApplicationDTO::new)
                .collect(Collectors.toList());
        
        return new PageDTO<>(dtoList, page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditApplication(Long id, Integer status, String rejectReason) {
        InfluencerApplicationEntity application = applicationService.getById(id);
        if (application == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, "申请不存在");
        }
        
        if (application.getStatus() != 0) {
            throw new ApiException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION, "该申请已审核");
        }
        
        application.setStatus(status);
        application.setAuditorId(AuthenticationUtils.getUserId());
        
        if (status == 1) {
            application.setApprovedAt(LocalDateTime.now());
        } else if (status == 2) {
            application.setRejectReason(rejectReason);
        }
        
        applicationService.updateById(application);
    }

    public boolean checkIsInfluencer(Long userId) {
        QueryWrapper<InfluencerApplicationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("status", 1);
        return applicationService.count(wrapper) > 0;
    }
}

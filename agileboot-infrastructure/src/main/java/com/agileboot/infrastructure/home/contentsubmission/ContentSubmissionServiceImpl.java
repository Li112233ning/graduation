package com.agileboot.infrastructure.home.contentsubmission;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 内容投稿服务实现
 *
 * @author agileboot
 */
@Service
public class ContentSubmissionServiceImpl 
        extends ServiceImpl<ContentSubmissionMapper, ContentSubmissionEntity> 
        implements ContentSubmissionService {

}

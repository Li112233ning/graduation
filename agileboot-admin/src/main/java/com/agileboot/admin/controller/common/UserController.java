package com.agileboot.admin.controller.common;

import com.agileboot.common.constant.Constants.UploadSubDir;
import com.agileboot.common.core.base.BaseController;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.common.utils.ServletHolderUtil;
import com.agileboot.common.utils.file.FileUploadUtils;
import com.agileboot.domain.system.user.UserApplicationService;
import com.agileboot.domain.system.user.command.UpdateProfileCommand;
import com.agileboot.domain.system.user.command.UpdateUserAvatarCommand;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import com.agileboot.infrastructure.user.web.SystemLoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 前端用户API控制器
 * 提供前端用户相关的接口
 *
 * @author agileboot
 */
@Tag(name = "用户API", description = "前端用户相关接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserApplicationService userApplicationService;

    /**
     * 更新用户资料
     */
    @Operation(summary = "更新用户资料")
    @PutMapping("/profile")
    public ResponseDTO<Void> updateProfile(@RequestBody Map<String, Object> request) {
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        
        UpdateProfileCommand command = new UpdateProfileCommand();
        command.setUserId(loginUser.getUserId());
        
        // 处理昵称字段（支持 nickname 和 nickName 两种格式）
        if (request.containsKey("nickname")) {
            command.setNickName((String) request.get("nickname"));
        } else if (request.containsKey("nickName")) {
            command.setNickName((String) request.get("nickName"));
        }
        
        // 处理邮箱字段
        if (request.containsKey("email")) {
            Object emailObj = request.get("email");
            if (emailObj != null) {
                command.setEmail(emailObj.toString());
            }
        }
        
        // 处理手机号字段
        if (request.containsKey("phoneNumber")) {
            command.setPhoneNumber((String) request.get("phoneNumber"));
        }
        
        // 处理性别字段
        if (request.containsKey("sex")) {
            Object sexObj = request.get("sex");
            if (sexObj instanceof Integer) {
                command.setSex((Integer) sexObj);
            } else if (sexObj instanceof Number) {
                command.setSex(((Number) sexObj).intValue());
            }
        }
        
        userApplicationService.updateUserProfile(command);
        return ResponseDTO.ok();
    }

    /**
     * 上传头像
     */
    @Operation(summary = "上传用户头像")
    @PostMapping("/avatar")
    public ResponseDTO<Map<String, String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ApiException(ErrorCode.Business.USER_UPLOAD_FILE_FAILED);
        }
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        String fileName = FileUploadUtils.upload(UploadSubDir.AVATAR_PATH, file);
        String avatarUrl = ServletHolderUtil.getContextUrl() + fileName;

        userApplicationService.updateUserAvatar(new UpdateUserAvatarCommand(loginUser.getUserId(), fileName));
        
        // 返回包含 url 字段的 Map，与前端期望的格式一致
        Map<String, String> result = new HashMap<>();
        result.put("url", avatarUrl);
        return ResponseDTO.ok(result);
    }
}


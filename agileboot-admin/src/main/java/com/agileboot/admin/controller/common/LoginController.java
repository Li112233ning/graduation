package com.agileboot.admin.controller.common;

import cn.hutool.core.util.StrUtil;
import com.agileboot.common.config.AgileBootConfig;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode.Business;
import com.agileboot.common.exception.error.ErrorCode.Client;
import com.agileboot.common.exception.error.ErrorCode.Internal;
import com.agileboot.domain.common.dto.CurrentLoginUserDTO;
import com.agileboot.domain.common.dto.TokenDTO;
import com.agileboot.domain.system.menu.MenuApplicationService;
import com.agileboot.domain.system.menu.dto.RouterDTO;
import com.agileboot.domain.system.user.UserApplicationService;
import com.agileboot.domain.system.user.command.AddUserCommand;
import com.agileboot.domain.system.user.command.RegisterCommand;

import com.agileboot.infrastructure.annotations.ratelimit.RateLimit;
import com.agileboot.infrastructure.annotations.ratelimit.RateLimit.CacheType;
import com.agileboot.infrastructure.annotations.ratelimit.RateLimit.LimitType;
import com.agileboot.infrastructure.user.AuthenticationUtils;
import com.agileboot.admin.customize.service.login.dto.CaptchaDTO;
import com.agileboot.admin.customize.service.login.dto.ConfigDTO;
import com.agileboot.admin.customize.service.login.command.LoginCommand;
import com.agileboot.infrastructure.user.web.SystemLoginUser;
import com.agileboot.infrastructure.annotations.ratelimit.RateLimitKey;
import com.agileboot.admin.customize.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 首页
 *
 * @author valarchie
 */
@Tag(name = "登录API", description = "登录相关接口")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final MenuApplicationService menuApplicationService;

    private final UserApplicationService userApplicationService;

    private final AgileBootConfig agileBootConfig;

    /**
     * 访问首页，提示语
     */
    @Operation(summary = "首页")
    @GetMapping("/")
    @RateLimit(key = RateLimitKey.TEST_KEY, time = 10, maxCount = 5, cacheType = CacheType.Map,
        limitType = LimitType.GLOBAL)
    public String index() {
        return StrUtil.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。",
            agileBootConfig.getName(), agileBootConfig.getVersion());
    }


    /**
     * 获取系统的内置配置
     *
     * @return 配置信息
     */
    @GetMapping("/getConfig")
    public ResponseDTO<ConfigDTO> getConfig() {
        ConfigDTO configDTO = loginService.getConfig();
        return ResponseDTO.ok(configDTO);
    }

    /**
     * 生成验证码
     */
    @Operation(summary = "验证码")
    @RateLimit(key = RateLimitKey.LOGIN_CAPTCHA_KEY, time = 10, maxCount = 10, cacheType = CacheType.REDIS,
        limitType = LimitType.IP)
    @GetMapping("/captchaImage")
    public ResponseDTO<CaptchaDTO> getCaptchaImg() {
        CaptchaDTO captchaImg = loginService.generateCaptchaImg();
        return ResponseDTO.ok(captchaImg);
    }

    /**
     * 登录方法
     *
     * @param loginCommand 登录信息
     * @return 结果
     */
    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseDTO<TokenDTO> login(@RequestBody LoginCommand loginCommand) {
        // 生成令牌
        String token = loginService.login(loginCommand);
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        CurrentLoginUserDTO currentUserDTO = userApplicationService.getLoginUserInfo(loginUser);

        return ResponseDTO.ok(new TokenDTO(token, currentUserDTO));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public ResponseDTO<CurrentLoginUserDTO> getLoginUserInfo() {
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();

        CurrentLoginUserDTO currentUserDTO = userApplicationService.getLoginUserInfo(loginUser);

        return ResponseDTO.ok(currentUserDTO);
    }

    /**
     * 获取用户信息（alias for /getLoginUserInfo）
     *
     * @return 用户信息
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/getInfo")
    public ResponseDTO<CurrentLoginUserDTO> getInfo() {
        return getLoginUserInfo();
    }

    /**
     * 获取路由信息
     * TODO 如果要在前端开启路由缓存的话 需要在ServerConfig.json 中  设置CachingAsyncRoutes=true  避免一直重复请求路由接口
     * @return 路由信息
     */
    @Operation(summary = "获取用户对应的菜单路由", description = "用于动态生成路由")
    @GetMapping("/getRouters")
    public ResponseDTO<List<RouterDTO>> getRouters() {
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        List<RouterDTO> routerTree = menuApplicationService.getRouterTree(loginUser);
        return ResponseDTO.ok(routerTree);
    }


    /**
     * 用户注册
     *
     * @param registerCommand 注册信息
     * @return 结果
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResponseDTO<Void> register(@Valid @RequestBody RegisterCommand registerCommand) {
        // 基本验证
        if (StrUtil.isBlank(registerCommand.getUsername())) {
            return ResponseDTO.fail(new ApiException(Client.COMMON_REQUEST_PARAMETERS_INVALID, "用户名不能为空"));
        }
        if (registerCommand.getUsername().length() < 4 || registerCommand.getUsername().length() > 64) {
            return ResponseDTO.fail(new ApiException(Client.COMMON_REQUEST_PARAMETERS_INVALID, "用户名长度必须在4-64个字符之间"));
        }
        if (StrUtil.isBlank(registerCommand.getPassword())) {
            return ResponseDTO.fail(new ApiException(Client.COMMON_REQUEST_PARAMETERS_INVALID, "密码不能为空"));
        }
        if (registerCommand.getPassword().length() < 6 || registerCommand.getPassword().length() > 128) {
            return ResponseDTO.fail(new ApiException(Client.COMMON_REQUEST_PARAMETERS_INVALID, "密码长度必须在6-128个字符之间"));
        }
        if (StrUtil.isBlank(registerCommand.getConfirmPassword())) {
            return ResponseDTO.fail(new ApiException(Client.COMMON_REQUEST_PARAMETERS_INVALID, "确认密码不能为空"));
        }
        // 验证密码确认
        if (!registerCommand.getPassword().equals(registerCommand.getConfirmPassword())) {
            return ResponseDTO.fail(new ApiException(Client.COMMON_REQUEST_PARAMETERS_INVALID, "两次输入的密码不一致"));
        }

        // 构建AddUserCommand，设置默认值
        AddUserCommand addUserCommand = new AddUserCommand();
        addUserCommand.setUsername(registerCommand.getUsername());
        addUserCommand.setPassword(registerCommand.getPassword());
        addUserCommand.setNickname(StrUtil.isNotBlank(registerCommand.getNickname()) 
            ? registerCommand.getNickname() 
            : registerCommand.getUsername()); // 如果没有昵称，使用用户名
        if (StrUtil.isNotBlank(registerCommand.getEmail())) {
            addUserCommand.setEmail(registerCommand.getEmail());
        }
        
        // 设置默认值：普通角色(roleId=2)，正常状态(status=1)
        addUserCommand.setRoleId(2L); // 普通角色
        addUserCommand.setStatus(1); // 正常状态
        // deptId, postId等可以为null，系统会自动处理

        try {
            userApplicationService.addUser(addUserCommand);
            return ResponseDTO.ok();
        } catch (ApiException e) {
            return ResponseDTO.fail(e);
        } catch (Exception e) {
            return ResponseDTO.fail(new ApiException(Internal.INTERNAL_ERROR, "注册失败：" + e.getMessage()));
        }
    }

}

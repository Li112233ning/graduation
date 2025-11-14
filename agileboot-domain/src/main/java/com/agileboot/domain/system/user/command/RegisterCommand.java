package com.agileboot.domain.system.user.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 用户注册命令
 *
 * @author agileboot
 */
@Data
public class RegisterCommand {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 64, message = "用户名长度必须在4-64个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 128, message = "密码长度必须在6-128个字符之间")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    @Size(max = 32, message = "昵称长度不能超过32个字符")
    private String nickname;

    @Size(max = 128, message = "邮箱长度不能超过128个字符")
    private String email;
}


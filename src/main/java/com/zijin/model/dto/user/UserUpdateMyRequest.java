package com.zijin.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户更新个人信息请求
 *
 *
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;


    /**
     * 用户角色：user/admin
     */
    private String userRole;

    /**
     * 性别 男 女
     */
    private String gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0 - 正常 1-注销 2-封号
     */
    private Integer userStatus;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户账号
     */
    private String userAccount;

    private static final long serialVersionUID = 1L;
}
package com.imooc.user.controller;

import com.imooc.user.VO.ResultVO;
import com.imooc.user.constant.CookieConstant;
import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.enums.ResultEnum;
import com.imooc.user.enums.RoleEnum;
import com.imooc.user.service.UserService;
import com.imooc.user.util.CookieUtil;
import com.imooc.user.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openId")String openId, HttpServletResponse response) {

        //1.openId和数据库里的数据是否匹配
        UserInfo userInfo = userService.findByOpenId(openId);
        if(userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //2.判断角色
        if(RoleEnum.BUYER.getCode() != userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //3. cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.OPENID, openId, CookieConstant.EXPIRE);

        return ResultVOUtil.success();
    }
}

package com.kong.mall.controller;

import com.kong.mall.common.CommonResult;
import com.kong.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登陆管理")
@RequestMapping("api/sso")
public class UmsMemberController {

  @Autowired
  private UmsMemberService memberService;

  @ApiOperation("获取验证码")
  @GetMapping(value = "/getAuthCode")
  @ResponseBody
  public CommonResult getAuthCode(@RequestParam String telephone) {
    return memberService.generateAuthCode(telephone);
  }

  @ApiOperation("判断验证码是否正确")
  @PostMapping(value = "/verifyAuthCode")
  @ResponseBody
  public CommonResult updatePassword(@RequestParam String telephone,
      @RequestParam String authCode) {
    return memberService.verifyAuthCode(telephone, authCode);
  }
}

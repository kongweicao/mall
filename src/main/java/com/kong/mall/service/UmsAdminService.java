package com.kong.mall.service;


import com.kong.mall.mapper.UmsAdminMapper;
import com.kong.mall.mapper.UmsAdminRoleRelationDao;
import com.kong.mall.model.UmsAdmin;
import com.kong.mall.model.UmsAdminExample;
import com.kong.mall.model.UmsPermission;
import com.kong.mall.tools.JwtTokenUtil;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UmsAdminService实现类 Created by macro on 2018/4/26.
 */
@Service
@Slf4j
public class UmsAdminService {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Value("${jwt.tokenHead}")
  private String tokenHead;
  @Autowired
  private UmsAdminMapper adminMapper;
  @Autowired
  private UmsAdminRoleRelationDao adminRoleRelationDao;


  public UmsAdmin getAdminByUsername(String username) {
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<UmsAdmin> adminList = adminMapper.selectByExample(example);
    if (adminList != null && adminList.size() > 0) {
      return adminList.get(0);
    }
    return null;
  }


  public UmsAdmin register(UmsAdmin umsAdminParam) {
    UmsAdmin umsAdmin = new UmsAdmin();
    BeanUtils.copyProperties(umsAdminParam, umsAdmin);
    umsAdmin.setCreateTime(new Date());
    umsAdmin.setStatus(1);
    //查询是否有相同用户名的用户
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
    List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
    if (umsAdminList.size() > 0) {
      return null;
    }
    //将密码进行加密操作
    String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
    umsAdmin.setPassword(encodePassword);
    adminMapper.insert(umsAdmin);
    return umsAdmin;
  }


  public String login(String username, String password) {
    String token = null;
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("密码不正确");
      }
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails);
    } catch (AuthenticationException e) {
      log.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }


  public List<UmsPermission> getPermissionList(Long adminId) {
    return adminRoleRelationDao.getPermissionList(adminId);
  }
}
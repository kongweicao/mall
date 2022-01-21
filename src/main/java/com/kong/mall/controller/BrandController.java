package com.kong.mall.controller;

import com.kong.mall.common.CommonResult;
import com.kong.mall.model.PmsBrand;
import com.kong.mall.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/brand")
@Api(tags = "管理")
public class BrandController {

  @Autowired
  private BrandService brandService;
  @ApiOperation("获取所有列表")
  @GetMapping(value = "listAll")
  @PreAuthorize("hasAuthority('pms:brand:read')")
  public CommonResult<List<PmsBrand>> getBrandList() {
    return CommonResult.success(brandService.listAllBrand());
  }

  @ApiOperation("获取列表分页")
  @GetMapping(value = "listByPage")
  public CommonResult<List<PmsBrand>> listByPage(
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
    return CommonResult.success(brandService.listByPage(pageNum, pageSize));
  }

  @ApiOperation("新增")
  @PostMapping(value = "/create")
  public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
    CommonResult commonResult;
    int count = brandService.createBrand(pmsBrand);
    if (count == 1) {
      commonResult = CommonResult.success(pmsBrand);
      log.debug("createBrand success:{}", pmsBrand);
    } else {
      commonResult = CommonResult.failed("操作失败");
      log.debug("createBrand failed:{}", pmsBrand);
    }
    return commonResult;
  }
}

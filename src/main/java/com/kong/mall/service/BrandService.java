package com.kong.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.kong.mall.mapper.PmsBrandMapper;
import com.kong.mall.model.PmsBrand;
import com.kong.mall.model.PmsBrandExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

  @Autowired
  private PmsBrandMapper pmsBrandMapper;

  public List<PmsBrand> listAllBrand() {
    return pmsBrandMapper.selectByExample(new PmsBrandExample());
  }

  public int createBrand(PmsBrand pmsBrand) {
    return pmsBrandMapper.insertSelective(pmsBrand);
  }

  public List<PmsBrand> listByPage(Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return pmsBrandMapper.selectByExample(new PmsBrandExample());
  }

}

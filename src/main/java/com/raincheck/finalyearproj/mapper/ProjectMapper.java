package com.raincheck.finalyearproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.raincheck.finalyearproj.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseMapper<User> {}
package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.entity.Travel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TravelMapper extends BaseMapper<Travel> {

    IPage<Travel> selectPageWithFilter(
            Page<Travel> page,
            @Param("experienceType") String experienceType,
            @Param("visualStyle") String visualStyle,
            @Param("rarityLevel") Integer rarityLevel
    );
}

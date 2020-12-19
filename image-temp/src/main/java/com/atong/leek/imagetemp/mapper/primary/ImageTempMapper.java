package com.atong.leek.imagetemp.mapper.primary;


import com.atong.leek.imagetemp.pojo.entity.ImageTemp;

public interface ImageTempMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ImageTemp record);

    int insertSelective(ImageTemp record);

    ImageTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImageTemp record);

    int updateByPrimaryKey(ImageTemp record);
}
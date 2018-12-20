package com.dao;

import com.entity.Code;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/12/20.
 */

public interface CodeMapper {
    List<Code> findAll();

    Code findOne(String par1, String par2);
}

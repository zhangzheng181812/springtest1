package com.dao;

import com.entity.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/12/20.
 */

@Repository
public interface CodeMapper {
    List<Code> findAll();

    Code findOne(String par1, String par2);

    int insertDode(String code);

    @Select("select * from code where id = #{0} and code = #{1}")
    Code testSelect(String par1, String par2);
}

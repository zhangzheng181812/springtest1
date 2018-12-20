package com.service;

import com.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeService extends JpaRepository<Code, Integer> {
    List<Code> findAll();

    Code findOne(Integer var1);

    Code findByCode(String var1);

    @Query("select code from Code where code = (?1)")
    Code findCodes1(String code);
}


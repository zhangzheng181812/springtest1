package com.service;

import com.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeService extends JpaRepository<Code, Integer> {
    List<Code> findAll();

    Code findOne(Integer var1);

    Code findByCode(String var1);
}


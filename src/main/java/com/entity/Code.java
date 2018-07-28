package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String code;
    @Transient
    private String dome;

    public Code() {
    }

    public String getDome() {
        return this.dome;
    }

    public void setDome(String dome) {
        this.dome = dome;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

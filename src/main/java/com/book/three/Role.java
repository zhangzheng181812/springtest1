package com.book.three;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by admin on 2018/12/25.
 */
@Document
@Setter
@Getter
@ToString
public class Role {
    private  String roleNamer ;

    private String note;
}

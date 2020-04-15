package com.book.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/12/25.
 */
@Document(collection = "test_user")
@Setter
@Getter
@ToString
public class User implements Serializable {
    @Id
    private Long id;

    private String name;

    private List<Role> reles = null;
}


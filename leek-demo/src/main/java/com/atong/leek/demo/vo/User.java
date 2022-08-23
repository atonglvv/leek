package com.atong.leek.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: leek
 * @description:
 * @author: atong
 * @create: 2022-07-15 16:54
 */
@Data
public class User {

    private String name;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime brithday;

    private List<InnerUser> innerUsers;
}

package org.redis.demo.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class Patient implements Serializable {


    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;
    private String address;

}

package org.redis.demo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class Patient implements Serializable {


    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;
    private String address;

}

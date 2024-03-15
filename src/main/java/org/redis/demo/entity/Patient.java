package org.redis.demo.entity;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class Patient implements Serializable {


    @Serial
    private static final long serialVersionUID = 6743010879607261063L;


    private Long id;
    private String name;
    private String address;

}

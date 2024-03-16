package org.redis.demo.model;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class PatientDto implements Serializable {


    @Serial
    private static final long serialVersionUID = 6743010879607261063L;

    private Long id;
    private String name;
    private String address;

}

package org.redis.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@RedisHash("PATIENT_SESSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {

    @Serial
    private static final long serialVersionUID = -3203661130260615646L;

    @Id
    private String id;

    @Indexed
    private String name;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long ttl;


}

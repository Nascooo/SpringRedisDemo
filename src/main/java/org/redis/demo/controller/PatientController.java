package org.redis.demo.controller;

import lombok.RequiredArgsConstructor;
import org.redis.demo.entity.Patient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private static final String PATIENT = "PATIENT";
    public static final String PATIENT_CACHE_MANAGER = "patientCacheManager";

    private final RedisTemplate<String, Patient> customRedisTemplate;


    @PostMapping
    @Cacheable(value = PATIENT, key = "#patient.id", cacheManager = PATIENT_CACHE_MANAGER)
    public Patient createNew(@RequestBody Patient patient) {

        HashOperations<String, Long, Patient> opsForHash = customRedisTemplate.opsForHash();
        if (opsForHash.hasKey(PATIENT, patient.getId())) {
            return opsForHash.get(PATIENT, patient.getId());
        }

        opsForHash.put(PATIENT, patient.getId(), patient);

        return opsForHash.get(PATIENT, patient.getId());
    }

    @PutMapping
    @CachePut(value = PATIENT, key = "#patient.id", cacheManager = PATIENT_CACHE_MANAGER)
    public Patient update(@RequestBody Patient patient) {

        HashOperations<String, Long, Patient> opsForHash = customRedisTemplate.opsForHash();
        opsForHash.put(PATIENT, patient.getId(), patient);

        return opsForHash.get(PATIENT, patient.getId());
    }

    @GetMapping("{id}")
    @Cacheable(value = PATIENT, key = "#id", cacheManager = PATIENT_CACHE_MANAGER, unless = "#result == null")
    public Patient update(@PathVariable Long id) {

        HashOperations<String, Long, Patient> opsForHash = customRedisTemplate.opsForHash();
        Optional<Patient> patient = Optional.ofNullable(opsForHash.get(PATIENT, id));

        return patient.orElse(null);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = PATIENT, key = "#id")
    public void delete(@PathVariable Long id) {
        HashOperations<String, Long, Patient> opsForHash = customRedisTemplate.opsForHash();
        opsForHash.delete(PATIENT, id);
    }


}

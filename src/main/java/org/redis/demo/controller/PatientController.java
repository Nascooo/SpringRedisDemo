package org.redis.demo.controller;

import lombok.RequiredArgsConstructor;
import org.redis.demo.model.PatientDto;
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

    private final RedisTemplate<String, PatientDto> patientRedisTemplate;


    @PostMapping
    @Cacheable(value = PATIENT, key = "#patientDto.id", cacheManager = PATIENT_CACHE_MANAGER)
    public PatientDto createNew(@RequestBody PatientDto patientDto) {

        HashOperations<String, Long, PatientDto> opsForHash = patientRedisTemplate.opsForHash();
        boolean isKeyExist = opsForHash.hasKey(PATIENT, patientDto.getId());
        if (isKeyExist) {
            return opsForHash.get(PATIENT, patientDto.getId());
        }

        opsForHash.put(PATIENT, patientDto.getId(), patientDto);

        return opsForHash.get(PATIENT, patientDto.getId());
    }

    @PutMapping
    @CachePut(value = PATIENT, key = "#patientDto.id", cacheManager = PATIENT_CACHE_MANAGER)
    public PatientDto update(@RequestBody PatientDto patientDto) {

        HashOperations<String, Long, PatientDto> opsForHash = patientRedisTemplate.opsForHash();
        opsForHash.put(PATIENT, patientDto.getId(), patientDto);

        return opsForHash.get(PATIENT, patientDto.getId());
    }

    @GetMapping("{id}")
    @Cacheable(value = PATIENT, key = "#id", cacheManager = PATIENT_CACHE_MANAGER, unless = "#result == null")
    public PatientDto update(@PathVariable Long id) {

        HashOperations<String, Long, PatientDto> opsForHash = patientRedisTemplate.opsForHash();
        Optional<PatientDto> patient = Optional.ofNullable(opsForHash.get(PATIENT, id));

        return patient.orElse(null);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = PATIENT, key = "#id")
    public void delete(@PathVariable Long id) {
        HashOperations<String, Long, PatientDto> opsForHash = patientRedisTemplate.opsForHash();
        opsForHash.delete(PATIENT, id);
    }


}

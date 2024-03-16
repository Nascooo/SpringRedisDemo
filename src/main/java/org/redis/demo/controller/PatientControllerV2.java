package org.redis.demo.controller;

import lombok.RequiredArgsConstructor;
import org.redis.demo.entity.Patient;
import org.redis.demo.repo.PatientRepo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("v2/patients")
@RequiredArgsConstructor
public class PatientControllerV2 {

    private final PatientRepo patientRepo;

    @PostMapping
    public Patient createNew(@RequestBody Patient patient) {
        patient.setId(patient.getId() + patient.getName());
        return patientRepo.save(patient);
    }

    @PutMapping
    public Patient update(@RequestBody Patient patient) {
        return patientRepo.save(patient);
    }

    @GetMapping("{id}")
    public Patient get(String id) {
        return patientRepo.findById(id).orElseThrow(() -> new RuntimeException("Entity Not Found"));
    }

    @DeleteMapping("{name}")
    public void delete(@PathVariable String name) {
        List<Patient> allByName = patientRepo.findAllByName(name);
        patientRepo.deleteAll(allByName);
    }

    @DeleteMapping("{id}/names/{names}")
    public void deleteMany(@PathVariable String id, @PathVariable Set<String> names) {
        List<String> ids = new ArrayList<>(names.size());
        for (String name : names) {
            ids.add(id + name);
        }
        patientRepo.deleteAll(patientRepo.findAllById(ids));
    }

}

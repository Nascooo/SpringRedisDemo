package org.redis.demo.repo;

import org.redis.demo.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends CrudRepository<Patient, String> {
    List<Patient> findAllByName(String name);

    void deleteAllByName(String name);
}

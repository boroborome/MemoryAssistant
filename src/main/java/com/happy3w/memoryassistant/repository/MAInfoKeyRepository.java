package com.happy3w.memoryassistant.repository;

import com.happy3w.memoryassistant.model.MAInfoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MAInfoKeyRepository extends JpaRepository<MAInfoKey, MAInfoKey> {
    void deleteAllByInfoid(long id);
}

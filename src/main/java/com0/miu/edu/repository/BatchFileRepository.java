package com0.miu.edu.repository;

import com0.miu.edu.model.BatchFileWritter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchFileRepository extends JpaRepository<BatchFileWritter, Integer> {
}

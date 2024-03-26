package com.fatech.api6backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fatech.api6backend.entity.Log;

public interface LogRepository extends JpaRepository<Log,Long>{
}
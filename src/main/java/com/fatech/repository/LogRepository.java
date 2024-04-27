package com.fatech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatech.entity.Log;
import com.fatech.entity.Redzone;

public interface LogRepository extends JpaRepository<Log, Long>{
    List<Log> findByRedzoneId(Redzone redzoneId);
}
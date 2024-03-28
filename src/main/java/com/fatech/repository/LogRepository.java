package com.fatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatech.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long>{
    
}
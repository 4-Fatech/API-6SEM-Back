package com.fatech.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatech.dto.LogSummary;
import com.fatech.entity.Log;
import com.fatech.entity.Redzone;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByRedzoneId(Redzone redzoneId);

    @Query("SELECT l FROM Log l WHERE l.redzoneId = :redzoneId AND l.data BETWEEN :startDate AND :endDate")
    List<Log> findByRedzoneIdAndDateRange(@Param("redzoneId") Redzone redzoneId,
            @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l.redzoneId, COUNT(l) FROM Log l GROUP BY l.redzoneId ORDER BY COUNT(l) DESC")
    List<Object[]> findRedzoneWithMostLogs();

    @Query("SELECT DATE(l.data), " +
            "SUM(CASE WHEN l.entrada = true THEN 1 ELSE 0 END) AS entradas, " +
            "SUM(CASE WHEN l.entrada = false THEN 1 ELSE 0 END) AS saidas " +
            "FROM Log l WHERE l.redzoneId.id = :redzoneId " +
            "AND DATE(l.data) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(l.data)")
    List<Object[]> countLogsByDateAndRedzone(@Param("redzoneId") Long redzoneId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT DATE(l.data) AS dia, COUNT(l) AS quantidade " +
            "FROM Log l " +
            "WHERE l.redzoneId = :redzoneId AND l.data BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(l.data) " +
            "ORDER BY DATE(l.data) ASC")
    List<Object[]> findLogCountByRedzoneIdAndDateRangeGroupedByDay(
            @Param("redzoneId") Redzone redzoneId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

            @Query("SELECT new com.fatech.dto.LogSummary(TO_CHAR(l.data, 'DD/MM/YYYY'), l.redzoneId.nome_redzone, COUNT(l)) " +
            "FROM Log l " +
            "WHERE l.redzoneId.id_departamento.id_departamento = :departamentoId " +
            "AND l.data BETWEEN :startDate AND :endDate " +
            "GROUP BY TO_CHAR(l.data, 'DD/MM/YYYY'), l.redzoneId.nome_redzone")
     List<LogSummary> findLogsByDepartmentAndDateRange(
         @Param("departamentoId") long departamentoId,
         @Param("startDate") LocalDateTime startDate,
         @Param("endDate") LocalDateTime endDate
     );
 }



package com.fatech.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatech.dto.LogDTO;
import com.fatech.dto.LogSummary;
import com.fatech.entity.Log;
import com.fatech.entity.Redzone;
import com.fatech.service.LogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/log")
@CrossOrigin
@Tag(name = "Log")
public class LogController {
    @Autowired
    private LogService service;

    @Operation(summary = "Realiza a busca de registros de redzones dentro do intervalo desejado", method = "GET", description = "Realiza a busca de registros de redzones dentro do intervalo desejado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros agrupados por dia e redzone"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/logs")
public Map<String, List<LogSummary>> getLogs(
    @RequestParam long departamentoId,
    @RequestParam String startDate,
    @RequestParam String endDate
) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate end = LocalDate.parse(endDate, formatter);

    return service.getLogsByDepartmentAndDateRange(departamentoId, start, end);
}


    @Operation(summary = "Realiza a busca de registros de entrada e saida agrupados por dia", method = "GET", description = "Realiza a busca de registros de entrada e saida agrupados por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros agrupados por dia"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/gra")
    public List<Map<String, Object>> countLogsByDateAndRedzone(
            @RequestParam Long redzoneId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Object[]> results = service.countLogsByDateAndRedzone(redzoneId, startDate, endDate);
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> formattedResult = new LinkedHashMap<>();
            formattedResult.put("data", result[0]);
            formattedResult.put("entradas", result[1]);
            formattedResult.put("saidas", result[2]);
            formattedResults.add(formattedResult);
        }

        return formattedResults;
    }

    @Operation(summary = "Realiza a busca de registros agrupados por dia", method = "GET", description = "Busca registros por id_redzone e intervalo de datas, agrupando por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros agrupados por dia"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/redzone/{redzoneId}/dialogcontar")
    public ResponseEntity<List<Object[]>> findLogCountByRedzoneIdAndDateRangeGroupedByDay(
            @PathVariable Redzone redzoneId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<Object[]> logs = service.findLogCountByRedzoneIdAndDateRangeGroupedByDay(redzoneId,
                startDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1).minusNanos(1));
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Realiza a busca redzones com mais registros", method = "GET", description = "Realiza a busca redzones com mais registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna redzones com mais registros"),
            @ApiResponse(responseCode = "400", description = "Não existe nenhum registro")
    })
    @GetMapping("/redzonemaislogs")
    public List<Object[]> getRedzoneWithMostLogs() {
        return service.getRedzoneWithMostLogs();
    }

    @Operation(summary = "Realiza a busca de registros", method = "GET", description = "Busca todos os registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros"),
            @ApiResponse(responseCode = "400", description = "Não existe nenhum registro")
    })
    @GetMapping
    public List<LogDTO> buscarTodosLogs() {
        return service.buscarTodosLogs();
    }

    @Operation(summary = "Realiza a criação de registros", method = "POST", description = "Cria um registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cria o registro"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o registro")
    })
    @PostMapping("/post")
    public ResponseEntity<Log> criarLog(@RequestBody Log novoLog) {
        try {
            novoLog.setData(LocalDateTime.now());
            Log logCriado = service.criarLog(novoLog);
            return new ResponseEntity<>(logCriado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Realiza a busca de registro por ID", method = "GET", description = "Busca registro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o registro"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LogDTO> buscarLogPorId(@PathVariable Long id) {
        try {
            LogDTO log = service.buscarLogPorId(id);
            return new ResponseEntity<>(log, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Realiza a exclusão do registro por ID", method = "DELETE", description = "Exclui o registro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLog(@PathVariable Long id) {
        try {
            service.deletarLog(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Realiza a exclusão de todos os registros", method = "DELETE", description = "Deleta todos os registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "400", description = "")
    })
    @DeleteMapping("/all")
    public ResponseEntity<Void> deletarTodosLogs() {
        service.deletarTodosLogs();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Realiza a busca de registro por ID da redzone", method = "GET", description = "Busca registro por id_redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o registro"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/redzone/{redzoneId}")
    public ResponseEntity<List<LogDTO>> findLogsByRedzoneId(@PathVariable Redzone redzoneId) {
        List<Log> logs = service.findLogsByRedzoneId(redzoneId);
        List<LogDTO> logDTOs = logs.stream()
                .map(log -> new LogDTO(log.getId(), log.getEntradaAsString(), log.getData(), log.getLotacao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(logDTOs);
    }

    @Operation(summary = "Realiza a busca de registros filtrando pelo ID da redzone e um período de datas", method = "GET", description = "Busca registro por id_redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/redzone/{redzoneId}/dates")
    public ResponseEntity<List<Log>> findByRedzoneIdAndDateRange(
            @PathVariable Redzone redzoneId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Log> logs = service.findByRedzoneIdAndDateRange(redzoneId, startDate, endDate);
        return ResponseEntity.ok(logs);
    }

}

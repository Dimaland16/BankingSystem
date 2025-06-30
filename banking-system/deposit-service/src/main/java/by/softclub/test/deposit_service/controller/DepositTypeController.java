package by.softclub.test.deposit_service.controller;

import by.softclub.test.deposit_service.dto.depositType.DepositTypeRequestDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeResponseDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeUpdateDto;
import by.softclub.test.deposit_service.service.DepositTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/deposits/types")
public class DepositTypeController {

    private final DepositTypeService depositTypeService;

    @Autowired
    public DepositTypeController(DepositTypeService depositTypeService) {
        this.depositTypeService = depositTypeService;
    }

    // Создание нового типа депозита
    @PostMapping
    public ResponseEntity<DepositTypeResponseDto> createDepositType(@RequestBody DepositTypeRequestDto requestDTO) {
        DepositTypeResponseDto createdType = depositTypeService.createDepositType(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdType);
    }

    // Получение типа депозита по ID
    @GetMapping("/{id}")
    public ResponseEntity<DepositTypeResponseDto> getDepositType(@PathVariable Long id) {
        DepositTypeResponseDto depositType = depositTypeService.getDepositTypeById(id);
        return ResponseEntity.ok(depositType);
    }

    // Получение списка всех типов депозитов
    @GetMapping
    public ResponseEntity<List<DepositTypeResponseDto>> getAllDepositTypes() {
        List<DepositTypeResponseDto> depositTypes = depositTypeService.getAllDepositTypes();
        return ResponseEntity.ok(depositTypes);
    }

    // Обновление типа депозита
    @PutMapping("/{id}")
    public ResponseEntity<DepositTypeResponseDto> updateDepositType(@PathVariable Long id, @RequestBody DepositTypeUpdateDto updateDTO) {
        DepositTypeResponseDto updatedType = depositTypeService.updateDepositType(id, updateDTO);
        return ResponseEntity.ok(updatedType);
    }

    // Удаление типа депозита
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepositType(@PathVariable Long id) {
        depositTypeService.deleteDepositType(id);
        return ResponseEntity.noContent().build();
    }
}

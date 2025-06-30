package by.softclub.test.deposit_service.service;

import by.softclub.test.deposit_service.dto.depositType.DepositTypeRequestDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeResponseDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeUpdateDto;
import by.softclub.test.deposit_service.entity.DepositCondition;
import by.softclub.test.deposit_service.entity.DepositType;
import by.softclub.test.deposit_service.mapper.DepositTypeMapper;
import by.softclub.test.deposit_service.repository.DepositTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositTypeService{

    private final DepositTypeRepository depositTypeRepository;
    private final DepositTypeMapper depositTypeMapper;

    @Autowired
    public DepositTypeService(DepositTypeRepository depositTypeRepository, DepositTypeMapper depositTypeMapper) {
        this.depositTypeRepository = depositTypeRepository;
        this.depositTypeMapper = depositTypeMapper;
    }

    public DepositTypeResponseDto createDepositType(DepositTypeRequestDto requestDTO) {
        // Проверка уникальности имени
        if (depositTypeRepository.existsByName(requestDTO.getName())) {
            throw new IllegalStateException("Тип депозита с таким именем уже существует");
        }

        // Преобразование DTO в сущность
        DepositType depositType = depositTypeMapper.toEntity(requestDTO);
        DepositCondition condition = depositType.getCondition();
        if (condition != null) {
            condition.setDepositType(depositType); // Устанавливаем связь
        }
        depositType = depositTypeRepository.save(depositType);
        return depositTypeMapper.toDto(depositType);
    }

    public DepositTypeResponseDto getDepositTypeById(Long id) {
        DepositType depositType = depositTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тип депозита с ID " + id + " не найден"));
        return depositTypeMapper.toDto(depositType);
    }

    public List<DepositTypeResponseDto> getAllDepositTypes() {
        return depositTypeRepository.findAll().stream()
                .map(depositTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public DepositTypeResponseDto updateDepositType(Long id, DepositTypeUpdateDto updateDTO) {
        DepositType depositType = depositTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тип депозита с ID " + id + " не найден"));

        // Обновление полей, если они заданы
/*        updateDTO.getName().ifPresent(depositType::setName);
        updateDTO.getCondition().ifPresent(conditionDTO -> {
            DepositCondition condition = depositType.getCondition();
            if (condition == null) {
                condition = new DepositCondition();
                condition.setDepositType(depositType);
                depositType.setCondition(condition);
            }
            depositTypeMapper.updateConditionFromDto(conditionDTO, condition);
        })*/;

        depositType = depositTypeRepository.save(depositType);
        return depositTypeMapper.toDto(depositType);
    }

    public void deleteDepositType(Long id) {
        if (!depositTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("Тип депозита с ID " + id + " не найден");
        }
        depositTypeRepository.deleteById(id);
    }
}

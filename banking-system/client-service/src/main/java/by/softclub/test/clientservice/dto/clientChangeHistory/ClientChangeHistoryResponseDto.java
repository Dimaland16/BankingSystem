package by.softclub.test.clientservice.dto.clientChangeHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientChangeHistoryResponseDto {

    private Long id;

    private Long clientId;

    private String fieldName;

    private String oldValue;

    private String newValue;

    private LocalDateTime changeDate;

}

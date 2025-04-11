package by.softclub.test.clientservice.dto.clientStatus;

import by.softclub.test.clientservice.entity.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientStatusUpdateDto {

    private ClientStatus status;

}

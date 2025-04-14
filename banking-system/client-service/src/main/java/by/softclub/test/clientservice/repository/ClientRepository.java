package by.softclub.test.clientservice.repository;

import by.softclub.test.clientservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    Boolean existsByContactInfoEmail(String email);
    Boolean existsByContactInfoPhoneNumber(String phoneNumber);
    Integer countClientsByAddressId(Long id);
}

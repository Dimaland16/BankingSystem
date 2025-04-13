package by.softclub.test.clientservice.repository;

import by.softclub.test.clientservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByRegionAndCityAndStreetAndHouseAndApartment(
            String region, String city, String street, String house, String apartment
    );

}

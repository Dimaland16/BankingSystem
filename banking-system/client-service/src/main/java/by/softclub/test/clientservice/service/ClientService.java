package by.softclub.test.clientservice.service;

import by.softclub.test.clientservice.dto.address.AddressRequestDto;
import by.softclub.test.clientservice.dto.address.AddressUpdateDto;
import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.dto.client.ClientUpdateDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoUpdateDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataUpdateDto;
import by.softclub.test.clientservice.entity.*;
import by.softclub.test.clientservice.mapper.AddressMapper;
import by.softclub.test.clientservice.mapper.ClientMapper;
import by.softclub.test.clientservice.mapper.ContactInfoMapper;
import by.softclub.test.clientservice.mapper.PassportDataMapper;
import by.softclub.test.clientservice.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PassportDataRepository passportDataRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final AddressRepository addressRepository;
    private final ClientChangeHistoryRepository changeHistoryRepository;
    private final ClientMapper clientMapper;
    private final PassportDataMapper passportDataMapper;
    private final ContactInfoMapper contactInfoMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, PassportDataRepository passportDataRepository, ContactInfoRepository contactInfoRepository, AddressRepository addressRepository, ClientChangeHistoryRepository changeHistoryRepository, ClientMapper clientMapper, PassportDataMapper passportDataMapper, ContactInfoMapper contactInfoMapper, AddressMapper addressMapper) {
        this.clientRepository = clientRepository;
        this.passportDataRepository = passportDataRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.addressRepository = addressRepository;
        this.changeHistoryRepository = changeHistoryRepository;
        this.clientMapper = clientMapper;
        this.passportDataMapper = passportDataMapper;
        this.contactInfoMapper = contactInfoMapper;
        this.addressMapper = addressMapper;
    }

    public ClientResponseDto createClient(ClientRequestDto requestDto) {

        if (clientRepository.existsByContactInfoEmail(requestDto.getContactInfo().getEmail())) {
            throw new RuntimeException("Client with this email already exists");
        }

        if (clientRepository.existsByContactInfoPhoneNumber(requestDto.getContactInfo().getPhoneNumber())) {
            throw new RuntimeException("Client with this phone number already exists");
        }

        if (passportDataRepository.existsBySeriesAndNumber(requestDto.getPassportData().getSeries(), requestDto.getPassportData().getNumber())) {
            throw new RuntimeException("Passport with this series and number already exists");
        }

        PassportData passportData = passportDataMapper.toEntity(requestDto.getPassportData());
        ContactInfo contactInfo = contactInfoMapper.toEntity(requestDto.getContactInfo());

        Address address = findOrCreateAddress(requestDto.getAddress());

        passportDataRepository.save(passportData);
        contactInfoRepository.save(contactInfo);

        Client client = clientMapper.toEntity(requestDto);
        client.setPassportData(passportData);
        client.setContactInfo(contactInfo);
        client.setAddress(address);
        client.setStatus(ClientStatus.ACTIVE);
        client.setRegistrationDate(LocalDateTime.now());

        clientRepository.save(client);

        return clientMapper.toDto(client);
    }

    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
        return clientMapper.toDto(client);
    }

    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
        clientRepository.delete(client);
    }

    public ClientResponseDto updateClient(Long id, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));

        if (clientUpdateDto.getFirstName() != null && !clientUpdateDto.getFirstName().equals(client.getFirstName())) {
            client.setFirstName(clientUpdateDto.getFirstName());
        }
        if (clientUpdateDto.getLastName() != null && !clientUpdateDto.getLastName().equals(client.getLastName())) {
            client.setLastName(clientUpdateDto.getLastName());
        }
        if (clientUpdateDto.getMiddleName() != null && !clientUpdateDto.getMiddleName().equals(client.getMiddleName())) {
            client.setMiddleName(clientUpdateDto.getMiddleName());
        }
        if (clientUpdateDto.getBirthDate() != null && !clientUpdateDto.getBirthDate().equals(client.getBirthDate())) {
            client.setBirthDate(clientUpdateDto.getBirthDate());
        }
        if (clientUpdateDto.getStatus() != null && !clientUpdateDto.getStatus().equals(client.getStatus())) {
            client.setStatus(clientUpdateDto.getStatus());
        }

        if (clientUpdateDto.getPassportDataUpdateDto() != null) {
            updatePassportData(client.getPassportData(), clientUpdateDto.getPassportDataUpdateDto());
        }
        if (clientUpdateDto.getContactInfoUpdateDto() != null) {
            updateContactInfo(client.getContactInfo(), clientUpdateDto.getContactInfoUpdateDto());
        }
        if (clientUpdateDto.getAddressUpdateDto() != null) {
            createOrUpdateAddress(client, clientUpdateDto.getAddressUpdateDto());
        }

        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDto(updatedClient);
    }

    public ClientStatus getClientStatus(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));
        return client.getStatus();
    }

    public void changeClientStatus(Long clientId, ClientStatus newStatus) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));

        client.setStatus(newStatus);
        clientRepository.save(client);
    }

    private void createOrUpdateAddress(Client client, AddressUpdateDto addressUpdateDto) {

        Address currentAddress = client.getAddress();

        boolean isSharedAddress = clientRepository.countClientsByAddressId(currentAddress.getId()) > 1;

        if (isSharedAddress) {
            Address newAddress = new Address();
            copyAddressFields(currentAddress, newAddress);
            updateAddress(newAddress, addressUpdateDto);
            client.setAddress(addressRepository.save(newAddress));
        } else {
            updateAddress(currentAddress, addressUpdateDto);
        }
    }

    private void copyAddressFields(Address source, Address target) {
        target.setPostalCode(source.getPostalCode());
        target.setRegion(source.getRegion());
        target.setCity(source.getCity());
        target.setStreet(source.getStreet());
        target.setHouse(source.getHouse());
        target.setApartment(source.getApartment());
    }

    private void updateAddress(Address currentAddress, AddressUpdateDto addressUpdateDto) {
        if (addressUpdateDto.getPostalCode() != null && !addressUpdateDto.getPostalCode().equals(currentAddress.getPostalCode())) {
            currentAddress.setPostalCode(addressUpdateDto.getPostalCode());
        }
        if (addressUpdateDto.getRegion() != null && !addressUpdateDto.getRegion().equals(currentAddress.getRegion())) {
            currentAddress.setRegion(addressUpdateDto.getRegion());
        }
        if (addressUpdateDto.getCity() != null && !addressUpdateDto.getCity().equals(currentAddress.getCity())) {
            currentAddress.setCity(addressUpdateDto.getCity());
        }
        if (addressUpdateDto.getStreet() != null && !addressUpdateDto.getStreet().equals(currentAddress.getStreet())) {
            currentAddress.setStreet(addressUpdateDto.getStreet());
        }
        if (addressUpdateDto.getHouse() != null && !addressUpdateDto.getHouse().equals(currentAddress.getHouse())) {
            currentAddress.setHouse(addressUpdateDto.getHouse());
        }
        if (addressUpdateDto.getApartment() != null && !addressUpdateDto.getApartment().equals(currentAddress.getApartment())) {
            currentAddress.setApartment(addressUpdateDto.getApartment());
        }
    }

    private void updateContactInfo(ContactInfo contactInfo, ContactInfoUpdateDto contactInfoUpdateDto) {
        if (contactInfoUpdateDto.getPhoneNumber() != null) {
            contactInfo.setPhoneNumber(contactInfoUpdateDto.getPhoneNumber());
        }
        if (contactInfoUpdateDto.getEmail() != null) {
            contactInfo.setEmail(contactInfoUpdateDto.getEmail());
        }
    }

    private void updatePassportData(PassportData passportData, PassportDataUpdateDto passportDataUpdateDto) {

        if (passportDataUpdateDto.getSeries() != null) {
            passportData.setSeries(passportDataUpdateDto.getSeries());
        }
        if (passportDataUpdateDto.getNumber() != null) {
            passportData.setNumber(passportDataUpdateDto.getNumber());
        }
        if (passportDataUpdateDto.getIssuer() != null) {
            passportData.setIssuer(passportDataUpdateDto.getIssuer());
        }
        if (passportDataUpdateDto.getIssueDate() != null) {
            passportData.setIssueDate(passportDataUpdateDto.getIssueDate());
        }
        if (passportDataUpdateDto.getCode() != null) {
            passportData.setCode(passportDataUpdateDto.getCode());
        }
    }

    private Address findOrCreateAddress(AddressRequestDto addressRequestDto) {

        Optional<Address> existingAddress = addressRepository.findAddressByRegionAndCityAndStreetAndHouseAndApartment(
                addressRequestDto.getRegion(),
                addressRequestDto.getCity(),
                addressRequestDto.getStreet(),
                addressRequestDto.getHouse(),
                addressRequestDto.getApartment()
        );

        if (existingAddress.isPresent()) {
            return existingAddress.get();
        }

        Address newAddress = addressMapper.toEntity(addressRequestDto);
        return addressRepository.save(newAddress);
    }
}

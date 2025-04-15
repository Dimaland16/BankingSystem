package by.softclub.test.clientservice.service;

import by.softclub.test.clientservice.dto.address.AddressRequestDto;
import by.softclub.test.clientservice.dto.address.AddressUpdateDto;
import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.dto.client.ClientUpdateDto;
import by.softclub.test.clientservice.dto.clientChangeHistory.ClientChangeHistoryResponseDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoUpdateDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataUpdateDto;
import by.softclub.test.clientservice.entity.*;
import by.softclub.test.clientservice.exception.DuplicateEmailException;
import by.softclub.test.clientservice.exception.DuplicatePassportDataException;
import by.softclub.test.clientservice.exception.DuplicatePhoneNumberException;
import by.softclub.test.clientservice.mapper.*;
import by.softclub.test.clientservice.repository.*;
import by.softclub.test.clientservice.specification.ClientSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PassportDataRepository passportDataRepository;
    private final AddressRepository addressRepository;
    private final ClientChangeHistoryRepository changeHistoryRepository;
    private final ClientMapper clientMapper;
    private final PassportDataMapper passportDataMapper;
    private final ContactInfoMapper contactInfoMapper;
    private final AddressMapper addressMapper;
    private final ClientChangeHistoryMapper clientChangeHistoryMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, PassportDataRepository passportDataRepository,
                         AddressRepository addressRepository, ClientChangeHistoryRepository changeHistoryRepository,
                         ClientMapper clientMapper, PassportDataMapper passportDataMapper, ContactInfoMapper contactInfoMapper,
                         AddressMapper addressMapper, ClientChangeHistoryMapper clientChangeHistoryMapper) {
        this.clientRepository = clientRepository;
        this.passportDataRepository = passportDataRepository;
        this.addressRepository = addressRepository;
        this.changeHistoryRepository = changeHistoryRepository;
        this.clientMapper = clientMapper;
        this.passportDataMapper = passportDataMapper;
        this.contactInfoMapper = contactInfoMapper;
        this.addressMapper = addressMapper;
        this.clientChangeHistoryMapper = clientChangeHistoryMapper;
    }

    @Transactional
    public ClientResponseDto createClient(ClientRequestDto requestDto) {

        validateUniqueFields(requestDto);
        validateAge(requestDto.getBirthDate());

        PassportData passportData = passportDataMapper.toEntity(requestDto.getPassportData());
        ContactInfo contactInfo = contactInfoMapper.toEntity(requestDto.getContactInfo());
        Address address = findOrCreateAddress(requestDto.getAddress());

        Client client = clientMapper.toEntity(requestDto);
        client.setPassportData(passportData);
        client.setContactInfo(contactInfo);
        client.setAddress(address);
        client.setStatus(ClientStatus.ACTIVE);
        client.setRegistrationDate(LocalDateTime.now());

        return clientMapper.toDto(clientRepository.save(client));
    }

    public ClientResponseDto getClientById(Long id) {
        Client client = findClientById(id);
        return clientMapper.toDto(client);
    }

    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = findClientById(id);
        clientRepository.delete(client);
    }

    @Transactional
    public ClientResponseDto updateClient(Long id, ClientUpdateDto clientUpdateDto) {
        Client client = findClientById(id);

        validateUniqueFields(client, clientUpdateDto);
        validateAge(clientUpdateDto.getBirthDate());

        updateFieldIfChanged(client, "firstName", client.getFirstName(), clientUpdateDto.getFirstName(), client::setFirstName);
        updateFieldIfChanged(client, "lastName", client.getLastName(), clientUpdateDto.getLastName(), client::setLastName);
        updateFieldIfChanged(client, "middleName", client.getMiddleName(), clientUpdateDto.getMiddleName(), client::setMiddleName);
        updateFieldIfChanged(client, "birthDate", client.getBirthDate(), clientUpdateDto.getBirthDate(), client::setBirthDate);
        updateFieldIfChanged(client, "status", client.getStatus(), clientUpdateDto.getStatus(), client::setStatus);

        if (clientUpdateDto.getPassportDataUpdateDto() != null) {
            updatePassportData(client, clientUpdateDto.getPassportDataUpdateDto());
        }
        if (clientUpdateDto.getContactInfoUpdateDto() != null) {
            updateContactInfo(client, clientUpdateDto.getContactInfoUpdateDto());
        }
        if (clientUpdateDto.getAddressUpdateDto() != null) {
            createOrUpdateAddress(client, clientUpdateDto.getAddressUpdateDto());
        }

        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDto(updatedClient);
    }

    public ClientStatus getClientStatus(Long clientId) {
        Client client = findClientById(clientId);
        return client.getStatus();
    }

    @Transactional
    public void changeClientStatus(Long clientId, ClientStatus newStatus) {
        Client client = findClientById(clientId);

        saveChangeHistory(client, "status", client.getStatus().name(), newStatus.name());
        client.setStatus(newStatus);
        clientRepository.save(client);
    }

    public List<ClientChangeHistoryResponseDto> getClientChangeHistory(Long id) {
        return changeHistoryRepository.findByClientIdOrderByChangeDateDesc(id)
                .stream().map(clientChangeHistoryMapper::toDto)
                .toList();
    }

    public List<ClientResponseDto> searchClients(String firstName, String lastName, String middleName, LocalDate birthDate,
                                      LocalDate birthDateStart, LocalDate birthDateEnd, String passportSeries,
                                      String passportNumber, String email, String phoneNumber, ClientStatus status,
                                      LocalDateTime registrationDateStart, LocalDateTime registrationDateEnd) {

        Specification<Client> spec = ClientSpecification.searchClients(
                firstName,
                lastName,
                middleName,
                birthDate,
                birthDateStart,
                birthDateEnd,
                passportSeries,
                passportNumber,
                email,
                phoneNumber,
                status,
                registrationDateStart,
                registrationDateEnd
        );

        return clientRepository.findAll(spec)
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    private Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    private void validateUniqueFields(ClientRequestDto requestDto) {

        if (clientRepository.existsByContactInfoEmail(requestDto.getContactInfo().getEmail())) {
            throw new DuplicateEmailException(requestDto.getContactInfo().getEmail());
        }

        if (clientRepository.existsByContactInfoPhoneNumber(requestDto.getContactInfo().getPhoneNumber())) {
            throw new DuplicatePhoneNumberException(requestDto.getContactInfo().getPhoneNumber());
        }

        if (passportDataRepository.existsBySeriesAndNumber(
                requestDto.getPassportData().getSeries(),
                requestDto.getPassportData().getNumber())) {
            throw new DuplicatePassportDataException(
                    requestDto.getPassportData().getSeries(),
                    requestDto.getPassportData().getNumber());        }
    }

    private void validateUniqueFields(Client client, ClientUpdateDto clientUpdateDto) {
        Long clientId = client.getId();
        ContactInfoUpdateDto newContactInfo = clientUpdateDto.getContactInfoUpdateDto();
        PassportDataUpdateDto newPassportData = clientUpdateDto.getPassportDataUpdateDto();

        if (newContactInfo != null &&
                newContactInfo.getEmail() != null &&
                !newContactInfo.getEmail().equals(client.getContactInfo().getEmail())) {

            if (clientRepository.existsByContactInfoEmailAndIdNot(
                    newContactInfo.getEmail(), clientId)) {
                throw new RuntimeException("Client with this email already exists");
            }
        }

        if (newContactInfo != null &&
                newContactInfo.getPhoneNumber() != null &&
                !newContactInfo.getPhoneNumber().equals(client.getContactInfo().getPhoneNumber())) {

            if (clientRepository.existsByContactInfoPhoneNumberAndIdNot(
                    newContactInfo.getPhoneNumber(), clientId)) {
                throw new RuntimeException("Client with this phone number already exists");
            }
        }

        if (newPassportData != null &&
                (newPassportData.getSeries() != null ||
                        newPassportData.getNumber() != null)) {

            String newSeries = newPassportData.getSeries() != null
                    ? newPassportData.getSeries()
                    : client.getPassportData().getSeries();
            String newNumber = newPassportData.getNumber() != null
                    ? newPassportData.getNumber()
                    : client.getPassportData().getNumber();

            if (passportDataRepository.existsBySeriesAndNumberAndClientIdNot(newSeries, newNumber, clientId)) {
                throw new RuntimeException("Passport with this series and number already exists");
            }
        }
    }

    private <T> void updateFieldIfChanged(
            Client client, String fieldName,
            T oldValue, T newValue,
            Consumer<T> fieldUpdater) {

        if (newValue != null && !newValue.equals(oldValue)) {
            saveChangeHistory(client, fieldName, oldValue != null ? oldValue.toString() : null, newValue.toString());
            fieldUpdater.accept(newValue);
        }
    }

    private void createOrUpdateAddress(Client client, AddressUpdateDto addressUpdateDto) {

        Address currentAddress = client.getAddress();

        boolean isSharedAddress = clientRepository.countClientsByAddressId(currentAddress.getId()) > 1;

        if (isSharedAddress) {
            Address newAddress = new Address();
            copyAddressFields(currentAddress, newAddress);
            updateAddress(newAddress, addressUpdateDto, client);
            client.setAddress(addressRepository.save(newAddress));
        } else {
            updateAddress(currentAddress, addressUpdateDto, client);
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

    private void updateAddress(Address currentAddress, AddressUpdateDto addressUpdateDto, Client client) {
        updateFieldIfChanged(client, "address_postalCode", currentAddress.getPostalCode(), addressUpdateDto.getPostalCode(), currentAddress::setPostalCode);
        updateFieldIfChanged(client, "address_region", currentAddress.getRegion(), addressUpdateDto.getRegion(), currentAddress::setRegion);
        updateFieldIfChanged(client, "address_city", currentAddress.getCity(), addressUpdateDto.getCity(), currentAddress::setCity);
        updateFieldIfChanged(client, "address_street", currentAddress.getStreet(), addressUpdateDto.getStreet(), currentAddress::setStreet);
        updateFieldIfChanged(client, "address_house", currentAddress.getHouse(), addressUpdateDto.getHouse(), currentAddress::setHouse);
        updateFieldIfChanged(client, "address_apartment", currentAddress.getApartment(), addressUpdateDto.getApartment(), currentAddress::setApartment);
    }

    private void updateContactInfo(Client client, ContactInfoUpdateDto contactInfoUpdateDto) {

        ContactInfo contactInfo = client.getContactInfo();

        updateFieldIfChanged(client, "contactInfo_phoneNumber", contactInfo.getPhoneNumber(), contactInfoUpdateDto.getPhoneNumber(), contactInfo::setPhoneNumber);
        updateFieldIfChanged(client, "contactInfo_email", contactInfo.getEmail(), contactInfoUpdateDto.getEmail(), contactInfo::setEmail);
    }

    private void updatePassportData(Client client, PassportDataUpdateDto passportDataUpdateDto) {

        PassportData passportData = client.getPassportData();

        updateFieldIfChanged(client, "passport_series", passportData.getSeries(), passportDataUpdateDto.getSeries(), passportData::setSeries);
        updateFieldIfChanged(client, "passport_number", passportData.getNumber(), passportDataUpdateDto.getNumber(), passportData::setNumber);
        updateFieldIfChanged(client, "passport_issuer", passportData.getIssuer(), passportDataUpdateDto.getIssuer(), passportData::setIssuer);
        updateFieldIfChanged(client, "passport_issueDate", passportData.getIssueDate(), passportDataUpdateDto.getIssueDate(), passportData::setIssueDate);
        updateFieldIfChanged(client, "passport_code", passportData.getCode(), passportDataUpdateDto.getCode(), passportData::setCode);
    }

    private void saveChangeHistory(Client client, String fieldName, String oldValue, String newValue) {
        ClientChangeHistory history = new ClientChangeHistory();
        history.setClient(client);
        history.setFieldName(fieldName);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setChangeDate(LocalDateTime.now());
        changeHistoryRepository.save(history);
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

    private void validateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return;
        }

        LocalDate now = LocalDate.now();
        int age = Period.between(birthDate, now).getYears();

        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Client must be between 18 and 100 years old");
        }
    }
}

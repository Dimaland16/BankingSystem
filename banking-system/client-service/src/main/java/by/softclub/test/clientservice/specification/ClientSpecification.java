package by.softclub.test.clientservice.specification;

import by.softclub.test.clientservice.entity.Client;
import by.softclub.test.clientservice.entity.ClientStatus;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {

    public static Specification<Client> searchClients(
            String firstName,
            String lastName,
            String middleName,
            LocalDate birthDate,
            LocalDate birthDateStart,
            LocalDate birthDateEnd,
            String passportSeries,
            String passportNumber,
            String email,
            String phoneNumber,
            ClientStatus status,
            LocalDateTime registrationDateStart,
            LocalDateTime registrationDateEnd) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }

            if (lastName != null) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }

            if (middleName != null) {
                predicates.add(cb.like(cb.lower(root.get("middleName")), "%" + middleName.toLowerCase() + "%"));
            }

            if (birthDate != null) {
                predicates.add(cb.equal(root.get("birthDate"), birthDate));
            }

            if (birthDateStart != null && birthDateEnd != null) {
                predicates.add(cb.between(root.get("birthDate"), birthDateStart, birthDateEnd));
            }

            if (passportSeries != null) {
                predicates.add(cb.equal(root.get("passportData").get("series"), passportSeries));
            }

            if (passportNumber != null) {
                predicates.add(cb.equal(root.get("passportData").get("number"), passportNumber));
            }

            if (email != null) {
                predicates.add(cb.equal(root.get("contactInfo").get("email"), email));
            }

            if (phoneNumber != null) {
                predicates.add(cb.equal(root.get("contactInfo").get("phoneNumber"), phoneNumber));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (registrationDateStart != null && registrationDateEnd != null) {
                predicates.add(cb.between(root.get("registrationDate"), registrationDateStart, registrationDateEnd));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
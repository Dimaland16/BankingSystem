package by.softclub.test.clientservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postalCode;
    private String region;
    private String city;
    private String street;
    private String house;
    private String apartment;

    @OneToMany(mappedBy = "address")
    private List<Client> clients;

}

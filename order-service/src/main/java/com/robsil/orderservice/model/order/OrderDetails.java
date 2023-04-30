package com.robsil.orderservice.model.order;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDetails {

    private String firstName;
    private String lastName;
    private String middleName;

    private String country;
    private String city;
    private String address;
    private String postcode;

}

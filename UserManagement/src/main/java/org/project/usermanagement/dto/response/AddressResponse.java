package org.project.usermanagement.dto.response;

import lombok.Data;

@Data
public class AddressResponse {
    private int id;
    private String addressLine;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String countryCode;
}

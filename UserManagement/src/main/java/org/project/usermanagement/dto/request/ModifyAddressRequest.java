package org.project.usermanagement.dto.request;

import lombok.Data;

@Data
public class ModifyAddressRequest {
    private String addressLine;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String countryCode;
}

package com.aes.corebackend.dto.personnelmanagement;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonalInformationDTO {
    private PersonalBasicInfoDTO basicInfo;
    private PersonalAttributesDTO attributes;
    private PersonalFamilyInfoDTO familyInfo;
    private PersonalIdentificationInfoDTO identificationInfo;
    private PersonalAddressInfoDTO address;
}

package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import lombok.Data;

@Data
public class PersonalAddressInfoDTO {
    private String presentAddress;
    private String permanentAddress;

    public PersonalAddressInfo getPersonalAddressInfoEntity(PersonalAddressInfoDTO addressInfoDTO) {
        PersonalAddressInfo personalAddressInfoEntity = new PersonalAddressInfo();
        personalAddressInfoEntity.setPermanentAddress(addressInfoDTO.getPermanentAddress());
        personalAddressInfoEntity.setPresentAddress(addressInfoDTO.getPresentAddress());
        return personalAddressInfoEntity;
    }
}

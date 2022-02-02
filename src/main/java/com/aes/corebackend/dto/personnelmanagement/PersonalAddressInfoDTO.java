package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import lombok.Data;

@Data
public class PersonalAddressInfoDTO {// change from string to object of type address
    private Long id;
    //max length
    private String presentAddress;
    //max length
    private String permanentAddress;

    public static PersonalAddressInfo getPersonalAddressInfoEntity(PersonalAddressInfoDTO addressInfoDTO) {
        PersonalAddressInfo personalAddressInfoEntity = new PersonalAddressInfo();
        personalAddressInfoEntity.setId(addressInfoDTO.getId());
        personalAddressInfoEntity.setPermanentAddress(addressInfoDTO.getPermanentAddress());
        personalAddressInfoEntity.setPresentAddress(addressInfoDTO.getPresentAddress());
        return personalAddressInfoEntity;
    }

    public static PersonalAddressInfoDTO getPersonalAddressInfoDTO(PersonalAddressInfo personalAddressInfo) {
        PersonalAddressInfoDTO addressInfoDTO = new PersonalAddressInfoDTO();
        addressInfoDTO.setId(personalAddressInfo.getId());
        addressInfoDTO.setPresentAddress(personalAddressInfo.getPresentAddress());
        addressInfoDTO.setPermanentAddress(personalAddressInfo.getPermanentAddress());
        return addressInfoDTO;
    }
}

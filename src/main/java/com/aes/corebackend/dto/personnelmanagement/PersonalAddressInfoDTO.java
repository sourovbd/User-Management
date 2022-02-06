package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
public class PersonalAddressInfoDTO {// change from string to object of type address
    private Long id;

    @Length(min = 0, max = 255, message = "Present address field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Present address field cannot have numeric or special characters")
    private String presentAddress;

    @Length(min = 0, max = 255, message = "Permanent address field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Permanent address field cannot have numeric or special characters")
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

    public static PersonalAddressInfo updateEntityFromDTO(PersonalAddressInfo addressEntity, PersonalAddressInfoDTO addressDTO) {
        addressEntity.setPermanentAddress(addressDTO.getPermanentAddress());
        addressEntity.setPresentAddress(addressDTO.getPresentAddress());
        return addressEntity;
    }
}

package com.aes.corebackend.dto;

import com.aes.corebackend.entity.PersonalInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonalInformationDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String religion;
    private String bloodGroup;
    private String birthPlace;
    private String nationality;
    private String nationalID;
    private String eTin;
    private String materialStatus;
    private String fathersName;
    private String mothersName;
    private String spouseName;
    private String presentAddress;
    private String permanentAddress;

    public PersonalInformation from(PersonalInformationDTO personalInformationDTO) {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setFirstName(personalInformationDTO.getFirstName());
        //TODO set other fields
        return personalInformation;
    }

    public PersonalInformationDTO to(PersonalInformation personalInformation) {
        PersonalInformationDTO dto = new PersonalInformationDTO();
        dto.setFirstName(personalInformation.getFirstName());
        //TODO set other fields
        return dto;
    }
}

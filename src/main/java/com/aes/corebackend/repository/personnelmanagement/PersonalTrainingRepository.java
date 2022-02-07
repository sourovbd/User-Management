package com.aes.corebackend.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface PersonalTrainingRepository extends JpaRepository<PersonalTrainingInfo, Long> {
    PersonalTrainingInfo findPersonalTrainingInfoByIdAndUserId(Long trainingId, Long userId);
    ArrayList<PersonalTrainingInfo> findPersonalTrainingInfosByUserId(Long userId);
}

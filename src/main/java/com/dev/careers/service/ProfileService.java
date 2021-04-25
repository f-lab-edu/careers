package com.dev.careers.service;

import com.dev.careers.mapper.ProfileMapper;
import com.dev.careers.model.Academic;
import com.dev.careers.model.Career;
import com.dev.careers.model.Profile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileMapper profileMapper;

    @Transactional
    public void updateProfile(Profile profile, int curatorId) {
        profile.setCuratorId(curatorId);

        int profileId = 0;
        if (profileMapper.existCuratorId(curatorId)){
            profileId = profileMapper.updateProfile(profile);
        } else{
            profileMapper.insertProfile(profile);
            profileId = profile.getProfileId();
        }

        if (profileId > 0){
            List<Career> updateCareers = new ArrayList<>();
            int finalProfileId = profileId;
            profile.getCareers().forEach(
                x -> updateCareers.add(new Career(
                    finalProfileId,
                    x.getCompany(),
                    x.getCompanyTitle()
                ))
            );
            profile.setCareers(updateCareers);
            profileMapper.updateCareer(profile.getCareers());

            List<Academic> updateAcademics = new ArrayList<>();
            profile.getAcademics().forEach(
                x -> updateAcademics.add(new Academic(
                    finalProfileId,
                    x.getName(),
                    x.getMajor()
                ))
            );
            profile.setAcademics(updateAcademics);
            profileMapper.updateAcademic(profile.getAcademics());
        }
    }

    public Profile getProfile(int curatorId) {
        return profileMapper.getUserProfile(curatorId);
    }
}

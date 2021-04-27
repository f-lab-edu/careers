package com.dev.careers.service;

import com.dev.careers.mapper.ProfileMapper;
import com.dev.careers.model.Academic;
import com.dev.careers.model.Career;
import com.dev.careers.model.Profile;
import java.util.List;
import java.util.stream.Collectors;
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
        if (profileMapper.existCuratorId(curatorId)) {
            profileId = profileMapper.updateProfile(profile);
        } else {
            profileMapper.insertProfile(profile);
            profileId = profile.getProfileId();
        }

        int finalProfileId = profileId;
        List<Career> updateCareers = profile.getCareers()
            .stream()
            .map(
                x -> new Career(
                    finalProfileId,
                    x.getCompany(),
                    x.getCompanyTitle()
                ))
            .collect(Collectors.toList());

        profile.setCareers(updateCareers);
        profileMapper.updateCareer(profile.getCareers());

        List<Academic> updateAcademics = profile.getAcademics()
            .stream()
            .map(
                x -> new Academic(
                    finalProfileId,
                    x.getName(),
                    x.getMajor()
                )
            )
            .collect(Collectors.toList());
        profile.setAcademics(updateAcademics);
        profileMapper.updateAcademic(profile.getAcademics());
    }

    public Profile getProfile(int curatorId) {
        return profileMapper.getUserProfile(curatorId);
    }
}

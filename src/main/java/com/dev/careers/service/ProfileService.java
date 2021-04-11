package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Academic;
import com.dev.careers.model.Career;
import com.dev.careers.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final CuratorMapper curatorMapper;

    @Transactional
    public void updateProfile(Profile profile, int curatorId) {
        profile.setCuratorId(curatorId);
        curatorMapper.insertProfile(profile);
        int profileId = profile.getProfileId();

        if (profile.getProfileId() > 0) {
            for (Career career : profile.getCareers()) {
                career.setProfileId(profileId);
            }
            curatorMapper.insertCareer(profile.getCareers());

            for (Academic academic : profile.getAcademics()){
                academic.setProfileId(profileId);
            }
            curatorMapper.insertAcademic(profile.getAcademics());
        }
    }

    public Profile getProfile(int curatorId) {
        return curatorMapper.getUserProfile(curatorId);
    }
}

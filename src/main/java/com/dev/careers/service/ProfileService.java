package com.dev.careers.service;

import com.dev.careers.mapper.ProfileMapper;
import com.dev.careers.model.Academic;
import com.dev.careers.model.Career;
import com.dev.careers.model.Profile;
import java.util.List;
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
            log.info("이미 등록되어있는 사용자 프로필");
        } else{
            profileMapper.insertProfile(profile);
            profileId = profile.getProfileId();
            log.info("새로 등록한 사용자 프로필");
        }

        int i = 0;
        if (profileId > 0){
            List<Integer> careerIdList = profileMapper.getCareerIdList(profileId);
            for (Integer id : careerIdList){
                profile.getCareers().get(i++).setCareerId(id);
            }
            for (Career career : profile.getCareers()) {
                career.setProfileId(profileId);
            }
            profileMapper.updateCareer(profile.getCareers());

            i=0;
            List<Integer> academicIdList = profileMapper.getAcademicIdList(profileId);
            for (Integer id : academicIdList){
                profile.getAcademics().get(i++).setAcademicId(id);
            }
            for (Academic academic : profile.getAcademics()){
                academic.setProfileId(profileId);
            }
            profileMapper.updateAcademic(profile.getAcademics());
        }
    }

    public Profile getProfile(int curatorId) {
        return profileMapper.getUserProfile(curatorId);
    }
}

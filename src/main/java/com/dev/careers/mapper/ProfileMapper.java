package com.dev.careers.mapper;

import com.dev.careers.model.Academic;
import com.dev.careers.model.Career;
import com.dev.careers.model.Profile;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    int insertProfile(Profile profile);

    Profile getUserProfile(int curatorId);

    boolean existCuratorId(int curatorId);

    int updateProfile(Profile profile);

    int updateCareer(List<Career> career);

    int updateAcademic(List<Academic> academic);

    List<Integer> getCareerIdList(int profileId);

    List<Integer> getAcademicIdList(int profileId);

    int getProfileId(int curatorId);
}

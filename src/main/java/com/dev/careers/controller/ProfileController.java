package com.dev.careers.controller;

import com.dev.careers.annotation.LoginChecker;
import com.dev.careers.model.Profile;
import com.dev.careers.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 프로필 요청처리
 *
 * @author junehee
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("curators/{curatorId}/profiles")
@Log4j2
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 정보 추가, 업데이트 요청
     *
     * @param profile 프로필 정보
     * @param curatorId 큐레이터 아이디
     */
    @PutMapping
    @LoginChecker
    public void updateProfile(@RequestBody Profile profile,
        @PathVariable("curatorId") int curatorId) {

        profileService.updateProfile(profile, curatorId);
    }

    /**
     * 해당 프로필 정보
     *
     * @param curatorId 큐레이터 아이디
     * @return 프로필 정보 (Profile, Career, Academic)
     */
    @GetMapping
    @LoginChecker
    public Profile getProfile(@PathVariable("curatorId") int curatorId) {
        return profileService.getProfile(curatorId);
    }
}

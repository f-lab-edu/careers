package com.dev.careers.controller;

import com.dev.careers.model.Profile;
import com.dev.careers.service.ProfileService;
import com.dev.careers.service.session.SessionAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("profiles")
@Log4j2
public class ProfileController {

    private final SessionAuthenticator sessionAuthenticator;
    private final ProfileService profileService;

    /**
     * 프로필 정보 추가, 업데이트 요청
     *
     * @param profile 프로파일 정보
     */
    @PutMapping()
    public void updateProfile(@RequestBody Profile profile) {
        int curatorId = sessionAuthenticator.successLoginUserId();
        profileService.updateProfile(profile, curatorId);
    }

    /**
     *
     * @return 프로파일 정보 (Profile, Career, Academic)
     */
    @GetMapping
    public Profile getProfile() {
        return profileService.getProfile(sessionAuthenticator.successLoginUserId());
    }
}

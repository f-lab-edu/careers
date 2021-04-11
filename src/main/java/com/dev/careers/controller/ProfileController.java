package com.dev.careers.controller;

import com.dev.careers.model.Profile;
import com.dev.careers.service.ProfileService;
import com.dev.careers.service.session.SessionAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("profile")
public class ProfileController {

    private final SessionAuthenticator sessionAuthenticator;
    private final ProfileService profileService;

    @PostMapping("update")
    public void updateProfile(@RequestBody Profile profile){
        int curatorId = sessionAuthenticator.successLoginUserId();
        profileService.updateProfile(profile, curatorId);
    }

    @GetMapping("get")
    public Profile getProfile(){
        return profileService.getProfile(sessionAuthenticator.successLoginUserId());
    }
}

package com.dev.careers.controller;

import com.dev.careers.annotation.LoginChecker;
import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.CuratorService;
import com.dev.careers.service.error.ViolationException;
import com.dev.careers.service.session.SessionAuthenticator;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 큐레이터 회원가입, 로그인, 로그아웃 처리를 한다.
 *
 * @author junehee
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("curators")
public class CuratorController {

    private final CuratorService curatorService;
    private final SessionAuthenticator sessionAuthenticator;

    /**
     * 회원가입
     *
     * @param curator 회원가입 할 큐레이터 정보
     */
    @PostMapping("join")
    public void joinCurator(@Valid @ModelAttribute Curator curator) {

        curatorService.join(curator);
    }

    /**
     * 로그인
     *
     * @param loginParamter 로그인 정보
     */
    @PostMapping("login")
    public void loginCurator(@Valid @ModelAttribute LoginParamter loginParamter) {

        int id = curatorService.getUserIdByEmailAndPassword(loginParamter);
        sessionAuthenticator.login(id);
    }

    /**
     * 로그아웃
     */
    @DeleteMapping("logout")
    @LoginChecker
    public void logout() {
        sessionAuthenticator.logout();
    }
}

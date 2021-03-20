package com.dev.careers.controller;

import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.CuratorService;
import com.dev.careers.service.error.ViolationException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@RequiredArgsConstructor
@RestController
@RequestMapping("curators")
public class CuratorController {

    private final CuratorService curatorService;

    @PostMapping("join")
    public void joinMember(@Valid @ModelAttribute Curator curator, BindingResult bindingResult)
        throws Exception {
        verifyCuratorParameter(bindingResult);

        curatorService.join(curator);
    }

    @PostMapping("login")
    public void loginMember(@Valid @ModelAttribute LoginParamter loginParamter,
        HttpSession httpSession,
        BindingResult bindingResult)
        throws NoSuchAlgorithmException {

        verifyCuratorParameter(bindingResult);
        curatorService.login(loginParamter.getEmail(), loginParamter.getPassword());
        httpSession.setAttribute("sessionInfo", loginParamter);
    }

    @DeleteMapping("logout")
    public void logout(HttpSession httpSession) {
        httpSession.removeAttribute("sessionInfo");
    }

    public void verifyCuratorParameter(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Optional<ObjectError> objectError = bindingResult.getAllErrors().stream().findFirst();
            if (objectError.isPresent()) {
                throw new ViolationException();
            }
        }
    }
}

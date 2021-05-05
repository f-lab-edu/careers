package com.dev.careers.controller;

import com.dev.careers.domain.Curator;
import com.dev.careers.domain.HttpStatusConstants;
import com.dev.careers.domain.SessionContainer;
import com.dev.careers.service.CuratorService;
import java.net.URISyntaxException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CuratorController {

    private CuratorService curatorService;
    private SessionContainer sessionContainer;


    @PostMapping("/curators")
    public ResponseEntity<Void> create(@Valid @RequestBody Curator curator,
        BindingResult bindingResult) throws URISyntaxException {
        vaildCuratorParameter(bindingResult);

        curatorService.addCurator(curator);

        return HttpStatusConstants.CREATE;
    }

    @PostMapping("/curators/confirm-Email/{email}")
    public Boolean confirmEmail(@PathVariable("email") String email) {
        return curatorService.isDuplicateEmail(email);
    }

    @PostMapping("/curators/curator-login")
    public ResponseEntity<Void> curatorLogin(@Valid @RequestBody Curator curator,
        HttpSession httpSession,
        BindingResult bindingResult) {
        vaildCuratorParameter(bindingResult);
        if(!curatorService.loginProcess(curator)){
            return HttpStatusConstants.BAD_REQUEST;
        }
        sessionContainer.setHttpSession(curator);
        
        return HttpStatusConstants.OK;
    }

    @PostMapping("/curators/curator-logout")
    public void curatorLogout(HttpSession httpSession){
        sessionContainer.sessionComplete(sessionContainer.SESSIONNAME);
    }


    public void vaildCuratorParameter(BindingResult bindingResult){
       if(bindingResult.hasErrors()){
            throw new IllegalArgumentException();
        }
    }

}

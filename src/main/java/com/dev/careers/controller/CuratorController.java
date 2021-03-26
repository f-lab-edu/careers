package com.dev.careers.controller;

import com.dev.careers.domain.Curator;
import com.dev.careers.service.CuratorService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CuratorController {

    @Autowired
    private CuratorService curatorService;

    @PostMapping("/curators")
    public HttpStatus create(@Valid @RequestBody Curator curator,
        BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors()) {
            return HttpStatus.BAD_REQUEST;
        }

        curatorService.addCurator(curator);

        return HttpStatus.CREATED;
    }

    @PostMapping("/curators/confirmEmail/{email}")
    public int confirmEmail(@PathVariable("email") String email) {
        return curatorService.isDuplicateEmail(email);
    }
}

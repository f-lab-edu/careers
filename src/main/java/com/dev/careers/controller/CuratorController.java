package com.dev.careers.controller;

import com.dev.careers.domain.Curator;
import com.dev.careers.service.CuratorService;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CuratorController {

    @Autowired
    private CuratorService curatorService;

    @PostMapping("/curators")
    public ResponseEntity<Void> create(@Valid @RequestBody Curator curator,
        BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        curatorService.addCurator(curator);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/curators/confirmEmail/{email}")
    public Boolean confirmEmail(@PathVariable("email") String email) {
        return curatorService.isDuplicateEmail(email);
    }
}

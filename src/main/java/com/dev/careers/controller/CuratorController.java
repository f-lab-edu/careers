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
    final static ResponseEntity<Void> BAD_REQUEST = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    final static ResponseEntity<Void> CREATE = new ResponseEntity<Void>(HttpStatus.CREATED);

    @Autowired
    private CuratorService curatorService;


    @PostMapping("/curators")
    public ResponseEntity<Void> create(@Valid @RequestBody Curator curator,
        BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors()) {
            return BAD_REQUEST;
        }

        curatorService.addCurator(curator);

        return CREATE;
    }

    @PostMapping("/curators/confirm-Email/{email}")
    public Boolean confirmEmail(@PathVariable("email") String email) {
        return curatorService.isDuplicateEmail(email);
    }
}

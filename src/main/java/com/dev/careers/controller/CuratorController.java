package com.dev.careers.controller;

import com.dev.careers.model.Curator;
import com.dev.careers.service.CuratorService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CuratorController {

    private final CuratorService curatorService;

    public CuratorController(CuratorService curatorService) {
        this.curatorService = curatorService;
    }

    @PutMapping("/curators/join")
    public String putMember(@Valid @ModelAttribute Curator curator, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            Optional<ObjectError> objectError = bindingResult.getAllErrors().stream().findFirst();
            if (objectError.isPresent()){
                return objectError.get().getDefaultMessage();
            }
        }
        return curatorService.join(curator);
    }

    @GetMapping("/curators/all")
    public List<Curator> getMembers(){
        return curatorService.getMembers();
    }
}

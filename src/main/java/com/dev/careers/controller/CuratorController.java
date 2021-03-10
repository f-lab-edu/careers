package com.dev.careers.controller;

import com.dev.careers.model.Curator;
import com.dev.careers.service.CuratorService;
import com.dev.careers.service.error.ViolationException;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class CuratorController {

    private final CuratorService curatorService;

    @PostMapping("/curators/join")
    public String putMember(@Valid @ModelAttribute Curator curator, BindingResult bindingResult)
        throws Exception {
        if (bindingResult.hasErrors()) {
            Optional<ObjectError> objectError = bindingResult.getAllErrors().stream().findFirst();
            if (objectError.isPresent()) {
                throw new ViolationException(objectError.get().getDefaultMessage());
            }
        }
        return curatorService.join(curator);
    }
}

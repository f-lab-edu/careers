package com.dev.careers.service;

import com.dev.careers.domain.Curator;
import com.dev.careers.domain.SessionAuthenticate;
import com.dev.careers.repository.CuratorRepository;
import com.dev.careers.util.encryption.Sha256Encrypt;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CuratorService {

    private CuratorRepository curatorRepository;
    private Sha256Encrypt sha256Encrypt;
    private SessionAuthenticate sessionAuthenticate;

    @Transactional
    public void addCurator(Curator curator) {
        curatorRepository.save(sha256Encrypt.passwordEncoder(curator));
    }

    @Transactional
    public boolean isDuplicateEmail(String email) {
       return curatorRepository.isExistEmail(email);
    }


    public void loginProcess(Curator curator) {
        Optional<Curator> curatortInfo = Optional
            .ofNullable(curatorRepository.getCuratorInfo(curator));

        String salt = curatortInfo.map(v -> v.getSalt()).
            orElseThrow(()-> new NoSuchElementException());

        String password = sha256Encrypt.encrypt(curator.getPassword(), salt);

        curatortInfo.filter(v-> curator.getEmail().equals(v.getEmail())).orElseThrow(()
            -> new ValidationException("이메일이 일치하지 않습니다."));

        curatortInfo.filter(v -> v.getPassword().equals(password)).orElseThrow(()
            -> new ValidationException("비밀번호가 일치하지 않습니다."));

        sessionAuthenticate.setHttpSession(curator);
    }

    public void logoutProcess(){
        sessionAuthenticate.httpSessionRemove(sessionAuthenticate.SESSIONNAME);
    }

}

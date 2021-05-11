package com.dev.careers.service;

import com.dev.careers.domain.Curator;
import com.dev.careers.domain.SessionContainer;
import com.dev.careers.repository.CuratorRepository;
import com.dev.careers.util.encryption.Sha256Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CuratorService {

    private CuratorRepository curatorRepository;
    private Sha256Encrypt sha256Encrypt;
    private SessionContainer sessionContainer;

    @Transactional
    public void addCurator(Curator curator) {
        curatorRepository.save(sha256Encrypt.passwordEncoder(curator));
    }

    @Transactional
    public Boolean isDuplicateEmail(String email) {
       return curatorRepository.isExistEmail(email);
    }


    public boolean loginProcess(Curator curator) {
        boolean result = curatorRepository.existByEmailPassword(sha256Encrypt.passwordEncoder(curator));
        sessionContainer.setHttpSession(curator);
        return result;
    }

    public void logoutProcess(){
        sessionContainer.httpSessionRemove(sessionContainer.SESSIONNAME);
    }

}

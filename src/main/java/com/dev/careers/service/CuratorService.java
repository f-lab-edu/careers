package com.dev.careers.service;

import com.dev.careers.domain.Curator;
import com.dev.careers.repository.CuratorRepository;
import com.dev.careers.util.encryption.Sha256Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuratorService {

    @Autowired
    private CuratorRepository curatorRepository;
    private Sha256Encrypt sha256Encrypt;


    @Transactional
    public void addCurator(Curator curator) {
        curatorRepository.save(sha256Encrypt.passwordEncoder(curator));
    }

    @Transactional
    public Boolean isDuplicateEmail(String email) {
       return curatorRepository.isExistEmail(email);
    }


    public Boolean loginProcess(Curator curator) {
        return curatorRepository.login(sha256Encrypt.passwordEncoder(curator));
    }

}

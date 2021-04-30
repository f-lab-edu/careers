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
        String encryptPassword = sha256Encrypt.encrypt(curator.getPassword(),
            sha256Encrypt.generateSalt());
        curatorRepository.save(new Curator(curator, encryptPassword));
    }

    @Transactional
    public Boolean isDuplicateEmail(String email) {
       return curatorRepository.isExistEmail(email);
    }
}

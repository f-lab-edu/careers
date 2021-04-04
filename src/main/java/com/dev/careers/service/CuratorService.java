package com.dev.careers.service;

import com.dev.careers.domain.Curator;
import com.dev.careers.repository.CuratorRepository;
import com.dev.careers.util.encryption.Sha256Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CuratorService {

    @Autowired
    private CuratorRepository curatorRepository;
    private Sha256Encrypt sha256Encrypt;

    public CuratorService(CuratorRepository curatorRepository, Sha256Encrypt sha256Encrypt) {
        this.curatorRepository = curatorRepository;
        this.sha256Encrypt = sha256Encrypt;
    }

    public void addCurator(Curator curator) {
        curator.setPassword(sha256Encrypt.encrypt(curator.getPassword(),
            sha256Encrypt.generateSalt()));
        curatorRepository.save(curator);
    }

    public Boolean isDuplicateEmail(String email) {
       return curatorRepository.isEmail(email);
    }
}

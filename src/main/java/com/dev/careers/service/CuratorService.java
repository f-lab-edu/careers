package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CuratorService {
    private final CuratorMapper curatorMapper;
    private final PasswordEncryptor passwordEncryptor;

    public void join(Curator curator) throws NoSuchAlgorithmException {
        //중복검증
        if (curatorMapper.checkEmailExists(curator.getEmail()))
            throw new DuplicatedEmailException();

        String salt = passwordEncryptor.makeSalt();
        curatorMapper.insertCurator(
            curator.getEmail(),
            curator.getName(),
            passwordEncryptor.hashing(curator.getPassword().getBytes(), salt),
            salt);
    }
}

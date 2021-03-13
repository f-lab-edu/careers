package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CuratorService {
    private final CuratorMapper curatorMapper;
    private final PasswordEncryptor passwordEncryptor;

    public void join(Curator curator) throws NoSuchAlgorithmException {
        //중복검증
        Optional<String> email = getCuratorsEmail()
                .stream()
                .filter(m -> m.equals(curator.getEmail()))
                .findAny();

        if (email.isPresent()) {
            throw new DuplicatedEmailException();
        } else {
            String salt = passwordEncryptor.makeSalt();

            curatorMapper.insertCurator(
                    curator.getEmail(),
                    curator.getName(),
                    passwordEncryptor.hashing(curator.getPassword().getBytes(), salt),
                    salt);
        }
    }

    public List<String> getCuratorsEmail() {
        return curatorMapper.getCuratorsEmail();
    }
}

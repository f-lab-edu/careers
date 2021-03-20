package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.ViolationException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CuratorService {

    private final CuratorMapper curatorMapper;
    private final PasswordEncryptor passwordEncryptor;

    public void join(Curator curator) throws NoSuchAlgorithmException {
        //중복검증
        if (curatorMapper.checkEmailExists(curator.getEmail())) {
            throw new DuplicatedEmailException();
        }

        String salt = passwordEncryptor.makeSalt();
        curatorMapper.insertCurator(
            curator.getEmail(),
            curator.getName(),
            passwordEncryptor.hashing(curator.getPassword().getBytes(), salt),
            salt);
    }

    public void login(String email, String password) throws NoSuchAlgorithmException {
        Optional<HashMap<String, String>> memberInfo = Optional
            .ofNullable(curatorMapper.getMemberInfo(email));

        String salt = memberInfo.map(v -> v.get("salt"))
            .orElse("test");

        String hashing = passwordEncryptor.hashing(password.getBytes(), salt);
        memberInfo.filter(v -> hashing.equals(v.get("password")))
            .orElseThrow(ViolationException::new);
    }
}

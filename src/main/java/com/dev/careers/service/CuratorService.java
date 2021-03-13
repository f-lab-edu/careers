package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.ViolationException;
import java.util.HashMap;
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

    public String join(Curator curator) throws NoSuchAlgorithmException {
        //중복검증
        Optional<String> email = getCuratorsEmail()
            .stream()
            .filter(m -> m.equals(curator.getEmail()))
            .findAny();

        if (email.isPresent()) {
            throw new DuplicatedEmailException("Duplicated email");
        } else {
            String salt = passwordEncryptor.makeSalt();

            curatorMapper.insertCurator(
                curator.getEmail(),
                curator.getName(),
                passwordEncryptor.hashing(curator.getPassword().getBytes(), salt),
                salt);

            return "Success";
        }
    }

    public void login(String email, String password) throws NoSuchAlgorithmException {
        Optional<HashMap<String, String>> memberInfo = Optional
            .ofNullable(curatorMapper.getMemberInfo(email));

        if (memberInfo.isPresent()) {
            String hashing = passwordEncryptor
                .hashing(password.getBytes(), memberInfo.get().get("salt"));

            if (!hashing.equals(memberInfo.get().get("password"))) {
                throw new ViolationException("login fail");
            }
        } else {
            throw new ViolationException("You are not registered as a member");
        }
    }

    public List<String> getCuratorsEmail() {
        return curatorMapper.getCuratorsEmail();
    }
}

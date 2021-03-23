package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.ViolationException;
import com.dev.careers.service.session.SessionAuthenticator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CuratorService {

    private final CuratorMapper curatorMapper;
    private final PasswordEncryptor passwordEncryptor;
    private final SessionAuthenticator sessionAuthenticator;

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

    public void login(LoginParamter loginParamter, HttpSession httpSession) throws NoSuchAlgorithmException {
        Optional<HashMap<String, String>> memberInfo = Optional
            .ofNullable(curatorMapper.getMemberInfo(loginParamter.getEmail()));

        String salt = memberInfo.map(v -> v.get("salt"))
            .orElse("test");

        String hashing = passwordEncryptor.hashing(loginParamter.getPassword().getBytes(), salt);
        memberInfo.filter(v -> hashing.equals(v.get("password")))
            .orElseThrow(ViolationException::new);

        sessionAuthenticator.setSession(Optional.ofNullable(httpSession), loginParamter);
    }

    public void logout(HttpSession httpSession){
        sessionAuthenticator.deleteSession(Optional.ofNullable(httpSession));
    }
}

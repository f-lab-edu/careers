package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.SqlInsertException;
import com.dev.careers.service.error.ViolationException;
import com.dev.careers.service.session.SessionAuthenticator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.RuntimeSqlException;
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

        curator.setSalt(passwordEncryptor.makeSalt());
        curator.setPassword(
            passwordEncryptor.hashing(curator.getPassword().getBytes(), curator.getSalt()));

        int errorCode = curatorMapper.insertCurator(curator);
        if (errorCode != 1){
            throw new SqlInsertException();
        }
    }

    public void login(LoginParamter loginParamter) throws NoSuchAlgorithmException {
        Optional<HashMap<String, String>> memberInfo = Optional
            .ofNullable(curatorMapper.getMemberInfo(loginParamter.getEmail()));

        String salt = memberInfo.map(v -> v.get("salt"))
            .orElse("test");

        String hashing = passwordEncryptor.hashing(loginParamter.getPassword().getBytes(), salt);
        memberInfo.filter(v -> hashing.equals(v.get("password")))
            .orElseThrow(ViolationException::new);
    }
}

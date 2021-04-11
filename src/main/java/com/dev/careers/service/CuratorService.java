package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.SqlInsertException;
import com.dev.careers.service.error.ViolationException;
import java.security.NoSuchAlgorithmException;
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

        curator.setSalt(passwordEncryptor.makeSalt());
        curator.setPassword(
            passwordEncryptor.hashing(curator.getPassword().getBytes(), curator.getSalt()));

        int errorCode = curatorMapper.insertCurator(curator);
        if (errorCode != 1) {
            throw new SqlInsertException();
        }
    }

    public Integer login(LoginParamter loginParamter) throws NoSuchAlgorithmException {
        Optional<Curator> memberInfo = Optional
            .ofNullable(curatorMapper.getMemberInfo(loginParamter.getEmail()));

        String salt = memberInfo.map(v -> v.getSalt())
            .orElse("test");

        String hashing = passwordEncryptor.hashing(loginParamter.getPassword().getBytes(), salt);
        memberInfo.filter(v -> hashing.equals(v.getPassword()))
            .orElseThrow(ViolationException::new);

        Integer id = memberInfo
            .map(v -> v.getId())
            .orElseThrow(ViolationException::new);

        return id;
    }
}

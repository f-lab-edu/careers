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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CuratorService {

    private final CuratorMapper curatorMapper;
    private final PasswordEncryptor passwordEncryptor;
    private final SessionAuthenticator sessionAuthenticator;

    public void join(Curator curator) {
        //중복검증
        if (curatorMapper.checkEmailExists(curator.getEmail())) {
            throw new DuplicatedEmailException("이미 가입된 이메일 입니다.");
        }

        curator.setSalt(passwordEncryptor.makeSalt());
        curator.setPassword(
            passwordEncryptor.hashing(curator.getPassword().getBytes(), curator.getSalt()));

        int errorCode = curatorMapper.insertCurator(curator);
        if (errorCode != 1) {
            throw new SqlInsertException("회원가입 정보를 저장하지 못했습니다.");
        }
    }

    public int getUserIdByEmailAndPassword(LoginParamter loginParamter) throws NoSuchAlgorithmException {
        Optional<Curator> memberInfo = Optional
            .ofNullable(curatorMapper.getMemberInfo(loginParamter.getEmail()));

        String salt = memberInfo.map(v -> v.getSalt())
            .orElse("test");

        String hashing = passwordEncryptor.hashing(loginParamter.getPassword().getBytes(), salt);
        memberInfo.filter(v -> hashing.equals(v.getPassword()))
            .orElseThrow(ViolationException::new);

        int id = memberInfo
            .map(v -> v.getId())
            .orElseThrow(ViolationException::new);

        return id;
    }
}

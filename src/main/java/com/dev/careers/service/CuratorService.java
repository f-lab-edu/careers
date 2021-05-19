package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.SqlInsertException;
import com.dev.careers.service.error.ViolationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 큐레이터 회원관리 서비스
 *
 * @author junehee
 */
@RequiredArgsConstructor
@Service
public class CuratorService {

    private final CuratorMapper curatorMapper;
    private final PasswordEncryptor passwordEncryptor;

    /**
     * 큐레이터 회원가입 시 중복체크하여 문제없으면 Mybatis를 이용하여 insert 한다.
     *
     * @param curator 회원가입 요청한 큐레이터 정보
     */
    public void join(Curator curator) {
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

    /**
     * 회원가입되어있는 큐레이터 정보(이메일, 패스워드)를 받아 큐레이터ID를 반환한다.
     * 만약 비밀번호가 일치하지않거나 큐레이터ID를 읽어올 수 없는 경우에는 예외가 발생한다.
     *
     * @param loginParamter 로그인 정보
     * @return 큐레이터 ID
     */
    public int getUserIdByEmailAndPassword(LoginParamter loginParamter) {
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

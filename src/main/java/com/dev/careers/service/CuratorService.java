package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.service.encryption.PasswordEncryption;
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
    private final PasswordEncryption passwordEncryption;

    public String join(Curator curator) throws NoSuchAlgorithmException {
        //중복검증
        Optional<Curator> memberOptional = getCurators()
                .stream()
                .filter(m -> m.getEmail().equals(curator.getEmail()))
                .findAny();

        if (memberOptional.isPresent()) {
            throw new DuplicatedEmailException("Duplicated email");
        } else {
            String salt = passwordEncryption.makeSalt();

            curatorMapper.insertCurator(
                    curator.getEmail(),
                    curator.getName(),
                    passwordEncryption.hashing(curator.getPassword().getBytes(), salt),
                    salt);

            return "Success";
        }
    }

    public List<Curator> getCurators() {
        return curatorMapper.getCurators();
    }
}

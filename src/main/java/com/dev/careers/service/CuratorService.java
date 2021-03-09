package com.dev.careers.service;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class CuratorService {
    private final CuratorMapper curatorMapper;
    private final int SALT_SIZE = 16;

    public CuratorService(CuratorMapper curatorMapper) {
        this.curatorMapper = curatorMapper;
    }

    public String join(Curator curator) throws NoSuchAlgorithmException {
        //중복검증
        Optional<Curator> memberOptional = getCurators()
                .stream()
                .filter(m -> m.getEmail().equals(curator.getEmail()))
                .findAny();

        if (memberOptional.isPresent()) {
            return "Duplicated Email";
        } else {
            String salt = makeSalt();

            curatorMapper.insertCurator(
                    curator.getEmail(),
                    curator.getName(),
                    hashing(curator.getPassword().getBytes(), salt),
                    salt);

            return "Success";
        }
    }

    private String makeSalt() {
        SecureRandom srd = new SecureRandom();
        byte[] data = new byte[SALT_SIZE];
        srd.nextBytes(data);

        return byteArrayToString(data);
    }

    //Salt와 키 스트레칭 방식으로 구현
    private String hashing(byte[] password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            buffer.append(byteArrayToString(password));
            buffer.append(salt);
            md.update(buffer.toString().getBytes());
            password = md.digest();
        }
        return new String(password);
    }

    public String byteArrayToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte data : bytes) {
            builder.append(String.format("%02X ", data));
        }
        return builder.toString();
    }

    public List<Curator> getCurators() {
        return curatorMapper.getCurators();
    }
}

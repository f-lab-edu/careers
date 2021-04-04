package com.dev.careers.repository;

import com.dev.careers.domain.Curator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface CuratorRepository {

    void save(Curator curator);

    int isEmail(String email);
}

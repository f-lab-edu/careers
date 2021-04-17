package com.dev.careers.repository;

import com.dev.careers.domain.Curator;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CuratorRepository {

    void save(Curator curator);

    Boolean isExistEmail(String email);
}

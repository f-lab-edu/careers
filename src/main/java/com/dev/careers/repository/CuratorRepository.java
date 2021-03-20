package com.dev.careers.repository;

import com.dev.careers.domain.Curator;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CuratorRepository {

    void save(Curator curator);

    List<Curator> findAll();

    int isEmail(String email);
}

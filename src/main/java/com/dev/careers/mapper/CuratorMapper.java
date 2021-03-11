package com.dev.careers.mapper;

import com.dev.careers.model.Curator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CuratorMapper {

    Integer insertCurator(
            @Param("email") String email,
            @Param("name") String name,
            @Param("password") String password,
            @Param("salt") String salt);

    List<String> getCuratorsEmail();
}

package com.dev.careers.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CuratorMapper {

    Integer insertCurator(
        @Param("email") String email,
        @Param("name") String name,
        @Param("password") String password,
        @Param("salt") String salt);

    List<String> getCuratorsEmail();

    HashMap<String, String> getMemberInfo(@Param("email") String email);
}

package com.dev.careers.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CuratorMapper {

    Integer insertCurator(
        @Param("email") String email,
        @Param("name") String name,
        @Param("password") String password,
        @Param("salt") String salt);

    HashMap<String, String> getMemberInfo(@Param("email") String email);

    String getCuratorsEmail(@Param("email") String email);
}

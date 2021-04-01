package com.dev.careers.mapper;

import com.dev.careers.model.Curator;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CuratorMapper {

    int insertCurator(Curator curator);

    HashMap<String, String> getMemberInfo(@Param("email") String email);

    boolean checkEmailExists(@Param("email") String email);
}

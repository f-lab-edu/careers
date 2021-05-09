package com.dev.careers.mapper;

import com.dev.careers.model.Academic;
import com.dev.careers.model.Career;
import com.dev.careers.model.Curator;
import com.dev.careers.model.Profile;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CuratorMapper {

    int insertCurator(Curator curator);
    Curator getMemberInfo(@Param("email") String email);
    boolean checkEmailExists(@Param("email") String email);
}

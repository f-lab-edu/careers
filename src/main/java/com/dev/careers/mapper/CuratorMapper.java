package com.dev.careers.mapper;

import com.dev.careers.model.Curator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 큐레이터 Mapper
 */
@Mapper
public interface CuratorMapper {

    int insertCurator(Curator curator);

    Curator getMemberInfo(@Param("email") String email);

    boolean checkEmailExists(@Param("email") String email);
}

package com.dev.careers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Academic Model
 *
 * <p>Redis캐싱 적용 시 Jackson라이브러리가 역직렬화 시 기본생성자가 필요함에 따라 NoArgsConstructor 추가
 *
 * @author junehee
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Academic {
    private int academicId;
    private int profileId;
    private String name;
    private String major;

    public void setAcademicId(int academicId) {
        this.academicId = academicId;
    }
}

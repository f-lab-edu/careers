package com.dev.careers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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

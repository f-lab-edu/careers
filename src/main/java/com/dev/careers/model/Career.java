package com.dev.careers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Career {
    private int careerId;
    private int profileId;
    private String company;
    private String companyTitle;

    public void setCareerId(int careerId) {
        this.careerId = careerId;
    }
}

package com.dev.careers.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Profile Model
 */
@Setter
@Getter
public class Profile {
    private int profileId;
    private int curatorId;
    private String title;
    private String introduction;
    private List<Career> careers;
    private List<Academic> academics;
}

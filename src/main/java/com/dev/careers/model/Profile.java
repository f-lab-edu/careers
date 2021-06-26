package com.dev.careers.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Profile Model
 *
 * <p>Redis 캐시 적용 시 Jackson이 deserialize 할때 기본생성자가 있어야 함으로 룸복에서 지원하는 NoArgsConstructor추가
 *
 * @author junehee
 */
@Setter
@Getter
@NoArgsConstructor
public class Profile {
    private int profileId;
    private int curatorId;
    private String title;
    private String introduction;
    private List<Career> careers;
    private List<Academic> academics;
}

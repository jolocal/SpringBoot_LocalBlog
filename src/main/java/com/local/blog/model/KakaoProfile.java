package com.local.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoProfile {

    public Long id;
    public String connectedt;
    public Properties properties;
    public KakaoAccount kakaoAccount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    static public class Properties {
        public String nickname;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    static public class KakaoAccount {

        public Boolean profileNicknameNeedsAgreement;
        public Profile profile;
        public Boolean hasEmail;
        public Boolean emailNeedsAgreement;
        public Boolean isEmailValid;
        public Boolean isEmailVerified;
        public String email;

    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    static public class Profile {
        public String nickname;

    }

}






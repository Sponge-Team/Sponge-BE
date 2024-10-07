package com.petweb.sponge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SpongeApplication {

    // TODO 프론트 같이 서버에 올릴 방법 알아보기
    public static void main(String[] args) {
        SpringApplication.run(SpongeApplication.class, args);
    }

}

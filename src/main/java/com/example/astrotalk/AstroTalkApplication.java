package com.example.astrotalk;

import com.example.astrotalk.entity.user.User;
import com.example.astrotalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AstroTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstroTalkApplication.class, args);
    }

}

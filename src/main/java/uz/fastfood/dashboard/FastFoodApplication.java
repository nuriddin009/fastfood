package uz.fastfood.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class FastFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastFoodApplication.class, args);
    }

}

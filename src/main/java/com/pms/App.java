package com.pms;

import com.pms.service.RabbitAmqpRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Hello world!
 *
 */

@EnableCaching
@SpringBootApplication
public class App
{
    @Profile({"sender","receiver","hello-world"})
    @Bean
    public CommandLineRunner tutorial() {
        return  new RabbitAmqpRunner();
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}

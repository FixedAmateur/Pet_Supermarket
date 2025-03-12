package com.laborexport.pet_supermarket.configuration;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Bean
    public Cloudinary configKey() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "fixedamateur");
        config.put("api_key", "981881743227222");
        config.put("api_secret", "0r3MZpnkLcQcwW3kOlLEEf6mA5c");

        return new Cloudinary(config);
    }
}

package com.laborexport.pet_supermarket.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
//    cloudinary.cloud_name=fixedamateur
//    cloudinary.api_key=526775646914161
//    cloudinary.api_secret=i9iUaAznWqYUl6s1rMZ7XjZtJ3k


    @Bean
    public Cloudinary configKey() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "fixedamateur");
        config.put("api_key", "526775646914161");
        config.put("api_secret", "i9iUaAznWqYUl6s1rMZ7XjZtJ3k");

        return new Cloudinary(config);

    }
}

package com.off3dstudio.off3dbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest {

    @Test
    void applicationClassExists() {
        assertThat(Application.class)
                .as("A classe Application deve existir")
                .isNotNull();
    }

    @Test
    void applicationHasSpringBootAnnotation() {
        assertThat(Application.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class))
                .as("Deve ter a anotação @SpringBootApplication")
                .isTrue();
    }

    @Test
    void applicationStartsInTestMode() {
        assertDoesNotThrow(() -> {
            SpringApplication app = new SpringApplication(Application.class);
            app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
            try (ConfigurableApplicationContext context = app.run("--spring.profiles.active=test")) {
                assertThat(context).isNotNull();
                assertThat(context.isRunning()).isTrue();
            }
        });
    }
}
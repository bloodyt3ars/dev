package ru.naumen.dev.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Тестовое задание",
                description = "Тестовое задание для naumen по направлению Стажер-разработчик", version = "1.0.0",
                contact = @Contact(
                        name = "Antonov Vladimir",
                        url = "https://t.me/bloodyt3ars"
                )
        )
)
public class OpenApiConfig {
}

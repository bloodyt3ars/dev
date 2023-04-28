package ru.naumen.dev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.naumen.dev.component.ReadFromFile;
import ru.naumen.dev.entity.User;

import java.util.*;

@RestController
@RequestMapping("/api/names")
@Tag(name = "names", description = "В этом разделе находятся методы пользователя с системой")
public class ApiController {

    private List<User> users;

    private String path = "src/main/resources/name_age.txt";

    @PostConstruct
    public void init() {
        users = ReadFromFile.read(path);
    }

    @GetMapping("/name")
    @Operation(summary = "Вывод возраста по имени",
            description = "Вывод возраста по имени",
            operationId = "getAge")
    public Map<String, Object> getAge(@RequestParam @Parameter(name = "Name", description = "Имя") String name) {
        String url = "https://api.agify.io/?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        Optional<User> user = users.stream().filter(u -> u.getFirstName().equals(name)).findFirst();
        if (user.isPresent()) {
            user.get().setRequestCount(user.get().getRequestCount() + 1);
        }
        Object ageFromQuery = restTemplate.getForObject(url, Map.class).get("age");
        int queryAge;
        if (ageFromQuery==null){
            queryAge = 0;
        }
        else {
            queryAge = (int) ageFromQuery;
        }
        int age = user.map(User::getAge).orElse(
                queryAge
        );
        Map<String, Object> map = new HashMap<>();
        map.put("Name", name);
        map.put("Age", age);
        return map;
    }

    @GetMapping("/stats")
    @Operation(summary = "Вывод статистики",
            description = "Вывод статистики",
            operationId = "stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        for (User user : users) {
            stats.put("Name", user.getFirstName());
            stats.put("Request count", user.getRequestCount());
        }
        return stats;
    }

    @GetMapping("/stats/max-age")
    @Operation(summary = "Вывод статистики",
            description = "Вывод статистики",
            operationId = "stats/max-age")
    public Map<String, Object> getMaxAge() {
        Map<String, Object> stats = new HashMap<>();
        Optional<User> userWithMaxAge = users.stream().max(Comparator.comparing(User::getAge));
        if (userWithMaxAge.isPresent()){
            User user = userWithMaxAge.get();
            stats.put("Name", user.getFirstName());
            stats.put("Max age", user.getAge());
        }
        else {
            stats.put("Response", "No users found.");
        }
        return stats;
    }
}

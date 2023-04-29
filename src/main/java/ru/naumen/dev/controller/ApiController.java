package ru.naumen.dev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
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
    private Map<String, Integer> stats;

    private String path = "src/main/resources/name_age.txt";

    @PostConstruct
    public void init() {
        users = ReadFromFile.read(path);
        stats = new HashMap<>();
    }

    @GetMapping("/name")
    @Operation(summary = "Вывод возраста по имени",
            description = "Вывод возраста по имени",
            operationId = "getAge")
    public Map<String, Object> getAge(@RequestParam @Parameter(name = "Name", description = "Имя") String name) {
        Map<String, Object> map = new HashMap<>();
        if (stats.containsKey(name)) {
            stats.put(name, stats.get(name) + 1);
        } else {
            stats.put(name, 1);
        }
        String url = "https://api.agify.io/?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        Optional<User> user = users.stream().filter(u -> u.getFirstName().equals(name)).findFirst();
        map.put("Name", name);
        if (user.isPresent()) {
            int age = user.map(User::getAge).orElse(null);
            map.put("Age", age);
        } else {
            if (restTemplate.getForObject(url, Map.class).get("age") == null) {
                map.put("Age", "Не указано");
            } else {
                int age = (Integer) restTemplate.getForObject(url, Map.class).get("age");
                map.put("Age", age);
            }
        }
        return map;
    }

    @GetMapping("/stats")
    @Operation(summary = "Вывод статистики",
            description = "Вывод статистики",
            operationId = "stats")
    public List<Map<String, Object>> getStats() {
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("Name", stat.getKey());
            stats.put("Request count", stat.getValue());
            response.add(stats);
        }
        return response;
    }

    @GetMapping("/stats/max-age")
    @Operation(summary = "Вывод статистики",
            description = "Вывод статистики",
            operationId = "stats/max-age")
    public Map<String, Object> getMaxAge() {
        Map<String, Object> stats = new HashMap<>();
        Optional<User> userWithMaxAge = users.stream().max(Comparator.comparing(User::getAge));
        if (userWithMaxAge.isPresent()) {
            User user = userWithMaxAge.get();
            stats.put("Name", user.getFirstName());
            stats.put("Max age", user.getAge());
        } else {
            stats.put("Response", "No users found.");
        }
        return stats;
    }
}

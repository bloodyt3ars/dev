package ru.naumen.dev.component;

import org.springframework.stereotype.Component;
import ru.naumen.dev.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadFromFile {

    public static List<User> read(String path) {
        List<User> users = new ArrayList<>();
        File file = new File(path);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found: " + e);
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("_");
                User user = new User(parts[0], Integer.parseInt(parts[1]));
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e);
        }
        return users;
    }
}

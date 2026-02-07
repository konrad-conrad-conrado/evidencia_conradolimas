package com.evidencia.repo;

import com.evidencia.model.AdminUser;

import java.io.*;
import java.util.*;

public class AdminRepository {
    private final String filePath;
    private final Map<String, AdminUser> admins = new HashMap<>();

    public AdminRepository(String filePath) {
        this.filePath = filePath;
    }

    public void load() {
        admins.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                line = line.trim();
                if (line.isEmpty()) continue;
                AdminUser a = AdminUser.fromCsv(line);
                if (a != null) admins.put(a.getUserId(), a);
            }
        } catch (IOException e) {
            System.out.println("Error loading admins: " + e.getMessage());
        }
    }

    public boolean validate(String userId, String password) {
        AdminUser a = admins.get(userId);
        return a != null && a.getPassword().equals(password);
    }
}


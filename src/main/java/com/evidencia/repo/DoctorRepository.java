package com.evidencia.repo;

import com.evidencia.model.Doctor;

import java.io.*;
import java.util.*;

public class DoctorRepository {
    private final String filePath;
    private final Map<String, Doctor> doctors = new LinkedHashMap<>();

    public DoctorRepository(String filePath) {
        this.filePath = filePath;
    }

    public void load() {
        doctors.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // header
                line = line.trim();
                if (line.isEmpty()) continue;
                Doctor d = Doctor.fromCsv(line);
                if (d != null) doctors.put(d.getId(), d);
            }
        } catch (IOException e) {
            System.out.println("Error loading doctors: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("id,fullName,specialty");
            bw.newLine();
            for (Doctor d : doctors.values()) {
                bw.write(d.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors: " + e.getMessage());
        }
    }

    public boolean exists(String id) { return doctors.containsKey(id); }

    public void add(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }

    public Collection<Doctor> list() {
        return doctors.values();
    }

    public Doctor getById(String id) {
        return doctors.get(id);
    }
}

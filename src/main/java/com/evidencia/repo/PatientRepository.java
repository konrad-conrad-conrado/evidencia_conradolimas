package com.evidencia.repo;

import com.evidencia.model.Patient;

import java.io.*;
import java.util.*;

public class PatientRepository {
    private final String filePath;
    private final Map<String, Patient> patients = new LinkedHashMap<>();

    public PatientRepository(String filePath) {
        this.filePath = filePath;
    }

    public void load() {
        patients.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                line = line.trim();
                if (line.isEmpty()) continue;
                Patient p = Patient.fromCsv(line);
                if (p != null) patients.put(p.getId(), p);
            }
        } catch (IOException e) {
            System.out.println("Error loading patients: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("id,fullName");
            bw.newLine();
            for (Patient p : patients.values()) {
                bw.write(p.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving patients: " + e.getMessage());
        }
    }

    public boolean exists(String id) { return patients.containsKey(id); }

    public void add(Patient patient) {
        patients.put(patient.getId(), patient);
    }

    public Collection<Patient> list() {
        return patients.values();
    }

    public Patient getById(String id) {
        return patients.get(id);
    }
}

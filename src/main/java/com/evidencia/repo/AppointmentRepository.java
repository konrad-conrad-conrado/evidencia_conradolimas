package com.evidencia.repo;

import com.evidencia.model.Appointment;

import java.io.*;
import java.util.*;

public class AppointmentRepository {
    private final String filePath;
    private final Map<String, Appointment> appts = new LinkedHashMap<>();

    public AppointmentRepository(String filePath) {
        this.filePath = filePath;
    }

    public void load() {
        appts.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                line = line.trim();
                if (line.isEmpty()) continue;
                Appointment a = Appointment.fromCsv(line);
                if (a != null) appts.put(a.getId(), a);
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("id,dateTime,reason,doctorId,patientId");
            bw.newLine();
            for (Appointment a : appts.values()) {
                bw.write(a.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }

    public boolean exists(String id) { return appts.containsKey(id); }

    public void add(Appointment appt) {
        appts.put(appt.getId(), appt);
    }

    public Collection<Appointment> list() {
        return appts.values();
    }
}

package com.evidencia.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DbUtil {

    public static final String DB_DIR = "db";
    public static final String ADMINS = "db/admins.csv";
    public static final String DOCTORS = "db/doctors.csv";
    public static final String PATIENTS = "db/patients.csv";
    public static final String APPOINTMENTS = "db/appointments.csv";

    public static void ensureDbFiles() {
        ensureDir(DB_DIR);

        ensureFile(ADMINS, "userId,password\nadmin,admin123\n"); // admin default
        ensureFile(DOCTORS, "id,fullName,specialty\n");
        ensureFile(PATIENTS, "id,fullName\n");
        ensureFile(APPOINTMENTS, "id,dateTime,reason,doctorId,patientId\n");
    }

    private static void ensureDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) f.mkdirs();
    }

    private static void ensureFile(String path, String contentIfNew) {
        File f = new File(path);
        if (!f.exists()) {
            try (FileWriter fw = new FileWriter(f)) {
                fw.write(contentIfNew);
            } catch (IOException e) {
                System.out.println("Error creando archivo DB: " + path + " -> " + e.getMessage());
            }
        }
    }
}

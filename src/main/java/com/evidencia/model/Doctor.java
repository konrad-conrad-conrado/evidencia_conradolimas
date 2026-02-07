package com.evidencia.model;

public class Doctor {
    private String id;
    private  String fullName;
    private String specialty;

    public Doctor(String id, String fullName, String speciality) {
        this.id = id;
        this.fullName = fullName;
        this.specialty = speciality;
    }
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getSpeciality() { return specialty; }

    public String toCsv() {
        return id + "," + fullName + "," + specialty;
    }

    public static Doctor fromCsv(String line) {
        String[] p =line.split(",",3);
        if (p.length < 3) return null;
        return new Doctor(p[0].trim(), p[1].trim(), p[2].trim());
    }
}
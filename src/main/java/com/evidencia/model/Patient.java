package com.evidencia.model;

public class Patient {
    private String id;
    private String fullName;

    public Patient(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() { return  id; }
    public String getFullName() { return fullName; }

    public String toCsv() {
        return id + "," + fullName;
    }

    public static Patient fromCsv(String line) {
        String[] p = line.split(",", 2);
        if (p.length < 2) return null;
        return new Patient(p[0].trim(), p[1].trim());
    }
}

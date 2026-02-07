package com.evidencia.model;

import java.security.PublicKey;

public class Appointment {
    private String id;
    private String dataTime;
    private String reason;
    private String doctorId;
    private String patientId;

    public Appointment(String id, String dataTime, String reason, String doctorId, String patientId) {
        this.id = id;
        this.dataTime = dataTime;
        this.reason = reason;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public String getId() { return id; }
    public String getDataTime() { return dataTime; }
    public String getReason() {return reason; }
    public String getDoctorId() { return doctorId; }
    public String getPatientId() { return patientId; }

    public String toCsv() {
        return id + "," + dataTime + "," + reason + "," + doctorId + "," + patientId;
    }

    public static Appointment fromCsv(String line) {
        String[] p = line.split(",", 5);
        if (p.length < 5) return null;
        return new Appointment(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim());
    }
}

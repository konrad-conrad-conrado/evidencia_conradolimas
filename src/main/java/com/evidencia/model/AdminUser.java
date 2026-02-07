package com.evidencia.model;

public class AdminUser {
    private String userId;
    private String password;

    public AdminUser(String userId, String password){
        this.userId =userId;
        this.password = password;
    }

    public String getUserId() {return userId;}
    public String getPassword(){return password;}

    public String toCsv(){
        return userId + "," + password;
    }

    public static AdminUser fromCsv(String line) {
        String[] p = line.split(",", 2);
        if (p.length < 2) return null;
        return new AdminUser(p[0].trim(), p[1].trim());
    }
}


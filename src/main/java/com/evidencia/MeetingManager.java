package com.evidencia;

import com.evidencia.db.DbUtil;
import com.evidencia.model.*;
import com.evidencia.repo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeetingManager {

    List<AdminUser> admins = new ArrayList<>();


    public static void main(String[] args) {
        DbUtil.ensureDbFiles();

        DoctorRepository doctorRepo = new DoctorRepository(DbUtil.DOCTORS);
        PatientRepository patientRepo = new PatientRepository(DbUtil.PATIENTS);
        AppointmentRepository apptRepo = new AppointmentRepository(DbUtil.APPOINTMENTS);
        AdminRepository adminRepo = new AdminRepository(DbUtil.ADMINS);

        doctorRepo.load();
        patientRepo.load();
        apptRepo.load();
        adminRepo.load();

        Scanner sc = new Scanner(System.in);

        // Login loop
        if (!login(sc, adminRepo)) {
            System.out.println("Demasiados intentos. Saliendo...");
            return;
        }

        int option = 0;
        while (option != 6) {
            System.out.println("\n=== Sistema de Gestión de Citas ===");
            System.out.println("1) Alta Doctor");
            System.out.println("2) Alta Paciente");
            System.out.println("3) Crear Cita");
            System.out.println("4) Listar Doctores");
            System.out.println("5) Listar Pacientes");
            System.out.println("6) Salir");
            System.out.print("Selecciona una opción: ");

            String input = sc.nextLine().trim();
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            try {
                switch (option) {
                    case 1:
                        altaDoctor(sc, doctorRepo);
                        doctorRepo.save();
                        break;
                    case 2:
                        altaPaciente(sc, patientRepo);
                        patientRepo.save();
                        break;
                    case 3:
                        crearCita(sc, doctorRepo, patientRepo, apptRepo);
                        apptRepo.save();
                        break;
                    case 4:
                        listarDoctores(doctorRepo);
                        break;
                    case 5:
                        listarPacientes(patientRepo);
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        sc.close();
    }

    private static boolean login(Scanner sc, AdminRepository adminRepo) {
        int tries = 0;
        while (tries < 3) {
            System.out.print("Admin ID: ");
            String user = sc.nextLine().trim();
            System.out.print("Password: ");
            String pass = sc.nextLine().trim();

            if (adminRepo.validate(user, pass)) {
                System.out.println("Acceso concedido.\n");
                return true;
            }
            System.out.println("Credenciales incorrectas.\n");
            tries++;
        }
        return false;
    }

    private static void altaDoctor(Scanner sc, DoctorRepository repo) {
        System.out.print("ID Doctor: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) { System.out.println("ID vacío."); return; }
        if (repo.exists(id)) { System.out.println("Ya existe ese ID."); return; }

        System.out.print("Nombre completo: ");
        String name = sc.nextLine().trim();
        System.out.print("Especialidad: ");
        String spec = sc.nextLine().trim();

        if (name.isEmpty() || spec.isEmpty()) {
            System.out.println("Datos incompletos.");
            return;
        }

        repo.add(new Doctor(id, name, spec));
        System.out.println("Doctor registrado.");
    }

    private static void altaPaciente(Scanner sc, PatientRepository repo) {
        System.out.print("ID Paciente: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) { System.out.println("ID vacío."); return; }
        if (repo.exists(id)) { System.out.println("Ya existe ese ID."); return; }

        System.out.print("Nombre completo: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Nombre vacío."); return; }

        repo.add(new Patient(id, name));
        System.out.println("Paciente registrado.");
    }

    private static void crearCita(Scanner sc, DoctorRepository dRepo, PatientRepository pRepo, AppointmentRepository aRepo) {
        System.out.print("ID Cita: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) { System.out.println("ID vacío."); return; }
        if (aRepo.exists(id)) { System.out.println("Ya existe ese ID de cita."); return; }

        System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
        String dt = sc.nextLine().trim();
        System.out.print("Motivo: ");
        String reason = sc.nextLine().trim();

        System.out.print("ID Doctor: ");
        String doctorId = sc.nextLine().trim();
        if (!dRepo.exists(doctorId)) { System.out.println("Doctor no encontrado."); return; }

        System.out.print("ID Paciente: ");
        String patientId = sc.nextLine().trim();
        if (!pRepo.exists(patientId)) { System.out.println("Paciente no encontrado."); return; }

        if (dt.isEmpty() || reason.isEmpty()) {
            System.out.println("Datos incompletos.");
            return;
        }

        aRepo.add(new Appointment(id, dt, reason, doctorId, patientId));
        System.out.println("Cita creada.");
    }

    private static void listarDoctores(DoctorRepository repo) {
        System.out.println("\n--- Doctores ---");
        for (Doctor d : repo.list()) {
            System.out.println(d.getId() + " | " + d.getFullName() + " | " + d.getSpeciality());
        }
    }

    private static void listarPacientes(PatientRepository repo) {
        System.out.println("\n--- Pacientes ---");
        for (Patient p : repo.list()) {
            System.out.println(p.getId() + " | " + p.getFullName());
        }
    }
}


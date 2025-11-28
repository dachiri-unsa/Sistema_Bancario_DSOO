package sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import gestores.GestorPermisos;

public class MenuPermisos {
    private Scanner sc;
    private Banco banco;

    public MenuPermisos(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void mostrarMenuPermisos() {
        if (!entidades.SessionManager.getCurrentUser().getPermisos().contains("PERM")) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
        String opcion;
        do {
            System.out.println("\n==== MENU PERMISOS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Listar permisos por rol.");
            System.out.println("2. Agregar permiso a un rol.");
            System.out.println("3. Quitar permiso a un rol.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    listarPermisosPorRol();
                    break;
                case "2":
                    agregarPermisoRol();
                    break;
                case "3":
                    quitarPermisoRol();
                    break;
                case "0":
                    MenuSistema.limpiarPantalla();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        } while (!opcion.equalsIgnoreCase("0"));
    }

    private void listarPermisosPorRol() {
        System.out.println("Roles disponibles: Administrador, Empleado, Cliente");
        System.out.println("Ingrese el rol a consultar:");
        String rol = sc.nextLine().trim();
        List<String> permisos = GestorPermisos.getPermisosByRol(rol);
        if (permisos != null) {
            System.out.println("Permisos para " + rol + ":");
            for (String p : permisos) {
                System.out.println("- " + p);
            }
        } else {
            System.out.println("Rol no encontrado o sin permisos asignados.");
        }
    }

    private void agregarPermisoRol() {
        System.out.println("Roles disponibles: Administrador, Empleado, Cliente");
        System.out.println("Ingrese el rol al que desea agregar un permiso:");
        String rol = sc.nextLine().trim();
        List<String> permisos = GestorPermisos.getPermisosByRol(rol);

        if (permisos == null) {
            System.out.println("Rol no encontrado.");
            return;
        }

        ArrayList<String> permisosDisponibles = banco.getGestorPermisos().getListaPermisos();
        System.out.println("Permisos disponibles en el sistema: " + permisosDisponibles);

        System.out.println("Ingrese el codigo del permiso a agregar:");
        String nuevoPermiso = sc.nextLine().trim().toUpperCase();

        if (!permisosDisponibles.contains(nuevoPermiso)) {
            System.out.println("El permiso ingresado no es valido (no existe en la lista global).");
            return;
        }

        if (permisos.contains(nuevoPermiso)) {
            System.out.println("El rol ya tiene este permiso.");
        } else {
            List<String> nuevosPermisos = new ArrayList<>(permisos);
            nuevosPermisos.add(nuevoPermiso);
            banco.getGestorPermisos().modificarPermisosByRol(rol, nuevosPermisos);
            System.out.println("Permiso agregado exitosamente.");
        }
    }

    private void quitarPermisoRol() {
        System.out.println("Roles disponibles: Administrador, Empleado, Cliente");
        System.out.println("Ingrese el rol al que desea quitar un permiso:");
        String rol = sc.nextLine().trim();
        List<String> permisos = GestorPermisos.getPermisosByRol(rol);

        if (permisos == null) {
            System.out.println("Rol no encontrado.");
            return;
        }

        System.out.println("Permisos actuales: " + permisos);
        System.out.println("Ingrese el codigo del permiso a quitar:");
        String permisoQuitar = sc.nextLine().trim().toUpperCase();

        if (permisos.contains(permisoQuitar)) {
            List<String> nuevosPermisos = new ArrayList<>(permisos);
            nuevosPermisos.remove(permisoQuitar);
            banco.getGestorPermisos().modificarPermisosByRol(rol, nuevosPermisos);
            System.out.println("Permiso eliminado exitosamente.");
        } else {
            System.out.println("El rol no tiene ese permiso.");
        }
    }
}

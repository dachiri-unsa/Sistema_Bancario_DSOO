package sistema;

import java.util.Scanner;

import entidades.concretas.Cajero;
import entidades.enumerables.TipoPermiso;

public class MenuCajeros {
    private Scanner sc;
    private Banco banco;

    public MenuCajeros(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void mostrarMenuCajeros() {
        if (!entidades.concretas.SessionManager.getCurrentUser().getPermisos().contains(TipoPermiso.CAJE)) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
        String opcion;
        do {
            System.out.println("\n==== MENU GESTION CAJEROS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Registrar nuevo cajero.");
            System.out.println("2. Cambiar disponibilidad de cajero.");
            System.out.println("3. Listar cajeros.");
            System.out.println("4. Eliminar cajero.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registrarCajero();
                    break;
                case "2":
                    cambiarDisponibilidad();
                    break;
                case "3":
                    banco.getGestorCajeros().listarCajeros();
                    break;
                case "4":
                    eliminarCajero();
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

    private void registrarCajero() {
        System.out.println("==== REGISTRAR CAJERO ====");
        System.out.println("Ingrese el ID del nuevo cajero (Ej: C005):");
        String id = sc.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("El ID no puede estar vacio.");
            return;
        }

        if (banco.getGestorCajeros().buscarCajero(id) != null) {
            System.out.println("Ya existe un cajero con ese ID.");
            return;
        }

        Cajero nuevoCajero = new Cajero(id, true); // Por defecto disponible
        banco.getGestorCajeros().agregarCajero(nuevoCajero);
        System.out.println("Cajero registrado exitosamente.");
    }

    private void cambiarDisponibilidad() {
        System.out.println("==== CAMBIAR DISPONIBILIDAD ====");
        System.out.println("Ingrese el ID del cajero:");
        String id = sc.nextLine().trim();
        Cajero cajero = banco.getGestorCajeros().buscarCajero(id);

        if (cajero != null) {
            System.out.println("Estado actual: " + (cajero.getDisponible() ? "Disponible" : "Fuera de servicio"));
            System.out.println("Desea cambiar el estado? (S/N)");
            String resp = sc.nextLine().trim();
            if (resp.equalsIgnoreCase("S")) {
                cajero.setDisponible(!cajero.getDisponible());
                System.out.println(
                        "Estado actualizado a: " + (cajero.getDisponible() ? "Disponible" : "Fuera de servicio"));
            }
        } else {
            System.out.println("Cajero no encontrado.");
        }
    }

    private void eliminarCajero() {
        System.out.println("==== ELIMINAR CAJERO ====");
        System.out.println("Ingrese el ID del cajero a eliminar:");
        String id = sc.nextLine().trim();
        Cajero cajero = banco.getGestorCajeros().buscarCajero(id);

        if (cajero != null) {
            banco.getGestorCajeros().eliminar(cajero);
            System.out.println("Cajero eliminado exitosamente.");
        } else {
            System.out.println("Cajero no encontrado.");
        }
    }
}

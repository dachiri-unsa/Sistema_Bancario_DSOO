package sistema;

import java.util.Scanner;

import entidades.Usuario;
import gestores.GestorUsuario;
import entidades.LoginView;

public class MenuUsuarios {
    private Banco banco;
    private Scanner sc;

    public MenuUsuarios(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void mostrarMenuUsuarios() {
        if (!entidades.SessionManager.getCurrentUser().getPermisos().contains("USUA")) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
        String opcion;
        do {
            System.out.println("\n==== MENU USUARIOS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Crear un usuario a Cliente.");
            System.out.println("2. Modificar contraseña de un usuario.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registrarUsuario();
                    break;
                case "2":
                    modificarContraseña();
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

    public void registrarUsuario() {
        System.out.println("\n==== REGISTRANDO UN CLIENTE ====");
        // #### VALIDACION DE NOMBRE USUARIO ####
        System.out.println("Ingrese nombre de usuario.");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }
        if (nombre.length() < 2) {
            System.out.println("El nombre es demasiado corto.");
            return;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("El nombre solo puede contener letras y espacios.");
            return;
        }
        // #### VALIDACION DE CONTRASEÑA DE USUARIO ####
        System.out.println("Ingrese su contraseña de usuario.");
        String contrasenia = sc.nextLine().trim();
        if (contrasenia.isEmpty()) {
            System.out.println("La contraseña no puede estar vacia.");
            return;
        }
        if (contrasenia.length() < 2) {
            System.out.println("La contraseña es demasiado corta.");
            return;
        }
        // #### VALIDACION DE DNI PERSONA ####
        System.out.println("Ingrese su DNI: ");
        String dniPersona = sc.nextLine().trim();
        if (dniPersona.isEmpty()) {
            System.out.println("El DNI no puede estar vacio.");
            return;
        }
        if (!dniPersona.matches("\\d{8}")) {
            System.out.println("El DNI debe contener exactamente 8 dígitos numéricos.");
            return;
        }
        System.out.println("DNI valido: " + dniPersona);

        banco.getGestorUsuario().crearUsuario(dniPersona, new LoginView(nombre, contrasenia));
    }

    public void modificarContraseña() {
        System.out.println("Ingrese nombre del usuario a cambiar contraseña: ");
        Usuario usuario = GestorUsuario.buscarPorUsuario(sc.nextLine().trim());
        if (usuario == null) {
            System.out.println("No se encontro ese usuario.");
            return;
        }
        // #### VALIDACION DE CONTRASEÑA DE USUARIO ####
        System.out.println("Ingrese su nueva contraseña de usuario.");
        String contrasenia = sc.nextLine().trim();
        if (contrasenia.isEmpty()) {
            System.out.println("La contraseña no puede estar vacia.");
            return;
        }
        if (contrasenia.length() < 2) {
            System.out.println("La contraseña es demasiado corta.");
            return;
        }
        banco.getGestorUsuario().modificarContraseña(usuario.getNombreUsuario(), contrasenia);
    }

}

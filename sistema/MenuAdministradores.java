package sistema;

import java.util.Scanner;

import entidades.concretas.Administrador;
import entidades.concretas.Persona;
import entidades.enumerables.TipoPermiso;

public class MenuAdministradores {
    private Scanner sc;
    private Banco banco;

    public MenuAdministradores(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void mostrarMenuAdministradores() {
        if (!entidades.concretas.SessionManager.getCurrentUser().getPermisos().contains(TipoPermiso.ADMI)) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
        String opcion;
        do {
            System.out.println("\n==== MENU ADMINISTRADORES ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Registrar un nuevo Administrador.");
            System.out.println("2. Modificar datos de un Administrador.");
            System.out.println("3. Eliminar un Administrador.");
            System.out.println("4. Listar Administradores.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registrarAdministrador();
                    break;
                case "2":
                    modificarAdministrador();
                    break;
                case "3":
                    eliminarAdministrador();
                    break;
                case "4":
                    banco.getGestorAdministradores().listarAdministradores();
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

    public void registrarAdministrador() {
        System.out.println("==== REGISTRANDO ADMINISTRADOR ====");

        System.out.println("Ingrese nombre del administrador:");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        System.out.println("Ingrese los apellidos del administrador:");
        String apellidos = sc.nextLine().trim();
        if (apellidos.isEmpty()) {
            System.out.println("Los apellidos no pueden estar vacios.");
            return;
        }

        System.out.println("Ingrese el DNI del administrador:");
        String dni = sc.nextLine().trim();
        if (!dni.matches("\\d{8}")) {
            System.out.println("El DNI debe tener 8 digitos.");
            return;
        }

        if (banco.getGestorAdministradores().buscarPorDni(dni) != null) {
            System.out.println("Ya existe un administrador con ese DNI.");
            return;
        }

        System.out.println("Ingrese el telefono:");
        String telefono = sc.nextLine().trim();

        System.out.println("Ingrese la direccion:");
        String direccion = sc.nextLine().trim();

        System.out.println("Ingrese el nombre de usuario para el sistema:");
        String usuario = sc.nextLine().trim();
        if (usuario.isEmpty()) {
            System.out.println("El usuario no puede estar vacio.");
            return;
        }

        System.out.println("Ingrese la contraseña:");
        String password = sc.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("La contraseña no puede estar vacia.");
            return;
        }

        Persona persona = new Persona(nombre, apellidos, dni, telefono, direccion);
        Administrador admin = new Administrador(persona, usuario,
                util.PasswordUtil.hashPassword(password), true);

        banco.getGestorAdministradores().agregar(admin);
        System.out.println("Administrador registrado con exito.");
    }

    public void modificarAdministrador() {
        System.out.println("==== MODIFICAR ADMINISTRADOR ====");
        System.out.println("Ingresar el DNI del administrador a buscar:");
        String dni = sc.nextLine().trim();
        Administrador admin = banco.getGestorAdministradores().buscarPorDni(dni);

        if (admin != null) {
            System.out.println("Administrador encontrado: " + admin.getNombre() + " " +
                    admin.getApellido());
            System.out.println("Ingrese que dato modificar:");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Telefono");
            System.out.println("4. Direccion");
            System.out.println("0. Cancelar");

            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println("Ingrese el nombre nuevo: ");
                    admin.setNombre(sc.nextLine().trim());
                    break;
                case "2":
                    System.out.println("Ingrese el apellido nuevo: ");
                    admin.setApellido(sc.nextLine().trim());
                    break;
                case "3":
                    System.out.println("Ingrese el telefono nuevo: ");
                    admin.setTelefono(sc.nextLine().trim());
                    break;
                case "4":
                    System.out.println("Ingrese la direccion nueva: ");
                    admin.setDireccion(sc.nextLine().trim());
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
            System.out.println("Datos actualizados.");
        } else {
            System.out.println("Administrador no encontrado.");
        }
    }

    public void eliminarAdministrador() {
        System.out.println("==== ELIMINAR ADMINISTRADOR ====");
        System.out.println("Ingresar el DNI del administrador a eliminar:");
        String dni = sc.nextLine().trim();

        if (entidades.concretas.SessionManager.getCurrentUser().getPersona().getDNI().equals(dni)) {
            System.out.println("No puedes eliminar tu propia cuenta de administrador.");
            return;
        }

        Administrador admin = banco.getGestorAdministradores().buscarPorDni(dni);
        if (admin != null) {
            banco.getGestorAdministradores().eliminarAdministrador(admin);
            System.out.println("Administrador eliminado de la lista.");
        } else {
            System.out.println("Administrador no encontrado.");
        }
    }
}

package sistema;

import java.util.Scanner;

import entidades.concretas.Cliente;
import entidades.concretas.Persona;
import entidades.enumerables.TipoPermiso;

public class MenuClientes {
    private Scanner sc;
    private Banco banco;

    public MenuClientes(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void mostrarMenuClientes() {
        if (!entidades.concretas.SessionManager.getCurrentUser().getPermisos().contains(TipoPermiso.CLIE)) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
        String opcion;
        do {
            System.out.println("\n==== MENU CLIENTES ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Registrar Cliente.");
            System.out.println("2. Buscar Cliente.");
            System.out.println("3. Modificar datos de un Cliente.");
            System.out.println("4. Eliminar un cliente.");
            System.out.println("5. Lista de Clientes");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registrarCliente();
                    break;
                case "2":
                    buscarCliente();
                    break;
                case "3":
                    modificarCliente();
                    break;
                case "4":
                    eliminarCliente();
                    break;
                case "5":
                    listarClientes();
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

    public void registrarCliente() {
        System.out.println("==== REGISTRANDO CLIENTE ====");
        // ##### VALIDACION DE NOMBRE(S) #####
        System.out.println("Ingrese su nombre.");
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
        System.out.println("Nombre valido: " + nombre);
        // ##### VALIDACION DE APELLIDOS #####
        System.out.println("Ingrese sus apellidos: ");
        String apellidos = sc.nextLine().trim();
        if (apellidos.isEmpty()) {
            System.out.println("Los apellidos no puede estar vacio.");
            return;
        }
        if (nombre.length() < 2) {
            System.out.println("Los apellidos es demasiado corto.");
            return;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Los apellidos solo puede contener letras y espacios.");
            return;
        }
        System.out.println("Apellidos validos: " + apellidos);
        // ##### VALIDACION DE DNI #####
        System.out.println("Ingrese su dni: ");
        String dni = sc.nextLine().trim();
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }
        if (!dni.matches("\\d{8}")) {
            System.out.println("El DNI debe contener exactamente 8 dígitos numéricos.");
            return;
        }
        System.out.println("DNI valido: " + dni);
        // ##### VALIDACION DE NUMERO DE TELEFONO #####
        System.out.println("Ingrese su telefono: ");
        String telefono = sc.nextLine().trim();
        if (telefono.isEmpty()) {
            System.out.println("El telefono no puede estar vacio.");
            return;
        }
        if (!telefono.matches("9\\d{8}")) {
            System.out.println("El teléfono debe tener 9 dígitos y empezar con 9.");
            return;
        }
        // ##### VALIDACION DE DIRECCION #####
        System.out.println("Ingrese su direccion: ");
        String direccion = sc.nextLine().trim();
        if (direccion.isEmpty()) {
            System.out.println("La direccion no puede estar vacio.");
            return;
        }
        Persona persona = new Persona(nombre, apellidos, dni, telefono, direccion);
        Cliente cliente = new Cliente(persona);
        banco.getGestorClientes().agregarCliente(cliente);
    }

    public void buscarCliente() {
        System.out.println("==== BUSCAR CLIENTE ====");
        System.out.println("Ingresar el DNI del cliente a buscar.");
        String dni = sc.nextLine().trim();
        Cliente cliente = banco.getGestorClientes().buscarCliente(dni);
        if (cliente != null) {
            System.out.println("Cliente encontrado.");
            System.out.println(cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    public void modificarCliente() {
        System.out.println("==== MODIFICAR CLIENTE ====");
        System.out.println("Ingresar el DNI del cliente a buscar.");
        String dni = sc.nextLine().trim();
        Cliente cliente = banco.getGestorClientes().buscarCliente(dni);
        if (cliente != null) {
            System.out.println("Cliente encontrado.");
            System.out.println("Ingrese que dato modificar:");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Telefono");
            System.out.println("4. Direccion");
            System.out.println("5. Volver al Menu Clientes");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println("Ingrese el nombre nuevo: ");
                    String new_nombre = sc.nextLine();
                    cliente.setNombre(new_nombre);
                    break;
                case "2":
                    System.out.println("Ingrese el apellido nuevo: ");
                    String new_apellido = sc.nextLine();
                    cliente.setApellido(new_apellido);
                    break;
                case "3":
                    System.out.println("Ingrese el nombre nuevo: ");
                    String new_telefono = sc.nextLine();
                    cliente.setTelefono(new_telefono);
                    break;
                case "4":
                    System.out.println("Ingrese el nombre nuevo: ");
                    String new_direccion = sc.nextLine();
                    cliente.setDireccion(new_direccion);
                    break;
                case "5":
                    MenuSistema.limpiarPantalla();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }

    }

    public void listarClientes() {
        System.out.println("==== LISTA DE CLIENTES ====");
        banco.getGestorClientes().listarClientes();
    }

    public void eliminarCliente() {
        System.out.println("==== ELIMINAR CLIENTE ====");
        System.out.println("Ingresar el DNI del cliente a buscar.");
        String dni = sc.nextLine().trim();
        Cliente cliente = banco.getGestorClientes().buscarCliente(dni);
        if (cliente != null) {
            banco.getGestorClientes().eliminarCliente(cliente);
            System.out.println("Cliente eliminado con exito.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
}

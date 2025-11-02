package sistema;

import java.util.Scanner;
import entidades.Cliente;

public class MenuClientes {
    private Scanner sc;
    private Banco banco;

    public MenuClientes(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }
    public void mostrarMenuClientes() {
        String opcion;
        do {
            System.out.println("\n==== BANCO ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Registrar Cliente.");
            System.out.println("2. Buscar Cliente.");
            System.out.println("3. Lista de Clientes");
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
                    listarClientes();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        while (!opcion.equalsIgnoreCase("0"));
    }


    public void registrarCliente() {
        System.out.println("==== REGISTRANDO CLIENTE ====");
        System.out.println("Ingrese su nombre.");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }
        System.out.println("Ingrese sus apellidos: ");
        String apellidos = sc.nextLine().trim();
        if (apellidos.isEmpty()) {
            System.out.println("El apellido no puede estar vacio.");
            return;
        }
        System.out.println("Ingrese su dni: ");
        String dni = sc.nextLine().trim();
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }
        System.out.println("Ingrese su telefono: ");
        String telefono = sc.nextLine().trim();
        if (telefono.isEmpty()) {
            System.out.println("El telefono no puede estar vacio.");
            return;
        }
        System.out.println("Ingrese su direccion: ");
        String direccion = sc.nextLine().trim();
        if (direccion.isEmpty()) {
            System.out.println("La direccion no puede estar vacio.");
            return;
        }
        Cliente cliente = new Cliente(nombre, apellidos, dni, telefono, direccion);
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
        }
        else {
            System.out.println("Cliente no encontrado.");
        }
    }

    public void listarClientes() {
        System.out.println("==== LISTA DE CLIENTES ====");
        banco.getGestorClientes().listarClientes();
    }
}

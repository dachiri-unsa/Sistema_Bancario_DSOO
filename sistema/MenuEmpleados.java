package sistema;

import java.util.Scanner;

import entidades.Cliente;
import entidades.Empleado;
import gestores.GestorEmpleados;

public class MenuEmpleados {
    private Scanner sc;
    private Banco banco;

    public MenuEmpleados(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }
    public void mostrarMenuEmpleados() {
        String opcion;
        do {
            System.out.println("\n==== MENU EMPLEADOS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Contratar un nuevo Empleado.");
            System.out.println("2. Modificar datos de un Empleado.");
            System.out.println("3. Despedir algun empleado.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registrarEmpleado();
                    break;
                case "2":
                    modificarEmpleado();
                    break;
                case "3":
                    eliminarEmpleado();
                    break;
                case "0":
                    MenuSistema.limpiarPantalla();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        while (!opcion.equalsIgnoreCase("0"));
    }

    public void registrarEmpleado() {
        //El id en este caso tiene que ser E003
        System.out.println("==== REGISTRANDO EMPLEADO ====");
    // #### VALIDACION DE NOMBRE(S) ####
        System.out.println("Ingrese nombre del empleado.");
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
    // #### VALIDACION DE APELLIDOS ####
        System.out.println("Ingrese los apellidos del empleado: ");
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
        System.out.println("Apellidos validos: "+apellidos);
    // ##### VALIDACION DE DNI #####
        System.out.println("Ingrese el dni del empleado: ");
        String dni = sc.nextLine().trim();
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }
        if (!dni.matches("\\d{8}")) {
            System.out.println("El DNI debe contener exactamente 8 dígitos numéricos.");
            return;
        }
        System.out.println("DNI valido: "+dni);
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
        System.out.println("Ingrese la direccion del empleado: ");
        String direccion = sc.nextLine().trim();
        if (direccion.isEmpty()) {
            System.out.println("La direccion no puede estar vacio.");
            return;
        }
    // #### CREACION DEL ID ####
        System.out.println("Ingrese el que sera el ID empleado (Ejemplo: E003): ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("El ID no puede estar vacio.");
            return;
        }
        if (!id.substring(0,1).equals("E")) {
            System.out.println("El ID de empleado debe empezar con E.");
            return;
        }
        Empleado empleado = new Empleado(id,nombre, apellidos, dni, telefono, direccion);
        banco.getGestorEmpleados().agregarEmpleado(empleado);
    }
// Aqui hacer lo mismo XD
    public void modificarEmpleado() {
        System.out.println("==== MODIFICAR EMPLEADO ====");
        System.out.println("Ingresar el DNI del empleado a buscar.");
        String dni = sc.nextLine().trim();
        Empleado empleado = GestorEmpleados.buscarPorDni(dni);
        if (empleado != null) {
            System.out.println("Empleado encontrado.");
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
                    empleado.setNombre(new_nombre);
                    break;
                case "2":
                    System.out.println("Ingrese el apellido nuevo: ");
                    String new_apellido = sc.nextLine();
                    empleado.setApellido(new_apellido);
                    break;
                case "3":
                    System.out.println("Ingrese el nombre nuevo: ");
                    String new_telefono = sc.nextLine();
                    empleado.setTelefono(new_telefono);
                    break;
                case "4":
                    System.out.println("Ingrese el nombre nuevo: ");
                    String new_direccion = sc.nextLine();
                    empleado.setDireccion(new_direccion);
                    break;
                case "5":
                    MenuSistema.limpiarPantalla();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        else {
            System.out.println("Empleado no encontrado.");
        }
    }
    public void eliminarEmpleado() {
        System.out.println("==== DESPEDIR EMPLEADO ====");
        System.out.println("Ingresar el DNI del empleado a despedir.");
        String dni = sc.nextLine().trim();
        Empleado empleado = GestorEmpleados.buscarPorDni(dni);
        if (empleado != null) {
            banco.getGestorEmpleados().despedirEmpleado(empleado);
            System.out.println("Empleado despedido con éxito.");
        }
        else {
            System.out.println("Empleado no encontrado.");
        }
    }
}

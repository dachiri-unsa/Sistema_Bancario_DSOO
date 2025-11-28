package sistema;

import entidades.*;
import gestores.*;
import interfaces.Funciones;

import java.util.Scanner;

public class MenuMovimientos {
    private Scanner sc;
    private Banco banco;

    public MenuMovimientos(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void mostrarMenuMovimientos() {
        if (!entidades.SessionManager.getCurrentUser().getPermisos().contains("MOVI")) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
        String opcion;
        do {
            System.out.println("\n==== MENU MOVIMIENTOS ====");
            System.out.println("Desea realizar movimientos con:");
            System.out.println("1. Empleado");
            System.out.println("2. Cajero automatico");
            System.out.println("0. Volver al menu principal");
            opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    Empleado empleado = seleccionarEmpleado();
                    if (empleado != null) {
                        menuEmpleado(empleado);
                    }
                    break;
                case "2":
                    Cajero cajero = seleccionarCajero();
                    if (cajero != null) {
                        menuCajero(cajero);
                    }
                    break;
                case "0":
                    MenuSistema.limpiarPantalla();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
            }
        } while (!opcion.equalsIgnoreCase("0"));
    }

    private Empleado seleccionarEmpleado() {
        System.out.println("=== EMPLEADOS DISPONIBLES ===");
        banco.getGestorEmpleados().listarEmpleados();
        System.out.print("Ingrese ID del empleado: ");
        String id = sc.nextLine();
        Empleado empleado = banco.getGestorEmpleados().buscarEmpleado(id);
        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
        }
        return empleado;
    }

    private Cajero seleccionarCajero() {
        System.out.println("=== CAJEROS DISPONIBLES ===");
        banco.getGestorCajeros().listarCajeros();
        System.out.print("Ingrese ID del cajero: ");
        String id = sc.nextLine();
        Cajero cajero = banco.getGestorCajeros().buscarCajero(id);
        if (cajero == null) {
            System.out.println("Cajero no encontrado.");
        }
        return cajero;
    }

    private void menuEmpleado(Empleado empleado) {
        System.out.println("=== MOVIMIENTOS CON EMPLEADO ===");
        System.out.println("1. Depositar con tarjeta");
        System.out.println("2. Retirar con tarjeta");
        System.out.println("3. Depositar con cuenta");
        System.out.println("4. Retirar con cuenta");
        System.out.println("5. Transferencia entre cuentas");
        System.out.print("Seleccione: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                Tarjeta tarjeta1 = obtenerTarjeta();
                if (tarjeta1 != null) {
                    empleado.depositarConTarjeta(tarjeta1, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            case "2":
                Tarjeta tarjeta2 = obtenerTarjeta();
                if (tarjeta2 != null) {
                    empleado.retirarConTarjeta(tarjeta2, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            case "3":
                CuentaBancaria cuenta1 = obtenerCuenta();
                if (cuenta1 != null) {
                    empleado.depositarConCuenta(cuenta1, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            case "4":
                CuentaBancaria cuenta2 = obtenerCuenta();
                if (cuenta2 != null) {
                    empleado.retirarConCuenta(cuenta2, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            case "5":
                System.out.println("Cuenta origen:");
                CuentaBancaria origen = obtenerCuenta();
                System.out.println("Cuenta destino:");
                CuentaBancaria destino = obtenerCuenta();
                if (origen != null && destino != null) {
                    empleado.transferir(origen, destino, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private void menuCajero(Cajero cajero) {
        System.out.println("=== MOVIMIENTOS CON CAJERO AUTOMATICO ===");
        System.out.println("1. Depositar con tarjeta");
        System.out.println("2. Retirar con tarjeta");
        System.out.print("Seleccione: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                Tarjeta tarjeta1 = obtenerTarjeta();
                if (tarjeta1 != null) {
                    cajero.depositarConTarjeta(tarjeta1, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            case "2":
                Tarjeta tarjeta2 = obtenerTarjeta();
                if (tarjeta2 != null) {
                    cajero.retirarConTarjeta(tarjeta2, pedirMonto(), banco.getGestorMovimientos());
                }
                break;
            default:
                System.out.println("Opcion inválida.");
        }
    }

    private Tarjeta obtenerTarjeta() {
        System.out.print("Ingrese numero de tarjeta: ");
        String numero = sc.nextLine();
        Tarjeta tarjeta = banco.getHashTarjetas().get(numero);
        if (tarjeta == null) {
            System.out.println("Tarjeta no encontrada.");
        }
        return tarjeta;
    }

    private CuentaBancaria obtenerCuenta() {
        System.out.print("Ingrese numero de cuenta: ");
        String numero = sc.nextLine();
        CuentaBancaria cuenta = banco.getHashCuentas().get(numero);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada.");
        }
        return cuenta;
    }

    private double pedirMonto() {
        System.out.print("Ingrese monto: ");
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Monto invalido. Se usara 0.");
            return 0;
        }
    }
}

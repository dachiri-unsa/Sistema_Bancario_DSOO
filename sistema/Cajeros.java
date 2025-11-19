package sistema;

import entidades.*;
import gestores.*;
import interfaces.Funciones;

import java.util.Scanner;

public class Cajeros {
    private GestorCajeros gestorCajeros;
    private Scanner sc;
    private Cajero cajero;
    private Tarjeta tarjeta;
    private Banco banco;

    public Cajeros(Banco banco, GestorCajeros gestorCajeros, Scanner sc) {
        this.banco = banco;
        this.gestorCajeros = gestorCajeros;
        this.sc = sc;
        this.tarjeta = null;
    }

    public void iniciar() {
        System.out.println("\n=== CAJEROS DISPONIBLES ===");
        Cajero cajero = seleccionarCajero();
        if (cajero != null) {
            this.cajero = cajero;
            MenuSistema.limpiarPantalla();
            operacionesCajero(cajero);
        } else {
            menuCajeros();
        }
    }

    private void menuCajeros() {
        String opcion;
        do {
            System.out.println("\n=== === === === === === ===");
            System.out.println("1. Volver a consultar cajeros");
            System.out.println("0. Volver a menu principal");
            System.out.print("Seleccione: ");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    MenuSistema.limpiarPantalla();
                    iniciar();
                    break;
                case "0":
                    MenuSistema.limpiarPantalla();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
            }
        } while (!opcion.equalsIgnoreCase("0"));
    }

    private Cajero seleccionarCajero() {
        if (banco.getGestorCajeros().cajerosDisponibles() > 0) {
            banco.getGestorCajeros().listarCajerosDisponibles();
            System.out.print("Ingrese ID del cajero: ");
            String id = sc.nextLine();
            Cajero cajero = banco.getGestorCajeros().buscarCajero(id);
            if (cajero == null) {
                System.out.println("Cajero no encontrado.");
            }
            return cajero;
        } else {
            System.out.println("No hay cajeros disponibles.");
            return null;
        }
    }

    private void operacionesCajero(Cajero cajero) {
        mostrarCajero(cajero);
        
        // if (this.tarjeta == null) {
        //     tarjeta = seleccionarTarjeta();
        // }
        System.out.println("=== OPERACIONES CAJERO ===");
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

    private void mostrarCajero(Cajero cajero){
        System.out.print(cajero);
    }
}

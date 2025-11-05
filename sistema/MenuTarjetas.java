package sistema;

import entidades.CuentaBancaria;
import entidades.Tarjeta;
import gestores.GestorCuentasBancarias;
import gestores.GestorTarjetas;

import java.util.Scanner;

public class MenuTarjetas {
    private Scanner sc;
    private Banco banco;
    private GestorTarjetas gestorTarjetas;
    private GestorCuentasBancarias gestorCuentasBancarias;

    public MenuTarjetas(Banco banco, Scanner sc) {
        this.banco = banco;
        this.gestorTarjetas = new GestorTarjetas();
        this.gestorCuentasBancarias = new GestorCuentasBancarias();
        this.sc = sc;
    }
    public void mostrarMenuTarjetas() {
        String opcion;
        do {
            System.out.println("\n==== MENU TARJETAS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Emitir Tarjeta.");
            System.out.println("2. Vincular tarjeta a una cuenta.");
            System.out.println("0. Volver al menu principal.");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    emitirTarjeta();
                    break;
                case "2":
                    vincularTarjeta();
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

    public void emitirTarjeta() {
        System.out.println("==== EMITIR TARJETA ====");
        System.out.print("Ingrese la compañia: ");
        String compañia = sc.nextLine();
        String numeroTarjeta = gestorTarjetas.generarNumeroTarjeta();
        Tarjeta tarjeta = new Tarjeta(numeroTarjeta, compañia);
        gestorTarjetas.agregarTarjeta(tarjeta);
        this.banco.getHashTarjetas().put(numeroTarjeta, tarjeta);
        System.out.println("Tarjeta emitida exitosamente!");
        System.out.println("Numero: " + numeroTarjeta);
    }
    public void vincularTarjeta() {
        System.out.println("==== VINCULAR TARJETA ====");
        System.out.print("Ingrese el numero de tarjeta: ");
        String numeroTarjeta = sc.nextLine();

        Tarjeta tarjeta = gestorTarjetas.buscarTarjeta(numeroTarjeta);
        if(tarjeta == null) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        System.out.print("Ingrese el numero de cuenta: ");
        String numeroCuenta = sc.nextLine();
        /*
        CuentaBancaria cuenta = gestorCuentasBancarias.buscarCuenta(numeroCuenta);
        if(cuenta == null) {
            System.out.println("Error: Cuenta no encontrada.");
            return;
        }

        // Crear la vinculación
        CuentaTarjeta cuentaTarjeta = new CuentaTarjeta(tarjeta, cuenta);
        cuenta.afiliarCuenta(cuentaTarjeta);
        */
        System.out.println("Tarjeta vinculada exitosamente a la cuenta!");
    }


}

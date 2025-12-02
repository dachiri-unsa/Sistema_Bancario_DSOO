package sistema;

import gestores.GestorCuentas;
import gestores.GestorTarjetas;

import java.util.Scanner;

import entidades.concretas.CuentaBancaria;
import entidades.concretas.CuentaTarjeta;
import entidades.concretas.Tarjeta;
import entidades.enumerables.TipoPermiso;

public class MenuTarjetas {
    private Scanner sc;
    private Banco banco;
    private GestorTarjetas gestorTarjetas;
    private GestorCuentas gestorCuentas;

    public MenuTarjetas(Banco banco, Scanner sc) {
        this.banco = banco;
        this.gestorTarjetas = new GestorTarjetas();
        this.gestorCuentas = new GestorCuentas();
        this.sc = sc;
    }

    public void mostrarMenuTarjetas() {
        if (!entidades.concretas.SessionManager.getCurrentUser().getPermisos().contains(TipoPermiso.TARJ)) {
            System.out.println("No tiene permisos para acceder a este menu.");
            return;
        }
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
        } while (!opcion.equalsIgnoreCase("0"));
    }

    public void emitirTarjeta() {
        System.out.println("==== EMITIR TARJETA ====");
        System.out.print("Ingrese la compañia: ");
        String compania = sc.nextLine();
        if (compania.isEmpty()) {
            System.out.println("La compañia no puede estar vacio.");
        }
        String numeroTarjeta = gestorTarjetas.generarNumeroTarjeta();
        Tarjeta tarjeta = new Tarjeta(numeroTarjeta, compania);
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
        if (tarjeta == null) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        System.out.print("Ingrese el numero de cuenta: ");
        String numeroCuenta = sc.nextLine();

        CuentaBancaria cuenta = gestorCuentas.buscarCuenta(numeroCuenta);
        if (cuenta == null) {
            System.out.println("Error: Cuenta no encontrada.");
            return;
        }
        // Realiza la vinculacion
        CuentaTarjeta cuentaTarjeta = new CuentaTarjeta(tarjeta, cuenta);
        banco.getGestorClientes().buscarCliente(cuenta.getDniCliente()).afiliarCuenta(cuentaTarjeta);
        ;
        System.out.println("Tarjeta vinculada exitosamente a la cuenta!");
    }

}

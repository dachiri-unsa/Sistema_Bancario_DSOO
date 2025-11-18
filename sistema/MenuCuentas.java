package sistema;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.Cliente;
import entidades.CuentaBancaria;
import entidades.TipoMoneda;
import gestores.GestorCuentasBancarias;
// CuentaBancaria
public class MenuCuentas {
    private Scanner sc;
    private Banco banco;

    public MenuCuentas(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }
    public void mostrarMenuCuentas() {
        String opcion;
        do {
            System.out.println("\n==== MENU CUENTAS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Crear cuenta.");
            System.out.println("2. Consultar saldo.");
            System.out.println("3. Ver movimientos (transacciones).");
            System.out.println("0. Volver al menu principal.");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    crearCuenta();
                    break;
                case "2":
                    consultarSaldo();
                    break;
                case "3":
                    verMovimientos();
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

    public void crearCuenta() {
        System.out.println("==== CREAR CUENTA ====");
        Cliente cliente = obtenerCliente();
        if (cliente == null) {
            return;
        }
        System.out.println("Ingrese el tipo de moneda que usara.");
        System.out.println("1. Soles\n2. Dolares\n3. Euros");
        String tipoMoneda = sc.nextLine().trim();
        TipoMoneda moneda;
        if (tipoMoneda.equals("1")) {
            moneda = TipoMoneda.Soles;
        }
        else if (tipoMoneda.equals("2")) {
            moneda = TipoMoneda.Dolares;
        }
        else if (tipoMoneda.equals("3")) {
            moneda = TipoMoneda.Euros;
        }
        else {
            System.out.println("Eleccion no valida.");
            return;
        }
        CuentaBancaria cuenta = new CuentaBancaria(moneda, cliente.getDNI());
        System.out.println("EL numero de la cuenta creada sera: "+cuenta.getNumeroCuenta());
        this.banco.getHashCuentas().put(cuenta.getNumeroCuenta(), cuenta);
        cliente.agregarCuenta(cuenta);
    }

    public void consultarSaldo() {
        System.out.println("==== CONSULTAR SALDO ====");
        Cliente cliente = obtenerCliente();
        if (cliente == null) {
            return;
        }
        GestorCuentasBancarias gestor = cliente.getGestorCuentasBancarias();
        if (gestor.getCuentas().isEmpty()) {
            System.out.println("El cliente no tiene cuentas disponibles.");
            return;
        }
        String numeroCuenta;
        if(gestor.getCuentas().size() > 1) {
            System.out.println("Las cuentas del cliente: ");
            gestor.listarCuentas();
            System.out.println("Ingresar el numero de la cuenta a consultar.");
            numeroCuenta = sc.nextLine().trim();
            if (numeroCuenta.isEmpty()) {
                System.out.println("El numero de cuenta no puede estar vacio.");
                return;
            }
            if (!numeroCuenta.matches("\\d+")) {
                System.out.println("Solo debe contener números.");
            }
            for(CuentaBancaria c : gestor.getCuentas()) {
                if (c.getNumeroCuenta().equals(numeroCuenta)) {
                    System.out.println("El saldo disponible en esa cuenta es: "+c.getSaldo());
                    return;
                }
            }
        }
        else {
            CuentaBancaria c = gestor.getCuentas().get(0);
            System.out.println("El saldo disponible de la cuenta "+c.getNumeroCuenta()+" es: "+c.getSaldo());
            return;
        }
        System.out.println("Numero de cuenta no encontrado.");
    }
    public void verMovimientos() {
        System.out.println("==== VER MOVIMIENTOS DE CUENTA ====");
        Cliente cliente = obtenerCliente();
        if (cliente == null) {
            return;
        }
        GestorCuentasBancarias gestor = cliente.getGestorCuentasBancarias();
        if (gestor.getCuentas().isEmpty()) {
            System.out.println("El cliente no tiene cuentas disponibles.");
            return;
        }
        String numeroCuenta;
        if(gestor.getCuentas().size() > 1) {
            System.out.println("Las cuentas del cliente: ");
            gestor.listarCuentas();
            System.out.println("Ingresar el numero de la cuenta a consultar.");
            numeroCuenta = sc.nextLine().trim();
            if (numeroCuenta.isEmpty()) {
                System.out.println("El numero de cuenta no puede estar vacio.");
                return;
            }
            if (!numeroCuenta.matches("\\d+")) {
                System.out.println("Solo debe contener números.");
            }
            for(CuentaBancaria c : gestor.getCuentas()) {
                if (c.getNumeroCuenta().equals(numeroCuenta)) {
                    c.getHistorial().listarMovimientos();
                    return;
                }
            }
        }
        else {
            CuentaBancaria c = gestor.getCuentas().get(0);
            System.out.println("El saldo disponible de la cuenta "+c.getNumeroCuenta()+" es: "+c.getSaldo());
            return;
        }
        System.out.println("Numero de cuenta no encontrado.");
    }
    public Cliente obtenerCliente() {
        System.out.println("Ingrese el DNI del cliente: ");
        String dni = sc.nextLine().trim();
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacio.");
            return null;
        }
        if (!dni.matches("\\d{8}")) {
            System.out.println("El DNI debe contener exactamente 8 dígitos numéricos.");
            return null;
        }
        Cliente cliente = banco.getGestorClientes().buscarCliente(dni);
        if (cliente != null) {
            System.out.println("Cliente encontrado.");
            return cliente;
        }
        else {
            System.out.println("Cliente no encontrado.");
            return null;
        }
    }
}

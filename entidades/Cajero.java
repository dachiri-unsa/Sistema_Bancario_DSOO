package entidades;

import gestores.GestorMovimientos;
import interfaces.Funciones;

public class Cajero extends Encargado implements Funciones {

    public Cajero(String id, String nombre, String apellido, String dni, String telefono, String direccion) {
        super(id, nombre, apellido, dni, telefono, direccion);
    }

    @Override
    public void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento deposito = new Deposito(monto, "Deposito por cajero", cuenta, this);
        if (deposito.procesar()) {
            gestor.registrarMovimiento(deposito);
            System.out.println("Deposito exitoso.");
        } else {
            System.out.println("Error al procesar depósito.");
        }
    }

    @Override
    public void retirarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento retiro = new Retiro(monto, "Retiro por cajero", cuenta, this);
        if (retiro.procesar()) {
            gestor.registrarMovimiento(retiro);
            System.out.println("Retiro exitoso.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }
}

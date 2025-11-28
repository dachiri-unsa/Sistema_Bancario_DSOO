package entidades;

import gestores.GestorMovimientos;
import interfaces.Funciones;

public class Cajero implements Funciones {
    private String Id;
    private boolean disponible;

    public Cajero(String Id, boolean disponible) {
        this.Id = Id;
        this.disponible = disponible;
    }

    public boolean getDisponible() {
        return this.disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getId() {
        return this.Id;
    }

    @Override
    public void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento deposito = new Deposito(monto, "Deposito por cajero", cuenta, this.Id);
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

        Movimiento retiro = new Retiro(monto, "Retiro por cajero", cuenta, this.Id);
        if (retiro.procesar()) {
            gestor.registrarMovimiento(retiro);
            System.out.println("Retiro exitoso.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }
}

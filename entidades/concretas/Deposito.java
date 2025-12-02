package entidades.concretas;

import entidades.abstractas.Movimiento;

public class Deposito extends Movimiento {

    public Deposito(double monto, String descripcion, CuentaBancaria origen, String encargado) {
        super(monto, descripcion, origen, encargado);
    }

    @Override
    public boolean procesar() {
        origen.incrementarSaldo(monto);
        origen.getHistorial().agregarMovimiento(this);
        return true;
    }

    @Override
    public String toString() {
        return "DEPOSITO: " + fecha + " - " + descripcion + " +S/ " + monto;
    }
}

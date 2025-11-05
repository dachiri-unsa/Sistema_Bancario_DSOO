package entidades;

import movimientos.HistorialMovimientos;

public class CuentaBancaria {
    private static int contadorCuentas = 1000;
    private String numeroCuenta;
    private TipoMoneda moneda;
    private double saldo;
    private HistorialMovimientos historial;

    public CuentaBancaria(TipoMoneda moneda) {
        this.numeroCuenta = ""+contadorCuentas;
        contadorCuentas++;
        this.moneda = moneda;
        this.saldo = 0;
        this.historial = new HistorialMovimientos();
    }

    public String getNumeroCuenta() { return this.numeroCuenta; }

    public TipoMoneda getTipoMoneda() { return this.moneda; }

    public double getSaldo() { return this.saldo; }

    public HistorialMovimientos getHistorial() { return this.historial; }

    // Considerar agregar al historia de movimientos de la cuenta (no cliente) XD
    public void incrementarSaldo(double monto) {
        this.numeroCuenta += monto;
    }
    public boolean decrementarSaldo(double monto) {
        if (monto < this.saldo) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }
}

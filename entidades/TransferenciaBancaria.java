package entidades;

public class TransferenciaBancaria extends Movimiento {
    private CuentaBancaria destino;

    public TransferenciaBancaria(double monto, String descripcion, CuentaBancaria origen, String encargado,
            CuentaBancaria destino) {
        super(monto, descripcion, origen, encargado);
        this.destino = destino;
    }

    public CuentaBancaria getDestino() {
        return this.destino;
    }

    @Override
    public boolean procesar() {
        if (origen.getSaldo() >= monto) {
            origen.decrementarSaldo(monto);
            destino.incrementarSaldo(monto);
            origen.getHistorial().agregarMovimiento(this);
            destino.getHistorial().agregarMovimiento(this);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TRANSFERENCIA: " + fecha + " - " + descripcion + " -S/ " + monto +
                " → Cuenta destino: " + destino.getNumeroCuenta();
    }
}

package entidades;

public class Retiro extends Movimiento {
    public Retiro(double monto, String descripcion, CuentaBancaria origen, Encargado encargado) {
        super(monto, descripcion, origen, encargado);
    }

    @Override
    public boolean procesar() {
        if (origen.getSaldo() >= monto) {
            origen.decrementarSaldo(monto);
            origen.getHistorial().agregarMovimiento(this);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RETIRO: " + fecha + " - " + descripcion + " -S/ " + monto ;
    }
}

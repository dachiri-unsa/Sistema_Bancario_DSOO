package entidades;

import java.time.LocalDateTime;

public abstract class Movimiento {
    protected double monto;
    protected String descripcion;
    protected CuentaBancaria origen;
    protected LocalDateTime fecha;
    protected String encargado;

    public Movimiento(double monto, String descripcion, CuentaBancaria origen, String encargado) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.origen = origen;
        this.fecha = LocalDateTime.now();
        this.encargado = encargado;
    }

    public String getEncargado() {
        return encargado;
    }

    public double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CuentaBancaria getOrigen() {
        return origen;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public abstract boolean procesar();

    @Override
    public String toString() {
        return "[" + fecha + "] " + descripcion + " - S/ " + monto;
    }
}

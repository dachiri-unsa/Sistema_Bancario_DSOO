package entidades;

public class CuentaTarjeta {
    private Tarjeta tarjeta;
    private CuentaBancaria cuenta;

    public CuentaTarjeta(Tarjeta tarjeta, CuentaBancaria cuenta) {
        this.tarjeta = tarjeta;
        this.cuenta = cuenta;
    }
}
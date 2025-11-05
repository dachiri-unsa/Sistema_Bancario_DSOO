package entidades;

import java.util.ArrayList;
import java.util.HashMap;

public class Tarjeta {
    protected String numeroTarjeta;
    protected String compañia;
    protected HashMap<TipoMoneda,CuentaBancaria> mapCuentasMoneda;

    public Tarjeta(String numeroTarjeta, String compañia) {
        this.numeroTarjeta = numeroTarjeta;
        this.compañia = compañia;
    }

    //public boolean afiliarCuenta(CuentaBancaria cuenta) {
    public void afiliarCuenta(CuentaBancaria cuenta) {
        mapCuentasMoneda.put(cuenta.getTipoMoneda(),cuenta);
    }
    public CuentaBancaria getCuentaMoneda(TipoMoneda moneda) {
        return mapCuentasMoneda.get(moneda);
    }
    public ArrayList<CuentaBancaria> getCuentasAfiliadas() {
        // Esto transforma los valores del HashMap a un ArrayList.
        return new ArrayList<>(this.mapCuentasMoneda.values());
    }
}

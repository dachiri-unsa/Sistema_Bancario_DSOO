package entidades;

import java.util.ArrayList;
import gestores.GestorCuentasBancarias;
import gestores.GestorTarjetas;

public class Cliente extends Persona {
    private GestorCuentasBancarias gestorCuentas;
    private GestorTarjetas gestorTarjetas;
    private ArrayList<CuentaTarjeta> listaCuentaTarjeta;

    public Cliente(String nombre,String apellido,String dni,String telefono, String direccion) {
        super(nombre, apellido, dni, telefono, direccion);
        this.gestorCuentas = new GestorCuentasBancarias();
        this.gestorTarjetas = new GestorTarjetas();
        this.listaCuentaTarjeta = new ArrayList<>();
    }
    
    public GestorCuentasBancarias getGestorCuentasBancarias() { return gestorCuentas; }
    public GestorTarjetas getGestorTarjetas() { return gestorTarjetas; }

    public void agregarCuenta(CuentaBancaria cuenta) {
        gestorCuentas.agregarCuenta(cuenta);
    }
    public void agregarTarjeta(Tarjeta tarjeta) {
        gestorTarjetas.agregarTarjeta(tarjeta);
    }
    public void afiliarCuenta(CuentaTarjeta cuentaTarjeta) {
        listaCuentaTarjeta.add(cuentaTarjeta);
    }

    public String toString() {
        return "Nombre completo: "+apellido+", "+nombre+
            "\nDNI: "+dni+". Telefono: "+telefono;
    }
}

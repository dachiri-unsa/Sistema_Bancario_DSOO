package entidades;

import gestores.GestorCuentasBancarias;

public class Cliente extends Persona {
    private GestorCuentasBancarias gestorCuentas;

    public Cliente(String nombre,String apellido,String dni,String telefono, String direccion) {
        super(nombre, apellido, dni, telefono, direccion);
        this.gestorCuentas = new GestorCuentasBancarias();
    }
    
    public GestorCuentasBancarias getGestorCuentasBancarias() { return gestorCuentas; }

    public void agregarCuenta(CuentaBancaria cuenta) {
        gestorCuentas.agregarCuenta(cuenta);
    }
    public String toString() {
        return "Nombre completo: "+apellido+", "+nombre+
            "\nDNI: "+dni+". Telefono: "+telefono;
    }
}

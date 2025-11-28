package entidades;

import java.util.ArrayList;
import java.util.List;

import gestores.GestorCuentasBancarias;
import gestores.GestorPermisos;
import gestores.GestorTarjetas;

public class Cliente extends Usuario {
    private GestorCuentasBancarias gestorCuentas;
    private GestorTarjetas gestorTarjetas;
    private ArrayList<CuentaTarjeta> listaCuentaTarjeta;

    public Cliente(String nombre, String apellido, String dni, String telefono, String direccion, String nombreUsuario,
            String contrasenia, boolean estado) {
        super(nombre, apellido, dni, telefono, direccion, nombreUsuario, contrasenia, estado);
        this.gestorCuentas = new GestorCuentasBancarias();
        this.gestorTarjetas = new GestorTarjetas();
        this.listaCuentaTarjeta = new ArrayList<>();
    }

    public Cliente(Persona persona, String nombreUsuario, String contrasenia, boolean estado) {
        super(persona.getNombre(), persona.getApellido(), persona.getDNI(), persona.getTelefono(),
                persona.getDireccion(), nombreUsuario, contrasenia, estado);
        this.gestorCuentas = new GestorCuentasBancarias();
        this.gestorTarjetas = new GestorTarjetas();
        this.listaCuentaTarjeta = new ArrayList<>();
    }

    // Cliente sin cuenta
    public Cliente(Persona persona) {
        super(persona);
        this.gestorCuentas = new GestorCuentasBancarias();
        this.gestorTarjetas = new GestorTarjetas();
        this.listaCuentaTarjeta = new ArrayList<>();
    }

    public GestorCuentasBancarias getGestorCuentasBancarias() {
        return gestorCuentas;
    }

    public GestorTarjetas getGestorTarjetas() {
        return gestorTarjetas;
    }

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
        return "Nombre completo: " + apellido + ", " + nombre +
                "\nDNI: " + dni + ". Telefono: " + telefono;
    }

    @Override
    public List<String> getPermisos() {
        return GestorPermisos.getPermisosByRol("Cliente");
    }

    @Override
    public TipoRol getTipoRol() {
        return TipoRol.Cliente;
    }
}

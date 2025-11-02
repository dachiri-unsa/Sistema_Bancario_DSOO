package sistema;

import java.util.HashMap;
import gestores.*;
import entidades.CuentaBancaria;
import entidades.Tarjeta;

public class Banco {
    private HashMap<String,CuentaBancaria> hashCuentas;
    private HashMap<String,Tarjeta> hashTarjetas;
    private GestorMovimientos gestorMovimientos;
    private GestorClientes gestorClientes;
    private GestorEmpleados gestorEmpleados;
    private GestorCajeros gestorCajeros;

    public Banco() {
        this.hashCuentas = new HashMap<>();
        this.hashTarjetas = new HashMap<>();
        this.gestorMovimientos = new GestorMovimientos();
        this.gestorClientes = new GestorClientes();
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorCajeros = new GestorCajeros();
    }
    public HashMap<String,CuentaBancaria> getHashCuentas() { return this.hashCuentas; }
    public HashMap<String,Tarjeta> getHashTarjetas() { return this.hashTarjetas; }
    public GestorMovimientos getGestorMovimientos() { return this.gestorMovimientos; }
    public GestorClientes getGestorClientes() { return this.gestorClientes; }
    public GestorEmpleados getGestorEmpleados() { return this.gestorEmpleados; }
    public GestorCajeros getGestorCajeros() { return this.gestorCajeros; }
}

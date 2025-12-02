package sistema;

import entidades.abstractas.Movimiento;
import entidades.concretas.Administrador;
import entidades.concretas.Cajero;
import entidades.concretas.Cliente;
import entidades.concretas.CuentaBancaria;
import entidades.concretas.Empleado;
import entidades.concretas.Tarjeta;
import entidades.concretas.Usuario;
import gestores.GestorAdministradores;
import gestores.GestorCajeros;
import gestores.GestorClientes;
import gestores.GestorCuentas;
import gestores.GestorEmpleados;
import gestores.GestorMovimientos;
import gestores.GestorTarjetas;
import gestores.GestorUsuarios;
import sincronizacion.SincronizacionCompartida;
import sincronizacion.SincronizadorEntidad;

public class SistemaBanco {
    private static SistemaBanco instance;
    private Banco banco;

    private final SincronizadorEntidad<Administrador> sincronizadorAdministradores;
    private final SincronizadorEntidad<Cajero> sincronizadorCajeros;
    private final SincronizadorEntidad<Cliente> sincronizadorClientes;
    private final SincronizadorEntidad<Empleado> sincronizadorEmpleados;
    private final SincronizadorEntidad<Movimiento> sincronizadorMovimientos;
    private final SincronizadorEntidad<Tarjeta> sincronizadorTarjetas;
    private final SincronizadorEntidad<CuentaBancaria> sincronizadorCuentas;
    private final SincronizadorEntidad<Usuario> sincronizadorUsuarios;

    private SistemaBanco() {
        this.sincronizadorAdministradores = new SincronizadorEntidad<>();
        this.sincronizadorCajeros = new SincronizadorEntidad<>();
        this.sincronizadorClientes = new SincronizadorEntidad<>();
        this.sincronizadorEmpleados = new SincronizadorEntidad<>();
        this.sincronizadorMovimientos = new SincronizadorEntidad<>();
        this.sincronizadorTarjetas = new SincronizadorEntidad<>();
        this.sincronizadorCuentas = new SincronizadorEntidad<>();
        this.sincronizadorUsuarios = new SincronizadorEntidad<>();

        this.banco = new Banco(
                sincronizadorAdministradores,
                sincronizadorCajeros,
                sincronizadorClientes,
                sincronizadorEmpleados,
                sincronizadorMovimientos,
                sincronizadorTarjetas,
                sincronizadorCuentas,
                sincronizadorUsuarios);

        this.banco.inicializarDatos();
    }

    public static synchronized SistemaBanco getInstance() {
        if (instance == null) {
            instance = new SistemaBanco();
        }
        return instance;
    }

    public Banco getBanco() {
        return banco;
    }

    public GestorAdministradores getGestorAdministradores() {
        return banco.getGestorAdministradores();
    }

    public GestorCajeros getGestorCajeros() {
        return banco.getGestorCajeros();
    }

    public GestorClientes getGestorClientes() {
        return banco.getGestorClientes();
    }

    public GestorEmpleados getGestorEmpleados() {
        return banco.getGestorEmpleados();
    }

    public GestorMovimientos getGestorMovimientos() {
        return banco.getGestorMovimientos();
    }

    public GestorTarjetas getGestorTarjetas() {
        return banco.getGestorTarjetas();
    }

    public GestorCuentas getGestorCuentas() {
        return banco.getGestorCuentas();
    }

    public GestorUsuarios getGestorUsuarios() {
        return banco.getGestorUsuarios();
    }

    public void notificarCambios() {
        SincronizacionCompartida.notificarListeners();
    }
}

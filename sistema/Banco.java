package sistema;

import java.util.HashMap;
import java.util.List;

import gestores.*;
import entidades.*;

public class Banco {
    private HashMap<String, CuentaBancaria> hashCuentas;
    private HashMap<String, Tarjeta> hashTarjetas;
    private GestorMovimientos gestorMovimientos;
    private GestorClientes gestorClientes;
    private GestorEmpleados gestorEmpleados;
    private GestorCajeros gestorCajeros;
    private GestorUsuario gestorUsuario;
    private GestorPermisos gestorPermisos;
    private GestorAdministradores gestorAdministradores;

    public Banco() {
        this.hashCuentas = new HashMap<>();
        this.hashTarjetas = new HashMap<>();
        this.gestorMovimientos = new GestorMovimientos();
        this.gestorClientes = new GestorClientes();
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorCajeros = new GestorCajeros();
        this.gestorUsuario = new GestorUsuario();
        this.gestorPermisos = new GestorPermisos();
        this.gestorAdministradores = new GestorAdministradores();
    }

    public HashMap<String, CuentaBancaria> getHashCuentas() {
        return this.hashCuentas;
    }

    public HashMap<String, Tarjeta> getHashTarjetas() {
        return this.hashTarjetas;
    }

    public GestorMovimientos getGestorMovimientos() {
        return this.gestorMovimientos;
    }

    public GestorClientes getGestorClientes() {
        return this.gestorClientes;
    }

    public GestorEmpleados getGestorEmpleados() {
        return this.gestorEmpleados;
    }

    public GestorCajeros getGestorCajeros() {
        return this.gestorCajeros;
    }

    public GestorUsuario getGestorUsuario() {
        return this.gestorUsuario;
    }

    public GestorPermisos getGestorPermisos() {
        return this.gestorPermisos;
    }

    public GestorAdministradores getGestorAdministradores() {
        return this.gestorAdministradores;
    }

    public void inicializarDatos() {
        inicializarPermisos();
        inicializarCajeros();
        inicializarUsuarios();
        inicializarAdministradores();
        inicializarEmpleados();
        inicializarClientes();
        inicializarCuentas();
        inicializarTarjetas();
        inicializarMovimientos();
    }

    private void inicializarPermisos() {
        gestorPermisos.agregarPermisos(List.of("TARJ", "CUEN", "MOVI", "USUA", "CLIE", "EMPL", "ADMI", "PERM", "CAJE"));
        gestorPermisos.modificarPermisosByRol("Administrador",
                List.of("TARJ", "CUEN", "MOVI", "USUA", "CLIE", "EMPL", "ADMI", "PERM", "CAJE"));
        gestorPermisos.modificarPermisosByRol("Empleado", List.of("TARJ", "CUEN", "MOVI", "CLIE"));
        gestorPermisos.modificarPermisosByRol("Cliente", List.of("TARJ", "CUEN", "MOVI"));
    }

    private void inicializarCajeros() {
        gestorCajeros.agregarCajero(new Cajero("C001", true));
        gestorCajeros.agregarCajero(new Cajero("C002", true));
        gestorCajeros.agregarCajero(new Cajero("C003", true));
        gestorCajeros.agregarCajero(new Cajero("C004", false));
    }

    private void inicializarUsuarios() {
        Usuario u1 = new Administrador("Juan", "Perez", "10203040", "999888777", "Av. Javier Prado 123, Lima", "admin",
                util.PasswordUtil.hashPassword("1234"), true);
        Usuario u2 = new Empleado("Maria", "Fernandez", "40506070", "987654321", "Calle Los Pinos 456, Arequipa",
                "maria", util.PasswordUtil.hashPassword("maria"), "E002", true);
        Usuario u3 = new Cliente("Ana", "Torres", "12345678", "987654321", "Av. Arequipa 101, Lima", "anaUser",
                util.PasswordUtil.hashPassword("anaPass"), true);

        gestorUsuario.agregarUsuario(u1);
        gestorUsuario.agregarUsuario(u2);
        gestorUsuario.agregarUsuario(u3);

        gestorAdministradores.agregarAdministrador((Administrador) u1);
        gestorEmpleados.agregarEmpleado((Empleado) u2);
        gestorClientes.agregarCliente((Cliente) u3);
    }

    private void inicializarAdministradores() {
        Administrador admin2 = new Administrador("Carlos", "Soto", "11223344", "998877665", "Av. Sol 123", "admin2",
                util.PasswordUtil.hashPassword("admin2"), true);
        gestorAdministradores.agregarAdministrador(admin2);
        gestorUsuario.agregarUsuario(admin2);

        Persona pAdmin3 = new Persona("Luisa", "Mendez", "55667788", "911223344", "Jr. Luna 456");
        Administrador admin3 = new Administrador(pAdmin3, "admin3", util.PasswordUtil.hashPassword("admin3"), true);
        gestorAdministradores.agregarAdministrador(admin3);
        gestorUsuario.agregarUsuario(admin3);
    }

    private void inicializarEmpleados() {
        Empleado emp3 = new Empleado("Pedro", "Castillo", "99887766", "911222333", "Jr. Ayacucho 808, Cajamarca",
                "pedro", util.PasswordUtil.hashPassword("pedro"), "E003", true);
        gestorEmpleados.agregarEmpleado(emp3);
        gestorUsuario.agregarUsuario(emp3);

        Persona pEmp4 = new Persona("Lucia", "Reyes", "77665544", "999000111", "Av. Grau 202");
        Empleado emp4 = new Empleado(pEmp4, "lucia", util.PasswordUtil.hashPassword("lucia"), "E004", true);
        gestorEmpleados.agregarEmpleado(emp4);
        gestorUsuario.agregarUsuario(emp4);
    }

    private void inicializarClientes() {
        Cliente cMatias = new Cliente("Matias", "Lopez", "12345678", "987654321", "Av. Arequipa 101, Lima", "matias",
                util.PasswordUtil.hashPassword("matias"), true);
        gestorClientes.agregarCliente(cMatias);
        gestorUsuario.agregarUsuario(cMatias);

        Persona pLuis = new Persona("Luis", "Gómez", "87654321", "912345678", "Av. Larco 202, Trujillo");
        Cliente cLuis = new Cliente(pLuis, "luisUser", util.PasswordUtil.hashPassword("luisPass"), true);
        gestorClientes.agregarCliente(cLuis);
        gestorUsuario.agregarUsuario(cLuis);

        Persona pElena = new Persona("Elena", "Chavez", "45678901", "999111222", "Calle Real 303, Huancayo");
        Cliente cElena = new Cliente(pElena);
        gestorClientes.agregarCliente(cElena);
        gestorUsuario.agregarUsuario(cElena);
    }

    private void inicializarCuentas() {
        Cliente cAna = gestorClientes.buscarCliente("12345678");
        if (cAna != null) {
            CuentaBancaria c1 = new CuentaBancaria(TipoMoneda.Soles, cAna.getDNI());
            c1.incrementarSaldo(1500);
            cAna.agregarCuenta(c1);
            hashCuentas.put(c1.getNumeroCuenta(), c1);
        }

        Cliente cLuis = gestorClientes.buscarCliente("87654321");
        if (cLuis != null) {
            CuentaBancaria c2 = new CuentaBancaria(TipoMoneda.Dolares, cLuis.getDNI());
            c2.incrementarSaldo(2000);
            cLuis.agregarCuenta(c2);
            hashCuentas.put(c2.getNumeroCuenta(), c2);
        }

        Cliente cElena = gestorClientes.buscarCliente("45678901");
        if (cElena != null) {
            CuentaBancaria c3 = new CuentaBancaria(TipoMoneda.Soles, cElena.getDNI());
            c3.incrementarSaldo(5000);
            cElena.agregarCuenta(c3);
            hashCuentas.put(c3.getNumeroCuenta(), c3);

            CuentaBancaria c4 = new CuentaBancaria(TipoMoneda.Dolares, cElena.getDNI());
            c4.incrementarSaldo(100);
            cElena.agregarCuenta(c4);
            hashCuentas.put(c4.getNumeroCuenta(), c4);
        }
    }

    private void inicializarTarjetas() {
        Cliente cAna = gestorClientes.buscarCliente("12345678");
        if (cAna != null && !cAna.getGestorCuentasBancarias().getCuentas().isEmpty()) {
            Tarjeta t1 = new Debito("1111222233334444", "Visa", 0.0);
            t1.afiliarCuenta(cAna.getGestorCuentasBancarias().getCuentas().get(0));
            cAna.agregarTarjeta(t1);
            hashTarjetas.put(t1.getNumeroTarjeta(), t1);
        }

        Cliente cLuis = gestorClientes.buscarCliente("87654321");
        if (cLuis != null && !cLuis.getGestorCuentasBancarias().getCuentas().isEmpty()) {
            Tarjeta t2 = new Credito("5555666677778888", "Mastercard");
            t2.afiliarCuenta(cLuis.getGestorCuentasBancarias().getCuentas().get(0));
            cLuis.agregarTarjeta(t2);
            hashTarjetas.put(t2.getNumeroTarjeta(), t2);
        }

        Cliente cElena = gestorClientes.buscarCliente("45678901");
        if (cElena != null && cElena.getGestorCuentasBancarias().getCuentas().size() >= 2) {
            Tarjeta t3 = new Debito("9999888877776666", "Visa", 0.0);
            t3.afiliarCuenta(cElena.getGestorCuentasBancarias().getCuentas().get(0));
            cElena.agregarTarjeta(t3);
            hashTarjetas.put(t3.getNumeroTarjeta(), t3);

            Tarjeta t4 = new Credito("1234123412341234", "Amex");
            t4.afiliarCuenta(cElena.getGestorCuentasBancarias().getCuentas().get(1));
            cElena.agregarTarjeta(t4);
            hashTarjetas.put(t4.getNumeroTarjeta(), t4);
        }
    }

    private void inicializarMovimientos() {
        Cliente cAna = gestorClientes.buscarCliente("12345678");
        if (cAna != null && !cAna.getGestorCuentasBancarias().getCuentas().isEmpty()) {
            CuentaBancaria c = cAna.getGestorCuentasBancarias().getCuentas().get(0);
            Movimiento m1 = new Deposito(500, "Bono Bienvenida", c, "SISTEMA");
            m1.procesar();
            gestorMovimientos.registrarMovimiento(m1);
        }

        Cliente cLuis = gestorClientes.buscarCliente("87654321");
        if (cLuis != null && !cLuis.getGestorCuentasBancarias().getCuentas().isEmpty()) {
            CuentaBancaria c = cLuis.getGestorCuentasBancarias().getCuentas().get(0);
            Movimiento m2 = new Retiro(200, "Pago Servicios", c, "SISTEMA");
            m2.procesar();
            gestorMovimientos.registrarMovimiento(m2);
        }

        Cliente cElena = gestorClientes.buscarCliente("45678901");
        if (cElena != null && cAna != null) {
            CuentaBancaria origen = cElena.getGestorCuentasBancarias().getCuentas().get(0);
            CuentaBancaria destino = cAna.getGestorCuentasBancarias().getCuentas().get(0);
            Movimiento m3 = new TransferenciaBancaria(100, "Prestamo", origen, "SISTEMA", destino);
            m3.procesar();
            gestorMovimientos.registrarMovimiento(m3);
        }
    }
}

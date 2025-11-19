package sistema;

import java.util.HashMap;
import gestores.*;
import entidades.*;
import java.util.List;

public class Banco {
    private HashMap<String,CuentaBancaria> hashCuentas;
    private HashMap<String,Tarjeta> hashTarjetas;
    private GestorMovimientos gestorMovimientos;
    private GestorClientes gestorClientes;
    private GestorEmpleados gestorEmpleados;
    private GestorCajeros gestorCajeros;
    private GestorUsuario gestorUsuario;
    private GestorRoles gestorRoles;

    public Banco() {
        this.hashCuentas = new HashMap<>();
        this.hashTarjetas = new HashMap<>();
        this.gestorMovimientos = new GestorMovimientos();
        this.gestorClientes = new GestorClientes();
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorCajeros = new GestorCajeros();
        this.gestorUsuario = new GestorUsuario();
        this.gestorRoles = new GestorRoles();
    }

    public HashMap<String,CuentaBancaria> getHashCuentas() { return this.hashCuentas; }
    public HashMap<String,Tarjeta> getHashTarjetas() { return this.hashTarjetas; }
    public GestorMovimientos getGestorMovimientos() { return this.gestorMovimientos; }
    public GestorClientes getGestorClientes() { return this.gestorClientes; }
    public GestorEmpleados getGestorEmpleados() { return this.gestorEmpleados; }
    public GestorCajeros getGestorCajeros() { return this.gestorCajeros; }
    public GestorUsuario getGestorUsuario() { return this.gestorUsuario; }
    public GestorRoles getGestorRoles() { return this.gestorRoles; }

    public void inicializarDatos() {
        Cliente cliente1 = new Cliente("Ana", "Torres", "12345678", "987654321", "Lima");
        Cliente cliente2 = new Cliente("Luis", "Gómez", "87654321", "912345678", "Arequipa");

        //Las siguientes 7 lineas son procesos en paralelo cuando se crea una nueva cuenta
        //Se puede ver en MenuCuentas linea 72 y 75
        CuentaBancaria cuentaAnaSoles = new CuentaBancaria(TipoMoneda.Soles,cliente1.getDNI());
        cuentaAnaSoles.incrementarSaldo(1500);
        CuentaBancaria cuentaLuisDolares = new CuentaBancaria(TipoMoneda.Dolares,cliente2.getDNI());
        cuentaLuisDolares.incrementarSaldo(2000);

        cliente1.agregarCuenta(cuentaAnaSoles);
        cliente2.agregarCuenta(cuentaLuisDolares);

        hashCuentas.put(cuentaAnaSoles.getNumeroCuenta(), cuentaAnaSoles);
        hashCuentas.put(cuentaLuisDolares.getNumeroCuenta(), cuentaLuisDolares);

        Tarjeta tarjetaAna = new Debito("1111222233334444", "Visa", 0.02);
        Tarjeta tarjetaLuis = new Credito("5555666677778888", "Mastercard");

        tarjetaAna.afiliarCuenta(cuentaAnaSoles);
        tarjetaLuis.afiliarCuenta(cuentaLuisDolares);

        cliente1.agregarTarjeta(tarjetaAna);
        cliente2.agregarTarjeta(tarjetaLuis);

        hashTarjetas.put(tarjetaAna.getNumeroTarjeta(), tarjetaAna);
        hashTarjetas.put(tarjetaLuis.getNumeroTarjeta(), tarjetaLuis);

        gestorClientes.agregarCliente(cliente1);
        gestorClientes.agregarCliente(cliente2);

        // empleado1 id: "E001"   empleado2 id "E002"
        Empleado empleado1 = new Empleado("E001", "Carlos", "Mendoza", "45678901", "999111222", "Lima");
        Empleado empleado2 = new Empleado("E002", "Maria", "Fernandez", "45678902", "999333444", "Arequipa");

        gestorEmpleados.agregarEmpleado(empleado1);
        gestorEmpleados.agregarEmpleado(empleado2);

        Cajero cajero1 = new Cajero("C001", "Cajero", "Central", "00011122", "0800111222", "Lima", true);
        Cajero cajero2 = new Cajero("C002", "Cajero", "Sur", "00011123", "0800111333", "Arequipa", true);

        gestorCajeros.agregarCajero(cajero1);
        gestorCajeros.agregarCajero(cajero2);

        Movimiento depositoAna = new Deposito(500, "Deposito inicial", cuentaAnaSoles, empleado1);
        depositoAna.procesar();

        Movimiento retiroLuis = new Retiro(300, "Retiro por cajero", cuentaLuisDolares, cajero1);
        retiroLuis.procesar();

        Movimiento transferencia = new TransferenciaBancaria(200, "Pago a Ana", cuentaLuisDolares, empleado2, cuentaAnaSoles);
        transferencia.procesar();

        gestorMovimientos.registrarMovimiento(depositoAna);
        gestorMovimientos.registrarMovimiento(retiroLuis);
        gestorMovimientos.registrarMovimiento(transferencia);

        Usuario usuarioAna = new Usuario("anaUser", "anaPass", cliente1.getDNI());
        gestorUsuario.agregarUsuario(usuarioAna);
        GestorRoles rolesAna = new GestorRoles();
        rolesAna.agregarRol(new UsuarioRol("anaUser", List.of(TipoRol.Cliente)));

        Usuario usuarioLuis = new Usuario("luisUser", "luisPass", cliente2.getDNI());
        gestorUsuario.agregarUsuario(usuarioLuis);
        rolesAna.agregarRol(new UsuarioRol("luisUser", List.of(TipoRol.Cliente)));

        Usuario usuarioCarlos = new Usuario("admin", "1234", empleado1.getDNI());
        gestorUsuario.agregarUsuario(usuarioCarlos);
        rolesAna.agregarRol(new UsuarioRol("admin", List.of(TipoRol.Administrador, TipoRol.Cliente)));

        Usuario usuarioMaria = new Usuario("asistente", "123", empleado2.getDNI());
        gestorUsuario.agregarUsuario(usuarioMaria);
        rolesAna.agregarRol(new UsuarioRol("asistente", List.of(TipoRol.Asistente)));
    }

}

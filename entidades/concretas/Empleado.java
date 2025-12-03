package entidades.concretas;

import java.util.List;

import entidades.abstractas.Movimiento;
import entidades.enumerables.TipoMoneda;
import entidades.enumerables.TipoRol;
import entidades.enumerables.TipoPermiso;
import gestores.GestorMovimientos;
import gestores.GestorPermisos;
import interfaces.Funciones;

public class Empleado extends Usuario implements Funciones {

    private String Id;
    private static int contadorID = 0;

    public Empleado(String nombre, String apellido, String dni, String telefono, String direccion,
            String nombreUsuario, String contrasenia, boolean estado) {
        super(nombre, apellido, dni, telefono, direccion, nombreUsuario, contrasenia, estado);
        this.Id = generarID();
    }
    private String generarID() {
        return String.format("EMP-%04d", contadorID++);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Empleado(Persona persona, String nombreUsuario, String contrasenia, boolean estado) {
        super(persona.getNombre(), persona.getApellido(), persona.getDNI(), persona.getTelefono(),
                persona.getDireccion(), nombreUsuario, contrasenia, estado);
        this.Id = generarID();
    }

    @Override
    public void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento deposito = new Deposito(monto, "Deposito por empleado", cuenta, this.Id);
        if (deposito.procesar()) {
            gestor.registrarMovimiento(deposito);
            System.out.println("Deposito exitoso.");
        } else {
            System.out.println("Error al procesar depósito.");
        }
    }

    @Override
    public void retirarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento retiro = new Retiro(monto, "Retiro por empleado", cuenta, this.Id);
        if (retiro.procesar()) {
            gestor.registrarMovimiento(retiro);
            System.out.println("Retiro exitoso.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }

    public void depositarConCuenta(CuentaBancaria cuenta, double monto, GestorMovimientos gestor) {
        Movimiento deposito = new Deposito(monto, "Deposito directo", cuenta, this.Id);
        if (deposito.procesar()) {
            gestor.registrarMovimiento(deposito);
            System.out.println("Deposito exitoso.");
        } else {
            System.out.println("Error al procesar depósito.");
        }
    }

    public void retirarConCuenta(CuentaBancaria cuenta, double monto, GestorMovimientos gestor) {
        Movimiento retiro = new Retiro(monto, "Retiro directo", cuenta, this.Id);
        if (retiro.procesar()) {
            gestor.registrarMovimiento(retiro);
            System.out.println("Retiro exitoso.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }

    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto, GestorMovimientos gestor) {
        Movimiento transferencia = new TransferenciaBancaria(monto, "Transferencia por empleado", origen, this.Id,
                destino);
        if (transferencia.procesar()) {
            gestor.registrarMovimiento(transferencia);
            System.out.println("Transferencia exitosa.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }

    @Override
    public List<TipoPermiso> getPermisos() {
        return GestorPermisos.getPermisosByRol("Empleado");
    }

    @Override
    public TipoRol getTipoRol() {
        return TipoRol.Empleado;
    }
}

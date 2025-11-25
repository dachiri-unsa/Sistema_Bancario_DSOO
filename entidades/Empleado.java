package entidades;

import gestores.GestorMovimientos;
import interfaces.Funciones;

public class Empleado extends Encargado implements Funciones {

    public Empleado(String id, String nombre, String apellido, String dni, String telefono, String direccion) {
        super(id, nombre, apellido, dni, telefono, direccion);
    }

    @Override
    public void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento deposito = new Deposito(monto, "Deposito por empleado", cuenta, this);
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

        Movimiento retiro = new Retiro(monto, "Retiro por empleado", cuenta, this);
        if (retiro.procesar()) {
            gestor.registrarMovimiento(retiro);
            System.out.println("Retiro exitoso.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }

    public void depositarConCuenta(CuentaBancaria cuenta, double monto, GestorMovimientos gestor) {
        Movimiento deposito = new Deposito(monto, "Deposito directo", cuenta, this);
        if (deposito.procesar()) {
            gestor.registrarMovimiento(deposito);
            System.out.println("Deposito exitoso.");
        } else {
            System.out.println("Error al procesar depósito.");
        }
    }

    public void retirarConCuenta(CuentaBancaria cuenta, double monto, GestorMovimientos gestor) {
        Movimiento retiro = new Retiro(monto, "Retiro directo", cuenta, this);
        if (retiro.procesar()) {
            gestor.registrarMovimiento(retiro);
            System.out.println("Retiro exitoso.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }

    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto, GestorMovimientos gestor) {
        Movimiento transferencia = new TransferenciaBancaria(monto, "Transferencia por empleado", origen, this, destino);
        if (transferencia.procesar()) {
            gestor.registrarMovimiento(transferencia);
            System.out.println("Transferencia exitosa.");
        } else {
            System.out.println("Fondos insuficientes.");
        }
    }

    @Override
    public void mostrarPermisos(){
        System.out.println("Usted es empleado sus permisos son: \\n" + //
                        "1. Gestion de tarjetas \\n" + //
                        "2. Gestion de clientes \\n" + //
                        "3. Gestion de cuentas\"");
    }
}

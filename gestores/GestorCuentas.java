package gestores;

import java.util.List;

import entidades.concretas.CuentaBancaria;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorCuentas implements Gestor<CuentaBancaria> {
    private final SincronizadorEntidad<CuentaBancaria> sincronizadorCuentas;

    public GestorCuentas(SincronizadorEntidad<CuentaBancaria> sincronizadorCuentas) {
        this.sincronizadorCuentas = sincronizadorCuentas;
    }

    public GestorCuentas() {
        this.sincronizadorCuentas = new SincronizadorEntidad<>();
    }

    @Override
    public void agregar(CuentaBancaria cuenta) {
        sincronizadorCuentas.agregar(cuenta);
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        agregar(cuenta);
    }

    @Override
    public List<CuentaBancaria> listarTodos() {
        return sincronizadorCuentas.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorCuentas.eliminar(index);
    }

    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        for (CuentaBancaria c : sincronizadorCuentas.listarTodos()) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        return null;
    }

    public void listarCuentas() {
        if (sincronizadorCuentas.listarTodos().isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        for (CuentaBancaria c : sincronizadorCuentas.listarTodos()) {
            System.out.println("- Numero de cuenta: " + c.getNumeroCuenta() + ". Saldo: " + c.getSaldo() + "("
                    + c.getTipoMoneda() + ")");
        }
    }

    public void eliminarCuenta(CuentaBancaria cuenta) {
        int index = sincronizadorCuentas.listarTodos().indexOf(cuenta);
        if (index != -1) {
            sincronizadorCuentas.eliminar(index);
        }
    }

    public List<CuentaBancaria> getCuentas() {
        return listarTodos();
    }
}

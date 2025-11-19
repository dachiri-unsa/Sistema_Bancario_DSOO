package gestores;

import entidades.Cliente;
import entidades.CuentaBancaria;
import java.util.ArrayList;

public class GestorCuentasBancarias {
    private ArrayList<CuentaBancaria> cuentas;

    public GestorCuentasBancarias() {
        this.cuentas = new ArrayList<>();
    }
    public ArrayList<CuentaBancaria> getCuentas() { return this.cuentas; }
    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }
    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        for (CuentaBancaria c : cuentas) {
            if (c.getNumeroCuenta() == numeroCuenta) {
                return c;
            }
        }
        return null;
    }
    public void listarCuentas() {
        for (CuentaBancaria c : cuentas) {
            System.out.println("- Numero de cuenta: "+c.getNumeroCuenta()+". Saldo: "+c.getSaldo()+"("+c.getTipoMoneda()+")");
        }
    }
    public void eliminarCuenta (CuentaBancaria cuenta) {
        cuentas.remove(cuenta);
    }
}

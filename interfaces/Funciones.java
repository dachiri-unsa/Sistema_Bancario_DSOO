package interfaces;

import entidades.Tarjeta;
import entidades.CuentaBancaria;
import gestores.GestorMovimientos;

public interface Funciones {
    void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor);
    void retirarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor);
}

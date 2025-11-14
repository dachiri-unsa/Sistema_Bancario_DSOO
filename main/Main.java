package main;
import sistema.*;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.inicializarDatos();
        MenuSistema menu = new MenuSistema(banco);
        char continuar = menu.bienvenida();
        if (continuar == 's' || continuar == 'S') menu.mostrarMenuSistema();
        else menu.despedida();
    }
}
package main;
import sistema.*;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        MenuSistema menu = new MenuSistema(banco);
        menu.mostrarMenuPrincipal();
    }
}
package sistema;

import java.util.Scanner;

public class MenuPrincipal {
    private Banco banco;
    private MenuSistema menuSistema;
    private MenuClientes menuClientes;
    private MenuCuentas menuCuentas;
    private MenuTarjetas menuTarjetas;
    private Scanner sc;

    public MenuPrincipal(Banco banco, MenuSistema menuSistema, Scanner sc) {
        this.banco = banco;
        this.menuSistema = menuSistema;
        this.menuClientes = new MenuClientes(banco, sc);
        this.menuCuentas = new MenuCuentas(banco, sc);
        this.menuTarjetas = new MenuTarjetas(banco, sc);
        this.sc = sc;
    }

    public void mostrarMenuPrincipal() {
        String opcion;
        do {
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Gestion de clientes.");
            System.out.println("2. Gestion de cuentas.");
            System.out.println("3. Gestion de tarjetas.");
            System.out.println("0. Cerrar Secion.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    MenuSistema.limpiarPantalla();
                    menuClientes.mostrarMenuClientes();
                    break;
                case "2":
                    MenuSistema.limpiarPantalla();
                    menuCuentas.mostrarMenuCuentas();
                    break;
                case "3":
                    MenuSistema.limpiarPantalla();
                    menuTarjetas.mostrarMenuTarjetas();
                    break;
                case "0":
                    MenuSistema.limpiarPantalla();
                    menuSistema.LogOut();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        while (!opcion.equalsIgnoreCase("0"));
    }

    // faltan metodo para mostrar diferente opciones en el MenuPrincipal segun el SessionManager este activo y sus roles


}

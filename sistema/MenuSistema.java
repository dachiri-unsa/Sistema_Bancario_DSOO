package sistema;

import java.util.Scanner;

public class MenuSistema {
    private Banco banco;
    private MenuClientes menuClientes;
    private MenuCuentas menuCuentas;
    private Scanner sc = new Scanner(System.in);

    public MenuSistema(Banco banco) {
        this.banco = banco;
        this.menuClientes = new MenuClientes(banco, sc);
        this.menuCuentas = new MenuCuentas(banco, sc);
    }

    public void mostrarMenuPrincipal() {
        String opcion;
        do {
            System.out.println("\n==== BANCO ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Gestion de clientes.");
            System.out.println("2. Gestion de cuentas.");
            System.out.println("3. Gestion de tarjetas.");
            System.out.println("4. Cajero automatico.");
            System.out.println("5. Gestion de Empleados.");
            System.out.println("6. Gestion de cajeros.");
            System.out.println("0. Salir.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    menuClientes.mostrarMenuClientes();
                    break;
                case "2":
                    menuCuentas.mostrarMenuCuentas();
                    break;
                //Aqui demas casos.
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        while (!opcion.equalsIgnoreCase("0"));
    }


}

package sistema;

import java.util.Scanner;

public class MenuSistema {
    private Banco banco;
    private MenuClientes menuClientes;
    private MenuCuentas menuCuentas;
    private MenuTarjetas menuTarjetas;
    private MenuMovimientos menuMovimientos;
    private boolean sesionActiva;
    private Scanner sc = new Scanner(System.in);

    public MenuSistema(Banco banco) {
        this.banco = banco;
        this.menuClientes = new MenuClientes(banco, sc);
        this.menuCuentas = new MenuCuentas(banco, sc);
        this.menuTarjetas = new MenuTarjetas(banco, sc);
        this.menuMovimientos = new MenuMovimientos(banco, sc);
        this.sesionActiva = true;
    }

    public char bienvenida() {
        System.out.println("Bienvenido al sistema bancario");
        System.out.println("Desea ingresar al sistema? (s/n)");
        char respuesta = sc.next().charAt(0);
        sc.nextLine();
        limpiarPantalla();
        return respuesta;
    }

    public void mostrarMenuPrincipal() {
        String opcion;
        do {
            System.out.println("\n==== BANCO ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Gestion de clientes.");
            System.out.println("2. Gestion de cuentas.");
            System.out.println("3. Gestion de tarjetas.");
            System.out.println("4. Gestion de movimientos.");
            System.out.println("0. Salir.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    limpiarPantalla();
                    menuClientes.mostrarMenuClientes();
                    break;
                case "2":
                    limpiarPantalla();
                    menuCuentas.mostrarMenuCuentas();
                    break;
                case "3":
                    limpiarPantalla();
                    menuTarjetas.mostrarMenuTarjetas();
                    break;
                case "4":
                    limpiarPantalla();
                    menuMovimientos.mostrarMenuMovimientos(); // ✅ corregido
                    break;
                case "0":
                    sesionActiva = false;
                    despedida();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        while (!opcion.equalsIgnoreCase("0") && sesionActiva);
    }

    public void despedida(){
        System.out.println("Gracias por usar el sistema bancario.");
        sc.close();
    }

    public static void limpiarPantalla() {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Error al limpiar la consola: " + e.getMessage());
        }
    }

}

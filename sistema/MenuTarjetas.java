package sistema;

import java.util.Scanner;

public class MenuTarjetas {
    private Scanner sc;
    private Banco banco;

    public MenuTarjetas(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }
    public void mostrarMenuTarjetas() {
        String opcion;
        do {
            System.out.println("\n==== MENU TARJETAS ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Emitir Tarjeta.");
            System.out.println("2. Vincular tarjeta a una cuenta.");
            System.out.println("0. Volver al menu principal.");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    emitirTarjeta();
                    break;
                case "2":
                    
                    break;
                case "3":
                    
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        }
        while (!opcion.equalsIgnoreCase("0"));
    }

    public void emitirTarjeta() {
        System.out.println("==== EMITIR TARJETA ====");
    }
}

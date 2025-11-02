package sistema;

import sistema.Banco;
import java.util.Scanner;

public class MenuSistema {
    private Banco banco;
    private Scanner sc = new Scanner(System.in);

    public MenuSistema(Banco banco) {
        this.banco = banco;
    }
    public void mostrarMenuPrincipal() {
        String opcion;
        do {
            System.out.println("Escoje: ");
            opcion = sc.nextLine();
        }
        while (opcion.equalsIgnoreCase("0"));
    }
}

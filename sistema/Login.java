package sistema;

import entidades.LoginView;
import java.util.Scanner;

public class Login {
    private MenuSistema menuSistema;
    private LoginView login;
    private Scanner sc;

    public Login(MenuSistema menuSistema, Scanner sc) {
        this.menuSistema = menuSistema;
        this.login = null;
        this.sc = sc;
    }

    public void iniciar() {
        System.out.println("\n==== LOGIN ====");
        System.out.print("Ingrese su Usuario: ");
        String user = sc.nextLine();
        System.out.print("Ingrese su Contrasenia: ");
        String pass = sc.nextLine();
        this.login = new LoginView(user, pass);
        MenuSistema.limpiarPantalla();
        menuSistema.login(this.login);
    }
}
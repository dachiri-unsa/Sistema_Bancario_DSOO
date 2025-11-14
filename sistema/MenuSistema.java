package sistema;

import entidades.*;
import java.util.Arrays;
import java.util.Scanner;

public class MenuSistema {
    private Banco banco;
    private MenuPrincipal menuPrincipal;
    private MenuMovimientos menuMovimientos;
    private boolean sesionActiva;
    private Scanner sc = new Scanner(System.in);
    private static Membership membership = new Membership();

    public MenuSistema(Banco banco) {
        this.banco = banco;
        this.menuPrincipal = new MenuPrincipal(banco, this, sc);
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

    public void mostrarMenuSistema() {
        String opcion;
        do {
            System.out.println("\n==== BANCO ====");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Ir a Cajero");
            //System.out.println("3. Ir a Empleado");
            System.out.println("0. Salir.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    limpiarPantalla();
                    new Login(this, this.sc).iniciar();
                    break;
                case "2":
                    limpiarPantalla();
                    menuMovimientos.mostrarMenuMovimientos();
                    break;
                case "3":
                    //faltaria un javax.websocket o jakarta.websocket
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

    public void login(LoginView loginView) {
        if (membership.validateUser(loginView.getUsername(), loginView.getPassword())) {
            UsuarioSistema usuario = membership.getUser(loginView.getUsername());
            if (usuario != null) {
                System.out.println("\nBienvenido " + usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido());
                System.out.println("Roles: " + usuario.getRoles());

                if (usuario.getRoles().size() > 1 && usuario.getRoles().contains(TipoRol.Cliente)) {
                    System.out.println("\nQuiere ingresar como Cliente o Empleado? (C/E)");
                    String eleccion = sc.next();
                    if (eleccion.equals("C")) { usuario.setRoles(Arrays.asList(TipoRol.Cliente)); }
                }

                SessionManager.setCurrentUser(usuario);
                menuPrincipal.mostrarMenuPrincipal();
            }
        } else {
            limpiarPantalla();
            System.out.println("Credenciales incorrectas");
            new Login(this, this.sc).iniciar();
        }
    }

    public void LogOut() {
        SessionManager.setCurrentUser(null);
        mostrarMenuSistema();
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

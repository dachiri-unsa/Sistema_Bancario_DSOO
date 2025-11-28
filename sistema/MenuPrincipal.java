package sistema;

import java.util.Scanner;
import entidades.*;

public class MenuPrincipal {
    private Banco banco;
    private MenuSistema menuSistema;
    private MenuClientes menuClientes;
    private MenuCuentas menuCuentas;
    private MenuTarjetas menuTarjetas;
    private MenuEmpleados menuEmpleados;
    private MenuAdministradores menuAdministradores;
    private MenuPermisos menuPermisos;
    private MenuCajeros menuCajeros;
    private Scanner sc;

    public MenuPrincipal(Banco banco, MenuSistema menuSistema, Scanner sc) {
        this.banco = banco;
        this.menuSistema = menuSistema;
        this.menuClientes = new MenuClientes(banco, sc);
        this.menuCuentas = new MenuCuentas(banco, sc);
        this.menuTarjetas = new MenuTarjetas(banco, sc);
        this.menuEmpleados = new MenuEmpleados(banco, sc);
        this.menuAdministradores = new MenuAdministradores(banco, sc);
        this.menuPermisos = new MenuPermisos(banco, sc);
        this.menuCajeros = new MenuCajeros(banco, sc);
        this.sc = sc;
    }

    public void mostrarMenuPrincipal() {
        String opcion;
        UsuarioSistema usuarioSistema = SessionManager.getCurrentUser();
        TipoRol rol;
        if (usuarioSistema.getRol().equals(TipoRol.Administrador)) {
            rol = TipoRol.Administrador;
        } else if (usuarioSistema.getRol().equals(TipoRol.Empleado)) {
            rol = TipoRol.Empleado;
        } else {
            rol = TipoRol.Cliente;
        }
        do {
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("Ingrese una opcion: ");
            if (usuarioSistema.getPermisos().contains("CUEN")) {
                System.out.println("1. Gestion de cuentas.");
            }
            if (usuarioSistema.getPermisos().contains("TARJ")) {
                System.out.println("2. Gestion de tarjetas.");
            }
            if (usuarioSistema.getPermisos().contains("CLIE")) {
                System.out.println("3. Gestion de clientes.");
            }
            if (usuarioSistema.getPermisos().contains("EMPL")) {
                System.out.println("4. Gestion de empleados");
            }
            if (usuarioSistema.getPermisos().contains("USUA")) {
                System.out.println("5. Gestion de usuarios");
            }
            if (usuarioSistema.getPermisos().contains("ADMI")) {
                System.out.println("6. Gestion de administradores");
            }
            if (usuarioSistema.getPermisos().contains("PERM")) {
                System.out.println("7. Gestion de permisos");
            }
            if (usuarioSistema.getPermisos().contains("CAJE")) {
                System.out.println("8. Gestion de cajeros");
            }
            System.out.println("0. Cerrar Secion.");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    if (usuarioSistema.getPermisos().contains("CUEN")) {
                        MenuSistema.limpiarPantalla();
                        menuCuentas.mostrarMenuCuentas(rol);
                    }
                    break;
                case "2":
                    if (usuarioSistema.getPermisos().contains("TARJ")) {
                        MenuSistema.limpiarPantalla();
                        menuTarjetas.mostrarMenuTarjetas();
                    }
                    break;
                case "3":
                    if (usuarioSistema.getPermisos().contains("CLIE")) {
                        MenuSistema.limpiarPantalla();
                        menuClientes.mostrarMenuClientes();
                    }
                    break;
                case "4":
                    if (usuarioSistema.getPermisos().contains("EMPL")) {
                        MenuSistema.limpiarPantalla();
                        menuEmpleados.mostrarMenuEmpleados();
                    }
                    break;
                case "5":
                    if (usuarioSistema.getPermisos().contains("USUA")) {
                        MenuSistema.limpiarPantalla();
                        new MenuUsuarios(banco, sc).mostrarMenuUsuarios();
                    }
                    break;
                case "6":
                    if (usuarioSistema.getPermisos().contains("ADMI")) {
                        MenuSistema.limpiarPantalla();
                        menuAdministradores.mostrarMenuAdministradores();
                    }
                    break;
                case "7":
                    if (usuarioSistema.getPermisos().contains("PERM")) {
                        MenuSistema.limpiarPantalla();
                        menuPermisos.mostrarMenuPermisos();
                    }
                    break;
                case "8":
                    if (usuarioSistema.getPermisos().contains("CAJE")) {
                        MenuSistema.limpiarPantalla();
                        menuCajeros.mostrarMenuCajeros();
                    }
                    break;
                case "0":
                    MenuSistema.limpiarPantalla();
                    menuSistema.LogOut();
                    break;
                default:
                    System.out.println("Eleccion no valida. Por favor volver a ingresar.");
                    break;
            }
        } while (!opcion.equalsIgnoreCase("0"));
    }

}

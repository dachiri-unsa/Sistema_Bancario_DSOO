package sistema;

import java.util.Scanner;

import entidades.concretas.Cliente;
import entidades.concretas.LoginView;

public class Registro {
    private Banco banco;
    private Scanner sc;

    public Registro(Banco banco, Scanner sc) {
        this.banco = banco;
        this.sc = sc;
    }

    public void iniciar() {
        System.out.println("\n==== REGISTRO DE USUARIO ====");
        System.out.println("Ingrese su DNI:");
        String dni = sc.nextLine().trim();

        Cliente cliente = banco.getGestorClientes().buscarCliente(dni);
        if (cliente == null) {
            System.out.println("Error: No existe un cliente registrado con ese DNI.");
            System.out.println("Por favor, acerquese a una plataforma para registrarse como cliente.");
            return;
        }

        System.out.println("Ingrese su nuevo nombre de usuario:");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("El nombre de usuario no puede estar vacio.");
            return;
        }

        if (banco.getGestorUsuarios().buscarPorUsuario(username) != null) {
            System.out.println("Error: El nombre de usuario ya esta en uso.");
            return;
        }

        System.out.println("Ingrese su nueva contrasenia:");
        String password = sc.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("La contrasenia no puede estar vacia.");
            return;
        }

        banco.getGestorUsuarios().crearUsuario(dni, new LoginView(username, password));

        if (banco.getGestorUsuarios().buscarPorUsuario(username) != null) {
            System.out.println("Usuario registrado exitosamente! Ahora puede iniciar sesion.");
        } else {
            System.out.println(
                    "Error: No se pudo registrar el usuario. Verifique si ya tiene una cuenta activa o si sus datos son correctos.");
        }
    }
}

package sistema;

import java.util.Scanner;
import entidades.Cliente;
import entidades.LoginView;
import gestores.GestorClientes;

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

        // Verificar si el cliente existe en el banco
        Cliente cliente = banco.getGestorClientes().buscarCliente(dni);
        if (cliente == null) {
            System.out.println("Error: No existe un cliente registrado con ese DNI.");
            System.out.println("Por favor, acerquese a una plataforma para registrarse como cliente.");
            return;
        }

        // Verificar si ya tiene usuario (esto lo valida GestorUsuario.crearUsuario
        // internamente,
        // pero podemos hacer una pre-validacion si quisieramos, aunque GestorUsuario
        // itera sobre Usuarios)
        // Vamos a confiar en la logica de GestorUsuario pero primero pedimos los datos.

        // Sin embargo, GestorUsuario.crearUsuario no retorna nada ni tira excepcion si
        // falla.
        // Seria ideal verificar antes si el usuario ya tiene cuenta activa para no
        // pedir datos en vano.
        // Pero dado que GestorUsuario es quien tiene la logica de 'personaSinUsuario',
        // y GestorUsuario trabaja sobre la lista de Usuarios (que son Personas),
        // asumimos que el flujo es intentar crear.

        System.out.println("Ingrese su nuevo nombre de usuario:");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("El nombre de usuario no puede estar vacio.");
            return;
        }

        // Verificar que el username no este en uso por otro usuario
        if (gestores.GestorUsuario.buscarPorUsuario(username) != null) {
            System.out.println("Error: El nombre de usuario ya esta en uso.");
            return;
        }

        System.out.println("Ingrese su nueva contrasenia:");
        String password = sc.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("La contrasenia no puede estar vacia.");
            return;
        }

        // Intentar crear el usuario
        // Nota: GestorUsuario.crearUsuario busca por DNI en la lista de usuarios.
        // Si el cliente fue agregado a GestorUsuario (como se hace en
        // Banco.inicializarClientes), funcionara.
        banco.getGestorUsuario().crearUsuario(dni, new LoginView(username, password));

        // Verificamos si se creo exitosamente buscando por el nuevo username
        if (gestores.GestorUsuario.buscarPorUsuario(username) != null) {
            System.out.println("Usuario registrado exitosamente! Ahora puede iniciar sesion.");
        } else {
            System.out.println(
                    "Error: No se pudo registrar el usuario. Verifique si ya tiene una cuenta activa o si sus datos son correctos.");
        }
    }
}

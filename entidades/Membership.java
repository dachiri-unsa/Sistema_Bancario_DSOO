package entidades;

import java.util.List;
import gestores.*;

public class Membership {
    public boolean validateUser(String username, String password) {
        Usuario usuario = GestorUsuario.buscarPorUsuario(username);
        return usuario != null && util.PasswordUtil.checkPassword(password, usuario.getContrasenia());
    }

    public UsuarioSistema getUser(String username) {
        Usuario usuario = GestorUsuario.buscarPorUsuario(username);
        if (usuario == null) {
            System.out.print("Error al encontrar usuario.");
            return null;
        }

        Persona persona = GestorClientes.buscarPorDni(usuario.getDNI());
        if (persona == null) {
            persona = GestorEmpleados.buscarPorDni(usuario.getDNI());
            if (persona == null) {
                persona = GestorAdministradores.buscarPorDni(usuario.getDNI());
                if (persona == null) {
                    System.out.print("Error al encontrar persona.");
                    return null;
                }
            }
        }

        TipoRol rol = usuario.getTipoRol();
        if (rol == null) {
            System.out.print("Error al encontrar rol.");
            return null;
        }

        List<String> permisos = usuario.getPermisos();

        return new UsuarioSistema(usuario.getNombreUsuario(), usuario.getContrasenia(), persona, rol, permisos);
    }
}
package entidades;

import java.util.ArrayList;
import java.util.List;
import gestores.*;

public class Membership {
    public boolean validateUser(String username, String password) {
        Usuario usuario = GestorUsuario.buscarPorUsuario(username);
        return usuario != null && usuario.getContrasenia().equals(password);
    }

    public UsuarioSistema getUser(String username) {
        Usuario usuario = GestorUsuario.buscarPorUsuario(username);
        if (usuario == null) return null;

        Persona persona = GestorClientes.buscarPorDni(usuario.getDniPersona());
        if (persona == null) {
            persona = GestorEmpleados.buscarPorDni(usuario.getDniPersona());
        }
        if (persona == null) return null;

        List<TipoRol> roles = GestorRoles.buscarRolesPorUsuario(usuario.getNombreUsuario());

        return new UsuarioSistema(usuario.getNombreUsuario(), usuario.getContrasenia(), persona, roles);
    }
}
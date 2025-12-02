package entidades.concretas;

import java.util.List;

import entidades.enumerables.TipoRol;
import entidades.enumerables.TipoPermiso;
import sistema.SistemaBanco;

public class Membership {
    public boolean validateUser(String username, String password) {
        Usuario usuario = SistemaBanco.getInstance().getBanco().getGestorUsuarios().buscarPorUsuario(username);
        return usuario != null && util.PasswordUtil.checkPassword(password, usuario.getContrasenia());
    }

    public UsuarioSistema getUser(String username) {
        Usuario usuario = SistemaBanco.getInstance().getBanco().getGestorUsuarios().buscarPorUsuario(username);
        if (usuario == null) {
            System.out.print("Error al encontrar usuario.");
            return null;
        }

        Persona persona = SistemaBanco.getInstance().getBanco().getGestorClientes().buscarCliente(usuario.getDNI());
        if (persona == null) {
            persona = SistemaBanco.getInstance().getBanco().getGestorEmpleados().buscarPorDni(usuario.getDNI());
            if (persona == null) {
                persona = SistemaBanco.getInstance().getBanco().getGestorAdministradores()
                        .buscarPorDni(usuario.getDNI());
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

        List<TipoPermiso> permisos = usuario.getPermisos();

        return new UsuarioSistema(usuario.getNombreUsuario(), usuario.getContrasenia(), persona, rol, permisos);
    }
}
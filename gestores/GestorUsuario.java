package gestores;

import java.util.ArrayList;

import entidades.LoginView;
import entidades.Persona;
import entidades.Usuario;
import java.util.List;

public class GestorUsuario {
    private ArrayList<Usuario> listaUsuarios;
    private static List<Usuario> usuarios = new ArrayList<>();

    public GestorUsuario() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
        usuarios.add(usuario);
    }

    public void listarUsuarios() {
        for (Usuario c : listaUsuarios) {
            System.out.println("-" + c.getNombreUsuario() + " (Password:" + c.getContrasenia() + ")");
        }
    }

    public static Usuario buscarPorUsuario(String username) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public void crearUsuario(String dni, LoginView usuario) {
        for (Usuario u : usuarios) {
            if (u.getDNI().equals(dni)) {
                if (u.personaSinUsuario()) {
                    u.setContrasenia(util.PasswordUtil.hashPassword(usuario.getPassword()));
                    u.setNombreUsuario(usuario.getUsername());
                    u.setEstado(true);
                    return;
                }
            }
        }
    }

    public void modificarContraseña(String username, String nueva) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(username)) {
                u.setContrasenia(util.PasswordUtil.hashPassword(nueva));
                return;
            }
        }
    }
}

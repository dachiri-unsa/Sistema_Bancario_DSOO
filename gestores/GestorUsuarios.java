package gestores;

import java.util.List;

import entidades.concretas.LoginView;
import entidades.concretas.Usuario;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorUsuarios implements Gestor<Usuario> {
    private final SincronizadorEntidad<Usuario> sincronizadorUsuarios;

    public GestorUsuarios(SincronizadorEntidad<Usuario> sincronizadorUsuarios) {
        this.sincronizadorUsuarios = sincronizadorUsuarios;
    }

    public GestorUsuarios() {
        this.sincronizadorUsuarios = new SincronizadorEntidad<>();
    }

    @Override
    public void agregar(Usuario usuario) {
        sincronizadorUsuarios.agregar(usuario);
    }

    public void agregarUsuario(Usuario usuario) {
        agregar(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return sincronizadorUsuarios.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorUsuarios.eliminar(index);
    }

    public void listarUsuarios() {
        if (sincronizadorUsuarios.listarTodos().isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        for (Usuario c : sincronizadorUsuarios.listarTodos()) {
            System.out.println("-" + c.getNombreUsuario() + " (Password:" + c.getContrasenia() + ")");
        }
    }

    public Usuario buscarPorUsuario(String username) {
        for (Usuario u : sincronizadorUsuarios.listarTodos()) {
            if (u.getNombreUsuario().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public void crearUsuario(String dni, LoginView usuario) {
        for (Usuario u : sincronizadorUsuarios.listarTodos()) {
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
        for (Usuario u : sincronizadorUsuarios.listarTodos()) {
            if (u.getNombreUsuario().equals(username)) {
                u.setContrasenia(util.PasswordUtil.hashPassword(nueva));
                return;
            }
        }
    }
}

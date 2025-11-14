package gestores;

import entidades.TipoRol;
import java.util.ArrayList;
import entidades.UsuarioRol;
import java.util.List;

public class GestorRoles { 
    private ArrayList<UsuarioRol> listaRoles;
    private static List<UsuarioRol> roles = new ArrayList<>();

    public GestorRoles() {
        this.listaRoles = new ArrayList<>();
    }
    public void agregarRol(UsuarioRol usuario) {
        listaRoles.add(usuario);
        roles.add(usuario);
    }
    
    public void listarRoles() {
        for (UsuarioRol u : roles) {
            System.out.print("- " + u.getNombreUsuario() + " (Roles: ");
            for (TipoRol r : u.getRoles()) {
                System.out.print(r + " ");
            }
            System.out.println(")");
        }
    }
    
    public static List<TipoRol> buscarRolesPorUsuario(String username) {
        for (UsuarioRol u : roles) {
            if (u.getNombreUsuario().equals(username)) {
                return u.getRoles();
            }
        }
        return new ArrayList<>();
    }
}



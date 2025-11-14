package entidades;

import java.util.List;

public class UsuarioRol {
    private String nombreUsuario;
    private List<TipoRol> roles;

    public UsuarioRol(String nombreUsuario, List<TipoRol> roles) {
        this.nombreUsuario = nombreUsuario;
        this.roles = roles;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public List<TipoRol> getRoles() { return roles; }
}

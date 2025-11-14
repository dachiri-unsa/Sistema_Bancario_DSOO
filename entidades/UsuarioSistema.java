package entidades;

import java.util.List;

public class UsuarioSistema {
    private String usuario;
    private String contrasenia;
    private Persona persona;
    private List<TipoRol> roles;

    public UsuarioSistema(String usuario, String contrasenia, Persona persona, List<TipoRol> roles) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.persona = persona;
        this.roles = roles;
    }

    public String getUsuario() { return usuario; }
    public String getContrasenia() { return contrasenia; }
    public Persona getPersona() { return persona; }
    public List<TipoRol> getRoles() { return roles; }

    public void setRoles(List<TipoRol> roles) { this.roles = roles; }
}

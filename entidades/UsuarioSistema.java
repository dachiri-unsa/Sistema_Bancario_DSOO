package entidades;

import java.util.List;

public class UsuarioSistema {
    private String usuario;
    private String contrasenia;
    private Persona persona;
    private TipoRol rol;
    private List<String> permisos;

    public UsuarioSistema(String usuario, String contrasenia, Persona persona, TipoRol rol, List<String> permisos) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.persona = persona;
        this.rol = rol;
        this.permisos = permisos;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Persona getPersona() {
        return persona;
    }

    public TipoRol getRol() {
        return rol;
    }

    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }
}

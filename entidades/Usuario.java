package entidades;

import java.util.List;

public class Usuario extends Persona {
    private String nombreUsuario;
    private String contrasenia;
    private boolean estado;

    // Persona con usuario
    public Usuario(String nombre, String apellido, String dni, String telefono, String direccion, String nombreUsuario,
            String contrasenia, boolean estado) {
        super(nombre, apellido, dni, telefono, direccion);
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.estado = estado;
    }

    // Persona sin usuario
    public Usuario(Persona persona) {
        super(persona.getNombre(), persona.getApellido(), persona.getDNI(), persona.getTelefono(),
                persona.getDireccion());
        this.nombreUsuario = "";
        this.contrasenia = "";
        this.estado = false;
    }

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return this.contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean login() {
        return this.nombreUsuario.equals(nombreUsuario) && this.contrasenia.equals(contrasenia) && this.estado;
    }

    public void mostrarDatos() {
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Estado: " + estado);
    }

    public List<String> getPermisos() {
        return null;
    }

    public TipoRol getTipoRol() {
        return null;
    }

    public boolean personaSinUsuario() {
        return this.nombreUsuario.equals("") && this.contrasenia.equals("") && this.estado == false;
    }

}

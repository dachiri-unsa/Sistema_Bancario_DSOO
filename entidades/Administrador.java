package entidades;

import java.util.ArrayList;
import java.util.List;

import gestores.GestorPermisos;

public class Administrador extends Usuario {
    public Administrador(String nombre, String apellido, String dni, String telefono, String direccion,
            String nombreUsuario, String contrasenia, boolean estado) {
        super(nombre, apellido, dni, telefono, direccion, nombreUsuario, contrasenia, estado);
    }

    public Administrador(Persona persona, String nombreUsuario, String contrasenia, boolean estado) {
        super(persona.getNombre(), persona.getApellido(), persona.getDNI(), persona.getTelefono(),
                persona.getDireccion(), nombreUsuario, contrasenia, estado);
    }

    @Override
    public List<String> getPermisos() {
        ArrayList<String> permisos = new ArrayList<>(GestorPermisos.getPermisosByRol("Administrador"));
        permisos.add("PERM");
        return permisos;
    }

    @Override
    public TipoRol getTipoRol() {
        return TipoRol.Administrador;
    }
}

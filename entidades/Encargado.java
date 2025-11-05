package entidades;

public abstract class Encargado extends Persona {
    protected String id;
    protected String tipo;

    public Encargado(String id, String nombre, String apellido, String dni, String telefono, String direccion) {
        super(nombre, apellido, dni, telefono, direccion);
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (ID: " + id + ")";
    }
}

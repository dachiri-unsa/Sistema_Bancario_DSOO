package entidades;

public class Persona {
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected String telefono;
    protected String direccion;

    public Persona(String nombre,String apellido,String dni,String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() { return this.nombre; }

    public String getApellido() { return this.apellido; }

    public String getDNI() { return this.dni; }

    public String getTelefono() { return this.telefono; }

    public String getDireccion() { return this.direccion; }
}

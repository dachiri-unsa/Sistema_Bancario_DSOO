package entidades;

public class Cliente extends Persona {

    public Cliente(String nombre,String apellido,String dni,String telefono, String direccion) {
        super(nombre, apellido, dni, telefono, direccion);
    }
    public String toString() {
        return "Nombre completo: "+apellido+", "+nombre+
            "\nDNI: "+dni+". Telefono: "+telefono;
    }
}

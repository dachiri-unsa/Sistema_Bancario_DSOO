package entidades;

public class Administrador extends Persona{
    public Administrador(String nombre, String apellido, String dni, String telefono, String direccion) {
        super(nombre, apellido, dni, telefono, direccion);
    }

    @Override
    public void mostrarPermisos(){
        System.out.println("Usted es un administrador sus permisos son: \n 1. Gestion de empleados \n2. Gestion de clientes \n3. Gestion de cuentas \n4. Gestion de tarjetas");
    }
}

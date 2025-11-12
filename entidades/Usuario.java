package entidades;

public class Usuario {
    protected  String nombreUsuario;
    protected  String contraseña;
    protected  boolean estado;
    public Usuario(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.estado = true;
    }
    public boolean login(){
        return this.nombreUsuario.equals(nombreUsuario) && this.contraseña.equals(contraseña) && this.estado;
    }
    public void mostrarDatos(){
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Estado: " +estado);
    }
    public void mostrarPermisos(){

    }
}

package entidades;

public class Usuario {
    protected  String nombreUsuario;
    protected  String contrasenia;
    protected  boolean estado;
    protected  String dniPersona;
    public Usuario(String nombreUsuario, String contrasenia, String dniPersona) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.dniPersona = dniPersona;
        this.estado = true;
    }

    public String getNombreUsuario() { return this.nombreUsuario; }
    public String getContrasenia() { return this.contrasenia; }
    public String getDniPersona() { return this.dniPersona; }

    public boolean login(){
        return this.nombreUsuario.equals(nombreUsuario) && this.contrasenia.equals(contrasenia) && this.estado;
    }
    public void mostrarDatos(){
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Estado: " +estado);
    }
    public void mostrarPermisos(){

    }
}

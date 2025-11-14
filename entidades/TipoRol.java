package entidades;

public enum TipoRol {
    Administrador, //Grupo Empleado
    Asistente, //Grupo Empleado
    Cliente; //Grupo Cliente

    public String toString() {
        return this.name();
    }
}
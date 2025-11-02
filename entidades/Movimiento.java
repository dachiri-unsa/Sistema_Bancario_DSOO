package entidades;

public class Movimiento {
    protected double monto;
    // Importan del LocalDataTime y lo aplican porfa, yo no se XD
    // protected LocalDateTime fecha;
    protected String descripcion;
    protected CuentaBancaria origen;
    //Agregar un toString para que en Historial de movimientos pueda mostrarlo
    // O tambien cambiar el metodo listarMovimientos en HistorialMovimientos
}

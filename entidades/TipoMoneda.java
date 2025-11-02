package entidades;

public enum TipoMoneda {
    Soles,
    Dolares,
    Euros;

    public String toString() {
        return this.name();
    }
}

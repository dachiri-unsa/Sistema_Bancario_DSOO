package gestores;

import java.util.ArrayList;
import entidades.Tarjeta;

public class GestorTarjetas {
    private ArrayList<Tarjeta> tarjetas;

    public GestorTarjetas() {
        this.tarjetas = new ArrayList<>();
    }

    public ArrayList<Tarjeta> getTarjetas() { return this.tarjetas; }
    public void agregarTarjeta(Tarjeta tarjeta) {
        tarjetas.add(tarjeta);
    }
}

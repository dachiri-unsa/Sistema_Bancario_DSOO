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
    public String generarNumeroTarjeta() {
        String numero = "";
        for(int i = 0; i < 16; i++) {
            int digito = (int)(Math.random() * 10);
            numero +=digito;
        }
        return numero;
    }
    public Tarjeta buscarTarjeta(String numeroTarjeta) {
        if(tarjetas.isEmpty()){
            return null;
        }
        for (Tarjeta tarjeta : tarjetas) {
            if(tarjeta.getNumeroTarjeta().equals(numeroTarjeta)){
                return tarjeta;
            }
        }
        return null;
    }
    public boolean eliminarTarjeta(String numeroTarjeta) {
        if(tarjetas.isEmpty()){
            return false;
        }
        for (Tarjeta tarjeta : tarjetas) {
            if(tarjeta.getNumeroTarjeta().equals(numeroTarjeta)){
                tarjetas.remove(tarjeta);
                return true;
            }
        }
        return false;
    }
    public void  mostrarTarjetas() {
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println(tarjeta);
        }
    }
}

package gestores;

import java.util.ArrayList;
import entidades.Cliente;

public class GestorClientes { 
    private ArrayList<Cliente> listaClientes;

    public GestorClientes() {
        this.listaClientes = new ArrayList<>();
    }
    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }
    public Cliente buscarCliente(String dni) {
        for (Cliente c : listaClientes) {
            if ( c.getDNI().equals(dni)) {
                return c;
            }
        }
        return null;
    }
    public void listarClientes() {
        for (Cliente c: listaClientes) {
            System.out.println("-"+c.getNombre()+" (DNI:"+c.getDNI()+")");
        }
    }
}

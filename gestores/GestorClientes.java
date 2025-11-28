package gestores;

import java.util.ArrayList;
import entidades.Cliente;
import java.util.List;

public class GestorClientes {
    private ArrayList<Cliente> listaClientes;
    private static List<Cliente> clientes = new ArrayList<>();

    public ArrayList<Cliente> getClientes() {
        return listaClientes;
    }

    public GestorClientes() {
        this.listaClientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
        clientes.add(cliente);
    }

    public Cliente buscarCliente(String dni) {
        for (Cliente c : listaClientes) {
            if (c.getDNI().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public void listarClientes() {
        for (Cliente c : listaClientes) {
            System.out.println("-" + c.getNombre() + " (DNI:" + c.getDNI() + ")");
        }
    }

    public static Cliente buscarPorDni(String dni) {
        for (Cliente c : clientes) {
            if (c.getDNI().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public void eliminarCliente(Cliente cliente) {
        listaClientes.remove(cliente);
    }
}

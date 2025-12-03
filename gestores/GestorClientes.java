package gestores;

import java.util.List;

import entidades.concretas.Cliente;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorClientes implements Gestor<Cliente> {
    private final SincronizadorEntidad<Cliente> sincronizadorClientes;

    public GestorClientes(SincronizadorEntidad<Cliente> sincronizadorClientes) {
        this.sincronizadorClientes = sincronizadorClientes;
    }

    public GestorClientes() {
        this.sincronizadorClientes = new SincronizadorEntidad<>();
    }

    @Override
    public void agregar(Cliente cliente) {
        sincronizadorClientes.agregar(cliente);
    }

    public void agregarCliente(Cliente cliente) {
        agregar(cliente);
    }

    @Override
    public List<Cliente> listarTodos() {
        return sincronizadorClientes.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorClientes.eliminar(index);
    }

    public Cliente buscarCliente(String dni) {
        for (Cliente c : sincronizadorClientes.listarTodos()) {
            if (c.getDNI().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public void eliminarCliente(Cliente cliente) {
        int index = sincronizadorClientes.listarTodos().indexOf(cliente);
        if (index != -1) {
            sincronizadorClientes.eliminar(index);
        }
    }

    public void listarClientes() {
        if (sincronizadorClientes.listarTodos().isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : sincronizadorClientes.listarTodos()) {
            System.out.println("-" + c.getNombre() + " (DNI:" + c.getDNI() + ")");
        }
    }
}

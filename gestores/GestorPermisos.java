package gestores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorPermisos {
    private ArrayList<String> listaPermisos;
    private static Map<String, List<String>> permisosRol = new HashMap<>();

    public GestorPermisos() {
        this.listaPermisos = new ArrayList<>();
    }

    public void agregarPermisos(List<String> permisos) {
        this.listaPermisos.addAll(permisos);
    }

    public void agregarPermisos(String permiso) {
        listaPermisos.add(permiso);
    }

    public void listarPermisos() {
        for (String p : listaPermisos) {
            System.out.println("-" + p);
        }
    }

    public static List<String> getPermisosByRol(String rol) {
        return permisosRol.get(rol);
    }

    public void modificarPermisosByRol(String rol, List<String> permisos) {
        permisosRol.put(rol, permisos);
    }

    public ArrayList<String> getListaPermisos() {
        return listaPermisos;
    }
}

package src.main.gerentes;

import src.main.java.psa.modelo.Proyecto;

import java.util.ArrayList;
import java.util.List;

public class gerentePortafolio {
    private List<Proyecto> proyectos = new ArrayList<>();

    public Proyecto crearProyecto(String nombre, String descripcion, String liderProyecto){
        Proyecto proyecto = new Proyecto(nombre, descripcion, liderProyecto);
        proyectos.add(proyecto);
        return proyecto;
    }
}

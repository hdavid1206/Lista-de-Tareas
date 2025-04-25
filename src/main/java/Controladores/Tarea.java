package Controladores;

public class Tarea {
    private int id;
    private String descripcion;
    private boolean completada = false;
    
    public Tarea(int nuevoId, String descripcion1) {
        this.id = nuevoId;
        this.descripcion = descripcion1;
        this.completada = false;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
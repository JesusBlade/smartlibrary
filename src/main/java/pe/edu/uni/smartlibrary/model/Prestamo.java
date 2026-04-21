package pe.edu.uni.smartlibrary.model;

public class Prestamo {
    private int id;
    private String libro;
    private String usuario;
    private int dias;
    
    public Prestamo() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibro() { return libro; }
    public void setLibro(String libro) { this.libro = libro; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }
}
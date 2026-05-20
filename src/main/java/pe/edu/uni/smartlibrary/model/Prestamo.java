package pe.edu.uni.smartlibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestamo")
public class Prestamo {
    
    @Id
    @SequenceGenerator(name = "seq_prestamo", sequenceName = "seq_prestamo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prestamo")
    private Long id;
    
    private String libro;
    private String usuario;
    private int dias;
    
    public Prestamo() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibro() { return libro; }
    public void setLibro(String libro) { this.libro = libro; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }
}
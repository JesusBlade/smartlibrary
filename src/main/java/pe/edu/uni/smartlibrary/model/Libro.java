package pe.edu.uni.smartlibrary.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @SequenceGenerator(name = "seq_libro", sequenceName = "seq_libro", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_libro")
    private Long id;

    private Long idAutor;
    private Long idCategoria;
    private String titulo;
    private String editorial;
    private int paginas;
    private int lanzamiento;

    public Libro() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdAutor() { return idAutor; }
    public void setIdAutor(Long idAutor) { this.idAutor = idAutor; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    public int getLanzamiento() { return lanzamiento; }
    public void setLanzamiento(int lanzamiento) { this.lanzamiento = lanzamiento; }
}
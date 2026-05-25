package pe.edu.uni.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.uni.smartlibrary.model.Categoria;

public interface RepositoryCategoria extends JpaRepository<Categoria, Long> {
	
}

package pe.edu.uni.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.uni.smartlibrary.model.Autor;

public interface RepositoryAutor extends JpaRepository<Autor, Long> {
	
}


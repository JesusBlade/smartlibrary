package pe.edu.uni.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.uni.smartlibrary.model.Libro;

@Repository
public interface RepositoryLibro extends JpaRepository<Libro, Long> {
    
}

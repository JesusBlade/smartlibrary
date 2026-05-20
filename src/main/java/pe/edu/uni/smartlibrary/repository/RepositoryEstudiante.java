package pe.edu.uni.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.uni.smartlibrary.model.Estudiante;

@Repository
public interface RepositoryEstudiante extends JpaRepository<Estudiante, Long> {
    
}

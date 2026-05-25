package pe.edu.uni.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.uni.smartlibrary.model.Persona;

public interface RepositoryPersona extends JpaRepository<Persona, Long> {
}

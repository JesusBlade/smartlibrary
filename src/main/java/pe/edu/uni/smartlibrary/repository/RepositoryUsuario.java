package pe.edu.uni.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.uni.smartlibrary.model.Usuario;

@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {
	
	public Usuario findByUser(String user);
}

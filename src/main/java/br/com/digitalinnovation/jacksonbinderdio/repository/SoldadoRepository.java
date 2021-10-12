package br.com.digitalinnovation.jacksonbinderdio.repository;

import br.com.digitalinnovation.jacksonbinderdio.model.Soldado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldadoRepository extends JpaRepository<Soldado, Long> {
}

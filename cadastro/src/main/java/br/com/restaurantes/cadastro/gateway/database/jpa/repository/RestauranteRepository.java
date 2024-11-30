package br.com.restaurantes.cadastro.gateway.database.jpa.repository;

import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long>{
	Optional<RestauranteEntity> findByNome(String nome);
	Optional<RestauranteEntity> findByLocalizacao(String localizacao);
	Optional<RestauranteEntity> findByTipoCozinha(String tipoCozinha);
}

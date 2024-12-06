package br.com.restaurantes.cadastro.gateway.database.jpa.repository;

import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long>{
	List<RestauranteEntity> findByNome(String nome);
	List<RestauranteEntity> findByLocalizacao(String localizacao);
	List<RestauranteEntity> findByTipoCozinha(String tipoCozinha);
}

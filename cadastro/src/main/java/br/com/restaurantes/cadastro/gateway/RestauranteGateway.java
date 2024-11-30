package br.com.restaurantes.cadastro.gateway;

import br.com.restaurantes.cadastro.domain.Restaurante;

import java.util.Optional;

public interface RestauranteGateway {

	Optional<Restaurante> buscarRestaurantePorNome(String nome);
	Optional<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha);
	Optional<Restaurante> buscarRestaurantePorLocalizacao(String localizacao);
	void cadastrarRestaurante(Restaurante restaurante);

}

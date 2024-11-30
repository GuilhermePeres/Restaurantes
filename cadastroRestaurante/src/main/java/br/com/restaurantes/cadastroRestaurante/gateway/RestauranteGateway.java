package br.com.restaurantes.cadastroRestaurante.gateway;

import br.com.restaurantes.cadastroRestaurante.domain.Restaurante;

import java.util.Optional;

public interface RestauranteGateway {

	Optional<Restaurante> buscarRestaurantePorNome(String nome);
	Optional<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha);
	Optional<Restaurante> buscarRestaurantePorLocalizacao(String localizacao);
	void cadastrarRestaurante(Restaurante restaurante);

}

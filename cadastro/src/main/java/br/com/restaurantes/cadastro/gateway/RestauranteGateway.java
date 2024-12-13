package br.com.restaurantes.cadastro.gateway;

import br.com.restaurantes.cadastro.domain.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteGateway {

	List<Restaurante> buscarRestaurantePorNome(String nome);
	List<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha);
	List<Restaurante> buscarRestaurantePorLocalizacao(String localizacao);
	void cadastrarRestaurante(Restaurante restaurante);
	Optional<Restaurante> atualizarRestaurante(Long id, Restaurante restaurante);
	void removerRestaurante(Long id);
	int verificarDisponibilidadeLugares(Long restauranteId, String dataReserva);
}

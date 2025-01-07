package br.com.restaurantes.cadastro.usecase;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GerenciarRestauranteUsecase {

	private final RestauranteGateway restauranteGateway;

	public void cadastrarRestaurante(Restaurante restaurante) {
        restauranteGateway.cadastrarRestaurante(restaurante);
    }

    public List<Restaurante> buscarRestaurantePorNome(String nome){
        return restauranteGateway.buscarRestaurantePorNome(nome);
    }

    public List<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha){
        return restauranteGateway.buscarRestaurantePorTipoCozinha(tipoCozinha);
    }

    public List<Restaurante> buscarRestaurantePorLocalizacao(String localizacao) {
        return restauranteGateway.buscarRestaurantePorLocalizacao(localizacao);
    }

    public int verificarDisponibilidadeLugares(Long restauranteId, String dataReserva) {
        return restauranteGateway.verificarDisponibilidadeLugares(restauranteId, dataReserva);
    }

    public Optional<Restaurante> atualizarRestaurante(Long id, Restaurante restaurante){
        return restauranteGateway.atualizarRestaurante(id, restaurante);
    }

    public void removerRestaurante(Long id){
        restauranteGateway.removerRestaurante(id);
    }
}

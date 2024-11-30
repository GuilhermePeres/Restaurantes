package br.com.restaurantes.cadastroRestaurante.usecase;

import br.com.restaurantes.cadastroRestaurante.domain.Restaurante;
import br.com.restaurantes.cadastroRestaurante.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GerenciarRestauranteUsecase {

	private final RestauranteGateway restauranteGateway;

	public void cadastrar(Restaurante restaurante) {
        restauranteGateway.cadastrarRestaurante(restaurante);
    }

    public Optional<Restaurante> buscarRestaurantePorNome(String nome){
        return restauranteGateway.buscarRestaurantePorNome(nome);
    }

    public Optional<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha){
        return restauranteGateway.buscarRestaurantePorTipoCozinha(tipoCozinha);
    }

    public Optional<Restaurante> buscarRestaurantePorLocalizacao(String localizacao) {
        return restauranteGateway.buscarRestaurantePorLocalizacao(localizacao);
    }
}

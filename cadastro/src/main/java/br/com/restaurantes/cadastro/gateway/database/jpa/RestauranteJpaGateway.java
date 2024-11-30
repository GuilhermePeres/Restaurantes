package br.com.restaurantes.cadastro.gateway.database.jpa;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.restaurantes.cadastro.gateway.RestauranteGateway;
import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import br.com.restaurantes.cadastro.gateway.database.jpa.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestauranteJpaGateway implements RestauranteGateway {

	private final RestauranteRepository restauranteRepository;

	@Override
	public void cadastrarRestaurante(Restaurante restaurante) {
		try {
			RestauranteEntity restauranteEntity = mapToEntity(restaurante);

			restauranteRepository.save(restauranteEntity);

		}catch (Exception e){
			throw new ErroAcessarRepositorioException();
		}

	}

	@Override
	public Optional<Restaurante> buscarRestaurantePorNome(String nome) {
		try {
			return restauranteRepository.findByNome(nome).map(this::mapToDomain);

		} catch (Exception e) {
			throw new ErroAcessarRepositorioException();
		}
	}

	@Override
	public Optional<Restaurante> buscarRestaurantePorLocalizacao(String localizacao) {
		try {
			return restauranteRepository.findByLocalizacao(localizacao).map(this::mapToDomain);

		} catch (Exception e) {
			throw new ErroAcessarRepositorioException();
		}
	}

	@Override
	public Optional<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha) {
		try {
			return restauranteRepository.findByTipoCozinha(tipoCozinha).map(this::mapToDomain);

		} catch (Exception e) {
			throw new ErroAcessarRepositorioException();
		}
	}
	
	private Restaurante mapToDomain(RestauranteEntity restauranteEntity) {
		return new Restaurante(
				restauranteEntity.getId(),
				restauranteEntity.getNome(),
				restauranteEntity.getQuantidadeLugares(),
				restauranteEntity.getLocalizacao(),
				restauranteEntity.getTipoCozinha(),
				restauranteEntity.getHorarioFuncionamento()
		);
	}
	
	private RestauranteEntity mapToEntity(Restaurante restaurante) {
		return RestauranteEntity.builder()
				.id(restaurante.getId())
				.nome(restaurante.getNome())
				.quantidadeLugares(restaurante.getQuantidadeLugares())
				.localizacao(restaurante.getLocalizacao())
				.tipoCozinha(restaurante.getTipoCozinha())
				.horarioFuncionamento(restaurante.getHorarioFuncionamento())
				.build();
	}
}

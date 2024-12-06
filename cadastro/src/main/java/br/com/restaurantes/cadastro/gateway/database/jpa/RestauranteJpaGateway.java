package br.com.restaurantes.cadastro.gateway.database.jpa;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.restaurantes.cadastro.exception.RestauranteNaoEncontradoException;
import br.com.restaurantes.cadastro.gateway.RestauranteGateway;
import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import br.com.restaurantes.cadastro.gateway.database.jpa.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
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
	public Optional<Restaurante> atualizarRestaurante(Long id, Restaurante restaurante) {
			return Optional.ofNullable(restauranteRepository.findById(id)
                    .map(restauranteEntity -> {
                        restauranteEntity.setNome(restaurante.getNome());
                        restauranteEntity.setQuantidadeLugares(restaurante.getQuantidadeLugares());
                        restauranteEntity.setLocalizacao(restaurante.getLocalizacao());
                        restauranteEntity.setTipoCozinha(restaurante.getTipoCozinha());
                        restauranteEntity.setHorarioFuncionamento(restaurante.getHorarioFuncionamento());

                        return mapToDomain(restauranteRepository.save(restauranteEntity));
                    })
                    .orElseThrow(RestauranteNaoEncontradoException::new));
	}

	@Override
	public void removerRestaurante(Long id) {
		try {
			restauranteRepository.deleteById(id);
		}catch (Exception e){
			throw new ErroAcessarRepositorioException();
		}
	}

	@Override
	public Optional<Restaurante> buscarRestaurantePorNome(String nome) {
		return Optional.ofNullable(restauranteRepository.findByNome(nome).map(this::mapToDomain)
                .orElseThrow(RestauranteNaoEncontradoException::new));
	}

	@Override
	public Optional<Restaurante> buscarRestaurantePorLocalizacao(String localizacao) {
		return Optional.ofNullable(restauranteRepository.findByLocalizacao(localizacao).map(this::mapToDomain)
				.orElseThrow(RestauranteNaoEncontradoException::new));
	}

	@Override
	public Optional<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha) {
		return Optional.ofNullable(restauranteRepository.findByTipoCozinha(tipoCozinha).map(this::mapToDomain)
				.orElseThrow(RestauranteNaoEncontradoException::new));
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
		return new RestauranteEntity(
				restaurante.getId(),
				restaurante.getNome(),
				restaurante.getQuantidadeLugares(),
				restaurante.getLocalizacao(),
				restaurante.getTipoCozinha(),
				restaurante.getHorarioFuncionamento());
	}
}

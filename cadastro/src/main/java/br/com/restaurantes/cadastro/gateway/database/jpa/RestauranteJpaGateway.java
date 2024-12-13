package br.com.restaurantes.cadastro.gateway.database.jpa;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.restaurantes.cadastro.exception.IllegalArgumentException;
import br.com.restaurantes.cadastro.exception.RestauranteNaoEncontradoException;
import br.com.restaurantes.cadastro.gateway.RestauranteGateway;
import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import br.com.restaurantes.cadastro.gateway.database.jpa.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public List<Restaurante> buscarRestaurantePorNome(String nome) {
		List<RestauranteEntity> restaurantes = restauranteRepository.findByNome(nome);

		if(restaurantes.isEmpty()){
			throw new RestauranteNaoEncontradoException();
		}

		return restaurantes.stream().map(this::mapToDomain).collect(Collectors.toList());
	}

	@Override
	public List<Restaurante> buscarRestaurantePorLocalizacao(String localizacao) {
		List<RestauranteEntity> restaurantes = restauranteRepository.findByLocalizacao(localizacao);

		if(restaurantes.isEmpty()){
			throw new RestauranteNaoEncontradoException();
		}

		return restaurantes.stream().map(this::mapToDomain).collect(Collectors.toList());
	}

	@Override
	public List<Restaurante> buscarRestaurantePorTipoCozinha(String tipoCozinha) {
		List<RestauranteEntity> restaurantes = restauranteRepository.findByTipoCozinha(tipoCozinha);

		if(restaurantes.isEmpty()){
			throw new RestauranteNaoEncontradoException();
		}

		return restaurantes.stream().map(this::mapToDomain).collect(Collectors.toList());
	}

	@Override
	public int verificarDisponibilidadeLugares(Long restauranteId, String dataReserva) {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);

		if(restauranteEntity.isEmpty()){
			throw new RestauranteNaoEncontradoException();
		}

		int lugaresDisponiveis = restauranteEntity.get().getQuantidadeLugares();

		try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(dataReserva, formatter);

			LocalDateTime inicioNoite = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(18, 0));
			boolean noite = dateTime.isAfter(inicioNoite);

			if(noite){
				return lugaresDisponiveis;
			}

		}catch (DateTimeParseException e){
			throw new IllegalArgumentException("Campo dataReserva inválido: " + dataReserva);
		}

		return lugaresDisponiveis / 2;
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

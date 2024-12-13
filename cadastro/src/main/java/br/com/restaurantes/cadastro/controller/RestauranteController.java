package br.com.restaurantes.cadastro.controller;

import br.com.restaurantes.cadastro.exception.ErroAcessarRepositorioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.usecase.GerenciarRestauranteUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

	private GerenciarRestauranteUsecase gerenciarRestauranteUsecase;

	@Autowired
	public RestauranteController(GerenciarRestauranteUsecase gerenciarRestauranteUsecase){
		this.gerenciarRestauranteUsecase = gerenciarRestauranteUsecase;
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@Valid @RequestBody RestauranteJson restauranteJson) {
		gerenciarRestauranteUsecase.cadastrarRestaurante(mapToDomain(restauranteJson));

		return ResponseEntity.ok("Restaurante cadastrado com sucesso!");
	}

	@GetMapping("/{nome}")
	public ResponseEntity<?> buscarRestaurantePorNome(@PathVariable String nome) {
		List<Restaurante> restaurantes = gerenciarRestauranteUsecase.buscarRestaurantePorNome(nome);

		List<RestauranteJson> restauranteJsons = restaurantes.stream().map(this::mapToJson).collect(Collectors.toList());

		return ResponseEntity.ok(restauranteJsons);
	}

	@GetMapping("tipoCozinha/{tipoCozinha}")
	public ResponseEntity<?> buscarRestaurantePorTipoCozinha(@PathVariable String tipoCozinha){
		List<Restaurante> restaurantes = gerenciarRestauranteUsecase.buscarRestaurantePorTipoCozinha(tipoCozinha);

		List<RestauranteJson> restauranteJsons = restaurantes.stream().map(this::mapToJson).collect(Collectors.toList());

		return ResponseEntity.ok(restauranteJsons);
	}

	@GetMapping("localizacao/{localizacao}")
	public ResponseEntity<?> buscarRestaurantePorLocalizacao(@PathVariable String localizacao){
		List<Restaurante> restaurantes = gerenciarRestauranteUsecase.buscarRestaurantePorLocalizacao(localizacao);

		List<RestauranteJson> restauranteJsons = restaurantes.stream().map(this::mapToJson).collect(Collectors.toList());

		return ResponseEntity.ok(restauranteJsons);
	}
	///api/v1/restaurantes/disponibilidade?restauranteId=%s&dataReserva=%s
	@GetMapping("disponibilidade")
	public ResponseEntity<?> verificarDisponibilidadeLugares(
			@RequestParam Long restauranteId,
			@RequestParam String dataReserva){ //verificar se é valido
		int lugaresDisponiveis = gerenciarRestauranteUsecase.verificarDisponibilidadeLugares(restauranteId, dataReserva);

		return ResponseEntity.ok(lugaresDisponiveis);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarRestaurante(@PathVariable Long id, @RequestBody RestauranteJson restauranteJson){
		Optional<Restaurante> restaurante = gerenciarRestauranteUsecase.atualizarRestaurante(id, mapToDomain(restauranteJson));

		return restaurante.map(r -> ResponseEntity.ok(mapToJson(restaurante))).orElse(ResponseEntity.notFound().build());
	}


	@DeleteMapping("/{id}")
	public void removerRestaurante(@PathVariable Long id){
		gerenciarRestauranteUsecase.removerRestaurante(id);
	}

	private Restaurante mapToDomain(RestauranteJson restauranteJson) {
		return new Restaurante(
				restauranteJson.getId(),
				restauranteJson.getNome(),
				restauranteJson.getQuantidadeLugares(),
				restauranteJson.getLocalizacao(),
				restauranteJson.getTipoCozinha(),
				restauranteJson.getHorarioFuncionamento()
		);
	}

	private RestauranteJson mapToJson(Optional<Restaurante> restaurante) {
		return restaurante.map(r -> new RestauranteJson(
				r.getId(),
				r.getNome(),
				r.getQuantidadeLugares(),
				r.getLocalizacao(),
				r.getTipoCozinha(),
				r.getHorarioFuncionamento()
		)).orElse(null);
	}

	private RestauranteJson mapToJson(Restaurante restaurante) {
		return new RestauranteJson(
				restaurante.getId(),
				restaurante.getNome(),
				restaurante.getQuantidadeLugares(),
				restaurante.getLocalizacao(),
				restaurante.getTipoCozinha(),
				restaurante.getHorarioFuncionamento()
		);
	}
}

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

import java.util.Optional;

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
		gerenciarRestauranteUsecase.cadastrar(mapToDomain(restauranteJson));

		return ResponseEntity.ok("Restaurante cadastrado com sucesso!");
	}

	@GetMapping("/{nome}")
	public ResponseEntity<?> buscarRestaurantePorNome(@PathVariable String nome) {
		try {
			Optional<Restaurante> restaurante = gerenciarRestauranteUsecase.buscarRestaurantePorNome(nome);

			return restaurante.map(r -> ResponseEntity.ok(mapToJson(restaurante))).orElse(ResponseEntity.notFound().build());

		} catch (Exception e) {
			throw new ErroAcessarRepositorioException();
		}
	}

	@GetMapping("tipoCozinha/{tipoCozinha}")
	public ResponseEntity<?> buscarRestaurantePorTipoCozinha(@PathVariable String tipoCozinha){
		try {
			Optional<Restaurante> restaurante = gerenciarRestauranteUsecase.buscarRestaurantePorTipoCozinha(tipoCozinha);

			return restaurante.map(r -> ResponseEntity.ok(mapToJson(restaurante))).orElse(ResponseEntity.notFound().build());

		} catch (Exception e) {
			throw new ErroAcessarRepositorioException();
		}
	}

	@GetMapping("localizacao/{localizacao}")
	public ResponseEntity<?> buscarRestaurantePorLocalizacao(@PathVariable String localizacao){
		try {
			Optional<Restaurante> restaurante = gerenciarRestauranteUsecase.buscarRestaurantePorLocalizacao(localizacao);

			return restaurante.map(r -> ResponseEntity.ok(mapToJson(restaurante))).orElse(ResponseEntity.notFound().build());

		} catch (Exception e) {
			throw new ErroAcessarRepositorioException();
		}
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
}

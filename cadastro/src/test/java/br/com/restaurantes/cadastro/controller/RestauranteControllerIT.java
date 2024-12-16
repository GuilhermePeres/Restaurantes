package br.com.restaurantes.cadastro.controller;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import br.com.restaurantes.cadastro.gateway.database.jpa.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class RestauranteControllerIT {

    @Autowired
    RestauranteController restauranteController;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @Sql(scripts = {"/clean.sql"})
    public void deveCadastrarRestaurante() {
        RestauranteJson restauranteJson = new RestauranteJson(
                null,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        );

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/restaurantes", restauranteJson, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurante cadastrado com sucesso!", response.getBody());

        List<RestauranteEntity> restaurantesCadastrados = restauranteRepository.findByNome("Tio Zeca Lanches");
        assertFalse(restaurantesCadastrados.isEmpty(), "Deveria encontrar o restaurante");
        assertEquals(restauranteJson.getNome(), restaurantesCadastrados.get(0).getNome());
        assertEquals(restauranteJson.getQuantidadeLugares(), restaurantesCadastrados.get(0).getQuantidadeLugares());
        assertEquals(restauranteJson.getLocalizacao(), restaurantesCadastrados.get(0).getLocalizacao());
        assertEquals(restauranteJson.getTipoCozinha(), restaurantesCadastrados.get(0).getTipoCozinha());
        assertEquals(restauranteJson.getHorarioFuncionamento(), restaurantesCadastrados.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveBuscarRestaurantePorNome() throws Exception {
        String nome = "Tio Zeca Lanches";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));

        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/restaurantes/" + nome, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Object> restaurantesRetornados = response.getBody();
        List<Restaurante> restaurantes = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            restaurantes.add(restaurante);
        }

        assertFalse(restaurantes.isEmpty());
        assertEquals(restaurantesEsperados.get(0).getId(), restaurantes.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantes.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantes.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantes.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantes.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantes.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveBuscarRestaurantePorTipoCozinha() {
        String tipoCozinha = "Restaurante Chinês";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));

        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/restaurantes/tipoCozinha/" + tipoCozinha, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Object> restaurantesRetornados = response.getBody();
        List<Restaurante> restaurantes = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            restaurantes.add(restaurante);
        }

        assertFalse(restaurantes.isEmpty());
        assertEquals(restaurantesEsperados.get(0).getId(), restaurantes.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantes.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantes.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantes.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantes.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantes.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveBuscarRestaurantePorLocalizacao() {
        String localizacao = "Rua Almirante";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));
        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/restaurantes/localizacao/" + localizacao, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Object> restaurantesRetornados = response.getBody();
        List<Restaurante> restaurantes = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            restaurantes.add(restaurante);
        }

        assertFalse(restaurantes.isEmpty());
        assertEquals(restaurantesEsperados.get(0).getId(), restaurantes.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantes.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantes.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantes.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantes.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantes.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveVerificarDisponibilidadeLugaresDoRestaurante() {
        Long id = 1L;
        String dataReserva = "2024-12-11T19:00";
        int lugaresDisponiveisEsperados = 50;

        String url = String.format("/api/v1/restaurantes/disponibilidade?restauranteId=%s&dataReserva=%s", id, dataReserva.toString());

        ResponseEntity<Integer> response = restTemplate.getForEntity(url, Integer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lugaresDisponiveisEsperados, response.getBody());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveAtualizarRestaurante() throws Throwable {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Tio Zeca Lanches 2",
                50,
                "Rua Almirante 2",
                "Restaurante Chinês 2",
                "06:00 às 21:00"
        );

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(restauranteAtualizado);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<RestauranteJson> response = restTemplate.exchange(
                "/api/v1/restaurantes/" + id,
                HttpMethod.PUT,
                request,
                RestauranteJson.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(restauranteAtualizado.getId(), response.getBody().getId());
        assertEquals(restauranteAtualizado.getNome(), response.getBody().getNome());
        assertEquals(restauranteAtualizado.getQuantidadeLugares(), response.getBody().getQuantidadeLugares());
        assertEquals(restauranteAtualizado.getLocalizacao(), response.getBody().getLocalizacao());
        assertEquals(restauranteAtualizado.getTipoCozinha(), response.getBody().getTipoCozinha());
        assertEquals(restauranteAtualizado.getHorarioFuncionamento(), response.getBody().getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveRemoverRestaurante() {
        Long id = 1L;

        restTemplate.delete("/api/v1/restaurantes/" + id);

        Optional<RestauranteEntity> restauranteRetornado = restauranteRepository.findById(id);

        assertFalse(restauranteRetornado.isPresent(), "Não deveria encontrar o restaurante");
    }
}

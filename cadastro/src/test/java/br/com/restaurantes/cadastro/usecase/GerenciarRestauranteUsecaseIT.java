package br.com.restaurantes.cadastro.usecase;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import br.com.restaurantes.cadastro.gateway.database.jpa.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class GerenciarRestauranteUsecaseIT {

    @Autowired
    private GerenciarRestauranteUsecase gerenciarRestauranteUsecase;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    @Sql(scripts = {"/clean.sql"})
     void deveCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante(
                null,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        );

        gerenciarRestauranteUsecase.cadastrarRestaurante(restaurante);

        List<RestauranteEntity> restaurantesCadastrados = restauranteRepository.findByNome("Tio Zeca Lanches");
        assertFalse(restaurantesCadastrados.isEmpty(), "Deveria encontrar o restaurante");
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
     void deveBuscarRestaurantePorNome() {
        String nome = "Tio Zeca Lanches";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));
        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorNome(nome);

        assertNotNull(restaurantesRetornados);
        assertFalse(restaurantesRetornados.isEmpty());
        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
     void deveBuscarRestaurantePorTipoCozinha() {
        String tipoCozinha = "Restaurante Chinês";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));
        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorTipoCozinha(tipoCozinha);

        assertNotNull(restaurantesRetornados);
        assertFalse(restaurantesRetornados.isEmpty());
        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
     void deveBuscarRestaurantePorLocalizacao() {
        String localizacao = "Rua Almirante";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));
        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorLocalizacao(localizacao);

        assertNotNull(restaurantesRetornados);
        assertFalse(restaurantesRetornados.isEmpty());
        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
     void deveVerificarDisponibilidadeLugaresDoRestaurante() {
        Long id = 1L;
        String dataReserva = "2024-12-11T19:00";
        int lugaresDisponiveisEsperados = 50;

        int lugaresDisponiveis = gerenciarRestauranteUsecase.verificarDisponibilidadeLugares(id, dataReserva);

        assertEquals(lugaresDisponiveisEsperados, lugaresDisponiveis);
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
     void deveAtualizarRestaurante() {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Tio Zeca Lanches 2",
                50,
                "Rua Almirante 2",
                "Restaurante Chinês 2",
                "06:00 às 21:00"
        );

        Optional<Restaurante> restauranteRetornado = gerenciarRestauranteUsecase.atualizarRestaurante(id, restauranteAtualizado);

        assertTrue(restauranteRetornado.isPresent());
        assertEquals(restauranteAtualizado.getId(), restauranteRetornado.get().getId());
        assertEquals(restauranteAtualizado.getNome(), restauranteRetornado.get().getNome());
        assertEquals(restauranteAtualizado.getQuantidadeLugares(), restauranteRetornado.get().getQuantidadeLugares());
        assertEquals(restauranteAtualizado.getLocalizacao(), restauranteRetornado.get().getLocalizacao());
        assertEquals(restauranteAtualizado.getTipoCozinha(), restauranteRetornado.get().getTipoCozinha());
        assertEquals(restauranteAtualizado.getHorarioFuncionamento(), restauranteRetornado.get().getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
     void deveRemoverRestaurante() {
        Long id = 1L;

        gerenciarRestauranteUsecase.removerRestaurante(id);

        Optional<RestauranteEntity> restauranteRetornado = restauranteRepository.findById(id);

        assertFalse(restauranteRetornado.isPresent(), "Não deveria encontrar o restaurante");
    }
}

package br.com.restaurantes.cadastro.gateway.database.jpa;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.exception.IllegalArgumentException;
import br.com.restaurantes.cadastro.exception.RestauranteNaoEncontradoException;
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
public class RestauranteJpaGatewayIT {
    @Autowired
    private RestauranteJpaGateway restauranteJpaGateway;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante(
                1L,
                "Restaurante da Ana",
                20,
                "Rua Palmeiras",
                "Japonesa",
                "06:00 às 21:00"
        );

        restauranteJpaGateway.cadastrarRestaurante(restaurante);

        List<RestauranteEntity> restaurantesCadastrados = restauranteRepository.findByNome("Restaurante da Ana");
        assertFalse(restaurantesCadastrados.isEmpty(), "Deveria encontrar o restaurante");
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveBuscarRestaurantePorNome() {
        String nome = "Tio Zeca Lanches";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Tio Zeca Lanches",
                50,
                "Rua Almirante",
                "Restaurante Chinês",
                "24h"
        ));
        List<Restaurante> restaurantesRetornados = restauranteJpaGateway.buscarRestaurantePorNome(nome);

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
    @Sql(scripts = {"/clean.sql"})
    public void deveBuscarRestaurantePorNome_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        String nome = "Tio Zeca Lanches";

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.buscarRestaurantePorNome(nome);});
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
        List<Restaurante> restaurantesRetornados = restauranteJpaGateway.buscarRestaurantePorTipoCozinha(tipoCozinha);

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
    @Sql(scripts = {"/clean.sql"})
    public void deveBuscarRestaurantePorTipoCozinha_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        String tipoCozinha = "Restaurante Chinês";

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.buscarRestaurantePorTipoCozinha(tipoCozinha);});
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
        List<Restaurante> restaurantesRetornados = restauranteJpaGateway.buscarRestaurantePorLocalizacao(localizacao);

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
    @Sql(scripts = {"/clean.sql"})
    public void deveBuscarRestaurantePorLocalizacao_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        String localizacao = "Rua Almirante";

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.buscarRestaurantePorLocalizacao(localizacao);});
    }


    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveVerificarDisponibilidadeLugaresDoRestauranteDeNoite() {
        Long id = 1L;
        String dataReserva = "2024-12-11T19:00";
        int lugaresDisponiveisEsperados = 50;

        int lugaresDisponiveis = restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);

        assertEquals(lugaresDisponiveisEsperados, lugaresDisponiveis);
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveVerificarDisponibilidadeLugaresDoRestauranteDeDia() {
        Long id = 1L;
        String dataReserva = "2024-12-11T15:00";
        int lugaresDisponiveisEsperados = 25;

        int lugaresDisponiveis = restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);

        assertEquals(lugaresDisponiveisEsperados, lugaresDisponiveis);
    }

    @Test
    @Sql(scripts = {"/clean.sql"})
    public void deveVerificarDisponibilidadeLugaresDoRestauranteDeDia_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        Long id = 1L;
        String dataReserva = "2024-12-11T19:00";

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);});
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveVerificarDisponibilidadeLugaresDoRestauranteDeDia_LancarIllegalArgumentException_QuandoDataReservaComFormatoInesperado() {
        Long id = 1L;
        String dataReserva = "11-12-2024 19:00";

        assertThrows(IllegalArgumentException.class, () -> {restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);});
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveAtualizarRestaurante() {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Tio Zeca Lanches 2",
                50,
                "Rua Almirante 2",
                "Restaurante Chinês 2",
                "06:00 às 21:00"
        );

        Optional<Restaurante> restauranteRetornado = restauranteJpaGateway.atualizarRestaurante(id, restauranteAtualizado);

        assertTrue(restauranteRetornado.isPresent());
        assertEquals(restauranteAtualizado.getId(), restauranteRetornado.get().getId());
        assertEquals(restauranteAtualizado.getNome(), restauranteRetornado.get().getNome());
        assertEquals(restauranteAtualizado.getQuantidadeLugares(), restauranteRetornado.get().getQuantidadeLugares());
        assertEquals(restauranteAtualizado.getLocalizacao(), restauranteRetornado.get().getLocalizacao());
        assertEquals(restauranteAtualizado.getTipoCozinha(), restauranteRetornado.get().getTipoCozinha());
        assertEquals(restauranteAtualizado.getHorarioFuncionamento(), restauranteRetornado.get().getHorarioFuncionamento());
    }

    @Test
    @Sql(scripts = {"/clean.sql"})
    public void deveAtualizarRestaurante_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Tio Zeca Lanches 2",
                50,
                "Rua Almirante 2",
                "Restaurante Chinês 2",
                "06:00 às 21:00"
        );

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.atualizarRestaurante(id, restauranteAtualizado);});
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/restaurante.sql"})
    public void deveRemoverRestaurante() {
        Long id = 1L;

        restauranteJpaGateway.removerRestaurante(id);

        Optional<RestauranteEntity> restauranteRetornado = restauranteRepository.findById(id);

        assertFalse(restauranteRetornado.isPresent(), "Não deveria encontrar o restaurante");
    }
}

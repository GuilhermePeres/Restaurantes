package br.com.restaurantes.cadastro.gateway.database.jpa;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.exception.IllegalArgumentException;
import br.com.restaurantes.cadastro.exception.RestauranteNaoEncontradoException;
import br.com.restaurantes.cadastro.gateway.database.jpa.entity.RestauranteEntity;
import br.com.restaurantes.cadastro.gateway.database.jpa.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteJpaGatewayTest {
    @InjectMocks
    private RestauranteJpaGateway restauranteJpaGateway;

    @Mock
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    public void deveCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        );

        RestauranteEntity restauranteEntity = new RestauranteEntity(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        );

        restauranteJpaGateway.cadastrarRestaurante(restaurante);

        Mockito.verify(restauranteRepository, Mockito.times(1)).save(restauranteEntity);
    }

    @Test
    public void deveBuscarRestaurantePorNome() {
        String nome = "Restaurante do Jonas";

        List<RestauranteEntity> restaurantesEsperados = Arrays.asList(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteRepository.findByNome(nome)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = restauranteJpaGateway.buscarRestaurantePorNome(nome);

        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
        Mockito.verify(restauranteRepository, Mockito.times(1)).findByNome(nome);
    }

    @Test
    public void deveBuscarRestaurantePorNome_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        String nome = "Restaurante do Jonas";

        Mockito.when(restauranteRepository.findByNome(nome)).thenReturn(Collections.emptyList());

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.buscarRestaurantePorNome(nome);});
        Mockito.verify(restauranteRepository, Mockito.times(1)).findByNome(nome);
    }

    @Test
    public void deveBuscarRestaurantePorTipoCozinha() {
        String tipoCozinha = "Italiana";

        List<RestauranteEntity> restaurantesEsperados = Arrays.asList(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteRepository.findByTipoCozinha(tipoCozinha)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = restauranteJpaGateway.buscarRestaurantePorTipoCozinha(tipoCozinha);

        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
        Mockito.verify(restauranteRepository, Mockito.times(1)).findByTipoCozinha(tipoCozinha);
    }

    @Test
    public void deveBuscarRestaurantePorTipoCozinha_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        String tipoCozinha = "Italiana";

        Mockito.when(restauranteRepository.findByTipoCozinha(tipoCozinha)).thenReturn(Collections.emptyList());

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.buscarRestaurantePorTipoCozinha(tipoCozinha);});
        Mockito.verify(restauranteRepository, Mockito.times(1)).findByTipoCozinha(tipoCozinha);
    }

    @Test
    public void deveBuscarRestaurantePorLocalizacao() {
        String localizacao = "Rua Alves";

        List<RestauranteEntity> restaurantesEsperados = Arrays.asList(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteRepository.findByLocalizacao(localizacao)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = restauranteJpaGateway.buscarRestaurantePorLocalizacao(localizacao);

        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
        Mockito.verify(restauranteRepository, Mockito.times(1)).findByLocalizacao(localizacao);
    }

    @Test
    public void deveBuscarRestaurantePorLocalizacao_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        String localizacao = "Rua Alves";

        Mockito.when(restauranteRepository.findByLocalizacao(localizacao)).thenReturn(Collections.emptyList());

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.buscarRestaurantePorLocalizacao(localizacao);});
        Mockito.verify(restauranteRepository, Mockito.times(1)).findByLocalizacao(localizacao);
    }


    @Test
    public void deveVerificarDisponibilidadeDeLugaresDoRestauranteDeNoite(){
        Long id = 1L;
        String dataReserva = "2024-12-11 19:00";
        int lugaresDisponiveisEsperados = 100;

        Optional<RestauranteEntity> restaurantesEsperados = Optional.of(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteRepository.findById(id)).thenReturn(restaurantesEsperados);

        int lugaresDisponiveis = restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);

        assertEquals(lugaresDisponiveisEsperados, lugaresDisponiveis);
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void deveVerificarDisponibilidadeDeLugaresDoRestauranteDeDia(){
        Long id = 1L;
        String dataReserva = "2024-12-11 15:00";
        int lugaresDisponiveisEsperados = 50;

        Optional<RestauranteEntity> restaurantesEsperados = Optional.of(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteRepository.findById(id)).thenReturn(restaurantesEsperados);

        int lugaresDisponiveis = restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);

        assertEquals(lugaresDisponiveisEsperados, lugaresDisponiveis);
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void deveVerificarDisponibilidadeDeLugaresDoRestaurante_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        Long id = 1L;
        String dataReserva = "2024-12-11 19:00";

        Mockito.when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);});
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void deveVerificarDisponibilidadeDeLugaresDoRestaurante_LancarIllegalArgumentException_QuandoDataReservaComFormatoInesperado() {
        Long id = 1L;
        String dataReserva = "11-12-2024 19:00";

        Optional<RestauranteEntity> restaurantesEsperados = Optional.of(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteRepository.findById(id)).thenReturn(restaurantesEsperados);

        assertThrows(IllegalArgumentException.class, () -> {restauranteJpaGateway.verificarDisponibilidadeLugares(id, dataReserva);});
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void deveAtualizarRestaurante() {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alvez 2",
                "Italiana 2",
                "06:00 às 20:00"
        );

        Optional<RestauranteEntity> restauranteAntigoRetornado = Optional.of(new RestauranteEntity(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        RestauranteEntity restauranteAtualizadoSalvo = new RestauranteEntity(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alvez 2",
                "Italiana 2",
                "06:00 às 20:00"
        );

        Mockito.when(restauranteRepository.findById(id)).thenReturn(restauranteAntigoRetornado);
        Mockito.when(restauranteRepository.save(restauranteAtualizadoSalvo)).thenReturn(restauranteAtualizadoSalvo);

        Optional<Restaurante> restauranteRetornado = restauranteJpaGateway.atualizarRestaurante(id, restauranteAtualizado);

        assertTrue(restauranteRetornado.isPresent());
        assertEquals(restauranteAtualizado.getId(), restauranteRetornado.get().getId());
        assertEquals(restauranteAtualizado.getNome(), restauranteRetornado.get().getNome());
        assertEquals(restauranteAtualizado.getQuantidadeLugares(), restauranteRetornado.get().getQuantidadeLugares());
        assertEquals(restauranteAtualizado.getLocalizacao(), restauranteRetornado.get().getLocalizacao());
        assertEquals(restauranteAtualizado.getTipoCozinha(), restauranteRetornado.get().getTipoCozinha());
        assertEquals(restauranteAtualizado.getHorarioFuncionamento(), restauranteRetornado.get().getHorarioFuncionamento());
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void deveAtualizarRestaurante_LancarRestauranteNaoEncontradoException_QuandoRestauranteNaoForEncontrado() {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alvez 2",
                "Italiana 2",
                "06:00 às 20:00"
        );

        Mockito.when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> {restauranteJpaGateway.atualizarRestaurante(id, restauranteAtualizado);});
        Mockito.verify(restauranteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void deveRemoverRestaurante() {
        Long id = 1L;

        restauranteJpaGateway.removerRestaurante(id);

        Mockito.verify(restauranteRepository, Mockito.times(1)).deleteById(id);
    }
}

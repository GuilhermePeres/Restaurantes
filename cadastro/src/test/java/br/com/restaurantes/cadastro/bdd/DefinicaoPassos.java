package br.com.restaurantes.cadastro.bdd;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefinicaoPassos {
    private Response response;

    private RestauranteJson restauranteJson;

    private String endpoint = "http://localhost:8080/api/v1/restaurantes";

    private String dataReserva = "2024-12-11T19:00";

    @Dado("que eu tenho os dados de um restaurante")
    public void queEuTenhoOsDadosDeUmRestaurante() {
        restauranteJson = new RestauranteJson(null,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        );
    }

    @Quando("enviar requisição para cadastrar o restaurante")
    public void enviarRequisicaoParaCadastrarORestaurante() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteJson)
                .when()
                .post(endpoint);
    }

    @Entao("o restaurante deve ser cadastrado com sucesso")
    public void oRestauranteDeveSerCadastradoComSucesso() {
        String corpoResposta = response.then().statusCode(HttpStatus.OK.value()).extract().asString();

        assertEquals("Restaurante cadastrado com sucesso!", corpoResposta);
    }

    @Quando("enviar requisição para buscar restaurantes por nome")
    public void enviarRequisicaoParaBuscarRestaurantesPorNome(){
        response = given().get("/api/v1/restaurantes/{nome}", restauranteJson.getNome());
    }

    @Entao("a resposta deve conter os restaurantes buscados por nome")
    public void aRespostaDeveConterOsRestaurantesBuscadosPorNome(){
        List<Object> restaurantesRetornados = response.as(List.class);

        assertFalse(restaurantesRetornados.isEmpty());

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            assertEquals(restauranteJson.getNome(), restaurante.getNome());
        }
    }

    @Quando("enviar requisição para buscar restaurantes por tipoCozinha")
    public void enviarRequisicaoParaBuscarRestaurantesPorTipoCozinha(){
        response = given().get("/api/v1/restaurantes/tipoCozinha/{tipoCozinha}", restauranteJson.getTipoCozinha());
    }

    @Entao("a resposta deve conter os restaurantes buscados por tipoCozinha")
    public void aRespostaDeveConterOsRestaurantesBuscadosPorTipoCozinha(){
        List<Object> restaurantesRetornados = response.as(List.class);

        assertFalse(restaurantesRetornados.isEmpty());

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            assertEquals(restauranteJson.getTipoCozinha(), restaurante.getTipoCozinha());
        }
    }

    @Quando("enviar requisição para buscar restaurantes por localizacao")
    public void enviarRequisicaoParaBuscarRestaurantesPorLocalizacao(){
        response = given().get("/api/v1/restaurantes/localizacao/{localizacao}", restauranteJson.getLocalizacao());
    }

    @Entao("a resposta deve conter os restaurantes buscados por localizacao")
    public void aRespostaDeveConterOsRestaurantesBuscadosPorLocalizacao(){
        List<Object> restaurantesRetornados = response.as(List.class);

        assertFalse(restaurantesRetornados.isEmpty());

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            assertEquals(restauranteJson.getLocalizacao(), restaurante.getLocalizacao());
        }
    }

    @Dado("que eu tenho os dados de um restaurante com id")
    public void queEuTenhoOsDadosDeUmRestauranteComId() {

        enviarRequisicaoParaBuscarRestaurantesPorNome();

        List<Object> restaurantesRetornados = response.as(List.class);

        assertFalse(restaurantesRetornados.isEmpty());

        List<Restaurante> restaurantes = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Object restauranteObj : restaurantesRetornados){
            Restaurante restaurante = objectMapper.convertValue(restauranteObj, Restaurante.class);
            restaurantes.add(restaurante);
        }

        restauranteJson = new RestauranteJson(
                restaurantes.get(0).getId(),
                restaurantes.get(0).getNome(),
                restaurantes.get(0).getQuantidadeLugares(),
                restaurantes.get(0).getLocalizacao(),
                restaurantes.get(0).getTipoCozinha(),
                restaurantes.get(0).getHorarioFuncionamento()
        );
    }

    @Quando("enviar requisição para verificar a disponibilidade de lugares")
    public void enviarRequisicaoParaVerificarADisponibilidadeDeLugares() {
        response = given()
                .param("restauranteId", restauranteJson.getId())
                .param("dataReserva", dataReserva)
                .get("/api/v1/restaurantes/disponibilidade");
    }

    @Entao("a resposta deve conter o número de lugares disponíveis")
    public void aRespostaDeveConterONumeroDeLugaresDisponiveis() {
        int lugaresDisponiveis = response.then().statusCode(HttpStatus.OK.value()).extract().as(Integer.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dataReserva, formatter);

        LocalDateTime inicioNoite = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(18, 0));

        boolean noite = dateTime.isAfter(inicioNoite);

        if(noite){
            assertEquals(restauranteJson.getQuantidadeLugares(), lugaresDisponiveis);
        }else{
            assertEquals(restauranteJson.getQuantidadeLugares()/2, lugaresDisponiveis);
        }
    }

    @Quando("enviar requisição para atualizar o restaurante")
    public void enviarRequisicaoParaAtualizarORestaurante(){

        restauranteJson = new RestauranteJson(
                restauranteJson.getId(),
                restauranteJson.getNome() + "_atualizado",
                restauranteJson.getQuantidadeLugares() + 2,
                restauranteJson.getLocalizacao() + "_atualizado",
                restauranteJson.getTipoCozinha() + "_atualizado",
                "09:00 às 23:00"
        );

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteJson)
                .put("/api/v1/restaurantes/" + restauranteJson.getId());
    }

    @Entao("a resposta deve conter o restaurante atualizado")
    public void aRespostaDeveConterORestauranteAtualizado(){
        assertEquals(HttpStatus.OK.value(), this.response.getStatusCode());

        assertNotNull(response.getBody());

        RestauranteJson restauranteJsonRetornado = response.as(RestauranteJson.class);

        assertEquals(restauranteJson.getId(), restauranteJsonRetornado.getId());
        assertEquals(restauranteJson.getNome(), restauranteJsonRetornado.getNome());
        assertEquals(restauranteJson.getQuantidadeLugares(), restauranteJsonRetornado.getQuantidadeLugares());
        assertEquals(restauranteJson.getLocalizacao(), restauranteJsonRetornado.getLocalizacao());
        assertEquals(restauranteJson.getTipoCozinha(), restauranteJsonRetornado.getTipoCozinha());
        assertEquals(restauranteJson.getHorarioFuncionamento(), restauranteJsonRetornado.getHorarioFuncionamento());
    }

    @Quando("enviar requisição para remover o restaurante")
    public void enviarRequisicaoParaRemoverORestaurante(){
        response = given().delete("/api/v1/restaurantes/" + restauranteJson.getId());
    }

    @Entao("o restaurante deve ser removido")
    public void oRestauranteDeveSerRemovido(){
        response.then().statusCode(HttpStatus.OK.value());
    }
}

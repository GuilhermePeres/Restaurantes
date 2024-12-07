package br.com.restaurantes.cadastro.bdd;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefinicaoPassos {
    private Response response;

    private RestauranteJson restauranteJson;

    private String endpoint = "http://localhost:8080/api/v1/restaurantes";

    @Quando("criar novo restaurante")
    public void criarNovoRestaurante() {
        RestauranteJson restauranteJson = new RestauranteJson(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        );

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(restauranteJson).when().post(endpoint);

        this.restauranteJson = response.then().extract().as(RestauranteJson.class);
    }

    @Entao("o restaurante é criado com sucesso")
    public void restauranteCriado() {
        this.restauranteJson = response.then().statusCode(HttpStatus.CREATED.value()).body("$", hasKey("id")).extract().as(RestauranteJson.class);
        System.out.println();
    }


}

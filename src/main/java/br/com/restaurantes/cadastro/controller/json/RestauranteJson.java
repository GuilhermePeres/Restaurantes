package br.com.restaurantes.cadastro.controller.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestauranteJson {
	private Long id;
	@NotBlank
	private String nome;
	private Integer quantidadeLugares;
	@NotBlank
	private String localizacao;
	@NotNull
	private String tipoCozinha;
	@NotBlank
	private String horarioFuncionamento;
}
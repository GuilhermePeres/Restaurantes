package br.com.restaurantes.cadastro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Restaurante {
	private Long id;
	private String nome;
	private Integer quantidadeLugares;
	private String localizacao;
	private String tipoCozinha;
	private String horarioFuncionamento;
}

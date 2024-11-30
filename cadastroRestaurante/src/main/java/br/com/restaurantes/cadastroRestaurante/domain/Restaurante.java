package br.com.restaurantes.cadastroRestaurante.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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

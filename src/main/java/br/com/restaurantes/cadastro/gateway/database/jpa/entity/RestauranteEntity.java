package br.com.restaurantes.cadastro.gateway.database.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Restaurante")
public class RestauranteEntity  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Integer quantidadeLugares;
	private String localizacao;
	private String tipoCozinha;
	private String horarioFuncionamento;
}

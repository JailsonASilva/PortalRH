package br.com.projeto.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "vaga_perfil")
public class VagaPerfil extends GenericDomain {
	@ManyToOne
	@JoinColumn(nullable = false)
	private Vaga vaga;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Perfil perfil;

	@Column(nullable = false)
	private Long quantidadeVaga;

	@Column(nullable = false)
	private Boolean ativo;

	@Column(nullable = false, length = 2000)
	private String funcao;

	@Column(nullable = false, length = 2000)
	private String requisitos;

	@Column(nullable = false, length = 2000)
	private String desejavel;

	@Column(nullable = false, precision = 6, scale = 2)
	private double renumeracao;

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getQuantidadeVaga() {
		return quantidadeVaga;
	}

	public void setQuantidadeVaga(Long quantidadeVaga) {
		this.quantidadeVaga = quantidadeVaga;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public String getDesejavel() {
		return desejavel;
	}

	public void setDesejavel(String desejavel) {
		this.desejavel = desejavel;
	}

	public double getRenumeracao() {
		return renumeracao;
	}

	public void setRenumeracao(double renumeracao) {
		this.renumeracao = renumeracao;
	}

	@Transient
	public String getRenumeracaoFormatada() {
		String renumeracaoFormatada;

		if (renumeracao <= 0) {
			renumeracaoFormatada = "Não Informada";
		} else {
			renumeracaoFormatada = String.valueOf(renumeracao) + "0";
		}

		return renumeracaoFormatada;
	}

	@Transient
	public String getQuantidadeVagaFormatada() {
		String QuantidadeVagaFormatada;

		if (quantidadeVaga <= 0L) {
			QuantidadeVagaFormatada = "Não Informada";
		} else {
			QuantidadeVagaFormatada = String.valueOf(quantidadeVaga);
		}

		return QuantidadeVagaFormatada;
	}

	@Transient
	public String getAtivoFormatado() {
		String ativoFormatado = "Não";

		if (ativo) {
			ativoFormatado = "Sim";
		}

		return ativoFormatado;
	}

}

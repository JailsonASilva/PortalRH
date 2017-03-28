package br.com.projeto.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "vaga")
public class Vaga extends GenericDomain {
	@Column(nullable = false, length = 100)
	private String nome;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataFinal;

	@Column(nullable = false)
	private Boolean ativo;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Etapa etapaInicial;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataResultadoEtapa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Etapa getEtapaInicial() {
		return etapaInicial;
	}

	public void setEtapaInicial(Etapa etapaInicial) {
		this.etapaInicial = etapaInicial;
	}

	public Date getDataResultadoEtapa() {
		return dataResultadoEtapa;
	}

	public void setDataResultadoEtapa(Date dataResultadoEtapa) {
		this.dataResultadoEtapa = dataResultadoEtapa;
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

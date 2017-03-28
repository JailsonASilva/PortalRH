package br.com.projeto.domain;

import java.text.SimpleDateFormat;
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
@Table(name = "curriculo_vaga")
public class CurriculoVaga extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Curriculo curriculo;

	@ManyToOne
	@JoinColumn(nullable = false)
	private VagaPerfil vagaPerfil;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Etapa etapa;

	@Column(nullable = false, length = 60)
	private String status;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataFinal;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataResultado;

	@Column(nullable = true, length = 2000)
	private String retornoRH;

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

	public VagaPerfil getVagaPerfil() {
		return vagaPerfil;
	}

	public void setVagaPerfil(VagaPerfil vagaPerfil) {
		this.vagaPerfil = vagaPerfil;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
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

	public Date getDataResultado() {
		return dataResultado;
	}

	public void setDataResultado(Date dataResultado) {
		this.dataResultado = dataResultado;
	}

	public String getRetornoRH() {
		return retornoRH;
	}

	public void setRetornoRH(String retornoRH) {
		this.retornoRH = retornoRH;
	}

	@Transient
	public String getDataInicialFormatada() {
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dataInicial);

		return dataFormatada;
	}

	@Transient
	public String getDataFinalFormatada() {
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dataFinal);

		return dataFormatada;
	}

	@Transient
	public String getDataResultadoFormatada() {
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dataResultado);

		return dataFormatada;
	}

}

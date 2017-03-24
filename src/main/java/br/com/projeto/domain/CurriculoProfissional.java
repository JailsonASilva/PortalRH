package br.com.projeto.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "curriculo_experiencia_profissional")
public class CurriculoProfissional extends GenericDomain {
	@ManyToOne
	@JoinColumn(nullable = false)
	private Curriculo curriculo;

	@Column(nullable = false, length = 100)
	private String empresa;

	@Column(nullable = false, length = 100)
	private String cargo;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataFinal;

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
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

}

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
@Table(name = "curriculo_formacao_academico")
public class CurriculoAcademico extends GenericDomain {

	@Column(nullable = false, length = 100)
	private String curso;

	@Column(nullable = false, length = 100)
	private String instituicao;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataFinal;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Curriculo curriculo;

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
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

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

}

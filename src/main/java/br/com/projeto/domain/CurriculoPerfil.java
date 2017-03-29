package br.com.projeto.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "curriculo_perfil")
public class CurriculoPerfil extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Curriculo curriculo;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Perfil perfil;

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}

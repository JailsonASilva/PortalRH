package br.com.projeto.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;

import br.com.projeto.domain.Curriculo;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class PrincipalBean implements Serializable {
	private AutenticacaoBean autenticacaoBean;
	private Curriculo usuario;

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public Curriculo getUsuario() {
		return usuario;
	}

	public void setUsuario(Curriculo usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void bemVindo() {
		AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
	}
}

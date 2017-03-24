package br.com.projeto.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.projeto.domain.Curriculo;
import br.com.projeto.util.EmailUtils;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class FaleConoscoBean implements Serializable {
	private String assunto;
	private String texto;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void enviar() {
		try {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Curriculo curriculo = autenticacaoBean.getUsuarioLogado();

			texto = texto + "\n" + "" + "\n" + "Dados do Remetente" + "\n" + "Nome: " + curriculo.getNome() + "\n"
					+ "E-mail: " + curriculo.getEmail() + "\n" + "CPF: " + curriculo.getCpf() + "\n" + "Fone: "
					+ curriculo.getFoneCelular1();

			EmailUtils.enviaEmail(assunto, texto, "rh@facema.edu.br");

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Enviado com Sucesso!",
					"Seu contato foi enviado com Sucesso! Em breve entraremos em contato."));

			assunto = null;
			texto = null;

		} catch (Exception erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Ocorreu um Erro ao Tentar Enviar: " + erro.getMessage());
		}
	}

}

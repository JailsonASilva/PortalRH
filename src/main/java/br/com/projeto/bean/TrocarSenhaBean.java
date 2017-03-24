package br.com.projeto.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;

import br.com.projeto.dao.CurriculoDAO;
import br.com.projeto.domain.Curriculo;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class TrocarSenhaBean implements Serializable {
	private String novaSenha;
	private String confirmeSenha;

	private FacesMessage message;

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmeSenha() {
		return confirmeSenha;
	}

	public void setConfirmeSenha(String confirmeSenha) {
		this.confirmeSenha = confirmeSenha;
	}

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public void trocarSenha() {
		try {
			if (!novaSenha.equals(confirmeSenha)) {

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Aviso!", "Senhas não conferem. Por favor tente novamente"));

				return;
			}

			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Curriculo curriculo = autenticacaoBean.getUsuarioLogado();

			SimpleHash hash = new SimpleHash("md5", novaSenha);

			curriculo.setSenha(hash.toHex());

			CurriculoDAO curriculoDAO = new CurriculoDAO();
			curriculoDAO.merge(curriculo);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Senha Salva com Sucesso!"));

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').hide();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Trocar de Senha.",
					"Erro Inesperado!");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}
}

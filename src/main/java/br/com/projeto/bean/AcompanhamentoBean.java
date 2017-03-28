package br.com.projeto.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.projeto.dao.CurriculoVagaDAO;
import br.com.projeto.domain.Curriculo;
import br.com.projeto.domain.CurriculoVaga;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class AcompanhamentoBean implements Serializable {

	private CurriculoVaga curriculoVaga;
	private List<CurriculoVaga> curriculoVagas;

	private FacesMessage message;

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public CurriculoVaga getCurriculoVaga() {
		return curriculoVaga;
	}

	public void setCurriculoVaga(CurriculoVaga curriculoVaga) {
		this.curriculoVaga = curriculoVaga;
	}

	public List<CurriculoVaga> getCurriculoVagas() {
		return curriculoVagas;
	}

	public void setCurriculoVagas(List<CurriculoVaga> curriculoVagas) {
		this.curriculoVagas = curriculoVagas;
	}

	@PostConstruct
	public void listarSelecao() {
		try {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Curriculo curriculo = autenticacaoBean.getUsuarioLogado();

			CurriculoVagaDAO curriculoVagaDAO = new CurriculoVagaDAO();
			curriculoVagas = curriculoVagaDAO.listarSelecao(curriculo.getCodigo());

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Listar Seleções.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void onRowSelect(SelectEvent event) {

	}

	public void onRowUnselect(UnselectEvent event) {
		curriculoVaga = null;
	}

	public void cancelar() {
		try {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Curriculo curriculo = autenticacaoBean.getUsuarioLogado();

			CurriculoVagaDAO curriculoVagaDAO = new CurriculoVagaDAO();
			curriculoVagaDAO.excluir(curriculoVaga);

			curriculoVagas = curriculoVagaDAO.listarSelecao(curriculo.getCodigo());

			curriculoVaga = null;

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Cancelamento Efetuado com Sucesso!"));

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').hide();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Cancelar Seleção.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void duploClique(SelectEvent evento) {
		try {

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

}

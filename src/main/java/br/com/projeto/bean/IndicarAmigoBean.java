package br.com.projeto.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.projeto.dao.VagaPerfilDAO;
import br.com.projeto.domain.Curriculo;
import br.com.projeto.domain.CurriculoVaga;
import br.com.projeto.domain.VagaPerfil;
import br.com.projeto.util.EmailUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class IndicarAmigoBean implements Serializable {
	private CurriculoVaga curriculoVaga;
	private FacesMessage message;

	private String email;

	private VagaPerfil vagaPerfil;
	private List<VagaPerfil> vagasDisponiveis;

	public List<VagaPerfil> getVagasDisponiveis() {
		return vagasDisponiveis;
	}

	public void setVagasDisponiveis(List<VagaPerfil> vagasDisponiveis) {
		this.vagasDisponiveis = vagasDisponiveis;
	}

	public CurriculoVaga getCurriculoVaga() {
		return curriculoVaga;
	}

	public void setCurriculoVaga(CurriculoVaga curriculoVaga) {
		this.curriculoVaga = curriculoVaga;
	}

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public VagaPerfil getVagaPerfil() {
		return vagaPerfil;
	}

	public void setVagaPerfil(VagaPerfil vagaPerfil) {
		this.vagaPerfil = vagaPerfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@PostConstruct
	public void inicializar() {
		try {
			VagaPerfilDAO vagaPerfilDAO = new VagaPerfilDAO();
			vagasDisponiveis = vagaPerfilDAO.listarVagas();

		} catch (Exception erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Não foi possível Listar as Vagas Disponíveis. Erro: " + erro.getMessage());
		}
	}

	public void duploClique(SelectEvent evento) {
		try {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoVaga').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado!");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void onRowSelect(SelectEvent event) {

	}

	public void onRowUnselect(UnselectEvent event) {
		vagaPerfil = null;
	}

	public void indicar() throws EmailException {
		try {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Curriculo curriculo = autenticacaoBean.getUsuarioLogado();

			String mensagem = "Olá! Tudo Bem?" + "\n" + "\n" + curriculo.getNome() + " lhe indicou uma vaga de "
					+ vagaPerfil.getPerfil().getNome()
					+ ", para participar do processo seletivo acesse wwww.facema.edu.br." + "\n" + "\n" + "" + "\n"
					+ "Atenciosamente, " + "\n" + "" + "\n" + "Faculdade de Ciências e Tecnologia do Maranhão" + "\n"
					+ "" + "Fone: (99) 3422-6800" + "\n" + "" + "\n"
					+ "Esta mensagem foi enviada por um sistema automático. Favor, não respondê-la.";

			EmailUtils.enviaEmail("Indicação Vaga - Facema", mensagem, email);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Enviado com Sucesso!", "Indicação feita com Sucesso!"));

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoVaga').hide();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Indicar a Vaga.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

}

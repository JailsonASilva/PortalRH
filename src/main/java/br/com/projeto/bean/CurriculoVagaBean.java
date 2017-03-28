package br.com.projeto.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.projeto.dao.CurriculoVagaDAO;
import br.com.projeto.dao.VagaPerfilDAO;
import br.com.projeto.domain.Curriculo;
import br.com.projeto.domain.CurriculoVaga;
import br.com.projeto.domain.VagaPerfil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CurriculoVagaBean implements Serializable {

	private CurriculoVaga curriculoVaga;
	private FacesMessage message;

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

	@PostConstruct
	public void inicializar() {
		try {
			VagaPerfilDAO vagaPerfilDAO = new VagaPerfilDAO();
			vagasDisponiveis = vagaPerfilDAO.listarVagas();

		} catch (Exception erro) {
			erro.printStackTrace();
			Messages.addGlobalError(
					"Não foi possível Listar as Vagas Disponíveis a Autenticação. Erro: " + erro.getMessage());
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

	public void candidatar() {
		try {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Curriculo curriculo = autenticacaoBean.getUsuarioLogado();

			CurriculoVagaDAO curriculoVagaDAO = new CurriculoVagaDAO();

			if (curriculoVagaDAO.verificarDuplicidade(curriculo.getCodigo(), vagaPerfil.getCodigo()) == true) {

				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!",
						"Operação não Permitida! Você não pode se candidatar-se a mesma vaga duas vezes.");

				RequestContext.getCurrentInstance().showMessageInDialog(message);

				return;
			}

			curriculoVaga = new CurriculoVaga();

			curriculoVaga.setCurriculo(curriculo);
			curriculoVaga.setStatus("Em andamento");
			curriculoVaga.setVagaPerfil(vagaPerfil);
			curriculoVaga.setDataInicial(vagaPerfil.getVaga().getDataInicial());
			curriculoVaga.setDataFinal(vagaPerfil.getVaga().getDataFinal());
			curriculoVaga.setEtapa(vagaPerfil.getVaga().getEtapaInicial());
			curriculoVaga.setDataResultado(vagaPerfil.getVaga().getDataResultadoEtapa());
			curriculoVaga.setRetornoRH("Aguarde... Em breve entraremos em contato");

			curriculoVagaDAO.merge(curriculoVaga);

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parabéns!",
					"Parabéns você candidatou-se a essa Vaga com Sucesso! Para acompanhar sua seleção acesse o menu 'Acompanhar Seleção'.");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoVaga').hide();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Candidata-se a Vaga.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

}

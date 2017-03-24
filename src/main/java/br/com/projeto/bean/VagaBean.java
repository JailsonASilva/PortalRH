
package br.com.projeto.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.projeto.dao.PerfilDAO;
import br.com.projeto.dao.VagaDAO;
import br.com.projeto.dao.VagaPerfilDAO;
import br.com.projeto.domain.Perfil;
import br.com.projeto.domain.Vaga;
import br.com.projeto.domain.VagaPerfil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class VagaBean implements Serializable {
	private Vaga vaga;
	private List<Vaga> vagas;

	private Perfil perfil;
	private List<Perfil> perfis;

	private VagaPerfil vagaPerfil;
	private List<VagaPerfil> vagasPerfis;

	private FacesMessage message;
	private String busca;

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<Vaga> getVagas() {
		return vagas;
	}

	public void setVagas(List<Vaga> vagas) {
		this.vagas = vagas;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public VagaPerfil getVagaPerfil() {
		return vagaPerfil;
	}

	public void setVagaPerfil(VagaPerfil vagaPerfil) {
		this.vagaPerfil = vagaPerfil;
	}

	public List<VagaPerfil> getVagasPerfis() {
		return vagasPerfis;
	}

	public void setVagasPerfis(List<VagaPerfil> vagasPerfis) {
		this.vagasPerfis = vagasPerfis;
	}

	@PostConstruct
	public void inicializar() {
		try {
			PerfilDAO perfilDAO = new PerfilDAO();
			perfis = perfilDAO.listar("nome");

			perfil = new Perfil();

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Inicializar Tabelas.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void pesquisar() {
		try {
			VagaDAO vagaDAO = new VagaDAO();
			vagas = vagaDAO.pesquisar(busca);

			vaga = null;

			if (vagas.isEmpty() == true) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro foi Encontrado! Por favor Tente Novamente.", "Registro não Encontrado!");

				RequestContext.getCurrentInstance().showMessageInDialog(message);
			}

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Pesquisar Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void novo() {
		vaga = new Vaga();
		vaga.setAtivo(true);
	}

	public void duploClique(SelectEvent evento) {
		try {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado!");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			VagaDAO vagaDAO = new VagaDAO();
			vagaDAO.merge(vaga);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').hide();");

			vagas = vagaDAO.listar("nome");

			vaga = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Salvar este Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			VagaDAO vagaDAO = new VagaDAO();
			vagaDAO.excluir(vaga);

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Excluído com Sucesso!",
					"Registro: " + vaga.getNome());

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			vagas = vagaDAO.listar("nome");

			vaga = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void onRowSelect(SelectEvent event) {

	}

	public void onRowUnselect(UnselectEvent event) {
		vaga = null;
	}

	public void listarVagaPerfis(ActionEvent evento) {
		try {
			vaga = (Vaga) evento.getComponent().getAttributes().get("vagaSelecionado");

			VagaPerfilDAO vagaPerfilDAO = new VagaPerfilDAO();
			vagasPerfis = vagaPerfilDAO.pesquisarVagaPerfil(vaga.getCodigo());

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Abrir Ocorrencias.",
					"Erro Inesperado!");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void novoVinculoPerfil() {
		vagaPerfil = new VagaPerfil();

		vagaPerfil.setVaga(vaga);
		vagaPerfil.setAtivo(true);
	}

	public void salvarVagaPerfil() {
		try {
			VagaPerfilDAO vagaperfilDAO = new VagaPerfilDAO();
			vagaperfilDAO.merge(vagaPerfil);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoVincularPerfil').hide();");

			vagasPerfis = vagaperfilDAO.pesquisarVagaPerfil(vaga.getCodigo());

			vagaPerfil = new VagaPerfil();

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Salvar este Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void salvarPerfil() {
		try {
			PerfilDAO perfilDAO = new PerfilDAO();
			perfilDAO.merge(perfil);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoPerfil').hide();");

			perfis = perfilDAO.listar("nome");

			perfil = new Perfil();

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Salvar este Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void editarVagaPerfil(ActionEvent evento) {
		try {
			vagaPerfil = (VagaPerfil) evento.getComponent().getAttributes().get("vagaPerfilSelecionado");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Selecionar este Registro.", "Erro Inesperado!");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluirVagaPerfil(ActionEvent evento) {
		try {
			vagaPerfil = (VagaPerfil) evento.getComponent().getAttributes().get("vagaPerfilSelecionado");

			VagaPerfilDAO vagaPerfilDAO = new VagaPerfilDAO();
			vagaPerfilDAO.excluir(vagaPerfil);

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Excluído com Sucesso!",
					"Perfil Excluído: " + vagaPerfil.getPerfil().getNome());

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			vagasPerfis = vagaPerfilDAO.pesquisarVagaPerfil(vaga.getCodigo());

			vagaPerfil = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}
}

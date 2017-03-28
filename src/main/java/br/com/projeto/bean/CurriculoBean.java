package br.com.projeto.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.projeto.dao.CurriculoAcademicoDAO;
import br.com.projeto.dao.CurriculoCursoDAO;
import br.com.projeto.dao.CurriculoDAO;
import br.com.projeto.dao.CurriculoPessoalDAO;
import br.com.projeto.dao.CurriculoProfissionalDAO;
import br.com.projeto.domain.Curriculo;
import br.com.projeto.domain.CurriculoAcademico;
import br.com.projeto.domain.CurriculoCurso;
import br.com.projeto.domain.CurriculoPessoal;
import br.com.projeto.domain.CurriculoProfissional;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CurriculoBean implements Serializable {
	private Curriculo curriculo;
	private List<Curriculo> curriculos;

	private CurriculoAcademico curriculoAcademico;
	private List<CurriculoAcademico> curriculoAcademicos;

	private CurriculoCurso curriculoCurso;
	private List<CurriculoCurso> curriculoCursos;

	private CurriculoProfissional curriculoProfissional;
	private List<CurriculoProfissional> curriculoProfissionais;

	private CurriculoPessoal curriculoPessoal;
	private List<CurriculoPessoal> curriculoPessoais;

	private FacesMessage message;
	private String busca;
	private String cpf;
	private boolean proximo;

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

	public List<Curriculo> getCurriculos() {
		return curriculos;
	}

	public void setCurriculos(List<Curriculo> curriculos) {
		this.curriculos = curriculos;
	}

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

	public CurriculoAcademico getCurriculoAcademico() {
		return curriculoAcademico;
	}

	public void setCurriculoAcademico(CurriculoAcademico curriculoAcademico) {
		this.curriculoAcademico = curriculoAcademico;
	}

	public List<CurriculoAcademico> getCurriculoAcademicos() {
		return curriculoAcademicos;
	}

	public void setCurriculoAcademicos(List<CurriculoAcademico> curriculoAcademicos) {
		this.curriculoAcademicos = curriculoAcademicos;
	}

	public CurriculoCurso getCurriculoCurso() {
		return curriculoCurso;
	}

	public void setCurriculoCurso(CurriculoCurso curriculoCurso) {
		this.curriculoCurso = curriculoCurso;
	}

	public List<CurriculoCurso> getCurriculoCursos() {
		return curriculoCursos;
	}

	public void setCurriculoCursos(List<CurriculoCurso> curriculoCursos) {
		this.curriculoCursos = curriculoCursos;
	}

	public CurriculoProfissional getCurriculoProfissional() {
		return curriculoProfissional;
	}

	public void setCurriculoProfissional(CurriculoProfissional curriculoProfissional) {
		this.curriculoProfissional = curriculoProfissional;
	}

	public List<CurriculoProfissional> getCurriculoProfissionais() {
		return curriculoProfissionais;
	}

	public void setCurriculoProfissionais(List<CurriculoProfissional> curriculoProfissionais) {
		this.curriculoProfissionais = curriculoProfissionais;
	}

	public CurriculoPessoal getCurriculoPessoal() {
		return curriculoPessoal;
	}

	public void setCurriculoPessoal(CurriculoPessoal curriculoPessoal) {
		this.curriculoPessoal = curriculoPessoal;
	}

	public List<CurriculoPessoal> getCurriculoPessoais() {
		return curriculoPessoais;
	}

	public void setCurriculoPessoais(List<CurriculoPessoal> curriculoPessoais) {
		this.curriculoPessoais = curriculoPessoais;
	}

	public boolean isProximo() {
		return proximo;
	}

	public void setProximo(boolean proximo) {
		this.proximo = proximo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@PostConstruct
	public void localizar() {
		try {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			curriculo = autenticacaoBean.getUsuarioLogado();

			abrirTabelas();

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Abrir Dados do Currículo.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void abrirTabelas() {
		try {
			listarCurriculoAcademico();
			listarCurriculoCurso();
			listarCurriculoProfissional();
			listarCurriculoPessoal();

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Abrir as Tabelas.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void novo() {
		curriculo = new Curriculo();

		curriculoAcademicos = null;
		curriculoCursos = null;
		curriculoProfissionais = null;
		curriculoPessoais = null;
	}

	public void novoFormacaoAcademica() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		cpf = curriculo.getCpf();

		curriculoAcademico = new CurriculoAcademico();
	}

	public void novoCurso() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculoCurso = new CurriculoCurso();
		curriculoCurso.setCurriculo(curriculo);
	}

	public void novoProfissional() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculoProfissional = new CurriculoProfissional();
		curriculoProfissional.setCurriculo(curriculo);
	}

	public void novoPessoal() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculoPessoal = new CurriculoPessoal();
		curriculoPessoal.setCurriculo(curriculo);
	}

	public void listarCurriculoAcademico() {
		try {
			CurriculoAcademicoDAO curriculoAcademicoDAO = new CurriculoAcademicoDAO();
			curriculoAcademicos = curriculoAcademicoDAO.listarCurriculoAcademico(curriculo.getCodigo());

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Listar Dados Acadêmicos.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void listarCurriculoCurso() {
		try {
			CurriculoCursoDAO curriculoCursoDAO = new CurriculoCursoDAO();
			curriculoCursos = curriculoCursoDAO.listarCurriculoCurso(curriculo.getCodigo());

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Listar Dados do Curso.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void listarCurriculoProfissional() {
		try {
			CurriculoProfissionalDAO curriculoProfissionalDAO = new CurriculoProfissionalDAO();
			curriculoProfissionais = curriculoProfissionalDAO.listarCurriculoProfissional(curriculo.getCodigo());

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Listar Dados Profissionais.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void listarCurriculoPessoal() {
		try {
			CurriculoPessoalDAO curriculoPessoalDAO = new CurriculoPessoalDAO();
			curriculoPessoais = curriculoPessoalDAO.listarCurriculoPessoal(curriculo.getCodigo());

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Listar Referências Pessoais.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CurriculoDAO curriculoDAO = new CurriculoDAO();
			curriculoDAO.merge(curriculo);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').hide();");

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Currículo Salvo com Sucesso!"));

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Salvar este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void salvarCurriculoAcademico() {
		try {
			CurriculoAcademicoDAO curriculoAcademicoDAO = new CurriculoAcademicoDAO();
			curriculoAcademicoDAO.merge(curriculoAcademico);

			org.primefaces.context.RequestContext.getCurrentInstance()
					.execute("PF('dialogoFormacaoAcademica').hide();");

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Curso Salvo com Sucesso!"));

			curriculoAcademicos = curriculoAcademicoDAO.listar("curso");

			curriculoAcademico = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Salvar Dados Acadêmico este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void salvarCurriculoCurso() {
		try {
			CurriculoCursoDAO curriculoCursoDAO = new CurriculoCursoDAO();
			curriculoCursoDAO.merge(curriculoCurso);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoCurso').hide();");

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Curso Salvo com Sucesso!"));

			curriculoCursos = curriculoCursoDAO.listar("curso");

			curriculoCurso = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Salvar Dados do Curso este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void salvarCurriculoProfissional() {
		try {
			CurriculoProfissionalDAO curriculoProfissionalDAO = new CurriculoProfissionalDAO();
			curriculoProfissionalDAO.merge(curriculoProfissional);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoProfissional').hide();");

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Dados Profissionais Salvo com Sucesso!"));

			curriculoProfissionais = curriculoProfissionalDAO.listar("empresa");

			curriculoProfissional = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Salvar Dados Profissionais este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void salvarCurriculoPessoal() {
		try {
			CurriculoPessoalDAO curriculoPessoalDAO = new CurriculoPessoalDAO();
			curriculoPessoalDAO.merge(curriculoPessoal);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoPessoal').hide();");

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Dados Pessoais Salvo com Sucesso!"));

			curriculoPessoais = curriculoPessoalDAO.listar("nome");

			curriculoPessoal = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Salvar Dados Pessoais este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			CurriculoDAO curriculoDAO = new CurriculoDAO();
			curriculoDAO.excluir(curriculo);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "Curso Excluído com Sucesso!"));

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			curriculos = curriculoDAO.listar("nome");

			curriculo = null;

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluirCurriculoAcademico(ActionEvent evento) {
		try {
			curriculoAcademico = (CurriculoAcademico) evento.getComponent().getAttributes()
					.get("curriculoAcademicoSelecionado");

			CurriculoAcademicoDAO curriculoAcademicoDAO = new CurriculoAcademicoDAO();
			curriculoAcademicoDAO.excluir(curriculoAcademico);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Exclusão!", "Curso Excluído com Sucesso!"));

			curriculoAcademicos = curriculoAcademicoDAO.listarCurriculoAcademico(curriculo.getCodigo());

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluirCurriculoCurso(ActionEvent evento) {
		try {
			curriculoCurso = (CurriculoCurso) evento.getComponent().getAttributes().get("curriculoCursoSelecionado");

			CurriculoCursoDAO curriculoCursoDAO = new CurriculoCursoDAO();
			curriculoCursoDAO.excluir(curriculoCurso);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Exclusão!", "Curso Excluído com Sucesso!"));

			curriculoCursos = curriculoCursoDAO.listarCurriculoCurso(curriculo.getCodigo());

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluirCurriculoProfissional(ActionEvent evento) {
		try {
			curriculoProfissional = (CurriculoProfissional) evento.getComponent().getAttributes()
					.get("curriculoProfissionalSelecionado");

			CurriculoProfissionalDAO curriculoProfissionalDAO = new CurriculoProfissionalDAO();
			curriculoProfissionalDAO.excluir(curriculoProfissional);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Exclusão!", "Dados Profissionais Excluído com Sucesso!"));

			curriculoProfissionais = curriculoProfissionalDAO.listarCurriculoProfissional(curriculo.getCodigo());

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void excluirCurriculoPessoal(ActionEvent evento) {
		try {
			curriculoPessoal = (CurriculoPessoal) evento.getComponent().getAttributes()
					.get("curriculoPessoalSelecionado");

			CurriculoPessoalDAO curriculoPessoalDAO = new CurriculoPessoalDAO();
			curriculoPessoalDAO.excluir(curriculoPessoal);

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Exclusão!", "Dados Pessoais Excluído com Sucesso!"));

			curriculoPessoais = curriculoPessoalDAO.listarCurriculoPessoal(curriculo.getCodigo());

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Excluir este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void editarCurriculoAcademico(ActionEvent evento) {
		try {
			curriculoAcademico = (CurriculoAcademico) evento.getComponent().getAttributes()
					.get("curriculoAcademicoSelecionado");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Selecionar este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void editarCurriculoCurso(ActionEvent evento) {
		try {
			curriculoCurso = (CurriculoCurso) evento.getComponent().getAttributes().get("curriculoCursoSelecionado");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Selecionar este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void editarCurriculoProfissional(ActionEvent evento) {
		try {
			curriculoProfissional = (CurriculoProfissional) evento.getComponent().getAttributes()
					.get("curriculoProfissionalSelecionado");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Selecionar este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void editarCurriculoPessoal(ActionEvent evento) {
		try {
			curriculoPessoal = (CurriculoPessoal) evento.getComponent().getAttributes()
					.get("curriculoPessoalSelecionado");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um Erro ao Tentar Selecionar este Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void duploClique(SelectEvent evento) {
		try {
			abrirTabelas();

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void duploCliqueAcademico(SelectEvent evento) {
		try {
			org.primefaces.context.RequestContext.getCurrentInstance()
					.execute("PF('dialogoFormacaoAcademica').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void duploCliqueCurso(SelectEvent evento) {
		try {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoCurso').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void duploCliqueProfissional(SelectEvent evento) {
		try {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoProfissional').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void duploCliquePessoal(SelectEvent evento) {
		try {
			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoPessoal').show();");

		} catch (RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Selecionar Registro.",
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

			RequestContext.getCurrentInstance().showMessageInDialog(message);
			erro.printStackTrace();
		}
	}

	public void redirencionar() {
		try {
			Faces.redirect("./pages/curriculo.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Não foi possível Executar este Comando. Erro: " + erro.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {

	}

	public void onRowUnselect(UnselectEvent event) {
		curriculo = null;
	}
}

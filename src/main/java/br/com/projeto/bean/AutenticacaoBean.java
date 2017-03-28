package br.com.projeto.bean;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
import br.com.projeto.util.EmailUtils;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	private Curriculo usuario;
	private Curriculo usuarioLogado;

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

	private String cpf;

	public Curriculo getUsuario() {
		return usuario;
	}

	public void setUsuario(Curriculo usuario) {
		this.usuario = usuario;
	}

	public Curriculo getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Curriculo usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void esqueciMinhaSenha() {
		cpf = "";
	}

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

	public List<Curriculo> getCurriculos() {
		return curriculos;
	}

	public void setCurriculos(List<Curriculo> curriculos) {
		this.curriculos = curriculos;
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

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public void novo() {

		if (validaCpf() == false) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "CPF inválido! Por favor tente novamente."));

			return;
		}

		if (cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666")
				|| cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999")
				|| cpf.equals("00000000000")) {

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!", "CPF inválido! Por favor tente novamente."));

			return;
		}

		CurriculoDAO curriculoDAO = new CurriculoDAO();

		if (curriculoDAO.verificarDuplicidade(cpf) == true) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Aviso!",
					"CPF já Cadastrado! Caso não lembre sua senha clique em 'Esqueci minha Senha'."));

			return;
		}

		curriculo = new Curriculo();
		curriculo.setCpf(cpf);

		curriculoAcademicos = null;
		curriculoCursos = null;
		curriculoProfissionais = null;
		curriculoPessoais = null;

		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').show();");
	}

	public void novoFormacaoAcademica() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculo = curriculoDAO.localizarCurriculo(curriculo.getCpf());

		curriculoAcademico = new CurriculoAcademico();
		curriculoAcademico.setCurriculo(curriculo);
	}

	public void novoCurso() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculo = curriculoDAO.localizarCurriculo(curriculo.getCpf());

		curriculoCurso = new CurriculoCurso();
		curriculoCurso.setCurriculo(curriculo);
	}

	public void novoProfissional() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculo = curriculoDAO.localizarCurriculo(curriculo.getCpf());

		curriculoProfissional = new CurriculoProfissional();
		curriculoProfissional.setCurriculo(curriculo);
	}

	public void novoPessoal() {
		CurriculoDAO curriculoDAO = new CurriculoDAO();
		curriculoDAO.merge(curriculo);

		curriculo = curriculoDAO.localizarCurriculo(curriculo.getCpf());

		curriculoPessoal = new CurriculoPessoal();
		curriculoPessoal.setCurriculo(curriculo);
	}

	public void autenticar() {
		try {
			CurriculoDAO curriculoDAO = new CurriculoDAO();
			usuarioLogado = curriculoDAO.autenticar(usuario.getCpf(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou Senha Incorretos");
				return;
			}

			Faces.redirect("./pages/curriculoVaga.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();

			Messages.addGlobalError("Não foi possível Realizar a Autenticação. Erro: " + erro.getMessage());
		}
	}

	public void desconectar() {
		try {
			usuario = new Curriculo();
			usuarioLogado = null;

			Faces.redirect("./pages/autenticacao.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Não foi possível Desconectar. Erro: " + erro.getMessage());
		}
	}

	public void enviarNovaSenha() {
		try {
			UUID uuid = UUID.randomUUID();
			String novaSenha = uuid.toString();

			SimpleHash hash = new SimpleHash("md5", novaSenha.substring(0, 6));

			String mensagem = "Sua nova senha é: " + novaSenha.substring(0, 6) + "\n" + "" + "\n" + "\n" + "" + "\n"
					+ "Atenciosamente, " + "\n" + "" + "\n" + "Faculdade de Ciências e Tecnologia do Maranhão" + "\n"
					+ "" + "Fone: (99) 3422-6800" + "\n" + "" + "\n"
					+ "Esta mensagem foi enviada por um sistema automático. Favor, não respondê-la.";

			CurriculoDAO curriculoDAO = new CurriculoDAO();

			Curriculo curriculo = curriculoDAO.pesquisarCPF(cpf);

			if (curriculo == null) {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Aviso!", "CPF não Encontrado!"));
				return;
			}

			curriculo.setSenha(hash.toHex());

			curriculoDAO.merge(curriculo);

			EmailUtils.enviaEmail("Currículo Online - Solicitação de uma nova senha", mensagem, curriculo.getEmail());

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null,
					new FacesMessage("Enviado com Sucesso!", "Uma nova senha foi enviada para o seu e-mail!"));

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoSenha').hide();");

		} catch (Exception erro) {
			erro.printStackTrace();

			Messages.addGlobalError("Ocorreu um Erro ao Tentar Enviar a Nova Senha: " + erro.getMessage());
		}

	}

	public void enviarNovaSenhaCadastro() {
		try {
			UUID uuid = UUID.randomUUID();
			String novaSenha = uuid.toString();

			SimpleHash hash = new SimpleHash("md5", novaSenha.substring(0, 6));

			String mensagem = "Sua senha de acesso é: " + novaSenha.substring(0, 6) + "\n" + "" + "\n" + "\n" + ""
					+ "\n" + "Atenciosamente, " + "\n" + "" + "\n" + "Faculdade de Ciências e Tecnologia do Maranhão"
					+ "\n" + "" + "Fone: (99) 3422-6800" + "\n" + "" + "\n"
					+ "Esta mensagem foi enviada por um sistema automático. Favor, não respondê-la.";

			CurriculoDAO curriculoDAO = new CurriculoDAO();
			curriculo = curriculoDAO.localizarCurriculo(curriculo.getCpf());

			if (curriculo == null) {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null,
						new FacesMessage("Ocorreu um Erro!", "Não foi possível identificar o Currículo."));
				return;
			}

			curriculo.setSenha(hash.toHex());
			curriculoDAO.merge(curriculo);

			EmailUtils.enviaEmail("Currículo Online - Senha de Acesso", mensagem, curriculo.getEmail());

			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Currículo Salvo com Sucesso!",
					"Para efetuar o login no sistema use o seu CPF e sua senha de acesso foi enviada para o e-mail cadastrado"));

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoSenha').hide();");

		} catch (Exception erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Ocorreu um Erro ao Tentar Enviar a Nova Senha: " + erro.getMessage());
		}

	}

	public void novoCadastro() {
		try {
			Faces.redirect("./pages/novoCadastro.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Não foi possível Executar este Comando. Erro: " + erro.getMessage());
		}
	}

	@PostConstruct
	public void abrirTabelas() {
		try {
			usuario = new Curriculo();

		} catch (

		RuntimeException erro) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um Erro ao Tentar Abrir as Tabelas.",
					"Erro: " + erro.getMessage());

			RequestContext.getCurrentInstance().showMessageInDialog(message);

			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CurriculoDAO curriculoDAO = new CurriculoDAO();
			curriculoDAO.merge(curriculo);

			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogo').hide();");
			org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoNovo').hide();");

			usuario.setCpf(curriculo.getCpf());

			enviarNovaSenhaCadastro();

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
					"Erro Inesperado! Favor comunicar o RH: (99) 3422-6800 Ramal 845");

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
					"Ocorreu um Erro ao Tentar Selecionar este Registro.", "Erro Inesperado!");

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

	public boolean validaCpf() {
		int cpf[] = new int[11], dv1 = 0, dv2 = 0;

		this.cpf = this.cpf.replace(".", "");
		this.cpf = this.cpf.replace("-", "");

		if (this.cpf.length() != 11)
			return false;

		for (int i = 0; i < 11; i++)
			cpf[i] = Integer.parseInt(this.cpf.substring(i, i + 1));
		for (int i = 0; i < 9; i++)
			dv1 += cpf[i] * (i + 1);
		cpf[9] = dv1 = dv1 % 11;
		for (int i = 0; i < 10; i++)
			dv2 += cpf[i] * i;
		cpf[10] = dv2 = dv2 % 11;
		if (dv1 > 9)
			cpf[9] = 0;
		if (dv2 > 9)
			cpf[10] = 0;

		if (Integer.parseInt(this.cpf.substring(9, 10)) != cpf[9]
				|| Integer.parseInt(this.cpf.substring(10, 11)) != cpf[10])
			return false;
		else
			return true;
	}

	public void iniciarCPF() {
		cpf = null;
	}
}

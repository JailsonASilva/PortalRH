package br.com.projeto.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "curriculo")
public class Curriculo extends GenericDomain {

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(nullable = false, length = 20)
	private String sexo;

	@Column(nullable = false, length = 30)
	private String estadoCivil;

	@Column(nullable = true, length = 30)
	private String foneResidencial;

	@Column(nullable = true, length = 30)
	private String foneCelular1;

	@Column(nullable = true, length = 30)
	private String foneCelular2;

	@Column(nullable = true, length = 30)
	private String foneComercial;

	@Column(nullable = true, length = 30)
	private String foneRecado;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 100)
	private String escolaridade;

	@Column(nullable = false, length = 100)
	private String nascionalidade;

	@Column(nullable = false, length = 100)
	private String naturalidade;

	@Column(nullable = true, length = 100)
	private String pai;

	@Column(nullable = false, length = 100)
	private String mae;

	@Column(nullable = false, length = 20)
	private String cpf;

	@Column(nullable = false, length = 40)
	private String identidade;

	@Column(nullable = false, length = 10)
	private String identidadeOrgao;

	@Column(nullable = false, length = 60)
	private String ctps;

	@Column(nullable = true, length = 60)
	private String ctpsSerie;

	@Column(nullable = true, length = 60)
	private String carteiraHabilitacao;

	@Column(nullable = true, length = 10)
	private String carteiraHabilitacaoCategoria;

	@Column(nullable = false, length = 100)
	private String endereco;

	@Column(nullable = false, length = 10)
	private String numero;

	@Column(nullable = true, length = 100)
	private String complemento;

	@Column(nullable = false, length = 60)
	private String bairro;

	@Column(nullable = false, length = 60)
	private String cidade;

	@Column(nullable = false, length = 60)
	private String estado;

	@Column(nullable = false, length = 60)
	private String pais;

	@Column(nullable = false, length = 2000)
	private String objetivoProfissional;

	@Column(nullable = false, length = 2000)
	private String perfilProfissional;

	@Column(nullable = false, length = 60)
	private String deficiencia;

	@Column(nullable = true, length = 2000)
	private String informacaoAdicional;

	@Column(nullable = true, length = 100)
	private String indicacao;

	@Column(nullable = true, length = 2000)
	private String informacaoRH;

	@Column(length = 100, nullable = true)
	private String senha;

	@Transient
	private String senhaSemCriptografia;

	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}

	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getFoneResidencial() {
		return foneResidencial;
	}

	public void setFoneResidencial(String foneResidencial) {
		this.foneResidencial = foneResidencial;
	}

	public String getFoneCelular1() {
		return foneCelular1;
	}

	public void setFoneCelular1(String foneCelular1) {
		this.foneCelular1 = foneCelular1;
	}

	public String getFoneCelular2() {
		return foneCelular2;
	}

	public void setFoneCelular2(String foneCelular2) {
		this.foneCelular2 = foneCelular2;
	}

	public String getFoneComercial() {
		return foneComercial;
	}

	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}

	public String getFoneRecado() {
		return foneRecado;
	}

	public void setFoneRecado(String foneRecado) {
		this.foneRecado = foneRecado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getNascionalidade() {
		return nascionalidade;
	}

	public void setNascionalidade(String nascionalidade) {
		this.nascionalidade = nascionalidade;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getIdentidadeOrgao() {
		return identidadeOrgao;
	}

	public void setIdentidadeOrgao(String identidadeOrgao) {
		this.identidadeOrgao = identidadeOrgao;
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getCtpsSerie() {
		return ctpsSerie;
	}

	public void setCtpsSerie(String ctpsSerie) {
		this.ctpsSerie = ctpsSerie;
	}

	public String getCarteiraHabilitacao() {
		return carteiraHabilitacao;
	}

	public void setCarteiraHabilitacao(String carteiraHabilitacao) {
		this.carteiraHabilitacao = carteiraHabilitacao;
	}

	public String getCarteiraHabilitacaoCategoria() {
		return carteiraHabilitacaoCategoria;
	}

	public void setCarteiraHabilitacaoCategoria(String carteiraHabilitacaoCategoria) {
		this.carteiraHabilitacaoCategoria = carteiraHabilitacaoCategoria;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getObjetivoProfissional() {
		return objetivoProfissional;
	}

	public void setObjetivoProfissional(String objetivoProfissional) {
		this.objetivoProfissional = objetivoProfissional;
	}

	public String getPerfilProfissional() {
		return perfilProfissional;
	}

	public void setPerfilProfissional(String perfilProfissional) {
		this.perfilProfissional = perfilProfissional;
	}

	public String getDeficiencia() {
		return deficiencia;
	}

	public void setDeficiencia(String deficiencia) {
		this.deficiencia = deficiencia;
	}

	public String getInformacaoAdicional() {
		return informacaoAdicional;
	}

	public void setInformacaoAdicional(String informacaoAdicional) {
		this.informacaoAdicional = informacaoAdicional;
	}

	public String getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}

	public String getInformacaoRH() {
		return informacaoRH;
	}

	public void setInformacaoRH(String informacaoRH) {
		this.informacaoRH = informacaoRH;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}

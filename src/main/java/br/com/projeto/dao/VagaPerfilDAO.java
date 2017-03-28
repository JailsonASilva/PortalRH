package br.com.projeto.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.domain.VagaPerfil;
import br.com.projeto.util.HibernateUtil;

public class VagaPerfilDAO extends GenericDAO<VagaPerfil> {

	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	public List<VagaPerfil> pesquisarVagaPerfil(Long vaga) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(VagaPerfil.class);

			Criteria consultaTicket = consulta.createCriteria("vaga", "vaga", Criteria.INNER_JOIN,
					Restrictions.eq("vaga.codigo", vaga));

			List<VagaPerfil> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	public List<VagaPerfil> listarVagas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(VagaPerfil.class);

			Criteria consultaVaga = consulta.createCriteria("vaga", "vaga", Criteria.INNER_JOIN,
					Restrictions.eq("vaga.ativo", true));	
			
			Criteria consultaPerfil = consulta.createCriteria("perfil", "perfil", Criteria.INNER_JOIN);

			consulta.add(Restrictions.le("vaga.dataInicial", new Date()));
			consulta.add(Restrictions.ge("vaga.dataFinal", new Date()));
			consulta.add(Restrictions.eq("ativo", true));
			
			consulta.addOrder(Order.asc("perfil.nome"));

			List<VagaPerfil> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}

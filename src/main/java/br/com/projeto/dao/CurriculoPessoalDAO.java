package br.com.projeto.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.domain.CurriculoPessoal;
import br.com.projeto.util.HibernateUtil;

public class CurriculoPessoalDAO extends GenericDAO<CurriculoPessoal> {

	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	public List<CurriculoPessoal> listarCurriculoPessoal(Long curriculo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CurriculoPessoal.class);

			Criteria consultaCurriculo = consulta.createCriteria("curriculo", "curriculo", Criteria.INNER_JOIN,
					Restrictions.eq("curriculo.codigo", curriculo));

			List<CurriculoPessoal> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}

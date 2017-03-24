package br.com.projeto.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.domain.CurriculoProfissional;
import br.com.projeto.util.HibernateUtil;

public class CurriculoProfissionalDAO extends GenericDAO<CurriculoProfissional> {

	
	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	public List<CurriculoProfissional> listarCurriculoProfissional(Long curriculo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CurriculoProfissional.class);			
			
			Criteria consultaCurriculo = consulta.createCriteria("curriculo", "curriculo",
					Criteria.INNER_JOIN, Restrictions.eq("curriculo.codigo", curriculo));					
			
			List<CurriculoProfissional> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

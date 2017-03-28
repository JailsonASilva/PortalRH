package br.com.projeto.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.domain.CurriculoVaga;
import br.com.projeto.util.HibernateUtil;

public class CurriculoVagaDAO extends GenericDAO<CurriculoVaga> {

	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	public List<CurriculoVaga> listarSelecao(Long curriculo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(CurriculoVaga.class);

			Criteria consultaCurriculo = consulta.createCriteria("curriculo", "curriculo", Criteria.INNER_JOIN,
					Restrictions.eq("curriculo.codigo", curriculo));

			List<CurriculoVaga> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	public boolean verificarDuplicidade(Long curriculo, Long curriculoVaga) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(CurriculoVaga.class);

			Criteria consultaCurriculo = consulta.createCriteria("curriculo", "curriculo", Criteria.INNER_JOIN,
					Restrictions.eq("curriculo.codigo", curriculo));

			Criteria consultaVagaPerfil = consulta.createCriteria("vagaPerfil", "vagaPerfil", Criteria.INNER_JOIN,
					Restrictions.eq("vagaPerfil.codigo", curriculoVaga));

			List<CurriculoVaga> registro = consulta.list();

			if (registro.size() > 0) {
				return true;
			} else {
				return false;
			}

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

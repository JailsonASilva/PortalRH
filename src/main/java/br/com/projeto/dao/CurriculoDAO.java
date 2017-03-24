package br.com.projeto.dao;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.domain.Curriculo;
import br.com.projeto.util.HibernateUtil;

public class CurriculoDAO extends GenericDAO<Curriculo> {

	public Curriculo autenticar(String cpf, String senha) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Curriculo.class);

			consulta.add(Restrictions.eq("cpf", cpf));

			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Curriculo resultado = (Curriculo) consulta.uniqueResult();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public String novaSenha(String cpf) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Curriculo.class);

			consulta.add(Restrictions.eq("cpf", cpf));

			Curriculo curriculo = (Curriculo) consulta.uniqueResult();

			return curriculo.getEmail();

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public Curriculo pesquisarCPF(String cpf) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Curriculo.class);

			consulta.add(Restrictions.eq("cpf", cpf));

			Curriculo resultado = (Curriculo) consulta.uniqueResult();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean verificarDuplicidade(String cpf) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Curriculo.class);

			cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
			
			consulta.add(Restrictions.eq("cpf", cpf));

			List<Curriculo> resultado = consulta.list();

			if (resultado.size() > 0) {
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

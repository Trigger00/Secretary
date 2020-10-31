package pe.edu.lamolina.security.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

@Repository
public class AutenticacionDAOHibernate extends HibernateTemplate implements AutenticacionDAO {
	
	private final Logger log =Logger.getLogger(AutenticacionDAOHibernate.class);
	
	@Autowired
	public AutenticacionDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public SeguridadUsuarioEntity validarUsuarioDAO(SeguridadUsuarioEntity usuario) {
		final String method="validarUsuarioDAO";
		final String params = "SeguridadUsuarioEntity usuario";
		UNALMLogger.entry(log, method,params,new Object[] { usuario });
		Criteria criteriaList = this.getSessionFactory().getCurrentSession().createCriteria(SeguridadUsuarioEntity.class);
		criteriaList.add(Restrictions.eq("flagEliminado", "0"));
		criteriaList.add(Restrictions.eq("textoLoginMD5", usuario.getTextoLoginMD5()));
		criteriaList.add(Restrictions.eq("textoClaveMD5", usuario.getTextoClaveMD5()));
		SeguridadUsuarioEntity result = (SeguridadUsuarioEntity)criteriaList.uniqueResult();
		UNALMLogger.exit(log, method,result);
		return result;
	}

}

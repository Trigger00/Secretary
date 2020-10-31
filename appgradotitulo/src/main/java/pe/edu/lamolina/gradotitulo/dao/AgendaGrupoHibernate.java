package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class AgendaGrupoHibernate extends HibernateTemplate implements AgendaGrupoDAO {
	
	private final static Logger log = Logger.getLogger(AgendaGrupoHibernate.class);

	@Autowired
	public AgendaGrupoHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<SCGAgendaGrupoEntity> getListAgendaGrupoDAO(SCGAgendaGrupoEntity entity) {
		final String method = "getListAgendaGrupoDAO";
		final String params = "SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGAgendaGrupoEntity.class, "ag");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		List<SCGAgendaGrupoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGAgendaGrupoEntity getForUpdateAgendaGrupoDAO(SCGAgendaGrupoEntity entity) {
		final String method = "getForUpdatePersonaDAO";
		final String params = "SCGPersonaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGAgendaGrupoEntity.class, "ag");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombre())){
				UNALMLogger.trace(log, method, "textoNombre: "+entity.getTextoNombre());
				criteria.add(Restrictions.eq("textoNombre", entity.getTextoNombre()));
			}
			
		}		
		SCGAgendaGrupoEntity result = (SCGAgendaGrupoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGAgendaGrupoEntity saveAgendaGrupoDAO(SCGAgendaGrupoEntity entity) {
		final String method="saveAgendaGrupoDAO";
		final String params="SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAgendaGrupoEntity result=(SCGAgendaGrupoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}
}

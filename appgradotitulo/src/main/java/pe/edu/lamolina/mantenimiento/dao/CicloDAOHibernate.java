package pe.edu.lamolina.mantenimiento.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class CicloDAOHibernate extends HibernateTemplate implements CicloDAO {
	
	private final static Logger log = Logger.getLogger(CicloDAOHibernate.class);

	@Autowired
	public CicloDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<SCGCicloEntity> listCicloDAO(SCGCicloEntity entity, FilterUtil filterUtil) {
		final String method = "listCicloDAO";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ entity,filterUtil });
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGCicloEntity.class );
		criteria.add( Restrictions.eq( "flagEliminado", ApplicationConstant.FLAG_ELIMINADO ));
		
		if (entity != null) {
			if (!TypesUtil.isEmptyString(entity.getTextoCiclo())) {
				 UNALMLogger.trace(log, method, "textoCiclo: "+ entity.getTextoCiclo());
				 criteria.add(Restrictions.ilike("textoCiclo", entity.getTextoCiclo(), MatchMode.ANYWHERE ));
			}
		}
		criteria.addOrder(Order.desc("codigo"));

		
		List<SCGCicloEntity> result = criteria.list();
		if (result.size() > 0){
			Long totalCount = Long.valueOf(result.size());
			filterUtil.setTotalCount(totalCount);
		}
		
		UNALMLogger.trace(log, method, "1_result.size(): "+ result.size() );
		UNALMLogger.trace(log, method, "1_result.size(): "+ result.size() );
		UNALMLogger.trace(log, method, "filterUtil.getStart(): "+ filterUtil.getStart() );
		UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+ filterUtil.getLimit() );
		if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
			UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
			criteria.setFirstResult(filterUtil.getStart());
			UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
			criteria.setMaxResults(filterUtil.getLimit());
		}
		
		result = criteria.list();
		UNALMLogger.trace(log, method, "2_result.size(): "+ result.size() );
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}

	@Override
	public SCGCicloEntity saveCicloDAO(SCGCicloEntity entity) {
		final String method="saveCicloDAO";
		final String params="SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGCicloEntity result = (SCGCicloEntity)this.getSessionFactory().getCurrentSession().merge( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGCicloEntity getForUpdateCicloDAO(SCGCicloEntity entity) {
		final String method = "getForUpdateCicloDAO";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGCicloEntity.class, "c");
		
		criteria.add( Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO ) );
		if (entity !=null) {
			if (entity.getCodigo() != null) {
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
		}
		
		SCGCicloEntity result = (SCGCicloEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}

}

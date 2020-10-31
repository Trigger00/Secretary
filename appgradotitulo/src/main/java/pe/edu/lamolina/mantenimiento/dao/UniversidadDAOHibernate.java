package pe.edu.lamolina.mantenimiento.dao;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoUniversidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class UniversidadDAOHibernate extends HibernateTemplate implements UniversidadDAO {
	
	private final static Logger log = Logger.getLogger(UniversidadDAOHibernate.class);

	@Autowired
	public UniversidadDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	@Override
	public List<SCGTipoUniversidadEntity> listTipoUniversidadDAO() {
		final String method = "listTipoUniversidad";
		final String params = "";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGTipoUniversidadEntity.class );
		criteria.add( Restrictions.eq( "flagEliminado", ApplicationConstant.FLAG_ELIMINADO ));
		List<SCGTipoUniversidadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	@Override
	public List<SCGUniversidadEntity> listUniversidadDAO(
				SCGUniversidadEntity entity,
				FilterUtil filterUtil ){
		final String method = "listUniversidadDAO";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ entity });
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGUniversidadEntity.class );
		criteria.createCriteria("pais", "p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoUniversidad", "tu", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq( "flagEliminado", ApplicationConstant.FLAG_ELIMINADO ));
		if( entity != null ){
			if( entity.getPais() != null ){
				if( entity.getPais().getCodigo() != null ){
					UNALMLogger.trace(log, method, "pais.codigo: "+ entity.getPais().getCodigo() );
					criteria.add(Restrictions.eq("pais.codigo", entity.getPais().getCodigo() ));
				}
				if( !TypesUtil.isEmptyString( entity.getPais().getTextoNombre() )){
					UNALMLogger.trace(log, method, "pais.codigo: "+ entity.getPais().getTextoNombre() );
					criteria.add(Restrictions.eq("pais.textoNombre", entity.getPais().getTextoNombre() ));
				}
			}
			if( entity.getTipoUniversidad() != null ){
				if( entity.getTipoUniversidad().getCodigo() != null ){
					UNALMLogger.trace(log, method, "tipoUniversidad.codigo: "+ entity.getTipoUniversidad().getCodigo() );
					criteria.add(Restrictions.eq("tipoUniversidad.codigo", entity.getTipoUniversidad().getCodigo() ));
				}
				if( !TypesUtil.isEmptyString( entity.getTipoUniversidad().getTextoNombre() )){
					UNALMLogger.trace(log, method, "tipoUniversidad.textoNombre: "+ entity.getTipoUniversidad().getTextoNombre() );
					criteria.add(Restrictions.eq("tipoUniversidad.textoNombre", entity.getTipoUniversidad().getTextoNombre() ));
				}
			}
			if( !TypesUtil.isEmptyString( entity.getTextoNombre() )){
				 UNALMLogger.trace(log, method, "textoNombre: "+ entity.getTextoNombre() );
				 criteria.add(Restrictions.ilike("textoNombre", entity.getTextoNombre(), MatchMode.ANYWHERE ));
			 }
			if( !TypesUtil.isEmptyString( entity.getTextoNombreAbreviado() )){
				UNALMLogger.trace(log, method, "textoNombreAbreviado: "+ entity.getTextoNombreAbreviado() );
				criteria.add(Restrictions.ilike("textoNombreAbreviado", entity.getTextoNombreAbreviado(), MatchMode.ANYWHERE ));
			}
			if( !TypesUtil.isEmptyString( entity.getTextoSiglas() )){
				UNALMLogger.trace(log, method, "textoNombreSiglas: "+ entity.getTextoSiglas() );
				criteria.add(Restrictions.ilike("textoNombreSiglas", entity.getTextoSiglas(), MatchMode.ANYWHERE ));
			}
			
		}
//		Criteria criteriaCount = criteria;
//		criteriaCount =  this.getSessionFactory().getCurrentSession().createCriteria( SCGUniversidadEntity.class );
//		Long totalCount = new Long(		
//				String.valueOf( criteriaCount.setProjection(Projections.count("codigo")).list().get(0))
//				);
//		UNALMLogger.trace(log, method, "totalCount: "+ totalCount );
//		filterUtil.setTotalCount(totalCount);
		
		List<SCGUniversidadEntity> result = criteria.list();
		if( result.size() > 0){
			Long totalCount = Long.valueOf(result.size());
			filterUtil.setTotalCount(totalCount);
		}
		UNALMLogger.trace(log, method, "1_result.size(): "+ result.size() );
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
	public SCGUniversidadEntity  saveUniversidadDAO( SCGUniversidadEntity entity ){
		final String method="saveUniversidadDAO";
		final String params="SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGUniversidadEntity result = (SCGUniversidadEntity)this.getSessionFactory().getCurrentSession().merge( entity );
		UNALMLogger.exit(log, method, result);
		return result;
		
	}
	@Override
	public SCGUniversidadEntity getForUpdateUnverisdadDAO(SCGUniversidadEntity entity) {
		final String method = "getForUpdateUnverisdadDAO";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGUniversidadEntity.class, "u");
			criteria.createCriteria("pais", "p", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("tipoUniversidad", "tu", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO ) );
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
//			if(!TypesUtil.isEmptyString(entity.getTextoNombre())){
//				UNALMLogger.trace(log, method, "textoNombre: "+entity.getTextoNombre());
//				criteria.add(Restrictions.eq("textoNombre", entity.getTextoNombre()));
//			}
			
		}		
		SCGUniversidadEntity result = ( SCGUniversidadEntity )criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void deleteDAO() {
 		
	}

}

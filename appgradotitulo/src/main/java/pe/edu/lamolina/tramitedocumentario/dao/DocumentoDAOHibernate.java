package pe.edu.lamolina.tramitedocumentario.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.dao.UniversidadDAOHibernate;
import pe.edu.lamolina.tramitedocumentario.entity.SCGArchivoTramiteDocumento;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class DocumentoDAOHibernate extends HibernateTemplate implements DocumentoDAO{
	
	
	private final static Logger log = Logger.getLogger(UniversidadDAOHibernate.class);
	
	@Autowired
	public DocumentoDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumentoDAO(SCGArchivoTramiteDocumento entity
			, FilterUtil filterUtil){
		final String method = "listArchivoTramiteDocumentoDAO";
		UNALMLogger.entry(log, method);
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGArchivoTramiteDocumento.class);
			criteria.add( Restrictions.eq( "flagEliminado", "0"));
		
		UNALMLogger.trace(log, method, "entity "+ entity);
		if(entity != null){
			if( !TypesUtil.isEmptyString(entity.getTextoNombreRegistroNoMatch()) ){
				UNALMLogger.trace(log, method, "entity.getTextoNombreArchivoNoMatch() "+ entity.getTextoNombreRegistroNoMatch());
				criteria.add( Restrictions.eq( "textoNombreRegistro", entity.getTextoNombreRegistroNoMatch()) );

			}else{
				if(!TypesUtil.isEmptyString(entity.getTextoNombreRegistro())){
					UNALMLogger.trace(log, method, "entity.getTextoNombreRegistro() "+ entity.getTextoNombreRegistro());
					criteria.add(Restrictions.ilike("textoNombreRegistro",entity.getTextoNombreRegistro(),MatchMode.ANYWHERE ));
				}
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombreArchivo())){
				UNALMLogger.trace(log, method, "entity.getTextoNombreArchivo() "+ entity.getTextoNombreArchivo());
				criteria.add( Restrictions.eq( "textoNombreArchivo", entity.getTextoNombreArchivo()) );
			}

			if(entity.getFechaAgregarInicio() != null && entity.getFechaAgregarFinal() != null){
				UNALMLogger.trace(log, method, "entity.getFechaAgregarInicio(): "+entity.getFechaAgregarInicio());
				UNALMLogger.trace(log, method, "entity.getFechaAgregarFinal(): "+ entity.getFechaAgregarFinal());
				
				criteria.add(Restrictions.ge("fechaAgregar", entity.getFechaAgregarInicio())); 
				criteria.add(Restrictions.le("fechaAgregar", entity.getFechaAgregarFinal()));
			
			}
		}
		List<SCGArchivoTramiteDocumento> result = criteria.list();
		
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
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}

	@Override
	public SCGArchivoTramiteDocumento getForUpdateArchivoTramiteDocumentoDAO(SCGArchivoTramiteDocumento entity) {
		final String method = "getForUpdateArchivoTramiteDocumento";
		final String params = "SCGArchivoTramiteDocumento entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGArchivoTramiteDocumento.class, "atd");
			criteria.add( Restrictions.eq("flagEliminado","0" ) );
			
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}			
		}	
		
		SCGArchivoTramiteDocumento result = ( SCGArchivoTramiteDocumento )criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGArchivoTramiteDocumento saveDAO(SCGArchivoTramiteDocumento entity) {
		final String method="SCGArchivoTramiteDocumento";
		final String params="SCGArchivoTramiteDocumento entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGArchivoTramiteDocumento result = (SCGArchivoTramiteDocumento)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
}

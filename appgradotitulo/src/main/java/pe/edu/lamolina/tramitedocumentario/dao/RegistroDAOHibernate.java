package pe.edu.lamolina.tramitedocumentario.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.dao.UniversidadDAOHibernate;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsableEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGTipoTramiteDocumentoEntity;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class RegistroDAOHibernate extends HibernateTemplate implements RegistroDAO{
	
	private final static Logger log = Logger.getLogger(UniversidadDAOHibernate.class);
	
	@Autowired
	public RegistroDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<SCGTipoTramiteDocumentoEntity> listTipoTramiteDocumentoDAO() {
		final String method = "listTipoTramiteDocumentoDAO";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoTramiteDocumentoEntity.class);
		criteria.add( Restrictions.eq( "flagEliminado", ApplicationConstant.FLAG_ELIMINADO ));
		List<SCGTipoTramiteDocumentoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}

	@Override
	public List<SCGResponsabilidadEntity> listResponsabilidadDAO(SCGResponsabilidadEntity entity) {
		final String method = "listResponsabilidadDAO";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGResponsabilidadEntity.class,"r");
			criteria.createCriteria("r.seguridadUsuario","su", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("r.responsable","re", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("re.definicionProceso","d", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("d.proceso","p", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq( "flagEliminado", "0" ));
//		criteria.add( Restrictions.eq( "d.flagInicio", "1"));
		if(entity.getResponsable()!= null){
			if(entity.getResponsable().getDefinicionProceso()!= null){
				if(entity.getResponsable().getDefinicionProceso() != null){
					if(!TypesUtil.isEmptyString(entity.getResponsable().getDefinicionProceso().getFlagInicio())){
						UNALMLogger.trace(log, method, "entity.getResponsable().getDefinicionProceso().getFlagInicio() "+entity.getResponsable().getDefinicionProceso().getFlagInicio());
						criteria.add( Restrictions.eq( "d.flagInicio", entity.getResponsable().getDefinicionProceso().getFlagInicio()));
					}
					if(entity.getResponsable().getDefinicionProceso().getProceso() != null){
						if(entity.getResponsable().getDefinicionProceso().getProceso().getCodigo() != null){
							UNALMLogger.trace(log, method, "entity.getResponsable().getDefinicionProceso().getProceso().getCodigo() "+entity.getResponsable().getDefinicionProceso().getProceso().getCodigo());
							criteria.add( Restrictions.eq( "p.codigo", entity.getResponsable().getDefinicionProceso().getProceso().getCodigo()));
						}
					}
				}
			}
		}
		List<SCGResponsabilidadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
		
//		List<SCGResponsabilidadEntity> result = new ArrayList<SCGResponsabilidadEntity>();
//		return result;
	}

	@Override
	public List<SCGProcesoEntity> listProcesoDAO() {
		final String method = "listProcesoDAO";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGProcesoEntity.class);
		criteria.add( Restrictions.eq( "flagEliminado", ApplicationConstant.FLAG_ELIMINADO ));
		List<SCGProcesoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	
//	@Override
//	public List<SCGSolicitudProcesoEntity> listSolicitudProcesoDAO(
//			SCGSolicitudProcesoEntity entity,
//			FilterUtil filterUtil
//			) {
//		final String method = "listSolicitudProcesoDAO";
//		final String params = "SCGSolicitudProcesoEntity entity";
//		UNALMLogger.entry(log, method,params,new Object[]{entity});
//		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGSolicitudProcesoEntity.class, "sp");
//			criteria.createCriteria("seguridadUsuario", "p", JoinType.LEFT_OUTER_JOIN);
//			criteria.createCriteria("tipoTramiteDocumento", "ttd", JoinType.LEFT_OUTER_JOIN);
//		criteria.add( Restrictions.eq("flagEliminado", "0" ) );
//		if(entity != null){
//			UNALMLogger.trace(log, method, "entity "+ entity);
//			if(!TypesUtil.isEmptyString(entity.getTextoNombreMatch())){
//				UNALMLogger.trace(log, method, "getTextoNombreMatch"+ entity.getTextoNombreMatch());
//				criteria.add(Restrictions.ilike("textoNumeroDocumento",entity.getTextoNombreMatch(),MatchMode.START ));
//			}
//			if(!TypesUtil.isEmptyString(entity.getTextoNumeroDocumento())){
//				UNALMLogger.trace(log, method, "entity.getTextoNumeroDocumento() "+ entity.getTextoNumeroDocumento());
////				criteria.add( Restrictions.eq("textoNumeroDocumento", entity.getTextoNumeroDocumento() ) );
//				criteria.add(Restrictions.ilike("textoNumeroDocumento",entity.getTextoNumeroDocumento(),MatchMode.START ));
//
//			}
//			if(!TypesUtil.isEmptyString(entity.getTextoNombreProcedencia())){
//				UNALMLogger.trace(log, method, "getTextoNombreProcedencia"+ entity.getTextoNombreProcedencia());
//				criteria.add(Restrictions.ilike("textoNombreProcedencia",entity.getTextoNombreProcedencia(),MatchMode.START ));
////			
////				UNALMLogger.trace(log, method, "entity.getDistinct() : "+ entity.getDistinct());
////				if(entity.getDistinct().equals("textoNombreProcedencia")){
////					UNALMLogger.trace(log, method, "entity.getDistinct() : "+ entity.getDistinct());
////					criteria.setProjection(Projections.distinct(Projections.property("textoNombreProcedencia")));
////				}
//			}
//			if(entity.getFechaAgregarInicio() != null && entity.getFechaAgregarFinal() != null){
//				UNALMLogger.trace(log, method, "entity.getFechaAgregarInicio(): "+entity.getFechaAgregarInicio());
//				UNALMLogger.trace(log, method, "entity.getFechaAgregarFinal(): "+ entity.getFechaAgregarFinal());
//				criteria.add(Restrictions.ge("fechaAgregar", entity.getFechaAgregarInicio())); 
//				criteria.add(Restrictions.le("fechaAgregar", entity.getFechaAgregarFinal()));
//			
//			}
//			if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
//				 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
//				 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
//			 }
//		}		
//		List<SCGSolicitudProcesoEntity> result = criteria.list();
//		if( result.size() > 0){
//			Long totalCount = Long.valueOf(result.size());
//			filterUtil.setTotalCount(totalCount);
//		}
//		UNALMLogger.trace(log, method, "1_result.size(): "+ result.size() );
//		if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
//			UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
//			criteria.setFirstResult(filterUtil.getStart());
//			UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
//			criteria.setMaxResults(filterUtil.getLimit());
//		}
//		result = criteria.list();
//		UNALMLogger.exit(log, method, result.size());		
//		return result;
//	}
	
	@Override
	public List<SCGSolicitudProcesoEntity> listSolicitudProcesoDAO(
			SCGSolicitudProcesoEntity entity,
			FilterUtil filterUtil
			) {
		final String method = "listSolicitudProcesoDAO";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGSolicitudProcesoEntity.class, "sp");
			criteria.createCriteria("seguridadUsuario", "p", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("tipoTramiteDocumento", "ttd", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq("flagEliminado", "0" ) );
		if(entity != null){
			UNALMLogger.trace(log, method, "entity "+ entity);
			if(!TypesUtil.isEmptyString(entity.getTextoNombreMatch())){
				UNALMLogger.trace(log, method, "getTextoNombreMatch"+ entity.getTextoNombreMatch());
				criteria.add(Restrictions.ilike("textoNumeroDocumento",entity.getTextoNombreMatch(),MatchMode.START ));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNumeroDocumento())){
				UNALMLogger.trace(log, method, "entity.getTextoNumeroDocumento() "+ entity.getTextoNumeroDocumento());
//				criteria.add( Restrictions.eq("textoNumeroDocumento", entity.getTextoNumeroDocumento() ) );
				criteria.add(Restrictions.ilike("textoNumeroDocumento",entity.getTextoNumeroDocumento(),MatchMode.START ));

			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombreProcedencia())){
				UNALMLogger.trace(log, method, "getTextoNombreProcedencia"+ entity.getTextoNombreProcedencia());
				criteria.add(Restrictions.ilike("textoNombreProcedencia",entity.getTextoNombreProcedencia(),MatchMode.START ));
//			
//				UNALMLogger.trace(log, method, "entity.getDistinct() : "+ entity.getDistinct());
//				if(entity.getDistinct().equals("textoNombreProcedencia")){
//					UNALMLogger.trace(log, method, "entity.getDistinct() : "+ entity.getDistinct());
//					criteria.setProjection(Projections.distinct(Projections.property("textoNombreProcedencia")));
//				}
			}
			if(entity.getFechaAgregarInicio() != null && entity.getFechaAgregarFinal() != null){
				UNALMLogger.trace(log, method, "entity.getFechaAgregarInicio(): "+entity.getFechaAgregarInicio());
				UNALMLogger.trace(log, method, "entity.getFechaAgregarFinal(): "+ entity.getFechaAgregarFinal());
				criteria.add(Restrictions.ge("fechaAgregar", entity.getFechaAgregarInicio())); 
				criteria.add(Restrictions.le("fechaAgregar", entity.getFechaAgregarFinal()));
			
			}
			if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
				 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
				 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
			 }
		}		
		List<SCGSolicitudProcesoEntity> result = criteria.list();
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
	public List<SCGDefinicionProcesoEntity> listDefinicionProcesoDAO(SCGDefinicionProcesoEntity entity) {
		final String method = "listDefinicionProcesoDAO";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGDefinicionProcesoEntity.class, "dp");
			criteria.createCriteria("dp.proceso","p", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq("flagEliminado","0"));
		if(!TypesUtil.isEmptyString(entity.getFlagInicio())){
			UNALMLogger.trace(log, method, "entity.getFlagInicio() "+entity.getFlagInicio());
			criteria.add( Restrictions.eq( "flagInicio", entity.getFlagInicio()));
		}
		if(entity.getProceso() != null){
			if(entity.getProceso().getCodigo() != null){
				criteria.add( Restrictions.eq( "p.codigo", entity.getProceso().getCodigo()));
			}
		}
		List<SCGDefinicionProcesoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	@Override
	public List<SCGFlujoProcesoEntity> listFlujoProcesoDAO(SCGFlujoProcesoEntity entity, FilterUtil filterUtil) {
		final String method = "listFlujoProcesoDAO";
		final String params = "SCGFlujoProcesoEntity entity, FilterUtil filterUtil";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGFlujoProcesoEntity.class, "fp");
			criteria.createCriteria("responsable", "r", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("seguridadUsuario", "su", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("solicitudProceso", "sp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("dp.proceso", "pr", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		if(entity != null){
//			if(entity.getCodigo() != null){
//				UNALMLogger.trace(log, method, "entity.getCodigo() "+ entity.getCodigo());
//				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));	
//			}
			if(entity.getDefinicionProceso() != null){
				SCGDefinicionProcesoEntity definicionProceos = entity.getDefinicionProceso();
				if(definicionProceos.getCodigo() != null){
					UNALMLogger.trace(log, method, "definicionProceos.getCodigo() "+ definicionProceos.getCodigo());
					criteria.add(Restrictions.eq("dp.codigo", definicionProceos.getCodigo()));	
				}
			}
			if(entity.getSolicitudProceso()!= null){
				SCGSolicitudProcesoEntity solicitudProceso = entity.getSolicitudProceso();
				if(solicitudProceso.getCodigo() != null){
					UNALMLogger.trace(log, method, "sp.codigo "+ solicitudProceso.getCodigo());
					criteria.add(Restrictions.eq("sp.codigo", solicitudProceso.getCodigo()));	
				}
			}
		}		
		List<SCGFlujoProcesoEntity> result = criteria.list();
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
	public List<SCGDecisionProcesoEntity> listDecisionProcesoDAO(SCGDecisionProcesoEntity entity) {
		final String method = "listDecisionProcesoDAO";
		final String params = "SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGDecisionProcesoEntity.class, "dp");
			criteria.createCriteria("dp.proceso","p", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq("definicionProcesoOrigen","dpo"));
		criteria.add( Restrictions.eq("definicionProcesoDestino","dpd"));
		if(entity != null){
			if(entity.getCodigo() != null){
				criteria.add( Restrictions.eq( "codigo", entity.getCodigo()));
			}
			if(entity.getDefinicionProcesoOrigen() != null){
				SCGDefinicionProcesoEntity origen = entity.getDefinicionProcesoOrigen();
				if(origen.getCodigo() != null){
					UNALMLogger.trace(log, method, "dpo.codigo: " + origen.getCodigo());
					criteria.add( Restrictions.eq( "dpo.codigo", origen.getCodigo()));
				}
			}
			if(entity.getDefinicionProcesoDestino() != null){
				SCGDefinicionProcesoEntity destino = entity.getDefinicionProcesoDestino();
				if(destino.getCodigo() != null){
					UNALMLogger.trace(log, method, "dpd.codigo: " + destino.getCodigo());
					criteria.add( Restrictions.eq( "dpd.codigo", destino.getCodigo()));
				}
			}
		}
		List<SCGDecisionProcesoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	
	@Override
	public List<SCGSolicitudProcesoEntity> listDistinctProcedenciaDAO(SCGSolicitudProcesoEntity entity,
			FilterUtil filterUtil) {

		final String method = "listDistinctProcedenciaDAO";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGSolicitudProcesoEntity.class, "dp");
		
		    
		ProjectionList projList = Projections.projectionList();
	    projList.add( Projections.distinct(Projections.property("textoNombreProcedencia")));
	    criteria.setProjection(projList);
		criteria.add( Restrictions.eq("flagEliminado", "0" ) );

		if(entity != null){
			 UNALMLogger.trace(log, method, "entity "+ entity);
			if(!TypesUtil.isEmptyString(entity.getTextoNombreProcedencia())){
				UNALMLogger.trace(log, method, "entity.getTextoNombreProcedencia() "+ entity.getTextoNombreProcedencia());
//					criteria.add( Restrictions.eq("textoNumeroDocumento", entity.getTextoNumeroDocumento() ) );
				criteria.add(Restrictions.ilike("textoNombreProcedencia",entity.getTextoNombreProcedencia(),MatchMode.START ));

			}
		}
	    List<SCGSolicitudProcesoEntity> result = new ArrayList<SCGSolicitudProcesoEntity>();
	    List<Object> projResult = criteria.list();		
		
		UNALMLogger.trace(log, method, "projResult.size() "+projResult.size());
		if( projResult.size() > 0){
			Long totalCount = Long.valueOf(projResult.size());
			filterUtil.setTotalCount(totalCount);
		}
		
		UNALMLogger.trace(log, method, "result.size(): "+ result.size() );
		if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
			UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
			criteria.setFirstResult(filterUtil.getStart());
			UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
			criteria.setMaxResults(filterUtil.getLimit());
		}
		
		projResult = criteria.list();		
		for (Object item : projResult ) {
			SCGSolicitudProcesoEntity solicitudProceso  = new SCGSolicitudProcesoEntity();
			solicitudProceso.setTextoNombreProcedencia((String) item);
			result.add(solicitudProceso);
	    }
		
//		result = criteria.list();
		UNALMLogger.trace(log, method,  "result.size() "+ result.size());
		UNALMLogger.exit(log, method, result.size());
		return result;
		
	}

	@Override
	public SCGSolicitudProcesoEntity saveSolicitudProcesoDAO(SCGSolicitudProcesoEntity entity) {
		final String method="saveSolicitudProcesoDAO";
		final String params="SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGSolicitudProcesoEntity result = (SCGSolicitudProcesoEntity )this.getSessionFactory().getCurrentSession().merge( entity );
		UNALMLogger.exit(log, method, result);
		return result;
		
	}
	@Override
	public SCGFlujoProcesoEntity saveFlujoProcesoDAO(SCGFlujoProcesoEntity entity) {
		final String method="saveFlujoProcesoDAO";
		final String params="SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGFlujoProcesoEntity result = (SCGFlujoProcesoEntity )this.getSessionFactory().getCurrentSession().merge( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGSolicitudProcesoEntity getForUpdateSolicitudProcesoDAO(SCGSolicitudProcesoEntity entity) {
		final String method = "getForUpdateSolicitudProcesoDAO";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGSolicitudProcesoEntity.class, "sp");
			criteria.createCriteria("seguridadUsuario", "p", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("tipoTramiteDocumento", "ttd", JoinType.LEFT_OUTER_JOIN);
//		criteria.add( Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO ) );
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}			
		}		
		SCGSolicitudProcesoEntity result = ( SCGSolicitudProcesoEntity )criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGFlujoProcesoEntity getForUpdateFlujoProcesoDAO(SCGFlujoProcesoEntity entity) {
		final String method = "getForUpdateFlujoProcesoDAO";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGFlujoProcesoEntity.class, "fp");
			criteria.createCriteria("responsable", "r", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("seguridadUsuario", "su", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("solicitudProceso", "sp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("sp.tipoTramiteDocumento", "ttd", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("dp.proceso", "pr", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("sp.seguridadUsuario", "spsu", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		if(entity !=null){
			UNALMLogger.trace(log, method, "codigo " + entity.getCodigo()) ;
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
		}	
		SCGFlujoProcesoEntity result = (SCGFlujoProcesoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGDefinicionProcesoEntity getForUpdateDefinicionProcesoDAO(SCGDefinicionProcesoEntity entity) {
		final String method = "getForUpdateDefinicionProcesoDAO";
		final String params = "SCGDefinicionProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGDefinicionProcesoEntity.class, "dp");
			criteria.createCriteria("proceso", "p", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0" ) );
		if(entity !=null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(!TypesUtil.isEmptyString(entity.getFlagInicio())){
				UNALMLogger.trace(log, method, "entity.getFlagInicio() "+entity.getFlagInicio());
				criteria.add(Restrictions.eq("flagInicio", entity.getFlagInicio()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombre())){
				UNALMLogger.trace(log, method, "entity.getTextoNombre() "+entity.getTextoNombre());
				criteria.add(Restrictions.eq("textoNombre", entity.getTextoNombre()));
			}
			if(entity.getProceso()!= null){
				if(entity.getProceso().getCodigo() != null){
					UNALMLogger.trace(log, method, "p.codigo "+entity.getProceso().getCodigo());
					criteria.add(Restrictions.eq("p.codigo", entity.getProceso().getCodigo()));
				}
				if(!TypesUtil.isEmptyString(entity.getProceso().getTextoNombre())){
					UNALMLogger.trace(log, method, "p.textoNombre" + entity.getProceso().getTextoNombre());
					criteria.add(Restrictions.eq("p.textoNombre", entity.getProceso().getTextoNombre()));
				}
			}
			
		}	
		SCGDefinicionProcesoEntity result = (SCGDefinicionProcesoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGResponsabilidadEntity getForUpdateResponsabilidadDAO(SCGResponsabilidadEntity entity) {
		final String method = "getForUpdateResponsabilidadDAO";
		final String params = "SCGResponsabilidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGResponsabilidadEntity.class, "re");
			criteria.createCriteria("seguridadUsuario", "su", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("responsable", "r", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("r.definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0" ) );
		if(entity !=null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(entity.getResponsable() != null){
				if( entity.getResponsable().getDefinicionProceso() != null){
					SCGDefinicionProcesoEntity definicionProceso = entity.getResponsable().getDefinicionProceso();
					if(definicionProceso.getCodigo() != null){
						UNALMLogger.trace(log, method, "dp.codigo "+definicionProceso.getCodigo());
						criteria.add(Restrictions.eq("dp.codigo", definicionProceso.getCodigo()));
					}
				}
				SCGResponsableEntity responsable = entity.getResponsable();
				if(!TypesUtil.isEmptyString(responsable.getTextoNombre())){
					UNALMLogger.trace(log, method, "r.textoNombre " + responsable.getTextoNombre());
					criteria.add(Restrictions.eq("r.textoNombre", responsable.getTextoNombre()));
				}
			}
		}
		SCGResponsabilidadEntity result = (SCGResponsabilidadEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGDecisionProcesoEntity getForUpdateDecisionProcesoDAO(SCGDecisionProcesoEntity entity) {
		final String method = "getForUpdateDecisionProcesoDAO";
		final String params = "SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGDecisionProcesoEntity.class, "dp");
//			criteria.createCriteria("dp.proceso","p", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("definicionProcesoOrigen","dpo", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("definicionProcesoDestino","dpd", JoinType.LEFT_OUTER_JOIN);
		if(entity != null){
			if(entity.getCodigo() != null){
				criteria.add( Restrictions.eq( "codigo", entity.getCodigo()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombre())){
				UNALMLogger.trace(log, method, "textoNombre: " + entity.getTextoNombre());
				criteria.add( Restrictions.eq( "textoNombre", entity.getTextoNombre()));
			}
			if(entity.getDefinicionProcesoOrigen() != null){
				SCGDefinicionProcesoEntity origen = entity.getDefinicionProcesoOrigen();
				if(origen.getCodigo() != null){
					UNALMLogger.trace(log, method, "dpo.codigo: " + origen.getCodigo());
					criteria.add( Restrictions.eq( "dpo.codigo", origen.getCodigo()));
				}
			}
//			if(entity.getDefinicionProcesoDestino() != null){
//				SCGDefinicionProcesoEntity destino = entity.getDefinicionProcesoDestino();
//				if(destino.getCodigo() != null){
//					UNALMLogger.trace(log, method, "dpd.codigo: " + destino.getCodigo());
//					criteria.add( Restrictions.eq( "dpd.codigo", destino.getCodigo()));
//				}
//			}
		}
		SCGDecisionProcesoEntity result = (SCGDecisionProcesoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public Long getMaxCodigoFlujoProcesoDAO(SCGFlujoProcesoEntity entity) {
		final String method = "getMaxCodigoFlujoProcesoDAO";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGFlujoProcesoEntity.class, "fp");
		criteria.createCriteria("responsable", "r", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("seguridadUsuario", "su", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("solicitudProceso", "sp", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("dp.proceso", "pr", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq("flagEliminado", "0" ) );
		if(entity != null){
			if(entity.getSolicitudProceso() != null){
				if(entity.getSolicitudProceso().getCodigo() != null){
					UNALMLogger.trace(log, method, "sp.codigo "+entity.getSolicitudProceso().getCodigo());
					criteria.add(Restrictions.eq("sp.codigo", entity.getSolicitudProceso().getCodigo()));	
				}
			}	
//			if(entity.getDefinicionProceso() != null){
//				if(!TypesUtil.isEmptyString(entity.getDefinicionProceso().getFlagInicio())){
//					UNALMLogger.trace(log, method, "dp.flagInicio "+entity.getDefinicionProceso().getFlagInicio());
//					criteria.add(Restrictions.eq("dp.flagInicio", entity.getDefinicionProceso().getFlagInicio()));	
//				}
//			}
		
		}
		criteria.setProjection(Projections.projectionList().add(Projections.max("codigo")));		
		Long result = (Long)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public Long getMaxCodigoAsociacionDAO(SCGSolicitudProcesoEntity entity) {
		final String method = "getMaxCodigoAsociacionDAO";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGSolicitudProcesoEntity.class, "sp");
		criteria.createCriteria("seguridadUsuario", "p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoTramiteDocumento", "ttd", JoinType.LEFT_OUTER_JOIN);
		criteria.add( Restrictions.eq("flagEliminado", "0" ) );
		criteria.setProjection(Projections.projectionList().add(Projections.max("codigoAsociacion")));		
		
		Long result = (Long)criteria.uniqueResult();
		
		if(result == null){
			result = 0L;
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}

}


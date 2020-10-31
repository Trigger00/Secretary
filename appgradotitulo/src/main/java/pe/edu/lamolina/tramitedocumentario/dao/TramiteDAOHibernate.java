package pe.edu.lamolina.tramitedocumentario.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.dao.UniversidadDAOHibernate;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGAtributosEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsableEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGValoresEntity;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class TramiteDAOHibernate extends HibernateTemplate implements TramiteDAO{
private final static Logger log = Logger.getLogger(UniversidadDAOHibernate.class);
	
	@Autowired
	public TramiteDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
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
			
			if(entity.getSeguridadUsuario() != null){
				SeguridadUsuarioEntity usuario  = entity.getSeguridadUsuario();
				
				if(usuario.getCodigo() != null){
					UNALMLogger.trace(log, method, "su.codigo: "+ usuario.getCodigo());
					criteria.add(Restrictions.eq("su.codigo", usuario.getCodigo()));	
				}
			}
			
			if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
				 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
				 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
			 }
			
			if(entity.getFechaAgregarInicio() != null && entity.getFechaAgregarFinal() != null){
				UNALMLogger.trace(log, method, "entity.getFechaAgregarInicio(): "+entity.getFechaAgregarInicio());
				UNALMLogger.trace(log, method, "entity.getFechaAgregarFinal(): "+ entity.getFechaAgregarFinal());
				
				criteria.add(Restrictions.ge("fechaInicio", entity.getFechaAgregarInicio())); 
				criteria.add(Restrictions.le("fechaInicio", entity.getFechaAgregarFinal()));
			
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
		
		if(!TypesUtil.isEmptyString(entity.getTextoTipoOrden())){
			
			if(entity.getTextoTipoOrden().equals("DES")){
				criteria.addOrder(Order.desc("codigo"));
			}else if(entity.getTextoTipoOrden().equals("ASC")){
				criteria.addOrder(Order.asc("codigo"));
			}
		}
		
		result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	
	@Override
	public List<SCGDecisionProcesoEntity> listDecisionProcesoDAO(SCGDecisionProcesoEntity entity) {
		final String method = "listDecisionProcesoDAO";
		final String params = "SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGDecisionProcesoEntity.class, "dp");
			criteria.createCriteria("definicionProcesoOrigen", "dpo", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("definicionProcesoDestino", "dpd", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
			criteria.add( Restrictions.eq("flagActivo", "1") );
		if(entity != null){
			if(entity.getDefinicionProcesoOrigen() != null){
				UNALMLogger.trace(log, method, "dpo.codigo " + entity.getDefinicionProcesoOrigen().getCodigo());
				criteria.add( Restrictions.eq( "dpo.codigo", entity.getDefinicionProcesoOrigen().getCodigo()));
			}
			if(entity.getDefinicionProcesoDestino() != null){
				UNALMLogger.trace(log, method, "dpd.codigo " + entity.getDefinicionProcesoDestino().getCodigo());
				criteria.add( Restrictions.eq( "dpd.codigo", entity.getDefinicionProcesoDestino().getCodigo()));
			}
			
		}		
		List<SCGDecisionProcesoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	
	@Override
	public List<SCGResponsabilidadEntity> listResponsabilidadDAO(SCGResponsabilidadEntity entity) {
		final String method = "listResponsabilidadDAO";
		final String params = "SCGResponsabilidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGResponsabilidadEntity.class, "r");
			criteria.createCriteria("seguridadUsuario", "su", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("responsable", "re", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("re.definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		
		if(entity != null){
			
			if(entity.getResponsable() != null){
				SCGResponsableEntity responsable = entity.getResponsable();
				
				if(responsable.getDefinicionProceso() != null){
					UNALMLogger.trace(log, method, "dp.codigo " + responsable.getDefinicionProceso().getCodigo());
					criteria.add( Restrictions.eq( "dp.codigo", responsable.getDefinicionProceso().getCodigo()));
				}
			}
		}
		
		List<SCGResponsabilidadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
		return result;
	}
	
	@Override
	public List<SCGAtributosEntity> listAtributosDAO(SCGAtributosEntity entity) {
		final String method = "listAtributosDAO";
		final String params = "SCGAtributosEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGAtributosEntity.class, "a");
			criteria.createCriteria("definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("tipoAtributo", "ta", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		
		if(entity != null){
			
			if(entity.getDefinicionProceso() != null){
				
				if(entity.getDefinicionProceso().getCodigo() != null){
					UNALMLogger.trace(log, method, "dp.codigo " + entity.getDefinicionProceso().getCodigo());
					criteria.add( Restrictions.eq( "dp.codigo", entity.getDefinicionProceso().getCodigo()));
				}
			}
		}
		
		List<SCGAtributosEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());		
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
	public SCGValoresEntity saveValoresDAO(SCGValoresEntity entity) {
		final String method="saveFlujoValoresDAO";
		final String params="SCGValoresEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGValoresEntity result = (SCGValoresEntity )this.getSessionFactory().getCurrentSession().merge( entity );
		
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
			criteria.createCriteria("definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("dp.proceso", "pr", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		if(entity != null){
			UNALMLogger.trace(log, method, "codigo "+ entity.getCodigo());
			criteria.add(Restrictions.eq("codigo", entity.getCodigo()));	
		}		
		SCGFlujoProcesoEntity result = (SCGFlujoProcesoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGDecisionProcesoEntity getForUpdateDecisionProcesoDAO(SCGDecisionProcesoEntity entity) {
		final String method = "getForUpdateDecisionProcesoDAO";
		final String params = "SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGFlujoProcesoEntity.class, "fp");
			criteria.createCriteria("definicionProcesoOrigen", "dpo", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("definicionProcesoDestino", "dpd", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		if(entity != null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo " + entity.getCodigo());
				criteria.add( Restrictions.eq( "codigo", entity.getCodigo()));
			}
			
			
		}		
		SCGDecisionProcesoEntity result = (SCGDecisionProcesoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}


	@Override
	public SCGResponsabilidadEntity getForUpdateResponsabilidadDAO(SCGResponsabilidadEntity entity) {
		final String method = "getForUpdateResponsabilidadDAO";
		final String params = "SCGResponsabilidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGResponsabilidadEntity.class, "r");
			criteria.createCriteria("seguridadUsuario", "su", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("responsable", "re", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("re.definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		
		if(entity != null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo " + entity.getCodigo());
				criteria.add( Restrictions.eq( "codigo", entity.getCodigo()));
			}

			if(entity.getResponsable() != null){
				SCGResponsableEntity responsable = entity.getResponsable();
				UNALMLogger.trace(log, method, "re.codigo " + responsable.getCodigo());
				criteria.add( Restrictions.eq( "re.codigo", responsable.getCodigo()));
			}
			if(entity.getSeguridadUsuario() != null){
				SeguridadUsuarioEntity seguridadUsuario = entity.getSeguridadUsuario();
				UNALMLogger.trace(log, method, "su.codigo " + seguridadUsuario.getCodigo());
				criteria.add( Restrictions.eq( "su.codigo", seguridadUsuario.getCodigo()));
			}
		}
	
		
		
		SCGResponsabilidadEntity result = (SCGResponsabilidadEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGAtributosEntity getForUpdateAtributoDAO(SCGAtributosEntity entity) {
		final String method = "getForUpdateAtributoDAO";
		final String params = "SCGAtributosEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGAtributosEntity.class, "a");
			criteria.createCriteria("definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("tipoAtributo", "ta", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		
		if(entity != null){
			
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo " + entity.getCodigo());
				criteria.add( Restrictions.eq( "codigo", entity.getCodigo()));
			}
		}
		
		SCGAtributosEntity result = (SCGAtributosEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGValoresEntity getForUpdateValoresDAO(SCGValoresEntity entity) {
		final String method = "getForUpdateValoresDAO";
		final String params = "SCGValoresEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria( SCGValoresEntity.class, "v");
			criteria.createCriteria("atributos", "a", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("a.definicionProceso", "dp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("solicitudProceso", "sp", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("archivoTramiteDocumento", "atd", JoinType.LEFT_OUTER_JOIN);
			criteria.add( Restrictions.eq("flagEliminado", "0") );
		
		if(entity != null){
			
			if(entity.getAtributos()!= null){
				
				if(entity.getAtributos().getCodigo() != null){
					UNALMLogger.trace(log, method, "a.codigo " + entity.getAtributos().getCodigo());
					criteria.add( Restrictions.eq( "a.codigo", entity.getAtributos().getCodigo()));
				}
				

				if(entity.getAtributos().getDefinicionProceso() != null){
					SCGDefinicionProcesoEntity defincionProceso = entity.getAtributos().getDefinicionProceso();
					UNALMLogger.trace(log, method, "dp.codigo: " + defincionProceso.getCodigo());
					criteria.add( Restrictions.eq( "dp.codigo", defincionProceso.getCodigo()));
				}
			}
			
			if(entity.getSolicitudProceso()!= null){
				
				if(entity.getSolicitudProceso().getCodigo() != null){
					UNALMLogger.trace(log, method, "sp.codigo: " + entity.getSolicitudProceso().getCodigo());
					criteria.add( Restrictions.eq( "sp.codigo", entity.getSolicitudProceso().getCodigo()));
				}
			}
			
			if(entity.getArchivoTramiteDocumento()!= null){
				
				if(entity.getArchivoTramiteDocumento().getCodigo() != null){
					UNALMLogger.trace(log, method, "atd.codigo: " + entity.getArchivoTramiteDocumento().getCodigo());
					criteria.add( Restrictions.eq( "atd.codigo", entity.getArchivoTramiteDocumento().getCodigo()));
				}
			}
		}
		
		SCGValoresEntity result = (SCGValoresEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		
		return result;
	}
	

	

}

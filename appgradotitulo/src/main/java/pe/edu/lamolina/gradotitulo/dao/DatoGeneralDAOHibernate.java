package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class DatoGeneralDAOHibernate  extends HibernateTemplate implements DatoGeneralDAO {
	
	private final static Logger log = Logger.getLogger(DatoGeneralDAOHibernate.class);

	@Autowired
	public DatoGeneralDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	@Override
	public List<SCGEstudianteEntity> getEstudianteListDAO(SCGEstudianteEntity entity,FilterUtil filterUtil) {
		final String method = "getListEstudianteDAO";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteriaCount.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
		
		UNALMLogger.trace(log, method, "entity: "+entity);
		if(entity!= null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "getCodigo "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo() ));
				criteriaCount.add(Restrictions.eq("codigo", entity.getCodigo() ));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoMatricula())){			
				UNALMLogger.trace(log, method, "getTextoMatricula() "+entity.getTextoMatricula());
				criteria.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
				criteriaCount.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombreCompleto())){			
				UNALMLogger.trace(log, method, "getTextoNombreCompleto "+entity.getTextoNombreCompleto());
				criteria.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
				criteriaCount.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
			}
			if(entity.getFacultad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getFacultad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getFacultad().getTextoNombreEspanol()  "+entity.getFacultad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
					criteriaCount.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getEspecialidad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getEspecialidad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getEspecialidad().getTextoNombreEspanol()  "+entity.getEspecialidad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
					criteriaCount.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getFechaCreacion() != null){
				UNALMLogger.trace(log, method, "getFechaCreacion "+entity.getFechaCreacion());
				criteria.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
				criteriaCount.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoCodigoExternoDocumento())){
				UNALMLogger.trace(log, method, "getTextoCodigoExternoDocumento "+entity.getTextoCodigoExternoDocumento());
				criteria.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
				criteriaCount.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNumeroDocumento())){
				UNALMLogger.trace(log, method, "getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
				criteria.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
				criteriaCount.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
			}
		}
		Long totalCount = new Long(
				String.valueOf(criteriaCount.setProjection(Projections.count("codigo")).list().get(0))
		);

		UNALMLogger.trace(log, method, "totalCount: "+totalCount);
			
		if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
			UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
			criteria.setFirstResult(filterUtil.getStart());
			UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
			criteria.setMaxResults(filterUtil.getLimit());
		}
		filterUtil.setTotalCount(totalCount);
		 
		List<SCGEstudianteEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteEntity> getEstudianteListExcelDAO(SCGEstudianteEntity entity) {
		final String method = "getEstudianteListExcelDAO";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));	
		UNALMLogger.trace(log, method, "entity: "+entity);
		if(entity!= null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "getCodigo "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo() ));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoMatricula())){			
				UNALMLogger.trace(log, method, "getTextoMatricula() "+entity.getTextoMatricula());
				criteria.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombreCompleto())){			
				UNALMLogger.trace(log, method, "getTextoNombreCompleto "+entity.getTextoNombreCompleto());
				criteria.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
			}
			if(entity.getFacultad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getFacultad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getFacultad().getTextoNombreEspanol()  "+entity.getFacultad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getEspecialidad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getEspecialidad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getEspecialidad().getTextoNombreEspanol()  "+entity.getEspecialidad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getFechaCreacion() != null){
				UNALMLogger.trace(log, method, "getFechaCreacion "+entity.getFechaCreacion());
				criteria.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoCodigoExternoDocumento())){
				UNALMLogger.trace(log, method, "getTextoCodigoExternoDocumento "+entity.getTextoCodigoExternoDocumento());
				criteria.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNumeroDocumento())){
				UNALMLogger.trace(log, method, "getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
				criteria.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
			}
		}
		criteria.setFirstResult(0).setMaxResults(2000);		 
		List<SCGEstudianteEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteEntity> getEstudiantePregradoListDAO(SCGEstudianteEntity entity,FilterUtil filterUtil) {
		final String method = "getEstudiantePregradoListDAO";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteriaCount.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
		UNALMLogger.trace(log, method, "entity: "+entity);
		if(entity!= null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "getCodigo "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo() ));
				criteriaCount.add(Restrictions.eq("codigo", entity.getCodigo() ));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoMatricula())){			
				UNALMLogger.trace(log, method, "getTextoMatricula() "+entity.getTextoMatricula());
				criteria.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
				criteriaCount.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombreCompleto())){			
				UNALMLogger.trace(log, method, "getTextoNombreCompleto "+entity.getTextoNombreCompleto());
				criteria.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
				criteriaCount.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
			}
			if(entity.getFacultad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getFacultad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getFacultad().getTextoNombreEspanol()  "+entity.getFacultad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
					criteriaCount.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getEspecialidad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getEspecialidad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getEspecialidad().getTextoNombreEspanol()  "+entity.getEspecialidad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
					criteriaCount.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getFechaCreacion() != null){
				UNALMLogger.trace(log, method, "getFechaCreacion "+entity.getFechaCreacion());
				criteria.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
				criteriaCount.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoCodigoExternoDocumento())){
				UNALMLogger.trace(log, method, "getTextoCodigoExternoDocumento "+entity.getTextoCodigoExternoDocumento());
				criteria.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
				criteriaCount.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNumeroDocumento())){
				UNALMLogger.trace(log, method, "getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
				criteria.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
				criteriaCount.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
			}
		}
		criteria.add(Restrictions.ne("textoMatricula",""));
		criteriaCount.add(Restrictions.ne("textoMatricula",""));
		Long totalCount = new Long(
				String.valueOf(criteriaCount.setProjection(Projections.count("codigo")).list().get(0))
		);
	
		UNALMLogger.trace(log, method, "totalCount: "+totalCount);
			
		if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
			UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
			criteria.setFirstResult(filterUtil.getStart());
			UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
			criteria.setMaxResults(filterUtil.getLimit());
		}
		filterUtil.setTotalCount(totalCount);
		 
			
		
		List<SCGEstudianteEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteEntity> getEstudianteRevalidaListDAO(SCGEstudianteEntity entity,FilterUtil filterUtil) {
		final String method = "getEstudianteRevalidaListDAO";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteriaCount.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
		UNALMLogger.trace(log, method, "entity: "+entity);
		if(entity!= null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "getCodigo "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo() ));
				criteriaCount.add(Restrictions.eq("codigo", entity.getCodigo() ));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoMatricula())){			
				UNALMLogger.trace(log, method, "getTextoMatricula() "+entity.getTextoMatricula());
				criteria.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
				criteriaCount.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombreCompleto())){			
				UNALMLogger.trace(log, method, "getTextoNombreCompleto "+entity.getTextoNombreCompleto());
				criteria.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
				criteriaCount.add(Restrictions.ilike("textoNombreCompleto",entity.getTextoNombreCompleto(), MatchMode.ANYWHERE ));
			}
			if(entity.getFacultad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getFacultad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getFacultad().getTextoNombreEspanol()  "+entity.getFacultad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
					criteriaCount.add(Restrictions.ilike("f.textoNombreEspanol",entity.getFacultad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getEspecialidad()!=null ){
				if(!TypesUtil.isEmptyString(entity.getEspecialidad().getTextoNombreEspanol())){
					UNALMLogger.trace(log, method, "entity.getEspecialidad().getTextoNombreEspanol()  "+entity.getEspecialidad().getTextoNombreEspanol() );
					criteria.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
					criteriaCount.add(Restrictions.ilike("es.textoNombreEspanol",entity.getEspecialidad().getTextoNombreEspanol(),MatchMode.ANYWHERE ));
				}
			}
			if(entity.getFechaCreacion() != null){
				UNALMLogger.trace(log, method, "getFechaCreacion "+entity.getFechaCreacion());
				criteria.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
				criteriaCount.add(Restrictions.eq("fechaCreacion",entity.getFechaCreacion()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoCodigoExternoDocumento())){
				UNALMLogger.trace(log, method, "getTextoCodigoExternoDocumento "+entity.getTextoCodigoExternoDocumento());
				criteria.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
				criteriaCount.add(Restrictions.eq("textoCodigoExternoDocumento",entity.getTextoCodigoExternoDocumento()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNumeroDocumento())){
				UNALMLogger.trace(log, method, "getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
				criteria.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
				criteriaCount.add(Restrictions.eq("textoNumeroDocumento",entity.getTextoNumeroDocumento()));
			}
		}
		criteria.add(Restrictions.eq("textoMatricula",""));
		criteriaCount.add(Restrictions.eq("textoMatricula",""));
		
		Long totalCount = new Long(
				String.valueOf(criteriaCount.setProjection(Projections.count("codigo")).list().get(0))
		);
	
		UNALMLogger.trace(log, method, "totalCount: "+totalCount);
			
		if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
			UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
			criteria.setFirstResult(filterUtil.getStart());
			UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
			criteria.setMaxResults(filterUtil.getLimit());
		}
		filterUtil.setTotalCount(totalCount);
		
		List<SCGEstudianteEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGPersonaDocumentoEntity> getDocumentoListDAO(SCGPersonaDocumentoEntity entity) {
		final String method = "getListDocumentoDAO";
		final String params = "SCGPersonaDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPersonaDocumentoEntity.class, "pd");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoDocumento", "td", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!= null){
			if(entity.getPersona() !=null ){
				if(entity.getPersona().getCodigo() !=null ){
					UNALMLogger.trace(log, method, "entity.getPersona().getCodigo()  "+entity.getPersona().getCodigo() );
					criteria.add(Restrictions.eq("p.codigo", entity.getPersona().getCodigo()));
				}
			}
		}
		List<SCGPersonaDocumentoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGPersonaTelefonoEntity> getTelefonoListDAO(SCGPersonaTelefonoEntity entity) {
		final String method = "getTelefonoListDAO";
		final String params = "SCGPersonaTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPersonaTelefonoEntity.class, "pt");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoTelefono", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("pt.codigo", entity.getCodigo()));
				UNALMLogger.trace(log, method, "pt.codigo: "+entity.getCodigo());
			}
			if(entity.getPersona() != null){
				criteria.add(Restrictions.eq("p.codigo", entity.getPersona().getCodigo()));
				UNALMLogger.trace(log, method, "p.codigo: "+entity.getPersona().getCodigo());
			}
			if(entity.getTipoTelefono()!=null){
				criteria.add(Restrictions.eq("tp.codigoExterno", entity.getTipoTelefono().getCodigoExterno()));
				UNALMLogger.trace(log, method, "tp.codigoExterno: "+entity.getTipoTelefono().getCodigoExterno());
			}
		}		
		List<SCGPersonaTelefonoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGTipoDocumentoEntity> getTipoDocumentoListDAO(SCGTipoDocumentoEntity entity) {
		final String method = "getTipoDocumentoListDAO";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoDocumentoEntity.class, "tp");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		List<SCGTipoDocumentoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGCicloEntity> getCicloListDAO(SCGCicloEntity entity) {
		final String method = "getCicloListDAO";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGCicloEntity.class, "c");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		criteria.addOrder(Order.desc("codigo"));
		List<SCGCicloEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGPersonaTelefonoEntity> getPersonaTelefonoListDAO(SCGPersonaTelefonoEntity entity) {
		final String method = "getPersonaTelefonoListDAO";
		final String params = "SCGPersonaTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPersonaTelefonoEntity.class, "pt");
				 criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("tipoTelefono","tt", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!=null){
			if(entity.getPersona()!=null){
				if(entity.getPersona().getCodigo()!=null){
					UNALMLogger.trace(log, method,"entity.getPersona().getCodigo(): "+entity.getPersona().getCodigo());
					criteria.add(Restrictions.eq("p.codigo",entity.getPersona().getCodigo()));
				}
			}
		}
		List<SCGPersonaTelefonoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public SCGAdjuntoEntity getForUpdateAdjuntoDAO(SCGAdjuntoEntity entity) {
		final String method = "getForUpdateAdjuntoDAO";
		final String params = "SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGAdjuntoEntity.class, "p");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			
		}		
		SCGAdjuntoEntity result = (SCGAdjuntoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGPersonaEntity getForUpdatePersonaDAO(SCGPersonaEntity entity) {
		final String method = "getForUpdatePersonaDAO";
		final String params = "SCGPersonaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPersonaEntity.class, "p");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			
		}		
		SCGPersonaEntity result = (SCGPersonaEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGEstudianteEntity getForUpdateEstudianteDAO(SCGEstudianteEntity entity) {
		final String method = "getForUpdateEstudianteDAO";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteEntity.class, "e");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("p.adjunto","a", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("facultad", "f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad", "es", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo: "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoMatricula())){
				UNALMLogger.trace(log, method,"entity.getTextoMatricula(): "+entity.getTextoMatricula());
				criteria.add(Restrictions.eq("textoMatricula",entity.getTextoMatricula()));
			}
		}		
		SCGEstudianteEntity result = (SCGEstudianteEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGPersonaTelefonoEntity getForUpdateTelefonoDAO(SCGPersonaTelefonoEntity entity) {
		final String method = "getForUpdateTelefonoDAO";
		final String params = "SCGPersonaTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPersonaTelefonoEntity.class, "pt");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoTelefono", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("pt.codigo", entity.getCodigo()));
				UNALMLogger.trace(log, method, "pt.codigo: "+entity.getCodigo());
			}
			if(entity.getPersona() != null){
				criteria.add(Restrictions.eq("p.codigo", entity.getPersona().getCodigo()));
				UNALMLogger.trace(log, method, "p.codigo: "+entity.getPersona().getCodigo());
			}
			if(entity.getTipoTelefono()!=null){
				criteria.add(Restrictions.eq("tp.codigoExterno", entity.getTipoTelefono().getCodigoExterno()));
				UNALMLogger.trace(log, method, "tp.codigoExterno: "+entity.getTipoTelefono().getCodigoExterno());
			}
		}		
		SCGPersonaTelefonoEntity result = (SCGPersonaTelefonoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGTipoTelefonoEntity getForUpdateTipoTelefonoDAO(SCGTipoTelefonoEntity entity) {
		final String method = "getForUpdateTipoTelefonoDAO";
		final String params = "SCGTipoTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoTelefonoEntity.class, "tt");;
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigoExterno() != null){
				criteria.add(Restrictions.eq("tt.codigoExterno", entity.getCodigoExterno()));
			}
		}		
		SCGTipoTelefonoEntity result = (SCGTipoTelefonoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGPersonaDocumentoEntity getForUpdateDocumentoDAO(SCGPersonaDocumentoEntity entity) {
		final String method = "getForUpdateDocumnetoDAO";
		final String params = "SCGPersonaDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPersonaDocumentoEntity.class, "pd");
		criteria.createCriteria("persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoDocumento", "td", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity.getPersona() != null){
			UNALMLogger.trace(log, method, "p.codigo: "+ entity.getPersona().getCodigo());
			criteria.add(Restrictions.eq("p.codigo", entity.getPersona().getCodigo()));
			
		}
		if(entity.getTipoDocumento()!=null){
			UNALMLogger.trace(log, method, "td.codigo: " + entity.getTipoDocumento().getCodigo());
			criteria.add(Restrictions.eq("td.codigo", entity.getTipoDocumento().getCodigo()));
		}
		
		SCGPersonaDocumentoEntity result = (SCGPersonaDocumentoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGTipoDocumentoEntity getForUpdateTipoDocumentoDAO(SCGTipoDocumentoEntity entity) {
		final String method = "getForUpdateTipoDocumentoDAO";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoDocumentoEntity.class, "td");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!=null){
			if(!TypesUtil.isEmptyString(entity.getCodigoExterno())){
				UNALMLogger.trace(log, method, "td.codigoExterno: " + entity.getCodigoExterno());
				criteria.add(Restrictions.eq("td.codigoExterno", entity.getCodigoExterno()));
			}
			if(entity.getCodigo()!=null){
				UNALMLogger.trace(log, method, "td.codigo: " + entity.getCodigo());
				criteria.add(Restrictions.eq("td.codigo", entity.getCodigo()));
			}
		}
		SCGTipoDocumentoEntity result = (SCGTipoDocumentoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGCicloEntity getForUpdateCicloDAO(SCGCicloEntity entity) {
		final String method = "getForUpdateCicloDAO";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGCicloEntity.class, "c");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!=null){
			if(entity.getCodigo()!=null){
				UNALMLogger.trace(log, method, "codigo: " + entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(!TypesUtil.isEmptyString(entity.getTextoSemestre())){
				UNALMLogger.trace(log, method, "textoSemestre: " + entity.getTextoSemestre());
				criteria.add(Restrictions.eq("textoSemestre", entity.getTextoSemestre()));
			}
		}
		SCGCicloEntity result = (SCGCicloEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGEstudianteEntity saveEstudianteDAO(SCGEstudianteEntity entity) {
		final String method="saveEstudianteDAO";
		final String params="SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteEntity result=(SCGEstudianteEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPersonaEntity savePersonaDAO(SCGPersonaEntity entity) {
		final String method="savePersonaDAO";
		final String params="SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaEntity result=(SCGPersonaEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPersonaTelefonoEntity savePersonaTelefonoDAO(SCGPersonaTelefonoEntity entity) {
		final String method="savePersonaTelefonoDAO";
		final String params="SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaTelefonoEntity result=(SCGPersonaTelefonoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGPersonaDocumentoEntity savePersonaDocumentoDAO(SCGPersonaDocumentoEntity entity) {
		final String method="savePersonaDocumentoDAO";
		final String params="SCGPersonaDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaDocumentoEntity result=(SCGPersonaDocumentoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity saveAdjuntoDAO(SCGAdjuntoEntity entity) {
		final String method="saveAdjuntoDAO";
		final String params="SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity result=(SCGAdjuntoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SCGTipoDocumentoEntity saveTipoDocumentoIdentidad(SCGTipoDocumentoEntity entity) {
		final String method="saveTipoDocumentoIdentidad";
		final String params="SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGTipoDocumentoEntity result=(SCGTipoDocumentoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
}

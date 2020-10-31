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
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGDiplomadoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDiplomadoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;
@Repository
public class DiplomadoDAOHibernate extends HibernateTemplate implements DiplomadoDAO {
	
	private final static Logger log = Logger.getLogger(DiplomadoDAOHibernate.class);

	@Autowired
	public DiplomadoDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	

	@Override
	public List<SCGDiplomadoEntity> getListDiplomadoDAO(SCGDiplomadoEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		
		final String method = "getListDiplomadoDAO";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGDiplomadoEntity.class, "d");
		criteria.createCriteria("tipoDiplomado","td", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("es.facultad","fa", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("estudiante", "e", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("autoridadRegistroRector", "arr", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);	
		criteria.createCriteria("autoridadRegistroDecano", "ard", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado",ApplicationConstant.FLAG_ELIMINADO));
		
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGDiplomadoEntity.class, "d");
		criteriaCount.createCriteria("tipoDiplomado","td", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("es.facultad","fa", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("estudiante", "e", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("autoridadRegistroRector", "arr", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);	
		criteriaCount.createCriteria("autoridadRegistroDecano", "ard", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
		criteriaCount.add(Restrictions.eq("flagEliminado",ApplicationConstant.FLAG_ELIMINADO));
		if(entity != null){
			 if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
				 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
				 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
				 criteriaCount.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
			 }
			 if(!TypesUtil.isEmptyString(entity.getTextoResolucionRectoral())){
				 UNALMLogger.trace(log, method, " entity.getTextoResolucionRectoral(): "+ entity.getTextoResolucionRectoral());
				 criteria.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral()));
				 criteriaCount.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral()));
			 }
			 if(entity.getEstudiante()!=null){
				 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoMatricula())){
					 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoMatricula(): "+ entity.getEstudiante().getTextoMatricula());
					 criteria.add(Restrictions.eq("e.textoMatricula",entity.getEstudiante().getTextoMatricula()));
					 criteriaCount.add(Restrictions.eq("e.textoMatricula",entity.getEstudiante().getTextoMatricula()));

				 }
				 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoNombreCompleto())){
					 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoNombreCompleto(): "+ entity.getEstudiante().getTextoNombreCompleto());
					 criteria.add(Restrictions.ilike("e.textoNombreCompleto",entity.getEstudiante().getTextoNombreCompleto(),MatchMode.ANYWHERE ));
					 criteriaCount.add(Restrictions.ilike("e.textoNombreCompleto",entity.getEstudiante().getTextoNombreCompleto(),MatchMode.ANYWHERE ));

				 }
				 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoCodigoExternoDocumento())){
					 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoCodigoExternoDocumento(): "+ entity.getEstudiante().getTextoCodigoExternoDocumento());
					 criteria.add(Restrictions.eq("e.textoCodigoExternoDocumento",entity.getEstudiante().getTextoCodigoExternoDocumento()));
					 criteriaCount.add(Restrictions.eq("e.textoCodigoExternoDocumento",entity.getEstudiante().getTextoCodigoExternoDocumento()));

				 }
				 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoNumeroDocumento())){
					 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoNumeroDocumento(): "+ entity.getEstudiante().getTextoNumeroDocumento());
					 criteria.add(Restrictions.eq("e.textoNumeroDocumento",entity.getEstudiante().getTextoNumeroDocumento()));
					 criteriaCount.add(Restrictions.eq("e.textoNumeroDocumento",entity.getEstudiante().getTextoNumeroDocumento()));

				 }

			 }
			 
			 if(entity.getNumeroFolio()!=null){
				 UNALMLogger.trace(log, method, "entity.getNumeroFolio(): "+ entity.getNumeroFolio());
				 criteria.add(Restrictions.eq("r.numeroFolio",entity.getNumeroFolio()));
				 criteriaCount.add(Restrictions.eq("r.numeroFolio",entity.getNumeroFolio()));

			 }
			 if(entity.getNumeroLibro()!=null){
				 UNALMLogger.trace(log, method, "entity.getNumeroLibro(): "+ entity.getNumeroLibro());
				 criteria.add(Restrictions.eq("r.numeroLibro",entity.getNumeroLibro()));
				 criteriaCount.add(Restrictions.eq("r.numeroLibro",entity.getNumeroLibro()));
			 }
			if(entity.getNumeroRegistro()!=null){
				 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
				 criteria.add(Restrictions.eq("r.numeroRegistro",entity.getNumeroRegistro())); 
				 criteriaCount.add(Restrictions.eq("r.numeroRegistro",entity.getNumeroRegistro())); 
			}
			if(entity.getAgendaGrupo()!=null){
				if(entity.getAgendaGrupo().getCodigo()!=null){
					 UNALMLogger.trace(log, method, "entity.getAgendaGrupo().getCodigo(): "+ entity.getAgendaGrupo().getCodigo());
					 criteria.add(Restrictions.eq("ag.codigo",entity.getAgendaGrupo().getCodigo())); 
					 criteriaCount.add(Restrictions.eq("ag.codigo",entity.getAgendaGrupo().getCodigo())); 
				}
			}
			if(entity.getFechaCierreInicial()!=null &&entity.getFechaCierreFinal()!=null){
				UNALMLogger.trace(log, method, "entity.getFechaCierreInicial(): "+entity.getFechaCierreInicial());
				UNALMLogger.trace(log, method, "entity.getFechaCierreFinal(): "+ entity.getFechaCierreFinal());
				criteria.add(Restrictions.ge("fechaCierre", entity.getFechaCierreInicial())); 
				criteria.add(Restrictions.le("fechaCierre", entity.getFechaCierreFinal()));
				criteriaCount.add(Restrictions.ge("fechaCierre", entity.getFechaCierreInicial())); 
				criteriaCount.add(Restrictions.le("fechaCierre", entity.getFechaCierreFinal()));
			}
		 }
		
		 Long totalCount = new Long(
				String.valueOf(criteriaCount.setProjection(Projections.count("codigo")).list().get(0))
		 );

		 UNALMLogger.trace(log, method, "totalCount: "+totalCount);
		
		 if(filterUtil.getStart() != null && filterUtil.getLimit() != null) {
			 UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
			 criteria.setFirstResult(filterUtil.getStart());
			 UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
			 criteria.setMaxResults(filterUtil.getLimit());
		 }
	 	 filterUtil.setTotalCount(totalCount);
		 if(agendas.size()>0){
			criteria.add(Restrictions.in("ag.codigo", agendas));
			criteriaCount.add(Restrictions.in("ag.codigo", agendas));
		 }
		 filterUtil.setTotalCount(totalCount);
		 criteria.addOrder(Order.asc("codigo"));
		 List<SCGDiplomadoEntity> result = criteria.list();
		 UNALMLogger.exit(log, method, result.size());
		 return result;
	
	}
	@Override
	public List<SCGTipoDiplomadoEntity> getListTipoDiplomadoDAO(SCGTipoDiplomadoEntity entity) {
		final String method = "getListTipoDiplomadoDAO";
		final String params = "SCGTipoDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoDiplomadoEntity.class, "td");
		criteria.add(Restrictions.eq("flagEliminado",ApplicationConstant.FLAG_ELIMINADO));
		List<SCGTipoDiplomadoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public SCGDiplomadoEntity getForUpdateDiplomadoDAO(SCGDiplomadoEntity entity) {
		final String method = "getListDiplomadoDAO";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGDiplomadoEntity.class, "d");
		criteria.createCriteria("tipoDiplomado","td", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("es.facultad","fa", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("estudiante", "e", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("autoridadRegistroRector", "arr", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);	
		criteria.createCriteria("autoridadRegistroDecano", "ard", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado",ApplicationConstant.FLAG_ELIMINADO));
		if(entity != null){
			if(entity.getCodigo()!=null){
				 UNALMLogger.trace(log, method, "entity.getCodigo(): "+ entity.getCodigo());
				 criteria.add(Restrictions.eq("codigo",entity.getCodigo()));
			 }
			 if(entity.getNumeroRegistro()!=null){
				 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
				 criteria.add(Restrictions.eq("numeroRegistro",entity.getNumeroRegistro()));
			 }
		 }
		SCGDiplomadoEntity result = (SCGDiplomadoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public Long getNumberRegistroMaxDiplomadoDAO() {
		final String method = "getNumberRegistroMaxDiplomadoDAO";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGDiplomadoEntity.class, "d");
		criteria.setProjection(Projections.projectionList()
	            .add(Projections.max("numeroRegistro")));		
		Long result = (Long)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGDiplomadoEntity saveDiplomadoDAO(SCGDiplomadoEntity entity) {
		final String method="saveDiplomadoDAO";
		final String params="SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGDiplomadoEntity result=(SCGDiplomadoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	
	}

	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}


	
}

package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
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
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPaisEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class DuplicadoDAOHibernate extends HibernateTemplate implements DuplicadoDAO {
	
	private final static Logger log = Logger.getLogger(DuplicadoDAOHibernate.class);

	@Autowired
	public DuplicadoDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListDuplicadoDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDuplicadoDAO";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
				 criteria.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

				 criteria.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
				 criteria.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteria.add(Restrictions.eq("flagDuplicado", "1"));
				 
	   Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
	   			criteriaCount.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

	   			criteriaCount.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
	   			criteriaCount.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
	   			criteriaCount.add(Restrictions.eq("flagDuplicado", "1"));
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
						 if(entity.getEstudiante().getCodigo()!=null){
							 UNALMLogger.trace(log, method, "e.codigo: "+ entity.getEstudiante().getCodigo());
							 criteria.add(Restrictions.eq("e.codigo",entity.getEstudiante().getCodigo()));
							 criteriaCount.add(Restrictions.eq("e.codigo",entity.getEstudiante().getCodigo()));

						 }

					 }
					 if(entity.getGradoAcademico()!=null){
						 if(entity.getGradoAcademico().getCodigo()!= null){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigo(): "+ entity.getGradoAcademico().getCodigo());
							 criteria.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));
							 criteriaCount.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));

						 }
					 }
					 if(entity.getNumeroFolio()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroFolio(): "+ entity.getNumeroFolio());
						 criteria.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));
						 criteriaCount.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));

					 }
					 if(entity.getNumeroLibro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroLibro(): "+ entity.getNumeroLibro());
						 criteria.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
						 criteriaCount.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
					 }
					if(entity.getNumeroRegistro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
						 criteria.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
						 criteriaCount.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
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
						criteria.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteria.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteriaCount.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
					}
					if(entity.getEspecialidad()!=null){
						if(entity.getEspecialidad().getCodigo()!=null){
							UNALMLogger.trace(log, method, "es.codigo: "+entity.getEspecialidad().getCodigo());
							criteria.add(Restrictions.eq("es.codigo",entity.getEspecialidad().getCodigo())); 
							criteriaCount.add(Restrictions.eq("es.codigo",entity.getEspecialidad().getCodigo())); 
						
						}
					}
					if(entity.getEspecialidadPostgrado()!=null){
						if(entity.getEspecialidadPostgrado().getCodigo()!=null){
							UNALMLogger.trace(log, method, "esp.codigo: "+entity.getEspecialidadPostgrado().getCodigo());
							criteria.add(Restrictions.eq("esp.codigo",entity.getEspecialidadPostgrado().getCodigo())); 
							criteriaCount.add(Restrictions.eq("esp.codigo",entity.getEspecialidadPostgrado().getCodigo())); 
						}
					}
					
				 }
				 if(agendas.size()>0){
					 criteria.add(Restrictions.in("ag.codigo", agendas));
					 criteriaCount.add(Restrictions.in("ag.codigo", agendas));
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
				 
				 criteria.addOrder(Order.asc("ga.numeroPrioridad"));
				 criteria.addOrder(Order.asc("er.flagDiplomaSexo").nulls(NullPrecedence.LAST));

				 criteria.addOrder(Order.asc("f.textoNombreEspanol"));
				 criteria.addOrder(Order.asc("fa.textoNombreEspanol"));
				 criteria.addOrder(Order.asc("es.textoNombreEspanol"));
				 criteria.addOrder(Order.asc("esp.textoNombreEspanol"));
				 //criteria.addOrder(Order.asc("e.textoNombreCompleto"));
				 criteria.addOrder(Order.asc("p.textoPaterno"));
				 criteria.addOrder(Order.asc("p.textoPaterno"));
				 criteria.addOrder(Order.asc("p.textoNombre"));

				 List<SCGEstudianteRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListDuplicadoAscendenteDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDuplicadoDAO";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
				 criteria.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

				 criteria.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
				 criteria.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("univm.pais","paism", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("univb.pais","paisb", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("programaEstudio","pe", JoinType.LEFT_OUTER_JOIN);
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteria.add(Restrictions.eq("flagDuplicado", "1"));
				 
	   Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
	   			criteriaCount.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

	   			criteriaCount.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
	   			criteriaCount.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
	   			criteriaCount.add(Restrictions.eq("flagDuplicado", "1"));
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
					 if(entity.getGradoAcademico()!=null){
						 if(entity.getGradoAcademico().getCodigo()!= null){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigo(): "+ entity.getGradoAcademico().getCodigo());
							 criteria.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));
							 criteriaCount.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));

						 }
					 }
					 if(entity.getNumeroFolio()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroFolio(): "+ entity.getNumeroFolio());
						 criteria.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));
						 criteriaCount.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));

					 }
					 if(entity.getNumeroLibro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroLibro(): "+ entity.getNumeroLibro());
						 criteria.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
						 criteriaCount.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
					 }
					if(entity.getNumeroRegistro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
						 criteria.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
						 criteriaCount.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
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
						criteria.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteria.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteriaCount.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
					}
					
				 }
				 if(agendas.size()>0){
					 criteria.add(Restrictions.in("ag.codigo", agendas));
					 criteriaCount.add(Restrictions.in("ag.codigo", agendas));
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

				 criteria.addOrder(Order.asc("numeroRegistro"));
				 List<SCGEstudianteRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListDuplicadoDescendenteDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDuplicadoDAO";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
				 criteria.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

				 criteria.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
				 criteria.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteria.add(Restrictions.eq("flagDuplicado", "1"));
				 
	   Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
	   			criteriaCount.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

	   			criteriaCount.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
	   			criteriaCount.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
	   			criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
	   			criteriaCount.add(Restrictions.eq("flagDuplicado", "1"));
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
					 if(entity.getGradoAcademico()!=null){
						 if(entity.getGradoAcademico().getCodigo()!= null){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigo(): "+ entity.getGradoAcademico().getCodigo());
							 criteria.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));
							 criteriaCount.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));

						 }
					 }
					 if(entity.getNumeroFolio()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroFolio(): "+ entity.getNumeroFolio());
						 criteria.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));
						 criteriaCount.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));

					 }
					 if(entity.getNumeroLibro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroLibro(): "+ entity.getNumeroLibro());
						 criteria.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
						 criteriaCount.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
					 }
					if(entity.getNumeroRegistro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
						 criteria.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
						 criteriaCount.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
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
						criteria.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteria.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteriaCount.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
					}
					
				 }
				 if(agendas.size()>0){
					 criteria.add(Restrictions.in("ag.codigo", agendas));
					 criteriaCount.add(Restrictions.in("ag.codigo", agendas));
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
				 
				 criteria.addOrder(Order.desc("numeroRegistro"));
				 List<SCGEstudianteRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public SCGEstudianteRegistroEntity getForUpdateDuplicadoDAO(SCGEstudianteRegistroEntity entity) {
		final String method = "getForUpdateDuplicadoDAO";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
				 criteria.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 

				 criteria.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
				
				 criteria.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadBachiller","unib", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("universidadMaestria","unim", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("unib.pais","pab", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("unim.pais","pam", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteria.add(Restrictions.eq("flagDuplicado", "1"));

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
				 SCGEstudianteRegistroEntity result = (SCGEstudianteRegistroEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateDuplicadoAdjuntoDAO(
			SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method = "getForUpdateDuplicadoAdjuntoDAO";
		final String params = "SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroAdjuntoEntity.class, "era");
			     criteria.createCriteria("adjunto","a", JoinType.LEFT_OUTER_JOIN);
			     criteria.createCriteria("a.tipoAdjunto","ta", JoinType.LEFT_OUTER_JOIN);
			     criteria.createCriteria("estudianteRegistro","er", JoinType.LEFT_OUTER_JOIN);	
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		//criteria.add(Restrictions.eq("flagDuplicado", "1"));
		if(entity !=null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo: "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(entity.getAdjunto() != null){
				if(entity.getAdjunto().getCodigo()!=null){
					UNALMLogger.trace(log, method, "a.codigo: "+entity.getAdjunto().getCodigo());
					criteria.add(Restrictions.eq("a.codigo",entity.getAdjunto().getCodigo()));
				}
				if(entity.getAdjunto().getTipoAdjunto()!=null){
					UNALMLogger.trace(log, method, "entity.getAdjunto().getTipoAdjunto(): "+entity.getAdjunto().getTipoAdjunto());
					if(entity.getAdjunto().getTipoAdjunto().getCodigo()!=null){
						UNALMLogger.trace(log, method, "ta.codigo: "+entity.getAdjunto().getTipoAdjunto().getCodigo());
						criteria.add(Restrictions.eq("ta.codigo",entity.getAdjunto().getTipoAdjunto().getCodigo()));
					}
					
				}
			}
			if(entity.getEstudianteRegistro() != null){
				if(entity.getEstudianteRegistro().getCodigo()!=null){
					UNALMLogger.trace(log, method, "er.codigo: "+entity.getEstudianteRegistro().getCodigo());
					criteria.add(Restrictions.eq("er.codigo",entity.getEstudianteRegistro().getCodigo()));
				}
			}
		}		
		SCGEstudianteRegistroAdjuntoEntity result = (SCGEstudianteRegistroAdjuntoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGAdjuntoEntity getForUpdateAdjuntoDuplicadoDAO(SCGAdjuntoEntity entity) {
		final String method = "getForUpdateAdjuntoDuplicadoDAO";
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
	public Long getNumberRegistroMaxDuplicadoDAO(SCGEstudianteRegistroEntity entity) {
		final String method = "getNumberRegistroMaxDuplicadoDAO";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagDuplicado","1" ));
		
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		criteria.add(Restrictions.eq("flagCandado","1" ));
		if(entity!=null){
			if(entity.getGradoAcademico()!=null){
				if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
					UNALMLogger.trace(log, method, "ga.codigo: "+entity.getGradoAcademico().getCodigoExterno());
					criteria.add(Restrictions.eq("ga.codigoExterno", entity.getGradoAcademico().getCodigoExterno()));	
				}
			}	
		}
		
		criteria.setProjection(Projections.projectionList()
	            .add(Projections.max("numeroRegistro")));		
		Long result = (Long)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroEntity saveDuplicadoDAO(SCGEstudianteRegistroEntity entity) {
		final String method="saveDuplicadoDAO";
		final String params="SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroEntity result=(SCGEstudianteRegistroEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity saveAdjuntoDuplicadoDAO(SCGAdjuntoEntity entity) {
		final String method="saveAdjuntoDuplicadoDAO";
		final String params="SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity result=(SCGAdjuntoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroAdjuntoEntity saveDuplicadoAdjuntoDAO(
			SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method="saveDuplicadoAdjuntoDAO";
		final String params="SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroAdjuntoEntity result=(SCGEstudianteRegistroAdjuntoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}

}

package pe.edu.lamolina.gradotitulo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPaisEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGProgramaEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class GradoTituloDAOHibernate extends HibernateTemplate implements GradoTituloDAO {
	
	private final static Logger log = Logger.getLogger(GradoTituloDAOHibernate.class);

	@Autowired
	public GradoTituloDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListEsudianteRegistroDAO";
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
				 criteria.add(Restrictions.eq("flagDuplicado", "0"));
		
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
				 criteriaCount.add(Restrictions.eq("flagDuplicado", "0"));
		
				 if(entity != null){
					 if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
						 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
						 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
						 criteriaCount.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
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
						 if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
							 criteria.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));
							 criteriaCount.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));

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
						/*
						criteria.add(Restrictions.between("fechaCierre", entity.getFechaCierreInicial(), entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.between("fechaCierre", entity.getFechaCierreInicial(),entity.getFechaCierreFinal())); 
						*/
						
						criteria.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteria.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteriaCount.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
					
					}
					if(!TypesUtil.isEmptyString(entity.getTextoResolucionRectoral())){
						UNALMLogger.trace(log, method, "entity.getTextoResolucionRectoral(): "+entity.getTextoResolucionRectoral());
						criteria.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral())); 
						criteriaCount.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral())); 
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
				 criteria.addOrder(Order.asc("mgt.numeroPrioridad"));
				 criteria.addOrder(Order.asc("er.flagDiplomaSexo").nulls(NullPrecedence.LAST));

				 criteria.addOrder(Order.asc("f.textoNombreEspanol"));				 
				 criteria.addOrder(Order.asc("fa.textoNombreEspanol"));
				 criteria.addOrder(Order.asc("es.textoNombreEspanol"));
				 criteria.addOrder(Order.asc("esp.textoNombreEspanol"));
				 //criteria.addOrder(Order.asc("e.textoNombreCompleto"));
				 criteria.addOrder(Order.asc("p.textoPaterno"));
				 criteria.addOrder(Order.asc("p.textoMaterno"));
				 criteria.addOrder(Order.asc("p.textoNombre"));
				 List<SCGEstudianteRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroAscendeteDAO(SCGEstudianteRegistroEntity entity,
			FilterUtil filterUtil, List<Long> agendas) {
		final String method = "getListEsudianteRegistroAscendeteDAO";
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
				 criteria.add(Restrictions.eq("flagDuplicado", "0"));
				 if(entity != null){
					 if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
						 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
						 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
					 }
					 if(entity.getEstudiante()!=null){
						 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoMatricula())){
							 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoMatricula(): "+ entity.getEstudiante().getTextoMatricula());
							 criteria.add(Restrictions.eq("e.textoMatricula",entity.getEstudiante().getTextoMatricula()));

						 }
						 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoNombreCompleto())){
							 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoNombreCompleto(): "+ entity.getEstudiante().getTextoNombreCompleto());
							 criteria.add(Restrictions.ilike("e.textoNombreCompleto",entity.getEstudiante().getTextoNombreCompleto(),MatchMode.ANYWHERE ));

						 }
						 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoCodigoExternoDocumento())){
							 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoCodigoExternoDocumento(): "+ entity.getEstudiante().getTextoCodigoExternoDocumento());
							 criteria.add(Restrictions.eq("e.textoCodigoExternoDocumento",entity.getEstudiante().getTextoCodigoExternoDocumento()));

						 }
						 if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoNumeroDocumento())){
							 UNALMLogger.trace(log, method, "entity.getEstudiante().getTextoNumeroDocumento(): "+ entity.getEstudiante().getTextoNumeroDocumento());
							 criteria.add(Restrictions.eq("e.textoNumeroDocumento",entity.getEstudiante().getTextoNumeroDocumento()));

						 }

					 }
					 if(entity.getGradoAcademico()!=null){
						 if(entity.getGradoAcademico().getCodigo()!= null){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigo(): "+ entity.getGradoAcademico().getCodigo());
							 criteria.add(Restrictions.eq("ga.codigo",entity.getGradoAcademico().getCodigo()));

						 }
						 if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
							 criteria.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));

						 }
					 }
					 if(entity.getNumeroFolio()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroFolio(): "+ entity.getNumeroFolio());
						 criteria.add(Restrictions.eq("er.numeroFolio",entity.getNumeroFolio()));

					 }
					 if(entity.getNumeroLibro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroLibro(): "+ entity.getNumeroLibro());
						 criteria.add(Restrictions.eq("er.numeroLibro",entity.getNumeroLibro()));
					 }
					if(entity.getNumeroRegistro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
						 criteria.add(Restrictions.eq("er.numeroRegistro",entity.getNumeroRegistro())); 
					}
					if(entity.getAgendaGrupo()!=null){
						if(entity.getAgendaGrupo().getCodigo()!=null){
							 UNALMLogger.trace(log, method, "entity.getAgendaGrupo().getCodigo(): "+ entity.getAgendaGrupo().getCodigo());
							 criteria.add(Restrictions.eq("ag.codigo",entity.getAgendaGrupo().getCodigo())); 
						}
					}
					if(entity.getFechaCierreInicial()!=null &&entity.getFechaCierreFinal()!=null){
						UNALMLogger.trace(log, method, "entity.getFechaCierreInicial(): "+entity.getFechaCierreInicial());
						UNALMLogger.trace(log, method, "entity.getFechaCierreFinal(): "+ entity.getFechaCierreFinal());
						criteria.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteria.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
					
					}
					if(!TypesUtil.isEmptyString(entity.getTextoResolucionRectoral())){
						UNALMLogger.trace(log, method, "entity.getTextoResolucionRectoral(): "+entity.getTextoResolucionRectoral());
						criteria.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral())); 
					}
				 }
				 /*
				 if(agendas.size()>0){
					 criteria.add(Restrictions.in("ag.codigo", agendas));
				 }
		
					
				 if (filterUtil.getStart() != null && filterUtil.getLimit() != null) {
					 UNALMLogger.trace(log, method, "filterUtil.getStart(): "+filterUtil.getStart());
					 criteria.setFirstResult(filterUtil.getStart());
					 UNALMLogger.trace(log, method, "filterUtil.getLimit(): "+filterUtil.getLimit());
					 criteria.setMaxResults(filterUtil.getLimit());
				 }
				 */
				 criteria.setFirstResult(0).setMaxResults(2000);
				 criteria.addOrder(Order.asc("numeroRegistro"));
				 List<SCGEstudianteRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	private Map<String,String> aliasColumn(){
		Map<String,String> column = new HashMap<String,String>();
		column.put("autoridadRegistroDecano","ard");
		column.put("ard.cargo","cd");
		column.put("autoridadRegistroSecretarioGeneral","arsg");
		column.put("arsg.cargo","csg");
		column.put("autoridadRegistroRector","arr");
		column.put("arr.cargo","cr");
		column.put("autoridadRegistroDirectorPostgrado","ardp");
		column.put("ardp.cargo","cp");	
		column.put("modalidadEstudio","me");
		column.put("modalidadGradoTitulo","mgt");
		column.put("gradoAcademico","ga");
		column.put("estudiante","e");
		column.put("e.persona","p");
		column.put("especialidad","es");
		column.put("especialidadPostgrado","esp");
		column.put("es.facultad","f");
		column.put("esp.facultad","fa");
		column.put("agendaGrupo","ag");
		column.put("universidadMaestria","univm");
		column.put("universidadBachiller","univb");
		column.put("adjunto","adj");
		return column;
	}

	@Override
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroDescendenteDAO(SCGEstudianteRegistroEntity entity,
			FilterUtil filterUtil, List<Long> agendas) {
		final String method = "getListEsudianteRegistroDescendenteDAO1";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
//				 criteria.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 
//
//				 criteria.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
//				
//				 criteria.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
//				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 Map<String,String> column = this.aliasColumn();
				 column.forEach(( name ,alias) -> criteria.createCriteria(name,alias, JoinType.LEFT_OUTER_JOIN));
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteria.add(Restrictions.eq("flagDuplicado", "0"));
		
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
//				 criteriaCount.createCriteria("autoridadRegistroDecano","ard", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("ard.cargo","cd", JoinType.LEFT_OUTER_JOIN);				 
//				 criteriaCount.createCriteria("autoridadRegistroSecretarioGeneral","arsg", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("arsg.cargo","csg", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("autoridadRegistroRector","arr", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("arr.cargo","cr", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("autoridadRegistroDirectorPostgrado","ardp", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("ardp.cargo","cp", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("modalidadEstudio","me", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("estudiante","e", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("e.persona","p", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("especialidad","es", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("especialidadPostgrado","esp", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("es.facultad","f", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("esp.facultad","fa", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("agendaGrupo","ag", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("universidadMaestria","univm", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("universidadBachiller","univb", JoinType.LEFT_OUTER_JOIN);
//				 criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 column.forEach((name,alias) -> criteriaCount.createCriteria(name,alias, JoinType.LEFT_OUTER_JOIN));
				 criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteriaCount.add(Restrictions.eq("flagDuplicado", "0"));
		
				 if(entity != null){
					 if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
						 UNALMLogger.trace(log, method, " entity.getFlagCandado(): "+ entity.getFlagCandado());
						 criteria.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
						 criteriaCount.add(Restrictions.eq("flagCandado",entity.getFlagCandado()));
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
						 if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
							 criteria.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));
							 criteriaCount.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));

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
						/*
						criteria.add(Restrictions.between("fechaCierre", entity.getFechaCierreInicial(), entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.between("fechaCierre", entity.getFechaCierreInicial(),entity.getFechaCierreFinal())); 
						*/
						
						criteria.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteria.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
						criteriaCount.add(Restrictions.ge("fechaAprobacioncu", entity.getFechaCierreInicial())); 
						criteriaCount.add(Restrictions.le("fechaAprobacioncu", entity.getFechaCierreFinal()));
					
					}
					if(!TypesUtil.isEmptyString(entity.getTextoResolucionRectoral())){
						UNALMLogger.trace(log, method, "entity.getTextoResolucionRectoral(): "+entity.getTextoResolucionRectoral());
						criteria.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral())); 
						criteriaCount.add(Restrictions.eq("textoResolucionRectoral",entity.getTextoResolucionRectoral())); 
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
	public List<SCGEstudianteRegistroEntity> listDependentRecordsDAO(SCGEstudianteRegistroEntity entity) {
		final String method = "listDependentRecordsDAO";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
			Map<String,String> column = this.aliasColumn();
			column.forEach(( name, alias) -> criteria.createCriteria(name,alias, JoinType.LEFT_OUTER_JOIN));
			criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
			criteria.add(Restrictions.eq("flagDuplicado", "0"));
		if(entity != null){
			if( entity.getAutoridadRegistroDecano() != null ){
				if( entity.getAutoridadRegistroDecano().getCodigo() != null ){
					UNALMLogger.trace(log, method, "entity.getAutoridadRegistroDecano().getCodigo(): "
							+ entity.getAutoridadRegistroDecano().getCodigo()); 
					criteria.add(Restrictions.eq("ard.codigo", entity.getAutoridadRegistroDecano().getCodigo()));
				}
			}
			if( entity.getAutoridadRegistroDirectorPostgrado() != null ){
				if( entity.getAutoridadRegistroDirectorPostgrado().getCodigo() != null ){
					UNALMLogger.trace(log, method, "entity.getAutoridadRegistroDirectorPostgrado().getCodigo(): "
							+ entity.getAutoridadRegistroDirectorPostgrado().getCodigo()); 
					criteria.add(Restrictions.eq("ardp.codigo", entity.getAutoridadRegistroDirectorPostgrado().getCodigo()));
				}
			}
			if( entity.getAutoridadRegistroRector() != null ){
				if( entity.getAutoridadRegistroRector().getCodigo() != null ){
					UNALMLogger.trace(log, method, "entity.getAutoridadRegistroRector().getCodigo(): "
							+ entity.getAutoridadRegistroRector().getCodigo()); 
					criteria.add(Restrictions.eq("arr.codigo", entity.getAutoridadRegistroRector().getCodigo()));
				}
			}
			if( entity.getAutoridadRegistroSecretarioGeneral() != null ){
				if( entity.getAutoridadRegistroSecretarioGeneral().getCodigo() != null ){
					UNALMLogger.trace(log, method, "entity.getAutoridadRegistroSecretarioGeneral().getCodigo(): "
							+ entity.getAutoridadRegistroSecretarioGeneral().getCodigo()); 
					criteria.add(Restrictions.eq("arsg.codigo", entity.getAutoridadRegistroSecretarioGeneral().getCodigo()));
				}
			}
			
		}
		List<SCGEstudianteRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public List<SCGPaisEntity> getListPaisDAO(SCGPaisEntity entity) {
		final String method = "getListPaisDAO";
		final String params = "SCGPaisEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPaisEntity.class, "p");
		 		 criteria.addOrder(Order.asc("textoNombre"));
		List<SCGPaisEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGUniversidadEntity> getLisUniversidadDAO(SCGUniversidadEntity entity) {
		final String method = "getLisUniversidadDAO";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGUniversidadEntity.class, "u");
			criteria.createCriteria("tipoUniversidad","tu", JoinType.LEFT_OUTER_JOIN);
			criteria.createCriteria("pais","p", JoinType.LEFT_OUTER_JOIN);
		if(entity!= null){
			if(entity.getPais() != null){
				if(entity.getPais().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getPais().getCodigo(): "+entity.getPais().getCodigo());
					criteria.add(Restrictions.eq("p.codigo", entity.getPais().getCodigo()));
				}
			}
		}
			criteria.addOrder(Order.asc("textoNombre"));
		List<SCGUniversidadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGModalidadGradoTituloGrupoEntity> getListModalidadGradoTituloGrupoDAO(SCGModalidadGradoTituloGrupoEntity entity) {
		final String method = "getListModalidadGradoTituloGrupoDAO";
		final String params = "SCGModalidadGradoTitulo entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGModalidadGradoTituloGrupoEntity.class, "mgtg");
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("modalidadGradoTitulo","mgt", JoinType.LEFT_OUTER_JOIN);
		if(entity!= null){
			if(entity.getGradoAcademico() != null){
				if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
					UNALMLogger.trace(log, method, "entity.getModalidadGradoAcademico().getCodigoExterno(): "+entity.getGradoAcademico().getCodigoExterno());
					criteria.add(Restrictions.ilike("ga.codigoExterno", entity.getGradoAcademico().getCodigoExterno(), MatchMode.ANYWHERE ));
				}
				if(entity.getGradoAcademico().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigo(): "+entity.getGradoAcademico().getCodigo());
					criteria.add(Restrictions.ilike("ga.codigo", entity.getGradoAcademico().getCodigo()));
				}
			}
		}
		List<SCGModalidadGradoTituloGrupoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGModalidadEstudioEntity> getListModalidadEstudioDAO(SCGModalidadEstudioEntity entity) {
		final String method = "getListModalidadEstudioDAO";
		final String params = "SCGModalidadEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGModalidadEstudioEntity.class, "mgtg");
		List<SCGModalidadEstudioEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGGradoAcademicoEntity> getListGradoAcademicoDAO(SCGGradoAcademicoEntity entity) {
		final String method = "getListGradoAcademicoDAO";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGGradoAcademicoEntity.class, "mgtg");
		if(entity != null){
			 if(!TypesUtil.isEmptyString(entity.getCodigoExterno())){
				 UNALMLogger.trace(log, method, "entity.getCodigoExterno(): "+ entity.getCodigoExterno());
				 criteria.add(Restrictions.eq("codigoExterno",entity.getCodigoExterno()));
			 }
		 }
		List<SCGGradoAcademicoEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public SCGEstudianteRegistroEntity getForUpdateEsudianteRegistroDAO(SCGEstudianteRegistroEntity entity) {
		final String method = "getForUpdateEsudianteRegistroDAO";
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
				 criteria.createCriteria("programaEstudio","pge", JoinType.LEFT_OUTER_JOIN);				 
				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
				 criteria.add(Restrictions.eq("flagDuplicado", "0"));

				 if(entity != null){
					 if(entity.getCodigo()!=null){
						 UNALMLogger.trace(log, method, "entity.getCodigo(): "+ entity.getCodigo());
						 criteria.add(Restrictions.eq("codigo",entity.getCodigo()));
					 }
					 if(entity.getNumeroRegistro()!=null){
						 UNALMLogger.trace(log, method, "entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
						 criteria.add(Restrictions.eq("numeroRegistro",entity.getNumeroRegistro()));
					 }
					 if(entity.getGradoAcademico()!=null){
						 if(entity.getGradoAcademico().getCodigoExterno()!=null){
							 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
							 criteria.add(Restrictions.eq("ga.codigoExterno", entity.getGradoAcademico().getCodigoExterno()));
						
						 }
					 }

				 }
				 SCGEstudianteRegistroEntity result = (SCGEstudianteRegistroEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGGradoAcademicoEntity getForUpdateGradoAcademicoDAO(SCGGradoAcademicoEntity entity) {
		final String method = "getForUpdateGradoAcademico";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGGradoAcademicoEntity.class, "ga");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
				UNALMLogger.trace(log, method, "codigo: "+entity.getCodigo());
			}
			if(!TypesUtil.isEmptyString(entity.getCodigoExterno())){
				criteria.add(Restrictions.eq("codigoExterno",entity.getCodigoExterno()));
				UNALMLogger.trace(log, method, "codigoExterno: "+entity.getCodigoExterno());
			}
			if(!TypesUtil.isEmptyString(entity.getTextoNombre())){
				criteria.add(Restrictions.eq("textoNombre",entity.getTextoNombre()));
				UNALMLogger.trace(log, method, "textoNombre: "+entity.getTextoNombre());
			}
		}		
		SCGGradoAcademicoEntity result = (SCGGradoAcademicoEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGUniversidadEntity getForUpdateUniversidadDAO(SCGUniversidadEntity entity) {
		final String method = "getForUpdateUniversidadDAO";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGUniversidadEntity.class, "u");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
				UNALMLogger.trace(log, method, "codigo: "+entity.getCodigo());
			}
			if(!TypesUtil.isEmptyString(entity.getCodigoExterno())){
				criteria.add(Restrictions.eq("codigoExterno",entity.getCodigoExterno()));
				UNALMLogger.trace(log, method, "codigoExterno: "+entity.getCodigoExterno());
			}
		}		
		SCGUniversidadEntity result = (SCGUniversidadEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateEstudianteRegistroAdjuntoDAO(
			SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method = "getForUpdateEstudianteRegistroAdjuntoDAO";
		final String params = "SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroAdjuntoEntity.class, "era");
			     criteria.createCriteria("adjunto","a", JoinType.LEFT_OUTER_JOIN);
			     criteria.createCriteria("a.tipoAdjunto","ta", JoinType.LEFT_OUTER_JOIN);
			     criteria.createCriteria("estudianteRegistro","er", JoinType.LEFT_OUTER_JOIN);	
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
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
	public SCGModalidadEstudioEntity getForUpdateModalidadEstudioDAO(SCGModalidadEstudioEntity entity) {
		final String method = "getForUpdateModalidadEstudioDAO";
		final String params = "SCGModalidadEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGModalidadEstudioEntity.class, "p");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigoExterno() != null){
				criteria.add(Restrictions.eq("codigoExterno", entity.getCodigoExterno()));
			}
			
		}		
		SCGModalidadEstudioEntity result = (SCGModalidadEstudioEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGModalidadGradoTituloEntity getForUpdateModalidadGradoTituloDAO(SCGModalidadGradoTituloEntity entity) {
		final String method = "getForUpdateModalidadGradoTituloDAO";
		final String params = "SCGModalidadGradoTituloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGModalidadGradoTituloEntity.class, "mgt");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity !=null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo: "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			if(entity.getCodigoExterno() != null){
				UNALMLogger.trace(log, method, "codigoExterno: "+entity.getCodigoExterno());
				criteria.add(Restrictions.eq("codigoExterno", entity.getCodigoExterno()));
			}
			
		}		
		SCGModalidadGradoTituloEntity result = (SCGModalidadGradoTituloEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public Long getNumberRegistroMaxDAO(SCGEstudianteRegistroEntity entity) {
		final String method = "getNumberRegistroMax";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEstudianteRegistroEntity.class, "er");
				 criteria.createCriteria("gradoAcademico","ga", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagDuplicado","0" ));

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
	            //.add(Projections.groupProperty("flagCandado"))
	            .add(Projections.max("numeroRegistro")));		
		//SCGEstudianteRegistroEntity result = (SCGEstudianteRegistroEntity)criteria.uniqueResult();
		Long result = (Long)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroEntity saveEsudianteRegistroDAO(SCGEstudianteRegistroEntity entity) {
		final String method="saveEsudianteRegistroDAO";
		final String params="SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroEntity result=(SCGEstudianteRegistroEntity)this.getSessionFactory().getCurrentSession().merge(entity);
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
	public SCGEstudianteRegistroAdjuntoEntity saveEstudianteRegistroAdjuntoDAO(SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method="saveEstudianteRegistroAdjuntoDAO";
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

	@Override
	public List<SCGProgramaEstudioEntity> getListProgramaEstudioDAO(SCGProgramaEstudioEntity entity) {
		final String method = "getListProgramaEstudioDAO";
		final String params = "SCGProgramaEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGProgramaEstudioEntity.class, "pe");
		 		 criteria.addOrder(Order.asc("textoNombre"));
		List<SCGProgramaEstudioEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGProgramaEstudioEntity getForUpdateProgramaEstudioDAO(SCGProgramaEstudioEntity entity) {
		final String method = "getForUpdateProgramaEstudioDAO";
		final String params = "SCGProgramaEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGProgramaEstudioEntity.class, "p");
		criteria.add(Restrictions.eq("flagActivo", "1"));
		if(entity !=null){
			UNALMLogger.trace(log, method, "entity: "+entity);
			if(entity.getCodigo() !=null){
				UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
				criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
			}
			
		}		
		SCGProgramaEstudioEntity result = (SCGProgramaEstudioEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	
}

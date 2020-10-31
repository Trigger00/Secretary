package pe.edu.lamolina.gradotitulo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.lowagie.text.xml.simpleparser.EntitiesToUnicode;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoRevalidaEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class RevalidaDAOHibernate extends HibernateTemplate implements RevalidaDAO {

	private final static Logger log = Logger.getLogger(RevalidaDAOHibernate.class);

	@Autowired
	public RevalidaDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
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
		column.put("universidadRevalida","univr");
		column.put("tipoRevalida","tr");
		column.put("univm.pais","pam");
		column.put("univb.pais","pab");
		column.put("univr.pais","par");	
		column.put("adjunto","adj");
		return column;
	}
	@Override
	public List<SCGRevalidaEntity> getListRevalidaDAO(SCGRevalidaEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListRevalidaDAO";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
		 criteria.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);	
		 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
		 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		 
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
		 criteriaCount.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);	
		 criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
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
				 if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
					 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
					 criteria.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));
					 criteriaCount.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));

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
				criteriaCount.add(Restrictions.le("fechaCierre", entity.getFechaCierreFinal()));
			}
			if(entity.getTipoRevalida() !=null){
				if(entity.getTipoRevalida().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getTipoRevalida().getCodigo(): "+entity.getTipoRevalida().getCodigo());
					criteria.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
					criteriaCount.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
				}
				if(!TypesUtil.isEmptyString(entity.getTipoRevalida().getCodigoExterno())){
					UNALMLogger.trace(log, method, "entity.getTipoRevalida().getCodigoExterno(): "+entity.getTipoRevalida().getCodigoExterno());
					criteria.add(Restrictions.eq("tr.codigoExterno",entity.getTipoRevalida().getCodigoExterno()));
					criteriaCount.add(Restrictions.eq("tr.codigoExterno",entity.getTipoRevalida().getCodigoExterno()));
				}
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
		 
		 criteria.addOrder(Order.desc("r.numeroRegistro"));
		List<SCGRevalidaEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGRevalidaEntity> getListRevalidaAscendeteDAO(SCGRevalidaEntity entity, FilterUtil filterUtil,
			List<Long> agendas) {
		final String method = "getListRevalidaDAO";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
		 criteria.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);	
		 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
		 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		 
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
		 criteriaCount.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);	
		 criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
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
				 if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
					 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
					 criteria.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));
					 criteriaCount.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));

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
				criteriaCount.add(Restrictions.le("fechaCierre", entity.getFechaCierreFinal()));
			}
			if(entity.getTipoRevalida() !=null){
				if(entity.getTipoRevalida().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getTipoRevalida().getCodigo(): "+entity.getTipoRevalida().getCodigo());
					criteria.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
					criteriaCount.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
				}
				if(!TypesUtil.isEmptyString(entity.getTipoRevalida().getCodigoExterno())){
					UNALMLogger.trace(log, method, "entity.getTipoRevalida().getCodigoExterno(): "+entity.getTipoRevalida().getCodigoExterno());
					criteria.add(Restrictions.eq("tr.codigoExterno",entity.getTipoRevalida().getCodigoExterno()));
					criteriaCount.add(Restrictions.eq("tr.codigoExterno",entity.getTipoRevalida().getCodigoExterno()));
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
		 
		 criteria.addOrder(Order.asc("numeroRegistro"));
		List<SCGRevalidaEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGRevalidaEntity> getListRevalidaDescendenteDAO(SCGRevalidaEntity entity, FilterUtil filterUtil,
			List<Long> agendas) {
		final String method = "getListRevalidaDAO";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
		 criteria.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
		 criteria.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);	
		 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
		 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		 
		Criteria criteriaCount = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
		 criteriaCount.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);	
		 criteriaCount.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
		 criteriaCount.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
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
				 if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getCodigoExterno())){
					 UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
					 criteria.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));
					 criteriaCount.add(Restrictions.eq("ga.codigoExterno",entity.getGradoAcademico().getCodigoExterno()));

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
				criteriaCount.add(Restrictions.le("fechaCierre", entity.getFechaCierreFinal()));
			}
			if(entity.getTipoRevalida() !=null){
				if(entity.getTipoRevalida().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getTipoRevalida().getCodigo(): "+entity.getTipoRevalida().getCodigo());
					criteria.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
					criteriaCount.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
				}
				if(!TypesUtil.isEmptyString(entity.getTipoRevalida().getCodigoExterno())){
					UNALMLogger.trace(log, method, "entity.getTipoRevalida().getCodigoExterno(): "+entity.getTipoRevalida().getCodigoExterno());
					criteria.add(Restrictions.eq("tr.codigoExterno",entity.getTipoRevalida().getCodigoExterno()));
					criteriaCount.add(Restrictions.eq("tr.codigoExterno",entity.getTipoRevalida().getCodigoExterno()));
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
		 
		 criteria.addOrder(Order.desc("numeroRegistro"));
		List<SCGRevalidaEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGRevalidaEntity> listDependentRecordsDAO(SCGRevalidaEntity entity) {
		final String method = "listDependentRecordsDAO";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
		Map<String,String> column = this.aliasColumn();
		column.forEach(( name ,alias) -> criteria.createCriteria(name,alias, JoinType.LEFT_OUTER_JOIN));
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		
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
		List<SCGRevalidaEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGRevalidaEntity getForUpdateRevalidaDAO(SCGRevalidaEntity entity) {
		final String method = "getForUpdateRevalidaDAO";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
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
				 criteria.createCriteria("universidadRevalida","univr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("univm.pais","pam", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("univb.pais","pab", JoinType.LEFT_OUTER_JOIN);
				 criteria.createCriteria("univr.pais","par", JoinType.LEFT_OUTER_JOIN);			
				 criteria.createCriteria("programaEstudio","pge", JoinType.LEFT_OUTER_JOIN);				 
				 criteria.createCriteria("adjunto","adj", JoinType.LEFT_OUTER_JOIN);
				 criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
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
				 SCGRevalidaEntity result = (SCGRevalidaEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGRevalidaEntity saveRevalidaDAO(SCGRevalidaEntity entity) {
		final String method="saveRevalidaDAO";
		final String params="SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGRevalidaEntity result=(SCGRevalidaEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SCGTipoRevalidaEntity getForUpdateTipoRevalidaDAO(SCGTipoRevalidaEntity entity) {
		final String method = "getForUpdateTipoRevalidaDAO";
		final String params = "SCGTipoRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoRevalidaEntity.class, "tr");
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
		SCGTipoRevalidaEntity result = (SCGTipoRevalidaEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public Long getNumberRegistroMaxRevalidaDAO(SCGRevalidaEntity entity) {
		final String method = "getNumberRegistroMaxRevalidaDAO";
		UNALMLogger.entry(log, method);
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGRevalidaEntity.class, "r");
		 		 criteria.createCriteria("tipoRevalida","tr", JoinType.LEFT_OUTER_JOIN);
		if(entity !=null){
			if(entity.getTipoRevalida() != null){
				if(entity.getTipoRevalida().getCodigo()!=null){
					UNALMLogger.trace(log, method, "tr.codigo: "+entity.getTipoRevalida().getCodigo());
					criteria.add(Restrictions.eq("tr.codigo", entity.getTipoRevalida().getCodigo()));
				}
			}
		}	
		criteria.setProjection(Projections.projectionList() .add(Projections.max("numeroRegistro")));		
		Long result = (Long)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	

}

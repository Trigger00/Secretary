package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCargoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class AutoridadDAOHibernate extends HibernateTemplate implements AutoridadDAO {
	
	private final static Logger log = Logger.getLogger(AutoridadDAOHibernate.class);

	@Autowired
	public AutoridadDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<SCGAutoridadRegistroEntity> getListAutoridadDAO(SCGAutoridadRegistroEntity entity) {
		final String method = "getListAutoridadDAO";
		final String params = "SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGAutoridadRegistroEntity.class, "ar");
		criteria.createCriteria("cargo","c", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("c.facultad","f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("gradoAcademico", "ga", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!= null){
			if(entity.getTextoNombreAutoridad() != null&&entity.getTextoNombreAutoridad()!=""){
				UNALMLogger.trace(log, method, "getTextoNombreAutoridad "+entity.getTextoNombreAutoridad());
				criteria.add(Restrictions.ilike("textoNombreAutoridad", entity.getTextoNombreAutoridad(), MatchMode.ANYWHERE ));
			}
			if( !TypesUtil.isEmptyString(entity.getFlagEstado())){			
				UNALMLogger.trace(log, method, "getFlagEstado "+entity.getFlagEstado());
				criteria.add(Restrictions.eq("flagEstado", entity.getFlagEstado() ));
			}
			if(entity.getCargo() !=null ){
				if( !TypesUtil.isEmptyString(entity.getCargo().getTextoNombre()) ){
					UNALMLogger.trace(log, method, "getCargo().getTextoNombre "+entity.getCargo().getTextoNombre());
					criteria.add(Restrictions.eq("c.textoNombre", entity.getCargo().getTextoNombre()));
				}
				if( !TypesUtil.isEmptyString(entity.getCargo().getCodigoExterno()) ){
					UNALMLogger.trace(log, method, "getCargo().getCodigoExterno "+entity.getCargo().getCodigoExterno());
					criteria.add(Restrictions.eq("c.codigoExterno", entity.getCargo().getCodigoExterno()));
				}
				if( entity.getCargo().getFacultad()!=null ){
					if(entity.getCargo().getFacultad().getCodigo()!=null){
						UNALMLogger.trace(log, method, " entity.getCargo().getFacultad().getCodigo() "+ entity.getCargo().getFacultad().getCodigo());
						criteria.add(Restrictions.eq("f.codigo",  entity.getCargo().getFacultad().getCodigo()));
					
					}
				}
				if( entity.getCargo().getCodigo()!=null ){
					if(entity.getCargo().getCodigo()!=null){
						UNALMLogger.trace(log, method, " entity.getCargo().getCodigo() "+ entity.getCargo().getCodigo());
						criteria.add(Restrictions.eq("c.codigo",  entity.getCargo().getCodigo()));
					
					}
				}
			}
			if(entity.getGradoAcademico() !=null ){
				if(entity.getGradoAcademico().getTextoNombre() !=null && entity.getGradoAcademico().getTextoNombre()!="" ){
					UNALMLogger.trace(log, method, "getGradoAcademico().getTextoNombre "+entity.getGradoAcademico().getTextoNombre());
					criteria.add(Restrictions.ilike("ga.textoNombre", entity.getGradoAcademico().getTextoNombre() , MatchMode.ANYWHERE ));
				}
			}
		}
		criteria.addOrder(Order.asc("textoNombreAutoridad"));
		List<SCGAutoridadRegistroEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGPeriodoEntity> getListPeriodoDAO(SCGPeriodoEntity entity) {
		final String method = "getListPeriodoDAO";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPeriodoEntity.class, "p");
		criteria.createCriteria("autoridadRegistro","ar", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!= null){
			if(entity.getAutoridadRegistro() !=null ){
				if(entity.getAutoridadRegistro().getCodigo() !=null ){
					UNALMLogger.trace(log, method, "getAutoridadRegistro().getCodigo "+entity.getAutoridadRegistro().getCodigo());
					criteria.add(Restrictions.eq("ar.codigo", entity.getAutoridadRegistro().getCodigo()));
				}
			}
		}
		if(entity.getFechaInicio() != null && entity.getFechaFinal() != null){
			UNALMLogger.trace(log, method, "entity.getFechaInicio(): "+entity.getFechaInicio());
			UNALMLogger.trace(log, method, "entity.getFechaFinal(): "+entity.getFechaFinal());
			criteria.add(Restrictions.ge("fechaInicio", entity.getFechaInicio())); 
			criteria.add(Restrictions.le("fechaFinal", entity.getFechaFinal()));
		}
		List<SCGPeriodoEntity>  result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGGradoAcademicoEntity> getListGradoAcademicoDAO(SCGGradoAcademicoEntity entity) {
		final String method = "getListGradoAcademico";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGGradoAcademicoEntity.class, "ga");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		List<SCGGradoAcademicoEntity>  result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGCargoEntity> getListCargoDAO(SCGCargoEntity entity) {
		final String method = "getListCargoDAO";
		final String params = "SCGCargoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGCargoEntity.class, "c");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		List<SCGCargoEntity>  result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	


	@Override
	public void deleteDAO() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SCGAutoridadRegistroEntity getForUpdateAutoridadDAO(SCGAutoridadRegistroEntity entity) {
		final String method = "getForUpdateAutoridadDAO";
		final String params = "SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGAutoridadRegistroEntity.class, "ar");
		criteria.createCriteria("cargo","c", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("gradoAcademico", "ga", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("adjunto", "a", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		UNALMLogger.trace(log, method, "SCGFacultadEntity: "+entity.getCodigo());
		criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
		SCGAutoridadRegistroEntity result = (SCGAutoridadRegistroEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPeriodoEntity getForUpdatePeriodoDAO(SCGPeriodoEntity entity) {
		final String method = "getForUpdatePeriodoDAO";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGPeriodoEntity.class, "p");
		criteria.createCriteria("autoridadRegistro","ar", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		UNALMLogger.trace(log, method, "SCGFacultadEntity: "+entity.getCodigo());
		criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
		SCGPeriodoEntity result = (SCGPeriodoEntity)criteria.uniqueResult();
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
	public SCGAutoridadRegistroEntity saveAutoridadDAO(SCGAutoridadRegistroEntity entity) {
		final String method="saveAutoridadDAO";
		final String params="SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAutoridadRegistroEntity result=(SCGAutoridadRegistroEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPeriodoEntity savePeriodoDAO(SCGPeriodoEntity entity) {
		final String method="savePeriodoDAO";
		final String params="SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPeriodoEntity result=(SCGPeriodoEntity)this.getSessionFactory().getCurrentSession().merge(entity);
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

	

}

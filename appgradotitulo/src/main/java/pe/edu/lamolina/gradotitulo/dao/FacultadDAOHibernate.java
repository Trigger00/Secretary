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
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoEspecialidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.TypesUtil;

@Repository
public class FacultadDAOHibernate extends HibernateTemplate implements FacultadDAO {

	private final static Logger log = Logger.getLogger(FacultadDAOHibernate.class);

	@Autowired
	public FacultadDAOHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	
	@Override
	public List<SCGFacultadEntity> getListFacultadDAO(SCGFacultadEntity entity) {
		final String method = "getListFacultadDAO";
		final String params = "SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGFacultadEntity.class, "f");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!= null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo "+entity.getCodigo());
				criteria.add(Restrictions.ilike("codigo", entity.getTextoNombreEspanol(), MatchMode.ANYWHERE ));
			}
			if(entity.getTextoNombreEspanol() != null){
				UNALMLogger.trace(log, method, "textoNombreEspanol "+entity.getTextoNombreEspanol());
				criteria.add(Restrictions.ilike("textoNombreEspanol", entity.getTextoNombreEspanol(), MatchMode.ANYWHERE ));
			}
			if(entity.getCodigoExterno() !=null && entity.getCodigoExterno()!=""){
				UNALMLogger.trace(log, method, "getCodigoExterno "+entity.getCodigoExterno());
				criteria.add(Restrictions.eq("codigoExterno", entity.getCodigoExterno() ));
			}
			if(entity.getFlagEstado()!=null && entity.getFlagEstado()!=""){
				UNALMLogger.trace(log, method, "getFlagEstado "+entity.getFlagEstado());
				criteria.add(Restrictions.eq("flagEstado", entity.getFlagEstado() ));
			}
			
			
		}
		criteria.addOrder(Order.asc("textoNombreEspanol"));
		List<SCGFacultadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGEspecialidadEntity> getListEspecialidadDAO(SCGEspecialidadEntity entity) {
		final String method = "getListEspecialidadDAO";
		final String params = "SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEspecialidadEntity.class,"e");
		criteria.createCriteria("facultad","f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoEspecialidad","te", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		if(entity!= null){
			if(entity.getCodigo() != null){
				UNALMLogger.trace(log, method, "codigo "+entity.getCodigo());
				criteria.add(Restrictions.ilike("codigo", entity.getTextoNombreEspanol(), MatchMode.ANYWHERE ));
			}
			if(entity.getTextoNombreEspanol() != null&&entity.getTextoNombreEspanol()!=""){
				UNALMLogger.trace(log, method, "textoNombreEspanol "+entity.getTextoNombreEspanol());
				criteria.add(Restrictions.ilike("textoNombreEspanol", entity.getTextoNombreEspanol(), MatchMode.ANYWHERE ));
			}
			if(entity.getCodigoExterno() !=null && entity.getCodigoExterno()!=""){
				UNALMLogger.trace(log, method, "getCodigoExterno "+entity.getCodigoExterno());
				criteria.add(Restrictions.eq("codigoExterno", entity.getCodigoExterno() ));
			}
			if(entity.getFacultad() !=null ){
				if(entity.getFacultad().getTextoNombreEspanol() !=null && entity.getFacultad().getTextoNombreEspanol()!="" ){
					UNALMLogger.trace(log, method, "getFacultad().getTextoNombreEspanol "+entity.getFacultad().getTextoNombreEspanol());
					criteria.add(Restrictions.ilike("f.textoNombreEspanol", entity.getFacultad().getTextoNombreEspanol() , MatchMode.ANYWHERE ));
				}
				if(entity.getFacultad().getCodigo() !=null ){
					UNALMLogger.trace(log, method, "getFacultad().getCodigo() "+entity.getFacultad().getCodigo());
					criteria.add(Restrictions.eq("f.codigo", entity.getFacultad().getCodigo() ));
				}
			}
			
			if(entity.getTipoEspecialidad() !=null ){
				if(entity.getTipoEspecialidad().getTextoNombreEspanol() !=null && entity.getTipoEspecialidad().getTextoNombreEspanol()!="" ){
					UNALMLogger.trace(log, method, "getTipoEspecialidad().getTextoNombreEspanol "+entity.getTipoEspecialidad().getTextoNombreEspanol());
					criteria.add(Restrictions.ilike("te.textoNombreEspanol", entity.getTipoEspecialidad().getTextoNombreEspanol() , MatchMode.ANYWHERE ));
				}
				if(entity.getTipoEspecialidad().getCodigo() !=null ){
					UNALMLogger.trace(log, method, "getTipoEspecialidad().getCodigo() "+entity.getTipoEspecialidad().getCodigo());
					criteria.add(Restrictions.eq("te.codigo", entity.getTipoEspecialidad().getCodigo() ));
				}
				if(!TypesUtil.isEmptyString(entity.getTipoEspecialidad().getCodigoExterno())){
					UNALMLogger.trace(log, method, "entity.getTipoEspecialidad().getCodigoExterno() "+entity.getTipoEspecialidad().getCodigoExterno());
					criteria.add(Restrictions.ilike("te.codigoExterno", entity.getTipoEspecialidad().getCodigoExterno() , MatchMode.ANYWHERE ));
				}
			}
			if(!TypesUtil.isEmptyString(entity.getFlagEstado())){			
				UNALMLogger.trace(log, method, "getFlagEstado "+entity.getFlagEstado());
				criteria.add(Restrictions.eq("flagEstado", entity.getFlagEstado() ));
			}
			
			
		}
		criteria.addOrder(Order.asc("f.textoNombreEspanol")); 
		criteria.addOrder(Order.asc("textoNombreEspanol")); 
		List<SCGEspecialidadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGTipoEspecialidadEntity> getListTipoEspecialidadDAO(SCGTipoEspecialidadEntity entity) {
		final String method = "getListTipoEspecialidadDAO";
		final String params = "SCGTipoEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGTipoEspecialidadEntity.class, "tp");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		List<SCGTipoEspecialidadEntity> result = criteria.list();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGFacultadEntity getForUpdateDAO(SCGFacultadEntity entity) {
		final String method = "getForUpdateDAO";
		final String params = "SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGFacultadEntity.class, "f");
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		UNALMLogger.trace(log, method, "SCGFacultadEntity: "+entity.getCodigo());
		criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
		SCGFacultadEntity result = (SCGFacultadEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGEspecialidadEntity getForUpdateEspecialidadDAO(SCGEspecialidadEntity entity) {
		final String method = "getForUpdateEspecialidadDAO";
		final String params = "SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SCGEspecialidadEntity.class,"e");
		criteria.createCriteria("facultad","f", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("tipoEspecialidad","te", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("flagEliminado", ApplicationConstant.FLAG_ELIMINADO));
		criteria.add(Restrictions.eq("codigo", entity.getCodigo()));
		SCGEspecialidadEntity result = (SCGEspecialidadEntity)criteria.uniqueResult();
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGFacultadEntity saveDAO(SCGFacultadEntity entity) {
		final String method="saveDAO";
		final String params="SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGFacultadEntity result=(SCGFacultadEntity)this.merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGEspecialidadEntity saveEspecialidadDAO(SCGEspecialidadEntity entity) {
		final String method="saveEspecialidadDAO";
		final String params="SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEspecialidadEntity result=(SCGEspecialidadEntity)this.getSessionFactory().getCurrentSession().merge(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public void deleteDAO(SCGFacultadEntity entity) {
		this.getSessionFactory().getCurrentSession().delete(entity);
		
	}


}

package pe.edu.lamolina.gradotitulo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.filemanager.component.FileManagerComponent;
import pe.edu.lamolina.gradotitulo.dao.AutoridadDAO;
import pe.edu.lamolina.gradotitulo.dao.FacultadDAOHibernate;
import pe.edu.lamolina.gradotitulo.dao.GradoTituloDAO;
import pe.edu.lamolina.gradotitulo.dao.RevalidaDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCargoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.TypesUtil;
@Service
@Transactional(rollbackFor = Exception.class)
public class AutoridadServiceImpl implements AutoridadService {
	
	private final static Logger log = Logger.getLogger(FacultadDAOHibernate.class);

	@Autowired
	private AutoridadDAO autoridadDAO;
	
	@Autowired
	private GradoTituloDAO gradoTituloDAO;
	
	@Autowired
	private RevalidaDAO	 revalidaDAO;
	
	@Autowired
	private FileManagerComponent fileManagerComponent;
	@Override
	public List<SCGAutoridadRegistroEntity> getListAutoridad(SCGAutoridadRegistroEntity entity , SCGPeriodoEntity periodo) {
		final String method = "getListAutoridad";
		final String params = "SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGPeriodoEntity> listPeriodo = new ArrayList<SCGPeriodoEntity>();
		List<SCGAutoridadRegistroEntity> result = new ArrayList<SCGAutoridadRegistroEntity>();
		
		
    	if(periodo.getFechaInicio()!=null &&periodo.getFechaFinal()!=null){
    		//String fechaInvalida="1000-01-01";
    		String formatFechaInicio= TypesUtil.getDefaultString(periodo.getFechaInicio(), "yyy-MM-dd");
			UNALMLogger.trace(log, method,"formatFechaInicio " +formatFechaInicio);
    		String formatFechaFinal= TypesUtil.getDefaultString(periodo.getFechaFinal(), "yyy-MM-dd");
			UNALMLogger.trace(log, method,"formatFechaFinal: " +formatFechaFinal);
    		if( !formatFechaInicio.equals(ApplicationConstant.DATE_INVALID) &&!formatFechaFinal.equals(ApplicationConstant.DATE_INVALID)){
    			UNALMLogger.trace(log, method,"Son fechas distintas");
    			listPeriodo=this.autoridadDAO.getListPeriodoDAO(periodo);
                UNALMLogger.trace(log, method,"listPeriodo: "+listPeriodo.size());
                

    		}
    	}
        UNALMLogger.trace(log, method,"fuera  de  listPeriodo: "+listPeriodo.size());
		
		
		if(entity.getFlagEstado() !=null){
			UNALMLogger.trace(log, method, "entity.getFlagEstado()= "+"'"+entity.getFlagEstado()+"'");
			String valorObtenido=entity.getFlagEstado();
			if( valorObtenido.equals("Activo")){										 
				entity.setFlagEstado("A");
				UNALMLogger.trace(log, method, "es Activo su valor es  "+"'"+entity.getFlagEstado()+"'");
			}else if(valorObtenido=="Desactivo"){
				entity.setFlagEstado("D");
				UNALMLogger.trace(log, method, "es Desactivo su valor es  "+"'"+entity.getFlagEstado()+"'");

			}
		}

		List<SCGAutoridadRegistroEntity> listAutoridad=this.autoridadDAO.getListAutoridadDAO(entity);	
		if(listPeriodo.size() !=0){
			for( SCGAutoridadRegistroEntity autoridad :listAutoridad ){
				for(SCGPeriodoEntity perirod:listPeriodo){
					if(autoridad.getCodigo()==perirod.getAutoridadRegistro().getCodigo()){
						result.add(autoridad);
					}
				}
			}
		}else{
			result=listAutoridad;
		}
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGPeriodoEntity> getListPeriodo(SCGPeriodoEntity entity) {
		final String method = "getListPeriodo";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGPeriodoEntity> result=this.autoridadDAO.getListPeriodoDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGGradoAcademicoEntity> getListGradoAcademico(SCGGradoAcademicoEntity entity) {
		final String method = "getListGradoAcademico";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGGradoAcademicoEntity> result=this.autoridadDAO.getListGradoAcademicoDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGCargoEntity> getListCargo(SCGCargoEntity entity) {
		final String method = "getListCargo";
		final String params = "SCGCargoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGCargoEntity> result=this.autoridadDAO.getListCargoDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public SCGAutoridadRegistroEntity getForUpdateAutoridad(SCGAutoridadRegistroEntity entity) {
		final String method = "getForUpdateAutoridad";
		final String params = "SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAutoridadRegistroEntity result=this.autoridadDAO.getForUpdateAutoridadDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPeriodoEntity getForUpdatePeriodo(SCGPeriodoEntity entity) {
		final String method = "getForUpdatePeriodo";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPeriodoEntity result=this.autoridadDAO.getForUpdatePeriodoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGAutoridadRegistroEntity saveAutoridad(SCGAutoridadRegistroEntity entity) {
		final String method="saveAutoridad";
		final String params="SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGAutoridadRegistroEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			entityForSave=autoridadDAO.getForUpdateAutoridadDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			
			entityForSave.setTextoNombreAutoridad(entity.getTextoNombreAutoridad());
			entityForSave.setGradoAcademico(entity.getGradoAcademico());
			entityForSave.setCargo(entity.getCargo());
			entityForSave.setFlagEstado(entity.getFlagEstado());
			entityForSave.setFechaEdicion(new Date());

		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		
		SCGAutoridadRegistroEntity result=this.autoridadDAO.saveAutoridadDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPeriodoEntity savePeriodo(SCGPeriodoEntity entity) {
		final String method="savePeriodo";
		final String params="SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGPeriodoEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			entityForSave=autoridadDAO.getForUpdatePeriodoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			
			entityForSave.setFechaInicio(entity.getFechaInicio());
			entityForSave.setFechaFinal(entity.getFechaFinal());
			entityForSave.setAutoridadRegistro(entity.getAutoridadRegistro());
			entityForSave.setTextoCodigoPeriodo(entity.getTextoCodigoPeriodo());
		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		SCGPeriodoEntity result=this.autoridadDAO.savePeriodoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity) {
		final String method="saveAdjunto";
		final String params="SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity entityForSave=null;
		UNALMLogger.trace(log, method, "(entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo() != null){
			entityForSave = autoridadDAO.getForUpdateAdjuntoDAO(entity);
			this.deleteFiles(entityForSave);
			entityForSave.setTextoNombreArchivo(entity.getTextoNombreArchivo());
			entityForSave.setTextoRuta(entity.getTextoRuta());;						
			this.syncFiles(entity);
		}else{
			this.syncFiles(entity);
			entity.setFileArchivo(null);
			entityForSave=entity;
		}
		SCGAdjuntoEntity result=this.autoridadDAO.saveAdjuntoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}


	@Override
	public void delete(SCGAutoridadRegistroEntity entity) {
		final String method = "delete";
		final String params = "SCGAutoridadRegistroEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGAutoridadRegistroEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
//		String result = "";
		
		
//		List<SCGEstudianteRegistroEntity> listEstudianteRegistro = new ArrayList<SCGEstudianteRegistroEntity>();
//		if( listEstudianteRegistro.size() == 0 ){
//			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
//			estudianteRegistro.setAutoridadRegistroDecano(entity);
//			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);	
//		}
//		if( listEstudianteRegistro.size() == 0 ){
//			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
//			estudianteRegistro.setAutoridadRegistroRector(entity);
//			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);	
//		}
//		if( listEstudianteRegistro.size() == 0 ){
//			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
//			estudianteRegistro.setAutoridadRegistroDirectorPostgrado(entity);
//			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);
//		}
//		if( listEstudianteRegistro.size() == 0 ){
//			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
//			estudianteRegistro.setAutoridadRegistroSecretarioGeneral(entity);
//			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);
//		}
//		UNALMLogger.trace(log, method, "listEstudianteRegistro.size() "+listEstudianteRegistro.size() );
//		if( listEstudianteRegistro.size() > 0 ){
//			result = "No se puede eliminar debido a que está asociado a registros originales existente ";
//		}else{
			if(entity.getCodigo()!=null){
				entityForDelete=autoridadDAO.getForUpdateAutoridadDAO(entity);
				UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
				entityForDelete.setFlagEliminado("0");
				
				SCGPeriodoEntity autoridadCodigo = new SCGPeriodoEntity();
				autoridadCodigo.setAutoridadRegistro(entity);
				List<SCGPeriodoEntity> periodoList = this.autoridadDAO.getListPeriodoDAO(autoridadCodigo);
				UNALMLogger.trace(log, method, "periodoList: "+periodoList.size());
				if(periodoList !=null){
					for (SCGPeriodoEntity item : periodoList) {
						item.setFlagEliminado("0");
						this.autoridadDAO.savePeriodoDAO(item);
					}
				}
				
			}else{
				entityForDelete=entity;
				UNALMLogger.trace(log, method, "entityForSave: "+entityForDelete);
			}
			this.autoridadDAO.saveAutoridadDAO(entityForDelete);
//		}
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		UNALMLogger.exit(log, method);
//		return result;
	}
	@Override
	public String validateDelete(SCGAutoridadRegistroEntity entity) {
		final String method = "validateDelete";
		final String params = "SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGAutoridadRegistroEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		String result = "";

		List<SCGEstudianteRegistroEntity> listEstudianteRegistro = new ArrayList<SCGEstudianteRegistroEntity>();
		List<SCGRevalidaEntity> listRevalida = new ArrayList<SCGRevalidaEntity>();
		if( listEstudianteRegistro.size() == 0 ){
			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
			estudianteRegistro.setAutoridadRegistroDecano(entity);
			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);	
		}
		if( listEstudianteRegistro.size() == 0 ){
			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
			estudianteRegistro.setAutoridadRegistroRector(entity);
			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);	
		}
		if( listEstudianteRegistro.size() == 0 ){
			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
			estudianteRegistro.setAutoridadRegistroDirectorPostgrado(entity);
			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);
		}
		if( listEstudianteRegistro.size() == 0 ){
			SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
			estudianteRegistro.setAutoridadRegistroSecretarioGeneral(entity);
			listEstudianteRegistro = this.gradoTituloDAO.listDependentRecordsDAO(estudianteRegistro);
		}
		
		if( listRevalida.size() == 0 ){
			SCGRevalidaEntity revalida = new SCGRevalidaEntity();
			revalida.setAutoridadRegistroDecano(entity);
			listRevalida = this.revalidaDAO.listDependentRecordsDAO(revalida);	
		}
		if( listRevalida.size() == 0 ){
			SCGRevalidaEntity revalida = new SCGRevalidaEntity();
			revalida.setAutoridadRegistroRector(entity);
			listRevalida = this.revalidaDAO.listDependentRecordsDAO(revalida);	
		}
		if( listRevalida.size() == 0 ){
			SCGRevalidaEntity revalida = new SCGRevalidaEntity();
			revalida.setAutoridadRegistroDirectorPostgrado(entity);
			listRevalida = this.revalidaDAO.listDependentRecordsDAO(revalida);	
		}
		if( listRevalida.size() == 0 ){
			SCGRevalidaEntity revalida = new SCGRevalidaEntity();
			revalida.setAutoridadRegistroSecretarioGeneral(entity);
			listRevalida = this.revalidaDAO.listDependentRecordsDAO(revalida);	
		}
		if( listEstudianteRegistro.size() > 0 ){
			result = "No se puede eliminar debido a que está asociado a registros originales existente ";
		}else if(listRevalida.size() > 0 ){
			result = "No se puede eliminar debido a que está asociado a registros de revalida existente ";
		}
		UNALMLogger.exit(log, method);
		return result;
	}
	@Override
	public void deleteAdjunto(SCGPersonaEntity entity) {
		final String method = "deleteAdjunto";
		final String params = "SCGEstudianteEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGAdjuntoEntity entityForDelete=null;
		if(entity.getAdjunto()!=null){
			if(entity.getAdjunto().getCodigo()!=null){
				UNALMLogger.trace(log, method, "entity.getAdjunto().getCodigo(): "+entity.getAdjunto().getCodigo());
				SCGAdjuntoEntity adjunto = new SCGAdjuntoEntity();
				adjunto.setCodigo(entity.getAdjunto().getCodigo());
				entityForDelete = this.autoridadDAO.getForUpdateAdjuntoDAO(adjunto);
				entityForDelete.setFlagEliminado("0");
				this.deleteFiles(entityForDelete);
				this.autoridadDAO.saveAdjuntoDAO(entityForDelete);
			}
				
		}
		UNALMLogger.exit(log, method);
	}
	@Override
	public void deletePeriodo(SCGPeriodoEntity entity) {
		final String method = "deletePeriodo";
		final String params = "SCGPeriodoEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGPeriodoEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getCodigo()!=null){
			entityForDelete=autoridadDAO.getForUpdatePeriodoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForDelete);
		}
		
		SCGPeriodoEntity result=this.autoridadDAO.savePeriodoDAO(entityForDelete);
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void deleteFiles(SCGAdjuntoEntity entity) {
		final String method="deleteFiles";
		UNALMLogger.entry(log, method);
		try {
			fileManagerComponent.delete(entity.getTextoRuta());
		} catch (Exception e) {
			UNALMLogger.error(log, method, "No se pudo sincronizar el archivo", e);
		}
				
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void syncFiles(SCGAdjuntoEntity entity) {
		final String method="syncFiles";
		UNALMLogger.entry(log, method);
		try {
			fileManagerComponent.write(entity.getFileArchivo(), entity.getTextoRuta());
		} catch (Exception e) {
			UNALMLogger.error(log, method, "No se pudo eliminar el archivo", e);
		}
				
		UNALMLogger.exit(log, method);
	}

	

	

}

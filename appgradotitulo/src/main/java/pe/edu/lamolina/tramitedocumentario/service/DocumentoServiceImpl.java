package pe.edu.lamolina.tramitedocumentario.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.filemanager.component.FileManagerComponent;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.tramitedocumentario.dao.DocumentoDAO;
import pe.edu.lamolina.tramitedocumentario.entity.SCGArchivoTramiteDocumento;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentoServiceImpl implements DocumentoService{
	private final static Logger log = Logger.getLogger(DocumentoServiceImpl.class);
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Autowired
	private FileManagerComponent fileManagerComponent;
	
	@Override
	public List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumento(SCGArchivoTramiteDocumento entity,
			FilterUtil filterUtil) {
		final String method = "listArchivoTramiteDocumento";
		UNALMLogger.entry(log, method);
		
		if(entity.getFechaAgregarFinal() != null && entity.getFechaAgregarInicio() != null){
			String fechaInicio = TypesUtil.getDefaultString(entity.getFechaAgregarInicio(), "yyy-MM-dd");
			String fechaFinal = TypesUtil.getDefaultString(entity.getFechaAgregarFinal(), "yyy-MM-dd");
			UNALMLogger.trace(log, method,"fechaAgregarFinal: "+ fechaInicio);
			UNALMLogger.trace(log, method,"fechaAgregarInicio: "+ fechaFinal);

			if(fechaInicio.equals(ApplicationConstant.DATE_INVALID) && fechaFinal.equals(ApplicationConstant.DATE_INVALID)){
				entity.setFechaAgregarFinal(null);
				entity.setFechaAgregarInicio(null);
			}
		}
		
		List<SCGArchivoTramiteDocumento> result = this.documentoDAO.listArchivoTramiteDocumentoDAO(entity,filterUtil);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public SCGArchivoTramiteDocumento getForUpdateArchivoTramiteDocumento(SCGArchivoTramiteDocumento entity) {
		final String method = "getForUpdateArchivoTramiteDocumento";
		final String params = "SCGArchivoTramiteDocumento entity";
		
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGArchivoTramiteDocumento result = this.documentoDAO.getForUpdateArchivoTramiteDocumentoDAO(entity);
		
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGArchivoTramiteDocumento save(SCGArchivoTramiteDocumento entity) {
		final String method="save";
		final String params="SCGArchivoTramiteDocumento entity";
		
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGArchivoTramiteDocumento entityForSave = null;
		
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		
		if(entity.getCodigo() != null){	
			entityForSave = this.documentoDAO.getForUpdateArchivoTramiteDocumentoDAO(entity);
			entityForSave.setTextoNombreRegistro(entity.getTextoNombreRegistro());
			entityForSave.setTextoAsunto(entity.getTextoAsunto());
			entityForSave.setTextoDetalle(entity.getTextoDetalle());
			
			UNALMLogger.trace(log, method, "entity.getArchivo(): "+entity.getArchivo());

			if(entity.getArchivo() != null){
				String fileName = TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename());
				UNALMLogger.trace(log, method, "fileName: "+fileName);
				
				entity.setTextoRuta("fotostitulos/"+ fileName );
				
				if( !TypesUtil.isEmptyString(fileName)){
					
					UNALMLogger.trace(log, method, "entityForSave.getTextoRuta(): "+entityForSave.getTextoRuta());
					UNALMLogger.trace(log, method, "entityForSave.getTextoNombreArchivo: "+entityForSave.getTextoNombreArchivo());

					this.deleteFiles(entityForSave);
					
					entityForSave.setTextoRuta(entity.getTextoRuta());
					entityForSave.setTextoNombreArchivo(fileName);
					entityForSave.setTextoRuta("fotostitulos/"+ fileName );
					
					
					UNALMLogger.trace(log, method, "entity.getArchivo().getOriginalFilename(): "+TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename()));
					UNALMLogger.trace(log, method, "entityForSave.getTextoRuta: "+entity.getTextoRuta());
					UNALMLogger.trace(log, method, "entityForSave.getTextoNombreArchivo: "+entity.getTextoNombreArchivo());
					this.syncFiles(entity);
				}
			}
					
		}else{
			String fileName = TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename());
			UNALMLogger.trace(log, method, "fileName: "+fileName);

			entity.setFlagEliminado("0");
			entity.setFlagCandado("0");
			entity.setFlagActivo("1");
			entity.setFechaAgregar(new Date());
			
			if( !TypesUtil.isEmptyString(fileName)){
				entity.setTextoNombreArchivo( TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename()) );
				entity.setTextoRuta(
						"fotostitulos/"+ TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename()) );
			}
			
			this.syncFiles(entity);
//			entity.setFileArchivo(null);
			entityForSave = entity;
		}
		
		SCGArchivoTramiteDocumento result = this.documentoDAO.saveDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	private void deleteFiles(SCGArchivoTramiteDocumento entity) {
		final String method="deleteFiles";
		UNALMLogger.entry(log, method);
		
		try {
			fileManagerComponent.delete(entity.getTextoRuta());
		
		} catch (Exception e) {
			UNALMLogger.error(log, method, "No se pudo sincronizar el archivo", e);
		}
				
		UNALMLogger.exit(log, method);
		
	}
	
	private void syncFiles(SCGArchivoTramiteDocumento entity) {
		final String method = "syncFiles";
		UNALMLogger.entry(log, method);
		
		try {
			fileManagerComponent.write(entity.getArchivo().getBytes(), entity.getTextoRuta());
			
		} catch (Exception e) {
			UNALMLogger.error(log, method, "No se pudo eliminar el archivo", e);
		}
				
		UNALMLogger.exit(log, method);
		
	}

	@Override
	public void delete(SCGArchivoTramiteDocumento entity) {
		final String method = "delete";
		final String params = "SCGArchivoTramiteDocumento entity";
		SCGArchivoTramiteDocumento entityForDelete=null;

		UNALMLogger.entry(log, method, params, new Object[] {entity});		
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());

		if(entity.getCodigo() != null){
			entityForDelete = this.documentoDAO.getForUpdateArchivoTramiteDocumentoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			
			this.deleteFiles(entityForDelete);
			entityForDelete.setTextoRuta("");
			entityForDelete.setTextoNombreArchivo("");
			entityForDelete.setFlagEliminado("1");
			
			
		}else{
			entityForDelete = entity;
		}
		
		SCGArchivoTramiteDocumento result = this.documentoDAO.saveDAO(entityForDelete);
		UNALMLogger.exit(log, method,result);
		
	}
	
	@Override
	public String validate(SCGArchivoTramiteDocumento entity) {
		final String method = "validate";
		final String params = "SCGSolicitudProcesoEntity entity";
		
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		String result = "";
		
		if(entity != null){
			
			if(entity.getCodigo() != null){
				result = this.validateEdit(entity);

			}else{
				result = this.validateDuplicate(entity);
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}


	private String validateDuplicate(SCGArchivoTramiteDocumento entity) {
		final String method = "validateDuplicate";
		final String params = "SCGSolicitudProcesoEntity entity";
		
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		String result = "";
		
		if(entity != null){
			SCGArchivoTramiteDocumento dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
			dataByFilterArchivoTramiteDocumento.setTextoNombreRegistroNoMatch(entity.getTextoNombreRegistro());
			
			List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());

			if(listArchivoTramiteDocumento.size() > 0 ){
				result = "El Nombre del Archivo ya Existe";
			}else{
				String filename = TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename());
				
				if( !TypesUtil.isEmptyString(filename) ){
					dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
					dataByFilterArchivoTramiteDocumento.setTextoNombreArchivo( filename );
//					dataByFilterArchivoTramiteDocumento.setTextoRuta(entity.getTextoRuta());
					
					listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());
					
					if(listArchivoTramiteDocumento.size() > 0 ){
						result = "El Nombre del Archivo que esta adjuntado ya existe";
					}
				}
			
			}
			
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
//	@Override
//	public String validateDuplsicate(SCGArchivoTramiteDocumento entity) {
//		final String method = "validateDuplicate";
//		final String params = "SCGSolicitudProcesoEntity entity";
//		
//		UNALMLogger.entry(log, method, params, new Object[] { entity });
//		String result = "";
//		
//		if(entity != null){
//			SCGArchivoTramiteDocumento dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
//			dataByFilterArchivoTramiteDocumento.setTextoNombreRegistroNoMatch(entity.getTextoNombreRegistro());
//			
//			List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());
//			
//			if(listArchivoTramiteDocumento.size() > 0 ){
//				result = "El Nombre del Archivo ya Existe";
//			}else{
//				dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
//				dataByFilterArchivoTramiteDocumento.setTextoNombreArchivo( TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename()) );
////				dataByFilterArchivoTramiteDocumento.setTextoRuta(entity.getTextoRuta());
//				
//				listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());
//				
//				if(listArchivoTramiteDocumento.size() > 0 ){
//					result = "El Nombre del Archivo que esta adjuntado ya existe";
//				}
//			}
//			
//		}
//		
//		UNALMLogger.exit(log, method, result);
//		return result;
//	}

	
	private String validateEdit(SCGArchivoTramiteDocumento entity) {
		final String method = "validateEdit";
		final String params = "SCGSolicitudProcesoEntity entity";
		String orignalTextoNombreRegistro = "";
		String textoNombreRegistro = "";
		String orignalTextoNombreArchivo = "";
		String textoNombreArchivo = "";
		String result = "";
		
		UNALMLogger.entry(log, method, params, new Object[] { entity });

		SCGArchivoTramiteDocumento dataByFilterArchivoTramiteDocumento  = new SCGArchivoTramiteDocumento();
		dataByFilterArchivoTramiteDocumento.setCodigo(entity.getCodigo());
		SCGArchivoTramiteDocumento originalArchivoTramiteDocumento = this.getForUpdateArchivoTramiteDocumento(dataByFilterArchivoTramiteDocumento);
		
		orignalTextoNombreRegistro = originalArchivoTramiteDocumento.getTextoNombreRegistro();
		textoNombreRegistro = entity.getTextoNombreRegistro();
		
		orignalTextoNombreArchivo = originalArchivoTramiteDocumento.getTextoNombreArchivo();
		textoNombreArchivo = TypesUtil.setFormatUTF8(entity.getArchivo().getOriginalFilename());

		UNALMLogger.trace(log, method, "orignalTextoNombreRegistro "+ orignalTextoNombreRegistro);
		UNALMLogger.trace(log, method, "textoNombreRegistro "+ textoNombreRegistro);
		UNALMLogger.trace(log, method, "orignalTextoNombreArchivo "+ orignalTextoNombreArchivo);
		UNALMLogger.trace(log, method, "textoNombreArchivo "+ textoNombreArchivo);
		
		List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumento  =  new ArrayList<SCGArchivoTramiteDocumento>();
		
		if(orignalTextoNombreRegistro.equals(textoNombreRegistro)){
			if(!TypesUtil.isEmptyString(textoNombreArchivo)){
				if(!orignalTextoNombreArchivo.equals(textoNombreArchivo)){
					dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
					dataByFilterArchivoTramiteDocumento.setTextoNombreArchivo(textoNombreArchivo);
					
					listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());
					
					if(listArchivoTramiteDocumento.size() > 0 ){
						result = "El Nombre del Archivo que esta adjuntado ya existe";
					}
				}
			}
			
		}else{
			dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
			dataByFilterArchivoTramiteDocumento.setTextoNombreRegistroNoMatch(entity.getTextoNombreRegistro());
			listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());

			if(listArchivoTramiteDocumento.size() > 0 ){
				result = "El Nombre del Archivo ya Existe";
				
			}else if( !TypesUtil.isEmptyString(textoNombreArchivo) ){
				
				if(!orignalTextoNombreArchivo.equals(textoNombreArchivo)){
					dataByFilterArchivoTramiteDocumento = new SCGArchivoTramiteDocumento();
					dataByFilterArchivoTramiteDocumento.setTextoNombreArchivo(textoNombreArchivo);
					
					listArchivoTramiteDocumento = new ArrayList<SCGArchivoTramiteDocumento>();
					listArchivoTramiteDocumento = this.documentoDAO.listArchivoTramiteDocumentoDAO(dataByFilterArchivoTramiteDocumento, new FilterUtil());
					
					if(listArchivoTramiteDocumento.size() > 0 ){
						result = "El Nombre del Archivo que esta adjuntado ya existe";
					}
				}
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}


//	@Override
//	public String validateDelete(SCGArchivoTramiteDocumento solicitudProceso) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}

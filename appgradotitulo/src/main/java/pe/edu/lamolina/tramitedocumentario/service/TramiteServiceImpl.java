package pe.edu.lamolina.tramitedocumentario.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.security.dao.AutenticacionDAO;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;
import pe.edu.lamolina.tramitedocumentario.dao.RegistroDAO;
import pe.edu.lamolina.tramitedocumentario.dao.TramiteDAO;
import pe.edu.lamolina.tramitedocumentario.entity.SCGArchivoTramiteDocumento;
import pe.edu.lamolina.tramitedocumentario.entity.SCGAtributosEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGValoresEntity;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Service
@Transactional( rollbackFor = Exception.class)
public class TramiteServiceImpl implements TramiteService{
	private final static Logger log = Logger.getLogger( RegistroServiceImpl.class );
	
	@Autowired
	TramiteDAO tramiteDAO;
	
	@Autowired
	RegistroDAO registroDao;
	
	@Autowired
	private AutenticacionDAO autenticacionDAO;
	
	@Override
	public List<SCGFlujoProcesoEntity> listFlujoProceso(SCGFlujoProcesoEntity entity, FilterUtil filterUtil) {
		final String method = "listFlujoProceso";
		UNALMLogger.entry(log, method);
		
		
		SeguridadUsuarioEntity usuario = entity.getSeguridadUsuario();
		SeguridadUsuarioEntity usuarioLogin = this.autenticacionDAO.validarUsuarioDAO(usuario);

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
		
		UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+ entity.getFlagCandado());
		if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
			entity.setSeguridadUsuario(null);
			
    		if(entity.getFlagCandado().equals("2")){
    			entity.setFlagCandado(null);
    		}
    	}else{
    		entity.setFlagCandado("0");
    	}
		
		entity.setTextoTipoOrden("ASC");
		
		UNALMLogger.trace(log, method,"after fechaAgregarFinal: "+ entity.getFechaAgregarFinal());
		UNALMLogger.trace(log, method,"after fechaAgregarInicio: "+ entity.getFechaAgregarInicio());
		
		List<SCGFlujoProcesoEntity> result = new ArrayList<SCGFlujoProcesoEntity>();
		List<SCGFlujoProcesoEntity> list = this.tramiteDAO.listFlujoProcesoDAO(entity,filterUtil);
		
		for(SCGFlujoProcesoEntity item : list){
			
			if(item.getSolicitudProceso() !=null){
				item.setTextoTipoColor( this.getTipoColor(item.getSolicitudProceso()) );
			}
			
			item.setFlagUsuaroValido(this.isSameUsurio(item, usuarioLogin));
			item.setTextoRegistrosAdjuntos(this.getTextoRegistrosAdjuntos(item));
			result.add(item);
		}
		
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	private String getTipoColor(SCGSolicitudProcesoEntity solicitudProceso){
		final String method = "getTipoColor";
		UNALMLogger.entry(log, method);
		String result = "N";
		
		UNALMLogger.trace(log, method,"solicitudProceso.getFlagCandado(): "+ solicitudProceso.getFlagCandado());
		if( solicitudProceso.getFlagCandado().equals("0")){
			
			if(solicitudProceso.getFechaInicio() != null && solicitudProceso.getFechaFinal() != null){
				int horasTotal = TypesUtil.getHourBetweenDate(solicitudProceso.getFechaInicio(), solicitudProceso.getFechaFinal());
				int horasTranscurridas = TypesUtil.getHourBetweenDate( solicitudProceso.getFechaInicio(), new Date());
				
				UNALMLogger.trace(log, method, "horasTotal "+horasTotal);
				UNALMLogger.trace(log, method, "horasTranscurridas "+horasTranscurridas);
				
				if(horasTotal > 0 && horasTranscurridas > 0){
					int nivelUno = (horasTotal/3);
					UNALMLogger.trace(log, method, "nivelUno "+nivelUno);
					int nivelDos = (horasTotal/3)*2;
					UNALMLogger.trace(log, method, "nivelDos "+nivelDos);

					if(horasTranscurridas <= nivelUno){
						result = "V";
						UNALMLogger.trace(log, method, "verde ");
					
					}else if(nivelUno <= horasTranscurridas && horasTranscurridas <= nivelDos){
						result = "A";
						UNALMLogger.trace(log, method, "amarillo ");
					
					}else if(horasTranscurridas > nivelDos){
						result = "R";
						UNALMLogger.trace(log, method, "rojo ");
					}
					
				}

			}
		}

		return result;
	}
	
	private String getTextoRegistrosAdjuntos(SCGFlujoProcesoEntity solicitudProceso){
		final String method = "getTextoRegistrosAdjuntos";
		UNALMLogger.entry(log, method);
		String result = "";
		int count = 0;
		UNALMLogger.trace(log, method, "solicitudProceso.getDefinicionProceso(): "+solicitudProceso.getDefinicionProceso());
		if(solicitudProceso.getDefinicionProceso() != null){
			SCGAtributosEntity dataByFilterAtribustos = new SCGAtributosEntity();
			dataByFilterAtribustos.setDefinicionProceso(new SCGDefinicionProcesoEntity());
			dataByFilterAtribustos.getDefinicionProceso().setCodigo(solicitudProceso.getDefinicionProceso().getCodigo());		
			
			List<SCGAtributosEntity> listAtributos = this.tramiteDAO.listAtributosDAO(dataByFilterAtribustos);
			UNALMLogger.trace(log, method, "listAtributos: "+listAtributos);
			
			
			for(SCGAtributosEntity atributo : listAtributos){
				
				
				SCGValoresEntity dataByFilerValores = new SCGValoresEntity();
				dataByFilerValores.setAtributos(atributo);
				dataByFilerValores.setSolicitudProceso(solicitudProceso.getSolicitudProceso());
				SCGValoresEntity valor = this.tramiteDAO.getForUpdateValoresDAO(dataByFilerValores);
				UNALMLogger.trace(log, method, "valor: "+valor);

				if(valor != null){
					
					UNALMLogger.trace(log, method, "valor.getArchivoTramiteDocumento(): "+valor.getArchivoTramiteDocumento());
					if(valor.getArchivoTramiteDocumento() != null){
						
						SCGArchivoTramiteDocumento archivo =  valor.getArchivoTramiteDocumento();
						
						if(!TypesUtil.isEmptyString(archivo.getTextoNombreRegistro())){
							count++;
							
							if(count > 1){
								result += ", ";
							}
							
							result += archivo.getTextoNombreRegistro();
						}
					}
				}
				
				
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	private String isSameUsurio(SCGFlujoProcesoEntity flujoProceso, SeguridadUsuarioEntity usuarioLogin){
		final String method = "isSameUsurio";
		UNALMLogger.entry(log, method);
		String result = "0";
		
		UNALMLogger.trace(log, method, "flujoProceso.getSeguridadUsuario(): "+flujoProceso.getSeguridadUsuario());
		
		if(flujoProceso.getSeguridadUsuario() != null){
			UNALMLogger.trace(log, method, "flujoProceso.getSeguridadUsuario().getCodigo(): "+flujoProceso.getSeguridadUsuario().getCodigo());
			UNALMLogger.trace(log, method, "usuarioLogin.getCodigo(): "+usuarioLogin.getCodigo());
			
			if(flujoProceso.getSeguridadUsuario().getCodigo() == usuarioLogin.getCodigo()){
				result = "1";
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public List<SCGDecisionProcesoEntity> listDecisionProceso(SCGDecisionProcesoEntity entity) {
		final String method = "listDecisionProceso";
		final String params = "SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGDecisionProcesoEntity> result = this.tramiteDAO.listDecisionProcesoDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGResponsabilidadEntity> listResponsabilidad(SCGResponsabilidadEntity entity) {
		final String method = "listResponsabilidad";
		final String params = "SCGResponsabilidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGResponsabilidadEntity> result = this.tramiteDAO.listResponsabilidadDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGAtributosEntity> listAtributos(SCGAtributosEntity entity) {
		final String method = "listAtributos";
		final String params = "SCGAtributosEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGAtributosEntity> result = this.tramiteDAO.listAtributosDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public void saveFlujoProceso(SCGFlujoProcesoEntity entity,HttpServletRequest request) {
		final String method = "saveFlujoProceso";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ entity });
		UNALMLogger.trace(log, method, entity.getFlagCandado());
		
		if(entity.getCodigo() != null){
			SCGFlujoProcesoEntity entityForSave = null;
			SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
			dataByFilterFlujoProceso.setCodigo(entity.getCodigo());
			UNALMLogger.trace(log, method, "entity.getCodigo()");
			entityForSave = this.tramiteDAO.getForUpdateFlujoProcesoDAO( dataByFilterFlujoProceso );
			UNALMLogger.trace(log, method, "entityForSave");
			
			if(entity.getFlagCandado().equals("1")){
				entityForSave.setTextoDetalle(entity.getTextoDetalle());
				entityForSave.setFlagCandado(entity.getFlagCandado());
				
				this.tramiteDAO.saveFlujoProcesoDAO( entityForSave );
				
				this.saveValores( entityForSave, request );

			}else if(entity.getFlagCandado().equals("0")){
				entityForSave.setTextoDetalle(entity.getTextoDetalle());
				entityForSave.setFlagCandado("1");
				entityForSave.setFechaFinal(new Date());
				
				this.tramiteDAO.saveFlujoProcesoDAO( entityForSave );
				
				this.saveValores( entityForSave, request );
				
				SCGDefinicionProcesoEntity dataByFilterDefinicionProceso = new SCGDefinicionProcesoEntity();
				dataByFilterDefinicionProceso.setCodigo(entity.getDefinicionProceso().getCodigo());
				SCGDefinicionProcesoEntity definicionProceso = this.registroDao.getForUpdateDefinicionProcesoDAO(dataByFilterDefinicionProceso);
				UNALMLogger.trace(log, method, "definicionProceso "+definicionProceso);
				
				if(definicionProceso.getTextoNombre().equals("PASO ANTERIOR")){
					this.savePasoAnterior(entityForSave);
				}else{
					UNALMLogger.trace(log, method, "agregar");
					entity.setSolicitudProceso(entityForSave.getSolicitudProceso());;
					entity.setCodigo(null);
					entity.setTextoDetalle(null);
					entity.setFlagEliminado("0");
					entity.setFlagActivo("1");
					entity.setFechaAgregar(new Date());
					entity.setFechaInicio(new Date());
					entity.setFlagCandado(this.getFlagCandado(entity));
					
					this.tramiteDAO.saveFlujoProcesoDAO( entity );
				}
				
			
				
				

			}
		}
		UNALMLogger.exit(log, method);
	}
	
	private void saveValores(SCGFlujoProcesoEntity flujoProceso, HttpServletRequest request) {
		final String method = "saveValores";
		final String params = "SCGValoresEntity entity, HttpServletRequest request";
		UNALMLogger.entry(log, method, params, new Object[]{ flujoProceso,request });
		
//		UNALMLogger.trace(log, method, "entity.getCodigo() "+ entity.getCodigo());
		
//
//		SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
//		dataByFilterFlujoProceso.setCodigo(entity.getCodigo());
//		UNALMLogger.trace(log, method, "entity.getCodigo()");
//		SCGFlujoProcesoEntity flujoProceso = this.tramiteDAO.getForUpdateFlujoProcesoDAO( dataByFilterFlujoProceso );
//		UNALMLogger.trace(log, method, "flujoProceso");
//		
		List<SCGValoresEntity> listValores = new ArrayList<SCGValoresEntity>();
		
		if(flujoProceso != null){
			
			if(flujoProceso.getDefinicionProceso() != null){
				SCGAtributosEntity dataByFilterAtributos = new SCGAtributosEntity();
				dataByFilterAtributos.setDefinicionProceso(flujoProceso.getDefinicionProceso());
				List<SCGAtributosEntity> listAtributos = this.tramiteDAO.listAtributosDAO(dataByFilterAtributos);
				
				if(listAtributos.size()>0){
					for(SCGAtributosEntity item : listAtributos ){
						
						UNALMLogger.trace(log, method, item.getCodigo()+".codigo");
						String valueField = request.getParameter(item.getCodigo()+".codigo");
						UNALMLogger.trace(log, method, "valueField1: " + valueField);
						
						if(!TypesUtil.isEmptyString(valueField)){
							Long codigoArchivoTramiteDocumento = Long.parseLong(valueField, 10);

							SCGValoresEntity valor = new SCGValoresEntity();
							valor.setArchivoTramiteDocumento(new SCGArchivoTramiteDocumento());
							valor.getArchivoTramiteDocumento().setCodigo(codigoArchivoTramiteDocumento);
							valor.setAtributos(item);
							UNALMLogger.trace(log, method, "flujoProceso.getSolicitudProceso(): " + flujoProceso.getSolicitudProceso());
							UNALMLogger.trace(log, method, "flujoProceso.getSolicitudProceso().getCodigo(): " + flujoProceso.getSolicitudProceso().getCodigo());
							valor.setSolicitudProceso(flujoProceso.getSolicitudProceso());
							
							listValores.add(valor);
							
						}
						
						
						
					}
				}
				
			}
		}
		
		if(listValores.size() > 0){
			
			for(SCGValoresEntity item : listValores){					
				
				SCGValoresEntity dataByFilterValor = new SCGValoresEntity();
				dataByFilterValor.setAtributos(item.getAtributos());
				dataByFilterValor.setSolicitudProceso(item.getSolicitudProceso());
				
				SCGValoresEntity valor = this.tramiteDAO .getForUpdateValoresDAO(dataByFilterValor);
				UNALMLogger.trace(log, method, "valor: " + valor);
				
				if(valor != null){
					
					if(valor.getCodigo() != null){
						valor.setArchivoTramiteDocumento(item.getArchivoTramiteDocumento());
					}
					
				}else{
					valor = item;
					valor.setFlagActivo("1");
					valor.setFlagEliminado("0");
				}
				
				this.tramiteDAO.saveValoresDAO(valor);

				
			}
		}

		
		UNALMLogger.exit(log, method);
	}
//	@Override
//	public void saveValores(SCGFlujoProcesoEntity entity, HttpServletRequest request) {
//		final String method = "saveValores";
//		final String params = "SCGValoresEntity entity";
//		UNALMLogger.entry(log, method, params, new Object[]{ entity });
//		
//		UNALMLogger.trace(log, method, "entity.getCodigo() "+ entity.getCodigo());
//		if( entity.getCodigo() != null ){
//			
//			SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
//			dataByFilterFlujoProceso.setCodigo(entity.getCodigo());
//			UNALMLogger.trace(log, method, "entity.getCodigo()");
//			SCGFlujoProcesoEntity flujoProceso = this.tramiteDAO.getForUpdateFlujoProcesoDAO( dataByFilterFlujoProceso );
//			UNALMLogger.trace(log, method, "flujoProceso");
//			
//			List<SCGValoresEntity> listValores = new ArrayList<SCGValoresEntity>();
//			
//			if(flujoProceso != null){
//				
//				if(flujoProceso.getDefinicionProceso() != null){
//					SCGAtributosEntity dataByFilterAtributos = new SCGAtributosEntity();
//					dataByFilterAtributos.setDefinicionProceso(flujoProceso.getDefinicionProceso());
//					List<SCGAtributosEntity> listAtributos = this.tramiteDAO.listAtributosDAO(dataByFilterAtributos);
//					
//					if(listAtributos.size()>0){
//						for(SCGAtributosEntity item : listAtributos ){
//							
//							UNALMLogger.trace(log, method, item.getCodigo()+".codigo");
//							String valueField = request.getParameter(item.getCodigo()+".codigo");
//							UNALMLogger.trace(log, method, "valueField" + valueField);
//							
//							if(!TypesUtil.isEmptyString(valueField)){
//								Long codigoArchivoTramiteDocumento = Long.parseLong(valueField, 10);
//								
//								SCGValoresEntity valor = new SCGValoresEntity();
//								valor.setArchivoTramiteDocumento(new SCGArchivoTramiteDocumento());
//								valor.getArchivoTramiteDocumento().setCodigo(codigoArchivoTramiteDocumento);
//								valor.setAtributos(item);
//								listValores.add(valor);
//								
//							}
//							
//							
//							
//						}
//					}
//					
//				}
//			}
//			
//			if(listValores.size() > 0){
//				
//				for(SCGValoresEntity item : listValores){					
//					
//					SCGValoresEntity valor = new SCGValoresEntity();
//					valor = this.tramiteDAO.getForUpdateValoresDAO(item);
//					
//					UNALMLogger.trace(log, method, "valor.getCodigo() " + valor.getCodigo() );
//					if(valor.getCodigo() != null){
//						valor.setArchivoTramiteDocumento(item.getArchivoTramiteDocumento());
//						
//					}else{
//						valor = item;
//						valor.setFlagActivo("1");
//						valor.setFlagEliminado("0");
//					}
//					
//					this.tramiteDAO.saveValoresDAO(valor);
//					
//					
//				}
//			}
//			
//			
//			
//			
//		}
//		
//		
////		if( entity.getCodigo() != null ){
////			entityForSave = this.tramiteDAO.getForUpdateValoresDAO( entity );
////			
////			entityForSave.setArchivoTramiteDocumento(entity.getArchivoTramiteDocumento());
////			entityForSave.setAtributos(entity.getAtributos());
////
////		}else{
////			entity.setFlagEliminado("0");
////			entity.setFlagActivo("1");
////			entityForSave = entity;
////		}
//		
////		SCGValoresEntity result = this.tramiteDAO.saveFlujoValoresDAO(entityForSave);
////		this.tramiteDAO.saveValoresDAO(entityForSave);
//		UNALMLogger.exit(log, method);
//	}
	
	private void savePasoAnterior(SCGFlujoProcesoEntity entity){
		final String method = "savePasoAnterior";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{entity});
		
		SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
		dataByFilterFlujoProceso.setSolicitudProceso(new SCGSolicitudProcesoEntity());
		dataByFilterFlujoProceso.getSolicitudProceso().setCodigo(entity.getSolicitudProceso().getCodigo());
		dataByFilterFlujoProceso.setTextoTipoOrden("DES");
		List<SCGFlujoProcesoEntity> listFlujoProceso = this.tramiteDAO.listFlujoProcesoDAO(dataByFilterFlujoProceso, new FilterUtil());
		UNALMLogger.trace(log, method, "listFlujoProceso.size() "+ listFlujoProceso.size());
		
		UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
		
		SCGFlujoProcesoEntity result = new SCGFlujoProcesoEntity();

		for(SCGFlujoProcesoEntity item : listFlujoProceso){
			
			if(item.getCodigo() < entity.getCodigo()){
				UNALMLogger.trace(log, method, "item.getCodigo()" + item.getCodigo());
				UNALMLogger.trace(log, method, "entity.getCodigo()" + entity.getCodigo());
				
				if(item.getDefinicionProceso().getCodigo() != entity.getDefinicionProceso().getCodigo()){
					UNALMLogger.trace(log, method, "item.getDefinicionProceso().getCodigo() " + item.getDefinicionProceso().getCodigo());
					UNALMLogger.trace(log, method, "entity.getDefinicionProceso().getCodigo() " +  entity.getDefinicionProceso().getCodigo());
					
					result = item;
					break; 
				}
			}
		}
		
		UNALMLogger.trace(log, method, "result "+result);
		if(result != null){
			UNALMLogger.trace(log, method, "result.getCodigo() "+result.getCodigo());
			
//			result.setCodigo(null);
			
			SCGFlujoProcesoEntity pasoAnteriorRegistro = new SCGFlujoProcesoEntity();
			pasoAnteriorRegistro.setResponsable(result.getResponsable());
			pasoAnteriorRegistro.setSeguridadUsuario(result.getSeguridadUsuario());
			pasoAnteriorRegistro.setFechaInicio(new Date());
			pasoAnteriorRegistro.setResponsable(result.getResponsable());
			pasoAnteriorRegistro.setSolicitudProceso(result.getSolicitudProceso());
			pasoAnteriorRegistro.setDefinicionProceso(result.getDefinicionProceso());
			pasoAnteriorRegistro.setFlagActivo("1");
			pasoAnteriorRegistro.setFlagEliminado("0");
			pasoAnteriorRegistro.setFlagCandado("0");
			pasoAnteriorRegistro.setFechaAgregar(new Date());
//			result.setFlagCandado("0");
//			result.setTextoDetalle("");
//			result.setFechaFinal(null);
			
			this.tramiteDAO.saveFlujoProcesoDAO( pasoAnteriorRegistro );
		}
		
		UNALMLogger.exit(log, method, result);
	}
	private String getFlagCandado(SCGFlujoProcesoEntity entity){
		final String method = "getFlagCandado";
		final String params = "SCGFlujoProcesoEntity entity";
		String result = "0";
				
		SCGDefinicionProcesoEntity dataByFilterDefinicionProceso = new SCGDefinicionProcesoEntity();
		dataByFilterDefinicionProceso.setCodigo(entity.getDefinicionProceso().getCodigo());
		SCGDefinicionProcesoEntity definicionProceso = this.registroDao.getForUpdateDefinicionProcesoDAO(dataByFilterDefinicionProceso);
		UNALMLogger.trace(log, method, "definicionProceso "+definicionProceso);
	
		
		
		if(!TypesUtil.isEmptyString(definicionProceso.getFlagFinal())){
			
			if(definicionProceso.getFlagFinal().equals("1")){
				SCGSolicitudProcesoEntity dataByFilterSolocitudProceso = new SCGSolicitudProcesoEntity();
				dataByFilterSolocitudProceso.setCodigo(entity.getSolicitudProceso().getCodigo());
				
				SCGSolicitudProcesoEntity solicitudProceso = this.registroDao.getForUpdateSolicitudProcesoDAO(dataByFilterSolocitudProceso);
				UNALMLogger.trace(log, method, "solicitudProceso "+solicitudProceso);
				
				if(solicitudProceso != null){
					result = "1";
					
					if(definicionProceso.getProceso().getTextoNombre().equals("POR DEFINIR")){						
						solicitudProceso.setFlagCandado("0");
						
						this.registroDao.saveSolicitudProcesoDAO(solicitudProceso);	
						
					}else{
						solicitudProceso.setFlagCandado("1");
						
						this.registroDao.saveSolicitudProcesoDAO(solicitudProceso);
					}

				}
			}
			
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	
//	private void closeSolicitudProceso(SCGFlujoProcesoEntity entity){
//		final String method = "closeSolicitudProceso";
//		final String params = "SCGFlujoProcesoEntity entity";
//		UNALMLogger.entry(log, method, params, new Object[]{ entity });
//		
//		SCGDefinicionProcesoEntity definicionProceso = entity.getDefinicionProceso();
//		if(!TypesUtil.isEmptyString(definicionProceso.getFlagFinal())){
//			
//			if(definicionProceso.getFlagFinal().equals("1")){
//				SCGSolicitudProcesoEntity dataByFilterSolocitudProceso = new SCGSolicitudProcesoEntity();
//				dataByFilterSolocitudProceso.setCodigo(entity.getSolicitudProceso().getCodigo());
//				
//				SCGSolicitudProcesoEntity solicitudProceso = this.registroDao.getForUpdateSolicitudProcesoDAO(dataByFilterSolocitudProceso);
//				UNALMLogger.trace(log, method, "solicitudProceso "+solicitudProceso);
//				
//				if(solicitudProceso != null){
//					solicitudProceso.setFlagCandado("1");
//					this.registroDao.saveSolicitudProcesoDAO(solicitudProceso);
//				}
//			}
//			
//		}
//		
//		UNALMLogger.exit(log, method);
//	}
	
	@Override
	public SCGFlujoProcesoEntity getFlujoProcesoSiguiente(SCGFlujoProcesoEntity entity) {
		final String method = "getFlujoProcesoAnterior";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{entity});
		
		SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
		dataByFilterFlujoProceso.setSolicitudProceso(new SCGSolicitudProcesoEntity());
		dataByFilterFlujoProceso.getSolicitudProceso().setCodigo(entity.getSolicitudProceso().getCodigo());
		dataByFilterFlujoProceso.setTextoTipoOrden("ASC");
		List<SCGFlujoProcesoEntity> listFlujoProceso = this.tramiteDAO.listFlujoProcesoDAO(dataByFilterFlujoProceso, new FilterUtil());
		UNALMLogger.trace(log, method, "listFlujoProceso.size() "+ listFlujoProceso.size());
		
		SCGFlujoProcesoEntity result = new SCGFlujoProcesoEntity();
		UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
		
		for(SCGFlujoProcesoEntity item : listFlujoProceso){
			UNALMLogger.trace(log, method, "item.getCodigo()" + item.getCodigo());
			if(entity.getCodigo() < item.getCodigo()){
				result = item;
				break;
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGFlujoProcesoEntity getForUpdateFlujoProceso(SCGFlujoProcesoEntity entity) {
		final String method = "getForUpdateFlujoProceso";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGFlujoProcesoEntity result = this.tramiteDAO.getForUpdateFlujoProcesoDAO( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGDecisionProcesoEntity getForUpdateDecisionProceso(SCGDecisionProcesoEntity entity) {
		final String method = "getForUpdateDecisionProceso";
		final String params = "SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGDecisionProcesoEntity result = this.tramiteDAO.getForUpdateDecisionProcesoDAO( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGResponsabilidadEntity getForUpdateResponsabilidad(SCGResponsabilidadEntity entity) {
		final String method = "getForUpdateResponsabilidad";
		final String params = "SCGResponsabilidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGResponsabilidadEntity result = this.tramiteDAO.getForUpdateResponsabilidadDAO( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGAtributosEntity getForUpdateAtributo(SCGAtributosEntity entity) {
		final String method = "getForUpdateAtributo";
		final String params = "SCGAtributosEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAtributosEntity result = this.tramiteDAO.getForUpdateAtributoDAO( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGValoresEntity getForUpdateValores(SCGValoresEntity entity) {
		final String method = "getForUpdateValores";
		final String params = "SCGValoresEntity entity";
		
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGValoresEntity result = this.tramiteDAO.getForUpdateValoresDAO( entity );
		UNALMLogger.exit(log, method, result);
		
		return result;
	}
	
	@Override
	public void delete(SCGFlujoProcesoEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desestimado(SCGFlujoProcesoEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String validateDuplicate(SCGFlujoProcesoEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateEdit(SCGFlujoProcesoEntity solicitudProceso, Long Proceso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateDelete(SCGFlujoProcesoEntity solicitudProceso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isProcesoPorDefinir(SCGFlujoProcesoEntity solicitudProceso, Long codigoProceso) {
		// TODO Auto-generated method stub
		return false;
	}

}

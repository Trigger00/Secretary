package pe.edu.lamolina.gradotitulo.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCargoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;
import pe.edu.lamolina.gradotitulo.service.AutoridadService;
import pe.edu.lamolina.gradotitulo.service.FacultadService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("autoridad/*")
@Controller
public class AutoridadController {
	
	private final static Logger log = Logger.getLogger(AutoridadController.class);

	@Autowired
	private AutoridadService autoridadService;
	
	@Autowired
	private FacultadService facultadService;

	@RequestMapping("load")
	public  String load(){
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "autoridad/loadView";
	}
	
	@RequestMapping("getAutoridadList")
	public  void getAutoridadList( HttpServletRequest request,
			HttpServletResponse response,
			SCGAutoridadRegistroEntity entity,
			SCGPeriodoEntity periodo,
			String codigoRevalida
	) throws Exception{
		
		final String method = "getAutoridadList";
		final String params = "SCGAutoridadRegistroEntity entity";
		
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method, "codigoRevalida: "+codigoRevalida);
		UNALMLogger.trace(log, method, "entity.getTextoNombreAutoridad(): "+entity.getTextoNombreAutoridad());
		
		UNALMLogger.trace(log, method, "periodo.getFechaInicio(): "+periodo.getFechaInicio());
		UNALMLogger.trace(log, method, "periodo.getFechaFinal(): "+periodo.getFechaFinal());

		if(entity.getCargo() !=null){
			UNALMLogger.trace(log, method,"entity.getCargo().getTextoNombre(): " +entity.getCargo().getTextoNombre());
			UNALMLogger.trace(log, method,"entity.getCargo().getEspecialidad(): " +entity.getCargo().getEspecialidad());
		}
		if(entity.getGradoAcademico() !=null){
			UNALMLogger.trace(log, method,"entity.getGradoAcademico().getTextoNombre(): " +entity.getGradoAcademico().getTextoNombre());
		}
		
        try {
        	JSONArray dataJSON = new JSONArray();
        	if(entity.getCargo()!=null){
    			if(entity.getCargo().getEspecialidad()!=null){
    				SCGEspecialidadEntity especialidad = new SCGEspecialidadEntity();
    				especialidad.setCodigo(entity.getCargo().getEspecialidad());
    				SCGEspecialidadEntity especialidadCodigo = this.facultadService.getForUpdateEspecialidad(especialidad);
    				if(especialidadCodigo!=null){
    					if(especialidadCodigo.getFacultad()!=null){
    						UNALMLogger.trace(log, method,"especialidadCodigo.getFacultad().getCodigo(): " +especialidadCodigo.getFacultad().getCodigo());
    						entity.getCargo().setFacultad(new SCGFacultadEntity());
    						entity.getCargo().getFacultad().setCodigo(especialidadCodigo.getFacultad().getCodigo());;
    					}
    				}
    			}
    		}
        	List<SCGAutoridadRegistroEntity> list=this.autoridadService.getListAutoridad(entity,periodo);
            UNALMLogger.trace(log, method,"list: "+list.size());
            
        	for (SCGAutoridadRegistroEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("cargo", item.getCargo().getTextoNombre());
        		itemJSON.put("gradoAcademico", item.getGradoAcademico().getTextoNombre());
        		itemJSON.put("textoNombreAutoridad", item.getTextoNombreAutoridad());
        		itemJSON.put("fechaAgregar", TypesUtil.getDefaultString(item.getFechaAgregar(), "dd/MM/yyy"));
        		itemJSON.put("flagEstado", item.getFlagEstado());
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",list.size());
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al listar la informacion", e);
            rootJSON.put("success", false);
            rootJSON.put("data", new Object[]{});
            rootJSON.put("totalCount",0);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
		UNALMLogger.exit(log, method);
		
	}
	@RequestMapping("getPeriodoList")
	public  void getPeriodoList(HttpServletRequest request,HttpServletResponse response,SCGPeriodoEntity entity) throws Exception{
		final String method = "getPeriodoList";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGPeriodoEntity> list=this.autoridadService.getListPeriodo(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGPeriodoEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("fechaInicio", TypesUtil.getDefaultString(item.getFechaInicio(), "dd/MM/yyy"));
        		itemJSON.put("fechaFinal", TypesUtil.getDefaultString(item.getFechaFinal(), "dd/MM/yyy"));
        		itemJSON.put("textoCodigoPeriodo", item.getTextoCodigoPeriodo());
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",list.size());
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al listar la informacion", e);
            rootJSON.put("success", false);
            rootJSON.put("data", new Object[]{});
            rootJSON.put("totalCount",0);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
		UNALMLogger.exit(log, method);
		
	}
	
	@RequestMapping("getCargoList")
	public  void getCargoList(HttpServletRequest request,HttpServletResponse response,SCGCargoEntity entity) throws Exception{
		final String method = "getCargoList";
		final String params = "SCGCargoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGCargoEntity> list=this.autoridadService.getListCargo(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGCargoEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombre",item.getTextoNombre());
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",list.size());
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al listar la informacion", e);
            rootJSON.put("success", false);
            rootJSON.put("data", new Object[]{});
            rootJSON.put("totalCount",0);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
		UNALMLogger.exit(log, method);
		
	}
	
	@RequestMapping("getGradoAcademicoList")
	public  void getGradoAcademicoList(HttpServletRequest request,HttpServletResponse response,SCGGradoAcademicoEntity entity) throws Exception{
		final String method = "getGradoAcademicoList";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGGradoAcademicoEntity> list=this.autoridadService.getListGradoAcademico(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGGradoAcademicoEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombre",item.getTextoNombre() );
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",list.size());
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al listar la informacion", e);
            rootJSON.put("success", false);
            rootJSON.put("data", new Object[]{});
            rootJSON.put("totalCount",0);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
		UNALMLogger.exit(log, method);
		
	}
	@RequestMapping("getAutoridad")
	public  void getAutoridad(HttpServletRequest request,HttpServletResponse response,SCGAutoridadRegistroEntity entity) throws Exception{
		final String method = "getAutoridad";
		final String params = "SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method, "entity.getTextoNombreAutoridad(): "+entity.getTextoNombreAutoridad());
		
		if(entity.getCargo() !=null){
			UNALMLogger.trace(log, method,"entity.getCargo().getTextoNombre(): " +entity.getCargo().getTextoNombre());
		}
		if(entity.getGradoAcademico() !=null){
			UNALMLogger.trace(log, method,"entity.getGradoAcademico().getTextoNombre(): " +entity.getGradoAcademico().getTextoNombre());
		}
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	SCGAutoridadRegistroEntity autoridad=this.autoridadService.getForUpdateAutoridad(entity);

        	UNALMLogger.trace(log, method,"autoridad: "+autoridad);

        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", autoridad.getCodigo());
        		itemJSON.put("cargo.codigo", autoridad.getCargo().getCodigo());
        		itemJSON.put("cargo.textoNombre", autoridad.getCargo().getTextoNombre());
        		itemJSON.put("gradoAcademico.codigo", autoridad.getGradoAcademico().getCodigo());
        		itemJSON.put("gradoAcademico.textoNombre", autoridad.getGradoAcademico().getTextoNombre());
        		itemJSON.put("textoNombreAutoridad", autoridad.getTextoNombreAutoridad());
        		itemJSON.put("fechaAgregar", TypesUtil.getDefaultString(autoridad.getFechaAgregar(), "dd/MM/yyy"));
        		itemJSON.put("flagEstado", autoridad.getFlagEstado());
        		if( autoridad.getAdjunto() !=null){
        			itemJSON.put("URLBase",autoridad.getAdjunto().getTextoRuta());
            		itemJSON.put("adjunto.codigo", autoridad.getAdjunto().getCodigo());
        		}
        	rootJSON.put("success", true);
            rootJSON.put("data",itemJSON);
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al listar la informacion", e);
            rootJSON.put("success", false);
            rootJSON.put("data", new Object[]{});
            rootJSON.put("totalCount",0);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
		UNALMLogger.exit(log, method);
		
	}
	
	@RequestMapping("getPeriodo")
	public  void getPeriodo(HttpServletRequest request,HttpServletResponse response,SCGPeriodoEntity entity) throws Exception{
		final String method = "getPeriodo";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	SCGPeriodoEntity periodo=this.autoridadService.getForUpdatePeriodo(entity);

        	UNALMLogger.trace(log, method,"periodo: "+periodo);

        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", periodo.getCodigo());
        		itemJSON.put("autoridadRegistro.codigo", periodo.getAutoridadRegistro().getCodigo());
        		itemJSON.put("fechaInicio", TypesUtil.getDefaultString(periodo.getFechaInicio(), "yyy-MM-dd"));
        		itemJSON.put("fechaFinal", TypesUtil.getDefaultString(periodo.getFechaFinal(), "yyy-MM-dd"));
        		itemJSON.put("textoCodigoPeriodo", periodo.getTextoCodigoPeriodo());
        		
        	
        	rootJSON.put("success", true);
            rootJSON.put("data",itemJSON);
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al listar la informacion", e);
            rootJSON.put("success", false);
            rootJSON.put("data", new Object[]{});
            rootJSON.put("totalCount",0);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
		UNALMLogger.exit(log, method);
		
	}
	@RequestMapping("saveAutoridad")
	public  void save(HttpServletRequest request,HttpServletResponse response,SCGAutoridadRegistroEntity entity,SCGAdjuntoEntity adjunto) throws Exception{

		final String method = "saveAutoridad";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGAutoridadRegistroEntity entity,SCGAdjuntoEntity adjunto";
		String textoNombreAutoridad =null,
			   formatOriginalName=null,
			   nombreArchivo=null;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		adjunto.setCodigo(null);
		
		textoNombreAutoridad=TypesUtil.setFormatUTF8(entity.getTextoNombreAutoridad());
		entity.setTextoNombreAutoridad(textoNombreAutoridad);
		
		formatOriginalName=TypesUtil.setFormatUTF8(adjunto.getArchivo().getOriginalFilename());

		UNALMLogger.trace(log, method, "adjunto.getCodigo"+adjunto.getCodigo());
		UNALMLogger.trace(log, method, "adjunto.getArchivo().getBytes() "+adjunto.getArchivo().getBytes());
		UNALMLogger.trace(log, method, "adjunto.getArchivo().getOriginalFilename(): "+formatOriginalName);
		if( entity.getCargo() != null ){
			UNALMLogger.trace(log, method, "entity.getCargo().getCodigo()"+entity.getCargo().getCodigo());
		}
		
		try {
			SCGAdjuntoEntity codigoAdjunto = new SCGAdjuntoEntity();
			if(!TypesUtil.isEmptyString(adjunto.getArchivo().getOriginalFilename())){
				nombreArchivo=TypesUtil.getFormatFile(formatOriginalName);
				if(entity.getAdjunto()!=null){
					adjunto.setCodigo(entity.getAdjunto().getCodigo());
				}
				adjunto.setFileArchivo(adjunto.getArchivo().getBytes());
				adjunto.setTextoRuta("adjuntos/"+nombreArchivo);			
				adjunto.setTextoNombreArchivo(nombreArchivo);
				adjunto.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
				codigoAdjunto =this.autoridadService.saveAdjunto(adjunto);
			}
			UNALMLogger.trace(log, method, "codigoAdjunto: "+codigoAdjunto);
			if(codigoAdjunto.getCodigo()!=null){
				entity.setAdjunto(codigoAdjunto);
			}else{
				List<SCGAutoridadRegistroEntity> listAutoridad = this.autoridadService.getListAutoridad(entity, new SCGPeriodoEntity() );
				if( listAutoridad.size() > 0 ){
					jsonObjectRoot.put("success", false);
					jsonObjectRoot.put("message", "Ya existe este registro");
					return;
				}
				entity.setAdjunto(null);
			}
			
			
			entity.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			entity.setFechaAgregar(date);
			this.autoridadService.saveAutoridad(entity);
			JSONObject jsonObject = new JSONObject();
			
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Grabado Exitosamente");
			jsonObjectRoot.put("success", true);

		} catch (Exception e) {
			UNALMLogger.trace(log, method, "Exception:" + e);
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());

		} finally {
			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);
	};
	
	@RequestMapping("savePeriodo")
	public  void savePeriodo(HttpServletRequest request,HttpServletResponse response,SCGPeriodoEntity entity) throws Exception{

		final String method = "savePeriodo";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		try {
			
//			
//			SCGPeriodoEntity codigoAutoridad = new SCGPeriodoEntity();
//			codigoAutoridad.setCodigo( entity.getCodigo() );
//			List<SCGPeriodoEntity> listPeridos = this.autoridadService.getListPeriodo( codigoAutoridad );
//			
//			for ( SCGPeriodoEntity item : listPeridos ){
//				if( (item.getFechaInicio().compareTo( entity.getFechaInicio() ) *
//				    entity.getFechaInicio().compareTo(  item.getFechaFinal() )) >= 0){
////					UNALMLogger.trace(log, method, "ESTA FECHA SE ENCUENTRA DENTRODE UN PERIDODO EXISTENTE");
//					jsonObjectRoot.put("success", false);
//					jsonObjectRoot.put("message", "Esta fecha se encuentra dentro de un periodo existente");
//					return;
//					
//				}
//			}
//			if( entity.getFechaInicio().after( entity.getFechaFinal() ) ){
//				jsonObjectRoot.put("success", false);
//				jsonObjectRoot.put("message", "La fecha inicio esta despues de la fecha final");
//				return;
//			}
//			
			
			
			String result = this.verificarPeriodo(entity);
			UNALMLogger.trace(log, method, "result "+result);

			if( !TypesUtil.isEmptyString( result ) ){

				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", result);
				return;
			}
//			if( entity.getCodigo() != null ){
//				String result = this.verificarPeriodo(entity);
//				if( result.length() > 0 ){
//					jsonObjectRoot.put("success", false);
//					jsonObjectRoot.put("message", result);
//					return;
//				}
//			}
			entity.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			
			this.autoridadService.savePeriodo(entity);
			JSONObject jsonObject = new JSONObject();
			
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Grabado Exitosamente");
			jsonObjectRoot.put("success", true);

		} catch (Exception e) {
			UNALMLogger.trace(log, method, "Exception:" + e);
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());

		} finally {
			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);
	};
	private String verificarPeriodo( SCGPeriodoEntity periodo ){
		final String method = "verificarPeriodo";
		final String params = "SCGPeriodoEntity periodo";
		UNALMLogger.entry(log, method,params,new Object[]{periodo});
		String result = null;
		
		SCGPeriodoEntity codigoAutoridad = new SCGPeriodoEntity();
		codigoAutoridad.setAutoridadRegistro(new SCGAutoridadRegistroEntity());
		if( periodo.getAutoridadRegistro() != null){
			
			if( periodo.getAutoridadRegistro().getCodigo() != null ){
				codigoAutoridad.setAutoridadRegistro(periodo.getAutoridadRegistro());
			}
		}
		UNALMLogger.trace(log, method, "codigoAutoridad");
		UNALMLogger.trace(log, method, "codigoAutoridad.getAutoridadRegistro() "+codigoAutoridad.getAutoridadRegistro());
		UNALMLogger.trace(log, method, "codigoAutoridad.getAutoridadRegistro().getCodigo() "+codigoAutoridad.getAutoridadRegistro().getCodigo());
		
		if( codigoAutoridad.getAutoridadRegistro().getCodigo() != null ){
			List<SCGPeriodoEntity> listPeridos = this.autoridadService.getListPeriodo( codigoAutoridad );
			UNALMLogger.trace(log, method, "listPeridos.size()2 "+listPeridos.size());
			
			if( listPeridos.size() > 0 ){
				
				for ( SCGPeriodoEntity item : listPeridos ){
					if( periodo.getCodigo() != null ){
						UNALMLogger.trace(log, method, "item.getCodigo() "+item.getCodigo());
						UNALMLogger.trace(log, method, "periodo.getCodigo() "+periodo.getCodigo());

						if( !item.getCodigo().equals(periodo.getCodigo()) ){
//							if( (item.getFechaInicio().compareTo( periodo.getFechaInicio() ) *
//									periodo.getFechaInicio().compareTo(  item.getFechaFinal() )) >= 0){
//									result = "Esta fecha se encuentra dentro de un periodo existente";
//								}
							UNALMLogger.trace(log, method, "if 1");

							result = this.isBetweenPeriodoExistente(item, periodo.getFechaInicio() );
						}
					} else{
//						if( (item.getFechaInicio().compareTo( periodo.getFechaInicio() ) *
//								periodo.getFechaInicio().compareTo(  item.getFechaFinal() )) >= 0){
//								result = "Esta fecha se encuentra dentro de un periodo existente";
//							}
						UNALMLogger.trace(log, method, "if 2");

						result = this.isBetweenPeriodoExistente(item, periodo.getFechaInicio() );
					}
				}
				
			}
			if( TypesUtil.isEmptyString(result) ){
				if( periodo.getFechaInicio().after( periodo.getFechaFinal() ) ){
					result = "La fecha inicio esta despues de la fecha final";
				}
			}
		}else{
			result = "Error - comunicarse con TI de SG";
		}
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	private String isBetweenPeriodoExistente( SCGPeriodoEntity periodo, Date fechaComparar){
		String result = null;
		if( (periodo.getFechaInicio().compareTo( fechaComparar ) *
				fechaComparar.compareTo(  periodo.getFechaFinal() )) >= 0){
				result = "Esta fecha se encuentra dentro de un periodo existente";
			}
		return result;
		
	}
	
	
	@RequestMapping("deleteAutoridad")
	public void deleteAutoridad(HttpServletRequest request, HttpServletResponse response, SCGAutoridadRegistroEntity entity)
			throws Exception {
		final String method = "deleteAutoridad";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGAutoridadRegistroEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
//			List<SCGAutoridadRegistroEntity> listAutoridad = 
			String message = this.autoridadService.validateDelete(entity);
			if( message.length() > 0 ){
				UNALMLogger.trace(log, method, "message "+ message);
				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", message);
				return;
			}else{
				this.autoridadService.delete(entity);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Eliminado Exitosamente");
			jsonObjectRoot.put("success", true);
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Error al eliminar", e);
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());
		} finally {

			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}

	}
	@RequestMapping("deletePeriodo")
	public void deletePeriodo(HttpServletRequest request, HttpServletResponse response, SCGPeriodoEntity entity)
			throws Exception {
		final String method = "deletePeriodo";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());

		try {
			autoridadService.deletePeriodo(entity);;
			jsonObjectRoot.put("message", "Eliminado Exitosamente");
			jsonObjectRoot.put("success", true);
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Error al eliminar", e);
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());
		} finally {

			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}

	}
}

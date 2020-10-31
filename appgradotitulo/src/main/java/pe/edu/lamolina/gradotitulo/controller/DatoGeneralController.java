package pe.edu.lamolina.gradotitulo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.constant.FormDatoGeneral;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.gradotitulo.service.DatoGeneralService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.ExcelUtils;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("datoGeneral/*")
@Controller
public class DatoGeneralController {
	
	private final static Logger log = Logger.getLogger(DatoGeneralController.class);

	@Autowired
	private DatoGeneralService datoGeneralService;
	
	@RequestMapping("load")
	public String load(){
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "datoGeneral/loadView";
	}
	
	@RequestMapping("getEstudianteList")
	public  void getEstudianteList(HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity) throws Exception{
		final String method = "getEstudianteList";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FilterUtil filterUtil = new FilterUtil();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		JSONObject rootJSON = new JSONObject();
    	
		SCGPersonaDocumentoEntity codigoPersonaDocumento = new SCGPersonaDocumentoEntity();
    	SCGPersonaTelefonoEntity codigoPersonaTelefono = new SCGPersonaTelefonoEntity();
    	SCGTipoTelefonoEntity codigoExternoTipoTelefono = new SCGTipoTelefonoEntity();
    	SCGPersonaEntity codigoPersona= new SCGPersonaEntity();
    	
        UNALMLogger.trace(log, method,"entity.getTextoCodigoExternoDocumento"+entity.getTextoCodigoExternoDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoMatricula "+entity.getTextoMatricula());
        UNALMLogger.trace(log, method,"entity.getTextoNombreCompleto "+entity.getTextoNombreCompleto());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroResolucion "+entity.getTextoNumeroResolucion());
        UNALMLogger.trace(log, method,"entity.getFechaCreacion "+entity.getFechaCreacion());
		try {
        	JSONArray dataJSON = new JSONArray();
            if(entity.getEspecialidad()!= null){
            	  UNALMLogger.trace(log, method,"entity.getEspecialidad "+entity.getEspecialidad().getTextoNombreEspanol());
            }
            if(entity.getFacultad()!= null){
                  UNALMLogger.trace(log, method,"entity.getFacultad "+entity.getFacultad().getTextoNombreEspanol());
            }
        	List<SCGEstudianteEntity> list=this.datoGeneralService.getEstudianteList(entity,filterUtil);

        	for (SCGEstudianteEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
                UNALMLogger.trace(log, method,"item.getPersona().getCodigo(): "+item.getPersona().getCodigo());
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombreCompleto", item.getTextoNombreCompleto());
        		itemJSON.put("textoMatricula", item.getTextoMatricula());
        		itemJSON.put("textoNumeroResolucion", item.getTextoNumeroResolucion());
        		//itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "yyy-MM-dd"));
        		itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "dd-MM-yyyy"));
        		itemJSON.put("textoSexo",item.getPersona().getTextoSexo());
        		itemJSON.put("flagEnviadoSunedu",item.getFlagEnviadoSunedu());  
        		itemJSON.put("tipoDocumentoTextoNombre",item.getTextoCodigoExternoDocumento());  
        		itemJSON.put("textoDocumento",item.getTextoNumeroDocumento());
        		
        		if(item.getFacultad()!=null){
        			itemJSON.put("facultad", item.getFacultad().getTextoNombreEspanol());
        		}
        		if(item.getEspecialidad()!=null){
        			itemJSON.put("especialidad", item.getEspecialidad().getTextoNombreEspanol());
        		}
        		
        		codigoPersona.setCodigo(item.getPersona().getCodigo());
        		codigoPersonaTelefono.setPersona(codigoPersona);  
            	codigoExternoTipoTelefono.setCodigoExterno("F");
            	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
            	SCGPersonaTelefonoEntity personaTelefonoFijo=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
                UNALMLogger.trace(log, method,"personaTelefonoFijo: "+personaTelefonoFijo);
        		if(personaTelefonoFijo!=null){
            		itemJSON.put("textoNumeroTelefonoFijo", personaTelefonoFijo.getTextoNumeroTelefono());
            		itemJSON.put("codigoTelefonoFijo", personaTelefonoFijo.getCodigo());
            		UNALMLogger.trace(log, method,"codigoTelefonoFijo: "+ personaTelefonoFijo.getCodigo());
        		}
        		
                codigoExternoTipoTelefono.setCodigoExterno("C");
               	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
            	SCGPersonaTelefonoEntity personaTelefonoCelular=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
                UNALMLogger.trace(log, method,"personaTelefonoCelular: "+personaTelefonoCelular);
        		if(personaTelefonoCelular!=null){
            		itemJSON.put("textoNumeroTelefonoCelular", personaTelefonoCelular.getTextoNumeroTelefono());
            		itemJSON.put("codigoTelefonoCelular", personaTelefonoCelular.getCodigo());
            		UNALMLogger.trace(log, method,"codigoTelefonoCelular: "+personaTelefonoCelular.getCodigo());	
            			
        		}
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",filterUtil.getTotalCount());
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
	@RequestMapping("getEstudiantePregradoList")
	public  void getEstudiantePregradoList(HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity) throws Exception{
		final String method = "getEstudiantePregradoList";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FilterUtil filterUtil = new FilterUtil();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		JSONObject rootJSON = new JSONObject();
    	
		SCGPersonaDocumentoEntity codigoPersonaDocumento = new SCGPersonaDocumentoEntity();
    	SCGPersonaTelefonoEntity codigoPersonaTelefono = new SCGPersonaTelefonoEntity();
    	SCGTipoTelefonoEntity codigoExternoTipoTelefono = new SCGTipoTelefonoEntity();
    	SCGPersonaEntity codigoPersona= new SCGPersonaEntity();
    	
        UNALMLogger.trace(log, method,"entity.getTextoCodigoExternoDocumento"+entity.getTextoCodigoExternoDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoMatricula "+entity.getTextoMatricula());
        UNALMLogger.trace(log, method,"entity.getTextoNombreCompleto "+entity.getTextoNombreCompleto());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroResolucion "+entity.getTextoNumeroResolucion());
        UNALMLogger.trace(log, method,"entity.getFechaCreacion "+entity.getFechaCreacion());
		try {
        	JSONArray dataJSON = new JSONArray();
            if(entity.getEspecialidad()!= null){
            	  UNALMLogger.trace(log, method,"entity.getEspecialidad "+entity.getEspecialidad().getTextoNombreEspanol());
            }
            if(entity.getFacultad()!= null){
                  UNALMLogger.trace(log, method,"entity.getFacultad "+entity.getFacultad().getTextoNombreEspanol());
            }
        	List<SCGEstudianteEntity> list=this.datoGeneralService.getEstudiantePregradoList(entity,filterUtil);

        	for (SCGEstudianteEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
                UNALMLogger.trace(log, method,"item.getPersona().getCodigo(): "+item.getPersona().getCodigo());
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombreCompleto", item.getTextoNombreCompleto());
        		itemJSON.put("textoMatricula", item.getTextoMatricula());
        		itemJSON.put("textoNumeroResolucion", item.getTextoNumeroResolucion());
        		//itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "yyy-MM-dd"));
        		itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "dd-MM-yyyy"));
        		itemJSON.put("textoSexo",item.getPersona().getTextoSexo());
        		itemJSON.put("flagEnviadoSunedu",item.getFlagEnviadoSunedu());  
        		itemJSON.put("tipoDocumentoTextoNombre",item.getTextoCodigoExternoDocumento());  
        		itemJSON.put("textoDocumento",item.getTextoNumeroDocumento());
        		
        		if(item.getFacultad()!=null){
        			itemJSON.put("facultad", item.getFacultad().getTextoNombreEspanol());
        		}
        		if(item.getEspecialidad()!=null){
        			itemJSON.put("especialidad", item.getEspecialidad().getTextoNombreEspanol());
        		}
        		
        		codigoPersonaTelefono.setPersona(codigoPersona);  
            	codigoExternoTipoTelefono.setCodigoExterno("F");
            	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
            	SCGPersonaTelefonoEntity personaTelefonoFijo=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
                UNALMLogger.trace(log, method,"personaTelefonoFijo: "+personaTelefonoFijo);
        		if(personaTelefonoFijo!=null){
            		itemJSON.put("textoNumeroTelefonoFijo", personaTelefonoFijo.getTextoNumeroTelefono());
            		itemJSON.put("codigoTelefonoFijo", personaTelefonoFijo.getCodigo());
            		UNALMLogger.trace(log, method,"codigoTelefonoFijo: "+ personaTelefonoFijo.getCodigo());
        		}
        		
                codigoPersona.setCodigo(item.getPersona().getCodigo());
                codigoExternoTipoTelefono.setCodigoExterno("C");
               	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
            	SCGPersonaTelefonoEntity personaTelefonoCelular=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
                UNALMLogger.trace(log, method,"personaTelefonoCelular: "+personaTelefonoCelular);
        		if(personaTelefonoCelular!=null){
            		itemJSON.put("textoNumeroTelefonoCelular", personaTelefonoCelular.getTextoNumeroTelefono());
            		itemJSON.put("codigoTelefonoCelular", personaTelefonoCelular.getCodigo());
            		UNALMLogger.trace(log, method,"codigoTelefonoCelular: "+personaTelefonoCelular.getCodigo());	
            			
        		}
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",filterUtil.getTotalCount());
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
	@RequestMapping("getEstudianteRevalidaList")
	public  void getEstudianteRevalidaList(HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity) throws Exception{
		final String method = "getEstudianteRevalidaList";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FilterUtil filterUtil = new FilterUtil();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		JSONObject rootJSON = new JSONObject();
    	
		SCGPersonaDocumentoEntity codigoPersonaDocumento = new SCGPersonaDocumentoEntity();
    	SCGPersonaTelefonoEntity codigoPersonaTelefono = new SCGPersonaTelefonoEntity();
    	SCGTipoTelefonoEntity codigoExternoTipoTelefono = new SCGTipoTelefonoEntity();
    	SCGPersonaEntity codigoPersona= new SCGPersonaEntity();
    	
        UNALMLogger.trace(log, method,"entity.getTextoCodigoExternoDocumento"+entity.getTextoCodigoExternoDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoMatricula "+entity.getTextoMatricula());
        UNALMLogger.trace(log, method,"entity.getTextoNombreCompleto "+entity.getTextoNombreCompleto());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroResolucion "+entity.getTextoNumeroResolucion());
        UNALMLogger.trace(log, method,"entity.getFechaCreacion "+entity.getFechaCreacion());
		try {
        	JSONArray dataJSON = new JSONArray();
            if(entity.getEspecialidad()!= null){
            	  UNALMLogger.trace(log, method,"entity.getEspecialidad "+entity.getEspecialidad().getTextoNombreEspanol());
            }
            if(entity.getFacultad()!= null){
                  UNALMLogger.trace(log, method,"entity.getFacultad "+entity.getFacultad().getTextoNombreEspanol());
            }
        	List<SCGEstudianteEntity> list=this.datoGeneralService.getEstudianteRevalidaList(entity,filterUtil);

        	for (SCGEstudianteEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
                UNALMLogger.trace(log, method,"item.getPersona().getCodigo(): "+item.getPersona().getCodigo());
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombreCompleto", item.getTextoNombreCompleto());
        		itemJSON.put("textoMatricula", item.getTextoMatricula());
        		itemJSON.put("textoNumeroResolucion", item.getTextoNumeroResolucion());
        		//itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "yyy-MM-dd"));
        		itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "dd-MM-yyyy"));
        		itemJSON.put("textoSexo",item.getPersona().getTextoSexo());
        		itemJSON.put("flagEnviadoSunedu",item.getFlagEnviadoSunedu());  
        		itemJSON.put("tipoDocumentoTextoNombre",item.getTextoCodigoExternoDocumento());  
        		itemJSON.put("textoDocumento",item.getTextoNumeroDocumento());
        		
        		if(item.getFacultad()!=null){
        			itemJSON.put("facultad", item.getFacultad().getTextoNombreEspanol());
        		}
        		if(item.getEspecialidad()!=null){
        			itemJSON.put("especialidad", item.getEspecialidad().getTextoNombreEspanol());
        		}
        		
        		codigoPersonaTelefono.setPersona(codigoPersona);  
            	codigoExternoTipoTelefono.setCodigoExterno("F");
            	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
            	SCGPersonaTelefonoEntity personaTelefonoFijo=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
                UNALMLogger.trace(log, method,"personaTelefonoFijo: "+personaTelefonoFijo);
        		if(personaTelefonoFijo!=null){
            		itemJSON.put("textoNumeroTelefonoFijo", personaTelefonoFijo.getTextoNumeroTelefono());
            		itemJSON.put("codigoTelefonoFijo", personaTelefonoFijo.getCodigo());
            		UNALMLogger.trace(log, method,"codigoTelefonoFijo: "+ personaTelefonoFijo.getCodigo());
        		}
        		
                codigoPersona.setCodigo(item.getPersona().getCodigo());
                codigoExternoTipoTelefono.setCodigoExterno("C");
               	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
            	SCGPersonaTelefonoEntity personaTelefonoCelular=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
                UNALMLogger.trace(log, method,"personaTelefonoCelular: "+personaTelefonoCelular);
        		if(personaTelefonoCelular!=null){
            		itemJSON.put("textoNumeroTelefonoCelular", personaTelefonoCelular.getTextoNumeroTelefono());
            		itemJSON.put("codigoTelefonoCelular", personaTelefonoCelular.getCodigo());
            		UNALMLogger.trace(log, method,"codigoTelefonoCelular: "+personaTelefonoCelular.getCodigo());	
            			
        		}
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount",filterUtil.getTotalCount());
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
	@RequestMapping("getTipoDocumentoList")
	public  void getTipoDocumentoList(HttpServletRequest request,HttpServletResponse response,SCGTipoDocumentoEntity entity) throws Exception{
		final String method = "getTipoDocumentoList";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGTipoDocumentoEntity> list=this.datoGeneralService.getTipoDocumentoList(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGTipoDocumentoEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombre",item.getTextoNombre());
        		itemJSON.put("codigoExterno",item.getCodigoExterno());
        		itemJSON.put("flagSunedu",item.getFlagSunedu());
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
	@RequestMapping("getEstudiante")
	public  void getEstudiante(HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity) throws Exception{
		final String method = "getEstudiante";
		final String params = "SCGEstudianteEntity entity";
		
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	SCGPersonaEntity personaCodigo = new SCGPersonaEntity();
        	SCGEstudianteEntity estudiante=this.datoGeneralService.getForUpdateEstudiante(entity);
        	personaCodigo.setCodigo(estudiante.getPersona().getCodigo());
        	SCGTipoDocumentoEntity tipoDocumento = new SCGTipoDocumentoEntity();
        	SCGTipoDocumentoEntity codigoTipoDocumento = new SCGTipoDocumentoEntity();
        	if(!TypesUtil.isEmptyString(estudiante.getTextoCodigoExternoDocumento())){
        		tipoDocumento.setCodigoExterno(estudiante.getTextoCodigoExternoDocumento());
            	codigoTipoDocumento=this.datoGeneralService.getForUpdateTipoDocumento(tipoDocumento);
        	}
        	

        	SCGPersonaTelefonoEntity personaTelefono = new SCGPersonaTelefonoEntity();
        	SCGTipoTelefonoEntity tipoTelefono = new SCGTipoTelefonoEntity();
        	personaTelefono.setPersona(personaCodigo);
        	tipoTelefono.setCodigoExterno("F");
        	personaTelefono.setTipoTelefono(tipoTelefono);; 
        	SCGPersonaTelefonoEntity telfonoFijo = this.datoGeneralService.getForUpdateTelefono(personaTelefono);
        	personaTelefono.setPersona(personaCodigo);
        	tipoTelefono.setCodigoExterno("C");
        	personaTelefono.setTipoTelefono(tipoTelefono);;        	
        	SCGPersonaTelefonoEntity telfonoCelular = this.datoGeneralService.getForUpdateTelefono(personaTelefono);
        	
        	JSONObject itemJSON = new JSONObject();
        	itemJSON.put("codigo", estudiante.getCodigo());
        	itemJSON.put("textoMatricula", estudiante.getTextoMatricula());
        	itemJSON.put("textoNombre", estudiante.getPersona().getTextoNombre());
        	itemJSON.put("textoPaterno", estudiante.getPersona().getTextoPaterno());
        	itemJSON.put("textoMaterno", estudiante.getPersona().getTextoMaterno());
        	itemJSON.put("textoSexo", estudiante.getPersona().getTextoSexo());
        	itemJSON.put("textoDocumento", estudiante.getTextoNumeroDocumento());
        	if(codigoTipoDocumento!=null){
        		if(codigoTipoDocumento.getCodigo()!=null){
        			itemJSON.put("tipoDocumento.codigo", codigoTipoDocumento.getCodigo());
        		}
        		
        	}
        	if(telfonoFijo !=null){
            	itemJSON.put("codigoTelefonoFijo", telfonoFijo.getCodigo());
        		itemJSON.put("textoNumeroTelefonoFijo", telfonoFijo.getTextoNumeroTelefono());
        	}
        	if(telfonoCelular !=null){
            	itemJSON.put("codigoTelefonoCelular", telfonoCelular.getCodigo());
            	itemJSON.put("textoNumeroTelefonoCelular", telfonoCelular.getTextoNumeroTelefono());
        	}
        	if(estudiante.getFacultad() != null){
        		itemJSON.put("facultad.codigo", estudiante.getFacultad().getCodigo());	
        	}
        	if( estudiante.getEspecialidad() !=null){
        		itemJSON.put("especialidad.codigo", estudiante.getEspecialidad().getCodigo());
        	}
        	/*
        	if( estudiante.getPersona().getAdjunto() !=null){
        		itemJSON.put("URLBase", estudiante.getPersona().getAdjunto().getTextoRuta());
        		itemJSON.put("adjunto.codigo", estudiante.getPersona().getAdjunto().getCodigo());
        	}
        	*/
        	if(!TypesUtil.isEmptyString(estudiante.getTextoCicloEgreso())){
        		SCGCicloEntity cicloSemestre = new SCGCicloEntity();
        		cicloSemestre.setTextoSemestre(estudiante.getTextoCicloEgreso());
        		SCGCicloEntity ciclo = this.datoGeneralService.getForUpdateCiclo(cicloSemestre);
        		if(ciclo != null){
            		itemJSON.put("textoCicloEgreso", ciclo.getCodigo());
        		}
        	}
        	itemJSON.put("fechaTramiteConstancia", TypesUtil.getDefaultString(estudiante.getFechaTramiteConstancia(), "dd/MM/yyy"));
        	itemJSON.put("fechaIngresanteMatricula", TypesUtil.getDefaultString(estudiante.getFechaIngresanteMatricula(), "dd/MM/yyy"));
        	itemJSON.put("textoNumeroResolucion", estudiante.getTextoNumeroResolucion());
        		
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
	
	@RequestMapping("getTipoDocumento")
	public  void getTipoDocumento(HttpServletRequest request,HttpServletResponse response,SCGTipoDocumentoEntity entity) throws Exception{
		final String method = "getTipoDocumento";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		UNALMLogger.trace(log, method,"entity.getCodigo "+entity.getCodigo());
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	SCGTipoDocumentoEntity tipoDocumento=this.datoGeneralService.getForUpdateTipoDocumento(entity);
        	JSONObject itemJSON = new JSONObject();
        	itemJSON.put("codigo", tipoDocumento.getCodigo());
        	itemJSON.put("codigoExterno", tipoDocumento.getCodigoExterno());
        	itemJSON.put("textoNombre", tipoDocumento.getTextoNombre());
        	itemJSON.put("flagSunedu", tipoDocumento.getFlagSunedu());
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
	
	@RequestMapping("getCicloList")
	public  void getCicloList(HttpServletRequest request,HttpServletResponse response,SCGCicloEntity entity) throws Exception{
		final String method = "getCicloList";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGCicloEntity> list=this.datoGeneralService.getCicloList(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGCicloEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoSemestre",item.getTextoSemestre());
        		itemJSON.put("textoCiclo",item.getTextoCiclo());
        		itemJSON.put("fechaFinalCiclo",item.getFechaFinalCiclo());
        		itemJSON.put("fechaInicioCiclo",item.getFechaInicioCiclo());
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
	@RequestMapping("saveEstudiante")
	public  void save(HttpServletRequest request,HttpServletResponse response,
			SCGEstudianteEntity estudiante,
			SCGPersonaDocumentoEntity documento,
			//SCGPersonaTelefonoEntity telefono, 
			SCGPersonaEntity persona,
			//SCGAdjuntoEntity adjunto,
			FormDatoGeneral form
	) throws Exception{

		final String method = "save";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{form});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		Date date = new Date();
		documento.setCodigo(null);		
		//adjunto.setCodigo(null);		
		UNALMLogger.trace(log, method, "codigo "+estudiante.getCodigo());	
		UNALMLogger.trace(log, method, "cicloEgreso "+estudiante.getTextoCicloEgreso());	
		UNALMLogger.trace(log, method, "fechaIngresanteMatricula "+estudiante.getFechaIngresanteMatricula());
		UNALMLogger.trace(log, method, "fechaTramiteConstancia "+estudiante.getFechaTramiteConstancia());	
		UNALMLogger.trace(log, method, "codigoTelefonoCelular " + form.getCodigoTelefonoCelular());	
		UNALMLogger.trace(log, method, "codigoTelefonoFijo "+form.getCodigoTelefonoFijo());	
		//UNALMLogger.trace(log, method, "adjunto.codigo "+persona.getAdjunto().getCodigo());	
		UNALMLogger.trace(log, method, "textoMatricula "+estudiante.getTextoMatricula());	
		UNALMLogger.trace(log, method, "textoNombreEstudiante "+persona.getTextoNombre());	
		UNALMLogger.trace(log, method, "textoApellidoPaternoEstudiante "+persona.getTextoPaterno());	
		UNALMLogger.trace(log, method, "textoApellidoMaternoEstudiante "+persona.getTextoMaterno());	
		UNALMLogger.trace(log, method, "textoSexo "+persona.getTextoSexo());	
		if(documento.getTipoDocumento()!=null){
			UNALMLogger.trace(log, method, "documento.getTipoDocumento().getCodigo() "+documento.getTipoDocumento().getCodigo());		
			UNALMLogger.trace(log, method, "documento.getTipoDocumento().getCodigoExterno() "+documento.getTipoDocumento().getCodigoExterno());		
			if( documento.getTipoDocumento().getCodigo() == 300L){
				documento.setTextoDocumento("NO DEFINIDO");

			}
			UNALMLogger.trace(log, method, "documento.getTextoDocumento() "+documento.getTextoDocumento());

			
		}
		/*
		if(estudiante.getFacultad()!=null){
			UNALMLogger.trace(log, method, "facultad "+estudiante.getFacultad().getCodigo());		
		}
		if(estudiante.getEspecialidad()!=null){
			UNALMLogger.trace(log, method, "especialidad "+estudiante.getEspecialidad().getCodigo());		
		}
		*/
		UNALMLogger.trace(log, method, "textoNumeroResolucion "+estudiante.getTextoNumeroResolucion());	
		UNALMLogger.trace(log, method, "textoNumeroTelefonoFijo "+form.getTextoNumeroTelefonoFijo());	
		UNALMLogger.trace(log, method, "textoNumeroTelefonoCelular "+form.getTextoNumeroTelefonoCelular());
		//UNALMLogger.trace(log, method, "archivo "+adjunto.getArchivo().getBytes());
		//UNALMLogger.trace(log, method, "adjunto.getArchivo().getOriginalFilename(): "+adjunto.getArchivo().getOriginalFilename());
		
		try {
			/*
			SCGAdjuntoEntity codigoAdjunto = new SCGAdjuntoEntity();
			if(!TypesUtil.isEmptyString(adjunto.getArchivo().getOriginalFilename())){
				String nombreArchivo=TypesUtil.getFormatFile(adjunto.getArchivo().getOriginalFilename());
				if(persona.getAdjunto()!=null){
					adjunto.setCodigo(persona.getAdjunto().getCodigo());
				}
				adjunto.setFileArchivo(adjunto.getArchivo().getBytes());
				adjunto.setTextoRuta("adjuntos/"+nombreArchivo);			
				adjunto.setTextoNombreArchivo(nombreArchivo);
				adjunto.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
				codigoAdjunto =this.datoGeneralService.saveAdjunto(adjunto);
			}
			UNALMLogger.trace(log, method, "codigoAdjunto: "+codigoAdjunto);
			*/
			
			String message = "";
			message = this.datoGeneralService.validatePeriodoEstudio( estudiante) ;
			if( message.length()>0 ){
				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", message);
				return;
			}
			
			if(estudiante.getCodigo()!=null){
				SCGEstudianteEntity estudianteCodigoFind = new SCGEstudianteEntity();
				estudianteCodigoFind.setCodigo(estudiante.getCodigo());
				SCGEstudianteEntity personaCodigo = this.datoGeneralService.getForUpdateEstudiante(estudianteCodigoFind);
				persona.setCodigo(personaCodigo.getPersona().getCodigo());
			}
			/*
			if(codigoAdjunto.getCodigo()!=null){
				persona.setAdjunto(codigoAdjunto);
			}else{
				persona.setAdjunto(null);
			}
			*/
			persona.setTextoNombreCompleto(persona.getTextoPaterno()+' '+persona.getTextoMaterno()+' '+persona.getTextoNombre());
			persona.setFechaAgregar(date);
			persona.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			SCGPersonaEntity codigoPersona=this.datoGeneralService.savePersona(persona);		

			documento.setPersona(codigoPersona);
			documento.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			documento.setTextoDocumento(documento.getTextoDocumento());;
			
			
			SCGPersonaTelefonoEntity persoTelefono = new SCGPersonaTelefonoEntity();
			SCGTipoTelefonoEntity codigoTipoTelefono = new SCGTipoTelefonoEntity();
			if(!TypesUtil.isEmptyString(form.getTextoNumeroTelefonoCelular()) ){
				//codigoTipoTelefono.setCodigo(form.getCodigoTelefonoCelular());
				codigoTipoTelefono.setCodigoExterno("C");
				SCGTipoTelefonoEntity tipoTelefono=this.datoGeneralService.getForUpdateTipoTelefono(codigoTipoTelefono);
				persoTelefono.setCodigo(form.getCodigoTelefonoCelular());
				persoTelefono.setPersona(codigoPersona);
				persoTelefono.setTipoTelefono(tipoTelefono);
				persoTelefono.setTextoNumeroTelefono(form.getTextoNumeroTelefonoCelular());
				persoTelefono.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
				this.datoGeneralService.savePersonaTelefono(persoTelefono);
			}
			if(!TypesUtil.isEmptyString(form.getTextoNumeroTelefonoFijo() )){
				//codigoTipoTelefono.setCodigo(form.getCodigoTelefonoFijo());
				codigoTipoTelefono.setCodigoExterno("F");
				SCGTipoTelefonoEntity tipoTelefono=this.datoGeneralService.getForUpdateTipoTelefono(codigoTipoTelefono);
				persoTelefono.setCodigo(form.getCodigoTelefonoFijo());
				persoTelefono.setPersona(codigoPersona);
				persoTelefono.setTipoTelefono(tipoTelefono);
				persoTelefono.setTextoNumeroTelefono(form.getTextoNumeroTelefonoFijo());
				persoTelefono.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
				this.datoGeneralService.savePersonaTelefono(persoTelefono);
			}
			
			estudiante.setPersona(codigoPersona);
			//estudiante.setTextoNombreCompleto(persona.getTextoPaterno()+' '+persona.getTextoMaterno()+' '+persona.getTextoNombre());
			estudiante.setTextoNombreCompleto(persona.getTextoNombre()+' '+persona.getTextoPaterno()+' '+persona.getTextoMaterno());
			estudiante.setFechaCreacion(date);
			SCGTipoDocumentoEntity codigoTipoDocumento = new SCGTipoDocumentoEntity();
			codigoTipoDocumento.setCodigo(documento.getTipoDocumento().getCodigo());
			SCGTipoDocumentoEntity tipoDocumento=this.datoGeneralService.getForUpdateTipoDocumento(codigoTipoDocumento);
			estudiante.setTextoCodigoExternoDocumento(tipoDocumento.getCodigoExterno());
			estudiante.setTextoNumeroDocumento(documento.getTextoDocumento());
			estudiante.setFechaCreacion(date);
			estudiante.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			if(!TypesUtil.isEmptyString(estudiante.getTextoCicloEgreso())){
				SCGCicloEntity ciclo = new SCGCicloEntity();
				Long codigoCiclo = Long.parseLong(estudiante.getTextoCicloEgreso(),10);
				ciclo.setCodigo(codigoCiclo);
				SCGCicloEntity estudianteCicloEgreso =this.datoGeneralService.getForUpdateCiclo(ciclo);
				UNALMLogger.trace(log, method, "estudianteCicloEgreso.getTextoSemestre(): "+estudianteCicloEgreso.getTextoSemestre());
				estudiante.setTextoCicloEgreso(estudianteCicloEgreso.getTextoSemestre());
			}
			
			this.datoGeneralService.savePersonaDocumento(documento);
			this.datoGeneralService.saveEstudiante(estudiante);
			
			
			
			JSONObject jsonObject = new JSONObject();
			
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Grabado Exitosamente");
			jsonObjectRoot.put("success", true);

		} catch (Exception e) {
			UNALMLogger.error(log, method, "Exception ", e);;
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());

		} finally {
			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);
	};
	@RequestMapping("saveTipoDocumentoIdentidad")
	public  void saveTipoDocumentoIdentidad(HttpServletRequest request,HttpServletResponse response,SCGTipoDocumentoEntity entity) throws Exception{
		final String method = "saveTipoDocumentoIdentidad";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGTipoDocumentoEntity estudiante";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "codigo "+entity.getCodigo());	
		UNALMLogger.trace(log, method, "getCodigoExterno "+entity.getCodigoExterno());	
		UNALMLogger.trace(log, method, "getTextoNombre "+entity.getTextoNombre());
		UNALMLogger.trace(log, method, "getFlagSunedu "+entity.getFlagSunedu());
		try {
			entity.setFlagEliminado("1");
			this.datoGeneralService.saveTipoDocumentoIdentidad(entity);
			JSONObject jsonObject = new JSONObject();			
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Grabado Exitosamente");
			jsonObjectRoot.put("success", true);

		} catch (Exception e) {
			UNALMLogger.error(log, method, "Exception ", e);;
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());

		} finally {
			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);
	};
	@RequestMapping("deleteEstudiante")
	public void deleteEstudiante(HttpServletRequest request, HttpServletResponse response, SCGEstudianteEntity entity)throws Exception {
		final String method = "deleteEstudiante";
		final String params = "SCGPeriodoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		SCGEstudianteEntity estudiante =this.datoGeneralService.getForUpdateEstudiante(entity);
		SCGPersonaEntity persona = new SCGPersonaEntity();
		SCGPersonaDocumentoEntity documento = new SCGPersonaDocumentoEntity();
		SCGPersonaTelefonoEntity telefono = new SCGPersonaTelefonoEntity();
		if(estudiante.getPersona() != null ){
			persona.setCodigo(estudiante.getPersona().getCodigo());
			documento.setPersona(new SCGPersonaEntity());
			documento.getPersona().setCodigo(estudiante.getPersona().getCodigo());
			telefono.setPersona(new SCGPersonaEntity());
			telefono.getPersona().setCodigo(estudiante.getPersona().getCodigo());
		}
		try {
			this.datoGeneralService.deleteEstudiante(entity);
			this.datoGeneralService.deleteDocumento(documento);;
			this.datoGeneralService.deleteTelefono(telefono);
			this.datoGeneralService.deletePersona(persona);
			this.datoGeneralService.deleteAdjunto(persona);
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
	@RequestMapping("deleteTipoDocumento")
	public void deleteTipoDocumento(HttpServletRequest request, HttpServletResponse response, SCGTipoDocumentoEntity entity)throws Exception {
		final String method = "deleteTipoDocumento";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			this.datoGeneralService.deleteTipoDocumento(entity);
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
	@RequestMapping(value = "excel")
	public ModelAndView excel(HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity) throws IOException {
		final String method = "excel";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
    	
    	SCGPersonaTelefonoEntity codigoPersonaTelefono = new SCGPersonaTelefonoEntity();
    	SCGTipoTelefonoEntity codigoExternoTipoTelefono = new SCGTipoTelefonoEntity();
    	SCGPersonaEntity codigoPersona= new SCGPersonaEntity();
		List<List<String>> masterList = new ArrayList<List<String>>();

    	UNALMLogger.trace(log, method,"entity.getTextoCodigoExternoDocumento"+entity.getTextoCodigoExternoDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoMatricula "+entity.getTextoMatricula());
        UNALMLogger.trace(log, method,"entity.getTextoNombreCompleto "+entity.getTextoNombreCompleto());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroDocumento "+entity.getTextoNumeroDocumento());
        UNALMLogger.trace(log, method,"entity.getTextoNumeroResolucion "+entity.getTextoNumeroResolucion());
        UNALMLogger.trace(log, method,"entity.getFechaCreacion "+entity.getFechaCreacion());
        if(entity.getEspecialidad()!= null){
        	UNALMLogger.trace(log, method,"entity.getEspecialidad "+entity.getEspecialidad().getTextoNombreEspanol());
        }
        if(entity.getFacultad()!= null){
        	UNALMLogger.trace(log, method,"entity.getFacultad "+entity.getFacultad().getTextoNombreEspanol());
        }
		List<SCGEstudianteEntity> list=this.datoGeneralService.getEstudianteListExcel(entity);
    	int count=0;
		for (SCGEstudianteEntity item : list) {
			List<String> objectDetails = new ArrayList<String>();
			String numeroFijo="",
				   numeroCelular="";
			count++;
			SCGPersonaTelefonoEntity personaTelefono = new SCGPersonaTelefonoEntity();
    		personaTelefono.setPersona(new SCGPersonaEntity());
    		personaTelefono.getPersona().setCodigo(item.getPersona().getCodigo());
        	List<SCGPersonaTelefonoEntity> personaTelefonoFijoList=this.datoGeneralService.getPersonaTelefonoList(personaTelefono);
        	if(personaTelefonoFijoList!=null){
        		for(SCGPersonaTelefonoEntity telefono :personaTelefonoFijoList){
        			if(telefono.getTipoTelefono().getCodigoExterno().equals("F")){
        				numeroFijo=telefono.getTextoNumeroTelefono();
        			}else if(telefono.getTipoTelefono().getCodigoExterno().equals("C")){
        				numeroCelular=telefono.getTextoNumeroTelefono();
        			}
        		}
        	}
			
			
			objectDetails.add(String.valueOf(count));
			objectDetails.add(item.getTextoMatricula());
			objectDetails.add(item.getTextoNombreCompleto());
			objectDetails.add("");
			objectDetails.add(""); 
			objectDetails.add(TypesUtil.getDefaultString(item.getFechaCreacion(), "dd-MM-yyy"));
    		objectDetails.add(item.getPersona().getTextoSexo() );
    		objectDetails.add(item.getTextoCodigoExternoDocumento());  
    		objectDetails.add(item.getTextoNumeroDocumento() );
    		objectDetails.add(numeroFijo);
    		objectDetails.add(numeroCelular);
    		
    		/*
    		codigoPersonaTelefono.setPersona(codigoPersona);  
        	codigoExternoTipoTelefono.setCodigoExterno("F");
        	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
        	SCGPersonaTelefonoEntity personaTelefonoFijo=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
            UNALMLogger.trace(log, method,"personaTelefonoFijo: "+personaTelefonoFijo);
    		if(personaTelefonoFijo!=null){
    			objectDetails.add(personaTelefonoFijo.getTextoNumeroTelefono());
    		}
    		
            codigoPersona.setCodigo(item.getPersona().getCodigo());
            codigoExternoTipoTelefono.setCodigoExterno("C");
           	codigoPersonaTelefono.setTipoTelefono(codigoExternoTipoTelefono);      		
        	SCGPersonaTelefonoEntity personaTelefonoCelular=this.datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
            UNALMLogger.trace(log, method,"personaTelefonoCelular: "+personaTelefonoCelular);
    		if(personaTelefonoCelular!=null){
        		objectDetails.add(personaTelefonoCelular.getTextoNumeroTelefono());
    		}
    		*/

    		masterList.add(objectDetails);
    	}
		
		List<String> myReportHeader = getTrainingReportHeader();
		masterList.add(0, myReportHeader);
		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, masterList, "ReportEstudiantes");
		ExcelUtils.generateReport(response, workBook, "PadronEstudiantes");
		return null;
	}
	
	private List<String> getTrainingReportHeader() {
		List<String> headerList = new ArrayList<String>(15);
		headerList.add("#");
		headerList.add("Matricula");
		headerList.add("Nombre");
		headerList.add("Facultad");
		headerList.add("Especialidad");
		headerList.add("Fecha de Registro");
		headerList.add("Sexo");
		headerList.add("Documento de Identidad");
		headerList.add("Numero de Documento");
		headerList.add("Telefono");
		headerList.add("Celular");
		return headerList;
	}
	
	
}

package pe.edu.lamolina.tramitedocumentario.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
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
import pe.edu.lamolina.gradotitulo.controller.GradoTituloController;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGProgramaEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsableEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGTipoTramiteDocumentoEntity;
import pe.edu.lamolina.tramitedocumentario.report.service.RegistroExcelService;
import pe.edu.lamolina.tramitedocumentario.service.RegistroService;
import pe.edu.lamolina.util.ExcelUtils;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("tramiteDocumentario/registro/*")
@Controller
public class RegistroController {
	
	private final static Logger log = Logger.getLogger(GradoTituloController.class);
	
	@Autowired
	private RegistroService registroService;
	
	@Autowired
	private RegistroExcelService registroExcelService;
	
	@RequestMapping("load")
	public String load() {
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "tramiteDocumentario/registro/loadView";
	}
	
	@RequestMapping("listTipoTramiteDocumento")
	public  void listTipoTramiteDocumento(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final String method = "listTipoTramiteDocumento";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	List<SCGTipoTramiteDocumentoEntity> list = this.registroService.listTipoTramiteDocumento();
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	for (SCGTipoTramiteDocumentoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoNombre", item.getTextoNombre());
				dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
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
	
	@RequestMapping("listResponsabilidad")
	public  void listResponsabilidad(HttpServletRequest request,HttpServletResponse response, SCGResponsabilidadEntity entity) throws Exception{
		final String method = "listResponsabilidad";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	List<SCGResponsabilidadEntity> list = this.registroService.listResponsabilidad(entity);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	for (SCGResponsabilidadEntity item : list) {
//				UNALMLogger.trace(log, method, "item.getTextoNombre() "+item.getSeguridadUsuario().getTextoNombre());
//				UNALMLogger.trace(log, method, "responsableCodigo "+item.getResponsable().getCodigo());

        		JSONObject itemJSON = new JSONObject();
//				itemJSON.put("codigo",item.getCodigo());
				itemJSON.put("codigo",item.getSeguridadUsuario().getCodigo());
				itemJSON.put("textoNombre", item.getSeguridadUsuario().getTextoNombre());
				itemJSON.put("responsableCodigo", item.getResponsable().getCodigo());
//				itemJSON.put("codigo", item.getSeguridadUsuario().getCodigo());
//				itemJSON.put("textoNombre", item.getSeguridadUsuario().getTextoNombre());
//				itemJSON.put("textoNombre", item.getTextoNombre());
				dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
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
	
	@RequestMapping("listSolicitudProceso")
	public  void listSolicitudProceso(HttpServletRequest request,HttpServletResponse response, SCGSolicitudProcesoEntity entity) throws Exception{
		final String method = "listSolicitudProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		String testoNumeroEscrito = request.getParameter("query");
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		FilterUtil filterUtil = new FilterUtil();
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
//		entity.setTextoNumeroDocumento(testoNumeroEscrito);
		entity.setTextoNombreMatch(testoNumeroEscrito);
		UNALMLogger.trace(log, method,"testoNumeroEscrito " + testoNumeroEscrito);
		UNALMLogger.trace(log, method,"getTextoAsunto " + entity.getTextoAsunto());
		UNALMLogger.trace(log, method,"getTextoNombreProcedencia " + entity.getTextoNombreProcedencia());
		UNALMLogger.trace(log, method,"getTextoNombreRemitente " + entity.getTextoNombreRemitente());
		UNALMLogger.trace(log, method,"getTextoNumeroDocumento " + entity.getTextoNumeroDocumento());
		UNALMLogger.trace(log, method,"getTextoObservacion " + entity.getTextoObservacion());
		UNALMLogger.trace(log, method,"getTextoObservacion " + entity.getFechaAgregarInicio());
		UNALMLogger.trace(log, method,"getTextoObservacion " + entity.getFechaAgregarFinal());
		UNALMLogger.trace(log, method,"getDistinct " + entity.getDistinct());
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	List<SCGSolicitudProcesoEntity> list = this.registroService.listSolicitudProceso(entity, filterUtil);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	for (SCGSolicitudProcesoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				UNALMLogger.trace(log, method, "item.getCodigo() "+item.getCodigo());
				UNALMLogger.trace(log, method, "item.getTextoNumeroDocumento() "+item.getTextoNumeroDocumento());
				UNALMLogger.trace(log, method, "fechaIncio "+item.getFechaInicio());
				UNALMLogger.trace(log, method, "fechaFinal "+item.getFechaFinal());
				UNALMLogger.trace(log, method, "tipoColor: "+item.getTextoTipoColor());
				
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("codigoAsociacion", item.getCodigoAsociacion());
				itemJSON.put("textoAsunto", item.getTextoAsunto());
				itemJSON.put("textoNombreProcedencia", item.getTextoNombreProcedencia());
				itemJSON.put("textoNombreRemitente", item.getTextoNombreRemitente());
				itemJSON.put("textoNumeroDocumento", item.getTextoNumeroDocumento());
				itemJSON.put("textoObservacion", item.getTextoObservacion());
				itemJSON.put("flagCandado", item.getFlagCandado());
				itemJSON.put("fechaInicio", item.getFechaInicio());
				itemJSON.put("fechaFinal", item.getFechaFinal());
				itemJSON.put("fechaAgregarInicio", item.getFechaAgregarInicio());
				itemJSON.put("fechaAgregarFinal", item.getFechaAgregarFinal());
				itemJSON.put("tipoColor", item.getTextoTipoColor());
				
				SCGFlujoProcesoEntity flujoProceso = this.registroService.getForUpdateSolicitudProceso(item);
				if(flujoProceso.getDefinicionProceso() != null){
					
					if(flujoProceso.getDefinicionProceso().getProceso() != null){
						SCGProcesoEntity proceso = flujoProceso.getDefinicionProceso().getProceso();
						UNALMLogger.trace(log, method, "textoNombreProceso " + proceso.getTextoNombre());
						itemJSON.put("proceso", proceso.getTextoNombre());
									
					}				
				}
				
				if(item.getTipoTramiteDocumento() != null){
					UNALMLogger.trace(log, method, "tipoTramiteDocumento " + item.getTipoTramiteDocumento().getTextoNombre());
					itemJSON.put("tipoTramiteDocumento", item.getTipoTramiteDocumento().getTextoNombre());
				}
				
				if(item.getSeguridadUsuario() != null){
					UNALMLogger.trace(log, method, "seguridadUsuario " + item.getSeguridadUsuario().getTextoNombre());
					itemJSON.put("seguridadUsuario", item.getSeguridadUsuario().getTextoNombre());
				}
				
				dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
			rootJSON.put("totalCount", filterUtil.getTotalCount() );
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
	
	@RequestMapping("listDistinctProcedencia")
	public  void listDistinctProcedencia(HttpServletRequest request,HttpServletResponse response, SCGSolicitudProcesoEntity entity) throws Exception{
		final String method = "listDistinctProcedencia";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		FilterUtil filterUtil = new FilterUtil();
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		
		UNALMLogger.trace(log, method,"getTextoNombreProcedencia " + entity.getTextoNombreProcedencia());
		
		try {
			JSONArray dataJSON = new JSONArray();        	
			List<SCGSolicitudProcesoEntity> list = this.registroService.listDistinctProcedencia(entity, filterUtil);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			for (SCGSolicitudProcesoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				UNALMLogger.trace(log, method, "item.getTextoNombreProcedencia() "+item.getTextoNombreProcedencia());
				itemJSON.put("textoNombreProcedencia", item.getTextoNombreProcedencia());
				dataJSON.put(itemJSON);
			}
			rootJSON.put("success", true);
			rootJSON.put("data",dataJSON);
			rootJSON.put("totalCount", filterUtil.getTotalCount() );
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
	@RequestMapping("listProceso")
	public  void listProceso(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final String method = "listProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	List<SCGProcesoEntity> list = this.registroService.listProceso();
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	
			for (SCGProcesoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoNombre", item.getTextoNombre());
				dataJSON.put(itemJSON);
			}
			
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
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
	@RequestMapping("listDefinicionProceso")
	public  void listDefinicionProceso(HttpServletRequest request,HttpServletResponse response, SCGDefinicionProcesoEntity entity) throws Exception{
		final String method = "listDefinicionProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	List<SCGDefinicionProcesoEntity> list = this.registroService.listDefinicionProceso(entity);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	for (SCGDefinicionProcesoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoNombre", item.getTextoNombre());
				dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
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
	@RequestMapping("getSolicitudProceso")
	public  void getSolicitudProceso(HttpServletRequest request,HttpServletResponse response, SCGSolicitudProcesoEntity entity) throws Exception{
		final String method = "getSolicitudProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		FilterUtil filterUtil = new FilterUtil();
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		UNALMLogger.trace(log, method,"getCodigo " + entity.getCodigo());
		UNALMLogger.trace(log, method,"getTextoAsunto " + entity.getTextoAsunto());
		UNALMLogger.trace(log, method,"getTextoNombreProcedencia " + entity.getTextoNombreProcedencia());
		UNALMLogger.trace(log, method,"getTextoNombreRemitente " + entity.getTextoNombreRemitente());
		UNALMLogger.trace(log, method,"getTextoNumeroDocumento " + entity.getTextoNumeroDocumento());
		UNALMLogger.trace(log, method,"getTextoObservacion " + entity.getTextoObservacion());
		JSONObject rootJSON = new JSONObject();

		try {
			SCGFlujoProcesoEntity item = this.registroService.getForUpdateSolicitudProceso(entity);
			UNALMLogger.trace(log, method, "item: " + item);
			JSONObject itemJSON = new JSONObject();
//			itemJSON.put("codigo", item.getCodigo());
			
			if(item.getSolicitudProceso() != null){
				itemJSON.put("codigo", item.getSolicitudProceso().getCodigo());
				SCGSolicitudProcesoEntity solicitudProceso = item.getSolicitudProceso();
//				if(!TypesUtil.isEmptyString(solicitudProceso.getTextoNumeroDocumento())){
				UNALMLogger.trace(log, method, "item.getTextoNumeroDocumento() "+solicitudProceso.getTextoNumeroDocumento());
				itemJSON.put("textoNumeroDocumento",solicitudProceso.getTextoNumeroDocumento());	
//				}
				itemJSON.put("textoAsunto", solicitudProceso.getTextoAsunto());
				itemJSON.put("textoNombreProcedencia", solicitudProceso.getTextoNombreProcedencia());
				itemJSON.put("textoNombreRemitente", solicitudProceso.getTextoNombreRemitente());
				itemJSON.put("textoObservacion", solicitudProceso.getTextoObservacion());
				itemJSON.put("codigoAsociacion", solicitudProceso.getCodigoAsociacion());
//				itemJSON.put("fechaInicio", solicitudProceso.getFechaInicio());
				if(solicitudProceso.getFechaInicio() != null){
					UNALMLogger.trace(log, method, "fechaInicio format "+new SimpleDateFormat("d/MM/Y hh:mm:ss aaa").format(solicitudProceso.getFechaInicio()));
					itemJSON.put("fechaInicio", new SimpleDateFormat("dd/MM/Y h:mm:ss aaa").format(solicitudProceso.getFechaInicio()));
//					itemJSON.put("fechaFinal", solicitudProceso.getFechaFinal());
				}
			
				if(solicitudProceso.getFechaFinal() != null){
					UNALMLogger.trace(log, method, "fechaFinal format "+new SimpleDateFormat("d/MM/Y hh:mm:ss aaa").format(solicitudProceso.getFechaFinal()));
					itemJSON.put("fechaFinal", new SimpleDateFormat("dd/MM/Y h:mm:ss aaa").format(solicitudProceso.getFechaFinal()));
				}

				if(solicitudProceso.getTipoTramiteDocumento() != null){
					UNALMLogger.trace(log, method, "tipoTramiteDocumento.codigo "+solicitudProceso.getTipoTramiteDocumento().getCodigo());
					itemJSON.put("tipoTramiteDocumento.codigo", solicitudProceso.getTipoTramiteDocumento().getCodigo());
				}
				if(solicitudProceso.getSeguridadUsuario() != null){
					itemJSON.put("seguridadUsuario.codigo", solicitudProceso.getSeguridadUsuario().getCodigo());
				}
			}
			if(item.getDefinicionProceso() != null){
				if(item.getDefinicionProceso().getProceso() != null){
					SCGProcesoEntity proceso = item.getDefinicionProceso().getProceso();
//					if(proceso.getCodigo() != null){
					UNALMLogger.trace(log, method, "proceso.codigo " + proceso.getCodigo());
					itemJSON.put("proceso.codigo", proceso.getCodigo());
//					}
				}				
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
	@RequestMapping("saveSolicitudProceso")
	public void saveSolicitudProceso(HttpServletRequest request, HttpServletResponse response, SCGSolicitudProcesoEntity entity)
			throws Exception {

		final String method = "saveSolicitudProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		Long codigoSeguridadUsuario = TypesUtil.getDefaultLong(request.getParameter("seguridadUsuario.codigo"));
		Long codigoProceso = TypesUtil.getDefaultLong(request.getParameter("proceso.codigo"));
		Long codigoResponsable = TypesUtil.getDefaultLong(request.getParameter("responsable.codigo"));
		UNALMLogger.trace(log, method, "codigoSeguridadUsuario: " + codigoSeguridadUsuario);
		UNALMLogger.trace(log, method, "codigoProceso: " + codigoProceso);
		UNALMLogger.trace(log, method, "codigoResponsable: " + codigoResponsable);
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		UNALMLogger.trace(log, method, "entity.getTextoAsunto(): " + entity.getTextoAsunto());
		UNALMLogger.trace(log, method, "entity.getTextoNombreProcedencia(): " + entity.getTextoNombreProcedencia());
		UNALMLogger.trace(log, method, "entity.getTextoNombreRemitente(): " + entity.getTextoNombreRemitente());
		UNALMLogger.trace(log, method, "entity.getTextoNumeroDocumento(): " + entity.getTextoNumeroDocumento());
		UNALMLogger.trace(log, method, "entity.getTextoObservacion(): " + entity.getTextoObservacion());
		UNALMLogger.trace(log, method, "entity.getFechaInicio(): " + entity.getFechaInicio());
//		UNALMLogger.trace(log, method, "fechaInicio Formato "
//				+new SimpleDateFormat("Y/m/d hh:mm:ss aaa").format(entity.getFechaInicio()));
		UNALMLogger.trace(log, method, "entity.getFechaFinal(): " + entity.getFechaFinal());
//		UNALMLogger.trace(log, method, "fechaInicio Formato "
//				+new SimpleDateFormat("Y/m/d hh:mm:ss aaa").format(entity.getFechaFinal()));
		if(entity.getTipoTramiteDocumento() != null){
			UNALMLogger.trace(log, method, "entity.getTipoTramiteDocumento().getCodigo(): " + entity.getTipoTramiteDocumento().getCodigo());
		}
		try {
//			entity.setFlagEliminado("1");
			String message = "";
			message = this.registroService.validateDuplicate(entity);
			if(message.length()>0){
				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", message);
				return;
			}
			
			message = this.registroService.validateEdit(entity, codigoProceso);			
			if(message.length()>0){
				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", message);
				return;
			}
			
			UNALMLogger.trace(log, method, "before save");
			SCGSolicitudProcesoEntity result = this.registroService.saveSolicitudProceso(entity);
			UNALMLogger.trace(log, method, "after save");
			UNALMLogger.trace(log, method, "result " + result);
			UNALMLogger.trace(log, method, "result.getCodigo() " + result.getCodigo());
			
//		if(entity.getCodigo() == null){
//			SCGDefinicionProcesoEntity definicionProceso = new SCGDefinicionProcesoEntity();
//			SeguridadUsuarioEntity seguridadUsuario = new SeguridadUsuarioEntity();
//			SCGFlujoProcesoEntity flujoProceso = new SCGFlujoProcesoEntity();
//			SCGResponsableEntity responsable = new SCGResponsableEntity();
//			responsable.setCodigo(codigoResponsable);
//			definicionProceso.setProceso(new SCGProcesoEntity());
//			definicionProceso.getProceso().setCodigo(codigoProceso);
//			definicionProceso.setFlagInicio("1");
//			seguridadUsuario.setCodigo(codigoSeguridadUsuario);
//			flujoProceso.setDefinicionProceso(definicionProceso);
//			flujoProceso.setSeguridadUsuario(seguridadUsuario);
//			flujoProceso.setResponsable(responsable);
//			this.registroService.saveFlujoProceso(flujoProceso, result);
//		}
			
			SCGDefinicionProcesoEntity definicionProceso = new SCGDefinicionProcesoEntity();
			SeguridadUsuarioEntity seguridadUsuario = new SeguridadUsuarioEntity();
			SCGFlujoProcesoEntity flujoProceso = new SCGFlujoProcesoEntity();
			SCGResponsableEntity responsable = new SCGResponsableEntity();
			responsable.setCodigo(codigoResponsable);
			definicionProceso.setProceso(new SCGProcesoEntity());
			definicionProceso.getProceso().setCodigo(codigoProceso);
			definicionProceso.setFlagInicio("1");
			seguridadUsuario.setCodigo(codigoSeguridadUsuario);
			flujoProceso.setDefinicionProceso(definicionProceso);
			flujoProceso.setSeguridadUsuario(seguridadUsuario);
			flujoProceso.setResponsable(responsable);
			
			boolean validateTipoProceso = this.registroService.isProcesoPorDefinir(entity, codigoProceso);
			UNALMLogger.trace(log, method, "validateTipoProceso " + validateTipoProceso);
			UNALMLogger.trace(log, method, "entity.getCodigo() " + entity.getCodigo());
			if(entity.getCodigo() == null ){
				this.registroService.saveFlujoProceso(flujoProceso, result);
			}else if(validateTipoProceso ){
				this.registroService.saveFlujoProceso(flujoProceso, result);
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Grabado exitosamente,no podrá editar el proceso asignado y su responsable");
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
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGSolicitudProcesoEntity entity)throws Exception {
		final String method = "delete";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			String message = "";
			message = this.registroService.validateDelete(entity);
			if(message.length()>0){
				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", message);
				return;
			}
			this.registroService.delete(entity);;
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
	
	@RequestMapping("desestimado")
	public void desestimado(HttpServletRequest request, HttpServletResponse response, SCGSolicitudProcesoEntity entity)throws Exception {
		final String method = "desestimado";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			this.registroService.desestimado(entity);;
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
	public ModelAndView excel(HttpServletRequest request,HttpServletResponse response, SCGSolicitudProcesoEntity entity) throws IOException {
		final String method = "excel";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		String testoNumeroEscrito = request.getParameter("query");
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		FilterUtil filterUtil = new FilterUtil();
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
//		entity.setTextoNumeroDocumento(testoNumeroEscrito);
		entity.setTextoNombreMatch(testoNumeroEscrito);

		List<SCGSolicitudProcesoEntity> list = this.registroService.listSolicitudProceso(entity, filterUtil);
		UNALMLogger.trace(log, method, "list.size(): " + list.size());
		
		List<List<String>> masterList = this.registroExcelService.reportValue(list);

		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, masterList, "ReporteRegistro");
		ExcelUtils.generateReport(response, workBook, "PadronRegistro");
		
		UNALMLogger.exit(log, method);
		return null;
	}
	
}

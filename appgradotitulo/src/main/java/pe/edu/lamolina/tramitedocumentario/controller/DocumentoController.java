package pe.edu.lamolina.tramitedocumentario.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.lamolina.gradotitulo.controller.GradoTituloController;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.tramitedocumentario.entity.SCGArchivoTramiteDocumento;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.service.DocumentoService;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("tramiteDocumentario/documento/*")
@Controller
public class DocumentoController {

	private final static Logger log = Logger.getLogger(GradoTituloController.class);
	
	@Autowired
	private DocumentoService documentoService;
	
	@RequestMapping("load")
	public String load() {
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "tramiteDocumentario/documento/loadView";
	}
	@RequestMapping("listArchivoTramiteDocumento")
	public  void listArchivoTramiteDocumento(HttpServletRequest request,HttpServletResponse response, SCGArchivoTramiteDocumento entity) throws Exception{
		final String method = "listArchivoTramiteDocumento";
		final String params = "SCGArchivoTramiteDocumento entity";
		
		UNALMLogger.entry(log, method, params,new Object[]{entity});
		
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
		
		try {
			JSONArray dataJSON = new JSONArray();   
			
			UNALMLogger.trace(log, method, "item.getCodigo() "+entity.getCodigo());
			UNALMLogger.trace(log, method, "item.getTextoNombreArchivo() "+entity.getTextoNombreArchivo());
	
			List<SCGArchivoTramiteDocumento> list = this.documentoService.listArchivoTramiteDocumento(entity, filterUtil);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			
			for(SCGArchivoTramiteDocumento item : list) {
				JSONObject itemJSON = new JSONObject();
				
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoAsunto", item.getTextoAsunto());
				itemJSON.put("textoDetalle", item.getTextoDetalle());
				itemJSON.put("textoNombreArchivo", item.getTextoNombreArchivo());
				itemJSON.put("textoNombreRegistro", item.getTextoNombreRegistro());
				itemJSON.put("textoRuta", item.getTextoRuta());
				itemJSON.put("fechaAgregar", TypesUtil.getDefaultString(item.getFechaAgregar(), "dd-MM-yyyy"));
				
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
	@RequestMapping("getArchivoTramiteDocumento")
	public  void getArchivoTramiteDocumento(HttpServletRequest request,HttpServletResponse response, SCGArchivoTramiteDocumento entity) throws Exception{
		final String method = "getFlujoProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGArchivoTramiteDocumento entity";
		
		UNALMLogger.entry(log, method, params,new Object[]{entity});
		
		JSONObject rootJSON = new JSONObject();

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		UNALMLogger.trace(log, method,"getCodigo " + entity.getCodigo());
		
		
		try {
			JSONObject itemJSON = new JSONObject();
			
			SCGArchivoTramiteDocumento item = this.documentoService.getForUpdateArchivoTramiteDocumento(entity);
			UNALMLogger.trace(log, method, "item: " + item);
	
			itemJSON.put("codigo", item.getCodigo());
			itemJSON.put("textoAsunto", item.getTextoAsunto());
			itemJSON.put("textoDetalle", item.getTextoDetalle());
			itemJSON.put("textoNombreArchivo", item.getTextoNombreArchivo());
			itemJSON.put("textoRuta", item.getTextoRuta());
			itemJSON.put("textoNombreRegistro", item.getTextoNombreRegistro());
			itemJSON.put("fechaAgregar", TypesUtil.getDefaultString(item.getFechaAgregar(), "dd-MM-yyyy"));
			
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
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGArchivoTramiteDocumento entity)throws Exception {
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
//			String message = "";
//			message = this.registroService.validateDelete(entity);
//			if(message.length()>0){
//				jsonObjectRoot.put("success", false);
//				jsonObjectRoot.put("message", message);
//				return;
//			}
			
			this.documentoService.delete(entity);
			
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
	
	@RequestMapping("save")
	public  void save(HttpServletRequest request,HttpServletResponse response,SCGArchivoTramiteDocumento entity) throws Exception{
		final String method = "save";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity,SCGAdjuntoEntity adjunto";
		
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		
		try {
//			String message = "";
//
//			message = this.documentoService.validateDuplicate(entity);			
//			if(message.length()>0){
//				jsonObjectRoot.put("success", false);
//				jsonObjectRoot.put("message", message);
//				return;
//			}
			
			String message = "";
			message = this.documentoService.validate(entity);			
			
			if(message.length()>0){
				jsonObjectRoot.put("success", false);
				jsonObjectRoot.put("message", message);
				return;
			}
			
			this.documentoService.save(entity);
			
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
	}
}

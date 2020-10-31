package pe.edu.lamolina.gradotitulo.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.service.FacultadService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;

@RequestMapping("facultades/*")
@Controller
public class FacultadController {
	
	private final static Logger log = Logger.getLogger(FacultadController.class);

	@Autowired
	private FacultadService facultadService;

	
	@RequestMapping("load")
	public  String load(){
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "facultades/loadView";
	}
	@RequestMapping("getFacultadList")
	public  void getFacultadList(HttpServletRequest request,HttpServletResponse response,SCGFacultadEntity entity) throws Exception{
		final String method = "getFacultadList";
		final String params = "SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method," entity.getCodigo() "+ entity.getCodigo());
		UNALMLogger.trace(log, method," entity.getTextoNombreEspanol() "+ entity.getTextoNombreEspanol());
		UNALMLogger.trace(log, method," entity.getCodigoExterno() "+ entity.getCodigoExterno());
		UNALMLogger.trace(log, method," entity.getFlagEstado() "+ entity.getFlagEstado());
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGFacultadEntity> list=this.facultadService.getListFacultad(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGFacultadEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("codigoExterno", item.getCodigoExterno());
        		itemJSON.put("textonNombreAbreviado", item.getTextoNombreAbreviado());
        		itemJSON.put("textoNombreEspanol", item.getTextoNombreEspanol());
        		itemJSON.put("textoNombreIngles", item.getTextoNombreIngles());
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
	@RequestMapping("getFacultad")
	public  void getFacultad(HttpServletRequest request,HttpServletResponse response,SCGFacultadEntity entity) throws Exception{
		final String method = "getFacultad";
		final String params = "SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method," entity.getCodigo() "+ entity.getCodigo());
		UNALMLogger.trace(log, method," entity.getTextoNombreEspanol() "+ entity.getTextoNombreEspanol());
		UNALMLogger.trace(log, method," entity.getCodigoExterno() "+ entity.getCodigoExterno());
		UNALMLogger.trace(log, method," entity.getFlagEstado() "+ entity.getFlagEstado());
        try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	SCGFacultadEntity facultad=this.facultadService.getForUpdate(entity);

        	UNALMLogger.trace(log, method,"facultadService: "+facultadService);

        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", facultad.getCodigo());
        		itemJSON.put("codigoExterno", facultad.getCodigoExterno());
        		itemJSON.put("textonNombreAbreviado", facultad.getTextoNombreAbreviado());
        		itemJSON.put("textoNombreEspanol", facultad.getTextoNombreEspanol());
        		itemJSON.put("textoNombreIngles", facultad.getTextoNombreIngles());
        		itemJSON.put("textoNombreAbreviado", facultad.getTextoNombreAbreviado());
        		itemJSON.put("flagEstado", facultad.getFlagEstado());
        	
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
	@RequestMapping("getEspecialidad")
	public  void getEspecialidad(HttpServletRequest request,HttpServletResponse response,SCGEspecialidadEntity entity) throws Exception{
		final String method = "getEspecialidad";
		final String params = "SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method," entity.getCodigo() "+ entity.getCodigo());
        try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	SCGEspecialidadEntity facultad=this.facultadService.getForUpdateEspecialidad(entity);

        	UNALMLogger.trace(log, method,"facultad: "+facultad);

        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", facultad.getCodigo());
        		itemJSON.put("codigoExterno", facultad.getCodigoExterno());
        		itemJSON.put("textonNombreAbreviado", facultad.getTextoNombreAbreviado());
        		itemJSON.put("textoNombreEspanol", facultad.getTextoNombreEspanol());
        		itemJSON.put("facultad.codigo", facultad.getFacultad().getCodigo());
        		itemJSON.put("tipoEspecialidad.codigo", facultad.getTipoEspecialidad().getCodigo());
        		itemJSON.put("textoNombreIngles", facultad.getTextoNombreIngles());
        		itemJSON.put("flagEstado", facultad.getFlagEstado());
        	
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
	@RequestMapping("getEspecialidadList")
	public  void getEspecialidadList(HttpServletRequest request,HttpServletResponse response,SCGEspecialidadEntity entity) throws Exception{
		final String method = "getEspecialidadList";
		final String params = "SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method," entity.getCodigo() "+ entity.getCodigo());
		UNALMLogger.trace(log, method," entity.getTextoNombreEspanol() "+ entity.getTextoNombreEspanol());
		UNALMLogger.trace(log, method," entity.getCodigoExterno() "+ entity.getCodigoExterno());
		//UNALMLogger.trace(log, method," entity.getFacultad().getTextoNombreEspanol() "+ entity.getFacultad().getTextoNombreEspanol());
		UNALMLogger.trace(log, method," entity.getFlagEstado() "+ entity.getFlagEstado());
		
		
		UNALMLogger.trace(log, method," entity.getFacultad(): "+ entity.getFacultad());
		if(entity.getFacultad() !=null){
			UNALMLogger.trace(log, method," entity.getFacultad().getCodigo(): "+ entity.getFacultad().getCodigo());
			UNALMLogger.trace(log, method," entity.getFacultad().getTextoNombreEspanol(): "+ entity.getFacultad().getTextoNombreEspanol());
		}
		
		UNALMLogger.trace(log, method," entity.getTipoEspecialidad(): "+ entity.getTipoEspecialidad());
		if(entity.getTipoEspecialidad() !=null){
			UNALMLogger.trace(log, method," entity.getTipoEspecialidad().getTextoNombreEspanol(): "+ entity.getTipoEspecialidad().getTextoNombreEspanol());
		}
		
		try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGEspecialidadEntity> list=this.facultadService.getListEspecialidad(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGEspecialidadEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("codigoExterno", item.getCodigoExterno());
        		itemJSON.put("textonNombreAbreviado", item.getTextoNombreAbreviado());
        		itemJSON.put("textoNombreEspanol", item.getTextoNombreEspanol());
        		itemJSON.put("textoNombreEspanolFacutald", item.getFacultad().getTextoNombreEspanol());
        		itemJSON.put("textoNombreIngles", item.getTextoNombreIngles());
        		itemJSON.put("textoNombreDenominacion", item.getTextoNombreDenominacion());
        		itemJSON.put("textoNombreBachiller", item.getTextoNombreBachiller());
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
	
	@RequestMapping("getTipoEspecialidadList")
	public  void getTipoEspecialidadList(HttpServletRequest request,HttpServletResponse response,SCGTipoEspecialidadEntity entity) throws Exception{
		final String method = "getTipoEspecialidadList";
		final String params = "SCGTipoEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGTipoEspecialidadEntity> list=this.facultadService.getListTipoEspecialidad(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGTipoEspecialidadEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("textoNombreEspanol", item.getTextoNombreEspanol());
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
	
	@RequestMapping("save")
	public  void save(HttpServletRequest request,HttpServletResponse response,SCGFacultadEntity entity) throws Exception{

		final String method = "save";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		try {
			
			entity.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			
			UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
			UNALMLogger.trace(log, method, "entity.getTextoNombreAbreviado() "+entity.getTextoNombreAbreviado());
			UNALMLogger.trace(log, method, "entity.getFlagEstado() "+entity.getFlagEstado());
			UNALMLogger.trace(log, method, "entity.getTextoNombreEspanol() "+entity.getTextoNombreEspanol());
			UNALMLogger.trace(log, method, "entity.getTextoNombreIngles() "+entity.getTextoNombreIngles());
			UNALMLogger.trace(log, method, "entity.getCodigoExterno() "+entity.getCodigoExterno());
			this.facultadService.save(entity);
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
	

	@RequestMapping("saveEspecialidad")
	public  void saveEspecialidad(HttpServletRequest request,HttpServletResponse response,SCGEspecialidadEntity entity) throws Exception{

		final String method = "saveEspecialidad";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		try {
			
			entity.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
			UNALMLogger.trace(log, method, "entity.getTextoNombreEspanol() "+entity.getTextoNombreEspanol());
			UNALMLogger.trace(log, method, "entity.getTextoNombreIngles() "+entity.getTextoNombreIngles());
			UNALMLogger.trace(log, method, "entity.getCodigoExterno() "+entity.getCodigoExterno());
			
			if(entity.getFacultad() != null){
				UNALMLogger.trace(log, method, "entity.getFacultad().getCodigo() "+entity.getFacultad().getCodigo());
			}
			
			if(entity.getTipoEspecialidad() != null){
				UNALMLogger.trace(log, method, "entity.getTipoEspecialidad().getCodigo() "+entity.getTipoEspecialidad().getCodigo());
			}
			this.facultadService.saveEspecialidad(entity);
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
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGFacultadEntity entity)
			throws Exception {
		final String method = "delete";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGFacultadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());

		try {
			facultadService.delete(entity);
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
	
	@RequestMapping("deleteEspecialidad")
	public void deleteEspecialidad(HttpServletRequest request, HttpServletResponse response, SCGEspecialidadEntity entity)
			throws Exception {
		final String method = "deleteEspecialidad";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());

		try {
			facultadService.deleteEspecialidad(entity);
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

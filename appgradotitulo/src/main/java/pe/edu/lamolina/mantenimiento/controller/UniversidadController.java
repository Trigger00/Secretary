package pe.edu.lamolina.mantenimiento.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.lamolina.gradotitulo.controller.GradoTituloController;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoUniversidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.gradotitulo.service.AgendaGrupoService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.service.UniversidadService;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("mantenimiento/universidad/*")
@Controller
public class UniversidadController {

	private final static Logger log = Logger.getLogger(GradoTituloController.class);

	@Autowired
	private UniversidadService universidadService;

	@RequestMapping("load")
	public String load() {
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "mantenimiento/universidad/loadView";
	}
	@RequestMapping("getUniversidad")
	public  void getUniversidad(HttpServletRequest request,HttpServletResponse response, SCGUniversidadEntity entity) throws Exception{
		final String method = "getUniversidad";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	SCGUniversidadEntity universidad = this.universidadService.getForUpdateUnverisdad( entity );
        	UNALMLogger.trace(log, method,"universidad: " + universidad );

        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", universidad.getCodigo());
        		itemJSON.put("textoNombre", universidad.getTextoNombre());
        		itemJSON.put("pais.codigo", universidad.getPais().getCodigo());
        		itemJSON.put("tipoUniversidad.codigo", universidad.getTipoUniversidad().getCodigo());
        		itemJSON.put("textoNombreAbreviado", universidad.getTextoNombreAbreviado());
        		itemJSON.put("textoSiglas", universidad.getTextoSiglas());
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
	@RequestMapping("getTipoUniversidadList")
	public void getTipoUniversidadList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String method = "getTipoUniversidadList";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		try {
			JSONArray dataJSON = new JSONArray();
			List<SCGTipoUniversidadEntity> list = this.universidadService.listTipoUniversidad();
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			for (SCGTipoUniversidadEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoNombre", item.getTextoNombre());
				dataJSON.put(itemJSON);
			}
			rootJSON.put("success", true);
			rootJSON.put("data", dataJSON);
			rootJSON.put("totalCount", list.size());
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Error al listar la informacion", e);
			rootJSON.put("success", false);
			rootJSON.put("data", new Object[] {});
			rootJSON.put("totalCount", 0);
			rootJSON.put("message", e.getMessage());
		} finally {
			String jsonString = rootJSON.toString();
			UNALMLogger.trace(log, method, "jsonString: " + jsonString);
			out.print(jsonString);
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);

	}
	@RequestMapping("getUniversidadList")
	public void getUniversidadList(HttpServletRequest request, 
			HttpServletResponse response,
			SCGUniversidadEntity entity) throws Exception {
		final String method = "getUniversidadLists";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGUniversidadEntity entity";
		FilterUtil filterUtil = new FilterUtil();
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		try {
			JSONArray dataJSON = new JSONArray();
			List<SCGUniversidadEntity> list = this.universidadService.listUniversidad( entity, filterUtil);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			for (SCGUniversidadEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoNombre", item.getTextoNombre());
				itemJSON.put("textoNombreAbreviado", item.getTextoNombreAbreviado());
				itemJSON.put("textoNombreSiglas", item.getTextoSiglas());
				if( item.getPais() != null ){
					itemJSON.put("pais", item.getPais().getTextoNombre());
				}
				if( item.getTipoUniversidad() != null ){
					itemJSON.put("tipoUniversidad", item.getTipoUniversidad().getTextoNombre());
				}
				dataJSON.put(itemJSON);
			}
			rootJSON.put("success", true );
			rootJSON.put("data", dataJSON );
			rootJSON.put("totalCount", filterUtil.getTotalCount() );
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Error al listar la informacion", e);
			rootJSON.put("success", false);
			rootJSON.put("data", new Object[] {});
			rootJSON.put("totalCount", 0);
			rootJSON.put("message", e.getMessage());
		} finally {
			String jsonString = rootJSON.toString();
			UNALMLogger.trace(log, method, "jsonString: " + jsonString);
			out.print(jsonString);
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);

	}
	@RequestMapping("saveUniversidad")
	public void saveUniversidad(HttpServletRequest request, HttpServletResponse response, SCGUniversidadEntity entity)
			throws Exception {

		final String method = "saveUniversidad";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();

		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			entity.setFlagEliminado("1");
			this.universidadService.saveUniversidad( entity );
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
}

package pe.edu.lamolina.mantenimiento.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.service.CicloService;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("mantenimiento/ciclo/*")
@Controller
public class CicloController {
	
	private final static Logger log = Logger.getLogger(CicloController.class);

	@Autowired
	private CicloService cicloService;
	
	@RequestMapping("load")
	public String load() {
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "mantenimiento/ciclo/loadView";
	}
	
	@RequestMapping("getCiclo")
	public  void getCiclo(HttpServletRequest request,HttpServletResponse response, SCGCicloEntity entity) throws Exception{
		final String method = "getCiclo";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	
        	SCGCicloEntity ciclo = this.cicloService.getForUpdateCiclo(entity);
        	UNALMLogger.trace(log, method,"ciclo: " + ciclo );
    		JSONObject itemJSON = new JSONObject();
    		itemJSON.put("codigo", ciclo.getCodigo());
    		itemJSON.put("textoCiclo", ciclo.getTextoCiclo());
    		itemJSON.put("textoSemestre", ciclo.getTextoSemestre());
    		itemJSON.put("textoNombreEspanol", ciclo.getTextoNombreEspanol());
			itemJSON.put("fechaInicioCiclo", TypesUtil.getDefaultString(ciclo.getFechaInicioCiclo(), "dd-MM-yyyy"));;
			itemJSON.put("fechaFinalCiclo", TypesUtil.getDefaultString(ciclo.getFechaFinalCiclo(), "dd-MM-yyyy"));;
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
	public void getCicloList(HttpServletRequest request, HttpServletResponse response, SCGCicloEntity entity) throws Exception {
		final String method = "getCicloList";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGCicloEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{entity,response,request});

		FilterUtil filterUtil = new FilterUtil();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		UNALMLogger.trace(log, method, "limit = "+limit);
		UNALMLogger.trace(log, method, "start = "+start);
		UNALMLogger.trace(log, method, "page = "+page);
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		
		try {
			
			JSONArray dataJSON = new JSONArray();
			List<SCGCicloEntity> list = this.cicloService.listCiclo( entity, filterUtil);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			for (SCGCicloEntity item : list) {
				
				JSONObject itemJSON = new JSONObject();
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("textoNombreEspanol", item.getTextoNombreEspanol());
	    		itemJSON.put("textoCiclo", item.getTextoCiclo());
	    		itemJSON.put("textoSemestre", item.getTextoSemestre());
				itemJSON.put("fechaInicioCiclo", TypesUtil.getDefaultString(item.getFechaInicioCiclo(), "dd-MM-yyyy"));;
				itemJSON.put("fechaFinalCiclo", TypesUtil.getDefaultString(item.getFechaFinalCiclo(), "dd-MM-yyyy"));;
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
	@RequestMapping("saveCiclo")
	public void saveCiclo(HttpServletRequest request, HttpServletResponse response, SCGCicloEntity entity)
			throws Exception {

		final String method = "saveCiclo";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGCicloEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity }); 
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();

		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		UNALMLogger.trace(log, method, "entity.getTextoCiclo(): " + entity.getTextoCiclo());
		UNALMLogger.trace(log, method, "entity.getTextoNombreEspanol(): " + entity.getTextoNombreEspanol());
		UNALMLogger.trace(log, method, "entity.getTextoSemestre(): " + entity.getTextoSemestre());
		UNALMLogger.trace(log, method, "entity.getFechaInicioCiclo(): " + entity.getFechaInicioCiclo());
		UNALMLogger.trace(log, method, "entity.getFechaFinalCiclo(): " + entity.getFechaFinalCiclo());
		try { 
			entity.setFlagEliminado("1");
			this.cicloService.saveCiclo( entity );
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

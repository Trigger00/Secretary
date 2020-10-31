package pe.edu.lamolina.gradotitulo.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.service.AgendaGrupoService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;

@RequestMapping("agendaGrupo/*")
@Controller
public class AgendaGrupoController {

	private final static Logger log = Logger.getLogger(GradoTituloController.class);

	@Autowired
	private AgendaGrupoService agendaGrupoService;

	@RequestMapping("load")
	public String load() {
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "agendaGrupo/loadView";
	}

	@RequestMapping("getAgendaGrupoList")
	public void getAgendaGrupoList(HttpServletRequest request, HttpServletResponse response,
			SCGAgendaGrupoEntity entity) throws Exception {
		final String method = "getAgendaGrupoList";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();

		UNALMLogger.trace(log, method, "entity.getCodigo() " + entity.getCodigo());
		try {
			JSONArray dataJSON = new JSONArray();
			List<SCGAgendaGrupoEntity> list = this.agendaGrupoService.getListAgendaGrupo(entity);
			UNALMLogger.trace(log, method, "list: " + list.size());
			for (SCGAgendaGrupoEntity item : list) {
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

	@RequestMapping("getAgendaGrupo")
	public void getAgendaGrupo(HttpServletRequest request, HttpServletResponse response, SCGAgendaGrupoEntity entity)
			throws Exception {
		final String method = "getAgendaGrupo";
		final String params = "SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();

		UNALMLogger.trace(log, method, " entity.getCodigo() " + entity.getCodigo());
		try {
			
			SCGAgendaGrupoEntity agendaGrupo = this.agendaGrupoService.getForUpdateAgendaGrupo(entity);
			UNALMLogger.trace(log, method, "agendaGrupo: " + agendaGrupo);
			JSONObject itemJSON = new JSONObject();
			itemJSON.put("codigo", agendaGrupo.getCodigo());
			itemJSON.put("textoNombre", agendaGrupo.getTextoNombre());
			rootJSON.put("success", true);
			rootJSON.put("data", itemJSON);
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

	@RequestMapping("saveAgendaGrupo")
	public void saveAgendaGrupo(HttpServletRequest request, HttpServletResponse response, SCGAgendaGrupoEntity entity)
			throws Exception {

		final String method = "saveAgendaGrupo";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();

		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			entity.setFlagEliminado("1");
			this.agendaGrupoService.saveAgendaGrupo(entity);
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
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGAgendaGrupoEntity entity)
			throws Exception {
		final String method = "delete";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());

		try {
			this.agendaGrupoService.delete(entity);
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

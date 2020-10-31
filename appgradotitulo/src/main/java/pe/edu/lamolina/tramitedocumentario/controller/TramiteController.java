package pe.edu.lamolina.tramitedocumentario.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGAtributosEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGValoresEntity;
import pe.edu.lamolina.tramitedocumentario.report.service.TramiteExcelService;
import pe.edu.lamolina.tramitedocumentario.service.TramiteService;
import pe.edu.lamolina.util.ExcelUtils;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("tramiteDocumentario/tramite/*")
@Controller
public class TramiteController {
	private final static Logger log = Logger.getLogger(TramiteController.class);
	
	@Autowired
	private TramiteService tramiteService;
	
	@Autowired
	private TramiteExcelService TramiteExcelService;

	@RequestMapping("load")
	public String load() {
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "tramiteDocumentario/tramite/loadView";
	}

	@RequestMapping("listFlujoProceso")
	public  void listFlujoProceso(HttpServletRequest request,HttpServletResponse response, SCGFlujoProcesoEntity entity) throws Exception{
		final String method = "listFlujoProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGFlujoProcesoEntity entity";
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
	
		try {
        	JSONArray dataJSON = new JSONArray(); 
        	
        	SeguridadUsuarioEntity usuario = (SeguridadUsuarioEntity)request.getSession().getAttribute(ApplicationConstant.USER_SESSION_ID);
			UNALMLogger.trace(log, method, "usuario: " + usuario);
			if(usuario != null){
				UNALMLogger.trace(log, method, "usuario.getCodigo(): " + usuario.getCodigo());
				UNALMLogger.trace(log, method, "usuario.getTextoNombre(): " + usuario.getTextoNombre());

			}
        	entity.setSeguridadUsuario(usuario);
			
        	List<SCGFlujoProcesoEntity> list = this.tramiteService.listFlujoProceso(entity, filterUtil);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			
        	for (SCGFlujoProcesoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				UNALMLogger.trace(log, method, "item.getCodigo() "+item.getCodigo());
				UNALMLogger.trace(log, method, "fechaIncio "+item.getFechaInicio());
				UNALMLogger.trace(log, method, "fechaFinal "+item.getFechaFinal());
				
				if(item.getSolicitudProceso() != null){
					UNALMLogger.trace(log, method, " item.getSolicitudProceso().getFlagCandado() " +  item.getSolicitudProceso().getFlagCandado());
					
					itemJSON.put("solicitudProceso", item.getSolicitudProceso().getTextoNumeroDocumento());
					itemJSON.put("solicitudProcesoFlagCandado", item.getSolicitudProceso().getFlagCandado());
					itemJSON.put("tipoColor", item.getTextoTipoColor());
					

				}
				
				if(item.getDefinicionProceso() != null){
					itemJSON.put("definicionProceso", item.getDefinicionProceso().getTextoNombre());
					itemJSON.put("codigoDefinicionProceso", item.getDefinicionProceso().getCodigo());
					itemJSON.put("flagFinal", item.getDefinicionProceso().getFlagFinal());
				}
				
				if(item.getResponsable() != null){
					itemJSON.put("responsable", item.getResponsable().getTextoNombre());
				}
				
				if(item.getSeguridadUsuario() != null){
					itemJSON.put("seguridadUsuario", item.getSeguridadUsuario().getTextoNombre());
				}
				
				itemJSON.put("codigo", item.getCodigo());
				itemJSON.put("flagCandado", item.getFlagCandado());
				itemJSON.put("textoRegistrosAdjuntos", item.getTextoRegistrosAdjuntos());				
				itemJSON.put("flagUsuarioValido", item.getFlagUsuaroValido());
				UNALMLogger.trace(log, method, "flagUsuarioValido: "+ item.getFlagUsuaroValido());
				
				itemJSON.put("fechaIncio", item.getFechaInicio());
				itemJSON.put("fechaFinal", item.getFechaFinal());
				
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
	
	@RequestMapping("listDecisionProceso")
	public  void listDecisionProceso(HttpServletRequest request,HttpServletResponse response, SCGDecisionProcesoEntity entity) throws Exception{
		final String method = "listDecisionProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGDecisionProcesoEntity entity";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	List<SCGDecisionProcesoEntity> list = this.tramiteService.listDecisionProceso(entity);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	
        	for (SCGDecisionProcesoEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				UNALMLogger.trace(log, method, "item.getCodigo() "+item.getCodigo());
				
				if(item.getDefinicionProcesoOrigen() != null){
					itemJSON.put("definicionProcesoOrigen", item.getDefinicionProcesoOrigen().getCodigo());
				}
				if(item.getDefinicionProcesoDestino() != null){
					itemJSON.put("definicionProcesoDestino", item.getDefinicionProcesoDestino().getCodigo());
					itemJSON.put("textoDefinicionProcesoDestino", item.getDefinicionProcesoDestino().getTextoNombre());
				}
				itemJSON.put("textoNombre", item.getTextoNombre());
				itemJSON.put("codigo", item.getCodigo());
				
				dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
//			rootJSON.put("totalCount", filterUtil.getTotalCount() );
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
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGResponsabilidadEntity entity";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	
        	List<SCGResponsabilidadEntity> list = this.tramiteService.listResponsabilidad(entity);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
        	
        	for (SCGResponsabilidadEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				UNALMLogger.trace(log, method, "item.getCodigo() "+item.getCodigo());
				UNALMLogger.trace(log, method, "item.getTextoNombre() "+item.getTextoNombre());
				

				if(item.getSeguridadUsuario() != null){
					itemJSON.put("textoNombreSeguridadUsuario", item.getSeguridadUsuario().getTextoNombre());
					itemJSON.put("codigoSeguridadUsuario", item.getSeguridadUsuario().getCodigo());
				}
				itemJSON.put("codigo", item.getCodigo());
				
				dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
//			rootJSON.put("totalCount", filterUtil.getTotalCount() );
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
	
	@RequestMapping("listAtributos")
	public  void listAtributos(HttpServletRequest request,HttpServletResponse response, SCGAtributosEntity entity) throws Exception{
		final String method = "listAtributos";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGDefinicionProcesoEntity entity";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
			JSONArray dataJSON = new JSONArray();    
			UNALMLogger.trace(log, method, "entity.getDefinicionProceso() "+entity.getDefinicionProceso());
			UNALMLogger.trace(log, method, "entity.getDefinicionProceso().getCodigo()) "+entity.getDefinicionProceso().getCodigo());
			
			List<SCGAtributosEntity> list = this.tramiteService.listAtributos(entity);
			UNALMLogger.trace(log, method, "list.size(): " + list.size());
			
			for (SCGAtributosEntity item : list) {
				JSONObject itemJSON = new JSONObject();
				UNALMLogger.trace(log, method, "item.getCodigo() "+item.getCodigo());
				
				if(item.getTipoAtributo() != null){
					UNALMLogger.trace(log, method, "tipoAtributo.textoNombre "+item.getTipoAtributo().getTextoNombre());
					UNALMLogger.trace(log, method, "tipoAtributo.codigo "+item.getTipoAtributo().getCodigo());
					
					itemJSON.put("tipoAtributo.textoNombre", item.getTipoAtributo().getTextoNombre());
					itemJSON.put("tipoAtributoCodigo", item.getTipoAtributo().getCodigo());
				}
				
				itemJSON.put("codigo", item.getCodigo());
//				
				dataJSON.put(itemJSON);
			}
			rootJSON.put("success", true);
			rootJSON.put("data",dataJSON);
//			rootJSON.put("totalCount", filterUtil.getTotalCount() );
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
	
	@RequestMapping("getFlujoProceso")
	public  void getFlujoProceso(HttpServletRequest request,HttpServletResponse response, SCGFlujoProcesoEntity entity) throws Exception{
		final String method = "getFlujoProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response, SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		UNALMLogger.trace(log, method,"getCodigo " + entity.getCodigo());
		
		JSONObject rootJSON = new JSONObject();
		
		try {
			JSONObject itemJSON = new JSONObject();
			
			SCGFlujoProcesoEntity item = this.tramiteService.getForUpdateFlujoProceso(entity);
			UNALMLogger.trace(log, method, "item: " + item);
			
			SCGFlujoProcesoEntity flujoProcesoSiguiente = this.tramiteService.getFlujoProcesoSiguiente(item);
			
			if(flujoProcesoSiguiente.getDefinicionProceso() != null){
				UNALMLogger.trace(log, method, "definicionProceso.codigo "+flujoProcesoSiguiente.getDefinicionProceso().getCodigo());
				itemJSON.put("definicionProceso.codigo", flujoProcesoSiguiente.getDefinicionProceso().getCodigo());	
					
				UNALMLogger.trace(log, method, "item.getResponsable() " + flujoProcesoSiguiente.getResponsable());
				UNALMLogger.trace(log, method, "item.getSeguridadUsuario() " + flujoProcesoSiguiente.getSeguridadUsuario());
				
				SCGResponsabilidadEntity dataByFilterResponsabilidad = new SCGResponsabilidadEntity();
				dataByFilterResponsabilidad.setResponsable(flujoProcesoSiguiente.getResponsable());
				dataByFilterResponsabilidad.setSeguridadUsuario(flujoProcesoSiguiente.getSeguridadUsuario());
				SCGResponsabilidadEntity responsabilidad = this.tramiteService.getForUpdateResponsabilidad(dataByFilterResponsabilidad);
				UNALMLogger.trace(log, method, "responsabilidad " + responsabilidad);

				if(responsabilidad != null){
					UNALMLogger.trace(log, method, "responsabilidad.codigo " + responsabilidad.getCodigo());
					itemJSON.put("responsabilidad.codigo", responsabilidad.getCodigo());
					
					if(responsabilidad.getResponsable() != null){
						
						if(responsabilidad.getResponsable().getDefinicionProceso() != null){
							SCGDefinicionProcesoEntity definicionProceso = responsabilidad.getResponsable().getDefinicionProceso();
							itemJSON.put("textoDefinicionProcesoDestino", definicionProceso.getTextoNombre());
						}
					}
				}
			}
			
			itemJSON.put("textoDetalle", item.getTextoDetalle());
			itemJSON.put("codigo", item.getCodigo());
			itemJSON.put("flagCandado", item.getFlagCandado());
			itemJSON.put("textoRegistrosAdjuntos", item.getTextoRegistrosAdjuntos());
			
			
			if(item.getDefinicionProceso() != null){
				SCGAtributosEntity dataByFilterAtribustos = new SCGAtributosEntity();
				dataByFilterAtribustos.setDefinicionProceso(new SCGDefinicionProcesoEntity());
				dataByFilterAtribustos.getDefinicionProceso().setCodigo(item.getDefinicionProceso().getCodigo());		
				
				List<SCGAtributosEntity> listAtributos = this.tramiteService.listAtributos(dataByFilterAtribustos);
				
				for(SCGAtributosEntity atributo : listAtributos){
					
					SCGValoresEntity dataByFilerValores = new SCGValoresEntity();
					dataByFilerValores.setAtributos(atributo);
					dataByFilerValores.setSolicitudProceso(item.getSolicitudProceso());
					SCGValoresEntity valor = this.tramiteService.getForUpdateValores(dataByFilerValores);
					
					if(valor != null){
						
						if(valor.getArchivoTramiteDocumento() != null){
								
							itemJSON.put(atributo.getCodigo() +".name",valor.getArchivoTramiteDocumento().getTextoNombreRegistro() );
							itemJSON.put(atributo.getCodigo() +".codigo",valor.getArchivoTramiteDocumento().getCodigo() );
						
						}
					}
					
					
				}
			}
			
//			itemJSON.put("resolucionUno","TEST 01" );
//			itemJSON.put("resolucionDos","TEST 02" );
//			itemJSON.put("resolucionTres","TEST 03" );
//			
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
	
	@RequestMapping("saveFlujoProceso")
	public void saveFlujoProceso(HttpServletRequest request, HttpServletResponse response, SCGFlujoProcesoEntity entity)
			throws Exception {

		final String method = "saveFlujoProceso";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGFlujoProcesoEntity entity";
		
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		
		Long codigoResponsabilidad = TypesUtil.getDefaultLong(request.getParameter("responsabilidad.codigo"));
		UNALMLogger.trace(log, method, "codigoResponsabilidad: " + codigoResponsabilidad);
		
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		UNALMLogger.trace(log, method, "flagCandado "+entity.getFlagCandado());

		try {
						
			SCGResponsabilidadEntity dataByFilterResponsabilidad = new SCGResponsabilidadEntity();
			dataByFilterResponsabilidad.setCodigo(codigoResponsabilidad);
			SCGResponsabilidadEntity responsabilidad  = this.tramiteService.getForUpdateResponsabilidad(dataByFilterResponsabilidad);
			
			
			entity.setResponsable(responsabilidad.getResponsable());
			entity.setSeguridadUsuario(responsabilidad.getSeguridadUsuario());

			UNALMLogger.trace(log, method, "1 entity.getCodigo(): " + entity.getCodigo());

			this.tramiteService.saveFlujoProceso(entity, request);
			
			UNALMLogger.trace(log, method, "2 entity.getCodigo(): " + entity.getCodigo());

//			this.tramiteService.saveValores(entity, request);
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
	
	@RequestMapping(value = "excel")
	public ModelAndView excel(HttpServletRequest request,HttpServletResponse response, SCGFlujoProcesoEntity entity) throws IOException {
		final String method = "excel";
		final String params = "HttpServletRequest request,HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[]{entity});
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String page = request.getParameter("page");
		
		FilterUtil filterUtil = new FilterUtil();
		Boolean isPaging = TypesUtil.isEmptyString(limit, Boolean.TRUE);
		filterUtil.setLimit(isPaging ? null : new Integer(limit));
		filterUtil.setStart(isPaging ? null : new Integer(start));
		filterUtil.setPage(isPaging ? null : new Integer(page));
		
		SeguridadUsuarioEntity usuario = (SeguridadUsuarioEntity)request.getSession().getAttribute(ApplicationConstant.USER_SESSION_ID);
		UNALMLogger.trace(log, method, "usuario: " + usuario);
		if(usuario != null){
			UNALMLogger.trace(log, method, "usuario.getCodigo(): " + usuario.getCodigo());
			UNALMLogger.trace(log, method, "usuario.getTextoNombre(): " + usuario.getTextoNombre());

		}
    	entity.setSeguridadUsuario(usuario);
		UNALMLogger.trace(log, method, "entity.getFlagCandado(): " + entity.getFlagCandado());

		
    	List<SCGFlujoProcesoEntity> list = this.tramiteService.listFlujoProceso(entity, filterUtil);
		UNALMLogger.trace(log, method, "list.size(): " + list.size());
		List<List<String>> masterList = this.TramiteExcelService.reportValue(list);
		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, masterList, "ReporteTramite");
		ExcelUtils.generateReport(response, workBook, "PadronTramite");
		
		UNALMLogger.exit(log, method);
		return null;
	}
	
}



package pe.edu.lamolina.gradotitulo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGProgramaEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.gradotitulo.service.AgendaGrupoService;
import pe.edu.lamolina.gradotitulo.service.AutoridadService;
import pe.edu.lamolina.gradotitulo.service.DatoGeneralService;
import pe.edu.lamolina.gradotitulo.service.DuplicadoService;
import pe.edu.lamolina.gradotitulo.service.GradoTituloService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.ExcelUtils;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("duplicado/*")
@Controller
public class DuplicadoController {
	
	private final static Logger log = Logger.getLogger(DuplicadoController.class);

	@Autowired
	private DuplicadoService duplicadoService;
	@Autowired
	private GradoTituloService gradoTituloService;
	@Autowired
	private DatoGeneralService datoGeneralService;
	
	@Autowired
	private AgendaGrupoService agendaGrupoService;
	
	@Autowired
	private AutoridadService autoridadService;
	
	@RequestMapping("load")
	public String load(){
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "duplicado/loadView";
	}
	
	@RequestMapping("getDuplicadoList")
	public  void getDuplicadoList(HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity) throws Exception{
		final String method = "getDuplicadoList";
		final String params = "VWSCGEstudainteRegistro entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
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
		List<Long> agendas = new ArrayList<Long>();
		
		
		if(entity.getEstudiante()!=null){
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoMatricula(): "+ entity.getEstudiante().getTextoMatricula());
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoNombreCompleto(): "+ entity.getEstudiante().getTextoNombreCompleto());
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoCodigoExternoDocumento(): "+ entity.getEstudiante().getTextoCodigoExternoDocumento());
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoNumeroDocumento(): "+ entity.getEstudiante().getTextoNumeroDocumento());
		}
		if(entity.getGradoAcademico()!=null){
			UNALMLogger.trace(log, method,"entity.getGradoAcademico().textoNombre(): "+ entity.getGradoAcademico().getTextoNombre());
			UNALMLogger.trace(log, method,"entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
			if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getTextoNombre())){
				SCGGradoAcademicoEntity gradoAcademicoNombre = new  SCGGradoAcademicoEntity();
				gradoAcademicoNombre.setTextoNombre(entity.getGradoAcademico().getTextoNombre());
				SCGGradoAcademicoEntity gradoAcademicoCodigo = this.gradoTituloService.getForUpdateGradoAcademico(gradoAcademicoNombre);
				if(gradoAcademicoCodigo !=null){
					UNALMLogger.trace(log, method,"gradoAcademicoCodigo.getCodigo(): "+ gradoAcademicoCodigo.getCodigo());
					entity.setGradoAcademico(gradoAcademicoCodigo);
				}
			}
		}
		if(entity.getAgendaGrupo()!=null){
			UNALMLogger.trace(log, method,"entity.getAgendaGrupo().getTextoNombre(): "+ entity.getAgendaGrupo().getTextoNombre());
			if(!TypesUtil.isEmptyString(entity.getAgendaGrupo().getTextoNombre())){
				SCGAgendaGrupoEntity agendaGrupoNombre = new  SCGAgendaGrupoEntity();
				agendaGrupoNombre.setTextoNombre(entity.getAgendaGrupo().getTextoNombre());
				SCGAgendaGrupoEntity agendaGrupoCodigo = this.agendaGrupoService.getForUpdateAgendaGrupo(agendaGrupoNombre);
				if(agendaGrupoCodigo !=null){
					UNALMLogger.trace(log, method,"agendaGrupoCodigo.getCodigo(): "+ agendaGrupoCodigo.getCodigo());
					entity.setAgendaGrupo(agendaGrupoCodigo);
				}
			}
		}
		
		if(entity.getFechaCierreFinal()!=null&&entity.getFechaCierreInicial()!=null){
			String fechaInicio = TypesUtil.getDefaultString(entity.getFechaCierreInicial(), "yyy-MM-dd");
			String fechaFinal = TypesUtil.getDefaultString(entity.getFechaCierreFinal(), "yyy-MM-dd");
			UNALMLogger.trace(log, method,"fechaInicio: "+ fechaInicio);
			UNALMLogger.trace(log, method,"fechaFinal: "+ fechaFinal);

			if(fechaInicio.equals(ApplicationConstant.DATE_INVALID)&&fechaFinal.equals(ApplicationConstant.DATE_INVALID)){
				UNALMLogger.trace(log, method,"set Fecha null ");
				entity.setFechaCierreFinal(null);
				entity.setFechaCierreInicial(null);
			}
		}
		if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
    		if(entity.getFlagCandado().equals("B")){
    			entity.setFlagCandado(null);
    		}
    	}else{
    		entity.setFlagCandado("0");
    	}
		
		UNALMLogger.trace(log, method,"entity.getNumeroFolio(): "+ entity.getNumeroFolio());
		UNALMLogger.trace(log, method,"entity.getNumeroLibro(): "+ entity.getNumeroLibro());
		UNALMLogger.trace(log, method,"entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
		UNALMLogger.trace(log, method,"entity.getFechaCierreInicial(): "+ entity.getFechaCierreInicial());
		UNALMLogger.trace(log, method,"entity.getFechaCierreFinal(): "+ entity.getFechaCierreFinal());
		UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+ entity.getFlagCandado());
		UNALMLogger.trace(log, method,"entity.getEstudianteRegistroPadre(): "+ entity.getEstudianteRegistroPadre());

		
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGEstudianteRegistroEntity> list=this.duplicadoService.getListDuplicadoDescendente(entity,filterUtil,agendas);
        	UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGEstudianteRegistroEntity item : list) {
        		String facultadNombre=null,
        				escuelaCarrera=null,
        				matricula=null,
        				especialidadPostgrado=null,
        				apellidoPaterno=null,
        				apellidoMaterno=null,
        				nombre=null,
        				sexo=null,
        				documentoTipo=null,
        				documentoNumero=null,
        				gradoTitulo=null,
        				abrebiadoGradoTitulo=null,
        				especialidad=null,
        				modalidadGradoTitulo=null,
        				modalidadEstudio=null,
        				tipoEmision=null,
        				nombreRector=null,
        				codigoRector=null,
        				nombreSecretarioGeneral=null,
        				codigoSecretarioGeneral=null,
        				tipoAutoridad=null,
        				nombreAutoridad=null,
        				agendaGrupo=null,
        				universidadBachiller=null,
        				universidadMaestria=null,
        				fechaIngresanteMatricula=null, 
        				egresadoCiclo=null,
                		egresadoFecha=null,
        				enviadoSunedu=null,
        				duplicado=null,
        				anulado=null,
        				numeroRegistroDuplicado=null,
        				fechaAprobacionCU=null,
        				actoFecha=null;
        		
        		if(item.getEstudiante()!=null){
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoNumero=item.getEstudiante().getTextoNumeroDocumento();
        			fechaIngresanteMatricula=TypesUtil.getDefaultString(item.getEstudiante().getFechaIngresanteMatricula(), "dd/MM/yyyy");
        			egresadoCiclo=item.getEstudiante().getTextoCicloEgreso();
        			egresadoFecha=TypesUtil.getDefaultString(item.getEstudiante().getFechaTramiteConstancia(), "dd/MM/yyyy");
        			matricula=item.getEstudiante().getTextoMatricula();
        			
        			if(item.getEstudiante().getPersona()!=null){
        				apellidoMaterno=item.getEstudiante().getPersona().getTextoMaterno();
        				apellidoPaterno=item.getEstudiante().getPersona().getTextoPaterno();
        				nombre=item.getEstudiante().getPersona().getTextoNombre();
        				sexo=item.getEstudiante().getPersona().getTextoSexo();
        			}
        		}
        		actoFecha= TypesUtil.getDefaultString(item.getFechaSustentacionTesis(), "dd/MM/yyyy");      		
        		fechaAprobacionCU= TypesUtil.getDefaultString(item.getFechaAprobacioncu(), "dd/MM/yyyy");      		
        		if(item.getGradoAcademico()!=null){
        			String tipo=item.getGradoAcademico().getCodigoExterno();
        			if(tipo.equals("B")){
        				gradoTitulo=item.getGradoAcademico().getTextoNombre();
        				if(item.getEspecialidad() !=null){
                			facultadNombre =item.getEspecialidad().getFacultad().getTextoNombreEspanol();
                			//especialidad=item.getEspecialidad().getTextoNombreBachiller();
                			gradoTitulo=item.getGradoAcademico().getTextoNombre()+
        							" en "+
        							item.getEspecialidad().getTextoNombreBachiller();
                			
        				}
        				/*
        				if(item.getModalidadGradoTitulo()!=null){
        					UNALMLogger.trace(log, method,"item.getModalidadGradoTitulo().getCodigoExterno(): "+ item.getModalidadGradoTitulo().getCodigoExterno());
        					if(item.getModalidadGradoTitulo().getCodigoExterno().equals("004")){
        						actoFecha=fechaAprobacionCU;
        					}
        				}
        				*/
        			}else if (tipo.equals("T")){
        				gradoTitulo=item.getEspecialidad().getTextoNombreDenominacion();
        				if(item.getEspecialidad() !=null){
                			facultadNombre =item.getEspecialidad().getFacultad().getTextoNombreEspanol();
                			especialidad="";
                		}
        			}else{
        				if(item.getEspecialidadPostgrado()!=null){
        					facultadNombre="";
                			especialidadPostgrado =item.getEspecialidadPostgrado().getFacultad().getTextoNombreEspanol();
                			especialidad =item.getEspecialidadPostgrado().getTextoNombreEspanol();
                		}
        				gradoTitulo=item.getGradoAcademico().getTextoNombre();
        			}
        			abrebiadoGradoTitulo=item.getGradoAcademico().getCodigoExterno();
        		}
        		if(item.getModalidadGradoTitulo()!=null){
        			modalidadGradoTitulo= item.getModalidadGradoTitulo().getTextoNombre();
        		}
        		if(item.getModalidadEstudio()!=null){
        			modalidadEstudio= item.getModalidadEstudio().getCodigoExterno();
        		}
        		if(item.getFlagDuplicado()!=null){
        			if(item.getFlagDuplicado().equals("0")){
            			tipoEmision="O";
            		}else if(item.getFlagDuplicado().equals("1")){
            			tipoEmision="D";
            		}
        		}
        		
        		if(item.getAutoridadRegistroRector()!=null){
        			nombreRector=item.getAutoridadRegistroRector().getTextoNombreAutoridad();
        			codigoRector=item.getAutoridadRegistroRector().getCargo().getTextoNombre();
        		}
        		if(item.getAutoridadRegistroSecretarioGeneral()!=null){
        			nombreSecretarioGeneral=item.getAutoridadRegistroRector().getTextoNombreAutoridad();
        			codigoSecretarioGeneral=item.getAutoridadRegistroRector().getCargo().getTextoNombre();
        		}
        		if(item.getAutoridadRegistroDecano()!=null){
        			if(!TypesUtil.isEmptyString(item.getAutoridadRegistroDecano().getTextoNombreAutoridad())){
        				nombreAutoridad=item.getAutoridadRegistroDecano().getTextoNombreAutoridad();
        				//tipoAutoridad =item.getAutoridadRegistroDecano().getCargo().getTextoNombre();
        				tipoAutoridad ="Decano";
        			}
        		}else if(item.getAutoridadRegistroDirectorPostgrado()!=null){
        			nombreAutoridad=item.getAutoridadRegistroDirectorPostgrado().getTextoNombreAutoridad();
    				tipoAutoridad =item.getAutoridadRegistroDirectorPostgrado().getCargo().getTextoNombre();
        		}
        		if(item.getAgendaGrupo()!=null){
        			agendaGrupo=item.getAgendaGrupo().getTextoNombre();
        		}
        		/*
        		if(item.getGradoAcademico()!=null){
        			gradoAcademico=item.getGradoAcademico().getTextoNombre();
        			gradoAcademicoCodigoExterno=item.getGradoAcademico().getCodigoExterno();
        		}
        		*/
        		if(item.getUniversidadBachiller()!=null){
        			universidadBachiller=item.getUniversidadBachiller().getTextoNombre();
        		}
        		if(item.getUniversidadMaestria()!=null){
        			universidadMaestria=item.getUniversidadMaestria().getTextoNombre();
        		}
        		if(!TypesUtil.isEmptyString(item.getFlagEnviadoSunedu())){
        			enviadoSunedu="Si";
        		}else{
        			enviadoSunedu="No";
        		}
        		if(item.getFlagAnulado().equals("1")){
        			anulado="Si";
        		}else{
        			anulado="No";
        		}
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("matricula", matricula);
        		itemJSON.put("codigoUniversidad","007");
        		itemJSON.put("razonSocial", "UNIVERSISDAD NACIONAL AGRARIA LA MOLINA");
        		itemJSON.put("matriculaFecha",fechaIngresanteMatricula);
        		itemJSON.put("facultadNombre", facultadNombre);
        		itemJSON.put("escuelaCarrera", "");
        		itemJSON.put("especialidadPostgrado", especialidadPostgrado);
        		itemJSON.put("apellidoPaterno", apellidoPaterno);
        		itemJSON.put("apellidoMaterno", apellidoMaterno);
        		itemJSON.put("nombre", nombre);
        		itemJSON.put("sexo", sexo);
        		itemJSON.put("documentoTipo", documentoTipo);
        		itemJSON.put("documentoNumero", documentoNumero);
        		itemJSON.put("gradoTitulo", gradoTitulo);
        		itemJSON.put("especialidad", especialidad);
        		itemJSON.put("abreviaturaGradoTitulo", abrebiadoGradoTitulo);
        		itemJSON.put("actoTituloGrado", modalidadGradoTitulo);
        		itemJSON.put("actoFecha",actoFecha);
        		itemJSON.put("tesis", item.getTextoNombreTesis());
        		itemJSON.put("modadlidad",modalidadEstudio );
        		itemJSON.put("procedenciaRevalidadPais", "-");
        		itemJSON.put("procedenciaRevalidadUniversidad", "-");
        		itemJSON.put("procedenciaRevalidadGradoExtranjero","-");
        		itemJSON.put("resolucionNumero", item.getTextoResolucionRectoral());
        		itemJSON.put("resolucionFacultad ", item.getTextoResolucionFacultad());
        		itemJSON.put("diplomaFecha", TypesUtil.getDefaultString(item.getFechaAprobacioncu(), "dd/MM/yyyy"));
        		itemJSON.put("diplomaNumero", item.getTextoCodigoBarra());
        		itemJSON.put("diplomaTipoEmision", tipoEmision);
        		itemJSON.put("registroLibro", item.getNumeroLibro());
        		itemJSON.put("registroFolio", item.getNumeroFolio());
        		itemJSON.put("registroRegistro", item.getNumeroRegistro());
        		itemJSON.put("cargoUno", codigoRector);
        		itemJSON.put("autoridadUno", nombreRector);
        		itemJSON.put("cargoDos", codigoSecretarioGeneral);
        		itemJSON.put("autoridadDos", nombreSecretarioGeneral);
        		itemJSON.put("cargoTres", tipoAutoridad);
        		itemJSON.put("autoridadTres", nombreAutoridad);
        		itemJSON.put("agendaGrupo", agendaGrupo);
        		//itemJSON.put("gradoAcademico", gradoAcademico);
        		//itemJSON.put("gradoAcademicoCodigoExterno", gradoAcademicoCodigoExterno);
        		itemJSON.put("procedenciaBachiller",universidadBachiller);
        		itemJSON.put("procedenciaMaestria", universidadMaestria);
        		itemJSON.put("enviadoSunedu",enviadoSunedu );
        		itemJSON.put("estudianteRegistroPadre",item.getEstudianteRegistroPadre() );
        		itemJSON.put("resolucionCambioNombre",item.getTextoResolucionCambioNombre() );
        		itemJSON.put("textoProcedenciaMaestriaExtranjero", item.getTextoProcedenciaMaestriaExtranjero());
        		itemJSON.put("egresadoCiclo", egresadoCiclo);
        		itemJSON.put("anulado", anulado);
        		itemJSON.put("egresadoFecha", egresadoFecha);
        		itemJSON.put("flagDiplomaSexo", item.getFlagDiplomaSexo());
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
	@RequestMapping("getModalidadGradoTituloList")
	public  void getModalidadGradoTituloList(HttpServletRequest request,HttpServletResponse response,SCGModalidadGradoTituloGrupoEntity entity) throws Exception{
		final String method = "getModalidadGradoTituloList";
		final String params = "SCGModalidadGradoTituloGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method," entity.getCodigo() "+ entity.getCodigo());
		if(entity.getGradoAcademico()!= null){
			UNALMLogger.trace(log, method,"entity.getGradoAcademico().getCodigoExterno(): "+ entity.getGradoAcademico().getCodigoExterno());
		}

        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGModalidadGradoTituloGrupoEntity> list=this.gradoTituloService.getListModalidadGradoTituloGrupo(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGModalidadGradoTituloGrupoEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getModalidadGradoTitulo().getCodigo());
        		itemJSON.put("textoNombre", item.getModalidadGradoTitulo().getTextoNombre());
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
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGEstudianteRegistroEntity entity)throws Exception {
		final String method = "delete";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			this.duplicadoService.delete(entity);;
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
	@RequestMapping("enviarSunedu")
	public void enviarSunedu(HttpServletRequest request, HttpServletResponse response, SCGEstudianteRegistroEntity entity)throws Exception {
		final String method = "enviarSunedu";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			this.duplicadoService.enviarSunedu(entity);
			jsonObjectRoot.put("message", "Enviado Exitosamente");
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
	@RequestMapping("saveDuplicado")
	public  void save(HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity,SCGAdjuntoEntity adjunto) throws Exception{

		final String method = "saveDuplicado";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity,SCGAdjuntoEntity adjunto";
		String URLAdjuntoFoto=null,
				   URLAdjuntoEscaneado=null,
				   textoCodigoDuplicadoAdjuntoFoto=null,
				   textoCodigoDuplicadoAdjuntoEscaneado=null,
				   textoCodigoEstudianteRegistroAdjuntoFoto=null,
				   textoCodigoEstudianteRegistroAdjuntoEscaneado=null,
				   textoDetalle=null,
				   textoNombreTesis=null,
				   textoNombreTrabajoInvestigacion=null,
				   textoResolucionEpg=null,
				   textoResolucionFacultad=null,
				   textoResolucionRectoral=null,
				   textoResolucionCambioNombre=null;
			Long codigoDuplicadoAdjuntoFoto=null,
				 codigoDuplicadoAdjuntoEscaneado=null,
				 codigoAdjuntoFoto=null,
				 codigoAdjuntoEscaneado=null;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		
//		textoNombreTesis=TypesUtil.setFormatUTF8(entity.getTextoNombreTesis());
//		entity.setTextoNombreTesis(textoNombreTesis);
		
		textoNombreTrabajoInvestigacion=TypesUtil.setFormatUTF8(entity.getTextoNombreTrabajoInvestigacion());
		entity.setTextoNombreTrabajoInvestigacion(textoNombreTrabajoInvestigacion);
		
//		textoResolucionEpg=TypesUtil.setFormatUTF8(entity.getTextoResolucionEpg());
//		entity.setTextoResolucionEpg(textoResolucionEpg);
//
		textoResolucionFacultad=TypesUtil.setFormatUTF8(entity.getTextoResolucionFacultad());
		entity.setTextoResolucionFacultad(textoResolucionFacultad);

//		textoResolucionRectoral=TypesUtil.setFormatUTF8(entity.getTextoResolucionRectoral());
//		entity.setTextoResolucionRectoral(textoResolucionRectoral);
//
//		textoResolucionCambioNombre=TypesUtil.setFormatUTF8(entity.getTextoResolucionCambioNombre());
//		entity.setTextoResolucionCambioNombre(textoResolucionCambioNombre);
//		 
//		textoDetalle=TypesUtil.setFormatUTF8(entity.getTextoDetalle());
//		entity.setTextoDetalle(textoDetalle);
		
		UNALMLogger.trace(log, method,"entity.getCodigo(): "+entity.getCodigo());
        UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+entity.getFlagCandado());
        UNALMLogger.trace(log, method,"entity.getFlagDuplicado(): "+entity.getFlagDuplicado());
        UNALMLogger.trace(log, method,"entity.getFlagEliminado(): "+entity.getFlagEliminado());
        UNALMLogger.trace(log, method,"entity.getFlagEnviadoSunedu(): "+entity.getFlagEnviadoSunedu());
        UNALMLogger.trace(log, method,"entity.getFlagMatricula20141(): "+entity.getFlagMatricula20141());
        UNALMLogger.trace(log, method,"entity.getTextoCodigoBarra(): "+entity.getTextoCodigoBarra());
        UNALMLogger.trace(log, method,"entity.getTextoDetalle(): "+entity.getTextoDetalle());
        UNALMLogger.trace(log, method,"entity.getTextoMatriculaPost(): "+entity.getTextoMatriculaPost());
        UNALMLogger.trace(log, method,"entity.getTextoNombreTesis(): "+entity.getTextoNombreTesis());
        UNALMLogger.trace(log, method,"entity.getTextoNombreTrabajoInvestigacion(): "+entity.getTextoNombreTrabajoInvestigacion());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionEpg(): "+entity.getTextoResolucionEpg());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionEpg(): "+entity.getTextoResolucionEpg());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionFacultad(): "+entity.getTextoResolucionFacultad());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionRectoral(): "+entity.getTextoResolucionRectoral());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionCambioNombre(): "+entity.getTextoResolucionCambioNombre());
        UNALMLogger.trace(log, method,"entity.getTextoSemestreEgreso(): "+entity.getTextoSemestreEgreso());
        UNALMLogger.trace(log, method,"entity.getNumeroFolio(): "+entity.getNumeroFolio());
        UNALMLogger.trace(log, method,"entity.getNumeroLibro(): "+entity.getNumeroLibro());
        UNALMLogger.trace(log, method,"entity.getNumeroRegistro(): "+entity.getNumeroRegistro());
        UNALMLogger.trace(log, method,"entity.getFechaCierre(): "+entity.getFechaCierre());
        UNALMLogger.trace(log, method,"entity.getEstudianteRegistroPadre(): "+entity.getEstudianteRegistroPadre());
        UNALMLogger.trace(log, method,"entity.getFlagDiplomaSexo(): "+entity.getFlagDiplomaSexo() );
        /*
        UNALMLogger.trace(log, method, "adjunto.getArchivo().getBytes() "+adjunto.getArchivo().getBytes());
		UNALMLogger.trace(log, method, "adjunto.getArchivo().getOriginalFilename(): "+adjunto.getArchivo().getOriginalFilename());
		UNALMLogger.trace(log, method, "adjunto.getCodigo(): "+adjunto.getCodigo());
		*/
        
        if(entity.getUniversidadBachiller()!= null){
        	 UNALMLogger.trace(log, method,"entity.getUniversidadBachiller().getCodigo(): "+entity.getUniversidadBachiller().getCodigo());
        }
        if(entity.getUniversidadMaestria()!= null){
       	 UNALMLogger.trace(log, method,"entity.getUniversidadMaestria().getCodigo(): "+entity.getUniversidadMaestria().getCodigo());
       }
        if(entity.getModalidadGradoTitulo() != null){
        	 UNALMLogger.trace(log, method,"entity.getModalidadGradoTitulo().getCodigo(): "+entity.getModalidadGradoTitulo().getCodigo());
        }
        if(entity.getModalidadEstudio() != null){
        	 UNALMLogger.trace(log, method,"entity.getModalidadEstudio().getCodigo(): "+entity.getModalidadEstudio().getCodigo());
        }
        if(entity.getAutoridadRegistroDecano() != null){
        	 UNALMLogger.trace(log, method,"entity.getAutoridadRegistroDecano().getCodigo(): "+entity.getAutoridadRegistroDecano().getCodigo());
        }
        if(entity.getAutoridadRegistroSecretarioGeneral() != null){
        	 UNALMLogger.trace(log, method,"entity.getAutoridadRegistroSecretarioGeneral().getCodigo(): "+entity.getAutoridadRegistroSecretarioGeneral().getCodigo());
        }
        if(entity.getAutoridadRegistroRector() != null){
        	 UNALMLogger.trace(log, method,"entity.getAutoridadRegistroRector().getCodigo(): "+entity.getAutoridadRegistroRector().getCodigo());
        }
        if(entity.getAutoridadRegistroDirectorPostgrado() != null){
        	 UNALMLogger.trace(log, method,"entity.getAutoridadRegistroDirectorPostgrado().getCodigo(): "+entity.getAutoridadRegistroDirectorPostgrado().getCodigo());
        }
        if(entity.getEspecialidad() != null){
        	 UNALMLogger.trace(log, method,"entity.getEspecialidad().getCodigo()"+entity.getEspecialidad().getCodigo());
        }
        if(entity.getEspecialidadPostgrado() != null){
        	 UNALMLogger.trace(log, method,"entity.getEspecialidadPostgrado().getCodigo(): "+entity.getEspecialidadPostgrado().getCodigo());
        }
        if(entity.getEstudiante() != null){
        	 UNALMLogger.trace(log, method,"entity.getEstudiante().getCodigo(): "+entity.getEstudiante().getCodigo());
        	 UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoMatricula(): "+entity.getEstudiante().getTextoMatricula());
        }
        if(entity.getGradoAcademico() != null){
        	 UNALMLogger.trace(log, method,"entity.getGradoAcademico().getCodigo(): "+entity.getGradoAcademico().getCodigo());
        	 UNALMLogger.trace(log, method,"entity.getGradoAcademico().getCodigoExterno(): "+entity.getGradoAcademico().getCodigoExterno());
        	 
        };
		
		try {
			
//			URLAdjuntoFoto =request.getParameter("URLAdjuntoFoto");
//			UNALMLogger.trace(log, method, "URLArchivoFoto: "+URLAdjuntoFoto);
//			textoCodigoDuplicadoAdjuntoFoto =request.getParameter("codigoEstudianteRegistroAdjuntoFoto");
//			UNALMLogger.trace(log, method, "textoCodigoDuplicadoAdjuntoFoto: "+textoCodigoDuplicadoAdjuntoFoto);
//			codigoDuplicadoAdjuntoFoto =this.duplicadoService.getFormatCodigoAdjunto(textoCodigoDuplicadoAdjuntoFoto);
//			if(codigoDuplicadoAdjuntoFoto!=null){
//				SCGEstudianteRegistroAdjuntoEntity duplicadoAdjuntos = this.duplicadoService.getDuplicadoAdjunto(codigoDuplicadoAdjuntoFoto);
//				codigoAdjuntoFoto =this.duplicadoService.getCodigoAdjuntoByDuplicadoAdjunto(duplicadoAdjuntos);	
//			
//			}
//			
//			SCGAdjuntoEntity codigoAdjuntoFotoSave = new SCGAdjuntoEntity();
//			if(!TypesUtil.isEmptyString(entity.getAdjuntoFoto().getOriginalFilename())){
//				SCGAdjuntoEntity adjuntoFoto = this.duplicadoService.setValuesAdjunto(entity.getAdjuntoFoto(), 1L);
//				adjuntoFoto.setCodigo(codigoAdjuntoFoto);
//				adjuntoFoto.setTextoRuta(this.duplicadoService.setURLFoto(entity, adjuntoFoto));
//				codigoAdjuntoFotoSave =this.duplicadoService.saveAdjuntoDuplicado(adjuntoFoto);
//				UNALMLogger.trace(log, method, "codigoAdjuntoFotoSave: "+codigoAdjuntoFotoSave);
//			}
//			
//			URLAdjuntoEscaneado =request.getParameter("URLAdjuntoEscaneado");
//			UNALMLogger.trace(log, method, "URLAdjuntoEscaneado: "+URLAdjuntoEscaneado);
//			textoCodigoDuplicadoAdjuntoEscaneado =request.getParameter("codigoEstudianteRegistroAdjuntoEscaneado");
//			UNALMLogger.trace(log, method, "textoCodigoDuplicadoAdjuntoEscaneado: "+textoCodigoDuplicadoAdjuntoEscaneado);
//			codigoDuplicadoAdjuntoEscaneado =this.duplicadoService.getFormatCodigoAdjunto(textoCodigoDuplicadoAdjuntoEscaneado);
//			if(codigoDuplicadoAdjuntoEscaneado!=null){
//				SCGEstudianteRegistroAdjuntoEntity duplicadoEscaneado = this.duplicadoService.getDuplicadoAdjunto(codigoDuplicadoAdjuntoEscaneado);
//				codigoAdjuntoEscaneado =this.duplicadoService.getCodigoAdjuntoByDuplicadoAdjunto(duplicadoEscaneado);	
//			}
//			
//			
//			SCGAdjuntoEntity codigoAdjuntoEscaneadoSave = new SCGAdjuntoEntity();
//			if(!TypesUtil.isEmptyString(entity.getAdjuntoEscaneado().getOriginalFilename())){
//				SCGAdjuntoEntity adjuntoEscaneado = this.duplicadoService.setValuesAdjunto(entity.getAdjuntoEscaneado(), 2L);
//				adjuntoEscaneado.setCodigo(codigoAdjuntoEscaneado);
//				adjuntoEscaneado.setTextoRuta(this.duplicadoService.setURLEscaneado(entity, adjuntoEscaneado));
//				codigoAdjuntoEscaneadoSave =this.duplicadoService.saveAdjuntoDuplicado(adjuntoEscaneado);
//				UNALMLogger.trace(log, method, "codigoAdjuntoEscaneadoSave: "+codigoAdjuntoEscaneadoSave);
//			}
//			
			if(entity.getGradoAcademico()!=null){
				if(entity.getGradoAcademico().getCodigo()==null){
					SCGGradoAcademicoEntity gradoAcademicoCodigoExterno = new SCGGradoAcademicoEntity();
					gradoAcademicoCodigoExterno.setCodigoExterno(entity.getGradoAcademico().getCodigoExterno());
					SCGGradoAcademicoEntity gradoAcademico =this.gradoTituloService.getForUpdateGradoAcademico(gradoAcademicoCodigoExterno);
					UNALMLogger.trace(log, method,"gradoAcademico: "+gradoAcademico);
					if(gradoAcademico!=null){
						if(gradoAcademico.getCodigo()!=null){
							UNALMLogger.trace(log, method,"gradoAcademico.getCodigo(): "+gradoAcademico.getCodigo());
							entity.setGradoAcademico(gradoAcademico);
							UNALMLogger.trace(log, method,"gradoAcademico.getCodigoExterno(): "+gradoAcademico.getCodigoExterno());
							entity.setGradoAcademico(gradoAcademico);
							if(!TypesUtil.isEmptyString(gradoAcademico.getCodigoExterno())){
								if(gradoAcademico.getCodigoExterno().equals("T")){
									SCGUniversidadEntity universidad = new SCGUniversidadEntity();
									universidad.setCodigoExterno("007");
									SCGUniversidadEntity codigoUniversidad = this.gradoTituloService.getForUpdateUniversidad(universidad);
									entity.setUniversidadBachiller(codigoUniversidad);
								}if(gradoAcademico.getCodigoExterno().equals("B")){
									if(entity.getModalidadGradoTitulo()!=null){
										SCGModalidadGradoTituloEntity modalidadGradoTitulo =this.gradoTituloService.getForUpdateModalidadGradoTitulo(entity.getModalidadGradoTitulo());
										if(modalidadGradoTitulo!=null){
											if(modalidadGradoTitulo.getCodigoExterno().equals("004")){
												entity.setTextoNombreTrabajoInvestigacion("");
												entity.setFechaSustentacionTesis(null);

											}
										}
									}
								}
							}
						}else{
							UNALMLogger.trace(log, method,"gradoAcademico.getCodigo(): "+null);
							entity.setGradoAcademico(null);
						}
					}
				}
			}
			if(entity.getModalidadEstudio()==null){
				SCGModalidadEstudioEntity modalidadEstudio = new SCGModalidadEstudioEntity();
				modalidadEstudio.setCodigoExterno("P");
				SCGModalidadEstudioEntity codigoModalidadEstudio = this.gradoTituloService.getForUpdateModalidadEstudio(modalidadEstudio);
				if(codigoModalidadEstudio!=null){
					entity.setModalidadEstudio(codigoModalidadEstudio);
				}
			}
			if( entity.getFlagDiplomaSexo() != null ){
				UNALMLogger.trace(log, method, "entity.getFlagDiplomaSexo(): "+entity.getFlagDiplomaSexo());
				if( !entity.getFlagDiplomaSexo().equals("1") ){
					entity.setFlagDiplomaSexo( null );
				}
			}
			
			entity.setFlagCandado("0");
			entity.setFlagEliminado("1");		
			entity.setFlagDuplicado("1");		
			entity.setFlagAnulado("0");
			entity.setFechaAgregar(new Date());
			
			
			//SCGEstudianteRegistroEntity codigoDuplicadoSave=this.duplicadoService.saveDuplicado(entity);
			SCGEstudianteRegistroEntity codigoDuplicadoSave= new SCGEstudianteRegistroEntity();
			UNALMLogger.trace(log, method, "codigoDuplicadoSave: "+codigoDuplicadoSave);
			
			if(entity.getCodigo()!=null){
				codigoDuplicadoSave=this.duplicadoService.saveDuplicado(entity);
				UNALMLogger.trace(log, method, "codigoDuplicadoSave: "+codigoDuplicadoSave);
			}else{
				if(this.duplicadoService.isDuplicateRecord(entity)){
					jsonObjectRoot.put("success", false);
					jsonObjectRoot.put("message", "Ya existe este Registro");
					return;
				}else{
					codigoDuplicadoSave=this.duplicadoService.saveDuplicado(entity);
					UNALMLogger.trace(log, method, "codigoDuplicadoSave: "+codigoDuplicadoSave);
				
				}
			}
			
			
//			if(codigoDuplicadoSave.getCodigo()!=null &&codigoAdjuntoFotoSave.getCodigo()!=null){
//				SCGEstudianteRegistroAdjuntoEntity duplicadoAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
//				duplicadoAdjunto = this.duplicadoService.setValuesDuplicadoAdjunto(codigoDuplicadoSave, codigoAdjuntoFotoSave);
//				if(codigoDuplicadoAdjuntoFoto!=null){
//					duplicadoAdjunto.setCodigo(codigoDuplicadoAdjuntoFoto);
//				}
//				this.gradoTituloService.saveEstudianteRegistroAdjunto(duplicadoAdjunto);
//			}
//			if(codigoDuplicadoSave.getCodigo()!=null &&codigoAdjuntoEscaneadoSave.getCodigo()!=null){
//				SCGEstudianteRegistroAdjuntoEntity duplicadoAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
//				duplicadoAdjunto = this.duplicadoService.setValuesDuplicadoAdjunto(codigoDuplicadoSave, codigoAdjuntoEscaneadoSave);
//				if(codigoDuplicadoAdjuntoEscaneado!=null){
//					duplicadoAdjunto.setCodigo(codigoDuplicadoAdjuntoEscaneado);
//				}
//				this.gradoTituloService.saveEstudianteRegistroAdjunto(duplicadoAdjunto);
//			}
			JSONObject jsonObject = new JSONObject();
			
			jsonObjectRoot.put("data", jsonObject);
			jsonObjectRoot.put("message", "Grabado Exitosamente");
			jsonObjectRoot.put("success", true);

		} catch (Exception e) {
			UNALMLogger.error(log, method, "Exception:", e);
			jsonObjectRoot.put("success", false);
			jsonObjectRoot.put("message", e.getLocalizedMessage());

		} finally {
			out.print(jsonObjectRoot.toString());
			out.flush();
			out.close();
		}
		UNALMLogger.exit(log, method);
	};
	@RequestMapping("getDuplicado")
	public  void getDuplicado(HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity) throws Exception{
		final String method = "getDuplicado";
		final String params = "(HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	     	
        	SCGEstudianteRegistroEntity estudiante = this.duplicadoService.getForUpdateDuplicado(entity);
        	JSONObject itemJSON = new JSONObject();
        	/*
        	itemJSON.put("codigo", estudiante.getCodigo());
    		itemJSON.put("codigoUniversidad","007");
    		itemJSON.put("razonSocial", "UNIVERSISDAD NACIONAL AGRARIA LA MOLINA");
    		itemJSON.put("matriculaFecha", "-");
    		itemJSON.put("procedenciaBachiller", "UNIVERSISDAD NACIONAL AGRARIA LA MOLINA");
    		*/
        	
        	itemJSON.put("codigo", estudiante.getCodigo());
    		itemJSON.put("textoResolucionFacultad", estudiante.getTextoResolucionFacultad());
    		itemJSON.put("textoResolucionRectoral", estudiante.getTextoResolucionRectoral());
    		itemJSON.put("textoNombreTrabajoInvestigacion", estudiante.getTextoNombreTrabajoInvestigacion());
    		itemJSON.put("textoNombreTesis", estudiante.getTextoNombreTesis());
    		itemJSON.put("textoDetalle", estudiante.getTextoDetalle());
    		itemJSON.put("numeroFolio", estudiante.getNumeroFolio());
    		itemJSON.put("numeroLibro", estudiante.getNumeroLibro());
    		itemJSON.put("numeroRegistro", estudiante.getNumeroRegistro());
    		itemJSON.put("flagCandado", estudiante.getFlagCandado());
    		itemJSON.put("fechaCierre",TypesUtil.getDefaultString(estudiante.getFechaCierre(), "dd-MM-yyyy")  );
    		itemJSON.put("textoCodigoBarra", estudiante.getTextoCodigoBarra());
    		itemJSON.put("textoMatriculaPost", estudiante.getTextoMatriculaPost());
    		itemJSON.put("textoResolucionEpg", estudiante.getTextoResolucionEpg());
    		itemJSON.put("textoResolucionCambioNombre", estudiante.getTextoResolucionCambioNombre());
    		itemJSON.put("fechaResolucionFacultad",  TypesUtil.getDefaultString(estudiante.getFechaResolucionFacultad(), "dd-MM-yyyy"));
    		itemJSON.put("fechaAprobacioncu",  TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "dd-MM-yyyy") );
    		itemJSON.put("fechaSustentacionTesis", TypesUtil.getDefaultString(estudiante.getFechaSustentacionTesis(), "dd-MM-yyyy") );
    		itemJSON.put("fechaResolucionEpg",TypesUtil.getDefaultString(estudiante.getFechaSustentacionTesis(), "dd-MM-yyyy")  );
    		itemJSON.put("codigoRegistroEscaneado", estudiante.getCodigo());
    		itemJSON.put("codigoRegistroFoto", estudiante.getCodigo());
    		if(estudiante.getEstudiante()!=null){
        		itemJSON.put("estudiante.textoMatricula", estudiante.getEstudiante().getTextoMatricula());
        		itemJSON.put("estudiante.codigo", estudiante.getEstudiante().getCodigo());
        	}
        	if(estudiante.getEspecialidad()!=null){
        		itemJSON.put("especialidad.codigo", estudiante.getEspecialidad().getCodigo());
        	}
        	if(estudiante.getEspecialidadPostgrado()!=null){
        		itemJSON.put("especialidadPostgrado.codigo", estudiante.getEspecialidadPostgrado().getCodigo());
        	}
    		if(estudiante.getModalidadGradoTitulo()!=null){
    			itemJSON.put("modalidadGradoTitulo.codigo", estudiante.getModalidadGradoTitulo().getCodigo());
    		}
    		if(estudiante.getModalidadEstudio()!=null){
    			itemJSON.put("modalidadEstudio.codigo", estudiante.getModalidadEstudio().getCodigo());
    		}
    		if(estudiante.getAutoridadRegistroRector()!=null){
    			itemJSON.put("autoridadRegistroRector.codigo", estudiante.getAutoridadRegistroRector().getCodigo());
    		}
    		if(estudiante.getAutoridadRegistroSecretarioGeneral()!=null){
    			itemJSON.put("autoridadRegistroSecretarioGeneral.codigo", estudiante.getAutoridadRegistroSecretarioGeneral().getCodigo());
    		}
    		if(estudiante.getAutoridadRegistroDecano()!=null){
    			itemJSON.put("autoridadRegistroDecano.codigo", estudiante.getAutoridadRegistroDecano().getCodigo());
    		}
    		if(estudiante.getUniversidadBachiller()!=null){
    				itemJSON.put("universidadBachiller.codigo", estudiante.getUniversidadBachiller().getCodigo());
	    		if(estudiante.getUniversidadBachiller().getPais()!=null){
	    			itemJSON.put("paisBachiller.codigo", estudiante.getUniversidadBachiller().getPais().getCodigo());
	    		}
    		}
    		if(estudiante.getUniversidadMaestria()!=null){
    				itemJSON.put("universidadMaestria.codigo", estudiante.getUniversidadMaestria().getCodigo());
	    		if(estudiante.getUniversidadMaestria().getPais()!=null){
	    			itemJSON.put("paisMaestria.codigo", estudiante.getUniversidadMaestria().getPais().getCodigo());
	    		}
    		}
    		if(estudiante.getAutoridadRegistroDirectorPostgrado()!=null){
    			itemJSON.put("autoridadRegistroDirectorPostgrado.codigo", estudiante.getAutoridadRegistroDirectorPostgrado().getCodigo());
    		}
    		if(estudiante.getAgendaGrupo()!=null){
    			itemJSON.put("agendaGrupo.codigo", estudiante.getAgendaGrupo().getCodigo());
    		}
    		if( estudiante.getAdjunto() !=null){
    			itemJSON.put("URLBase",estudiante.getAdjunto().getTextoRuta());
        		itemJSON.put("adjunto.codigo", estudiante.getAdjunto().getCodigo());
    		}
    		
    		//nuevo
    		itemJSON.put("textoSegundaEspecialidad", estudiante.getTextoSegundaEspecialidad());;
			
    		itemJSON.put("numeroCreditos", estudiante.getNumeroCreditos());;
			
    		itemJSON.put("textoRegistroMetadato", estudiante.getTextoRegistroMetadato());
			
    		itemJSON.put("textoProcedenciaTituloPedagogico", estudiante.getTextoProcedenciaTituloPedagogico());;
			
			itemJSON.put("fechaDiplomaDuplicado", TypesUtil.getDefaultString(estudiante.getFechaDiplomaDuplicado(), "dd-MM-yyyy"));
			
			itemJSON.put("textoProcedenciaGradoExtranjero", estudiante.getTextoProcedenciaGradoExtranjero());;
			
			itemJSON.put("fechaMatriculaPrograma", TypesUtil.getDefaultString(estudiante.getFechaMatriculaPrograma(), "dd-MM-yyyy"));;
			
			itemJSON.put("fechaMatriculaPosgrado", TypesUtil.getDefaultString(estudiante.getFechaMatriculaPosgrado(), "dd-MM-yyyy"));;
			
			itemJSON.put("fechaEgresoPosgrado", TypesUtil.getDefaultString(estudiante.getFechaEgresoPosgrado(), "dd-MM-yyyy"));;
			
			itemJSON.put("fechaInicioPrograma", TypesUtil.getDefaultString(estudiante.getFechaInicioPrograma(), "dd-MM-yyyy"));;
			
			itemJSON.put("fechaTerminoPrograma", TypesUtil.getDefaultString(estudiante.getFechaTerminoPrograma(), "dd-MM-yyyy"));;
    		
    		itemJSON.put("textoProcedenciaMaestriaExtranjero", estudiante.getTextoProcedenciaMaestriaExtranjero());

			UNALMLogger.trace(log, method, "estudiante.getCodigo(): "+estudiante.getCodigo());
    		SCGEstudianteRegistroAdjuntoEntity codigoDuplicadoAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
    		codigoDuplicadoAdjunto.setEstudianteRegistro(new SCGEstudianteRegistroEntity());
    		codigoDuplicadoAdjunto.getEstudianteRegistro().setCodigo(estudiante.getCodigo());
    		codigoDuplicadoAdjunto.setAdjunto(new SCGAdjuntoEntity());
    		codigoDuplicadoAdjunto.getAdjunto().setTipoAdjunto(new SCGTipoAdjuntoEntity());
    		codigoDuplicadoAdjunto.getAdjunto().getTipoAdjunto().setCodigo(1L);;
    		UNALMLogger.trace(log, method, "codigoDuplicadoAdjunto: "+codigoDuplicadoAdjunto);
    		SCGEstudianteRegistroAdjuntoEntity duplicadoAdjunto =this.duplicadoService.getForUpdateDuplicadoAdjunto(codigoDuplicadoAdjunto);
    		UNALMLogger.trace(log, method, "duplicadoAdjunto: "+duplicadoAdjunto);
			
    		if(duplicadoAdjunto!=null){
    			if( duplicadoAdjunto.getAdjunto() !=null){
        			UNALMLogger.trace(log, method, "URLAdjuntoFoto: "+duplicadoAdjunto.getAdjunto().getTextoRuta());
        			itemJSON.put("URLAdjuntoFoto",duplicadoAdjunto.getAdjunto().getTextoRuta());
        			//UNALMLogger.trace(log, method, "archivoFoto.codigo: "+ estudianteRegistroAdjunto.getAdjunto().getCodigo());
        			UNALMLogger.trace(log, method, "codigoArchivoFoto: "+ duplicadoAdjunto.getAdjunto().getCodigo());
            		//itemJSON.put("archivoFoto.codigo", estudianteRegistroAdjunto.getAdjunto().getCodigo());
            		itemJSON.put("codigoArchivoFoto", duplicadoAdjunto.getAdjunto().getCodigo());
            		itemJSON.put("codigoEstudianteRegistroAdjuntoFoto", duplicadoAdjunto.getCodigo());
        		}
    		}
    		
    		codigoDuplicadoAdjunto.getAdjunto().getTipoAdjunto().setCodigo(2L);
    		UNALMLogger.trace(log, method, "codigoDuplicadoAdjunto: "+codigoDuplicadoAdjunto);
    		SCGEstudianteRegistroAdjuntoEntity duplicadoAdjuntoEscaneado =this.duplicadoService.getForUpdateDuplicadoAdjunto(codigoDuplicadoAdjunto);
    		UNALMLogger.trace(log, method, "duplicadoAdjuntoEscaneado: "+duplicadoAdjuntoEscaneado);
			
    		if(duplicadoAdjuntoEscaneado!=null){
    			if( duplicadoAdjuntoEscaneado.getAdjunto() !=null){
        			UNALMLogger.trace(log, method, "URLAdjuntoEscaneado: "+duplicadoAdjuntoEscaneado.getAdjunto().getTextoRuta());
        			itemJSON.put("URLAdjuntoEscaneado",duplicadoAdjuntoEscaneado.getAdjunto().getTextoRuta());
        			UNALMLogger.trace(log, method, "codigoAdjuntoEscaneado: "+ duplicadoAdjuntoEscaneado.getAdjunto().getCodigo());
            		itemJSON.put("codigoAdjuntoEscaneado", duplicadoAdjuntoEscaneado.getAdjunto().getCodigo());
            		itemJSON.put("codigoEstudianteRegistroAdjuntoEscaneado", duplicadoAdjuntoEscaneado.getCodigo());
        		}
    		}
    		//itemJSON.put("fechaCreacion", TypesUtil.getDefaultString(item.getFechaCreacion(), "yyy-MM-dd"));
    		if( estudiante.getGradoAcademico() !=null){
    			itemJSON.put("gradoAcademico.codigo",estudiante.getGradoAcademico().getCodigo());
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
	/*
	public static List<Long> addCodigoAgenda(String[] splitSelectAgenda){
		List<Long> codigoAgenda = new ArrayList<Long>();
		for(String item:splitSelectAgenda){
			if(!TypesUtil.isEmptyString(item)){
				Long num =Long.parseLong(item, 10);
				codigoAgenda.add(num);
			}
		}
		return codigoAgenda;
	}
	public static String[] getSplitSelectAgenda(String selectAgenda){
		String delimiters = "(\\[)|,|(\\]$)";
		String[] values= selectAgenda.split(delimiters);
		return values;
	}
	public void saveAgendasThatWillNotBeClosed(List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed ){
		for(SCGEstudianteRegistroEntity agendaThatWillNotBeClosed:listOfAgendasThatWillNotBeClosed){
			agendaThatWillNotBeClosed.setAgendaGrupo(new SCGAgendaGrupoEntity());
			agendaThatWillNotBeClosed.getAgendaGrupo().setCodigo(1L);
			this.duplicadoService.saveDuplicado(agendaThatWillNotBeClosed);
		}
		
	}


	public  void saveAgendasThatHasToBeClose(String selectAgenda){
		String delimiters = "(\\[)|,|(\\]$)";
		String[] values= selectAgenda.split(delimiters);
	}
	public SCGEstudianteRegistroEntity getEstudianteRegistroOriginal(SCGEstudianteRegistroEntity agendaThatHasToBeClose){
		SCGEstudianteRegistroEntity numeroRegistroPadre= new SCGEstudianteRegistroEntity();
		numeroRegistroPadre.setNumeroRegistro(agendaThatHasToBeClose.getEstudianteRegistroPadre());
		SCGEstudianteRegistroEntity estudianteRegistroOriginal =this.gradoTituloService.getForUpdateEstudianteRegistro(numeroRegistroPadre);
		return estudianteRegistroOriginal;
	}


	public List<SCGEstudianteRegistroEntity> getListAgendasThatWillNotBeClosed(List<SCGEstudianteRegistroEntity> listOfOpenAgendas ,List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose){
		final String method = "close";
		List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed = new ArrayList<SCGEstudianteRegistroEntity>();

		if(listOfOpenAgendas.size()>0){
			for(SCGEstudianteRegistroEntity openAgenda:listOfOpenAgendas){
				listOfAgendasThatWillNotBeClosed.add(openAgenda);
			}
		}  
		for(SCGEstudianteRegistroEntity openAgenda:listOfOpenAgendas){
			for(SCGEstudianteRegistroEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeClose){
				if(openAgenda.getCodigo()==agendaThatHasToBeClose.getCodigo()){
					listOfAgendasThatWillNotBeClosed.remove(openAgenda);
					UNALMLogger.trace(log, method, "removiendo el registro:" + agendaThatHasToBeClose.getCodigo());
					UNALMLogger.trace(log, method, "listOfAgendasThatWillNotBeClosed.size():" + listOfAgendasThatWillNotBeClosed.size());
				}

			}
		}
		return listOfAgendasThatWillNotBeClosed;
	}
	*/
	@RequestMapping("close")
	public  void close(HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity) throws Exception{

		final String method = "close";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method);;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getFechaCierre(): " + entity.getFechaCierre());
		String groupList=request.getParameter("groupList");
		duplicadoService.agendasClose(entity, groupList);
		UNALMLogger.exit(log, method);
	};
	@RequestMapping(value = "excel")
	public ModelAndView excel(HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity) throws IOException {
		final String method = "getEstudianteRegistroList";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity";
		List<List<String>> masterList = new ArrayList<List<String>>();
		List<Long> agendas = new ArrayList<Long>();
		int count=0;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		
		
		if(entity.getEstudiante()!=null){
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoMatricula(): "+ entity.getEstudiante().getTextoMatricula());
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoNombreCompleto(): "+ entity.getEstudiante().getTextoNombreCompleto());
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoCodigoExternoDocumento(): "+ entity.getEstudiante().getTextoCodigoExternoDocumento());
			UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoNumeroDocumento(): "+ entity.getEstudiante().getTextoNumeroDocumento());
		}
		if(entity.getGradoAcademico()!=null){
			UNALMLogger.trace(log, method,"entity.getGradoAcademico().textoNombre(): "+ entity.getGradoAcademico().getTextoNombre());
			if(!TypesUtil.isEmptyString(entity.getGradoAcademico().getTextoNombre())){
				SCGGradoAcademicoEntity gradoAcademicoNombre = new  SCGGradoAcademicoEntity();
				gradoAcademicoNombre.setTextoNombre(entity.getGradoAcademico().getTextoNombre());
				SCGGradoAcademicoEntity gradoAcademicoCodigo = this.gradoTituloService.getForUpdateGradoAcademico(gradoAcademicoNombre);
				if(gradoAcademicoCodigo !=null){
					UNALMLogger.trace(log, method,"gradoAcademicoCodigo.getCodigo(): "+ gradoAcademicoCodigo.getCodigo());
					entity.setGradoAcademico(gradoAcademicoCodigo);
				}
			}
		}
		if(entity.getAgendaGrupo()!=null){
			UNALMLogger.trace(log, method,"entity.getAgendaGrupo().getTextoNombre(): "+ entity.getAgendaGrupo().getTextoNombre());
			if(!TypesUtil.isEmptyString(entity.getAgendaGrupo().getTextoNombre())){
				SCGAgendaGrupoEntity agendaGrupoNombre = new  SCGAgendaGrupoEntity();
				agendaGrupoNombre.setTextoNombre(entity.getAgendaGrupo().getTextoNombre());
				SCGAgendaGrupoEntity agendaGrupoCodigo = this.agendaGrupoService.getForUpdateAgendaGrupo(agendaGrupoNombre);
				if(agendaGrupoCodigo !=null){
					UNALMLogger.trace(log, method,"agendaGrupoCodigo.getCodigo(): "+ agendaGrupoCodigo.getCodigo());
					entity.setAgendaGrupo(agendaGrupoCodigo);
				}
			}
		}
		
		if(entity.getFechaCierreFinal()!=null&&entity.getFechaCierreInicial()!=null){
			String fechaInicio = TypesUtil.getDefaultString(entity.getFechaCierreInicial(), "yyy-MM-dd");
			String fechaFinal = TypesUtil.getDefaultString(entity.getFechaCierreFinal(), "yyy-MM-dd");
			UNALMLogger.trace(log, method,"fechaInicio: "+ fechaInicio);
			UNALMLogger.trace(log, method,"fechaFinal: "+ fechaFinal);

			if(fechaInicio.equals(ApplicationConstant.DATE_INVALID)&&fechaFinal.equals(ApplicationConstant.DATE_INVALID)){
				UNALMLogger.trace(log, method,"set Fecha null ");
				entity.setFechaCierreFinal(null);
				entity.setFechaCierreInicial(null);
			}
		}
		
		
		UNALMLogger.trace(log, method,"entity.getNumeroFolio(): "+ entity.getNumeroFolio());
		UNALMLogger.trace(log, method,"entity.getNumeroLibro(): "+ entity.getNumeroLibro());
		UNALMLogger.trace(log, method,"entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
		UNALMLogger.trace(log, method,"entity.getFechaCierreInicial(): "+ entity.getFechaCierreInicial());
		UNALMLogger.trace(log, method,"entity.getFechaCierreFinal(): "+ entity.getFechaCierreFinal());

        try {
        	FilterUtil filterUtil = new FilterUtil();
        	List<SCGEstudianteRegistroEntity> list=this.duplicadoService.getListDuplicadoAscendente(entity,filterUtil,agendas);
        	UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGEstudianteRegistroEntity item : list) {
        		String facultadNombre=null,
        				escuelaCarrera=null,
        				especialidadPostgrado=null,
        				especialidadNombre=null,
        				apellidoPaterno=null,
        				apellidoMaterno=null,
        				nombre=null,
        				sexo=null,
        				documentoTipo=null,
        				documentoNumero=null,
        				telefonoFijo=null,
        				telefonoCelular=null,
        				gradoTitulo=null,
        				abrebiadoGradoTitulo=null,
        				especialidad=null,
        				modalidadGradoTitulo=null,
        				modalidadEstudio=null,
        				tipoEmision=null,
        				nombreRector=null,
        				nombreRectorSunedu=null,
        				codigoRector=null,
        				nombreSecretarioGeneral=null,
        				nombreSecretarioGeneralSunedu=null,
        				codigoSecretarioGeneral=null,
        				tipoAutoridad=null,
        				nombreAutoridad=null,
        				nombreAutoridadSunedu = null,
        				agendaGrupo=null,
        				anuoAprobacionCU=null,
        				universidadBachiller=null,
        				universidadMaestria=null,
        				fechaIngresanteMatricula=null, 
        				fechaAprobacionCU=null, 
        				fechaAprobacionOriginal=null, 
        				egresadoFecha=null,
        				egresadoCiclo=null,
        				numeroFolio=null,
        				numeroRegistro=null,
        				actoFecha=null,
        				universidadExtranjero=null,
        				paisExtranjero=null,
        				procedenciaGradoExtranjero=null,
        				numeroCreditos=null,        				
        				programaEstudio=null,        				
        				denominacionGradoAcademicoTitulo=null,
        				textoResolucionRectoral=null;
        		List<String> objectDetails = new ArrayList<String>();
        		
        		
        		
        		if(item.getEstudiante()!=null){
        			
        			/*
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoNumero=item.getEstudiante().getTextoNumeroDocumento();
        			*/
        			fechaIngresanteMatricula=TypesUtil.getDefaultString(item.getEstudiante().getFechaIngresanteMatricula(), "dd/MM/yyyy");
        			egresadoCiclo=item.getEstudiante().getTextoCicloEgreso();
        			egresadoFecha=TypesUtil.getDefaultString(item.getEstudiante().getFechaTramiteConstancia(), "dd/MM/yyyy");	
        			actoFecha= TypesUtil.getDefaultString(item.getFechaSustentacionTesis(), "dd/MM/yyyy");      		
            		fechaAprobacionCU= TypesUtil.getDefaultString(item.getFechaAprobacioncu(), "dd/MM/yyyy");      		
            		anuoAprobacionCU=TypesUtil.getDefaultString(item.getFechaAprobacioncu(), "yyyy");

            		if(!TypesUtil.isEmptyString(item.getTextoResolucionRectoral())){
        				UNALMLogger.trace(log, method, "anuoAprobacionCU: "+anuoAprobacionCU);
        				textoResolucionRectoral=item.getTextoResolucionRectoral().replace("-"+anuoAprobacionCU+"-", "-");
        			}
            		
        			SCGTipoDocumentoEntity tipoDocumento = new SCGTipoDocumentoEntity();
                	tipoDocumento.setCodigoExterno(item.getEstudiante().getTextoCodigoExternoDocumento());
                	SCGTipoDocumentoEntity codigoTipoDocumento=this.datoGeneralService.getForUpdateTipoDocumento(tipoDocumento);
                	if(codigoTipoDocumento!=null){
                		if(codigoTipoDocumento.getFlagSunedu().equals("1")){
                			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
                			documentoNumero=item.getEstudiante().getTextoNumeroDocumento();
                		}
                	}
        			
        			if(item.getEstudiante().getPersona()!=null){
        				apellidoMaterno=item.getEstudiante().getPersona().getTextoMaterno();
        				apellidoPaterno=item.getEstudiante().getPersona().getTextoPaterno();
        				nombre=item.getEstudiante().getPersona().getTextoNombre();
        				sexo=item.getEstudiante().getPersona().getTextoSexo();
        				SCGPersonaTelefonoEntity  codigoPersonaTelefono = new SCGPersonaTelefonoEntity();
        				codigoPersonaTelefono.setPersona(new SCGPersonaEntity());
        				codigoPersonaTelefono.getPersona().setCodigo(item.getEstudiante().getPersona().getCodigo());;
        				codigoPersonaTelefono.setTipoTelefono(new SCGTipoTelefonoEntity());        				
        				codigoPersonaTelefono.getTipoTelefono().setCodigoExterno("F");        				
        				SCGPersonaTelefonoEntity personaTelefonoFijo = datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
        				if(personaTelefonoFijo != null ){
        					telefonoFijo = personaTelefonoFijo.getTextoNumeroTelefono();
        				}
        				codigoPersonaTelefono.getTipoTelefono().setCodigoExterno("C");        				
        				SCGPersonaTelefonoEntity personaTelefonoCelular = datoGeneralService.getForUpdateTelefono(codigoPersonaTelefono);
        				if(personaTelefonoCelular != null ){
        					telefonoCelular = personaTelefonoCelular.getTextoNumeroTelefono();
        				}
        			}
        		}
        		if(item.getGradoAcademico()!=null){
        			String tipo=item.getGradoAcademico().getCodigoExterno();
        			
        			
        			
        			if(tipo.equals("B")){
        				gradoTitulo=item.getGradoAcademico().getTextoNombre();
        				if(item.getEspecialidad() !=null){
                			facultadNombre =item.getEspecialidad().getFacultad().getTextoNombreEspanol();
                			//especialidad=item.getEspecialidad().getTextoNombreBachiller();
                			gradoTitulo=item.getGradoAcademico().getTextoNombre()+
        							" en "+
        							item.getEspecialidad().getTextoNombreBachiller();
                			
        				}/*
        				if(item.getModalidadGradoTitulo()!=null){
        					UNALMLogger.trace(log, method,"item.getModalidadGradoTitulo().getCodigoExterno(): "+ item.getModalidadGradoTitulo().getCodigoExterno());
        					if(item.getModalidadGradoTitulo().getCodigoExterno().equals("004")){
        						actoFecha=egresadoFecha;
        					}
        				}
        				*/
        			}else if (tipo.equals("T")){
//        				gradoTitulo=item.getEspecialidad().getTextoNombreDenominacion();
        				if( item.getFlagDiplomaSexo() != null ){
            				gradoTitulo=item.getEspecialidad().getTextoNombreFemenino();

        				}else{
        					gradoTitulo=item.getEspecialidad().getTextoNombreDenominacion();
        				}
        				
        				if(item.getEspecialidad() !=null){
                			facultadNombre =item.getEspecialidad().getFacultad().getTextoNombreEspanol();
                			especialidad="";
                		}
        			}else{
        				if(item.getEspecialidadPostgrado()!=null){
        					facultadNombre="";
                			especialidadPostgrado =item.getEspecialidadPostgrado().getFacultad().getTextoNombreEspanol();
                			especialidad =item.getEspecialidadPostgrado().getTextoNombreEspanol();
                		}
        				//gradoTitulo=item.getGradoAcademico().getTextoNombre();
        			}
        			
        			if(item.getEspecialidad() !=null){
            			especialidadNombre = item.getEspecialidad().getTextoNombreCarrera();
            			
            			
            		}
            		if(item.getEspecialidadPostgrado()!=null){
            			especialidadNombre = item.getEspecialidadPostgrado().getTextoNombreEspanol();
            		}
        			UNALMLogger.trace(log, method, "especialidadNombre "+especialidadNombre);

        			if(item.getUniversidadBachiller()!=null){
            			if(item.getUniversidadBachiller().getCodigoExterno().equals("007")){
            				universidadBachiller="007";
            			}else{
            				universidadBachiller=item.getUniversidadBachiller().getTextoNombre();
            			}
            			if(!item.getUniversidadBachiller().getPais().getCodigoExterno().equals("PE")){
            				universidadExtranjero = item.getUniversidadBachiller().getTextoNombre();
            				paisExtranjero = item.getUniversidadBachiller().getPais().getTextoNombre();
            				procedenciaGradoExtranjero = item.getTextoProcedenciaGradoExtranjero();
            			}
        		
            		}
        			denominacionGradoAcademicoTitulo=item.getGradoAcademico().getTextoNombre();
        			abrebiadoGradoTitulo=item.getGradoAcademico().getCodigoExterno();
        			
        			if(item.getFlagDiplomaSexo() != null){
        				if( denominacionGradoAcademicoTitulo.equalsIgnoreCase("Maestro" ) ){
        					denominacionGradoAcademicoTitulo = "Maestra";
        				}else if( denominacionGradoAcademicoTitulo.equalsIgnoreCase("Doctor" ) ){
        					denominacionGradoAcademicoTitulo = "Doctora";
        				}
    				}
        			if(tipo.equals("M")){
//        				if(item.getGradoAcademico().getTextoNombre().equals("Magister Scientiae")){
//        					gradoTitulo=item.getGradoAcademico().getTextoNombre().toUpperCase() + " EN "+especialidad; 					
//        				}else{
//        					gradoTitulo = "MAESTRO EN "+especialidad; 
//        				}
        				gradoTitulo=denominacionGradoAcademicoTitulo + " EN "+especialidad; 
                		if(item.getUniversidadBachiller()!=null){
	            			if(item.getUniversidadBachiller().getCodigoExterno().equals("007")){
	            				universidadBachiller="007";
	            			}else{
	            				universidadBachiller=item.getUniversidadBachiller().getTextoNombre();
//	            				universidadExtranjero = item.getUniversidadBachiller().getTextoNombre();
//	            				paisExtranjero = item.getUniversidadBachiller().getPais().getTextoNombre();
	            			
	            			}
//	            			if(!item.getUniversidadBachiller().getPais().getCodigoExterno().equals("PE")){
//	            				universidadExtranjero = item.getUniversidadBachiller().getTextoNombre();
//	            				paisExtranjero = item.getUniversidadBachiller().getPais().getTextoNombre();
//	            				procedenciaGradoExtranjero = item.getTextoProcedenciaGradoExtranjero();
//	            			}
            		
                		}
        			}else if( tipo.equals("D") ){
//        				gradoTitulo = "DOCTORADO EN "+especialidad; 
        				gradoTitulo = denominacionGradoAcademicoTitulo+" EN "+especialidad; 
        				if(item.getUniversidadMaestria()!=null){
                			universidadMaestria=item.getUniversidadMaestria().getTextoNombre();
//                			universidadExtranjero = item.getUniversidadMaestria().getTextoNombre();
//            				paisExtranjero = item.getUniversidadMaestria().getPais().getTextoNombre();
                			universidadExtranjero = universidadMaestria;
//                			universidadExtranjero = item.getUniversidadMaestria().getTextoNombre();
            				paisExtranjero = item.getUniversidadMaestria().getPais().getTextoNombre();
            				procedenciaGradoExtranjero = item.getTextoProcedenciaMaestriaExtranjero();
        				}
        			}
        		}
        		if(item.getModalidadGradoTitulo()!=null){
        			modalidadGradoTitulo= item.getModalidadGradoTitulo().getTextoNombre();
        		}
        		if(item.getModalidadEstudio()!=null){
        			modalidadEstudio= item.getModalidadEstudio().getCodigoExterno();
        		}
        		if(item.getFlagDuplicado()!=null){
        			if(item.getFlagDuplicado().equals("0")){
            			tipoEmision="O";
            		}else if(item.getFlagDuplicado().equals("1")){
            			tipoEmision="D";
            		}
        		}
        		
        		if(item.getAutoridadRegistroRector()!=null){
        			nombreRector=item.getAutoridadRegistroRector().getTextoNombreAutoridad();
//        			codigoRector=item.getAutoridadRegistroRector().getCargo().getTextoNombre();
        			codigoRector="1";
        			nombreRectorSunedu = this.setCodigoPeriodoAutoridadSunedo( item,item.getAutoridadRegistroRector().getCodigo() );
        			UNALMLogger.trace(log, method, "after nombreRectorSunedu "+nombreRectorSunedu);
        			if( TypesUtil.isEmptyString(nombreRectorSunedu) ){
        				nombreRectorSunedu = nombreRector;
        			}
        			UNALMLogger.trace(log, method, "before nombreRectorSunedu "+nombreRectorSunedu);

        			
        		}
        		if(item.getAutoridadRegistroSecretarioGeneral()!=null){
        			nombreSecretarioGeneral=item.getAutoridadRegistroSecretarioGeneral().getTextoNombreAutoridad();
//        			codigoSecretarioGeneral=item.getAutoridadRegistroSecretarioGeneral().getCargo().getTextoNombre();
        			codigoSecretarioGeneral="2";        			
        			nombreSecretarioGeneralSunedu = this.setCodigoPeriodoAutoridadSunedo( item, item.getAutoridadRegistroSecretarioGeneral().getCodigo() );
        			UNALMLogger.trace(log, method, "after nombreSecretarioGeneralSunedu "+nombreSecretarioGeneralSunedu);
        			if( TypesUtil.isEmptyString(nombreSecretarioGeneralSunedu) ){
        				nombreSecretarioGeneralSunedu = nombreSecretarioGeneral;
        			}
        			UNALMLogger.trace(log, method, "before nombreSecretarioGeneralSunedu "+nombreSecretarioGeneralSunedu);
        		
        		}
        		if(item.getAutoridadRegistroDecano()!=null){
        			if(!TypesUtil.isEmptyString(item.getAutoridadRegistroDecano().getTextoNombreAutoridad())){
        				nombreAutoridad=item.getAutoridadRegistroDecano().getTextoNombreAutoridad();
        				//tipoAutoridad =item.getAutoridadRegistroDecano().getCargo().getTextoNombre();
//        				tipoAutoridad ="Decano";
        				tipoAutoridad ="3";
        				nombreAutoridadSunedu = this.setCodigoPeriodoAutoridadSunedo( item, item.getAutoridadRegistroDecano().getCodigo() );
            			UNALMLogger.trace(log, method, "after nombreAutoridadSunedu "+nombreAutoridadSunedu);
            			if( TypesUtil.isEmptyString(nombreAutoridadSunedu) ){
            				nombreAutoridadSunedu = nombreAutoridad;
            			}
            			UNALMLogger.trace(log, method, "before nombreAutoridadSunedu "+nombreAutoridadSunedu);
        			
        			}
        		}else if(item.getAutoridadRegistroDirectorPostgrado()!=null){
        			nombreAutoridad=item.getAutoridadRegistroDirectorPostgrado().getTextoNombreAutoridad();
//    				tipoAutoridad =item.getAutoridadRegistroDirectorPostgrado().getCargo().getTextoNombre();
    				tipoAutoridad ="6";
    				
    				nombreAutoridadSunedu = this.setCodigoPeriodoAutoridadSunedo( item, item.getAutoridadRegistroDirectorPostgrado().getCodigo() );
        			UNALMLogger.trace(log, method, "after nombreAutoridadSunedu "+nombreAutoridadSunedu);
        			if( TypesUtil.isEmptyString(nombreAutoridadSunedu) ){
        				nombreAutoridadSunedu = nombreAutoridad;
        			}
        			UNALMLogger.trace(log, method, "before nombreAutoridadSunedu "+nombreAutoridadSunedu);
        		
        		
        		}
        		if(item.getAgendaGrupo()!=null){
        			agendaGrupo=item.getAgendaGrupo().getTextoNombre();
        		}
        		/*
        		if(item.getUniversidadBachiller()!=null){
        			universidadBachiller=item.getUniversidadBachiller().getTextoNombre();
        		}
        		*/
        		if(item.getUniversidadMaestria()!=null){
        			universidadMaestria=item.getUniversidadMaestria().getTextoNombre();
        		}
        		
        		if(item.getUniversidadBachiller()!=null){
        			if(item.getUniversidadBachiller().getCodigoExterno().equals("007")){
        				universidadBachiller="007";
        			}else{
        				universidadBachiller=item.getUniversidadBachiller().getTextoNombre();
        			}
        		
        		}
        		if(item.getNumeroFolio()!=null){
    				Formatter numeroFolioFormat = new Formatter();
    				numeroFolioFormat.format("%03d",item.getNumeroFolio());
    				UNALMLogger.trace(log, method, "numeroFolioFormat: "+numeroFolioFormat);
    				numeroFolio=numeroFolioFormat.toString();
    			}
        		if(item.getNumeroRegistro()!=null){
    				Formatter numeroRegistroFormat = new Formatter();
    				numeroRegistroFormat.format("%03d",item.getNumeroRegistro());
    				UNALMLogger.trace(log, method, "numeroLibroFormat: "+numeroRegistroFormat);
    				numeroRegistro=numeroRegistroFormat.toString();
    			}
				UNALMLogger.trace(log, method, "item.getEstudianteRegistroPadre(): "+item.getEstudianteRegistroPadre());
        		if(item.getEstudianteRegistroPadre() != null){
    				SCGEstudianteRegistroEntity registroCodigo = new SCGEstudianteRegistroEntity();
    				registroCodigo.setNumeroRegistro(item.getEstudianteRegistroPadre() );
    				if(item.getGradoAcademico()!=null){
    					registroCodigo.setGradoAcademico(item.getGradoAcademico());
    				}
    				UNALMLogger.trace(log, method, "registroCodigo: "+registroCodigo);
    				SCGEstudianteRegistroEntity registroOriginal  = this.gradoTituloService.getForUpdateEstudianteRegistro(registroCodigo);
    				UNALMLogger.trace(log, method, "registroOriginal: "+registroOriginal);
    				if(registroOriginal!=null){
    					if(registroOriginal.getFechaAprobacioncu()!=null){
    						fechaAprobacionOriginal= TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "dd/MM/yyyy");      	
    						UNALMLogger.trace(log, method, "fechaAprobacionOriginal: "+fechaAprobacionOriginal);
    					}
    					if(registroOriginal.getProgramaEstudio()!= null){
            				UNALMLogger.trace(log, method, "registroOriginal.getProgramaEstudio(): "+registroOriginal.getProgramaEstudio());
            				UNALMLogger.trace(log, method, "registroOriginal.getProgramaEstudio().getCodigo(): "+registroOriginal.getProgramaEstudio().getCodigo());
                			SCGProgramaEstudioEntity programaEstudioTexto = this.gradoTituloService.getForUpdateProgramaEstudio(registroOriginal.getProgramaEstudio());
            				UNALMLogger.trace(log, method, "programaEstudioTexto: "+programaEstudioTexto);
            				if( programaEstudioTexto != null){
            					UNALMLogger.trace(log, method, "programaEstudioTexto.getCodigoExterno(): "+programaEstudioTexto.getCodigoExterno());
            					if( programaEstudioTexto.getCodigoExterno().equals("OTROS") ){
            						programaEstudio = registroOriginal.getTextoNombreProgramaEstudio();
            					}else{
            						programaEstudio = programaEstudioTexto.getTextoNombre();
            					}
                				UNALMLogger.trace(log, method, "programaEstudio: "+programaEstudio);

            					
            				}
            			}
    					if(abrebiadoGradoTitulo.equals("M")||abrebiadoGradoTitulo.equals("D")){
    						if(registroOriginal.getFechaEgresoPosgrado() != null){
            					egresadoFecha = TypesUtil.getDefaultString(registroOriginal.getFechaEgresoPosgrado(), "dd/MM/yyyy");
    						}else{
    							egresadoFecha = "";
    						}
    						if( registroOriginal.getFechaMatriculaPosgrado() != null){
            					fechaIngresanteMatricula = TypesUtil.getDefaultString(registroOriginal.getFechaMatriculaPosgrado(), "dd/MM/yyyy");
            				}else{
            					fechaIngresanteMatricula = "";
            				}
    					}
    				}
    			}
        		
        		if(item.getNumeroCreditos() != null){
        			numeroCreditos = item.getNumeroCreditos().toString();
        		}

        		if(item.getEspecialidad() !=null){
        			especialidadNombre = item.getEspecialidad().getTextoNombreCarrera();
        			
        			
        		}
        		if(item.getEspecialidadPostgrado()!=null){
        			especialidadNombre = item.getEspecialidadPostgrado().getTextoNombreEspanol();
        		}
        		UNALMLogger.trace(log, method, "especialidadNombre "+especialidadNombre);

        		
        		count++;
        		objectDetails.add(TypesUtil.getDefaultString(count));
        		objectDetails.add("007");
    			objectDetails.add("UNIVERSIDAD NACIONAL AGRARIA LA MOLINA");
        		objectDetails.add(TypesUtil.getDefaultUpperCase(fechaIngresanteMatricula));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(facultadNombre));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(especialidadNombre));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(especialidadPostgrado));
        		objectDetails.add(egresadoFecha);
        		objectDetails.add(TypesUtil.getDefaultUpperCase(apellidoPaterno));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(apellidoMaterno));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombre));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(sexo));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(documentoTipo));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(documentoNumero));    		
        		
        		
        		
        		objectDetails.add(TypesUtil.getDefaultUpperCase(universidadBachiller));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(denominacionGradoAcademicoTitulo));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(gradoTitulo));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(item.getTextoSegundaEspecialidad()));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(item.getTextoNombreTesis()));
        		objectDetails.add(numeroCreditos);
        		objectDetails.add(item.getTextoRegistroMetadato());
        		objectDetails.add(programaEstudio);
        		objectDetails.add("");//PROC_TITULO_PED
        		objectDetails.add(TypesUtil.getDefaultUpperCase(modalidadGradoTitulo));
        		
        		
        		
        		objectDetails.add(TypesUtil.getDefaultUpperCase(modalidadEstudio));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(abrebiadoGradoTitulo));      		
        		objectDetails.add("");
        		objectDetails.add("");
        		objectDetails.add("");
        		objectDetails.add(TypesUtil.getDefaultUpperCase(textoResolucionRectoral));
        		objectDetails.add(fechaAprobacionCU);
        		objectDetails.add(fechaAprobacionOriginal);
        		objectDetails.add(fechaAprobacionCU);//DIPL_FEC_DUP
        		objectDetails.add(TypesUtil.getDefaultUpperCase(item.getTextoCodigoBarra()));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(tipoEmision));
        		
        		
        		if(item.getNumeroLibro()!=null){
        			objectDetails.add(Long.toString(item.getNumeroLibro()));
        		}else{
        			objectDetails.add("");
        		}
        		if(item.getNumeroFolio()!=null){
        			objectDetails.add(numeroFolio);
        		}else{
        			objectDetails.add("");
        		}
        		if(item.getNumeroRegistro()!=null){
        			/*
        			Formatter fmt = new Formatter();
        			fmt = new Formatter();
        			fmt.format("%03d",item.getNumeroRegistro());
        			UNALMLogger.trace(log, method,"fmt: "+ fmt);
        			formatoNumeroResolucion =fmt+"-"+anuoAprobacionCU+"-CU-UNALM";
        			UNALMLogger.trace(log, method,"formatoNumeroResolucion: "+ formatoNumeroResolucion);
        			objectDetails.add(formatoNumeroResolucion);
        			*/
        			objectDetails.add(numeroRegistro);
        		}else{
        			objectDetails.add("");
        		}
        		objectDetails.add(TypesUtil.getDefaultUpperCase(codigoRector));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombreRectorSunedu));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombreRector));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(codigoSecretarioGeneral));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombreSecretarioGeneralSunedu));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombreSecretarioGeneral));        		
        		objectDetails.add(TypesUtil.getDefaultUpperCase(tipoAutoridad));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombreAutoridadSunedu));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(nombreAutoridad));
        		objectDetails.add( TypesUtil.getDefaultUpperCase(paisExtranjero ));//PROC_PAIS_EXT
        		objectDetails.add( TypesUtil.getDefaultUpperCase(universidadExtranjero ));//PROC_UNV_EXT
        		objectDetails.add( TypesUtil.getDefaultUpperCase(procedenciaGradoExtranjero ));//PROC_GRADO_EXT
        		objectDetails.add("");//REG_OFICIO
        		objectDetails.add("");//FEC_MAT_PROG
        		objectDetails.add("");//FEC_INICIO_PROG
        		objectDetails.add("");//FEC_FIN_PROG
        		objectDetails.add(TypesUtil.getDefaultUpperCase(especialidad));
        		objectDetails.add(actoFecha);
        		//objectDetails.add("");
        		
        		
        		//objectDetails.add(agendaGrupo);
        		objectDetails.add(TypesUtil.getDefaultUpperCase(universidadMaestria));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(telefonoFijo));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(telefonoCelular));
        		objectDetails.add(egresadoCiclo);

        		masterList.add(objectDetails);
        	}
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Exception: ", e);     
		}
		
		List<String> myReportHeader = getTrainingReportHeader();
		masterList.add(0, myReportHeader);
		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, masterList, "ReporteDuplicado");
		ExcelUtils.generateReport(response, workBook, "PadronDuplicado");
		return null;
	}
	private String setCodigoPeriodoAutoridadSunedo( SCGEstudianteRegistroEntity registro, Long codigo ){
		final String method = "setCodigoPeriodoAutoridadSunedo";
		final String params = "SCGEstudianteRegistroEntity registro";
		UNALMLogger.entry(log, method,params,new Object[]{registro});
		String result = null;
		SCGPeriodoEntity codigoAutoridad = new SCGPeriodoEntity();
		codigoAutoridad.setAutoridadRegistro(new SCGAutoridadRegistroEntity());
		codigoAutoridad.getAutoridadRegistro().setCodigo( codigo );;
		UNALMLogger.trace(log, method, "codigo "+ codigo );

		List<SCGPeriodoEntity> listPeridos = this.autoridadService.getListPeriodo( codigoAutoridad );
		UNALMLogger.trace(log, method, "listPeridos.size()2 "+listPeridos.size());
		
		if( listPeridos.size() > 0 ){
			for ( SCGPeriodoEntity lista : listPeridos ){
    			UNALMLogger.trace(log, method, "lista.getFechaFinal() "+ lista.getFechaFinal() );
    			UNALMLogger.trace(log, method, "lista.getFechaInicio() "+ lista.getFechaInicio() );
    			UNALMLogger.trace(log, method, "item.getFechaAprobacioncu() "+ registro.getFechaAprobacioncu() );

				if( (lista.getFechaInicio().compareTo( registro.getFechaAprobacioncu()) *
						registro.getFechaAprobacioncu().compareTo(  lista.getFechaFinal() )) >= 0){
					if( !TypesUtil.isEmptyString(lista.getTextoCodigoPeriodo()) ) {
						result = lista.getTextoCodigoPeriodo();
					}
				}
			}
		}
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	private List<String> getTrainingReportHeader() {
		//List<String> headerList = new ArrayList<String>(15);
		List<String> headerList = new ArrayList<String>(45);
		/*
		headerList.add("CODUNIV");
		headerList.add("RAZ_SOC");
		headerList.add("MATRI_FEC");
		headerList.add("FAC_NOM");
		headerList.add("ESC_CARR");
		headerList.add("ESC_POST");
		headerList.add("APEPAT");
		headerList.add("APEMAT");
		headerList.add("NOMBRES");
		headerList.add("SEXO");
		headerList.add("DOCU_TIP");
		headerList.add("DOCU_NUM");
		headerList.add("EGRES_FEC");
		headerList.add("PROC_BACH");
		headerList.add("GRAD_TITU");
		headerList.add("ESPECIALIDAD");
		headerList.add("ABRE_GYT");
		headerList.add("ACTO_TIP");
		headerList.add("ACTO_FEC");
		headerList.add("TESIS");
		headerList.add("MODALIDAD");
		headerList.add("PROC_REV_PAIS");
		headerList.add("PROC_REV_UNIV");
		headerList.add("PROC_REV_GRADO_EXT");
		headerList.add("RESO_NUM");
		headerList.add("RESO_FEC");
		headerList.add("DIPL_FEC");
		headerList.add("DIPL_NUM");
		headerList.add("DIP_TIP_EMI");
		headerList.add("REG_LIBRO");
		headerList.add("REG_FOLIO");
		headerList.add("REG_REGISTRO");
		headerList.add("CARGO1");
		headerList.add("AUTORIDAD1");
		headerList.add("CARGO2");
		headerList.add("AUTORIDAD2");
		headerList.add("CARGO3");
		headerList.add("AUTORIDAD3");
		headerList.add("REG_OFICIO");
		//headerList.add("Agenda");
		headerList.add("Procedencia de Maestria");
		headerList.add("Fijo");
		headerList.add("Celular");
		headerList.add("Ciclo de Egreso");
		*/
		headerList.add("ID");
		headerList.add("CODUNIV");
		headerList.add("RAZ_SOC");
		headerList.add("MATRI_FEC");
		headerList.add("FAC_NOM");
		headerList.add("CARR_PROG");
		headerList.add("ESC_POS");
		headerList.add("EGRES_FEC");
		headerList.add("APEPAT");
		headerList.add("APEMAT");
		headerList.add("NOMBRE");
		headerList.add("SEXO");
		headerList.add("DOCU_TIP");
		headerList.add("DOCU_NUM");
		
		
		
		headerList.add("PROC_BACH");
		headerList.add("GRAD_TITU");
		headerList.add("DEN_GRAD");
		headerList.add("SEG_ESP");
		headerList.add("TRAB_INV");
		headerList.add("NUM_CRED");
		headerList.add("REG_METADATO");
		headerList.add("PROG_ESTU");
		headerList.add("PROC_TITULO_PED");
		headerList.add("MOD_OBT");
		
		headerList.add("MOD_EST");
		headerList.add("ABRE_GYT");
		headerList.add("PROC_REV_PAIS");
		headerList.add("PROC_REV_UNIV");
		headerList.add("PROC_REV_GRADO");
		headerList.add("RESO_NUM");
		headerList.add("RESO_FEC");
		headerList.add("DIPL_FEC_ORG");
		headerList.add("DIPL_FEC_DUP");
		headerList.add("DIPL_NUM");
		headerList.add("DIPL_TIP_EMI");
		
		
		headerList.add("REG_LIBRO");
		headerList.add("REG_FOLIO");
		headerList.add("REG_REGISTRO");
		headerList.add("CARGO1");
		headerList.add("AUTORIDAD1");
		headerList.add("AUTORIDAD1_SUNEDU");
		headerList.add("CARGO2");
		headerList.add("AUTORIDAD2");
		headerList.add("AUTORIDAD2_SUNEDU");
		headerList.add("CARGO3");
		headerList.add("AUTORIDAD3");
		headerList.add("AUTORIDAD3_SUNEDU");

		headerList.add("PROC_PAIS_EXT");
		headerList.add("PROC_UNIV_EXT");
		headerList.add("PROC_GRADO_EXT");
		headerList.add("REG_OFICIO");
		headerList.add("FEC_MAT_PROG");
		headerList.add("FEC_INICIO_PROG");
		headerList.add("FEC_FIN_PROG");		
		headerList.add("ESPECIALIDAD");
		headerList.add("ACTO_FEC");
		//headerList.add("REG_OFICIO");
		//headerList.add("Agenda");
		headerList.add("Procedencia de Maestria");
		headerList.add("Fijo");
		headerList.add("Celular");
		headerList.add("Ciclo de Egreso");

		return headerList;
	}
	@RequestMapping(value = "/exportDiploma")
	public void exportDiploma(HttpServletRequest request, HttpServletResponse response,SCGEstudianteRegistroEntity entity) throws IOException {

		final String method = "exportAhorroList";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=DuplicadoPDF.pdf");
		
		UNALMLogger.trace(log, method,"entity.getCodigo(): "+ entity.getCodigo());
		String nombreCompleto=null,
			   facultadEspecial=null,
			   facultad=null,
			   rector=null,
			   secretarioGeneral=null,
			   decano=null,
			   documentoTipo=null,
			   documentoNumero=null,
			   abrebiadoGradoTitulo=null,
			   modalidadGradoTitulo=null,
			   modalidadEstudio=null,
			   tipoEmision=null,
			   pathReport=null,
			   gradoAcademico=null,
			   textoFechaAprobacioncu=null,
			   anouCU=null,
			   textoFechaAprobacioncuOriginal=null,
			   anouConsejoUniversitarioOriginal=null,
			   diaConsejoUniversitarioOriginal=null,
			   mesConsejoUniversitarioOriginal=null,
			   mesConsejoUniversitarioOriginalRaw=null,
			   anouConsejoUniversitario=null,
			   diaConsejoUniversitario=null,
			   mesConsejoUniversitario=null,
			   mesConsejoUniversitarioRaw=null,
		       textoResolucionRectoral=null,
			   numeroLibro=null,
			   numeroFolio=null,
			   numeroRegistro=null,
			   textoMaestro="",
			   textoDoctor="";
			   
					   
				
		try {
        	JSONObject rootJSON = new JSONObject();
			JSONObject itemJSON=new JSONObject();	
			
			SCGEstudianteRegistroEntity estudiante = this.duplicadoService.getForUpdateDuplicado(entity);
			if(estudiante.getGradoAcademico()!=null){
    			abrebiadoGradoTitulo=estudiante.getGradoAcademico().getCodigoExterno();
    			gradoAcademico=estudiante.getGradoAcademico().getTextoNombre();
    			if(gradoAcademico.equals("Maestro")){
    				/*
    				textoMaestro="Maestro";
    				gradoAcademico="Magíster Scientiae";
    				*/
    				gradoAcademico="Magister Scientiae";
    				

    			}else if(gradoAcademico.equals("Doctor")){
    				/*
    				textoDoctor="Doctor";
    				gradoAcademico="Doctoris Philosophiae";
    				*/
    				gradoAcademico="Doctor";
    				

    			}
    			
    			if( estudiante.getFlagDiplomaSexo() != null && abrebiadoGradoTitulo.equals("M")  ){
					textoMaestro = "Maestra" ;
				}else{
					textoMaestro = "Maestro";
				}
    			
    			if( estudiante.getFlagDiplomaSexo() != null && abrebiadoGradoTitulo.equals("D")  ){
					textoDoctor = "Doctora" ;
				}else{
					textoDoctor = "Doctor";
				}
    			UNALMLogger.trace(log, method,"abrebiadoGradoTitulo: "+ abrebiadoGradoTitulo);
    			if(abrebiadoGradoTitulo.equals("D")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/PosgradoDoctorDuplicadoReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("M")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/PosgradoMaestriaDuplicadoReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("B")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/BachillerDuplicadoReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("T")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/TituloDuplicadoReport.jrxml";
    			}
			}
			
			if(estudiante.getEstudiante()!=null){
				nombreCompleto =estudiante.getEstudiante().getTextoNombreCompleto();
					if(estudiante.getGradoAcademico()!=null){
		    			if(abrebiadoGradoTitulo.equals("M")||abrebiadoGradoTitulo.equals("D")){
		    				if(estudiante.getEspecialidadPostgrado()!=null){
		    					facultad="Por Cuanto: La "+estudiante.getEspecialidadPostgrado().getFacultad().getTextoNombreEspanol()+" aprobó y";
			    				facultadEspecial =estudiante.getEspecialidadPostgrado().getTextoNombreEspanol();
		    				}
		    			}else if(abrebiadoGradoTitulo.equals("B")){
		    				if(estudiante.getEspecialidad()!=null){
		    					facultadEspecial =estudiante.getEspecialidad().getTextoNombreBachiller();
								facultad="Por Cuanto: La Facultad de "+estudiante.getEspecialidad().getFacultad().getTextoNombreEspanol();
		    				}
							
						}else if(abrebiadoGradoTitulo.equals("T")){
		    				if(estudiante.getEspecialidad()!=null){
//		    					facultadEspecial =estudiante.getEspecialidad().getTextoNombreDenominacion();
		    					if( estudiante.getFlagDiplomaSexo() != null ){
		    						facultadEspecial =estudiante.getEspecialidad().getTextoNombreFemenino();
		    					}else{
		    						facultadEspecial =estudiante.getEspecialidad().getTextoNombreDenominacion();
		    					}
		    					
								facultad="Por Cuanto: La Facultad de "+estudiante.getEspecialidad().getFacultad().getTextoNombreEspanol()+" aprobó y";
		    				}
							
						}
					}
				
			}
				
			if(estudiante.getAutoridadRegistroRector()!=null){
				rector= estudiante.getAutoridadRegistroRector().getTextoNombreAutoridad();
				
			}
			if(estudiante.getAutoridadRegistroSecretarioGeneral()!=null){
				secretarioGeneral = estudiante.getAutoridadRegistroSecretarioGeneral().getTextoNombreAutoridad();
			}
			
			if(estudiante.getAutoridadRegistroDecano()!=null){
				decano=estudiante.getAutoridadRegistroDecano().getTextoNombreAutoridad();
			}
			if(estudiante.getAutoridadRegistroDirectorPostgrado()!=null){
				decano=estudiante.getAutoridadRegistroDirectorPostgrado().getTextoNombreAutoridad();
			}
			if(estudiante.getEstudiante() != null){
				documentoTipo= estudiante.getEstudiante().getTextoCodigoExternoDocumento();
    			documentoNumero=estudiante.getEstudiante().getTextoNumeroDocumento();
			};
			
			if(estudiante.getModalidadGradoTitulo()!=null){
    			modalidadGradoTitulo= estudiante.getModalidadGradoTitulo().getTextoNombre();
    			if( modalidadGradoTitulo.equals("Automático") ){
    				modalidadGradoTitulo = "Bachiller Automático";
    			}
    		}
			if(estudiante.getModalidadEstudio()!=null){
    			modalidadEstudio= estudiante.getModalidadEstudio().getCodigoExterno();
    		}
			if(estudiante.getFlagDuplicado()!=null){
    			if(estudiante.getFlagDuplicado().equals("0")){
        			tipoEmision="O";
        		}else if(estudiante.getFlagDuplicado().equals("1")){
        			tipoEmision="D";
        		}
    		}
			anouCU=TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "MMMM");
			
			if(estudiante.getEstudianteRegistroPadre() != null){
				SCGEstudianteRegistroEntity registroCodigo = new SCGEstudianteRegistroEntity();
				registroCodigo.setNumeroRegistro(estudiante.getEstudianteRegistroPadre() );
				if(estudiante.getGradoAcademico()!=null){
					registroCodigo.setGradoAcademico(estudiante.getGradoAcademico());
				}
				SCGEstudianteRegistroEntity registroOriginal  = this.gradoTituloService.getForUpdateEstudianteRegistro(registroCodigo);
				if(registroOriginal!=null){
					if(registroOriginal.getFechaAprobacioncu()!=null){
						
						textoFechaAprobacioncuOriginal = TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "dd")+" de "+
								anouCU.substring(0, 1).toUpperCase() + anouCU.substring(1)+" de "+
								TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "yyyy");
						
						
						diaConsejoUniversitarioOriginal=TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "dd");
						mesConsejoUniversitarioOriginalRaw=TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "MMMM");
						if( mesConsejoUniversitarioOriginalRaw.equals("septiembre") ){
							mesConsejoUniversitarioOriginalRaw = "setiembre";
						}
						UNALMLogger.trace(log, method, "mesConsejoUniversitarioOriginalRaw: "+mesConsejoUniversitarioOriginalRaw);
						mesConsejoUniversitarioOriginal=" de "+mesConsejoUniversitarioOriginalRaw.substring(0, 1).toUpperCase() + mesConsejoUniversitarioOriginalRaw.substring(1)+" de ";
						anouConsejoUniversitarioOriginal=TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "yyyy");
						textoFechaAprobacioncuOriginal=diaConsejoUniversitario+mesConsejoUniversitario+anouConsejoUniversitario;
						
					}
				}	
			}
			if(!TypesUtil.isEmptyString(estudiante.getTextoResolucionRectoral())){
				String anuoAprobacionCU=null;
				if(estudiante.getFechaAprobacioncu()!=null){
					anuoAprobacionCU=TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "yyyy");
				}
				UNALMLogger.trace(log, method, "anuoAprobacionCU: "+anuoAprobacionCU);
				textoResolucionRectoral=estudiante.getTextoResolucionRectoral().replace("-"+anuoAprobacionCU+"-", "-");
			}
			if(estudiante.getNumeroFolio()!=null){
				Formatter numeroFolioFormat = new Formatter();
				numeroFolioFormat.format("%03d",estudiante.getNumeroFolio());
				UNALMLogger.trace(log, method, "numeroFolioFormat: "+numeroFolioFormat);
				numeroFolio=numeroFolioFormat.toString();
			}
			if(estudiante.getNumeroRegistro()!=null){
				Formatter numeroRegistroFormat = new Formatter();
				numeroRegistroFormat.format("%03d",estudiante.getNumeroRegistro());
				UNALMLogger.trace(log, method, "numeroLibroFormat: "+numeroRegistroFormat);
				numeroRegistro=numeroRegistroFormat.toString();
			}
			
			/*
			anouCU=TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "MMMM");
			
			textoFechaAprobacioncu= TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "dd")+" de "+
					anouCU.substring(0, 1).toUpperCase() + anouCU.substring(1)+" del "+
					TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "yyyy");
			*/
			
			diaConsejoUniversitario=TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "dd");
			mesConsejoUniversitarioRaw=TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "MMMM");
			if( mesConsejoUniversitarioRaw.equals("septiembre") ){
				mesConsejoUniversitarioRaw = "setiembre";
			}
			UNALMLogger.trace(log, method, "mesConsejoUniversitarioRaw: " + mesConsejoUniversitarioRaw);
			mesConsejoUniversitario=" de "+mesConsejoUniversitarioRaw.substring(0, 1).toUpperCase() + mesConsejoUniversitarioRaw.substring(1)+" de ";
			anouConsejoUniversitario=TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "yyyy");
			textoFechaAprobacioncu=diaConsejoUniversitario+mesConsejoUniversitario+anouConsejoUniversitario;
			
			itemJSON.put("textoNombreCompleto", nombreCompleto);
			itemJSON.put("facultadEspecial",facultadEspecial);
			itemJSON.put("facultad", facultad);
			itemJSON.put("textoFechaAprobacioncu",textoFechaAprobacioncu);
			
			itemJSON.put("diaConsejoUniversitario",diaConsejoUniversitario);
			itemJSON.put("mesConsejoUniversitario",mesConsejoUniversitario);
			itemJSON.put("anouConsejoUniversitario",anouConsejoUniversitario);
			
			itemJSON.put("textoFechaAprobacioncuOriginal",textoFechaAprobacioncuOriginal);
			
			itemJSON.put("diaConsejoUniversitarioOriginal",diaConsejoUniversitarioOriginal);
			itemJSON.put("mesConsejoUniversitarioOriginal",mesConsejoUniversitarioOriginal);
			itemJSON.put("anouConsejoUniversitarioOriginal",anouConsejoUniversitarioOriginal);
			
			itemJSON.put("fechaAprobacioncu",TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "dd/MM/yyyy"));
			itemJSON.put("decano", decano);
			itemJSON.put("rector",rector);
			itemJSON.put("secretarioGeneral",secretarioGeneral);
			
			itemJSON.put("codigoUniverisdad", "007");
			itemJSON.put("textoCodigoExternoDocumento",documentoTipo);
			itemJSON.put("textoNumeroDocumento", documentoNumero);
			itemJSON.put("abrebiadoGradoTitulo",abrebiadoGradoTitulo);
			itemJSON.put("modalidadGradoTitulo",modalidadGradoTitulo);
			itemJSON.put("modalidadEstudio", modalidadEstudio);
			itemJSON.put("textoResolucionRectoral",textoResolucionRectoral);
			itemJSON.put("tipoEmision",tipoEmision);
			itemJSON.put("numeroLibro",  estudiante.getNumeroLibro());
			itemJSON.put("numeroFolio",  numeroFolio);
			itemJSON.put("numeroRegistro", numeroRegistro);
			itemJSON.put("gradoAcademico",gradoAcademico);
			itemJSON.put("textoMaestro",textoMaestro);
			itemJSON.put("textoDoctor",textoDoctor);
			
			rootJSON.put("data", itemJSON);
			String reportData=rootJSON.toString();
			
			UNALMLogger.trace(log, method, "reportData: "+reportData);
			
			Map<String, Object> reportParameters=new HashMap<String,Object>();
			
			String requestURL=request.getRequestURL().toString();
			String serverName=request.getServerName();
			String scheme=request.getScheme();
			UNALMLogger.trace(log, method, "requestURL: "+requestURL);
			UNALMLogger.trace(log, method, "serverName: "+serverName);
			UNALMLogger.trace(log, method, "scheme: "+scheme);
			//String basePath=scheme+"://"+serverName+"/static";
			String basePath=scheme+"://"+serverName+":8080"+"/static";
			String pathLogo =  basePath+ "/images/logo/escudo.jpg";
			UNALMLogger.trace(log, method, "pathLogo:"+pathLogo);
          
			reportParameters.put("P_LOGO", pathLogo);
			
			InputStream is = new ByteArrayInputStream(reportData.getBytes("UTF-8"));
			
			JsonDataSource jsonDataSource=new JsonDataSource(is,"data");
			ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
			Resource resource = classPathXmlApplicationContext.getResource(pathReport);
			
			JasperDesign jasperDesign = JRXmlLoader.load(resource.getInputStream());
			JasperReport jasperReport  = JasperCompileManager.compileReport(jasperDesign);
		
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,  reportParameters,jsonDataSource);
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			classPathXmlApplicationContext.close();
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Error al generar el reporte.", e);
		}
	}
	@RequestMapping(value = "/exportInscripcion")
	public void exportInscripcion(HttpServletRequest request, HttpServletResponse response,SCGEstudianteRegistroEntity entity) throws IOException {

		final String method = "exportInscripcion";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=InscripcionDuplicadoPDF.pdf");
		
		UNALMLogger.trace(log, method,"entity.getCodigo(): "+ entity.getCodigo());
		String nombreCompleto=null,
			   facultadEspecial=null,
			   facultad=null,
			   textoFechaAprobacioncu=null,
			   fechaAprobacioncu=null,
			   rector=null,
			   secretarioGeneral=null,
			   decano=null,
			   codigoUniverisdad=null,
			   documentoTipo=null,
			   documentoNumero=null,
			   abrebiadoGradoTitulo=null,
			   modalidadGradoTitulo=null,
			   modalidadEstudio=null,
			   textoResolucionRectoral=null,
			   tipoEmision=null,
			   numeroLibro=null,
			   numeroFolio=null,
			   numeroRegistro=null,
			   pathReport=null,
			   textoCompletoFechaAprobacioncu=null,
			   textoCicloEgreso=null,
			   gradoAcademico=null,
			   textoDoctor="",
			   textoMaestro="";
			   
					   
				
		try {
        	JSONObject rootJSON = new JSONObject();
			JSONObject itemJSON=new JSONObject();	
			
			SCGEstudianteRegistroEntity estudiante = this.duplicadoService.getForUpdateDuplicado(entity);
			if(estudiante.getGradoAcademico()!=null){
    			abrebiadoGradoTitulo=estudiante.getGradoAcademico().getCodigoExterno();
    			UNALMLogger.trace(log, method,"abrebiadoGradoTitulo: "+ abrebiadoGradoTitulo);
    			gradoAcademico=estudiante.getGradoAcademico().getTextoNombre();
    			UNALMLogger.trace(log, method,"gradoAcademico: "+ gradoAcademico);

    			if(gradoAcademico.equals("Maestro")){
//    				textoMaestro="Maestro";
    				gradoAcademico="Magister Scientiae";
    				
    			}else if(gradoAcademico.equals("Doctor")){
//    				textoDoctor="Doctor";
    				gradoAcademico="Doctoris Philosophiae";
    				
    			}
    			if( estudiante.getFlagDiplomaSexo() != null && abrebiadoGradoTitulo.equals("M")  ){
					textoMaestro = "Maestra" ;
				}else{
					textoMaestro = "Maestro";
				}
    			
    			if( estudiante.getFlagDiplomaSexo() != null && abrebiadoGradoTitulo.equals("D")  ){
					textoDoctor = "Doctora" ;
				}else{
					textoDoctor = "Doctor";
				}
    			if(abrebiadoGradoTitulo.equals("M")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/InscripcionPosgradoDuplicadoMReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("D")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/InscripcionPosgradoDuplicadoDReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("B")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/InscripcionBachillerDuplicadoReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("T")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/InscripcionTituloDuplicadoReport.jrxml";
    			}
			}
			
			if(estudiante.getEstudiante()!=null){
				nombreCompleto =estudiante.getEstudiante().getTextoNombreCompleto();
				textoCicloEgreso =estudiante.getEstudiante().getTextoCicloEgreso();
					if(estudiante.getGradoAcademico()!=null){
		    			if(abrebiadoGradoTitulo.equals("M")||abrebiadoGradoTitulo.equals("D")){
		    				if(estudiante.getEspecialidadPostgrado()!=null){
		    					facultad="Por Cuanto: La "+estudiante.getEspecialidadPostgrado().getFacultad().getTextoNombreEspanol()+" aprobó y";
			    				facultadEspecial =estudiante.getEspecialidadPostgrado().getTextoNombreEspanol();
		    				}
		    			}else if(abrebiadoGradoTitulo.equals("B")){
		    				if(estudiante.getEspecialidad()!=null){
		    					facultadEspecial =estudiante.getEspecialidad().getTextoNombreBachiller();
								facultad="Por Cuanto: La Facultad de "+estudiante.getEspecialidad().getFacultad().getTextoNombreEspanol();
		    				}
							
						}else if(abrebiadoGradoTitulo.equals("T")){
		    				if(estudiante.getEspecialidad()!=null){
//		    					facultadEspecial =estudiante.getEspecialidad().getTextoNombreDenominacion();
		    					if( estudiante.getFlagDiplomaSexo() != null ){
		    						facultadEspecial =estudiante.getEspecialidad().getTextoNombreFemenino();
		    					}else{
		    						facultadEspecial =estudiante.getEspecialidad().getTextoNombreDenominacion();
		    					}
		    					
								facultad="Por Cuanto: La Facultad de "+estudiante.getEspecialidad().getFacultad().getTextoNombreEspanol()+" aprobó y";
		    				}
							
						}
					}
				
			}
				
			if(estudiante.getAutoridadRegistroRector()!=null){
				rector= estudiante.getAutoridadRegistroRector().getTextoNombreAutoridad();
				
			}
			if(estudiante.getAutoridadRegistroSecretarioGeneral()!=null){
				secretarioGeneral = estudiante.getAutoridadRegistroSecretarioGeneral().getTextoNombreAutoridad();
			}
			
			if(estudiante.getAutoridadRegistroDecano()!=null){
				decano=estudiante.getAutoridadRegistroDecano().getTextoNombreAutoridad();
			}
			if(estudiante.getAutoridadRegistroDirectorPostgrado()!=null){
				decano=estudiante.getAutoridadRegistroDirectorPostgrado().getTextoNombreAutoridad();
			}
			if(estudiante.getEstudiante() != null){
				documentoTipo= estudiante.getEstudiante().getTextoCodigoExternoDocumento();
    			documentoNumero=estudiante.getEstudiante().getTextoNumeroDocumento();
			};
			
			if(estudiante.getModalidadGradoTitulo()!=null){
    			modalidadGradoTitulo= estudiante.getModalidadGradoTitulo().getTextoNombre();
    		}
			if(estudiante.getModalidadEstudio()!=null){
    			modalidadEstudio= estudiante.getModalidadEstudio().getCodigoExterno();
    		}
			if(estudiante.getFlagDuplicado()!=null){
    			if(estudiante.getFlagDuplicado().equals("0")){
        			tipoEmision="O";
        		}else if(estudiante.getFlagDuplicado().equals("1")){
        			tipoEmision="D";
        		}
    		}
			if(estudiante.getNumeroLibro()!=null){
				Formatter numeroLibroFormat = new Formatter();
				numeroLibroFormat.format("%03d",estudiante.getNumeroLibro());
				UNALMLogger.trace(log, method, "numeroLibroFormat: "+numeroLibroFormat);
				numeroLibro=numeroLibroFormat.toString();
			}
			if(estudiante.getNumeroFolio()!=null){
				Formatter numeroLibroFormat = new Formatter();
				numeroLibroFormat.format("%03d",estudiante.getNumeroFolio());
				UNALMLogger.trace(log, method, "numeroLibroFormat: "+numeroLibroFormat);
				numeroFolio=numeroLibroFormat.toString();
			}
			if(estudiante.getNumeroRegistro()!=null){
				Formatter numeroRegistroFormat = new Formatter();
				numeroRegistroFormat.format("%03d",estudiante.getNumeroRegistro());
				UNALMLogger.trace(log, method, "numeroLibroFormat: "+numeroRegistroFormat);
				numeroRegistro=numeroRegistroFormat.toString();
			}
			textoCompletoFechaAprobacioncu=TypesUtil.getDateLetter(estudiante.getFechaAprobacioncu());
			textoFechaAprobacioncu=TypesUtil.getDateNumber(estudiante.getFechaAprobacioncu());
			itemJSON.put("textoNombreCompleto", nombreCompleto);
			itemJSON.put("facultadEspecial",facultadEspecial);
			itemJSON.put("facultad", facultad);
			itemJSON.put("textoFechaAprobacioncu",textoFechaAprobacioncu);
			itemJSON.put("fechaAprobacioncu",TypesUtil.getDefaultString(estudiante.getFechaAprobacioncu(), "dd/MM/yyyy"));
			itemJSON.put("decano", decano);
			itemJSON.put("rector",rector);
			itemJSON.put("secretarioGeneral",secretarioGeneral);
			
			itemJSON.put("codigoUniverisdad", "007");
			itemJSON.put("textoCodigoExternoDocumento",documentoTipo);
			itemJSON.put("textoNumeroDocumento", documentoNumero);
			itemJSON.put("abrebiadoGradoTitulo",abrebiadoGradoTitulo);
			itemJSON.put("modalidadGradoTitulo",modalidadGradoTitulo);
			itemJSON.put("modalidadEstudio", modalidadEstudio);
			itemJSON.put("textoResolucionRectoral",estudiante.getTextoResolucionRectoral());
			itemJSON.put("tipoEmision",tipoEmision);
			itemJSON.put("numeroLibro", numeroLibro);
			itemJSON.put("numeroFolio",  numeroFolio);
			itemJSON.put("numeroRegistro", numeroRegistro);
			itemJSON.put("gradoAcademico",gradoAcademico);
			itemJSON.put("textoCompletoFechaAprobacioncu",textoCompletoFechaAprobacioncu);
			itemJSON.put("textoCicloEgreso",textoCicloEgreso);
			itemJSON.put("textoMaestro",textoMaestro);
			UNALMLogger.trace(log, method, "textoDoctor: "+textoDoctor);
			itemJSON.put("textoDoctor",textoDoctor);
			
			rootJSON.put("data", itemJSON);
			String reportData=rootJSON.toString();
			
			UNALMLogger.trace(log, method, "reportData: "+reportData);
			
			Map<String, Object> reportParameters=new HashMap<String,Object>();
			
			String requestURL=request.getRequestURL().toString();
			String serverName=request.getServerName();
			String scheme=request.getScheme();
			UNALMLogger.trace(log, method, "requestURL: "+requestURL);
			UNALMLogger.trace(log, method, "serverName: "+serverName);
			UNALMLogger.trace(log, method, "scheme: "+scheme);
			//String basePath=scheme+"://"+serverName+"/static";
			String basePath=scheme+"://"+serverName+":8080"+"/static";
			String pathLogo =  basePath+ "/images/logo/escudo.jpg";
			UNALMLogger.trace(log, method, "pathLogo:"+pathLogo);
          
			reportParameters.put("P_LOGO", pathLogo);
			
			InputStream is = new ByteArrayInputStream(reportData.getBytes("UTF-8"));
			
			JsonDataSource jsonDataSource=new JsonDataSource(is,"data");
			ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
			Resource resource = classPathXmlApplicationContext.getResource(pathReport);
			
			JasperDesign jasperDesign = JRXmlLoader.load(resource.getInputStream());
			JasperReport jasperReport  = JasperCompileManager.compileReport(jasperDesign);
		
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,  reportParameters,jsonDataSource);
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			classPathXmlApplicationContext.close();
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Error al generar el reporte.", e);
		}
	}
	
}

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
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.gradotitulo.service.AgendaGrupoService;
import pe.edu.lamolina.gradotitulo.service.AutoridadService;
import pe.edu.lamolina.gradotitulo.service.DatoGeneralService;
import pe.edu.lamolina.gradotitulo.service.GradoTituloService;
import pe.edu.lamolina.gradotitulo.service.RevalidaService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.ExcelUtils;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("revalida/*")
@Controller
public class RevalidaController {
	private final static Logger log = Logger.getLogger(RevalidaController.class);

	@Autowired
	private RevalidaService revalidaService;
	
	@Autowired
	private DatoGeneralService datoGeneralService;
	
	@Autowired
	private AgendaGrupoService agendaGrupoService;
	
	@Autowired
	private GradoTituloService gradoTituloService;
	
	@Autowired
	private AutoridadService autoridadService;
	
	
	@RequestMapping("load")
	public  String load(){
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "revalida/loadView";
	}
	@RequestMapping("getRevalidaList")
	public  void getRevalidaList(HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity) throws Exception{
		final String method = "getRevalidaList";
		final String params = "SCGAutoridadRegistroEntity entity";
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
			/*
			if(!TypesUtil.isEmptyString(entity.getEstudiante().getTextoCodigoExternoDocumento())){
				Long codigo = Long.parseLong(entity.getEstudiante().getTextoCodigoExternoDocumento());
				SCGTipoDocumentoEntity tipoDocumentoCodigo = new SCGTipoDocumentoEntity();
				tipoDocumentoCodigo.setCodigo(codigo);
				
				SCGTipoDocumentoEntity tipoDocumento= this.datoGeneralService.getForUpdateTipoDocumento(tipoDocumentoCodigo);
				entity.getEstudiante().setTextoCodigoExternoDocumento(tipoDocumento.getCodigoExterno());
			}
			*/
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

		
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGRevalidaEntity> list=this.revalidaService.getListRevalida(entity,filterUtil,agendas);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGRevalidaEntity item : list) {
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
        				//gradoAcademico=null,
        				//gradoAcademicoCodigoExterno=null,
        				universidadBachiller=null,
        				universidadMaestria=null,
        				fechaIngresanteMatricula=null, 
        				egresadoCiclo=null,
        				egresadoFecha=null,
        				enviadoSunedu=null,
        				duplicado=null,
        				actoFecha=null,
        				fechaAprobacionCU=null, 
        				numeroCreditos=null,        				
        				numeroRegistroDuplicado=null;
        		
        		
        		if(item.getEstudiante()!=null){
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoNumero=item.getEstudiante().getTextoNumeroDocumento();
        			fechaIngresanteMatricula=TypesUtil.getDefaultString(item.getEstudiante().getFechaIngresanteMatricula(), "dd-MM-yyyy");
        			egresadoCiclo=item.getEstudiante().getTextoCicloEgreso();
        			egresadoFecha=TypesUtil.getDefaultString(item.getEstudiante().getFechaTramiteConstancia(), "dd-MM-yyyy");
        			matricula=item.getEstudiante().getTextoMatricula();
        			
        			if(item.getEstudiante().getPersona()!=null){
        				apellidoMaterno=item.getEstudiante().getPersona().getTextoMaterno();
        				apellidoPaterno=item.getEstudiante().getPersona().getTextoPaterno();
        				nombre=item.getEstudiante().getPersona().getTextoNombre();
        				sexo=item.getEstudiante().getPersona().getTextoSexo();
        			}
        		}
        		actoFecha= TypesUtil.getDefaultString(item.getFechaSustentacionTesis(), "dd/MM/yyyy");
        		fechaAprobacionCU= TypesUtil.getDefaultString(item.getFechaAprobacionConsejoUniversitario(), "dd/MM/yyyy");      		

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
        				if(item.getModalidadGradoTitulo()!=null){
        					UNALMLogger.trace(log, method,"item.getModalidadGradoTitulo().getCodigoExterno(): "+ item.getModalidadGradoTitulo().getCodigoExterno());
        					if(item.getModalidadGradoTitulo().getCodigoExterno().equals("004")){
        						actoFecha=fechaAprobacionCU;
        					}
        				}
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
        		itemJSON.put("actoFecha", actoFecha);
        		itemJSON.put("tesis", item.getTextoNombreTesis());
        		itemJSON.put("modadlidad",modalidadEstudio );
        		itemJSON.put("procedenciaRevalidadPais", "-");
        		itemJSON.put("procedenciaRevalidadUniversidad", "-");
        		itemJSON.put("procedenciaRevalidadGradoExtranjero","-");
        		itemJSON.put("resolucionNumero", item.getTextoResolucionRectoral());
        		itemJSON.put("diplomaFecha", TypesUtil.getDefaultString(item.getFechaAgregar(), "dd-MM-yyyy"));
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
        		itemJSON.put("egresadoCiclo", egresadoCiclo);
        		itemJSON.put("egresadoFecha", egresadoFecha);
        		itemJSON.put("flagDiplomaSexo", item.getFlagDiplomaSexo() );;
        		dataJSON.put(itemJSON);
			}
        	rootJSON.put("success", true);
            rootJSON.put("data",dataJSON);
            rootJSON.put("totalCount", filterUtil.getTotalCount());
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
	@RequestMapping("getRevalida")
	public  void getRevalida(HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity) throws Exception{
		final String method = "getRevalida";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		try {
        	JSONArray dataJSON = new JSONArray();        	     	
        	SCGRevalidaEntity revalida = this.revalidaService.getForUpdateRevalida(entity);
        	JSONObject itemJSON = new JSONObject();
        	itemJSON.put("codigo", revalida.getCodigo());
    		itemJSON.put("textoResolucionFacultad", revalida.getTextoResolucionFacultad());
    		itemJSON.put("textoResolucionRectoral", revalida.getTextoResolucionRectoral());
    		itemJSON.put("textoNombreTrabajoInvestigacion", revalida.getTextoNombreTrabajoInvestigacion());
    		itemJSON.put("textoNombreTesis", revalida.getTextoNombreTesis());
    		itemJSON.put("textoDetalle", revalida.getTextoDetalle());
    		itemJSON.put("numeroFolio", revalida.getNumeroFolio());
    		itemJSON.put("numeroLibro", revalida.getNumeroLibro());
    		itemJSON.put("numeroRegistro", revalida.getNumeroRegistro());
    		itemJSON.put("flagCandado", revalida.getFlagCandado());
    		itemJSON.put("fechaCierre",TypesUtil.getDefaultString(revalida.getFechaCierre(), "dd-MM-yyyy")  );
    		itemJSON.put("textoCodigoBarra", revalida.getTextoCodigoBarra());
    		//itemJSON.put("textoMatriculaPost", revalida.getTextoMatriculaPost());
    		itemJSON.put("textoResolucionEpg", revalida.getTextoResolucionEpg());
    		itemJSON.put("textoNombreTituloGrado", revalida.getTextoNombreTituloGrado());
    		itemJSON.put("textoLegalizado", revalida.getTextoLegalizado());
    		itemJSON.put("fechaResolucionFacultad",  TypesUtil.getDefaultString(revalida.getFechaResolucionFacultad(), "dd-MM-yyyy"));
    		itemJSON.put("fechaAprobacionConsejoUniversitario",  TypesUtil.getDefaultString(revalida.getFechaAprobacionConsejoUniversitario(), "dd-MM-yyyy") );
    		itemJSON.put("fechaSustentacionTesis", TypesUtil.getDefaultString(revalida.getFechaSustentacionTesis(), "dd-MM-yyyy") );
    		itemJSON.put("fechaResolucionEpg",TypesUtil.getDefaultString(revalida.getFechaSustentacionTesis(), "dd-MM-yyyy")  );
    		itemJSON.put("fechaInicio",TypesUtil.getDefaultString(revalida.getFechaInicio(), "dd-MM-yyyy")  );
    		itemJSON.put("fechaFinal",TypesUtil.getDefaultString(revalida.getFechaFinal(), "dd-MM-yyyy")  );
    		itemJSON.put("fechaDiploma",TypesUtil.getDefaultString(revalida.getFechaDiploma(), "dd-MM-yyyy")  );
    		if(revalida.getEstudiante()!=null){
        		//itemJSON.put("estudiante.textoMatricula", revalida.getEstudiante().getTextoMatricula());
        		itemJSON.put("estudiante.textoNumeroDocumento", revalida.getEstudiante().getTextoNumeroDocumento());
        		itemJSON.put("estudiante.codigo", revalida.getEstudiante().getCodigo());
        	}
        	if(revalida.getEspecialidad()!=null){
        		itemJSON.put("especialidad.codigo", revalida.getEspecialidad().getCodigo());
        	}
        	if(revalida.getEspecialidadPostgrado()!=null){
        		itemJSON.put("especialidadPostgrado.codigo", revalida.getEspecialidadPostgrado().getCodigo());
        	}
    		if(revalida.getModalidadGradoTitulo()!=null){
    			itemJSON.put("modalidadGradoTitulo.codigo", revalida.getModalidadGradoTitulo().getCodigo());
    		}
    		if(revalida.getModalidadEstudio()!=null){
    			itemJSON.put("modalidadEstudio.codigo", revalida.getModalidadEstudio().getCodigo());
    		}
    		if(revalida.getAutoridadRegistroRector()!=null){
    			itemJSON.put("autoridadRegistroRector.codigo", revalida.getAutoridadRegistroRector().getCodigo());
    		}
    		if(revalida.getAutoridadRegistroSecretarioGeneral()!=null){
    			itemJSON.put("autoridadRegistroSecretarioGeneral.codigo", revalida.getAutoridadRegistroSecretarioGeneral().getCodigo());
    		}
    		if(revalida.getAutoridadRegistroDecano()!=null){
    			itemJSON.put("autoridadRegistroDecano.codigo", revalida.getAutoridadRegistroDecano().getCodigo());
    		}
    		if(revalida.getUniversidadBachiller()!=null){
    			itemJSON.put("universidadBachiller.codigo", revalida.getUniversidadBachiller().getCodigo());
    	        UNALMLogger.trace(log, method,"universidadBachiller.codigo: "+revalida.getUniversidadBachiller().getCodigo());
    			if(revalida.getUniversidadBachiller().getPais()!=null){
	    			itemJSON.put("paisBachiller.codigo", revalida.getUniversidadBachiller().getPais().getCodigo());
	    		}
    		}
    		if(revalida.getUniversidadMaestria()!=null){
    			itemJSON.put("universidadMaestria.codigo", revalida.getUniversidadMaestria().getCodigo());
	    		if(revalida.getUniversidadMaestria().getPais()!=null){
	    			itemJSON.put("paisMaestria.codigo", revalida.getUniversidadMaestria().getPais().getCodigo());
	    		}
    		}
    		if(revalida.getUniversidadRevalida()!=null){
    			itemJSON.put("universidadRevalida.codigo", revalida.getUniversidadRevalida().getCodigo());
	    		if(revalida.getUniversidadRevalida().getPais()!=null){
	    			itemJSON.put("paisRevalida.codigo", revalida.getUniversidadRevalida().getPais().getCodigo());
	    		}
    		}
    		if(revalida.getAutoridadRegistroDirectorPostgrado()!=null){
    			itemJSON.put("autoridadRegistroDirectorPostgrado.codigo", revalida.getAutoridadRegistroDirectorPostgrado().getCodigo());
    		}
    		if(revalida.getAgendaGrupo()!=null){
    			itemJSON.put("agendaGrupo.codigo", revalida.getAgendaGrupo().getCodigo());
    		}
    		/*
    		if( revalida.getAdjunto() !=null){
    			itemJSON.put("URLBase",revalida.getAdjunto().getTextoRuta());
        		itemJSON.put("adjunto.codigo", revalida.getAdjunto().getCodigo());
    		}
    		*/
    		//nuevo
    		
    		
    		if( revalida.getProgramaEstudio()!=null){
    			UNALMLogger.trace(log, method, "programaEstudio.codigo: "+revalida.getProgramaEstudio().getCodigo());
    			itemJSON.put("programaEstudio.codigo", revalida.getProgramaEstudio().getCodigo());
    		}
    		
			itemJSON.put("textoNombreProgramaEstudio", revalida.getTextoNombreProgramaEstudio());

    		itemJSON.put("textoSegundaEspecialidad", revalida.getTextoSegundaEspecialidad());;
			
    		itemJSON.put("numeroCreditos", revalida.getNumeroCreditos());;
			
    		itemJSON.put("textoRegistroMetadato", revalida.getTextoRegistroMetadato());
			
    		itemJSON.put("textoProcedenciaTituloPedagogico", revalida.getTextoProcedenciaTituloPedagogico());;
			
			itemJSON.put("fechaDiplomaDuplicado", TypesUtil.getDefaultString(revalida.getFechaDiplomaDuplicado(), "dd-MM-yyyy"));
			
			itemJSON.put("textoProcedenciaGradoExtranjero", revalida.getTextoProcedenciaGradoExtranjero());;
			
			itemJSON.put("fechaMatriculaPrograma", TypesUtil.getDefaultString(revalida.getFechaMatriculaPrograma(), "dd-MM-yyyy"));;
			
			itemJSON.put("fechaInicioPrograma", TypesUtil.getDefaultString(revalida.getFechaInicioPrograma(), "dd-MM-yyyy"));;
			
			itemJSON.put("fechaTerminoPrograma", TypesUtil.getDefaultString(revalida.getFechaTerminoPrograma(), "dd-MM-yyyy"));;
			
    		itemJSON.put( "flagDiplomaSexo", revalida.getFlagDiplomaSexo() );;
    		
    		if( revalida.getGradoAcademico() !=null){
    			itemJSON.put("gradoAcademico.codigo",revalida.getGradoAcademico().getCodigo());
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
	@RequestMapping("close")
	public  void close(HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity) throws Exception{

		final String method = "close";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method);;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getFechaCierre(): " + entity.getFechaCierre());
		String groupList=request.getParameter("groupList");
		revalidaService.agendasClose(entity, groupList);
		UNALMLogger.exit(log, method);
		/* 
		jsonObjectRoot = new JSONObject();
		List<Long> codigoAgenda = new ArrayList<Long>();
		String delimiters = "(\\[)|,|(\\]$)";
		String[] groupList=request.getParameter("groupList").split(":");
		//String fechaCierre=request.getParameter("fechaCierre");
		//UNALMLogger.trace(log, method, "fechaCierre: " + fechaCierre);
		UNALMLogger.trace(log, method, "entity.getFechaCierre(): " + entity.getFechaCierre());
		List<Long> agendas = new ArrayList<Long>();
		List<SCGRevalidaEntity> sinCerrarList = new ArrayList<SCGRevalidaEntity>();
		List<SCGRevalidaEntity> gradoSinCerrarList = new ArrayList<SCGRevalidaEntity>();
		Boolean isSame = false;
		if(groupList.length>0){
			for (String group : groupList) {
				UNALMLogger.trace(log, method, "codigoItem:" + group);
				String[] values= group.split(delimiters);
				if(values.length >0){
					for(String item:values){
						UNALMLogger.trace(log, method, "item:" + item);
						if(!TypesUtil.isEmptyString(item)){
							Long num =Long.parseLong(item, 10);
							codigoAgenda.add(num);
						}
					}
				}
			}
		}
		
		UNALMLogger.trace(log, method, "codigoAgenda.size():" + codigoAgenda.size());
		
		
		SCGTipoRevalidaEntity tipoRevalidaTitulo = new SCGTipoRevalidaEntity();
		SCGRevalidaEntity revalidaTitulo = new SCGRevalidaEntity();
		tipoRevalidaTitulo.setCodigoExterno("RTIT");
		SCGTipoRevalidaEntity tituloCodigoTipoRevalida = this.revalidaService.getForUpdateTipoRevalidaDAO(tipoRevalidaTitulo);
		revalidaTitulo.setTipoRevalida(tituloCodigoTipoRevalida);
		
		SCGTipoRevalidaEntity tipoRevalidaGrado = new SCGTipoRevalidaEntity();
		SCGRevalidaEntity revalidaGrado = new SCGRevalidaEntity();
		tipoRevalidaGrado.setCodigoExterno("RGRAD");
		SCGTipoRevalidaEntity gradoCodigoTipoRevalida = this.revalidaService.getForUpdateTipoRevalidaDAO(tipoRevalidaGrado);
		revalidaGrado.setTipoRevalida(gradoCodigoTipoRevalida);
		
		if(codigoAgenda.size()>0){
			revalidaTitulo.setFlagCandado("0");
			revalidaGrado.setFlagCandado("0");
			FilterUtil filterUtil = new FilterUtil();
			
			
			
			
			
			List<SCGRevalidaEntity> abiertoList = this.revalidaService.getListRevalida(revalidaTitulo,filterUtil, agendas);
			UNALMLogger.trace(log, method, "listAbierto:" + abiertoList.size());
			if(abiertoList.size()>0){
				List<SCGRevalidaEntity> cerrarList= this.revalidaService.getListRevalida(revalidaTitulo,filterUtil, codigoAgenda);
				UNALMLogger.trace(log, method, "listCerrar:" + cerrarList.size());
				Long count = this.revalidaService.getNumberRegistroMaxRevalida(revalidaTitulo);
				UNALMLogger.trace(log, method, "count:" + count);
				
				if(abiertoList.size()>0){
					for(SCGRevalidaEntity abierto:abiertoList){
						sinCerrarList.add(abierto);
					}
				} 
				
				for(SCGRevalidaEntity abierto:abiertoList){
					for(SCGRevalidaEntity cerrar:cerrarList){
						if(abierto.getCodigo()==cerrar.getCodigo()){
							sinCerrarList.remove(abierto);
							UNALMLogger.trace(log, method, "removiendo el registro:" + cerrar.getCodigo());
							UNALMLogger.trace(log, method, "sinCerrarList.size():" + sinCerrarList.size());
						}
		
					}
				}
				
				if(sinCerrarList.size()>0){
					for(SCGRevalidaEntity item:sinCerrarList){
						item.setAgendaGrupo(new SCGAgendaGrupoEntity());
						item.getAgendaGrupo().setCodigo(1L);
						this.revalidaService.saveRevalida(item);
					}
				}
				if(count!=null){
					for(SCGRevalidaEntity cerrar:cerrarList){
							count++;
							cerrar.setNumeroRegistro(count);
							cerrar.setFlagCandado("1");	
							if(entity.getFechaCierre()!=null){
								cerrar.setFechaCierre(entity.getFechaCierre());
							}
							this.revalidaService.saveRevalida(cerrar);	
					}		
				}
				
			}
			
			
			
			
			
			
			
			
			
			
			List<SCGRevalidaEntity> gradoAbiertoList = this.revalidaService.getListRevalida(revalidaGrado, filterUtil,agendas);
			UNALMLogger.trace(log, method, "gradoAbiertoList:" + gradoAbiertoList.size());
			if(gradoAbiertoList.size()>0){
				List<SCGRevalidaEntity> gradoCerrarList= this.revalidaService.getListRevalida(revalidaGrado,filterUtil, codigoAgenda);
				UNALMLogger.trace(log, method, "gradoListCerrar:" + gradoCerrarList.size());		
				Long gradoCount = this.revalidaService.getNumberRegistroMaxRevalida(revalidaGrado);
				UNALMLogger.trace(log, method, "gradoCount:" + gradoCount);

				if(gradoAbiertoList.size()>0){
					for(SCGRevalidaEntity abierto:gradoAbiertoList){
						gradoSinCerrarList.add(abierto);
					}
				}  
				
				for(SCGRevalidaEntity abierto:gradoAbiertoList){
					for(SCGRevalidaEntity cerrar:gradoCerrarList){
						if(abierto.getCodigo()==cerrar.getCodigo()){
							gradoSinCerrarList.remove(abierto);
							UNALMLogger.trace(log, method, "removiendo el registro:" + cerrar.getCodigo());
							UNALMLogger.trace(log, method, "sinCerrarList.size():" + gradoSinCerrarList.size());
						}
		
					}
				}
				if(gradoSinCerrarList.size()>0){
					for(SCGRevalidaEntity item:gradoSinCerrarList){
						item.setAgendaGrupo(new SCGAgendaGrupoEntity());
						item.getAgendaGrupo().setCodigo(1L);
						this.revalidaService.saveRevalida(item);
					}
				}
				if(gradoCount!=null){
					for(SCGRevalidaEntity cerrar:gradoCerrarList){
						gradoCount++;
						cerrar.setNumeroRegistro(gradoCount);
						cerrar.setFlagCandado("1");	
						if(entity.getFechaCierre()!=null){
							cerrar.setFechaCierre(entity.getFechaCierre());
						}
						this.revalidaService.saveRevalida(cerrar);		
					}
				}
			}
				
		}
		UNALMLogger.exit(log, method);
		*/
	};
	@RequestMapping("saveRevalida")
	public  void save(HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity) throws Exception{

		final String method = "save";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity";		
		String textoSexo = "";

		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method,"entity.getCodigo(): "+entity.getCodigo());
        UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+entity.getFlagCandado());
        UNALMLogger.trace(log, method,"entity.getFlagDuplicado(): "+entity.getFlagDuplicado());
        UNALMLogger.trace(log, method,"entity.getFlagEliminado(): "+entity.getFlagEliminado());
        UNALMLogger.trace(log, method,"entity.getFlagEnviadoSunedu(): "+entity.getFlagEnviadoSunedu());
        UNALMLogger.trace(log, method,"entity.getTextoCodigoBarra(): "+entity.getTextoCodigoBarra());
        UNALMLogger.trace(log, method,"entity.getTextoDetalle(): "+entity.getTextoDetalle());
        UNALMLogger.trace(log, method,"entity.getTextoNombreTesis(): "+entity.getTextoNombreTesis());
        UNALMLogger.trace(log, method,"entity.getTextoNombreTrabajoInvestigacion(): "+entity.getTextoNombreTrabajoInvestigacion());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionEpg(): "+entity.getTextoResolucionEpg());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionEpg(): "+entity.getTextoResolucionEpg());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionFacultad(): "+entity.getTextoResolucionFacultad());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionRectoral(): "+entity.getTextoResolucionRectoral());
        UNALMLogger.trace(log, method,"entity.getTextoSemestreEgreso(): "+entity.getTextoSemestreEgreso());
        UNALMLogger.trace(log, method,"entity.getNumeroFolio(): "+entity.getNumeroFolio());
        UNALMLogger.trace(log, method,"entity.getNumeroLibro(): "+entity.getNumeroLibro());
        UNALMLogger.trace(log, method,"entity.getNumeroRegistro(): "+entity.getNumeroRegistro());
        UNALMLogger.trace(log, method,"entity.getFechaCierre(): "+entity.getFechaCierre());
		
        
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
		
        if(entity.getTipoRevalida() != null){
       	 UNALMLogger.trace(log, method,"entity.getTipoRevalida().getCodigo(): "+entity.getTipoRevalida().getCodigo());
       	 UNALMLogger.trace(log, method,"entity.getTipoRevalida().getCodigoExterno(): "+entity.getTipoRevalida().getCodigoExterno());
        };
		
       
		try {
			
		
			/*
			if(entity.getEstudiante()!=null){
				if(!TypesUtil.isEmptyString((entity.getEstudiante().getTextoMatricula()))){
					SCGEstudianteEntity estudiante= new SCGEstudianteEntity();
					estudiante.setTextoMatricula(entity.getEstudiante().getTextoMatricula());
					SCGEstudianteEntity estudianteCodigo = this.datoGeneralService.getForUpdateEstudiante(estudiante);
					entity.setEstudiante(estudianteCodigo);
				}
			}
			*/
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
//									SCGUniversidadEntity universidad = new SCGUniversidadEntity();
//									universidad.setCodigoExterno("002");
//									SCGUniversidadEntity codigoUniversidad = this.gradoTituloService.getForUpdateUniversidad(universidad);
//									entity.setUniversidadBachiller(codigoUniversidad);
								}
								
								
								if(gradoAcademico.getCodigoExterno().equals("B")){
									if(entity.getModalidadGradoTitulo()!=null){
										SCGModalidadGradoTituloEntity modalidadGradoTitulo =this.gradoTituloService.getForUpdateModalidadGradoTitulo(entity.getModalidadGradoTitulo());
										if(modalidadGradoTitulo!=null){
											if(modalidadGradoTitulo.getCodigoExterno().equals("004")){
												entity.setTextoNombreTrabajoInvestigacion("");

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
			/*
			SCGModalidadGradoTituloEntity codigoExternoGradoTitulo = new SCGModalidadGradoTituloEntity();
			codigoExternoGradoTitulo.setCodigoExterno("006");
			SCGModalidadGradoTituloEntity modalidadGradoTitulo = this.gradoTituloService.getForUpdateModalidadGradoTitulo(codigoExternoGradoTitulo);
			if(modalidadGradoTitulo != null){
				entity.setModalidadGradoTitulo(modalidadGradoTitulo);
			}	
			*/
			if(entity.getTipoRevalida()!=null){
				if(entity.getTipoRevalida().getCodigoExterno()!=null){
					SCGTipoRevalidaEntity tipoRevalida = new SCGTipoRevalidaEntity();
					tipoRevalida.setCodigoExterno(entity.getTipoRevalida().getCodigoExterno());
					SCGTipoRevalidaEntity codigoTipoRevalida = this.revalidaService.getForUpdateTipoRevalidaDAO(tipoRevalida);
					entity.setTipoRevalida(codigoTipoRevalida);
				}
			}
			
			SCGEstudianteEntity codigo = new SCGEstudianteEntity();
			if(entity.getEstudiante()!=null){
				UNALMLogger.trace(log, method, "entity.getEstudiante(): "+entity.getEstudiante() );

				codigo.setCodigo(entity.getEstudiante().getCodigo());
				UNALMLogger.trace(log, method, "codigo: "+entity.getEstudiante() );
				SCGEstudianteEntity estudianteInfo = this.datoGeneralService.getForUpdateEstudiante(codigo);
				UNALMLogger.trace(log, method, "estudianteInfo: "+estudianteInfo );

				if( estudianteInfo != null ){
					textoSexo = estudianteInfo.getPersona().getTextoSexo();
					UNALMLogger.trace(log, method, "textoSexo: "+textoSexo );
					UNALMLogger.trace(log, method, "entity.getFlagCandado(): "+entity.getFlagCandado() );
					if( entity.getFlagDiplomaSexo() != null && !textoSexo.equals("F")){
						UNALMLogger.trace(log, method, "entity.setFlagDiplomaSexo(null)" );
						entity.setFlagDiplomaSexo(null);
					}
				}
			}
			
			entity.setFechaAgregar(new Date());
			entity.setFlagCandado("0");
			entity.setFlagEliminado("1");		
			//this.revalidaService.saveRevalida(entity);
			
			if(entity.getCodigo()!=null){
				this.revalidaService.saveRevalida(entity);
			}else{
				if(isDuplicateRevalida(entity)){
					jsonObjectRoot.put("success", false);
					jsonObjectRoot.put("message", "Ya existe este Registro");
					return;
				}else{
					this.revalidaService.saveRevalida(entity);
				}
			}
			
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
	@RequestMapping("enviarSunedu")
	public void enviarSunedu(HttpServletRequest request, HttpServletResponse response, SCGRevalidaEntity entity)throws Exception {
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
			this.revalidaService.enviarSunedu(entity);
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
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGRevalidaEntity entity)throws Exception {
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
			this.revalidaService.delete(entity);;
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
	public ModelAndView excel(HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity) throws IOException {
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

        	List<SCGRevalidaEntity> list=this.revalidaService.getListRevalida(entity,filterUtil,agendas);
        	UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGRevalidaEntity item : list) {
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
        				universidadBachiller=null,
        				universidadMaestria=null,
        				universidadRevalida=null, 
        				paisRevalida=null, 
        				gradoRevalida=null, 
        				fechaIngresanteMatricula=null, 
        				egresadoCiclo=null,
        				egresadoFecha=null,
        				fechaAprobacionCU=null,
        				numeroFolio=null,
        				numeroRegistro=null,
        				anuoAprobacionCU=null,
        				numeroCreditos=null, 
        				denominacionGradoAcademicoTitulo=null,
        				textoResolucionRectoral=null,
                        actoFecha=null,
                        programaEstudio=null;
        		List<String> objectDetails = new ArrayList<String>();
        		if(item.getEstudiante()!=null){
        			/*
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoNumero=item.getEstudiante().getTextoNumeroDocumento();
        			*/
        			
        			anuoAprobacionCU=TypesUtil.getDefaultString(item.getFechaAprobacionConsejoUniversitario(), "yyyy");
            		
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
                	
        			fechaIngresanteMatricula=TypesUtil.getDefaultString(item.getEstudiante().getFechaIngresanteMatricula(), "dd/MM/yyyy");
        			egresadoCiclo=item.getEstudiante().getTextoCicloEgreso();
        			egresadoFecha=TypesUtil.getDefaultString(item.getEstudiante().getFechaTramiteConstancia(), "dd/MM/yyyy");
        			
        			
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
        		fechaAprobacionCU= TypesUtil.getDefaultString(item.getFechaAprobacionConsejoUniversitario(), "dd/MM/yyyy");      		
        		actoFecha= TypesUtil.getDefaultString(item.getFechaSustentacionTesis(), "dd/MM/yyyy");      		
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
        				if(item.getModalidadGradoTitulo()!=null){
        					UNALMLogger.trace(log, method,"item.getModalidadGradoTitulo().getCodigoExterno(): "+ item.getModalidadGradoTitulo().getCodigoExterno());
        					if(item.getModalidadGradoTitulo().getCodigoExterno().equals("004")){
        						actoFecha=egresadoFecha;
        					}
        				}
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
        				gradoTitulo=item.getGradoAcademico().getTextoNombre();
        			}
        			abrebiadoGradoTitulo=item.getGradoAcademico().getCodigoExterno();
        			denominacionGradoAcademicoTitulo=item.getGradoAcademico().getTextoNombre();
        		}
//        		if(item.getModalidadGradoTitulo()!=null){
//        			modalidadGradoTitulo= item.getModalidadGradoTitulo().getTextoNombre();
//        		}
        		modalidadGradoTitulo = "REVÁLIDA";
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
        		if(item.getUniversidadBachiller()!=null){
        			if(!abrebiadoGradoTitulo.equals("T")){
        				if(item.getUniversidadBachiller().getCodigoExterno().equals("007")){
            				universidadBachiller="007";
            			}else{
            				universidadBachiller=item.getUniversidadBachiller().getTextoNombre();
            			}
        			}else {
        				universidadBachiller = "";
        			}
        		}
        		if(item.getUniversidadMaestria()!=null){
        			universidadMaestria=item.getUniversidadMaestria().getTextoNombre();
        		}
        		
        		if(item.getUniversidadRevalida()!=null){
        			universidadRevalida=item.getUniversidadRevalida().getTextoNombre();
        			if(item.getUniversidadRevalida().getPais()!=null){
        				paisRevalida=item.getUniversidadRevalida().getPais().getTextoNombre();
        			}
        			gradoRevalida=item.getTextoNombreTituloGrado();
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
        		objectDetails.add(fechaIngresanteMatricula);
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
        		//objectDetails.add(egresadoFecha);
        		
        		
        		        		
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
        		
        		objectDetails.add(TypesUtil.getDefaultUpperCase(modalidadEstudio ));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(abrebiadoGradoTitulo));      		

        		objectDetails.add(TypesUtil.getDefaultUpperCase(paisRevalida));//PROC_REV_PAIS
        		objectDetails.add(TypesUtil.getDefaultUpperCase(universidadRevalida));//PROC_REV_UNIV
        		objectDetails.add(TypesUtil.getDefaultUpperCase(gradoRevalida));//PROC_REV_GRADO_EXT
        		
        		
        		objectDetails.add(TypesUtil.getDefaultUpperCase(textoResolucionRectoral));

        		objectDetails.add(fechaAprobacionCU);
        		objectDetails.add(fechaAprobacionCU);
        		objectDetails.add("");//DIPL_FEC_DUP

        		objectDetails.add(TypesUtil.getDefaultUpperCase(item.getTextoCodigoBarra()));
        		
        		objectDetails.add(tipoEmision);
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
        		//objectDetails.add("");
        		objectDetails.add("");//PROC_PAIS_EXT
        		objectDetails.add("");//PROC_UNV_EXT
        		objectDetails.add("");//PROC_GRADO_EXT
        		objectDetails.add("");//REG_OFICIO
        		objectDetails.add("");//FEC_MAT_PROG
        		objectDetails.add("");//FEC_INICIO_PROG
        		objectDetails.add("");//FEC_FIN_PROG
        		objectDetails.add(TypesUtil.getDefaultUpperCase(especialidad));
        		objectDetails.add(actoFecha);
        		
        		//objectDetails.add(agendaGrupo);
        		objectDetails.add(TypesUtil.getDefaultUpperCase(universidadMaestria));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(telefonoFijo));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(telefonoCelular));
        		objectDetails.add(TypesUtil.getDefaultUpperCase(egresadoCiclo));
//        		objectDetails.add(TypesUtil.getDefaultUpperCase(item.getTextoLegalizado()));

        		masterList.add(objectDetails);
        	}
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Exception: ", e);     
		}
		
		List<String> myReportHeader = getTrainingReportHeader();
		masterList.add(0, myReportHeader);
		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, masterList, "ReporteRevalidas");
		ExcelUtils.generateReport(response, workBook, "PadronRevalidas");
		return null;
	}
	private String setCodigoPeriodoAutoridadSunedo( SCGRevalidaEntity registro, Long codigo ){
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
    			UNALMLogger.trace(log, method, "item.getFechaAprobacioncu() "+ registro.getFechaAprobacionConsejoUniversitario() );

				if( (lista.getFechaInicio().compareTo( registro.getFechaAprobacionConsejoUniversitario()) *
						registro.getFechaAprobacionConsejoUniversitario().compareTo(  lista.getFechaFinal() )) >= 0){
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
		List<String> headerList = new ArrayList<String>(55);
//		headerList.add("CODUNIV");
//		headerList.add("RAZ_SOC");
//		headerList.add("MATRI_FEC");
//		headerList.add("FAC_NOM");
//		headerList.add("ESC_CARR");
//		headerList.add("ESC_POST");
//		headerList.add("APEPAT");
//		headerList.add("APEMAT");
//		headerList.add("NOMBRES");
//		headerList.add("SEXO");
//		headerList.add("DOCU_TIP");
//		headerList.add("DOCU_NUM");
//		headerList.add("EGRES_FEC");
//		headerList.add("PROC_BACH");
//		headerList.add("GRAD_TITU");
//		headerList.add("ESPECIALIDAD");
//		headerList.add("ABRE_GYT");
//		headerList.add("ACTO_TIP");
//		headerList.add("ACTO_FEC");
//		headerList.add("TESIS");
//		headerList.add("MODALIDAD");
//		headerList.add("PROC_REV_PAIS");
//		headerList.add("PROC_REV_UNIV");
//		headerList.add("PROC_REV_GRADO_EXT");
//		headerList.add("RESO_NUM");
//		headerList.add("RESO_FEC");
//		headerList.add("DIPL_FEC");
//		headerList.add("DIPL_NUM");
//		headerList.add("DIP_TIP_EMI");
//		headerList.add("REG_LIBRO");
//		headerList.add("REG_FOLIO");
//		headerList.add("REG_REGISTRO");
//		headerList.add("CARGO1");
//		headerList.add("AUTORIDAD1");
//		headerList.add("CARGO2");
//		headerList.add("AUTORIDAD2");
//		headerList.add("CARGO3");
//		headerList.add("AUTORIDAD3");
//		headerList.add("REG_OFICIO");
//		//headerList.add("Agenda");
//		headerList.add("Procedencia de Maestria");
//		headerList.add("Fijo");
//		headerList.add("Celular");
//		headerList.add("Ciclo de Egreso");
//		headerList.add("Legalizado en");
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
	public void exportDiploma(HttpServletRequest request, HttpServletResponse response,SCGRevalidaEntity entity) throws IOException {

		final String method = "exportDiploma";
		final String params = "HttpServletRequest request, HttpServletResponse response,SCGRevalidaEntity entity)";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=InscripcionRevalidaPDF.pdf");
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
			   textoFechaAprobacioncuOriginal=null,
			   numeroLibro=null,
			   numeroFolio=null,
			   numeroRegistro=null;
	
			   
					   
				
		try {
        	JSONObject rootJSON = new JSONObject();
			JSONObject itemJSON=new JSONObject();	
			
			SCGRevalidaEntity revalida = this.revalidaService.getForUpdateRevalida(entity);
			if(revalida.getGradoAcademico()!=null){
    			abrebiadoGradoTitulo=revalida.getGradoAcademico().getCodigoExterno();
    			gradoAcademico=revalida.getGradoAcademico().getTextoNombre();
    			UNALMLogger.trace(log, method,"abrebiadoGradoTitulo: "+ abrebiadoGradoTitulo);

    			if(abrebiadoGradoTitulo.equals("M")||abrebiadoGradoTitulo.equals("D")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/RevalidaReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("B")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/RevalidaReport.jrxml";
    				
    			}else if(abrebiadoGradoTitulo.equals("T")){
    				pathReport="classpath:pe/edu/lamolina/gradotitulo/report/jrxml/RevalidaReport.jrxml";
    			}
			}
			
			if(revalida.getEstudiante()!=null){
				nombreCompleto = "a: "+revalida.getEstudiante().getTextoNombreCompleto();
					if(revalida.getGradoAcademico()!=null){
		    			if(abrebiadoGradoTitulo.equals("M")||abrebiadoGradoTitulo.equals("D")){
		    				if(revalida.getEspecialidadPostgrado()!=null){
		    					facultad="Por Cuanto: La "+revalida.getEspecialidadPostgrado().getFacultad().getTextoNombreEspanol()+" aprobó y";
			    				facultadEspecial =revalida.getEspecialidadPostgrado().getTextoNombreEspanol();
		    				}
		    			}else if(abrebiadoGradoTitulo.equals("B")){
		    				if(revalida.getEspecialidad()!=null){
		    					facultadEspecial =revalida.getEspecialidad().getTextoNombreBachiller();
								facultad="Por Cuanto: La Facultad de "+revalida.getEspecialidad().getFacultad().getTextoNombreEspanol();
		    				}
							
						}else if(abrebiadoGradoTitulo.equals("T")){
		    				if(revalida.getEspecialidad()!=null){
//		    					facultadEspecial =revalida.getEspecialidad().getTextoNombreDenominacion();
		    					if( revalida.getFlagDiplomaSexo() != null ){
		    						facultadEspecial=revalida.getEspecialidad().getTextoNombreFemenino();
		        				}else{
		        					facultadEspecial=revalida.getEspecialidad().getTextoNombreDenominacion();
		        				}
		    					
		    					facultad="Por Cuanto: La Facultad de "+revalida.getEspecialidad().getFacultad().getTextoNombreEspanol()+" aprobó y";
		    				}
							
						}
					}
				
			}
				
			if(revalida.getAutoridadRegistroRector()!=null){
				rector= revalida.getAutoridadRegistroRector().getTextoNombreAutoridad();
				
			}
			if(revalida.getAutoridadRegistroSecretarioGeneral()!=null){
				secretarioGeneral = revalida.getAutoridadRegistroSecretarioGeneral().getTextoNombreAutoridad();
			}
			
			if(revalida.getAutoridadRegistroDecano()!=null){
				decano=revalida.getAutoridadRegistroDecano().getTextoNombreAutoridad();
			}
			if(revalida.getAutoridadRegistroDirectorPostgrado()!=null){
				decano=revalida.getAutoridadRegistroDirectorPostgrado().getTextoNombreAutoridad();
			}
			if(revalida.getEstudiante() != null){
				documentoTipo= revalida.getEstudiante().getTextoCodigoExternoDocumento();
    			documentoNumero=revalida.getEstudiante().getTextoNumeroDocumento();
			};
			
			if(revalida.getModalidadGradoTitulo()!=null){
    			modalidadGradoTitulo= revalida.getModalidadGradoTitulo().getTextoNombre();
    			if( modalidadGradoTitulo.equals("Automático") ){
    				modalidadGradoTitulo = "Bachiller Automático";
    			}
    		}
			if(revalida.getModalidadEstudio()!=null){
    			modalidadEstudio= revalida.getModalidadEstudio().getCodigoExterno();
    		}
			tipoEmision="O";
			if(revalida.getNumeroFolio()!=null){
				Formatter numeroFolioFormat = new Formatter();
				numeroFolioFormat.format("%03d",revalida.getNumeroFolio());
				UNALMLogger.trace(log, method, "numeroFolioFormat: "+numeroFolioFormat);
				numeroFolio=numeroFolioFormat.toString();
			}
			if(revalida.getNumeroRegistro()!=null){
				Formatter numeroRegistroFormat = new Formatter();
				numeroRegistroFormat.format("%03d",revalida.getNumeroRegistro());
				UNALMLogger.trace(log, method, "numeroLibroFormat: "+numeroRegistroFormat);
				numeroRegistro=numeroRegistroFormat.toString();
			}
			
			/*
			if(revalida.getFlagDuplicado()!=null){
    			if(revalida.getFlagDuplicado().equals("0")){
        			tipoEmision="O";
        		}else if(revalida.getFlagDuplicado().equals("1")){
        			tipoEmision="D";
        		}
    		}
			*/
			/*
			if(revalida.getEstudianteRegistroPadre() != null){
				SCGEstudianteRegistroEntity registroCodigo = new SCGEstudianteRegistroEntity();
				registroCodigo.setNumeroRegistro(revalida.getEstudianteRegistroPadre() );
				SCGEstudianteRegistroEntity registroOriginal  = this.gradoTituloService.getForUpdateEsudianteRegistro(registroCodigo);
				if(registroOriginal!=null){
					if(registroOriginal.getFechaAprobacioncu()!=null){
						textoFechaAprobacioncuOriginal = TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "dd")+" de "+
								TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "MM")+" del "+
								TypesUtil.getDefaultString(registroOriginal.getFechaAprobacioncu(), "yyyy");
					}
				}	
			}
			*/
			textoFechaAprobacioncu= TypesUtil.getDefaultString(revalida.getFechaAprobacionConsejoUniversitario(), "dd")+" de "+
					TypesUtil.getDefaultString(revalida.getFechaAprobacionConsejoUniversitario(), "MM")+" del "+
					TypesUtil.getDefaultString(revalida.getFechaAprobacionConsejoUniversitario(), "yyyy");


			itemJSON.put("textoNombreCompleto", nombreCompleto);
			itemJSON.put("gradoRevalida",facultadEspecial);
			itemJSON.put("facultad", facultad);
			itemJSON.put("textoFechaAprobacioncu",textoFechaAprobacioncu);
			itemJSON.put("textoFechaAprobacioncuOriginal",textoFechaAprobacioncuOriginal);
			itemJSON.put("fechaAprobacioncu",TypesUtil.getDefaultString(revalida.getFechaAprobacionConsejoUniversitario(), "dd/MM/yyyy"));
			itemJSON.put("decano", decano);
			itemJSON.put("rector",rector);
			itemJSON.put("secretarioGeneral",secretarioGeneral);
			
			itemJSON.put("codigoUniverisdad", "007");
			itemJSON.put("textoCodigoExternoDocumento",documentoTipo);
			itemJSON.put("textoNumeroDocumento", documentoNumero);
			itemJSON.put("abrebiadoGradoTitulo",abrebiadoGradoTitulo);
			itemJSON.put("modalidadGradoTitulo",modalidadGradoTitulo);
			itemJSON.put("modalidadEstudio", modalidadEstudio);
			itemJSON.put("textoResolucionRectoral",revalida.getTextoResolucionRectoral());
			itemJSON.put("tipoEmision",tipoEmision);
			itemJSON.put("numeroLibro",  revalida.getNumeroLibro());
			itemJSON.put("numeroFolio", numeroFolio);
			itemJSON.put("numeroRegistro", numeroRegistro);
			itemJSON.put("gradoAcademico",gradoAcademico);
			itemJSON.put("paisRevalida","España");
			itemJSON.put("universidadRevalida","Universidad Politécnica de Madrid");
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
			String basePath=scheme+"://"+serverName+"/static";
			String pathLogo =  basePath+ "/images/logo/escudoUnalm.png";
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
	public boolean isDuplicateRevalida(SCGRevalidaEntity entity){
		boolean result=false;
		String resolucionRecotoralFormat=null;
		SCGRevalidaEntity revalida= new SCGRevalidaEntity();
		List<Long> agendas = new ArrayList<Long>();
		try {
			revalida.setEstudiante(new SCGEstudianteEntity());
			revalida.getEstudiante().setTextoMatricula(entity.getEstudiante().getTextoMatricula());
			
			revalida.setGradoAcademico(new SCGGradoAcademicoEntity());
			revalida.getGradoAcademico().setCodigo(entity.getGradoAcademico().getCodigo());
			
			resolucionRecotoralFormat = this.revalidaService.setFormatResolucionRecotoral(entity);
			revalida.setTextoResolucionRectoral(resolucionRecotoralFormat);
			
			revalida.setFlagCandado(ApplicationConstant.FLAG_CANDADO_ABIERTO);
			FilterUtil filterUtil = new FilterUtil();
			List<SCGRevalidaEntity> revalidaList = this.revalidaService.getListRevalida(revalida, filterUtil,agendas);
			if(revalidaList.size()>0){
				result=true;
			}
		} catch (Exception e) {
			result=true;
		}
		return result;
	}
}

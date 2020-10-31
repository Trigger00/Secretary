package pe.edu.lamolina.gradotitulo.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import pe.edu.lamolina.gradotitulo.entity.SCGDiplomadoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDiplomadoEntity;
import pe.edu.lamolina.gradotitulo.service.AgendaGrupoService;
import pe.edu.lamolina.gradotitulo.service.DiplomadoService;
import pe.edu.lamolina.gradotitulo.service.RevalidaService;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONArray;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.util.ExcelUtils;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@RequestMapping("diplomado/*")
@Controller
public class DiplomadoController {
	private final static Logger log = Logger.getLogger(DiplomadoController.class);

	@Autowired
	private RevalidaService revalidaService;
	
	@Autowired
	private DiplomadoService diplomadoService;
	
	@Autowired
	private AgendaGrupoService agendaGrupoService;

	@RequestMapping("load")
	public  String load(){
		final String method = "load";
		UNALMLogger.entry(log, method);
		UNALMLogger.exit(log, method);
		return "diplomado/loadView";
	}
	@RequestMapping("getDiplomadoList")
	public  void getDiplomadoList(HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity) throws Exception{
		final String method = "getDiplomadoList";
		final String params = "SCGDiplomadoEntity entity";
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
        	List<SCGDiplomadoEntity> list=this.diplomadoService.getListDiplomado(entity,filterUtil,agendas);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGDiplomadoEntity item : list) {
        		String facultadNombre=null,
        				matricula=null,
        				apellidoPaterno=null,
        				apellidoMaterno=null,
        				nombre=null,
        				sexo=null,
        				documentoTipo=null,
        				documentoNumero=null,
        				especialidad=null,
        				nombreRector=null,
        				codigoRector=null,
        				tipoAutoridad=null,
        				nombreAutoridad=null,
        				agendaGrupo=null,
        				enviadoSunedu=null,

        				tipoDiplomado=null;
        		
        		
        		if(item.getEstudiante()!=null){
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoTipo= item.getEstudiante().getTextoCodigoExternoDocumento();
        			documentoNumero=item.getEstudiante().getTextoNumeroDocumento();
        			matricula=item.getEstudiante().getTextoMatricula();
        			
        			if(item.getEstudiante().getPersona()!=null){
        				apellidoMaterno=item.getEstudiante().getPersona().getTextoMaterno();
        				apellidoPaterno=item.getEstudiante().getPersona().getTextoPaterno();
        				nombre=item.getEstudiante().getPersona().getTextoNombre();
        				sexo=item.getEstudiante().getPersona().getTextoSexo();
        			}
        		}
        		
        		if(item.getAutoridadRegistroRector()!=null){
        			nombreRector=item.getAutoridadRegistroRector().getTextoNombreAutoridad();
        			codigoRector=item.getAutoridadRegistroRector().getCargo().getTextoNombre();
        		}
        		
        		if(item.getAutoridadRegistroDecano()!=null){
        			if(!TypesUtil.isEmptyString(item.getAutoridadRegistroDecano().getTextoNombreAutoridad())){
        				nombreAutoridad=item.getAutoridadRegistroDecano().getTextoNombreAutoridad();
        				//tipoAutoridad =item.getAutoridadRegistroDecano().getCargo().getTextoNombre();
        				tipoAutoridad ="Decano";
        			}
        		}
        		if(item.getAgendaGrupo()!=null){
        			agendaGrupo=item.getAgendaGrupo().getTextoNombre();
        		}
        		if(item.getTipoDiplomado()!=null){
        			tipoDiplomado=item.getTipoDiplomado().getTextoNombre();
        		}
        		/*
        		if(item.getGradoAcademico()!=null){
        			gradoAcademico=item.getGradoAcademico().getTextoNombre();
        			gradoAcademicoCodigoExterno=item.getGradoAcademico().getCodigoExterno();
        		}
        		*/
        		
        		if(!TypesUtil.isEmptyString(item.getFlagEnviadoSunedu())){
        			enviadoSunedu="Si";
        		}else{
        			enviadoSunedu="No";
        		}
        		/*/
        		 * COD. FAC.	
        		 * AP.PATERNO	
        		 * AP.MATERNO	
        		 * NOMBRES	
        		 * DOC. IDENT.	NUMERO	SEXO	COD. DIPLOMADO	FECHA INIC.	
        		 * FECHA FIN	
        		 * HRS. LECTIVAS	
        		 * HRS. TEÓR.-PRÁCT.	
        		 * PROM.FINAL	
        		 * FECHA RES.FAC	
        		 * RES. FAC.	
        		 * REG.DIPLOMAFAC.	
        		 * DECANO	
        		 * RECTOR	
        		 * REG. S.G	RES.C.U	FECHA  C.U. 	FOLIO	LIBRO
        		 */

        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("matricula", matricula);
        		itemJSON.put("apellidoPaterno", apellidoPaterno);
        		itemJSON.put("apellidoMaterno", apellidoMaterno);
        		itemJSON.put("nombre", nombre);
        		itemJSON.put("documentoTipo", documentoTipo);
        		itemJSON.put("documentoNumero", documentoNumero);
        		itemJSON.put("sexo", sexo);
        		itemJSON.put("tipoDiplomado", tipoDiplomado);
        		itemJSON.put("fechaInicioDiplomado", TypesUtil.getDefaultString(item.getFechaInicio(), "dd-MM-yyyy"));
        		itemJSON.put("fechaFinalDiplomado", TypesUtil.getDefaultString(item.getFechaFinal(), "dd-MM-yyyy"));
        		itemJSON.put("numeroHorasLectivas",item.getNumeroHorasLectivas());
        		itemJSON.put("numeroHorasTeoricoPractico",item.getNumeroHorasTeoricoPtc());
        		itemJSON.put("numeroPromedioFinal",item.getNumeroPromedioFinal());
        		itemJSON.put("fechaResolucionFacultad", TypesUtil.getDefaultString(item.getFechaResolucionFatultad(), "dd-MM-yyyy"));
        		itemJSON.put("resolucionFacultad", item.getTextoResolucionFacultad());
        		itemJSON.put("resolucionDiplomaFacultad", item.getTextoRegistroDiplomadoFacultad());
        		itemJSON.put("cargoTres", tipoAutoridad);
        		itemJSON.put("autoridadTres", nombreAutoridad);
        		
        		itemJSON.put("cargoUno", codigoRector);
        		itemJSON.put("autoridadUno", nombreRector);
        		itemJSON.put("registroRegistro", item.getNumeroRegistro());
        		itemJSON.put("resolucionNumero", item.getTextoResolucionRectoral());
        		itemJSON.put("fechaCU", TypesUtil.getDefaultString(item.getFechaAprobacionCu(), "dd-MM-yyyy"));
        		itemJSON.put("registroFolio", item.getNumeroFolio());
        		itemJSON.put("registroLibro", item.getNumeroLibro());
        		itemJSON.put("facultadNombre", facultadNombre);
        		itemJSON.put("especialidad", especialidad);
        		itemJSON.put("enviadoSunedu",enviadoSunedu );
        		itemJSON.put("agendaGrupo", agendaGrupo);

        		
        		
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
	@RequestMapping("getDiplomado")
	public  void getDiplomado(HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity) throws Exception{
		final String method = "getDiplomado";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method,"entity.getCodigo(): "+ entity.getCodigo());
		try {
        	JSONArray dataJSON = new JSONArray();        	     	
        	SCGDiplomadoEntity diplomado = this.diplomadoService.getForUpdateDiplomado(entity);
        	JSONObject itemJSON = new JSONObject();
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
    				nombreDiplomado=null,
    				fechaResolucionFacultad=null,
    				resolucionFacultad=null,
    				registroDiplomadoFacultad=null,
    				fechaAprobacionCU=null,
    				numeroRegistroDuplicado=null;
        	Long tipoDiplomado=0L,
        			horasLectivas=0L,
        			horasTeoricoPtc=0L,
        			promedioFinal=0L;

        	nombreDiplomado = diplomado.getTextoNombreDiplomado();
        	horasLectivas = diplomado.getNumeroHorasLectivas();
        	horasTeoricoPtc = diplomado.getNumeroHorasTeoricoPtc();
        	promedioFinal = diplomado.getNumeroPromedioFinal();
        	fechaResolucionFacultad=TypesUtil.getDefaultString(diplomado.getFechaResolucionFatultad(), "yyy-MM-dd");
        	resolucionFacultad=diplomado.getTextoResolucionFacultad();
        	registroDiplomadoFacultad=diplomado.getTextoRegistroDiplomadoFacultad();
        	fechaAprobacionCU=TypesUtil.getDefaultString(diplomado.getFechaAprobacionCu(), "yyy-MM-dd");
        	if(diplomado.getTipoDiplomado()!=null){
        		tipoDiplomado=diplomado.getTipoDiplomado().getCodigo();
        	}
        	if(diplomado.getEstudiante()!=null){
    			documentoTipo= diplomado.getEstudiante().getTextoCodigoExternoDocumento();
    			documentoNumero=diplomado.getEstudiante().getTextoNumeroDocumento();
    			fechaIngresanteMatricula=TypesUtil.getDefaultString(diplomado.getEstudiante().getFechaIngresanteMatricula(), "dd-MM-yyyy");
    			egresadoCiclo=diplomado.getEstudiante().getTextoCicloEgreso();
    			egresadoFecha=TypesUtil.getDefaultString(diplomado.getEstudiante().getFechaTramiteConstancia(), "dd-MM-yyyy");
    			matricula=diplomado.getEstudiante().getTextoMatricula();
    			
    			if(diplomado.getEstudiante().getPersona()!=null){
    				apellidoMaterno=diplomado.getEstudiante().getPersona().getTextoMaterno();
    				apellidoPaterno=diplomado.getEstudiante().getPersona().getTextoPaterno();
    				nombre=diplomado.getEstudiante().getPersona().getTextoNombre();
    				sexo=diplomado.getEstudiante().getPersona().getTextoSexo();
    			}
    		}
    		
    		if(diplomado.getAutoridadRegistroRector()!=null){
    			nombreRector=diplomado.getAutoridadRegistroRector().getTextoNombreAutoridad();
    			codigoRector=diplomado.getAutoridadRegistroRector().getCargo().getTextoNombre();
    		}
    		if(diplomado.getAutoridadRegistroDecano()!=null){
    			if(!TypesUtil.isEmptyString(diplomado.getAutoridadRegistroDecano().getTextoNombreAutoridad())){
    				nombreAutoridad=diplomado.getAutoridadRegistroDecano().getTextoNombreAutoridad();
    				tipoAutoridad ="Decano";
    			}
    		}
    		if(diplomado.getAgendaGrupo()!=null){
    			agendaGrupo=diplomado.getAgendaGrupo().getTextoNombre();
    		}
    		if(!TypesUtil.isEmptyString(diplomado.getFlagEnviadoSunedu())){
    			enviadoSunedu="Si";
    		}else{
    			enviadoSunedu="No";
    		}
        	
        	itemJSON.put("codigo", diplomado.getCodigo());
    		itemJSON.put("textoResolucionFacultad", diplomado.getTextoResolucionFacultad());
    		itemJSON.put("textoResolucionRectoral", diplomado.getTextoResolucionRectoral());
    		itemJSON.put("numeroFolio", diplomado.getNumeroFolio());
    		itemJSON.put("numeroLibro", diplomado.getNumeroLibro());
    		itemJSON.put("numeroRegistro", diplomado.getNumeroRegistro());
    		itemJSON.put("flagCandado", diplomado.getFlagCandado());
    		itemJSON.put("fechaCierre",TypesUtil.getDefaultString(diplomado.getFechaCierre(), "dd-MM-yyyy")  );
    		
    		itemJSON.put("fechaAprobacionConsejoUniversitario",  TypesUtil.getDefaultString(diplomado.getFechaAprobacionCu(), "dd-MM-yyyy"));
    		itemJSON.put("fechaInicio",TypesUtil.getDefaultString(diplomado.getFechaInicio(), "dd-MM-yyyy")  );
    		itemJSON.put("fechaFinal",TypesUtil.getDefaultString(diplomado.getFechaFinal(), "dd-MM-yyyy")  );
    		itemJSON.put("textoNombreDiplomado",nombreDiplomado );
    		itemJSON.put("numeroHorasLectivas",horasLectivas );
    		itemJSON.put("numeroHorasTeoricoPtc",horasTeoricoPtc );
    		itemJSON.put("numeroPromedioFinal",promedioFinal );
    		itemJSON.put("fechaResolucionFatultad",fechaResolucionFacultad );
    		itemJSON.put("textoResolucionFacultad",resolucionFacultad );
    		itemJSON.put("textoRegistroDiplomadoFacultad",registroDiplomadoFacultad );
    		itemJSON.put("fechaAprobacionCu",fechaAprobacionCU );
    		itemJSON.put("tipoDiplomado.codigo",tipoDiplomado );
    		
    		if(diplomado.getEstudiante()!=null){
        		itemJSON.put("estudiante.textoNumeroDocumento", diplomado.getEstudiante().getTextoNumeroDocumento());
        		itemJSON.put("estudiante.codigo", diplomado.getEstudiante().getCodigo());
        	}
        	if(diplomado.getEspecialidad()!=null){
        		itemJSON.put("especialidad.codigo", diplomado.getEspecialidad().getCodigo());
        	}
        	if(diplomado.getEspecialidad()!=null){
        		itemJSON.put("especialidad.codigo", diplomado.getEspecialidad().getCodigo());
        	}
    		if(diplomado.getAutoridadRegistroRector()!=null){
    			itemJSON.put("autoridadRegistroRector.codigo", diplomado.getAutoridadRegistroRector().getCodigo());
    		}
    		if(diplomado.getAutoridadRegistroDecano()!=null){
    			itemJSON.put("autoridadRegistroDecano.codigo", diplomado.getAutoridadRegistroDecano().getCodigo());
    		}
    		if(diplomado.getAgendaGrupo()!=null){
    			itemJSON.put("agendaGrupo.codigo", diplomado.getAgendaGrupo().getCodigo());
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
	@RequestMapping("getTipoDiplomadoList")
	public  void getTipoDiplomaList(HttpServletRequest request,HttpServletResponse response,SCGTipoDiplomadoEntity entity) throws Exception{
		final String method = "getTipoDiplomadoList";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGTipoDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = new JSONObject();
		
		UNALMLogger.trace(log, method," entity.getCodigo() "+ entity.getCodigo());
        try {
        	JSONArray dataJSON = new JSONArray();
        	List<SCGTipoDiplomadoEntity> list=this.diplomadoService.getListTipoDiplomado(entity);
            UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGTipoDiplomadoEntity item : list) {
        		JSONObject itemJSON = new JSONObject();
        		itemJSON.put("codigo", item.getCodigo());
        		itemJSON.put("codigoExterno", item.getCodigoExterno());
        		itemJSON.put("textoNombre", item.getTextoNombre());
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
	@RequestMapping("close")
	public  void close(HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity) throws Exception{

		final String method = "close";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method);;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		List<Long> codigoAgenda = new ArrayList<Long>();
		String delimiters = "(\\[)|,|(\\]$)";
		String[] groupList=request.getParameter("groupList").split(":");
		UNALMLogger.trace(log, method, "entity.getFechaCierre(): " + entity.getFechaCierre());
		List<Long> agendas = new ArrayList<Long>();
		List<SCGDiplomadoEntity> sinCerrarList = new ArrayList<SCGDiplomadoEntity>();
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
		
		
		SCGDiplomadoEntity diplomado = new SCGDiplomadoEntity();
		FilterUtil filterUtil = new FilterUtil();
		if(codigoAgenda.size()>0){
			diplomado.setFlagCandado("0");
			List<SCGDiplomadoEntity> abiertoList = this.diplomadoService.getListDiplomado(diplomado,filterUtil, agendas);
			UNALMLogger.trace(log, method, "listAbierto:" + abiertoList.size());
			if(abiertoList.size()>0){
				List<SCGDiplomadoEntity> cerrarList= this.diplomadoService.getListDiplomado(diplomado,filterUtil, codigoAgenda);
				UNALMLogger.trace(log, method, "listCerrar:" + cerrarList.size());
				Long count = this.diplomadoService.getNumberRegistroMaxDiplomado();
				UNALMLogger.trace(log, method, "count:" + count);
				
				if(abiertoList.size()>0){
					for(SCGDiplomadoEntity abierto:abiertoList){
						sinCerrarList.add(abierto);
					}
				} 
				
				for(SCGDiplomadoEntity abierto:abiertoList){
					for(SCGDiplomadoEntity cerrar:cerrarList){
						if(abierto.getCodigo()==cerrar.getCodigo()){
							sinCerrarList.remove(abierto);
							UNALMLogger.trace(log, method, "removiendo el registro:" + cerrar.getCodigo());
							UNALMLogger.trace(log, method, "sinCerrarList.size():" + sinCerrarList.size());
						}
		
					}
				}
				
				if(sinCerrarList.size()>0){
					for(SCGDiplomadoEntity item:sinCerrarList){
						item.setAgendaGrupo(new SCGAgendaGrupoEntity());
						item.getAgendaGrupo().setCodigo(1L);
						this.diplomadoService.saveDiplomado(item);
					}
				}
				if(count!=null){
					for(SCGDiplomadoEntity cerrar:cerrarList){
							count++;
							cerrar.setNumeroRegistro(count);
							cerrar.setFlagCandado("1");	
							if(entity.getFechaCierre()!=null){
								cerrar.setFechaCierre(entity.getFechaCierre());
							}
							this.diplomadoService.saveDiplomado(cerrar);	
					}		
				}
				
			}	
		}
		UNALMLogger.exit(log, method);
	};
	@RequestMapping("saveDiplomado")
	public  void save(HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity) throws Exception{

		final String method = "save";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method,"entity.getCodigo(): "+entity.getCodigo());
        UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+entity.getFlagCandado());
        UNALMLogger.trace(log, method,"entity.getFlagEliminado(): "+entity.getFlagEliminado());
        UNALMLogger.trace(log, method,"entity.getFlagEnviadoSunedu(): "+entity.getFlagEnviadoSunedu());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionFacultad(): "+entity.getTextoResolucionFacultad());
        UNALMLogger.trace(log, method,"entity.getTextoResolucionRectoral(): "+entity.getTextoResolucionRectoral());
        UNALMLogger.trace(log, method,"entity.getTextoRegistroDiplomadoFacultad(): "+entity.getTextoRegistroDiplomadoFacultad());
        UNALMLogger.trace(log, method,"entity.getNumeroFolio(): "+entity.getNumeroFolio());
        UNALMLogger.trace(log, method,"entity.getNumeroLibro(): "+entity.getNumeroLibro());
        UNALMLogger.trace(log, method,"entity.getNumeroRegistro(): "+entity.getNumeroRegistro());
        UNALMLogger.trace(log, method,"entity.getFechaCierre(): "+entity.getFechaCierre());
        UNALMLogger.trace(log, method,"entity.getFechaCierre(): "+entity.getFechaInicio());
        UNALMLogger.trace(log, method,"entity.getFechaCierre(): "+entity.getFechaFinal());
        if(entity.getAutoridadRegistroDecano() != null){
        	 UNALMLogger.trace(log, method,"entity.getAutoridadRegistroDecano().getCodigo(): "+entity.getAutoridadRegistroDecano().getCodigo());
        }
        if(entity.getAutoridadRegistroRector() != null){
        	 UNALMLogger.trace(log, method,"entity.getAutoridadRegistroRector().getCodigo(): "+entity.getAutoridadRegistroRector().getCodigo());
        }
        if(entity.getEspecialidad() != null){
        	 UNALMLogger.trace(log, method,"entity.getEspecialidad().getCodigo()"+entity.getEspecialidad().getCodigo());
        }
        if(entity.getEspecialidad() != null){
        	 UNALMLogger.trace(log, method,"entity.getEspecialidad().getCodigo(): "+entity.getEspecialidad().getCodigo());
        }
        if(entity.getEstudiante() != null){
        	 UNALMLogger.trace(log, method,"entity.getEstudiante().getCodigo(): "+entity.getEstudiante().getCodigo());
        	 UNALMLogger.trace(log, method,"entity.getEstudiante().getTextoMatricula(): "+entity.getEstudiante().getTextoMatricula());
        }
        
		
        if(entity.getTipoDiplomado() != null){
        	UNALMLogger.trace(log, method,"entity.getTipoDiplomado().getCodigo(): "+entity.getTipoDiplomado().getCodigo());
       	 	UNALMLogger.trace(log, method,"entity.getTipoDiplomado().getCodigoExterno(): "+entity.getTipoDiplomado().getCodigoExterno());
        };
		
       
		try {

			entity.setFlagCandado("0");
			entity.setFlagEliminado("1");		
			entity.setFechaAgregar(new Date());
			//this.diplomadoService.saveDiplomado(entity);
			if(entity.getCodigo()!=null){
				this.diplomadoService.saveDiplomado(entity);
			}else{
				if(isDuplicateDiplomado(entity)){
					jsonObjectRoot.put("success", false);
					jsonObjectRoot.put("message", "Ya existe este Registro Abierto");
					return;
				}else{
					this.diplomadoService.saveDiplomado(entity);
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
	public void enviarSunedu(HttpServletRequest request, HttpServletResponse response, SCGDiplomadoEntity entity)throws Exception {
		final String method = "enviarSunedu";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			this.diplomadoService.enviarSunedu(entity);
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
	public void delete(HttpServletRequest request, HttpServletResponse response, SCGDiplomadoEntity entity)throws Exception {
		final String method = "delete";
		final String params = "HttpServletRequest request, HttpServletResponse response, SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObjectRoot = null;
		jsonObjectRoot = new JSONObject();
		UNALMLogger.trace(log, method, "entity.getCodigo(): " + entity.getCodigo());
		try {
			this.diplomadoService.delete(entity);;
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
	public ModelAndView excel(HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity) throws IOException {
		final String method = "excel";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGDiplomadoEntity entity";
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
		/*
		if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
    		if(entity.getFlagCandado().equals("B")){
    			entity.setFlagCandado(null);
    		}
    	}else{
    		entity.setFlagCandado("0");
    	}
		*/
		UNALMLogger.trace(log, method,"entity.getNumeroFolio(): "+ entity.getNumeroFolio());
		UNALMLogger.trace(log, method,"entity.getNumeroLibro(): "+ entity.getNumeroLibro());
		UNALMLogger.trace(log, method,"entity.getNumeroRegistro(): "+ entity.getNumeroRegistro());
		UNALMLogger.trace(log, method,"entity.getFechaCierreInicial(): "+ entity.getFechaCierreInicial());
		UNALMLogger.trace(log, method,"entity.getFechaCierreFinal(): "+ entity.getFechaCierreFinal());
		UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+ entity.getFlagCandado());

        try {
    		FilterUtil filterUtil = new FilterUtil();
        	List<SCGDiplomadoEntity> list=this.diplomadoService.getListDiplomado(entity,filterUtil,agendas);
        	UNALMLogger.trace(log, method,"list: "+list.size());
        	for (SCGDiplomadoEntity item : list) {
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
        				actoFecha=null,
        				tipoDiplomado=null,
        				codigoExternoFacultad=null,
        				codigoExternoTipoDiplomado=null,
        				fechaInicio=null,
        				fechaFinal=null,
        				numeroRegistroDuplicado=null,
        				nombreDiplomado=null,
        	    		fechaResolucionFacultad=null,
        	    		resolucionFacultad=null,
        	    		resolucionRecotoral=null,
        	    		registroDiplomadoFacultad=null,
        	    		fechaAprobacionCU=null,
        	    		horasLectivas=null,
                    	horasTeoricoPtc=null,
                    	promedioFinal=null,
		        		numeroRegistro=null,
		        		numeroFolio=null,
		        		numeroLibro=null;
        	
        		List<String> objectDetails = new ArrayList<String>();
        	
        		
        		fechaInicio=TypesUtil.getDefaultString(item.getFechaInicio(), "dd/MM/yyyy");
        		fechaFinal=TypesUtil.getDefaultString(item.getFechaFinal(),"dd/MM/yyyy");
        		fechaResolucionFacultad =TypesUtil.getDefaultString(item.getFechaResolucionFatultad(),"dd/MM/yyyy");
        		fechaAprobacionCU=TypesUtil.getDefaultString(item.getFechaAprobacionCu(),"dd/MM/yyyy");
        		
        		resolucionFacultad =item.getTextoResolucionFacultad();
        		resolucionRecotoral=item.getTextoResolucionFacultad();
        		registroDiplomadoFacultad=item.getTextoRegistroDiplomadoFacultad();
        		if(item.getNumeroRegistro()!=null){
        			numeroRegistro=String.valueOf(item.getNumeroRegistro());
        		}
        		if(item.getNumeroFolio()!=null){
        			numeroFolio=String.valueOf(item.getNumeroFolio());
        		}
        		if(item.getNumeroLibro()!=null){
        			numeroLibro=String.valueOf(item.getNumeroLibro());
        		}
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
        		
        		if(item.getAutoridadRegistroRector()!=null){
        			nombreRector=item.getAutoridadRegistroRector().getTextoNombreAutoridad();
        			codigoRector=item.getAutoridadRegistroRector().getCargo().getTextoNombre();
        		}
        		
        		if(item.getAutoridadRegistroDecano()!=null){
        			if(!TypesUtil.isEmptyString(item.getAutoridadRegistroDecano().getTextoNombreAutoridad())){
        				nombreAutoridad=item.getAutoridadRegistroDecano().getTextoNombreAutoridad();
        				//tipoAutoridad =item.getAutoridadRegistroDecano().getCargo().getTextoNombre();
        				tipoAutoridad ="Decano";
        			}
        		}
        		if(item.getAgendaGrupo()!=null){
        			agendaGrupo=item.getAgendaGrupo().getTextoNombre();
        		}
        		if(item.getTipoDiplomado()!=null){
        			tipoDiplomado=item.getTipoDiplomado().getTextoNombre();
        			codigoExternoTipoDiplomado =item.getTipoDiplomado().getCodigoExterno();
        		}
        		if(item.getEspecialidad()!=null){
        			if(item.getEspecialidad().getFacultad()!=null){
        				codigoExternoFacultad=item.getEspecialidad().getFacultad().getCodigoExterno();
        			}
        		}
        		if(item.getNumeroHorasLectivas()!=null){
        			horasLectivas=String.valueOf(item.getNumeroHorasLectivas());
        		}
        		if(item.getNumeroHorasTeoricoPtc()!=null){
        			horasTeoricoPtc=String.valueOf(item.getNumeroHorasTeoricoPtc());
        		}
				if(item.getNumeroPromedioFinal()!=null){
					promedioFinal=String.valueOf(item.getNumeroPromedioFinal());
				}
        		if(!TypesUtil.isEmptyString(item.getFlagEnviadoSunedu())){
        			enviadoSunedu="Si";
        		}else{
        			enviadoSunedu="No";
        		}
    			count++;
    			objectDetails.add(String.valueOf(count));
    			objectDetails.add(codigoExternoFacultad);
    			objectDetails.add(apellidoPaterno);
        		objectDetails.add(apellidoMaterno);
        		objectDetails.add(nombre);
        		objectDetails.add(documentoTipo);
        		objectDetails.add(documentoNumero); 
        		objectDetails.add(sexo);
        		objectDetails.add(codigoExternoTipoDiplomado);
        		objectDetails.add(fechaInicio);
        		objectDetails.add(fechaFinal);
        		objectDetails.add(horasLectivas);
        		objectDetails.add(horasTeoricoPtc);
        		objectDetails.add(promedioFinal);
        		objectDetails.add(fechaResolucionFacultad);
        		objectDetails.add(resolucionFacultad);
        		objectDetails.add(registroDiplomadoFacultad);
        		objectDetails.add(nombreAutoridad);
        		objectDetails.add(nombreRector);
        		objectDetails.add(numeroRegistro);
        		objectDetails.add(resolucionRecotoral);
        		objectDetails.add(fechaAprobacionCU);
        		objectDetails.add(numeroFolio);
        		objectDetails.add(numeroLibro);
        		masterList.add(objectDetails);
        	}
		} catch (Exception e) {
			UNALMLogger.error(log, method, "Exception: ", e);     
		}
		
		List<String> myReportHeader = getTrainingReportHeader();
		masterList.add(0, myReportHeader);
		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, masterList, "ReportGradosTitulos");
		ExcelUtils.generateReport(response, workBook, "ReportTitulos");
		return null;
	}
	
	private List<String> getTrainingReportHeader() {
		List<String> headerList = new ArrayList<String>(15);
		headerList.add("#");
		headerList.add("COD. FAC.");
		headerList.add("AP.PATERNO");
		headerList.add("AP.MATERNO");
		headerList.add("NOMBRES");
		headerList.add("DOC. IDENT.");
		headerList.add("NUMERO");
		headerList.add("SEXO");
		headerList.add("COD. DIPLOMADO");
		headerList.add("FECHA INIC.	");
		headerList.add("FECHA FIN");
		headerList.add("HRS. LECTIVAS");
		headerList.add("HRS. TEÓR.-PRÁCT.");
		headerList.add("PROM.FINAL");
		headerList.add("FECHA RES.FAC");
		headerList.add("RES. FAC.");
		headerList.add("REG.DIPLOMAFAC.");
		headerList.add("DECANO");
		headerList.add("RECTOR");
		headerList.add("REG. S.G");
		headerList.add("RES.C.U");
		headerList.add("FECHA  C.U.");
		headerList.add("FOLIO");
		headerList.add("LIBRO");

		return headerList;
	}
	@RequestMapping(value = "/exportDiploma")
	public void exportDiploma(HttpServletRequest request, HttpServletResponse response,SCGRevalidaEntity entity) throws IOException {

		final String method = "exportDiploma";
		final String params = "HttpServletRequest request, HttpServletResponse response,SCGRevalidaEntity entity)";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=ReporteListaAhorro.pdf");
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
			   textoFechaAprobacioncuOriginal=null;
	
			   
					   
				
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
		    					facultadEspecial =revalida.getEspecialidad().getTextoNombreDenominacion();
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
    		}
			if(revalida.getModalidadEstudio()!=null){
    			modalidadEstudio= revalida.getModalidadEstudio().getCodigoExterno();
    		}
			tipoEmision="O";
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
			itemJSON.put("numeroFolio",  revalida.getNumeroFolio());
			itemJSON.put("numeroRegistro", revalida.getNumeroRegistro());
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
	public boolean isDuplicateDiplomado(SCGDiplomadoEntity entity){
		boolean result=false;
		String resolucionRecotoralFormat=null;
		SCGDiplomadoEntity diplomado= new SCGDiplomadoEntity();
		List<Long> agendas = new ArrayList<Long>();
		FilterUtil filterUtil = new FilterUtil();
		try {
			diplomado.setEstudiante(new SCGEstudianteEntity());
			diplomado.getEstudiante().setTextoNumeroDocumento(entity.getEstudiante().getTextoNumeroDocumento());
			
			resolucionRecotoralFormat = this.diplomadoService.setFormatResolucionRecotoral(entity);
			diplomado.setTextoResolucionRectoral(resolucionRecotoralFormat);
			
			diplomado.setFlagCandado(ApplicationConstant.FLAG_CANDADO_ABIERTO);

			List<SCGDiplomadoEntity> diplomadoList = this.diplomadoService.getListDiplomado(diplomado,filterUtil,agendas);
			if(diplomadoList.size()>0){
				result=true;
			}
		} catch (Exception e) {
			result=true;
		}
		return result;
	}
}
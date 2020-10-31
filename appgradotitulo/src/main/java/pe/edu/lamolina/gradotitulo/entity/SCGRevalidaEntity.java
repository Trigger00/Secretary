package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_REVALIDA", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGRevalidaEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGRevalidaGNRTOR")
	@TableGenerator(name = "SCGRevalidaGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
		         	pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		         	valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_REVALIDA", 
					allocationSize = 1)
	@Column(name = "CO_SCG_REVALIDA")
	private Long codigo;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_UNIVERSIDADBACHILLER")
	private SCGUniversidadEntity universidadBachiller;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_UNIVERSIDADMAESTRIA")
	private SCGUniversidadEntity universidadMaestria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_UNIVERSIDADREV")
	private SCGUniversidadEntity universidadRevalida;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_REVALIDA")
	private SCGTipoRevalidaEntity tipoRevalida;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_MOD_GRADO_TITULO")
	private SCGModalidadGradoTituloEntity modalidadGradoTitulo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_MOD_ESTUDIO")
	private SCGModalidadEstudioEntity modalidadEstudio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESTUDIANTE")
	private SCGEstudianteEntity estudiante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADSG")
	private SCGAutoridadRegistroEntity autoridadRegistroSecretarioGeneral;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADREC")
	private SCGAutoridadRegistroEntity autoridadRegistroRector;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADDEC")
	private SCGAutoridadRegistroEntity autoridadRegistroDecano;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADDICPOST")
	private SCGAutoridadRegistroEntity autoridadRegistroDirectorPostgrado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESPECIALIDAD")
	private SCGEspecialidadEntity especialidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESPECIALIDADPOST")
	private SCGEspecialidadEntity especialidadPostgrado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESPECIALIDADREV")
	private SCGEspecialidadEntity especialidadRevalida;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ADJUNTO")
	private SCGAdjuntoEntity adjunto;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AGENDA_GRUPO")
	private SCGAgendaGrupoEntity agendaGrupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_GRADO_ACADEMICO")
	private SCGGradoAcademicoEntity gradoAcademico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_PROGRAMA_ESTUDIO")
	private SCGProgramaEstudioEntity programaEstudio;
	
	@Column(name = "TX_NOMBREPROGRAMAESTUDIO")
	private String textoNombreProgramaEstudio;
	
	@Column(name = "TX_NOMBRETITULOGRADO")
	private String textoNombreTituloGrado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_FINAL")
	private Date fechaFinal;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_DIPLOMA")
	private Date fechaDiploma;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_APROBACIONCU")
	private Date fechaAprobacionConsejoUniversitario;
	
	@Column(name = "TX_LEGALIZADO")
	private String textoLegalizado;
	
	@Column(name = "TX_RESOLUCIONRECTORAL")
	private String textoResolucionRectoral;
	
	@Column(name = "NU_FOLIO")
	private Long numeroFolio;
	
	@Column(name = "NU_LIBRO")
	private Long numeroLibro;
	
	@Column(name = "NU_REGISTRO")
	private Long numeroRegistro;
	
	@Column(name = "FL_DUPLICADO")
	private String flagDuplicado;
	
	@Column(name = "FL_ENVIADOSUNEDU")
	private String flagEnviadoSunedu;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_AGREGAR")
	private Date fechaAgregar;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_EDICION")
	private Date fechaEdicion;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_ELIMINAR")
	private Date fechaEliminar;
	
	@Column(name = "TX_USUARIOAGREGAR")
	private String textoUsuarioAgregar;
	
	@Column(name = "TX_USUARIOEDICION")
	private String textoUsuarioEdicion;
	
	@Column(name = "TX_USUARIOELIMINAR")
	private String textoUsuarioEliminar;
	
	@Column(name = "TX_RESOLUCIONEPG")
	private String textoResolucionEpg;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_RESOLUCIONEPG")
	private Date fechaResolucionEpg;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_SUTENTACIONTESIS")
	private Date fechaSustentacionTesis;
	
	@Column(name = "TX_NOMBRETESIS")
	private String textoNombreTesis;
	
	@Column(name = "TX_DETALLE")
	private String textoDetalle;
	
	@Column(name = "TX_CODIGOBARRA")
	private String textoCodigoBarra;
	
	@Column(name = "TX_SEMESTREEGRESO")
	private String textoSemestreEgreso;
	
	@Column(name = "FL_CANDADO")
	private String flagCandado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_CIERRE")
	private Date fechaCierre;
	
	@Column(name = "TX_RESOLUCIONFACULTAD")
	private String textoResolucionFacultad;
	
	@Column(name = "TX_NOMBRETRABAJOINVESTIGACION")
	private String textoNombreTrabajoInvestigacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_RESOLUCIONFACULTAD")
	private Date fechaResolucionFacultad;
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaCierreInicial;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaCierreFinal;
	
	@Transient
	private MultipartFile archivoFoto;
	
	
	@Column(name = "TX_SEGUNDAESPECIALIDAD")
	private String textoSegundaEspecialidad;
	
	@Column(name = "NU_CREDITOS")
	private Long numeroCreditos;
	
	@Column(name = "TX_REGISTROMETADATO")
	private String textoRegistroMetadato;
	
	@Column(name = "TX_PROCEDENCIATITPDGC")
	private String textoProcedenciaTituloPedagogico;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_DIPLOMADUPLICADO")
	private Date fechaDiplomaDuplicado;
	
	@Column(name = "TX_PROCGRADOEXT")
	private String textoProcedenciaGradoExtranjero;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_MATRICULAPROGRAMA")
	private Date fechaMatriculaPrograma;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INICIOPROGRAMA")
	private Date fechaInicioPrograma;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_TERMINOPROGRAMA")
	private Date fechaTerminoPrograma;
	
	@Column(name = "FL_DIPLOMA_SEXO")
	private String flagDiplomaSexo;
		
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}



	public SCGUniversidadEntity getUniversidadBachiller() {
		return universidadBachiller;
	}

	public void setUniversidadBachiller(SCGUniversidadEntity universidadBachiller) {
		this.universidadBachiller = universidadBachiller;
	}

	public SCGTipoRevalidaEntity getTipoRevalida() {
		return tipoRevalida;
	}

	public void setTipoRevalida(SCGTipoRevalidaEntity tipoRevalida) {
		this.tipoRevalida = tipoRevalida;
	}

	public SCGModalidadGradoTituloEntity getModalidadGradoTitulo() {
		return modalidadGradoTitulo;
	}

	public void setModalidadGradoTitulo(SCGModalidadGradoTituloEntity modalidadGradoTitulo) {
		this.modalidadGradoTitulo = modalidadGradoTitulo;
	}

	public SCGModalidadEstudioEntity getModalidadEstudio() {
		return modalidadEstudio;
	}

	public void setModalidadEstudio(SCGModalidadEstudioEntity modalidadEstudio) {
		this.modalidadEstudio = modalidadEstudio;
	}

	public SCGEstudianteEntity getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(SCGEstudianteEntity estudiante) {
		this.estudiante = estudiante;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistroSecretarioGeneral() {
		return autoridadRegistroSecretarioGeneral;
	}

	public void setAutoridadRegistroSecretarioGeneral(SCGAutoridadRegistroEntity autoridadRegistroSecretarioGeneral) {
		this.autoridadRegistroSecretarioGeneral = autoridadRegistroSecretarioGeneral;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistroRector() {
		return autoridadRegistroRector;
	}

	public void setAutoridadRegistroRector(SCGAutoridadRegistroEntity autoridadRegistroRector) {
		this.autoridadRegistroRector = autoridadRegistroRector;
	}

	public SCGEspecialidadEntity getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(SCGEspecialidadEntity especialidad) {
		this.especialidad = especialidad;
	}

	public String getTextoNombreTituloGrado() {
		return textoNombreTituloGrado;
	}

	public void setTextoNombreTituloGrado(String textoNombreTituloGrado) {
		this.textoNombreTituloGrado = textoNombreTituloGrado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaDiploma() {
		return fechaDiploma;
	}

	public void setFechaDiploma(Date fechaDiploma) {
		this.fechaDiploma = fechaDiploma;
	}

	public Date getFechaAprobacionConsejoUniversitario() {
		return fechaAprobacionConsejoUniversitario;
	}

	public void setFechaAprobacionConsejoUniversitario(Date fechaAprobacionConsejoUniversitario) {
		this.fechaAprobacionConsejoUniversitario = fechaAprobacionConsejoUniversitario;
	}

	public String getTextoLegalizado() {
		return textoLegalizado;
	}

	public void setTextoLegalizado(String textoLegalizado) {
		this.textoLegalizado = textoLegalizado;
	}

	public String getTextoResolucionRectoral() {
		return textoResolucionRectoral;
	}

	public void setTextoResolucionRectoral(String textoResolucionRectoral) {
		this.textoResolucionRectoral = textoResolucionRectoral;
	}

	public Long getNumeroFolio() {
		return numeroFolio;
	}

	public void setNumeroFolio(Long numeroFolio) {
		this.numeroFolio = numeroFolio;
	}

	public Long getNumeroLibro() {
		return numeroLibro;
	}

	public void setNumeroLibro(Long numeroLibro) {
		this.numeroLibro = numeroLibro;
	}

	public Long getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(Long numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getFlagDuplicado() {
		return flagDuplicado;
	}

	public void setFlagDuplicado(String flagDuplicado) {
		this.flagDuplicado = flagDuplicado;
	}

	public String getFlagEnviadoSunedu() {
		return flagEnviadoSunedu;
	}

	public void setFlagEnviadoSunedu(String flagEnviadoSunedu) {
		this.flagEnviadoSunedu = flagEnviadoSunedu;
	}

	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public Date getFechaEliminar() {
		return fechaEliminar;
	}

	public void setFechaEliminar(Date fechaEliminar) {
		this.fechaEliminar = fechaEliminar;
	}

	public String getTextoUsuarioAgregar() {
		return textoUsuarioAgregar;
	}

	public void setTextoUsuarioAgregar(String textoUsuarioAgregar) {
		this.textoUsuarioAgregar = textoUsuarioAgregar;
	}

	public String getTextoUsuarioEdicion() {
		return textoUsuarioEdicion;
	}

	public void setTextoUsuarioEdicion(String textoUsuarioEdicion) {
		this.textoUsuarioEdicion = textoUsuarioEdicion;
	}

	public String getTextoUsuarioEliminar() {
		return textoUsuarioEliminar;
	}

	public void setTextoUsuarioEliminar(String textoUsuarioEliminar) {
		this.textoUsuarioEliminar = textoUsuarioEliminar;
	}

	public SCGEspecialidadEntity getEspecialidadPostgrado() {
		return especialidadPostgrado;
	}

	public void setEspecialidadPostgrado(SCGEspecialidadEntity especialidadPostgrado) {
		this.especialidadPostgrado = especialidadPostgrado;
	}

	public SCGUniversidadEntity getUniversidadMaestria() {
		return universidadMaestria;
	}

	public void setUniversidadMaestria(SCGUniversidadEntity universidadMaestria) {
		this.universidadMaestria = universidadMaestria;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistroDecano() {
		return autoridadRegistroDecano;
	}

	public void setAutoridadRegistroDecano(SCGAutoridadRegistroEntity autoridadRegistroDecano) {
		this.autoridadRegistroDecano = autoridadRegistroDecano;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistroDirectorPostgrado() {
		return autoridadRegistroDirectorPostgrado;
	}

	public void setAutoridadRegistroDirectorPostgrado(SCGAutoridadRegistroEntity autoridadRegistroDirectorPostgrado) {
		this.autoridadRegistroDirectorPostgrado = autoridadRegistroDirectorPostgrado;
	}

	public SCGAdjuntoEntity getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(SCGAdjuntoEntity adjunto) {
		this.adjunto = adjunto;
	}

	public SCGAgendaGrupoEntity getAgendaGrupo() {
		return agendaGrupo;
	}

	public void setAgendaGrupo(SCGAgendaGrupoEntity agendaGrupo) {
		this.agendaGrupo = agendaGrupo;
	}

	public String getTextoResolucionEpg() {
		return textoResolucionEpg;
	}

	public void setTextoResolucionEpg(String textoResolucionEpg) {
		this.textoResolucionEpg = textoResolucionEpg;
	}

	public Date getFechaResolucionEpg() {
		return fechaResolucionEpg;
	}

	public void setFechaResolucionEpg(Date fechaResolucionEpg) {
		this.fechaResolucionEpg = fechaResolucionEpg;
	}

	public Date getFechaSustentacionTesis() {
		return fechaSustentacionTesis;
	}

	public void setFechaSustentacionTesis(Date fechaSustentacionTesis) {
		this.fechaSustentacionTesis = fechaSustentacionTesis;
	}

	public String getTextoNombreTesis() {
		return textoNombreTesis;
	}

	public void setTextoNombreTesis(String textoNombreTesis) {
		this.textoNombreTesis = textoNombreTesis;
	}

	public String getTextoDetalle() {
		return textoDetalle;
	}

	public void setTextoDetalle(String textoDetalle) {
		this.textoDetalle = textoDetalle;
	}

	public String getTextoCodigoBarra() {
		return textoCodigoBarra;
	}

	public void setTextoCodigoBarra(String textoCodigoBarra) {
		this.textoCodigoBarra = textoCodigoBarra;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getTextoSemestreEgreso() {
		return textoSemestreEgreso;
	}

	public void setTextoSemestreEgreso(String textoSemestreEgreso) {
		this.textoSemestreEgreso = textoSemestreEgreso;
	}

	public String getFlagCandado() {
		return flagCandado;
	}

	public void setFlagCandado(String flagCandado) {
		this.flagCandado = flagCandado;
	}

	public Date getFechaCierreInicial() {
		return fechaCierreInicial;
	}

	public void setFechaCierreInicial(Date fechaCierreInicial) {
		this.fechaCierreInicial = fechaCierreInicial;
	}

	public Date getFechaCierreFinal() {
		return fechaCierreFinal;
	}

	public void setFechaCierreFinal(Date fechaCierreFinal) {
		this.fechaCierreFinal = fechaCierreFinal;
	}

	public SCGGradoAcademicoEntity getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(SCGGradoAcademicoEntity gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public SCGEspecialidadEntity getEspecialidadRevalida() {
		return especialidadRevalida;
	}

	public void setEspecialidadRevalida(SCGEspecialidadEntity especialidadRevalida) {
		this.especialidadRevalida = especialidadRevalida;
	}

	public SCGUniversidadEntity getUniversidadRevalida() {
		return universidadRevalida;
	}

	public void setUniversidadRevalida(SCGUniversidadEntity universidadRevalida) {
		this.universidadRevalida = universidadRevalida;
	}

	public String getTextoResolucionFacultad() {
		return textoResolucionFacultad;
	}

	public void setTextoResolucionFacultad(String textoResolucionFacultad) {
		this.textoResolucionFacultad = textoResolucionFacultad;
	}

	public String getTextoNombreTrabajoInvestigacion() {
		return textoNombreTrabajoInvestigacion;
	}

	public void setTextoNombreTrabajoInvestigacion(String textoNombreTrabajoInvestigacion) {
		this.textoNombreTrabajoInvestigacion = textoNombreTrabajoInvestigacion;
	}

	public Date getFechaResolucionFacultad() {
		return fechaResolucionFacultad;
	}

	public void setFechaResolucionFacultad(Date fechaResolucionFacultad) {
		this.fechaResolucionFacultad = fechaResolucionFacultad;
	}

	public MultipartFile getArchivoFoto() {
		return archivoFoto;
	}

	public void setArchivoFoto(MultipartFile archivoFoto) {
		this.archivoFoto = archivoFoto;
	}

	public String getTextoSegundaEspecialidad() {
		return textoSegundaEspecialidad;
	}

	public void setTextoSegundaEspecialidad(String textoSegundaEspecialidad) {
		this.textoSegundaEspecialidad = textoSegundaEspecialidad;
	}

	public Long getNumeroCreditos() {
		return numeroCreditos;
	}

	public void setNumeroCreditos(Long numeroCreditos) {
		this.numeroCreditos = numeroCreditos;
	}

	public String getTextoRegistroMetadato() {
		return textoRegistroMetadato;
	}

	public void setTextoRegistroMetadato(String textoRegistroMetadato) {
		this.textoRegistroMetadato = textoRegistroMetadato;
	}

	public String getTextoProcedenciaTituloPedagogico() {
		return textoProcedenciaTituloPedagogico;
	}

	public void setTextoProcedenciaTituloPedagogico(String textoProcedenciaTituloPedagogico) {
		this.textoProcedenciaTituloPedagogico = textoProcedenciaTituloPedagogico;
	}

	public Date getFechaDiplomaDuplicado() {
		return fechaDiplomaDuplicado;
	}

	public void setFechaDiplomaDuplicado(Date fechaDiplomaDuplicado) {
		this.fechaDiplomaDuplicado = fechaDiplomaDuplicado;
	}

	public String getTextoProcedenciaGradoExtranjero() {
		return textoProcedenciaGradoExtranjero;
	}

	public void setTextoProcedenciaGradoExtranjero(String textoProcedenciaGradoExtranjero) {
		this.textoProcedenciaGradoExtranjero = textoProcedenciaGradoExtranjero;
	}

	public Date getFechaMatriculaPrograma() {
		return fechaMatriculaPrograma;
	}

	public void setFechaMatriculaPrograma(Date fechaMatriculaPrograma) {
		this.fechaMatriculaPrograma = fechaMatriculaPrograma;
	}

	public Date getFechaInicioPrograma() {
		return fechaInicioPrograma;
	}

	public void setFechaInicioPrograma(Date fechaInicioPrograma) {
		this.fechaInicioPrograma = fechaInicioPrograma;
	}

	public Date getFechaTerminoPrograma() {
		return fechaTerminoPrograma;
	}

	public void setFechaTerminoPrograma(Date fechaTerminoPrograma) {
		this.fechaTerminoPrograma = fechaTerminoPrograma;
	}

	public SCGProgramaEstudioEntity getProgramaEstudio() {
		return programaEstudio;
	}

	public void setProgramaEstudio(SCGProgramaEstudioEntity programaEstudio) {
		this.programaEstudio = programaEstudio;
	}

	public String getTextoNombreProgramaEstudio() {
		return textoNombreProgramaEstudio;
	}

	public void setTextoNombreProgramaEstudio(String textoNombreProgramaEstudio) {
		this.textoNombreProgramaEstudio = textoNombreProgramaEstudio;
	}

	public String getFlagDiplomaSexo() {
		return flagDiplomaSexo;
	}

	public void setFlagDiplomaSexo(String flagDiplomaSexo) {
		this.flagDiplomaSexo = flagDiplomaSexo;
	}

	
}


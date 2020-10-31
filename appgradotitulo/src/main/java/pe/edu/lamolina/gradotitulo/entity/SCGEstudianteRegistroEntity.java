package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_ESTUDIANTE_REGISTRO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGEstudianteRegistroEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGEstudianteRegistroGNRTOR")
	@TableGenerator(name = "SCGEstudianteRegistroGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE, 
					pkColumnValue = "SCG_ESTUDIANTE_REGISTRO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_ESTUDIANTE_REGISTRO")
	private Long codigo;
	
	@Column(name = "CO_SCG_ESTUDIANTEREGISTROPADRE")
	private Long estudianteRegistroPadre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_UNIVERSIDADBACHILLER")
	private SCGUniversidadEntity universidadBachiller;
	


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_UNIVERSIDADMAESTRIA")
	private SCGUniversidadEntity universidadMaestria;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_MOD_GRADO_TITULO")
	private SCGModalidadGradoTituloEntity modalidadGradoTitulo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_MOD_ESTUDIO")
	private SCGModalidadEstudioEntity modalidadEstudio ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADDEC")
	private SCGAutoridadRegistroEntity autoridadRegistroDecano;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADSG")
	private SCGAutoridadRegistroEntity autoridadRegistroSecretarioGeneral;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADREC")
	private SCGAutoridadRegistroEntity autoridadRegistroRector;
	
	
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
	@JoinColumn(name = "CO_SCG_ESTUDIANTE")
	private SCGEstudianteEntity estudiante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_GRADO_ACADEMICO")
	private SCGGradoAcademicoEntity gradoAcademico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AGENDA_GRUPO")
	private SCGAgendaGrupoEntity agendaGrupo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ADJUNTO")
	private SCGAdjuntoEntity adjunto;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_PROGRAMA_ESTUDIO")
	private SCGProgramaEstudioEntity programaEstudio;
	
	@Column(name = "TX_MATRICULAPOST")
	private String textoMatriculaPost;
	
	@Column(name = "TX_RESOLUCIONFACULTAD")
	private String textoResolucionFacultad;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_RESOLUCIONFACULTAD")
	private Date fechaResolucionFacultad;
	
	@Column(name = "TX_RESOLUCIONEPG")
	private String textoResolucionEpg;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_RESOLUCIONEPG")
	private Date fechaResolucionEpg;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_APROBACIONCU")
	private Date fechaAprobacioncu;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_EGRESO_POSGRADO")
	private Date fechaEgresoPosgrado;

	@Column(name = "TX_RESOLUCIONRECTORAL")
	private String textoResolucionRectoral;
	
	@Temporal(TemporalType.DATE)	
	@Column(name = "FE_SUTENTACIONTESIS")
	private Date fechaSustentacionTesis;

	@Column(name = "TX_NOMBRETESIS")
	private String textoNombreTesis;
	
	@Column(name = "TX_NOMBRETRABAJOINVESTIGACION")
	private String textoNombreTrabajoInvestigacion;
	
	@Temporal(TemporalType.DATE)	
	@Column(name = "FE_CONSTANCIAEGRESO")
	private Date fechaConstanciaEgreso;
	
	@Column(name = "TX_DETALLE")
	private String textoDetalle;

	@Column(name = "NU_FOLIO")
	private Long numeroFolio;
	
	@Column(name = "NU_LIBRO")
	private Long numeroLibro;

	@Column(name = "NU_REGISTRO")
	private Long numeroRegistro;



	@Column(name = "TX_CODIGOBARRA")
	private String textoCodigoBarra;

	@Column(name = "FL_DUPLIACADO")
	private String flagDuplicado;

	@Column(name = "FL_MAT20141")
	private String flagMatricula20141;

	@Column(name = "FL_ENVIADOSUNEDU")
	private String flagEnviadoSunedu;

	@Column(name = "TX_SEMESTREEGRESO")
	private String textoSemestreEgreso;

	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;

	@Column(name = "FL_ANULADO")
	private String flagAnulado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_AGREGAR")
	private Date fechaAgregar;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_ELIMINAR")
	private Date fechaEliminar;
 
	@Column(name = "TX_USUARIOAGREGAR")
	private String textoUsuarioAgregar;

	@Column(name = "TX_USUARIOEDICION")
	private String textoUsuarioEdicion;

	@Column(name = "TX_USUARIOELIMINAR")
	private String textoUsuarioEliminar;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_EDICION")
	private Date fechaEdicion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_CIERRE")
	private Date fechaCierre;
	/*
	@Transient
	@Temporal(TemporalType.DATE)
	private Date adjuntoFoto;
	*/
	@Column(name = "FL_CANDADO")
	private String flagCandado;
	
	@Column(name = "FL_DIPLOMA_SEXO")
	private String flagDiplomaSexo;
	
	@Column(name = "TX_RESOLUCIONCAMBIONOMBRE")
	private String textoResolucionCambioNombre;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaCierreInicial;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaCierreFinal;
	
	@Transient
	private MultipartFile adjuntoFoto;
	
	@Transient
	private MultipartFile adjuntoEscaneado;
	
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
	@Column(name = "FE_MATRICULA_POSGRADO")
	private Date fechaMatriculaPosgrado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INICIOPROGRAMA")
	private Date fechaInicioPrograma;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_TERMINOPROGRAMA")
	private Date fechaTerminoPrograma;
	

	@Column(name = "TX_NOMBRE_PROGRAMA_ESTUDIO")
	private String textoNombreProgramaEstudio;
	
	@Column(name = "TX_PROCMAESTRIAEXT")
	private String textoProcedenciaMaestriaExtranjero;
	
	
	/*
	@Column(name = "CO_SCG_PROCUNIVERSIDADEXT")
	
	@Column(name = "CO_SCG_PROGRAMA_ESTUDIO");
	*/	
	@OneToMany(mappedBy = "estudianteRegistro", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroAdjuntoEntity> estudianteRegistroAdjuntoList;
	
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

	public SCGUniversidadEntity getUniversidadMaestria() {
		return universidadMaestria;
	}

	public void setUniversidadMaestria(SCGUniversidadEntity universidadMaestria) {
		this.universidadMaestria = universidadMaestria;
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

	public SCGAutoridadRegistroEntity getAutoridadRegistroDecano() {
		return autoridadRegistroDecano;
	}

	public void setAutoridadRegistroDecano(SCGAutoridadRegistroEntity autoridadRegistroDecano) {
		this.autoridadRegistroDecano = autoridadRegistroDecano;
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

	public SCGEstudianteEntity getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(SCGEstudianteEntity estudiante) {
		this.estudiante = estudiante;
	}

	public SCGGradoAcademicoEntity getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(SCGGradoAcademicoEntity gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public String getTextoMatriculaPost() {
		return textoMatriculaPost;
	}

	public void setTextoMatriculaPost(String textoMatriculaPost) {
		this.textoMatriculaPost = textoMatriculaPost;
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



	public Date getFechaAprobacioncu() {
		return fechaAprobacioncu;
	}

	public void setFechaAprobacioncu(Date fechaAprobacioncu) {
		this.fechaAprobacioncu = fechaAprobacioncu;
	}

	public String getTextoResolucionRectoral() {
		return textoResolucionRectoral;
	}

	public void setTextoResolucionRectoral(String textoResolucionRectoral) {
		this.textoResolucionRectoral = textoResolucionRectoral;
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

	public String getTextoCodigoBarra() {
		return textoCodigoBarra;
	}

	public void setTextoCodigoBarra(String textoCodigoBarra) {
		this.textoCodigoBarra = textoCodigoBarra;
	}

	public String getFlagDuplicado() {
		return flagDuplicado;
	}

	public void setFlagDuplicado(String flagDuplicado) {
		this.flagDuplicado = flagDuplicado;
	}

	public String getFlagMatricula20141() {
		return flagMatricula20141;
	}

	public void setFlagMatricula20141(String flagMatricula20141) {
		this.flagMatricula20141 = flagMatricula20141;
	}

	public String getFlagEnviadoSunedu() {
		return flagEnviadoSunedu;
	}

	public void setFlagEnviadoSunedu(String flagEnviadoSunedu) {
		this.flagEnviadoSunedu = flagEnviadoSunedu;
	}

	public String getTextoSemestreEgreso() {
		return textoSemestreEgreso;
	}

	public void setTextoSemestreEgreso(String textoSemestreEgreso) {
		this.textoSemestreEgreso = textoSemestreEgreso;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
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

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public String getFlagCandado() {
		return flagCandado;
	}

	public void setFlagCandado(String flagCandado) {
		this.flagCandado = flagCandado;
	}

	public String getTextoResolucionFacultad() {
		return textoResolucionFacultad;
	}

	public void setTextoResolucionFacultad(String textoResolucionFacultad) {
		this.textoResolucionFacultad = textoResolucionFacultad;
	}

	public Date getFechaResolucionFacultad() {
		return fechaResolucionFacultad;
	}

	public void setFechaResolucionFacultad(Date fechaResolucionFacultad) {
		this.fechaResolucionFacultad = fechaResolucionFacultad;
	}

	public String getTextoNombreTrabajoInvestigacion() {
		return textoNombreTrabajoInvestigacion;
	}

	public void setTextoNombreTrabajoInvestigacion(String textoNombreTrabajoInvestigacion) {
		this.textoNombreTrabajoInvestigacion = textoNombreTrabajoInvestigacion;
	}

	public Date getFechaConstanciaEgreso() {
		return fechaConstanciaEgreso;
	}

	public void setFechaConstanciaEgreso(Date fechaConstanciaEgreso) {
		this.fechaConstanciaEgreso = fechaConstanciaEgreso;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistroDirectorPostgrado() {
		return autoridadRegistroDirectorPostgrado;
	}

	public void setAutoridadRegistroDirectorPostgrado(SCGAutoridadRegistroEntity autoridadRegistroDirectorPostgrado) {
		this.autoridadRegistroDirectorPostgrado = autoridadRegistroDirectorPostgrado;
	}

	public SCGEspecialidadEntity getEspecialidadPostgrado() {
		return especialidadPostgrado;
	}

	public void setEspecialidadPostgrado(SCGEspecialidadEntity especialidadPostgrado) {
		this.especialidadPostgrado = especialidadPostgrado;
	}

	public SCGAgendaGrupoEntity getAgendaGrupo() {
		return agendaGrupo;
	}

	public void setAgendaGrupo(SCGAgendaGrupoEntity agendaGrupo) {
		this.agendaGrupo = agendaGrupo;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
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

	public SCGAdjuntoEntity getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(SCGAdjuntoEntity adjunto) {
		this.adjunto = adjunto;
	}

	public Long getEstudianteRegistroPadre() {
		return estudianteRegistroPadre;
	}

	public void setEstudianteRegistroPadre(Long estudianteRegistroPadre) {
		this.estudianteRegistroPadre = estudianteRegistroPadre;
	}

	public String getFlagAnulado() {
		return flagAnulado;
	}

	public void setFlagAnulado(String flagAnulado) {
		this.flagAnulado = flagAnulado;
	}

	public String getTextoResolucionCambioNombre() {
		return textoResolucionCambioNombre;
	}

	public void setTextoResolucionCambioNombre(String textoResolucionCambioNombre) {
		this.textoResolucionCambioNombre = textoResolucionCambioNombre;
	}

	public List<SCGEstudianteRegistroAdjuntoEntity> getEstudianteRegistroAdjuntoList() {
		return estudianteRegistroAdjuntoList;
	}

	public void setEstudianteRegistroAdjuntoList(List<SCGEstudianteRegistroAdjuntoEntity> estudianteRegistroAdjuntoList) {
		this.estudianteRegistroAdjuntoList = estudianteRegistroAdjuntoList;
	}
/*
	public Date getAdjuntoFoto() {
		return adjuntoFoto;
	}

	public void setAdjuntoFoto(Date adjuntoFoto) {
		this.adjuntoFoto = adjuntoFoto;
	}
*/

	public MultipartFile getAdjuntoFoto() {
		return adjuntoFoto;
	}

	public void setAdjuntoFoto(MultipartFile adjuntoFoto) {
		this.adjuntoFoto = adjuntoFoto;
	}

	public MultipartFile getAdjuntoEscaneado() {
		return adjuntoEscaneado;
	}

	public void setAdjuntoEscaneado(MultipartFile adjuntoEscaneado) {
		this.adjuntoEscaneado = adjuntoEscaneado;
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

	public Date getFechaEgresoPosgrado() {
		return fechaEgresoPosgrado;
	}

	public void setFechaEgresoPosgrado(Date fechaEgresoPosgrado) {
		this.fechaEgresoPosgrado = fechaEgresoPosgrado;
	}

	public Date getFechaMatriculaPosgrado() {
		return fechaMatriculaPosgrado;
	}

	public void setFechaMatriculaPosgrado(Date fechaMatriculaPosgrado) {
		this.fechaMatriculaPosgrado = fechaMatriculaPosgrado;
	}

	public String getFlagDiplomaSexo() {
		return flagDiplomaSexo;
	}

	public void setFlagDiplomaSexo(String flagDiplomaSexo) {
		this.flagDiplomaSexo = flagDiplomaSexo;
	}

	public String getTextoProcedenciaMaestriaExtranjero() {
		return textoProcedenciaMaestriaExtranjero;
	}

	public void setTextoProcedenciaMaestriaExtranjero(String textoProcedenciaMaestriaExtranjero) {
		this.textoProcedenciaMaestriaExtranjero = textoProcedenciaMaestriaExtranjero;
	}
	
	
	
}

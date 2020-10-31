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

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_DIPLOMADO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGDiplomadoEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGDiplomadoGNRTOR")
	@TableGenerator(name = "SCGDiplomadoGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE, 
					pkColumnValue = "SCG_DIPLOMADO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_DIPLOMADO")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_DIPLOMADO")
	private SCGTipoDiplomadoEntity tipoDiplomado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESTUDIANTE")
	private SCGEstudianteEntity estudiante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADREC")
	private SCGAutoridadRegistroEntity autoridadRegistroRector;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDADDEC")
	private SCGAutoridadRegistroEntity autoridadRegistroDecano;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESPECIALIDAD")
	private SCGEspecialidadEntity especialidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AGENDA_GRUPO")
	private SCGAgendaGrupoEntity agendaGrupo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_FINAL") 
	private Date fechaFinal; 
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INICIO") 
	private Date fechaInicio; 
	
	@Column(name = "NU_HORASLECTIVAS") 
	private Long numeroHorasLectivas;
	
	@Column(name = "NU_HORASTERORICOPTC") 
	private Long  numeroHorasTeoricoPtc;
	
	@Column(name = "TX_RESOLUCIONFACTULTAD") 
	private String textoResolucionFacultad;
	
	@Column(name = "NU_PROMEDIOFINAL") 
	private Long numeroPromedioFinal;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_RESOLUCUINFACULTAD") 
	private Date fechaResolucionFatultad;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_AGREGAR") 
	private Date fechaAgregar;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_APROBACIONCU") 
	private Date fechaAprobacionCu;
	
	@Column(name = "NU_FOLIO") 
	private Long numeroFolio;
	
	@Column(name = "NU_LIBRO") 
	private Long numeroLibro;
	
	@Column(name = "NU_REGISTRO") 
	private Long numeroRegistro;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "FL_ANULADO") 
	private String flagAnulado;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_REGISTRODIPLOMA") 
	private Date fechaRegistroDiploma;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_EDICION") 
	private Date fechaEdicion;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_ELIMINAR") 
	private Date fechaEliminar;
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_CIERRE") 
	private Date fechaCierre;
	
	@Column(name = "TX_USUARIOAGREGAR") 
	private String textoUsuarioAgregar;
	
	@Column(name = "TX_USUARIOEDICION") 
	private String textoUsuarioEdicion;
	
	@Column(name = "TX_USUARIOELIMINAR") 
	private String textoUsuarioEliminar;

	@Column(name = "TX_NOMBREDIPLOMADO") 
	private String textoNombreDiplomado;
	
	@Column(name = "TX_RESOLUCIONRECTORAL") 
	private String textoResolucionRectoral;
	
	@Column(name = "FL_CANDADO")
	private String flagCandado;
	
	@Column(name = "FL_ENVIADOSUNEDU")
	private String flagEnviadoSunedu;
	
	
	@Column(name = "TX_REGISTRODIPLOMADOFACULTAD")
	private String textoRegistroDiplomadoFacultad;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaCierreInicial;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaCierreFinal;
	
	public SCGAgendaGrupoEntity getAgendaGrupo() {
		return agendaGrupo;
	}

	public void setAgendaGrupo(SCGAgendaGrupoEntity agendaGrupo) {
		this.agendaGrupo = agendaGrupo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGTipoDiplomadoEntity getTipoDiplomado() {
		return tipoDiplomado;
	}

	public void setTipoDiplomado(SCGTipoDiplomadoEntity tipoDiplomado) {
		this.tipoDiplomado = tipoDiplomado;
	}

	public SCGEstudianteEntity getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(SCGEstudianteEntity estudiante) {
		this.estudiante = estudiante;
	}



	public SCGAutoridadRegistroEntity getAutoridadRegistroRector() {
		return autoridadRegistroRector;
	}

	public void setAutoridadRegistroRector(SCGAutoridadRegistroEntity autoridadRegistroRector) {
		this.autoridadRegistroRector = autoridadRegistroRector;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistroDecano() {
		return autoridadRegistroDecano;
	}

	public void setAutoridadRegistroDecano(SCGAutoridadRegistroEntity autoridadRegistroDecano) {
		this.autoridadRegistroDecano = autoridadRegistroDecano;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Long getNumeroHorasLectivas() {
		return numeroHorasLectivas;
	}

	public void setNumeroHorasLectivas(Long numeroHorasLectivas) {
		this.numeroHorasLectivas = numeroHorasLectivas;
	}

	public Long getNumeroHorasTeoricoPtc() {
		return numeroHorasTeoricoPtc;
	}

	public void setNumeroHorasTeoricoPtc(Long numeroHorasTeoricoPtc) {
		this.numeroHorasTeoricoPtc = numeroHorasTeoricoPtc;
	}

	public String getTextoResolucionFacultad() {
		return textoResolucionFacultad;
	}

	public void setTextoResolucionFacultad(String textoResolucionFacultad) {
		this.textoResolucionFacultad = textoResolucionFacultad;
	}

	public Long getNumeroPromedioFinal() {
		return numeroPromedioFinal;
	}

	public void setNumeroPromedioFinal(Long numeroPromedioFinal) {
		this.numeroPromedioFinal = numeroPromedioFinal;
	}

	public Date getFechaResolucionFatultad() {
		return fechaResolucionFatultad;
	}

	public void setFechaResolucionFatultad(Date fechaResolucionFatultad) {
		this.fechaResolucionFatultad = fechaResolucionFatultad;
	}

	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}

	public Date getFechaAprobacionCu() {
		return fechaAprobacionCu;
	}

	public void setFechaAprobacionCu(Date fechaAprobacionCu) {
		this.fechaAprobacionCu = fechaAprobacionCu;
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

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public String getFlagAnulado() {
		return flagAnulado;
	}

	public void setFlagAnulado(String flagAnulado) {
		this.flagAnulado = flagAnulado;
	}

	public Date getFechaRegistroDiploma() {
		return fechaRegistroDiploma;
	}

	public void setFechaRegistroDiploma(Date fechaRegistroDiploma) {
		this.fechaRegistroDiploma = fechaRegistroDiploma;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
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

	public String getTextoNombreDiplomado() {
		return textoNombreDiplomado;
	}

	public void setTextoNombreDiplomado(String textoNombreDiplomado) {
		this.textoNombreDiplomado = textoNombreDiplomado;
	}

	public String getTextoResolucionRectoral() {
		return textoResolucionRectoral;
	}

	public void setTextoResolucionRectoral(String textoResolucionRectoral) {
		this.textoResolucionRectoral = textoResolucionRectoral;
	}

	public SCGEspecialidadEntity getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(SCGEspecialidadEntity especialidad) {
		this.especialidad = especialidad;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
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

	public String getFlagEnviadoSunedu() {
		return flagEnviadoSunedu;
	}

	public void setFlagEnviadoSunedu(String flagEnviadoSunedu) {
		this.flagEnviadoSunedu = flagEnviadoSunedu;
	}

	public String getTextoRegistroDiplomadoFacultad() {
		return textoRegistroDiplomadoFacultad;
	}

	public void setTextoRegistroDiplomadoFacultad(String textoRegistroDiplomadoFacultad) {
		this.textoRegistroDiplomadoFacultad = textoRegistroDiplomadoFacultad;
	}
	
}

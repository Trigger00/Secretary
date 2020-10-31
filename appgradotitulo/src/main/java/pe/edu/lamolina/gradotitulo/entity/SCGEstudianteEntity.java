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

import org.springframework.format.annotation.DateTimeFormat;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_ESTUDIANTE", schema = ApplicationConstant.DB_SCHEMA, catalog = ApplicationConstant.DB_CATALOG)
public class SCGEstudianteEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SCGEstudianteGNRTOR")
	@TableGenerator(name = "SCGEstudianteGNRTOR", table = ApplicationConstant.KEY_MANAGER, pkColumnName = ApplicationConstant.KEY_MANAGER_TABLENAME, valueColumnName = ApplicationConstant.KEY_MANAGER_SEQUENCE, pkColumnValue = "SCG_ESTUDIANTE", allocationSize = 1)
	@Column(name = "CO_SCG_ESTUDIANTE")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_PERSONA")
	private SCGPersonaEntity persona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_FACULTAD")
	private SCGFacultadEntity facultad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESPECIALIDAD")
	private SCGEspecialidadEntity especialidad;

	@Column(name = "TX_MATRICULA")
	private String textoMatricula;

	@Column(name = "TX_NUMERORESOLUCION")
	private String textoNumeroResolucion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_CREACION")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_MODIFCACION")
	private Date fechaModificacion;

	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;

	@Column(name = "FL_ENVIADOSUNEDU")
	private String flagEnviadoSunedu;

	@Column(name = "TX_NOMBRECOMPLETO")
	private String textoNombreCompleto;

	@Column(name = "TX_NUMERODOCUMENTO")
	private String textoNumeroDocumento;

	@Column(name = "TX_CODIGOEXTERNODOCUMENTO")
	private String textoCodigoExternoDocumento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INGRESANTEMATRICULA")
	private Date fechaIngresanteMatricula;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_TRAMITECONSTANCIA")
	private Date fechaTramiteConstancia;

	@Column(name = "TX_CICLOEGRESO")
	private String textoCicloEgreso;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private List<SCGDiplomadoEntity> diplomadoList;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaList;

	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroList;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGPersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(SCGPersonaEntity persona) {
		this.persona = persona;
	}

	public SCGFacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(SCGFacultadEntity facultad) {
		this.facultad = facultad;
	}

	public SCGEspecialidadEntity getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(SCGEspecialidadEntity especialidad) {
		this.especialidad = especialidad;
	}

	public String getTextoMatricula() {
		return textoMatricula;
	}

	public void setTextoMatricula(String textoMatricula) {
		this.textoMatricula = textoMatricula;
	}

	public String getTextoNumeroResolucion() {
		return textoNumeroResolucion;
	}

	public void setTextoNumeroResolucion(String textoNumeroResolucion) {
		this.textoNumeroResolucion = textoNumeroResolucion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public String getFlagEnviadoSunedu() {
		return flagEnviadoSunedu;
	}

	public void setFlagEnviadoSunedu(String flagEnviadoSunedu) {
		this.flagEnviadoSunedu = flagEnviadoSunedu;
	}

	public List<SCGDiplomadoEntity> getDiplomadoList() {
		return diplomadoList;
	}

	public void setDiplomadoList(List<SCGDiplomadoEntity> diplomadoList) {
		this.diplomadoList = diplomadoList;
	}

	public List<SCGRevalidaEntity> getRevalidaList() {
		return revalidaList;
	}

	public void setRevalidaList(List<SCGRevalidaEntity> revalidaList) {
		this.revalidaList = revalidaList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroList() {
		return estudianteRegistroList;
	}

	public void setEstudianteRegistroList(List<SCGEstudianteRegistroEntity> estudianteRegistroList) {
		this.estudianteRegistroList = estudianteRegistroList;
	}

	public String getTextoNombreCompleto() {
		return textoNombreCompleto;
	}

	public void setTextoNombreCompleto(String textoNombreCompleto) {
		this.textoNombreCompleto = textoNombreCompleto;
	}

	public String getTextoNumeroDocumento() {
		return textoNumeroDocumento;
	}

	public void setTextoNumeroDocumento(String textoNumeroDocumento) {
		this.textoNumeroDocumento = textoNumeroDocumento;
	}

	public String getTextoCodigoExternoDocumento() {
		return textoCodigoExternoDocumento;
	}

	public void setTextoCodigoExternoDocumento(String textoCodigoExternoDocumento) {
		this.textoCodigoExternoDocumento = textoCodigoExternoDocumento;
	}

	public Date getFechaIngresanteMatricula() {
		return fechaIngresanteMatricula;
	}

	public void setFechaIngresanteMatricula(Date fechaIngresanteMatricula) {
		this.fechaIngresanteMatricula = fechaIngresanteMatricula;
	}

	public Date getFechaTramiteConstancia() {
		return fechaTramiteConstancia;
	}

	public void setFechaTramiteConstancia(Date fechaTramiteConstancia) {
		this.fechaTramiteConstancia = fechaTramiteConstancia;
	}

	public String getTextoCicloEgreso() {
		return textoCicloEgreso;
	}

	public void setTextoCicloEgreso(String textoCicloEgreso) {
		this.textoCicloEgreso = textoCicloEgreso;
	}

}

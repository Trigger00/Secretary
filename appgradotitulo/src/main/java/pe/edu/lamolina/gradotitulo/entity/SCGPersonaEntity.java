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

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_PERSONA", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGPersonaEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGPersonaGNRTOR")
	@TableGenerator(name = "SCGPersonaGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_PERSONA", 
					allocationSize = 1)
	@Column(name = "CO_SCG_PERSONA")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ADJUNTO")
	private SCGAdjuntoEntity adjunto;

	@Column(name = "TX_NOMBRE")
	private String textoNombre;
	
	@Column(name = "TX_PATERNO")
	private String textoPaterno;
	
	@Column(name = "TX_MATERNO")
	private String textoMaterno;
	
	@Column(name = "TX_SEXO")
	private String textoSexo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FE_EDICION")
	private Date fechaEdicion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FE_AGREGAR")
	private Date fechaAgregar;
	
	@Column(name = "TX_NOMBRECOMPLETO")
	private String textoNombreCompleto;
	
	@Column(name = "FL_ANULADO")
	private String flagAnulado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FE_ELIMINAR")
	private Date fechaEliminar;
	
	@Column(name = "TX_USUARIOAGREGAR")
	private String textoUsuaioAgregar;
	
	@Column(name = "TX_USUARIOEDICION")
	private String textoUsuarioEdicion;
	
	@Column(name = "TX_USUARIOELIMINAR")
	private String textoUsuarioEliminar;
	
	@Column(name = "FL_ELEMINADO")
	private String flagEliminado;
	
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
	private List<SCGEstudianteEntity> estudianteList;
	
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
	private List<SCGPersonaDocumentoEntity> personaDocumentoList;
	
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
	private List<SCGPersonaTelefonoEntity> personaTelefonoList;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	

	public SCGAdjuntoEntity getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(SCGAdjuntoEntity adjunto) {
		this.adjunto = adjunto;
	}

	public String getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}

	public String getTextoPaterno() {
		return textoPaterno;
	}

	public void setTextoPaterno(String textoPaterno) {
		this.textoPaterno = textoPaterno;
	}

	public String getTextoMaterno() {
		return textoMaterno;
	}

	public void setTextoMaterno(String textoMaterno) {
		this.textoMaterno = textoMaterno;
	}

	public String getTextoSexo() {
		return textoSexo;
	}

	public void setTextoSexo(String textoSexo) {
		this.textoSexo = textoSexo;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}

	public String getTextoNombreCompleto() {
		return textoNombreCompleto;
	}

	public void setTextoNombreCompleto(String textoNombreCompleto) {
		this.textoNombreCompleto = textoNombreCompleto;
	}

	public String getFlagAnulado() {
		return flagAnulado;
	}

	public void setFlagAnulado(String flagAnulado) {
		this.flagAnulado = flagAnulado;
	}



	public Date getFechaEliminar() {
		return fechaEliminar;
	}

	public void setFechaEliminar(Date fechaEliminar) {
		this.fechaEliminar = fechaEliminar;
	}

	public String getTextoUsuaioAgregar() {
		return textoUsuaioAgregar;
	}

	public void setTextoUsuaioAgregar(String textoUsuaioAgregar) {
		this.textoUsuaioAgregar = textoUsuaioAgregar;
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

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public List<SCGEstudianteEntity> getEstudianteList() {
		return estudianteList;
	}

	public void setEstudianteList(List<SCGEstudianteEntity> estudianteList) {
		this.estudianteList = estudianteList;
	}

	public List<SCGPersonaDocumentoEntity> getPersonaDocumentoList() {
		return personaDocumentoList;
	}

	public void setPersonaDocumentoList(List<SCGPersonaDocumentoEntity> personaDocumentoList) {
		this.personaDocumentoList = personaDocumentoList;
	}

	public List<SCGPersonaTelefonoEntity> getPersonaTelefonoList() {
		return personaTelefonoList;
	}

	public void setPersonaTelefonoList(List<SCGPersonaTelefonoEntity> personaTelefonoList) {
		this.personaTelefonoList = personaTelefonoList;
	}

	
}

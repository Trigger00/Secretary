package pe.edu.lamolina.tramitedocumentario.entity;

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
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

@Entity
@Table(name = "SCG_SOLICITUD_PROCESO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGSolicitudProcesoEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGSolicitudProcesoGNRTOR")
	@TableGenerator(name = "SCGSolicitudProcesoGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_SOLICITUD_PROCESO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_SOLICITUD_PROCESO")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SG_USUARIO")
	private SeguridadUsuarioEntity seguridadUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_TRAMITE_DOCUMENTO")
	private SCGTipoTramiteDocumentoEntity tipoTramiteDocumento;
	
	@Column(name = "CO_ASOCIACION")
	private Long codigoAsociacion;
	
	@Column(name = "TX_NOMBREPROCEDENCIA")
	private String textoNombreProcedencia;
	
	@Column(name = "TX_NOMBREREMITENTE")
	private String textoNombreRemitente;
	
	@Column(name = "TX_NUMERODOCUMENTO")
	private String textoNumeroDocumento;
	
	@Column(name = "TX_ASUNTO")
	private String textoAsunto;
	
	@Column(name = "TX_OBSERVACION")
	private String textoObservacion;
	
	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@Column(name = "FL_CANDADO")
	private String flagCandado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FE_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FE_FINAL")
	private Date fechaFinal;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_AGREGAR")
	private Date fechaAgregar;
	
	@Transient
	private String textoTipoColor;
	
	@Transient
	private String textoNombreMatch;
	
	@Transient
	private String distinct;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaAgregarInicio;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaAgregarFinal;
	
	@OneToMany(mappedBy = "solicitudProceso", fetch = FetchType.LAZY)
	private List<SCGDocumentosEntity> documentoList;
	
	@OneToMany(mappedBy = "solicitudProceso", fetch = FetchType.LAZY)
	private List<SCGFlujoProcesoEntity> flujoProcesoList;
	
	@OneToMany(mappedBy = "solicitudProceso", fetch = FetchType.LAZY)
	private List<SCGValoresEntity> valoresList;


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public SeguridadUsuarioEntity getSeguridadUsuario() {
		return seguridadUsuario;
	}


	public void setSeguridadUsuario(SeguridadUsuarioEntity seguridadUsuario) {
		this.seguridadUsuario = seguridadUsuario;
	}


	public List<SCGDocumentosEntity> getDocumentoList() {
		return documentoList;
	}


	public void setDocumentoList(List<SCGDocumentosEntity> documentoList) {
		this.documentoList = documentoList;
	}


	public List<SCGFlujoProcesoEntity> getFlujoProcesoList() {
		return flujoProcesoList;
	}


	public void setFlujoProcesoList(List<SCGFlujoProcesoEntity> flujoProcesoList) {
		this.flujoProcesoList = flujoProcesoList;
	}


	public List<SCGValoresEntity> getValoresList() {
		return valoresList;
	}


	public void setValoresList(List<SCGValoresEntity> valoresList) {
		this.valoresList = valoresList;
	}

	public SCGTipoTramiteDocumentoEntity getTipoTramiteDocumento() {
		return tipoTramiteDocumento;
	}


	public void setTipoTramiteDocumento(SCGTipoTramiteDocumentoEntity tipoTramiteDocumento) {
		this.tipoTramiteDocumento = tipoTramiteDocumento;
	}


	public String getTextoNombreProcedencia() {
		return textoNombreProcedencia;
	}


	public void setTextoNombreProcedencia(String textoNombreProcedencia) {
		this.textoNombreProcedencia = textoNombreProcedencia;
	}


	public String getTextoNombreRemitente() {
		return textoNombreRemitente;
	}


	public void setTextoNombreRemitente(String textoNombreRemitente) {
		this.textoNombreRemitente = textoNombreRemitente;
	}


	public String getTextoNumeroDocumento() {
		return textoNumeroDocumento;
	}


	public void setTextoNumeroDocumento(String textoNumeroDocumento) {
		this.textoNumeroDocumento = textoNumeroDocumento;
	}


	public String getTextoAsunto() {
		return textoAsunto;
	}


	public void setTextoAsunto(String textoAsunto) {
		this.textoAsunto = textoAsunto;
	}


	public String getTextoObservacion() {
		return textoObservacion;
	}


	public void setTextoObservacion(String textoObservacion) {
		this.textoObservacion = textoObservacion;
	}


	public String getTextoNombreMatch() {
		return textoNombreMatch;
	}


	public void setTextoNombreMatch(String textoNombreMatch) {
		this.textoNombreMatch = textoNombreMatch;
	}


	public String getFlagActivo() {
		return flagActivo;
	}


	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}


	public String getFlagEliminado() {
		return flagEliminado;
	}


	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}


	public String getFlagCandado() {
		return flagCandado;
	}


	public void setFlagCandado(String flagCandado) {
		this.flagCandado = flagCandado;
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


	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}

	public Date getFechaAgregarInicio() {
		return fechaAgregarInicio;
	}


	public void setFechaAgregarInicio(Date fechaAgregarInicio) {
		this.fechaAgregarInicio = fechaAgregarInicio;
	}


	public Date getFechaAgregarFinal() {
		return fechaAgregarFinal;
	}


	public void setFechaAgregarFinal(Date fechaAgregarFinal) {
		this.fechaAgregarFinal = fechaAgregarFinal;
	}


	public String getTextoTipoColor() {
		return textoTipoColor;
	}


	public void setTextoTipoColor(String textoTipoColor) {
		this.textoTipoColor = textoTipoColor;
	}


	public String getDistinct() {
		return distinct;
	}


	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}


	public Long getCodigoAsociacion() {
		return codigoAsociacion;
	}


	public void setCodigoAsociacion(Long codigoAsociacion) {
		this.codigoAsociacion = codigoAsociacion;
	}
	
}

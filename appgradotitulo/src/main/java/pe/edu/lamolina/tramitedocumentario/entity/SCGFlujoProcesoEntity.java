package pe.edu.lamolina.tramitedocumentario.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

@Entity
@Table(name = "SCG_FLUJO_PROCESO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGFlujoProcesoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGFlujoProcesoGNRTOR")
	@TableGenerator(name = "SCGFlujoProcesoGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_FLUJO_PROCESO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_FLUJO_PROCESO")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_RESPONSABLE")
	private SCGResponsableEntity responsable;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SG_USUARIO")
	private SeguridadUsuarioEntity seguridadUsuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_SOLICITUD_PROCESO")
	private SCGSolicitudProcesoEntity solicitudProceso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_DEFINICION_PROCESO")
	private SCGDefinicionProcesoEntity definicionProceso;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INICIO")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_FINAL")
	private Date fechaFinal;

	@Column(name = "FL_PROGRAMAR_PROCESO")
	private String flagProgramarProceso;

	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@Column(name = "FL_CANDADO")
	private String flagCandado;
	
	@Column(name = "TX_DETALLE")
	private String textoDetalle;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_AGREGAR")
	private Date fechaAgregar;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaAgregarInicio;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaAgregarFinal;
	
	@Transient
	private String textoTipoOrden;
	
	@Transient
	private String textoTipoColor;
	
	@Transient
	private String flagUsuaroValido;
	
	@Transient
	private String TextoRegistrosAdjuntos;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGResponsableEntity getResponsable() {
		return responsable;
	}

	public void setResponsable(SCGResponsableEntity responsable) {
		this.responsable = responsable;
	}

	public SeguridadUsuarioEntity getSeguridadUsuario() {
		return seguridadUsuario;
	}

	public void setSeguridadUsuario(SeguridadUsuarioEntity seguridadUsuario) {
		this.seguridadUsuario = seguridadUsuario;
	}

	public SCGSolicitudProcesoEntity getSolicitudProceso() {
		return solicitudProceso;
	}

	public void setSolicitudProceso(SCGSolicitudProcesoEntity solicitudProceso) {
		this.solicitudProceso = solicitudProceso;
	}

	public SCGDefinicionProcesoEntity getDefinicionProceso() {
		return definicionProceso;
	}

	public void setDefinicionProceso(SCGDefinicionProcesoEntity definicionProceso) {
		this.definicionProceso = definicionProceso;
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

	public String getFlagProgramarProceso() {
		return flagProgramarProceso;
	}

	public void setFlagProgramarProceso(String flagProgramarProceso) {
		this.flagProgramarProceso = flagProgramarProceso;
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

	public String getTextoDetalle() {
		return textoDetalle;
	}

	public void setTextoDetalle(String textoDetalle) {
		this.textoDetalle = textoDetalle;
	}

	public String getTextoTipoOrden() {
		return textoTipoOrden;
	}

	public void setTextoTipoOrden(String textoTipoOrden) {
		this.textoTipoOrden = textoTipoOrden;
	}

	public String getTextoTipoColor() {
		return textoTipoColor;
	}

	public void setTextoTipoColor(String textoTipoColor) {
		this.textoTipoColor = textoTipoColor;
	}

	public String getFlagUsuaroValido() {
		return flagUsuaroValido;
	}

	public void setFlagUsuaroValido(String flagUsuaroValido) {
		this.flagUsuaroValido = flagUsuaroValido;
	}

	public String getTextoRegistrosAdjuntos() {
		return TextoRegistrosAdjuntos;
	}

	public void setTextoRegistrosAdjuntos(String textoRegistrosAdjuntos) {
		TextoRegistrosAdjuntos = textoRegistrosAdjuntos;
	}
	
	
}

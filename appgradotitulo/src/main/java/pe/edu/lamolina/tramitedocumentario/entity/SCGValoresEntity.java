package pe.edu.lamolina.tramitedocumentario.entity;

import java.io.Serializable;

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

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_VALORES", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGValoresEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGValoresGNRTOR")
	@TableGenerator(name = "SCGValoresGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_VALORES", 
					allocationSize = 1)
	@Column(name = "CO_SCG_VALORES")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ATRIBUTOS")
	private SCGAtributosEntity atributos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_SOLICITUD_PROCESO")
	private SCGSolicitudProcesoEntity solicitudProceso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ARCHIVO_TRAMITE_DOC")
	private SCGArchivoTramiteDocumento archivoTramiteDocumento;
	
	
	@Column(name = "TX_NOMBRE")
	private String textoNombre;
	
	
	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGAtributosEntity getAtributos() {
		return atributos;
	}

	public void setAtributos(SCGAtributosEntity atributos) {
		this.atributos = atributos;
	}

	public SCGSolicitudProcesoEntity getSolicitudProceso() {
		return solicitudProceso;
	}

	public void setSolicitudProceso(SCGSolicitudProcesoEntity solicitudProceso) {
		this.solicitudProceso = solicitudProceso;
	}

	public String getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
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

	public SCGArchivoTramiteDocumento getArchivoTramiteDocumento() {
		return archivoTramiteDocumento;
	}

	public void setArchivoTramiteDocumento(SCGArchivoTramiteDocumento archivoTramiteDocumento) {
		this.archivoTramiteDocumento = archivoTramiteDocumento;
	}

	
	
}

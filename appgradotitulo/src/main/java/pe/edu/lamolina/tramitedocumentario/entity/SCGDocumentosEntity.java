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
@Table(name = "SCG_DOCUMENTOS", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGDocumentosEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGDocumentosGNRTOR")
	@TableGenerator(name = "SCGDocumentosGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_DOCUMENTOS", 
					allocationSize = 1)
	@Column(name = "CO_SCG_DOCUMENTOS")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_DEFINICION_PROCESO")
	private SCGDefinicionProcesoEntity definicionProceso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_SOLICITUD_PROCESO")
	private SCGSolicitudProcesoEntity solicitudProceso;
	
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

	public SCGDefinicionProcesoEntity getDefinicionProceso() {
		return definicionProceso;
	}

	public void setDefinicionProceso(SCGDefinicionProcesoEntity definicionProceso) {
		this.definicionProceso = definicionProceso;
	}

	public SCGSolicitudProcesoEntity getSolicitudProceso() {
		return solicitudProceso;
	}

	public void setSolicitudProceso(SCGSolicitudProcesoEntity solicitudProceso) {
		this.solicitudProceso = solicitudProceso;
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
	
	
}

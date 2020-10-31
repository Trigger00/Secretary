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

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_DEFINICION_PROCESO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGDefinicionProcesoEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGDefinicionProcesoGNRTOR")
	@TableGenerator(name = "SCGDefinicionProcesoGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_DEFINICION_PROCESO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_DEFINICION_PROCESO")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_PROCESO")
	private SCGProcesoEntity proceso;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_TIEMPO_LIMITE")
	private Date fechaTiempoLimite;
	
	@Column(name = "TX_NOMBRE")
	private String textoNombre;
	
	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_INICIO")
	private String flagInicio;
	
	@Column(name = "FL_FINAL")
	private String flagFinal;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@OneToMany(mappedBy = "definicionProceso", fetch = FetchType.LAZY)
	private List<SCGAtributosEntity> atributosList;
	
	@OneToMany(mappedBy = "definicionProcesoOrigen", fetch = FetchType.LAZY)
	private List<SCGDecisionProcesoEntity> decisionProcesoOrigenList;
	
	@OneToMany(mappedBy = "definicionProcesoDestino", fetch = FetchType.LAZY)
	private List<SCGDecisionProcesoEntity> decisionProcesoDestinoList;
	
	@OneToMany(mappedBy = "definicionProceso", fetch = FetchType.LAZY)
	private List<SCGDocumentosEntity> documentosList;
	
	@OneToMany(mappedBy = "definicionProceso", fetch = FetchType.LAZY)
	private List<SCGResponsableEntity> responsableList;
	
	@OneToMany(mappedBy = "definicionProceso", fetch = FetchType.LAZY)
	private List<SCGFlujoProcesoEntity> flujoProcesoList;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGProcesoEntity getProceso() {
		return proceso;
	}

	public void setProceso(SCGProcesoEntity proceso) {
		this.proceso = proceso;
	}

	public Date getFechaTiempoLimite() {
		return fechaTiempoLimite;
	}

	public void setFechaTiempoLimite(Date fechaTiempoLimite) {
		this.fechaTiempoLimite = fechaTiempoLimite;
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

	public List<SCGAtributosEntity> getAtributosList() {
		return atributosList;
	}

	public void setAtributosList(List<SCGAtributosEntity> atributosList) {
		this.atributosList = atributosList;
	}

	public List<SCGDecisionProcesoEntity> getDecisionProcesoOrigenList() {
		return decisionProcesoOrigenList;
	}

	public void setDecisionProcesoOrigenList(List<SCGDecisionProcesoEntity> decisionProcesoOrigenList) {
		this.decisionProcesoOrigenList = decisionProcesoOrigenList;
	}

	public List<SCGDecisionProcesoEntity> getDecisionProcesoDestinoList() {
		return decisionProcesoDestinoList;
	}

	public void setDecisionProcesoDestinoList(List<SCGDecisionProcesoEntity> decisionProcesoDestinoList) {
		this.decisionProcesoDestinoList = decisionProcesoDestinoList;
	}

	public List<SCGDocumentosEntity> getDocumentosList() {
		return documentosList;
	}

	public void setDocumentosList(List<SCGDocumentosEntity> documentosList) {
		this.documentosList = documentosList;
	}

	public List<SCGResponsableEntity> getResponsableList() {
		return responsableList;
	}

	public void setResponsableList(List<SCGResponsableEntity> responsableList) {
		this.responsableList = responsableList;
	}


	public String getFlagInicio() {
		return flagInicio;
	}

	public void setFlagInicio(String flagInicio) {
		this.flagInicio = flagInicio;
	}

	public String getFlagFinal() {
		return flagFinal;
	}

	public void setFlagFinal(String flagFinal) {
		this.flagFinal = flagFinal;
	}

	public List<SCGFlujoProcesoEntity> getFlujoProcesoList() {
		return flujoProcesoList;
	}

	public void setFlujoProcesoList(List<SCGFlujoProcesoEntity> flujoProcesoList) {
		this.flujoProcesoList = flujoProcesoList;
	}
	
	
	
}

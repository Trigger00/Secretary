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
@Table(name = "SCG_DECISION_PROCESO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGDecisionProcesoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGDecisionProcesoGNRTOR")
	@TableGenerator(name = "SCGDecisionProcesoGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_DECISION_PROCESO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_DECISION_PROCESO")
	private Long codigo;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_DEFINICION_ORIGEN")
	private SCGDefinicionProcesoEntity definicionProcesoOrigen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_DEFINICION_DESTINO")
	private SCGDefinicionProcesoEntity definicionProcesoDestino;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "FL_ACTIVO") 
	private String flagActivo;
	
	@Column(name = "TX_NOMBRE")
	private String textoNombre;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getFlagEliminado() {
		return flagEliminado;
	}
	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}
	public String getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}
	public SCGDefinicionProcesoEntity getDefinicionProcesoOrigen() {
		return definicionProcesoOrigen;
	}
	public void setDefinicionProcesoOrigen(SCGDefinicionProcesoEntity definicionProcesoOrigen) {
		this.definicionProcesoOrigen = definicionProcesoOrigen;
	}
	public SCGDefinicionProcesoEntity getDefinicionProcesoDestino() {
		return definicionProcesoDestino;
	}
	public void setDefinicionProcesoDestino(SCGDefinicionProcesoEntity definicionProcesoDestino) {
		this.definicionProcesoDestino = definicionProcesoDestino;
	}
	
	public String getTextoNombre() {
		return textoNombre;
	}
	
	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}
	
	
}

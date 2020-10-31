package pe.edu.lamolina.tramitedocumentario.entity;

import java.io.Serializable;
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

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_RESPONSABLE", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGResponsableEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGResponsableGNRTOR")
	@TableGenerator(name = "SCGResponsableGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_RESPONSABLE", 
					allocationSize = 1)
	@Column(name = "CO_SCG_RESPONSABLE")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_DEFINICION_PROCESO")
	private SCGDefinicionProcesoEntity definicionProceso;

	@Column(name = "FL_REASIGNABLE")
	private String flagReasignable;
	
	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@Column(name = "TX_NOMBRE")
	private String textoNombre;
	
	@OneToMany(mappedBy = "responsable", fetch = FetchType.LAZY)
	private List<SCGResponsabilidadEntity> responsabilidadList;

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

	public String getFlagReasignable() {
		return flagReasignable;
	}

	public void setFlagReasignable(String flagReasignable) {
		this.flagReasignable = flagReasignable;
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

	public List<SCGResponsabilidadEntity> getResponsabilidadList() {
		return responsabilidadList;
	}

	public void setResponsabilidadList(List<SCGResponsabilidadEntity> responsabilidadList) {
		this.responsabilidadList = responsabilidadList;
	}

	public String getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}
	
}

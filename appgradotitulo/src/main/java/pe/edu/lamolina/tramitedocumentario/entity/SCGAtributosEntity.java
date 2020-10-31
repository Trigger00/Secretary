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
@Table(name = "SCG_ATRIBUTOS", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGAtributosEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGAtributosGNRTOR")
	@TableGenerator(name = "SCGAtributosGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_ATRIBUTOS", 
					allocationSize = 1)
	@Column(name = "CO_SCG_ATRIBUTOS")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_DEFINICION_PROCESO")
	private SCGDefinicionProcesoEntity definicionProceso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_ATRIBUTO")
	private SCGTipoAtributoEntity tipoAtributo;

	@Column(name = "FL_OBLIGATORIO") 
	private String flagObligatorio;
	
	@Column(name = "FL_ACTIVO") 
	private String flagActivo;

	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@OneToMany(mappedBy = "atributos", fetch = FetchType.LAZY)
	private List<SCGValoresEntity> valoresList;
	
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getFlagObligatorio() {
		return flagObligatorio;
	}
	public void setFlagObligatorio(String flagObligatorio) {
		this.flagObligatorio = flagObligatorio;
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
	public SCGDefinicionProcesoEntity getDefinicionProceso() {
		return definicionProceso;
	}
	public void setDefinicionProceso(SCGDefinicionProcesoEntity definicionProceso) {
		this.definicionProceso = definicionProceso;
	}
	public SCGTipoAtributoEntity getTipoAtributo() {
		return tipoAtributo;
	}
	public void setTipoAtributo(SCGTipoAtributoEntity tipoAtributo) {
		this.tipoAtributo = tipoAtributo;
	}
	public List<SCGValoresEntity> getValoresList() {
		return valoresList;
	}
	public void setValoresList(List<SCGValoresEntity> valoresList) {
		this.valoresList = valoresList;
	}
	
	
	
	
}

package pe.edu.lamolina.tramitedocumentario.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_PROCESO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGProcesoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGProcesoGNRTOR")
	@TableGenerator(name = "SCGProcesoGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_PROCESO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_PROCESO")
	private Long codigo;
	
	@Column(name = "CO_EXTERNO")
	private String codigoExterno;

	@Column(name = "TX_NOMBRE")
	private String textoNombre;
	
	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@OneToMany(mappedBy = "proceso", fetch = FetchType.LAZY)
	private List<SCGDefinicionProcesoEntity> definicionProcesoList;
	

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
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

	public List<SCGDefinicionProcesoEntity> getDefinicionProcesoList() {
		return definicionProcesoList;
	}

	public void setDefinicionProcesoList(List<SCGDefinicionProcesoEntity> definicionProcesoList) {
		this.definicionProcesoList = definicionProcesoList;
	}
	
	
}

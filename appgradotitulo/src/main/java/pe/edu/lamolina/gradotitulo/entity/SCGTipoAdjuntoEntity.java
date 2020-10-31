package pe.edu.lamolina.gradotitulo.entity;

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
@Table(name = "SCG_TIPO_ADJUNTO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGTipoAdjuntoEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGTipoAdjuntoGNRTOR")
	@TableGenerator(name = "SCGTipoAdjuntoGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE, 
					pkColumnValue = "SCG_TIPO_ADJUNTO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_TIPO_ADJUNTO")
	private Long codigo;

	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;

	
	@OneToMany(mappedBy = "tipoAdjunto", fetch = FetchType.LAZY)
	private List<SCGAdjuntoEntity> adjuntoList;

	
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

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public List<SCGAdjuntoEntity> getAdjuntoList() {
		return adjuntoList;
	}

	public void setAdjuntoList(List<SCGAdjuntoEntity> adjuntoList) {
		this.adjuntoList = adjuntoList;
	}
	
	
}

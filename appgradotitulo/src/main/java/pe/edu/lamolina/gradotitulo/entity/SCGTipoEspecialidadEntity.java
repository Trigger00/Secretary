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
@Table(name = "SCG_TIPO_ESPECIALIDAD", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGTipoEspecialidadEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGTipoEspecialidadGNRTOR")
	@TableGenerator(name = "SCGTipoEspecialidadGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_TIPO_ESPECIALIDAD", 
					allocationSize = 1)
	@Column(name = "CO_SCG_TIPO_ESPECIALIDAD")
	private Long codigo;
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBREESPANOL") 
	private String textoNombreEspanol;
	
	@Column(name = "TX_NOMBREINGLES") 
	private String textoNombreIngles;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@OneToMany(mappedBy = "tipoEspecialidad", fetch = FetchType.LAZY)
	private List<SCGEspecialidadEntity> especialidadList;
	
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

	public String getTextoNombreEspanol() {
		return textoNombreEspanol;
	}

	public void setTextoNombreEspanol(String textoNombreEspanol) {
		this.textoNombreEspanol = textoNombreEspanol;
	}

	public String getTextoNombreIngles() {
		return textoNombreIngles;
	}

	public void setTextoNombreIngles(String textoNombreIngles) {
		this.textoNombreIngles = textoNombreIngles;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public List<SCGEspecialidadEntity> getEspecialidadList() {
		return especialidadList;
	}

	public void setEspecialidadList(List<SCGEspecialidadEntity> especialidadList) {
		this.especialidadList = especialidadList;
	}


	
}
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
@Table(name = "SCG_FACULTAD", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGFacultadEntity  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGFacultadGNRTOR")
	@TableGenerator(name = "SCGFacultadGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
		         	pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		         	valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_FACULTAD", 
					allocationSize = 1)
	@Column(name = "CO_SCG_FACULTAD")
	private Long codigo;
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBREESPANOL") 
	private String textoNombreEspanol;
	
	@Column(name = "TX_NOMBREINGLES") 
	private String textoNombreIngles;
	
	@Column(name = "TX_NOMBREABREVIADO") 
	private String textoNombreAbreviado;
	
	@Column(name = "FL_ESTADO") 
	private String flagEstado;

	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;

	@OneToMany(mappedBy = "facultad", fetch = FetchType.LAZY)
	private List<SCGEstudianteEntity> estudianteList;
	
	@OneToMany(mappedBy = "facultad", fetch = FetchType.LAZY)
	private List<SCGEspecialidadEntity> especialidaList;
	
	@OneToMany(mappedBy = "facultad", fetch = FetchType.LAZY)
	private List<SCGCargoEntity> cargoList;

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


	public String getTextoNombreAbreviado() {
		return textoNombreAbreviado;
	}

	public void setTextoNombreAbreviado(String textoNombreAbreviado) {
		this.textoNombreAbreviado = textoNombreAbreviado;
	}


	public String getFlagEstado() {
		return flagEstado;
	}

	public void setFlagEstado(String flagEstado) {
		this.flagEstado = flagEstado;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public List<SCGEstudianteEntity> getEstudianteList() {
		return estudianteList;
	}

	public void setEstudianteList(List<SCGEstudianteEntity> estudianteList) {
		this.estudianteList = estudianteList;
	}

	public List<SCGEspecialidadEntity> getEspecialidaList() {
		return especialidaList;
	}

	public void setEspecialidaList(List<SCGEspecialidadEntity> especialidaList) {
		this.especialidaList = especialidaList;
	}

	public List<SCGCargoEntity> getCargoList() {
		return cargoList;
	}

	public void setCargoList(List<SCGCargoEntity> cargoList) {
		this.cargoList = cargoList;
	}

	
	
	
}

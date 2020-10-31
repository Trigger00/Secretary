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
@Table(name = "SCG_PROGRAMA_ESTUDIO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGProgramaEstudioEntity  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGProgramaEstudioGNRTOR")
	@TableGenerator(name = "SCGProgramaEstudioGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_PROGRAMA_ESTUDIO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_PROGRAMA_ESTUDIO")
	private Long codigo;
	
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "FL_ACTIVO") 
	private String flagActivo;
	
	@OneToMany(mappedBy = "programaEstudio", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroList;

	@OneToMany(mappedBy = "programaEstudio", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaList;

	
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

	public String getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroList() {
		return estudianteRegistroList;
	}

	public void setEstudianteRegistroList(List<SCGEstudianteRegistroEntity> estudianteRegistroList) {
		this.estudianteRegistroList = estudianteRegistroList;
	}

	public String getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}

	public List<SCGRevalidaEntity> getRevalidaList() {
		return revalidaList;
	}

	public void setRevalidaList(List<SCGRevalidaEntity> revalidaList) {
		this.revalidaList = revalidaList;
	}
	
	
	
	
}

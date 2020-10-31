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
@Table(name = "SCG_GRADO_ACADEMICO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGGradoAcademicoEntity implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGGradoAcademicoGNRTOR")
	@TableGenerator(name = "SCGGradoAcademicoGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_GRADO_ACADEMICO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_GRADO_ACADEMICO")
	private Long codigo;
	
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "CO_SCG_TIPO_ESPECIALIDAD") 
	private Long especialidad;
	
	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "NU_PRIORIDAD") 
	private Long numeroPrioridad;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@OneToMany(mappedBy = "gradoAcademico", fetch = FetchType.LAZY)
	private List<SCGAutoridadRegistroEntity> autoridadRegistroList;
	
	@OneToMany(mappedBy = "gradoAcademico", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroList;

	@OneToMany(mappedBy = "gradoAcademico", fetch = FetchType.LAZY)
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

	public Long getNumeroPrioridad() {
		return numeroPrioridad;
	}

	public void setNumeroPrioridad(Long numeroPrioridad) {
		this.numeroPrioridad = numeroPrioridad;
	}

	public List<SCGAutoridadRegistroEntity> getAutoridadRegistroList() {
		return autoridadRegistroList;
	}

	public void setAutoridadRegistroList(List<SCGAutoridadRegistroEntity> autoridadRegistroList) {
		this.autoridadRegistroList = autoridadRegistroList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroList() {
		return estudianteRegistroList;
	}

	public void setEstudianteRegistroList(List<SCGEstudianteRegistroEntity> estudianteRegistroList) {
		this.estudianteRegistroList = estudianteRegistroList;
	}

	public List<SCGRevalidaEntity> getRevalidaList() {
		return revalidaList;
	}

	public void setRevalidaList(List<SCGRevalidaEntity> revalidaList) {
		this.revalidaList = revalidaList;
	}

	public Long getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Long especialidad) {
		this.especialidad = especialidad;
	}

	
	
}

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
@Table(name = "SCG_MOD_GRADO_TITULO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGModalidadGradoTituloEntity  implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGModalidadGradoGNRTOR")
	@TableGenerator(name = "SCGModalidadGradoGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_MOD_GRADO_TITULO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_MOD_GRADO_TITULO")
	private Long codigo;
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "NU_PRIORIDAD") 
	private Long numeroPrioridad;
	
	@OneToMany(mappedBy = "modalidadGradoTitulo", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaList;

	@OneToMany(mappedBy = "modalidadGradoTitulo", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroList;
	
	
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

	public List<SCGRevalidaEntity> getRevalidaList() {
		return revalidaList;
	}

	public void setRevalidaList(List<SCGRevalidaEntity> revalidaList) {
		this.revalidaList = revalidaList;
	}

	public Long getNumeroPrioridad() {
		return numeroPrioridad;
	}

	public void setNumeroPrioridad(Long numeroPrioridad) {
		this.numeroPrioridad = numeroPrioridad;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroList() {
		return estudianteRegistroList;
	}

	public void setEstudianteRegistroList(List<SCGEstudianteRegistroEntity> estudianteRegistroList) {
		this.estudianteRegistroList = estudianteRegistroList;
	}

	
	
	
	

	
	
}

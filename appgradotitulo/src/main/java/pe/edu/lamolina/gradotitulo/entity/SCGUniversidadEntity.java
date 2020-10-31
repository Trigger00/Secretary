package pe.edu.lamolina.gradotitulo.entity;

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
@Table(name = "SCG_UNIVERSIDAD", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGUniversidadEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGUniversidadGNRTOR")
	@TableGenerator(name = "SCGUniversidadGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
		         	pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		         	valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_UNIVERSIDAD", 
					allocationSize = 1)
	@Column(name = "CO_SCG_UNIVERSIDAD")
	private Long codigo;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_UNIVERSIDAD")
	private SCGTipoUniversidadEntity tipoUniversidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_PAIS")
	private SCGPaisEntity pais;
	

	@Column(name = "TX_SIGLAS")
	private String textoSiglas;
	
	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "TX_NOMBREABREVIADO") 
	private String textoNombreAbreviado;
	
	@OneToMany(mappedBy = "universidadBachiller", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaBachillerList;
	
	@OneToMany(mappedBy = "universidadMaestria", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaMaestriaList;

	@OneToMany(mappedBy = "universidadRevalida", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaProcedenciaList;
	
	@OneToMany(mappedBy = "universidadBachiller", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroBachillerList;
	
	@OneToMany(mappedBy = "universidadMaestria", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroMaestriaList;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	

	public SCGTipoUniversidadEntity getTipoUniversidad() {
		return tipoUniversidad;
	}

	public void setTipoUniversidad(SCGTipoUniversidadEntity tipoUniversidad) {
		this.tipoUniversidad = tipoUniversidad;
	}

	public SCGPaisEntity getPais() {
		return pais;
	}

	public void setPais(SCGPaisEntity pais) {
		this.pais = pais;
	}

	public String getTextoSiglas() {
		return textoSiglas;
	}

	public void setTextoSiglas(String textoSiglas) {
		this.textoSiglas = textoSiglas;
	}

	public String getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public String getTextoNombreAbreviado() {
		return textoNombreAbreviado;
	}

	public void setTextoNombreAbreviado(String textoNombreAbreviado) {
		this.textoNombreAbreviado = textoNombreAbreviado;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroBachillerList() {
		return estudianteRegistroBachillerList;
	}

	public void setEstudianteRegistroBachillerList(List<SCGEstudianteRegistroEntity> estudianteRegistroBachillerList) {
		this.estudianteRegistroBachillerList = estudianteRegistroBachillerList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroMaestriaList() {
		return estudianteRegistroMaestriaList;
	}

	public void setEstudianteRegistroMaestriaList(List<SCGEstudianteRegistroEntity> estudianteRegistroMaestriaList) {
		this.estudianteRegistroMaestriaList = estudianteRegistroMaestriaList;
	}

	public List<SCGRevalidaEntity> getRevalidaBachillerList() {
		return revalidaBachillerList;
	}

	public void setRevalidaBachillerList(List<SCGRevalidaEntity> revalidaBachillerList) {
		this.revalidaBachillerList = revalidaBachillerList;
	}

	public List<SCGRevalidaEntity> getRevalidaMaestriaList() {
		return revalidaMaestriaList;
	}

	public void setRevalidaMaestriaList(List<SCGRevalidaEntity> revalidaMaestriaList) {
		this.revalidaMaestriaList = revalidaMaestriaList;
	}

	public List<SCGRevalidaEntity> getRevalidaProcedenciaList() {
		return revalidaProcedenciaList;
	}

	public void setRevalidaProcedenciaList(List<SCGRevalidaEntity> revalidaProcedenciaList) {
		this.revalidaProcedenciaList = revalidaProcedenciaList;
	}



	
}

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
@Table(name = "SCG_ESPECIALIDAD", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGEspecialidadEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGEspecialidadGNRTOR")
	@TableGenerator(name = "SCGEspecialidadGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
		         	pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		         	valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_ESPECIALIDAD", 
					allocationSize = 1)
	@Column(name = "CO_SCG_ESPECIALIDAD")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_ESPECIALIDAD")
	private SCGTipoEspecialidadEntity tipoEspecialidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_FACULTAD")
	private SCGFacultadEntity facultad;

	@Column(name = "TX_NOMBREESPANOL") 
	private String textoNombreEspanol;
	
	@Column(name = "TX_NOMBREINGLES") 
	private String textoNombreIngles;
	
	@Column(name = "TX_NOMBREABREVIADO") 
	private String textoNombreAbreviado;
	
	@Column(name = "TX_NOMBREDENOMINACION") 
	private String textoNombreDenominacion;
	
	@Column(name = "TX_NOMBREBACHILLER") 
	private String textoNombreBachiller;
	
	@Column(name = "TX_NOMBRECARRERA") 
	private String textoNombreCarrera;
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBREFEMENINO")
	private String textoNombreFemenino;
	
	@Column(name = "FL_ESTADO") 
	private String flagEstado;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;

	@OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidalList;
	
	@OneToMany(mappedBy = "especialidadPostgrado", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaPostgradoList;
	
	@OneToMany(mappedBy = "especialidadRevalida", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaProcedenciaList;
	
	@OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroList;
	
	@OneToMany(mappedBy = "especialidadPostgrado", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroPostgradoList;
	
	@OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
	private List<SCGEstudianteEntity> estudianteList;
	
	@OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
	private List<SCGDiplomadoEntity> diplomadoList;
	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public SCGTipoEspecialidadEntity getTipoEspecialidad() {
		return tipoEspecialidad;
	}


	public void setTipoEspecialidad(SCGTipoEspecialidadEntity tipoEspecialidad) {
		this.tipoEspecialidad = tipoEspecialidad;
	}


	public SCGFacultadEntity getFacultad() {
		return facultad;
	}


	public void setFacultad(SCGFacultadEntity facultad) {
		this.facultad = facultad;
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


	public String getCodigoExterno() {
		return codigoExterno;
	}


	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
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


	public List<SCGRevalidaEntity> getRevalidalList() {
		return revalidalList;
	}


	public void setRevalidalList(List<SCGRevalidaEntity> revalidalList) {
		this.revalidalList = revalidalList;
	}


	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroList() {
		return estudianteRegistroList;
	}


	public void setEstudianteRegistroList(List<SCGEstudianteRegistroEntity> estudianteRegistroList) {
		this.estudianteRegistroList = estudianteRegistroList;
	}


	public List<SCGEstudianteEntity> getEstudianteList() {
		return estudianteList;
	}


	public void setEstudianteList(List<SCGEstudianteEntity> estudianteList) {
		this.estudianteList = estudianteList;
	}


	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroPostgradoList() {
		return estudianteRegistroPostgradoList;
	}


	public void setEstudianteRegistroPostgradoList(List<SCGEstudianteRegistroEntity> estudianteRegistroPostgradoList) {
		this.estudianteRegistroPostgradoList = estudianteRegistroPostgradoList;
	}


	public String getTextoNombreDenominacion() {
		return textoNombreDenominacion;
	}


	public void setTextoNombreDenominacion(String textoNombreDenominacion) {
		this.textoNombreDenominacion = textoNombreDenominacion;
	}


	public String getTextoNombreBachiller() {
		return textoNombreBachiller;
	}


	public void setTextoNombreBachiller(String textoNombreBachiller) {
		this.textoNombreBachiller = textoNombreBachiller;
	}


	public List<SCGRevalidaEntity> getRevalidaPostgradoList() {
		return revalidaPostgradoList;
	}


	public void setRevalidaPostgradoList(List<SCGRevalidaEntity> revalidaPostgradoList) {
		this.revalidaPostgradoList = revalidaPostgradoList;
	}


	public List<SCGRevalidaEntity> getRevalidaProcedenciaList() {
		return revalidaProcedenciaList;
	}


	public void setRevalidaProcedenciaList(List<SCGRevalidaEntity> revalidaProcedenciaList) {
		this.revalidaProcedenciaList = revalidaProcedenciaList;
	}


	public List<SCGDiplomadoEntity> getDiplomadoList() {
		return diplomadoList;
	}


	public void setDiplomadoList(List<SCGDiplomadoEntity> diplomadoList) {
		this.diplomadoList = diplomadoList;
	}


	public String getTextoNombreCarrera() {
		return textoNombreCarrera;
	}


	public void setTextoNombreCarrera(String textoNombreCarrera) {
		this.textoNombreCarrera = textoNombreCarrera;
	}


	public String getTextoNombreFemenino() {
		return textoNombreFemenino;
	}


	public void setTextoNombreFemenino(String textoNombreFemenino) {
		this.textoNombreFemenino = textoNombreFemenino;
	}


	

	
	
	
	
}

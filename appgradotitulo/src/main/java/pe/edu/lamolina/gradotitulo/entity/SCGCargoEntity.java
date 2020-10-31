package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_CARGO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGCargoEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGCargoGNRTOR")
	@TableGenerator(name = "SCGCargoGNRTOR", 
		            table=ApplicationConstant.KEY_MANAGER,
		            pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		            valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_CARGO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_CARGO")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_FACULTAD")
	private SCGFacultadEntity facultad;
	
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;

	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Transient
	private Long especialidad;

	@OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
	private List<SCGAutoridadRegistroEntity> autoridadRegistroList;
	
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

	public List<SCGAutoridadRegistroEntity> getAutoridadRegistroList() {
		return autoridadRegistroList;
	}

	public void setAutoridadRegistroList(List<SCGAutoridadRegistroEntity> autoridadRegistroList) {
		this.autoridadRegistroList = autoridadRegistroList;
	}

	public Long getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Long especialidad) {
		this.especialidad = especialidad;
	}

	public SCGFacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(SCGFacultadEntity facultad) {
		this.facultad = facultad;
	}

	
	
	
}

package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_DEPARTAMENTO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGDepartamentoEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGDepartamentoGNRTOR")
	@TableGenerator(name = "SCGDepartamentoGNRTOR", 
			 		table=ApplicationConstant.KEY_MANAGER,
             		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
             		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE, 
					pkColumnValue = "SCG_DEPARTAMENTO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_DEPARTAMENTO")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_FACULTAD")
	private SCGFacultadEntity facultad;
	
	@Column(name = "CO_EXTERNO") 
	private String codigoExterno;
	
	@Column(name = "TX_NOMBREESPANOL") 
	private String textoNombreEspanol;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "TX_NOMBREINGLES") 
	private String textoNombreIngles;


	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGFacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(SCGFacultadEntity facultad) {
		this.facultad = facultad;
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

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public String getTextoNombreIngles() {
		return textoNombreIngles;
	}

	public void setTextoNombreIngles(String textoNombreIngles) {
		this.textoNombreIngles = textoNombreIngles;
	}
	
	
}

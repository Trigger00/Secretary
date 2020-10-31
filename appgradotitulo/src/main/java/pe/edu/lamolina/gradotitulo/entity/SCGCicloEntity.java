package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_CICLO", schema = ApplicationConstant.DB_SCHEMA, catalog = ApplicationConstant.DB_CATALOG)
public class SCGCicloEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SCGCicloGNRTOR")
	@TableGenerator(name = "SCGCicloGNRTOR", table = ApplicationConstant.KEY_MANAGER, 
					pkColumnName = ApplicationConstant.KEY_MANAGER_TABLENAME, 
					valueColumnName = ApplicationConstant.KEY_MANAGER_SEQUENCE, 
					pkColumnValue = "SCG_CICLO", 
					allocationSize = 1)
	
	@Column(name = "CO_SCG_CICLO")
	private Long codigo;
	
	@Column(name = "TX_CICLO")
	private String textoCiclo;

	@Column(name = "TX_NOMBREESPANOL")
	private String textoNombreEspanol;
	
	@Column(name = "TX_SEMESTRE")
	private String textoSemestre;
	
	@Column(name = "TX_NOMBREINGLES")
	private String textoNombreIngles;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_INICIOCICLO")
	private Date fechaInicioCiclo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_FINCICLO")
	private Date fechaFinalCiclo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getTextoCiclo() {
		return textoCiclo;
	}

	public void setTextoCiclo(String textoCiclo) {
		this.textoCiclo = textoCiclo;
	}

	public String getTextoNombreEspanol() {
		return textoNombreEspanol;
	}

	public void setTextoNombreEspanol(String textoNombreEspanol) {
		this.textoNombreEspanol = textoNombreEspanol;
	}

	public String getTextoSemestre() {
		return textoSemestre;
	}

	public void setTextoSemestre(String textoSemestre) {
		this.textoSemestre = textoSemestre;
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

	public Date getFechaInicioCiclo() {
		return fechaInicioCiclo;
	}

	public void setFechaInicioCiclo(Date fechaInicioCiclo) {
		this.fechaInicioCiclo = fechaInicioCiclo;
	}

	public Date getFechaFinalCiclo() {
		return fechaFinalCiclo;
	}

	public void setFechaFinalCiclo(Date fechaFinalCiclo) {
		this.fechaFinalCiclo = fechaFinalCiclo;
	}
	
	

}

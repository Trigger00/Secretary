package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name = "SCG_PERIODO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGPeriodoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGPeriodoGNRTOR")
	@TableGenerator(name = "SCGPeriodoGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_PERIODO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_PERIODO")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_AUTORIDAD_REGISTRO")
	private SCGAutoridadRegistroEntity autoridadRegistro;

	@Column(name = "FE_INICIO") 
	private Date fechaInicio;
	
	@Column(name = "FE_FINAL") 
	private Date fechaFinal;
	
	@Column(name = "FL_ANULADO") 
	private String flagAnulado;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "TX_CODIGO_PERIODO") 
	private String textoCodigoPeriodo;



	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGAutoridadRegistroEntity getAutoridadRegistro() {
		return autoridadRegistro;
	}

	public void setAutoridadRegistro(SCGAutoridadRegistroEntity autoridadRegistro) {
		this.autoridadRegistro = autoridadRegistro;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getFlagAnulado() {
		return flagAnulado;
	}

	public void setFlagAnulado(String flagAnulado) {
		this.flagAnulado = flagAnulado;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public String getTextoCodigoPeriodo() {
		return textoCodigoPeriodo;
	}

	public void setTextoCodigoPeriodo(String textoCodigoPeriodo) {
		this.textoCodigoPeriodo = textoCodigoPeriodo;
	}
	

}

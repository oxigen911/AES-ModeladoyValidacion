package edu.javeriana.convenio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "convenio")
@XmlRootElement
public class Convenio implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	@NotNull
	private String nombre;
	
	@Column(name = "host")
   @NotNull
	private String host;
	
	@Column(name = "puerto")
   @NotNull
   private Integer puerto;
	
	@Column(name = "urlServicio")
   @NotNull
   private String urlServicio;
	
	@Column(name = "tipo")
   @NotNull
   private String tipo; 

}

package transactionalObjects;

public class Tipo_Recurso {
	
	private String titulo;
	private String carregadoPor;
	private String tipo;
	
	public Tipo_Recurso(String titulo, String carregadoPor, String tipo) {
		super();
		
		this.titulo = titulo;
		this.carregadoPor = carregadoPor;
		this.tipo = tipo;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getCarregadoPor() {
		return carregadoPor;
	}
	
	public void setCarregadoPor(String carregadoPor) {
		this.carregadoPor = carregadoPor;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
		return "Tipo_Recurso [titulo = " + titulo + ", carregadoPor = " + carregadoPor + 
				", tipo = " + tipo + "]";
	}

}

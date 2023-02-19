package transactionalObjects;

public class Poema {

	private String titulo;
	private String carregadoPor;
	
	public Poema(String titulo, String carregadoPor) {
		super();
		this.titulo = titulo;
		this.carregadoPor = carregadoPor;
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
	
	public String toString() {
		return "Poema [Título = " + titulo + ", Carregado Por = " + carregadoPor + "]";
	}
}

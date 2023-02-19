package transactionalObjects;

import java.sql.Date;

public class Filme {

	private String titulo;
	private String carregadoPor;
	private Date anoLancamento;
	
	public Filme(String titulo, String carregadoPor, Date anoLancamento) {
		super();
		this.titulo = titulo;
		this.carregadoPor = carregadoPor;
		this.anoLancamento = anoLancamento;
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
	
	public Date getAnoLancamento() {
		return anoLancamento;
	}
	
	public void setAnoLancamento(Date anoLancamento) {
		this.anoLancamento = anoLancamento;
	}
	
	public String toString() {
		return "Filme [Título = " + titulo + ", Carregado Por = " + carregadoPor + 
				", Ano Lançamento = " + anoLancamento + "]";
	}
}

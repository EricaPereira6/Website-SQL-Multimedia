package transactionalObjects;

public class Tipo_Utilizador {
	
	private String nomeUtilizador;
	private int tipo;
	
	public Tipo_Utilizador(String nomeUtilizador, int tipo) {
		super();
		
		this.nomeUtilizador = nomeUtilizador;
		this.tipo = tipo;
	}
	
	public String getNomeUtilizador() {
		return nomeUtilizador;
	}
	
	public void setNomeUtilizador(String nomeUtilizador) {
		this.nomeUtilizador = nomeUtilizador;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
		return "Tipo_Utilizador [nomeUtilizador = " + nomeUtilizador + ", tipo = " + tipo + "]";
	}

}

package transactionalObjects;

public class Administrador {
	
	private String nomeUtilizador;
	
	public Administrador(String nomeUtilizador) {
		super();
		this.nomeUtilizador = nomeUtilizador;
	}

	public String getNomeUtilizador() {
		return nomeUtilizador;
	}
	public void setNomeUtilizador(String nomeUtilizador) {
		this.nomeUtilizador = nomeUtilizador;
	}

	@Override
	public String toString() {
		return "Administrador [Nome Utilizador = " + nomeUtilizador + "]";
	}

}

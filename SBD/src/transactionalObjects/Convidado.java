package transactionalObjects;

public class Convidado {

	private String nomeUtilizador;
	private boolean bloqueado;
	
	public Convidado(String nomeUtilizador, boolean bloqueado) {
		super();
		this.nomeUtilizador = nomeUtilizador;
		this.bloqueado = bloqueado;
	}

	public String getNomeUtilizador() {
		return nomeUtilizador;
	}
	public void setNomeUtilizador(String nomeUtilizador) {
		this.nomeUtilizador = nomeUtilizador;
	}
	public boolean getBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Override
	public String toString() {
		return "Convidado [Nome Utilizador = " + nomeUtilizador + ", Está Bloqueado = " + bloqueado + "]";
	}
}

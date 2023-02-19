package transactionalObjects;

import java.io.InputStream;
import java.sql.Blob;

public class Artista {
	
	private String nomeArtista;
	private InputStream foto;
	
	public Artista(String nomeArtista, InputStream foto) {
		super();
		this.nomeArtista = nomeArtista;
		this.foto = foto;
	}

	public String getnomeArtista() {
		return nomeArtista;
	}
	public void setnomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	public InputStream getFoto() {
		return foto;
	}
	public void setFoto(InputStream foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Artista [Nome Artista = " + nomeArtista + ", Foto = " + foto + "]";
	}

}

package unoeste.fipp.springplaymysongs.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "musics")
public class Music {
    private Object _id;
    private String titulo;
    private String estilo;
    private String artista;
    private String nomeArquivo;

    public Music() {
    }

    public Music(Object id, String titulo, String estilo, String artista, String nomeArquivo) {
        this._id = id;
        this.titulo = titulo;
        this.estilo = estilo;
        this.artista = artista;
        this.nomeArquivo = nomeArquivo;
    }

    public Object getId() {
        return _id;
    }

    public void setId(Object id) {
        this._id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
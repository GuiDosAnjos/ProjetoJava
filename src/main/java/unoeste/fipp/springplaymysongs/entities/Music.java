package unoeste.fipp.springplaymysongs.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "musics")
public class Music {

    @Id
    private ObjectId _id; // @Id é obrigatório e o tipo deve ser ObjectId
    private String titulo;
    private String estilo;
    private String artista;
    private String nomeArquivo;

    public Music() {
    }

    public Music(ObjectId _id, String titulo, String estilo, String artista, String nomeArquivo) {
        this._id = _id;
        this.titulo = titulo;
        this.estilo = estilo;
        this.artista = artista;
        this.nomeArquivo = nomeArquivo;
    }

    public Music(String titulo, String estilo, String artista, String nomeArquivo) {
        this.titulo = titulo;
        this.estilo = estilo;
        this.artista = artista;
        this.nomeArquivo = nomeArquivo;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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
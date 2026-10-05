package unoeste.fipp.springplaymysongs.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import unoeste.fipp.springplaymysongs.entities.Music;

import java.util.List;

public interface MusicRepository extends MongoRepository<Music, ObjectId> {

    List<Music> findByTituloContainingIgnoreCaseOrEstiloContaningIgnoreCaseOrArtistaContainingIgnoreCase(
            String titulo, String estilo, String artista
    );
}

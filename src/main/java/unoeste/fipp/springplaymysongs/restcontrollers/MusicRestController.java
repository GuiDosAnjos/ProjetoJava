package unoeste.fipp.springplaymysongs.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.springplaymysongs.entities.Erro;
import unoeste.fipp.springplaymysongs.entities.Music;
import unoeste.fipp.springplaymysongs.services.MusicService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "apis")
public class MusicRestController {

    @Autowired
    private MusicService musicService;

    @GetMapping(value = "get-music-styles")
    public ResponseEntity<Object> getMusicStyles() {
        List<String> estilos = musicService.getMusicStyles();
        return ResponseEntity.ok(estilos);
    }

    @GetMapping(value = "find-musics")
    public ResponseEntity<Object> findMusics(@RequestParam(value = "chave", defaultValue = "") String chave) {
        List<Music> musicas = musicService.findMusics(chave);
        if (!musicas.isEmpty()) {
            return ResponseEntity.ok(musicas);
        }
        return ResponseEntity.badRequest().body(new Erro("Nenhuma música encontrada com essa palavra-chave"));
    }

    @PostMapping(value = "music-upload")
    public ResponseEntity<Object> musicUpload(@RequestParam("titulo") String titulo,
                                              @RequestParam("estilo") String estilo,
                                              @RequestParam("artista") String artista,
                                              @RequestParam("file") MultipartFile file) {
        try {
            Music musicaSalva = musicService.salvarMusica(titulo, estilo, artista, file);
            return ResponseEntity.ok(musicaSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Erro("Erro ao processar o upload do áudio: " + e.getMessage()));
        }
    }
}
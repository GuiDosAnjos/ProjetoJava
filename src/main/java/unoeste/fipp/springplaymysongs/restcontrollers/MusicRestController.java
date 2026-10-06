package unoeste.fipp.springplaymysongs.restcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.springplaymysongs.entities.Erro;
import unoeste.fipp.springplaymysongs.entities.Music;
import unoeste.fipp.springplaymysongs.repositories.MusicRepository;

import java.io.File;
import java.text.Normalizer;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "apis")
public class MusicRestController {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private HttpServletRequest request;

    // Método sugerido pelo professor para retornar a URL da área estática
    public String getHostStatic() {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/uploads/";
    }

    // Função auxiliar para formatar texto: minúsculas, sem acentos e sem espaços
    private String formatarTexto(String texto) {
        if (texto == null) return "";
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalizado.replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .replaceAll("\\s+", "");
    }

    @GetMapping(value = "get-music-styles")
    public ResponseEntity<Object> getMusicStyles() {
        List<String> estilos = List.of("Pop", "Rock", "Sertanejo", "Samba", "Funk", "MPB", "Hip Hop", "Eletrônica");
        return ResponseEntity.ok(estilos);
    }

    @GetMapping(value = "find-musics")
    public ResponseEntity<Object> findMusics(@RequestParam(value = "chave", defaultValue = "") String chave) {
        List<Music> musicas = musicRepository.findByTituloContainingIgnoreCaseOrEstiloContainingIgnoreCaseOrArtistaContainingIgnoreCase(chave, chave, chave);
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

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && (originalFilename.toLowerCase().endsWith(".mp3") || originalFilename.toLowerCase().endsWith(".ogg"))) {

            final String UPLOAD_FOLDER = "src\\main\\resources\\static\\uploads";
            File uploadFolder = new File(UPLOAD_FOLDER);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            try {
                // Pega a extensão (.mp3 ou .ogg)
                String extensao = originalFilename.substring(originalFilename.lastIndexOf("."));

                // Formata o nome do arquivo: titulo_estilo_artista.extensao
                String fileName = formatarTexto(titulo) + "_" + formatarTexto(estilo) + "_" + formatarTexto(artista) + extensao;

                File fileSalvo = new File(uploadFolder.getAbsolutePath() + "\\" + fileName);
                file.transferTo(fileSalvo);

                // Monta a URL completa utilizando o método getHostStatic() do professor
                String urlMusica = getHostStatic() + fileName;

                Music novaMusica = new Music(titulo, estilo, artista, urlMusica);
                musicRepository.save(novaMusica);

                return ResponseEntity.ok(novaMusica);

            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new Erro("Erro ao processar o upload do áudio: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.badRequest().body(new Erro("O arquivo deve ser .mp3 ou .ogg"));
        }
    }
}
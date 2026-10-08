package unoeste.fipp.springplaymysongs.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.springplaymysongs.entities.Music;
import unoeste.fipp.springplaymysongs.repositories.MusicRepository;

import java.io.File;
import java.text.Normalizer;
import java.util.List;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private HttpServletRequest request;

    // Retorna a URL base da pasta estática uploads
    public String getHostStatic() {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/uploads/";
    }

    // Trata o texto: converte para minúsculas, remove acentos e espaços em branco
    public String formatarTexto(String texto) {
        if (texto == null) return "";
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalizado.replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .replaceAll("\\s+", "");
    }

    // Retorna a lista de estilos de músicas para o combobox
    public List<String> getMusicStyles() {
        return List.of("Pop", "Rock", "Sertanejo", "Samba", "Funk", "MPB", "Hip Hop", "Eletrônica", "Rap", "Trap", "Outros");
    }

    // Busca músicas no MongoDB por palavra-chave
    public List<Music> findMusics(String chave) {
        return musicRepository.findByTituloContainingIgnoreCaseOrEstiloContainingIgnoreCaseOrArtistaContainingIgnoreCase(chave, chave, chave);
    }

    // Salva o arquivo no disco e registra o documento no MongoDB
    public Music salvarMusica(String titulo, String estilo, String artista, MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || (!originalFilename.toLowerCase().endsWith(".mp3") && !originalFilename.toLowerCase().endsWith(".ogg"))) {
            throw new IllegalArgumentException("O arquivo deve ser .mp3 ou .ogg");
        }

        final String UPLOAD_FOLDER = "src/main/resources/static/uploads/";
        File uploadFolder = new File(UPLOAD_FOLDER);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Pega a extensão (.mp3 ou .ogg)
        String extensao = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Gera o nome formatado: titulo_estilo_artista.extensao
        String fileName = formatarTexto(titulo) + "_" + formatarTexto(estilo) + "_" + formatarTexto(artista) + extensao;

        // Salva o arquivo na pasta de uploads
        File fileSalvo = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
        file.transferTo(fileSalvo);

        // Monta a URL estática para reprodução no player
        String urlMusica = getHostStatic() + fileName;

        Music novaMusica = new Music(titulo, estilo, artista, urlMusica);
        return musicRepository.save(novaMusica);
    }
}
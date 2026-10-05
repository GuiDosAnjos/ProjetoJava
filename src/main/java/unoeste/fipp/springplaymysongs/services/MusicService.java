package unoeste.fipp.springplaymysongs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.springplaymysongs.repositories.MusicRepository;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;
}

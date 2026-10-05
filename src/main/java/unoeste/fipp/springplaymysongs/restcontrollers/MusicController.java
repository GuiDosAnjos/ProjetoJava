package unoeste.fipp.springplaymysongs.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unoeste.fipp.springplaymysongs.services.MusicService;

@RestController
@RequestMapping(value = "apis")
public class MusicController {

    @Autowired
    private MusicService musicService;


}

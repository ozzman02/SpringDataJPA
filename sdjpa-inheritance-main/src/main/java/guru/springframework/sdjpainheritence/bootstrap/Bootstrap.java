package guru.springframework.sdjpainheritence.bootstrap;

import guru.springframework.sdjpainheritence.domain.joinedtable.ElectricGuitar;
import guru.springframework.sdjpainheritence.repository.ElectricGuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    ElectricGuitarRepository electricGuitarRepository;

    @Override
    public void run(String... args) throws Exception {
        ElectricGuitar electricGuitar = new ElectricGuitar();
        electricGuitar.setNumberOfStrings(6);
        electricGuitar.setNumberOfPickups(2);
        electricGuitarRepository.save(electricGuitar);
    }

}

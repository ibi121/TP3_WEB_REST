package a22.climoilou.web2.tp3.rd_pm_ih;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("a22.climoilou.web2.tp3.rd_pm_ih")
public class logistiqueApplication {

    public static void main(String[] args) {
        Application.launch(ApplicationFX.class, args);
    }
}

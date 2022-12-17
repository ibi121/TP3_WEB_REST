package a22.climoilou.web2.tp3.rd_pm_ih;

import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapSpring implements CommandLineRunner {

    public static void main(String[] args) {
        Application.launch(ApplicationFX.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}

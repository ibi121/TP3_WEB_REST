package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.REST.ServiceRestClient;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion.GestionClients;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {


    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(Application.class, args);
        API sr = new API();

        List<String> listeAddresse = new ArrayList<>();


        GestionClients gc = new GestionClients();
        ServiceRestClient serviceRestClient = new ServiceRestClient();


        for (Client client : gc.lireFichierClients()) {
            listeAddresse.add(client.getAdresse());
        }


        sr.TrouverLongLatCLient();


    }


}

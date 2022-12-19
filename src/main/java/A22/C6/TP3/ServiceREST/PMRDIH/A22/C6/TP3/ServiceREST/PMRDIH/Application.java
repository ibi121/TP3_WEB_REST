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
        API api = new API();

//        List<String> listeAddresse = new ArrayList<>();
        GestionClients gc = new GestionClients();
        ServiceRestClient serviceRestClient = new ServiceRestClient();
//
//        api.TrouverLaRouteOptimale(api.CreerRequeteDepart());
//        api.AddresseFormattedForSending();

//        api.ChangerAdresseEnLatLong("131 Rue Principale, Metis%2Dsur%2DMer, QC G0J 1S0");

//        api.CreerRequeteDepart();

        api.lancerApp();

    }


}

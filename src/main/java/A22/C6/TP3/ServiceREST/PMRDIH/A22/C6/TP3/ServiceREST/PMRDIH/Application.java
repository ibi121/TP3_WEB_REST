package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.lecture.LireFichiers;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.GestionClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
		ServiceRest sr = new ServiceRest();

		LireFichiers lireFichiers;


//		sr.EnvoiRequeteGeopify();

		List<Client> listeDeClient = new ArrayList<>();

		GestionClients gc = new GestionClients();

		//sr.PostRequestGeopify();
		//sr.EnvoiRequeteGeopify();
		sr.TrouverLaRouteOptimale();
	}



}

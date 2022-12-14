package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.lecture.LireFichiers;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.GestionClients;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {


	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(Application.class, args);
		ServiceRest sr = new ServiceRest();

		LireFichiers lireFichiers;
		List<String> listeAddresse = new ArrayList<>();


//		sr.EnvoiRequeteGeopify();


		GestionClients gc = new GestionClients();

		//sr.PostRequestGeopify();
		//sr.EnvoiRequeteGeopify();
		//sr.TrouverLaRouteOptimale();
		JSONArray jarray = new JSONArray();

		for (Client client: gc.lireFichierClients()) {
			listeAddresse.add(client.getAdresse());
//			jarray.add(client.getAdresse());
		}

//		System.out.println(listeAddresse);
//		System.out.println(jarray);

		sr.RetrouveLatLong(listeAddresse);
		sr.testOk();

	}



}

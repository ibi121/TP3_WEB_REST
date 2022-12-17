package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Entrepot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GestionEntrepot {
    private List<Entrepot> listeAdresseEntrepot;

    public GestionEntrepot(List<Entrepot> listeAdresseEntrepot) {
        this.listeAdresseEntrepot = this.LireFichierEntrepot();
    }

    public List<Entrepot> LireFichierEntrepot() {

            List<Entrepot> listeEntrepotLus = new ArrayList<>();
            JSONParser jsonP = new JSONParser();
            int id = 1;

            try {
                JSONObject a = (JSONObject) jsonP.parse(new FileReader("entrepot.json"));
                JSONArray clients = (JSONArray) a.get("entrepot");
                for (Object o: clients) {
                    JSONObject x = (JSONObject) o;
                    listeEntrepotLus.add(new Entrepot(id, String.valueOf(x.get("nom")), String.valueOf(x.get("adresse"))));
                    id++;
                }

            } catch (ParseException | IOException e) {
                throw new RuntimeException(e);
            }

            return listeEntrepotLus;
    }
}

package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele;

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
public class GestionClients {

    private List<Client> listeDeClients;


    public GestionClients() {
        this.listeDeClients = this.lireFichierClients();
    }

    public List<Client> getListeDeClients() {
        return listeDeClients;
    }


    public void AjouterClient(Client client) {
        this.listeDeClients.add(client);
    }

    public void retournerAdresse(int i) {
        this.listeDeClients.get(i);
    }


    public List<Client> lireFichierClients() {

        List<Client> listeDeClients = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        int id = 1;

        try {
            JSONObject a = (JSONObject) jsonP.parse(new FileReader("clients.json"));
            JSONArray clients = (JSONArray) a.get("clients");
            for (Object o: clients) {
                JSONObject x = (JSONObject) o;
                listeDeClients.add(new Client(id, String.valueOf(x.get("nom")), String.valueOf(x.get("adresse"))));
                id++;
            }

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return listeDeClients;
    }



}

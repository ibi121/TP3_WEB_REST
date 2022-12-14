package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.lecture;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
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
public class LireFichiers {


    public List<Object> lireFichierClients() {

        List<Object> listeDeClients = new ArrayList<>();
        JSONParser jsonP = new JSONParser();

        try {
            JSONObject a = (JSONObject) jsonP.parse(new FileReader("clients.json"));
            JSONArray clients = (JSONArray) a.get("clients");

            System.out.println(
                    clients.get(5)
            );


        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return listeDeClients;
    }


    public List<String> lireFichierEntrepot(List<String> listeEntrepot){
        JSONParser jsonP = new JSONParser();

        try {
            JSONObject a = (JSONObject) jsonP.parse(new FileReader("entrepot.json"));
            JSONArray clients = (JSONArray) a.get("entrepot");
            listeEntrepot.add(String.valueOf(clients));

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return listeEntrepot;

    }


//    public List<String> getLatLong(){
//
//    }

}



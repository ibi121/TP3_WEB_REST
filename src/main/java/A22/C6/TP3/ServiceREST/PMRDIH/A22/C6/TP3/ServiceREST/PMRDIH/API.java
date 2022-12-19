package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.BD.DbManager;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion.GestionClients;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion.GestionEntrepot;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Patente.JsonObjectModified;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Entrepot;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.RouteOptimale;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Les adresses ne sont pas bonnes.. je ne sais pas pourquoi, ca me fait vraiment vraiment chier,
 * Je suis vraiment proche de l'avoir.
 *
 * Si vous voulez tester, regarder avec postman les resultats et les resultats recu ici.
 *
 * En gros, nous avons des requetes qui ne retourne pas les bonne choses.
 *
 *
 */

@Component
public class API {

    private GestionClients gestionClients = new GestionClients();
    private GestionEntrepot gestionEntrepot = new GestionEntrepot();
    private DbManager BD = new DbManager();


    @Autowired
    public void setGestionClients(GestionClients gestionClients) {
        this.gestionClients = gestionClients;
    }

    @Autowired
    public void setGestionEntrepot(GestionEntrepot gestionEntrepot) {
        this.gestionEntrepot = gestionEntrepot;
    }

    /**
     * Adresses are formatted and returned
     * @return
     */
    public boolean AddresseFormattedForSending(){
        for (Client client: this.gestionClients.getListeAdresseClient()) {
            String adresseOriginal =client.getAdresse();
            adresseOriginal = adresseOriginal.replace("-", "%2D");
            adresseOriginal = adresseOriginal.replace("é", "e");
            adresseOriginal = adresseOriginal.replace("'", "%27");
            client.setAddresseFormatter(adresseOriginal);
        }
        return true;

    }

    /**
     * Loop les clients et set leur latlong avec la methode ChangerAdressEnLatLong
     *
     * @throws IOException
     * @throws ParseException
     */
    public boolean AjouterLatLongCLient() throws IOException, ParseException {
        if(AddresseFormattedForSending()) {
            for (Client client : this.gestionClients.getListeAdresseClient()) {
                client.setLatLong(this.ChangerAdresseEnLatLong(client.getAddresseFormatter()));
            }
        }
        return true;
    }


    /**
     * Meme chose, mais pour l'entrepot :o)
     *
     * @throws IOException
     * @throws ParseException
     */
    public boolean AjouterLatLongEntrpot() throws IOException, ParseException {
        for (Entrepot entrepot : this.gestionEntrepot.getListeAdresseEntrepot()) {
            entrepot.setLatLong(this.ChangerAdresseEnLatLong(entrepot.getAdresse()));
        }

        return true;

    }

    /**
     * Update, ca fonctionne ish.. la requete se creer
     * mais les jobs viennet avanmt les agents alors que ca devrait etre the other way around.
     * idk why,
     * si vous essayer : https://myprojects.geoapify.com/api/PmaKvLtvGdCDfeLfE5PV/keys
     * Suivre le liens pour voir ca ressemble a quoi le body.
     *
     * bye --JauneAttend
     */
    public String CreerRequeteDepart() throws IOException, ParseException {
        JsonObjectModified requeteDate = new JsonObjectModified();
        JSONArray jobs = new JSONArray();
        JSONArray agents = new JSONArray();

        if (AjouterLatLongEntrpot() && AjouterLatLongCLient()) {
            JSONObject requeteAgent = new JSONObject();
            requeteAgent.put("start_location", this.gestionEntrepot.getListeAdresseEntrepot().get(0).getLatLong());
            requeteAgent.put("end_location", this.gestionEntrepot.getListeAdresseEntrepot().get(0).getLatLong());
            requeteAgent.put("pickup_capacity", 10);

            agents.add(requeteAgent);

            for (Client client:this.gestionClients.getListeAdresseClient()) {
                JSONObject requqteClient = new JSONObject();

                requqteClient.put("location", client.getLatLong());
                requqteClient.put("duration", 100);
                requqteClient.put("pickup_amount", 1);

                jobs.add(requqteClient);
            }
        }
        return requeteDate.CustomJsonObject(agents, jobs).toJSONString();
    }


    /**
     * Recoit une adresse,
     * fait un appel POST a geoapify, geocoding,
     * Tri le JSON pour y prendre la Lat et Long
     * Les rajoute dans une liste
     * et les retourne a la methode AjouterLatLongClient.
     *
     * @param addresseAchanger
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<String> ChangerAdresseEnLatLong(String addresseAchanger) throws IOException, ParseException {
        String apiKey = "8fc90cab489e4420b6059a1fdb9f8163";

        List<String> latLongToBreturned = new ArrayList<>();


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();


        URL url = new URL("https://api.geoapify.com/v1/geocode/search?&apiKey="+apiKey+"&text="+addresseAchanger);

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {

            String reponseJson = response.body().string();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reponseJson);

            JSONArray features = (JSONArray) jsonObject.get("features");
            JSONObject idk = (JSONObject) features.get(0);
            JSONObject properties = (JSONObject) idk.get("properties");

            String longitudeClient = properties.get("lon").toString();
            String latitudeClient = properties.get("lat").toString();


            latLongToBreturned.add(longitudeClient);
            latLongToBreturned.add(latitudeClient);

            System.out.println(latLongToBreturned);

        }
        return latLongToBreturned;
    }

    public JSONArray TrouverLaRouteOptimale(String data) throws IOException, ParseException {

        URL url = new URL("https://api.geoapify.com/v1/routeplanner?apiKey=fe815e1c9fc94281b1416e7493715f05");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);
        InputStream inputStream = http.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));

        JSONArray features = (JSONArray) jsonObject.get("features");
        JSONObject transition = (JSONObject) features.get(0);
        JSONObject geometry = (JSONObject) transition.get("geometry");
        JSONArray coordinates = (JSONArray) geometry.get("coordinates");

        http.disconnect();

        return coordinates;
    }

    /**
     * Inserer dans la BD fonctionne,
     * Presentement elle ne fonctionne pas du au commentaire..
     * Elle prendre les lat long de la route et verifie si elles sont egale aux lat long des clients
     * si oui, elle rajouten en BD le nom des clients.
     * @throws IOException
     * @throws ParseException
     */
    public void SauvegarderEnBD() throws IOException, ParseException {
        JSONArray reponseRouteOptimaleEnLatLong = this.TrouverLaRouteOptimale(this.CreerRequeteDepart());
        JSONArray reponseDeLaReponse = new JSONArray();
        int index = 0;

        for (Object reponseArray: reponseRouteOptimaleEnLatLong) {
            JSONArray arrayReponse = (JSONArray) reponseArray;
            reponseDeLaReponse = (JSONArray) arrayReponse.get(0);

            for (Client client: this.gestionClients.getListeAdresseClient()) {
                if(client.getLatLong().get(0).equals(String.valueOf(reponseDeLaReponse.get(0)))) {
                    if(client.getLatLong().get(1).equals(String.valueOf(reponseDeLaReponse.get(1)))){
//                        System.out.println(client.getNom());
//                        BD.AjouterALABD(client.getNom().toString());
                    }
                }
            }
        }
    }

    public void testFetchBD(){
        System.out.println(BD.fetchRoute());
    }

    public void lancerApp() throws IOException, ParseException {
        SauvegarderEnBD();
    }


}

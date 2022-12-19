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
     * Format les caractere spéciaux dans un URL vers les caractere demandé.
     * Elle retourne une boolean pour s'assurer que le loop a été executé
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
     * Lis toutes les addresses des entrepots,
     * Elle appel la méthode SetLatLong qui elle a besoin d'une adresse
     * Elle va, du meme coups, set la latitude et la longitude de lèobject entrepot avec la reponse de la méthode.
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
     * Creer la requete JSON avec les param demandé
     *
     * doit respecter le format disponible dans ce liens : https://apidocs.geoapify.com/docs/route-planner/#about
     *
     * Elle retourne en String un Object JsonObjectModified.
     *
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
     * et les retourne a la methode AjouterLatLongClient().
     *
     * A besoin du APIKEy fournis dans le code, peut etre changé selon le projet
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

    /**
     * Envois une requete POST vers le fournisseur Geoapify,
     * Recois en param un String Data qui est le point longitude latitude a verifier (Methode ChangerAdresseEnLatLong).
     * Retourne un object JSONArray qui contient les reponse des routes.
     * @param data
     * @return
     * @throws IOException
     * @throws ParseException
     */
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
     *Méthode sauvegarder dans la BD
     * Elle prends tous les objects coordonnées recu de la méthode TrouverRouteOptimale
     * Elle compare les LatLOng recu avec avec LatLong disponible dans l'objet CLient.
     * Elle va chercher le nom des clients dans l'ordre de la route.
     * Elle Sauvegarde en BD la route optimale avec les nom de clients.
     *
     * Présentement en commentaire pour éviter les doublons de route en BD.
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

    /**
     * Lance l'application :o)
     * @throws IOException
     * @throws ParseException
     */
    public void lancerApp() throws IOException, ParseException {
        SauvegarderEnBD();
    }


}

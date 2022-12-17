package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion.GestionClients;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion.GestionEntrepot;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Entrepot;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class API {

    private GestionClients gestionClients = new GestionClients();
    private GestionEntrepot gestionEntrepot = new GestionEntrepot();
    String urlTobDeleted = "";

    @Autowired
    public void setGestionClients(GestionClients gestionClients) {
        this.gestionClients = gestionClients;
    }

    @Autowired
    public void setGestionEntrepot(GestionEntrepot gestionEntrepot) {
        this.gestionEntrepot = gestionEntrepot;
    }

    /**
     * Loop les clients et set leur latlong avec la methode ChangerAdressEnLatLong
     *
     * @throws IOException
     * @throws ParseException
     */
    public boolean AjouterLatLongCLient() throws IOException, ParseException {
        for (Client client : this.gestionClients.getListeAdresseClient()) {
            client.setLatLong(this.ChangerAdresseEnLatLong(client.getAdresse()));
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
     * JE PENSE QU'IL Y A UNE MEILLEURE FACON DE FAIRE QUE CA
     * MAIS C'EST TOUT CE QUE J'AI TROUVER
     * Y EST TARD PI J'AI HIT LE BONG
     * CA PROGRAMME PAS FORT FORT :o)
     */

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
        JSONObject rootJsonRequqtes = new JSONObject();
        JSONArray jobs = new JSONArray();
        JSONArray agents = new JSONArray();
//        JSONArray start_location_array = new JSONArray();
//        JSONArray end_location_array = new JSONArray();


        if (AjouterLatLongEntrpot() && AjouterLatLongCLient()) {
//            start_location_array.addAll(this.gestionEntrepot.getListeAdresseEntrepot().get(0).getLatLong());
//            end_location_array.addAll(this.gestionEntrepot.getListeAdresseEntrepot().get(0).getLatLong());

            JSONObject requeteAgent = new JSONObject();
            requeteAgent.put("start_location", this.gestionEntrepot.getListeAdresseEntrepot().get(0).getLatLong());
            requeteAgent.put("end_location", this.gestionEntrepot.getListeAdresseEntrepot().get(0).getLatLong());
            requeteAgent.put("pickup_capacity", 4);

            agents.add(requeteAgent);

            rootJsonRequqtes.put("mode", "drive");


            for (Client client:this.gestionClients.getListeAdresseClient()) {
                JSONObject requqteClient = new JSONObject();
                JSONArray locationJsonArray = new JSONArray();
//                locationJsonArray.add(client.getLatLong());

                requqteClient.put("location", client.getLatLong());
                requqteClient.put("duration", 300);
                requqteClient.put("pickup_amount", 1);

                jobs.add(requqteClient);
            }

            rootJsonRequqtes.put("agents", agents);
            rootJsonRequqtes.put("jobs", jobs);


            String test = rootJsonRequqtes.toJSONString();

            System.out.println(test);

        }
        return "rien";
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
        boolean isChanged = false;
        String apiKey = "8fc90cab489e4420b6059a1fdb9f8163";
        List<String> latLongToBreturned = new ArrayList<>();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.geoapify.com/v1/geocode/search?").newBuilder();
        urlBuilder.addQueryParameter("apiKey", apiKey);
        urlBuilder.addQueryParameter("text", addresseAchanger);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            isChanged = true;
            System.out.println(response);

            String reponseJson = response.body().string();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reponseJson);

            JSONArray features = (JSONArray) jsonObject.get("features");
            JSONObject idk = (JSONObject) features.get(0);
            JSONObject properties = (JSONObject) idk.get("properties");

            String longitudeClient = properties.get("lon").toString();
            String latitudeClient = properties.get("lat").toString();


            latLongToBreturned.add(latitudeClient);
            latLongToBreturned.add(longitudeClient);

        } else {
            isChanged = false;
        }
        return latLongToBreturned;
    }

    public void TrouverLaRouteOptimale(String data) throws IOException, ParseException {
        URL url = new URL("https://api.geoapify.com/v1/routeplanner?apiKey=fe815e1c9fc94281b1416e7493715f05");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

        InputStream inputStream = http.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));

        JSONArray features = (JSONArray) jsonObject.get("features");
        JSONObject transition = (JSONObject) features.get(0);
        JSONObject geometry = (JSONObject) transition.get("geometry");
        JSONArray coordinates = (JSONArray) geometry.get("coordinates");

//        System.out.println(transition);
        System.out.println(geometry.toJSONString());
        http.disconnect();
    }

    //Demande la liste original de clients, prends juste les addresses
    //Les change en jsonArray pour requetes
    //

    /**
     * this is the caca section
     * Must be destroyed before sending
     * :o)
     * Au plisir
     * rien de neuf sous le soleil
     */


//        URL url = new URL("https://api.geoapify.com/v1/geocode/search?apiKey=8fc90cab489e4420b6059a1fdb9f8163");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestMethod("GET");
//
//
//        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
//            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//        }
//
//        InputStream inputStream = conn.getInputStream();
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
//        conn.disconnect();
    public void RetrouveLatLong(List<String> listeAddresseParsed) throws IOException, ParseException {
        List<String> listeLatLong = new ArrayList<>();

        JSONArray addresseJson = new JSONArray();


        addresseJson.addAll(listeAddresseParsed);

        String data = addresseJson.toJSONString();


        //requetes envoye
        URL url = new URL("https://api.geoapify.com/v1/batch/geocode/search/?apiKey=fe815e1c9fc94281b1416e7493715f05");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        //creer tableau de bytes pour la requetes
        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        //lis la reponse
        InputStream inputStream = http.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));

        System.out.println(jsonObject);

        /**
         * To BE DELETED, URL global qui ne doit pas exister pour l'instant :o(
         */
        urlTobDeleted = jsonObject.get("url").toString();

        System.out.println(urlTobDeleted);

    }


}

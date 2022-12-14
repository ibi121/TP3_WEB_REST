package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH;

import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.GestionClients;
import com.fasterxml.jackson.core.JsonParser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ServiceRest {

    private GestionClients gestionClients = new GestionClients();
    String urlTobDeleted = "";

    @Autowired
    public void setGestionClients(GestionClients gestionClients) {
        this.gestionClients = gestionClients;
    }

//    @GetMapping(value = "/client/{adresse}")
//    public Client getClient(@PathVariable String adresse){
//
//
//    }


//    public void PostRequestGeopify() {
//
//        String adresse = gestionClients.getListeDeClients().get(3).getAdresse();
//        String adresse1 = gestionClients.getListeDeClients().get(4).getAdresse();
//
//        OkHttpClient client = new OkHttpClient();
//
//        FormBody formBody = new FormBody.Builder()
//                .add("arrival", adresse1)
//                .build();
//        Request request = new Request.Builder()
//                .url("https://api.geoapify.com/v1/batch?id=JOB_ID&apiKey=8fc90cab489e4420b6059a1fdb9f8163")
//                .post(formBody)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            System.out.println(response);
//
//            // Do something with the response.
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }


    public void EnvoiRequeteGeopify() throws IOException {
        String adresse1 = gestionClients.getListeDeClients().get(4).getAdresse();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.geoapify.com/v1/geocode/search?apiKey=8fc90cab489e4420b6059a1fdb9f8163")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(response);

    }

    public void TrouverLaRouteOptimale() throws IOException, ParseException {


        URL url = new URL("https://api.geoapify.com/v1/routeplanner?apiKey=fe815e1c9fc94281b1416e7493715f05");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = "{\"mode\":\"drive\",\"agents\":[{\"start_location\":[10.985736727197894,48.2649593],\"end_location\":[10.896261152517647,48.33227795],\"pickup_capacity\":4},{\"start_location\":[10.984995162319564,48.264409],\"end_location\":[10.896261152517647,48.33227795],\"pickup_capacity\":7},{\"start_location\":[10.990289270853658,48.2675442],\"end_location\":[10.896261152517647,48.33227795],\"pickup_capacity\":4},{\"start_location\":[10.987279662433057,48.2677992],\"end_location\":[10.896261152517647,48.33227795],\"pickup_capacity\":7},{\"start_location\":[10.983010635351173,48.262753950000004],\"end_location\":[10.896261152517647,48.33227795],\"pickup_capacity\":4}],\"jobs\":[{\"location\":[10.98698105,48.25615875],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.9845547,48.26311145],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.984630924828402,48.263248250000004],\"duration\":300,\"pickup_amount\":2},{\"location\":[10.968364837855287,48.262043399999996],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.984364769628737,48.25542385],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.984062746838354,48.25549435],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.983802751265776,48.25558745],\"duration\":300,\"pickup_amount\":2},{\"location\":[10.983222005227521,48.255775],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.983499356818182,48.25569725],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.982919152872745,48.2558497],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.983681544239769,48.25621035],\"duration\":300,\"pickup_amount\":2},{\"location\":[10.983236456481574,48.2560687],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.984312143079265,48.25577875],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.981143603167904,48.257296600000004],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.9807393,48.25748695],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.981209348235852,48.25786594111741],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.980955539642784,48.2562265],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.979089323915998,48.25726365],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.979089323915998,48.25726365],\"duration\":300,\"pickup_amount\":1},{\"location\":[10.978800955841443,48.25723825],\"duration\":300,\"pickup_amount\":1}]}";

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
    public void RetrouveLatLong(List<String> listeAddresseParsed) throws IOException, ParseException {
        List<String> listeLatLong = new ArrayList<>();

        JSONArray addresseJson = new JSONArray();


        addresseJson.addAll(listeAddresseParsed);

        String data = addresseJson.toJSONString();


        URL url = new URL("https://api.geoapify.com/v1/batch/geocode/search/?apiKey=fe815e1c9fc94281b1416e7493715f05");
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

        System.out.println(jsonObject);

        urlTobDeleted = jsonObject.get("url").toString();

    }

//    public void testOk() throws IOException, ParseException {
//        String Url = urlTobDeleted;
//
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(Url)
//                .method("GET", null)
//                .build();
//        Response response = client.newCall(request).execute();
//
//        if(response.isSuccessful()){
//            response
//        }
//
//    }
//
//

}

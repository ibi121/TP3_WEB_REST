package a22.climoilou.web2.tp3.rd_pm_ih.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConsommationREST {

    //Liste des adresses clients
    //route optimale pour les clients sélectionnés

    private HttpURLConnection con;

    public List<String> getAdressesClient() {
        return GETALL("http://localhost:8080/adresses");
    }

    public String getRouteOptimale(){
        return GETROUTE("http://localhost:8080/routeOptimal");
    }

    private String GETROUTE(String url) {
        String content = null;

        try {
            URL myurl = new URL(url);

            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new String();
            while ((inputLine = in.readLine()) != null) {
                content += (inputLine);
            }
            in.close();


        } catch (FileNotFoundException e) {
            content = "Aucun resultat";
        } catch (IOException e) {
            content = "Aucun resultat";
        } finally {
            con.disconnect();
        }
        return content;

    }

    private List<String> GETALL(String url) {
        String content = null;
        List<String> adresses = new ArrayList<>();
        ArrayList<String> listeRetour = new ArrayList<>();
        try {
            URL myurl = new URL(url);

            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new String();
            while ((inputLine = in.readLine()) != null) {
                content += (inputLine);
            }
            in.close();

            adresses = List.of(content.split("\",\""));

            String temp0 = adresses.get(0).substring(2);

            String tempLast = adresses.get(adresses.size() - 1).substring(0, adresses.get(adresses.size() - 1).length() - 2);

            for (int i = 0; i < adresses.size(); i++){
                if(i == 0){
                    listeRetour.add(temp0);
                }else{

                    if(i == adresses.size() - 1){
                        listeRetour.add(tempLast);
                    }else{
                        listeRetour.add(adresses.get(i));
                    }
                }

            }


        } catch (FileNotFoundException e) {
            content = "Aucun resultat";
        } catch (IOException e) {
            content = "Aucun resultat";
        } finally {
            con.disconnect();
        }
        return listeRetour;

    }

}

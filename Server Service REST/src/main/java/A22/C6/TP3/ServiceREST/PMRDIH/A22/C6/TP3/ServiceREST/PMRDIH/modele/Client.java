package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private int id;
    private String nom;
    private String adresse;
    private List<String> latLong;

    public Client(int id, String nom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.latLong = new ArrayList<>();
    }

    public Client() {
    }

    public List<String> getLatLong() {
        return latLong;
    }

    public void setLatLong(List<String> latLong) {
        this.latLong = latLong;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}

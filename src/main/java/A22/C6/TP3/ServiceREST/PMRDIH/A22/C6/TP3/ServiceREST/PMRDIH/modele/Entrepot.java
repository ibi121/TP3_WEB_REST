package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entrepot {

    private int id;
    private String nom;
    private String adresse;
    private List<String> LatLong;

    public Entrepot(int id, String nom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.LatLong = new ArrayList<>();
    }

    public Entrepot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<String> getLatLong() {
        return LatLong;
    }

    public void setLatLong(List<String> latLong) {
        LatLong = latLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrepot entrepot = (Entrepot) o;
        return id == entrepot.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entrepot{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", LatLong=" + LatLong +
                '}';
    }
}

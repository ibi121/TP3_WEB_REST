package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.REST;


import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.BD.BD;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.BD.DbManager;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.Client;
import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Gestion.GestionClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ServiceRestClient {

    private GestionClients gestionClients;

    private DbManager bd;

    public ServiceRestClient() {
        this.gestionClients = new GestionClients();
        this.bd = new DbManager();
    }

    @GetMapping(value = "/adresses")
    public List<String> getAdressesClient() {
        List<String> adresses = new ArrayList<>();
        adresses = gestionClients.getListeAdresseClient().stream().map(Client::getAdresse).collect(Collectors.toList());
        return adresses;
    }

    @GetMapping(value = "/client/all")
    public List<Client> getClients(){
        return gestionClients.getListeAdresseClient();
    }

    @PostMapping(value = "/client")
    public ResponseEntity<String> creerClient(@RequestBody Client client){
        gestionClients.AjouterClient(client);
        return new ResponseEntity<>("Client ajouté!", HttpStatus.CREATED);
    }

    @GetMapping(value = "/routeOptimal")
    public List<String> getRouteOptimale(){
        List<String> latLong = new ArrayList<>();
        latLong = bd.fetchRoute();
        return latLong;
    }



}

//package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.BD;
//
//import A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele.RouteOptimale;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ServiceRoute {
//
//    private RouteRepository routeRepository;
//
//    @Autowired
//    public void setRouteRepository(RouteRepository routeRepository) {
//        this.routeRepository = routeRepository;
//    }
//
//
//    @Transactional
//    public void ajouterRoute(RouteOptimale adresse){
//        this.routeRepository.save(adresse);
//    }
//
//    @Transactional
//    public List<RouteOptimale> getAllRouteOptimale(){
//        List<RouteOptimale> listeDeRoute = new ArrayList<>();
//        routeRepository.findAll().forEach(routeOptimale -> listeDeRoute.add(routeOptimale));
//        return listeDeRoute;
//    }
//
//
//
//
//}

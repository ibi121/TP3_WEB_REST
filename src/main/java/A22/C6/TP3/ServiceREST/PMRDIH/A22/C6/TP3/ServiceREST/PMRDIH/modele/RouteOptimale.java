package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Entity
public class RouteOptimale {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idRoute", nullable = false)
    private Long idRoute;


    private String routeOptimale;

    public RouteOptimale() {
    }

    public RouteOptimale(String routeOptimale) {
        this.routeOptimale = routeOptimale;
    }
    public String getRouteOptimale() {
        return routeOptimale;
    }

    public void setRouteOptimale(String routeOptimale) {
        this.routeOptimale = routeOptimale;
    }

    public void setIdRoute(Long idRoute) {
        this.idRoute = idRoute;
    }

    public Long getIdRoute() {
        return idRoute;
    }

    @Override
    public String toString() {
        return "RouteOptimale{" +
                "routeOptimale=" + routeOptimale +
                '}';
    }
}

package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.modele;

import java.util.ArrayList;
import java.util.List;

public class BD {


    public List<String> getMeilleureRoute() {
        List<String> meilleureRoute = new ArrayList<>();
        //TODO Changer pour lecture en bd
        meilleureRoute.add("12.5374");
        meilleureRoute.add("65.239816");
        return meilleureRoute;
    }
}

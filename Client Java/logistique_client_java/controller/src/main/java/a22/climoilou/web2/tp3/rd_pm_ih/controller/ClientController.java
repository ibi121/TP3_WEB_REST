package a22.climoilou.web2.tp3.rd_pm_ih.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("../vue/principale.fxml")
public class ClientController {

    private ConfigurableApplicationContext context;

    @FXML
    private Button btnRoute;

    @FXML
    private ListView listViewClients;

    @FXML
    private ListView listViewRoutes;

    ConsommationREST rest = new ConsommationREST();

    @FXML
    private void initialize() {
        listViewClients.getItems().addAll(rest.getAdressesClient());

        btnRoute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listViewRoutes.getItems().addAll(rest.getRouteOptimale());
            }
        });
    }


    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }


}

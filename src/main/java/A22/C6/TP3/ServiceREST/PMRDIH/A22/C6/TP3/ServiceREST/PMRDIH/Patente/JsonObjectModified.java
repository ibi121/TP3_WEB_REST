package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.Patente;


import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;


/**
 * Modifie la classe internet du JSON object
 * lui donne une linkedHashMap au lieu d'un hasmap
 * pour lui donner un ordre dans ses requetes
 * Recoit en param un JSONArray d'agents ainsi qu'un JSONArray de jobs pour y creer sa requete.
 */
@Slf4j
public class JsonObjectModified {

    public JSONObject CustomJsonObject(JSONArray agents, JSONArray jobs){
        JSONObject jsonObject = new JSONObject();
        try {
            Field changeMap = jsonObject.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jsonObject, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.info(e.getMessage());
        }

        jsonObject.put("mode", "drive");
        jsonObject.put("agents", agents);
        jsonObject.put("jobs", jobs);

        return jsonObject;
    }
}

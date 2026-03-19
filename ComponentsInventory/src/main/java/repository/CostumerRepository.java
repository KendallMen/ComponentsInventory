package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Costumer;
import util.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CostumerRepository {

    private final String FILE_PATH = "data/costumers.json";

    public List<Costumer> getAllCostumers(){

        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            JsonUtil.writeJSON(FILE_PATH, new ArrayList<>());
            return new ArrayList<>();
        }

        return JsonUtil.readList(
                FILE_PATH,
                new TypeReference<List<Costumer>>() {}
        );
    }

    public void saveCostumers(Costumer costumer){

        List<Costumer> costumers = getAllCostumers();
        costumers.add(costumer);
        JsonUtil.writeJSON(FILE_PATH, costumers);
    }

    public void eliminateCostumer(String id) {
        List<Costumer> costumers = getAllCostumers();
        costumers.removeIf(costumer -> costumer.getId().equals(id));
        saveCostumers((Costumer) costumers);
    }

    public void updateCostumer(Costumer updatedCostumer) {
        List<Costumer> costumers = getAllCostumers();
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getId().equals(updatedCostumer.getId())) {
                costumers.set(i, updatedCostumer);
                break;
            }
        }
        saveCostumers((Costumer) costumers);
    }
}

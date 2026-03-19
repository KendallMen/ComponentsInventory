package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Sale;
import util.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository {

    private final String FILE_PATH = "data/sales.json";

    public List<Sale> getAllSales(){

        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            JsonUtil.writeJSON(FILE_PATH, new ArrayList<>());
            return new ArrayList<>();
        }

        return JsonUtil.readList(
                FILE_PATH,
                new TypeReference<List<Sale>>() {}
        );
    }

    public void saveSale(Sale sale){
        List<Sale> sales = getAllSales();
        sales.add(sale);
        JsonUtil.writeJSON(FILE_PATH, sales);
    }

        public void eliminateSale(String id) {
            List<Sale> sales = getAllSales();
            sales.removeIf(sale -> sale.getId().equals(id));
            JsonUtil.writeJSON(FILE_PATH, sales);
        }

    public void updateSale(Sale updatedSale) {
        List<Sale> sales = getAllSales();
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getId().equals(updatedSale.getId())) {
                sales.set(i, updatedSale);
                break;
            }
        }
        JsonUtil.writeJSON(FILE_PATH, sales);
    }
}

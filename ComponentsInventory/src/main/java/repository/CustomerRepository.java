package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Customer;
import model.Product;
import util.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private final String FILE_PATH = "data/costumers.json";

    public List<Customer> getAllCustomers(){

        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            JsonUtil.writeJSON(FILE_PATH, new ArrayList<>());
            return new ArrayList<>();
        }

        return JsonUtil.readList(
                FILE_PATH,
                new TypeReference<List<Customer>>() {}
        );
    }

    public void saveCustomers(Customer customer){

        List<Customer> customers = getAllCustomers();
        customers.add(customer);
        JsonUtil.writeJSON(FILE_PATH, customers);
    }

    public void saveAllCustomers(List<Customer> costumers){
        JsonUtil.writeJSON(FILE_PATH, costumers);
    }

    public void eliminateCostumer(String id) {
        List<Customer> customers = getAllCustomers();
        customers.removeIf(customer -> customer.getId().equals(id));
        saveAllCustomers(customers);
    }

    public void updateCostumer(Customer updatedCustomer) {
        List<Customer> customers = getAllCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        saveAllCustomers(customers);
    }
}

package service;

import model.Costumer;
import repository.CostumerRepository;

import java.util.List;

public class CostumerService {

    private CostumerRepository repo = new CostumerRepository();

    public void addCostumer(Costumer newCostumer) {

        if (newCostumer.getName() == null || newCostumer.getName().isEmpty()) {
            throw new IllegalArgumentException("Empty name cannot be empty");
        }

        if (newCostumer.getPhoneNumber() == null || newCostumer.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        List<Costumer> clientes = repo.getAllCostumers();

        boolean existe = clientes.stream()
                .anyMatch(c -> c.getPhoneNumber().equals(newCostumer.getPhoneNumber()));

        if (existe) {
            throw new IllegalArgumentException("Costumer already exists");
        }

        repo.saveCostumers(newCostumer);
    }

    public List<Costumer> getAllCostumers() {
        return repo.getAllCostumers();
    }
}
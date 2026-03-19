package service;

import model.Customer;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private CustomerRepository repo = new CustomerRepository();

    public void addCustomer(Customer newCustomer) {

        if (newCustomer.getName() == null || newCustomer.getName().isEmpty()) {
            throw new IllegalArgumentException("Empty name cannot be empty");
        }

        if (newCustomer.getPhoneNumber() == null || newCustomer.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        List<Customer> clientes = repo.getAllCustomers();

        boolean existe = clientes.stream()
                .anyMatch(c -> c.getPhoneNumber().equals(newCustomer.getPhoneNumber()));

        if (existe) {
            throw new IllegalArgumentException("Costumer already exists");
        }

        repo.saveCustomers(newCustomer);
    }


    public void updateCustomer(Customer updatedCustomer) {
        if (updatedCustomer.getName() == null || updatedCustomer.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (updatedCustomer.getPhoneNumber() == null || updatedCustomer.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        List<Customer> clientes = repo.getAllCustomers();

        if (clientes.stream().noneMatch(c -> c.getId().equals(updatedCustomer.getId()))) {
            throw new IllegalArgumentException("Customer with id " + updatedCustomer.getId() + " does not exist");
        }

        // Validar que el teléfono no esté en uso por otro cliente
        boolean phoneExists = clientes.stream()
                .filter(c -> !c.getId().equals(updatedCustomer.getId()))
                .anyMatch(c -> c.getPhoneNumber().equals(updatedCustomer.getPhoneNumber()));

        if (phoneExists) {
            throw new IllegalArgumentException("Phone number already in use");
        }

        repo.updateCostumer(updatedCustomer);
    }

    public void deleteCustomer(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        if (repo.getAllCustomers().stream().noneMatch(c -> c.getId().equals(id))) {
            throw new IllegalArgumentException("Customer with id " + id + " does not exist");
        }

        repo.eliminateCostumer(id);
    }


    public List<Customer> getAllCustomers() {
        return repo.getAllCustomers();
    }
}
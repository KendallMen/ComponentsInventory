package model;

public class Customer {

    private String id;
    private String name;
    private String phoneNumber;

    public Customer() {}

    public Customer(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        String n = name != null ? name.trim() : "";
        String p = phoneNumber != null ? phoneNumber.trim() : "";

        if (!n.isEmpty() && !p.isEmpty()) {
            return n + " - " + p;
        }
        return !n.isEmpty() ? n : (p.isEmpty() ? id : p);
    }
}

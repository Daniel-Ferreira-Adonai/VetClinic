package com.example.vet.Backend.model;

public class VetService {
    private int id;
    private String name;
    private String description;
    private double price;
    private String url;
    private ServiceType serviceType;
    public VetService(int id, String name, String description, double price, String url, ServiceType serviceType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
        this.serviceType = serviceType;
    }

    public VetService(String name, String description, double price, String url, ServiceType serviceType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
        this.serviceType = serviceType;
    }
    public VetService(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }


}

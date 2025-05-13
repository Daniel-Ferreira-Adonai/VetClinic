package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.VetServiceDao;
import com.example.vet.Backend.model.ServiceType;
import com.example.vet.Backend.model.VetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    private final VetServiceDao vetServiceDao;

    public ServiceService() {
        this.vetServiceDao = new VetServiceDao();
    }

    public List<VetService> listServices() {
        return vetServiceDao.listAll();
    }

    public void insert(String name, String description, Double price, String url, String typeStr) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("service name is obligatory");
        }

        ServiceType type;
        try {
            type = ServiceType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid type of serivce: " + typeStr);
        }

        VetService service = new VetService();
        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);
        service.setUrl(url);
        service.setServiceType(type);

        vetServiceDao.insert(service);
    }

    public void update(int id, String name, String description, Double price, String url, String typeStr) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id.");
        }

        ServiceType type;
        try {
            type = ServiceType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid type of service: " + typeStr);
        }

        VetService service = new VetService();
        service.setId(id);
        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);
        service.setUrl(url);
        service.setServiceType(type);

        vetServiceDao.update(service);
    }

    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id.");
        }
        vetServiceDao.delete(id);
    }
}

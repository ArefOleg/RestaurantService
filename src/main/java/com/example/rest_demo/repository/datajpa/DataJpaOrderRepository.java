package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Order;
import com.example.rest_demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaOrderRepository implements OrderRepository {
    private final CrudOrderRepository crudOrderRepository;
    @Autowired
    public DataJpaOrderRepository(CrudOrderRepository crudOrderRepository){
        this.crudOrderRepository = crudOrderRepository;
    }

    @Override
    public Order save(Order order) {
        return crudOrderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return crudOrderRepository.findAll();
    }

    public List<Order> getAllUserVotes(int userId){return crudOrderRepository.getAllUserVotes(userId);}

    @Override
    public boolean delete(int id) {
        return crudOrderRepository.delete(id) != 0;
    }

    @Override
    public Order get(int id) {
        return crudOrderRepository.findById(id).orElse(null);
    }

    public Order getExistRec(int userId, int restrId){
        return crudOrderRepository.getExistRec(userId, restrId);
    }

    public Order getUserRec(int userId){
        return crudOrderRepository.getUserRec(userId);
    }

    public boolean disablingOrder(int orderId){return crudOrderRepository.disablingOrder(orderId) != 0;}
}

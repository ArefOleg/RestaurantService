package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Menu;
import com.example.rest_demo.repository.MenuRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public class DataJpaMenuRepository implements MenuRepository {
    private final CrudMenuRepository crudMenuRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository){
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    @Transactional
    public Menu save(Menu menu) {
        return crudMenuRepository.save(menu);
    }

    @Override
    public List<Menu> getAll() {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Menu get(int id) {
        return crudMenuRepository.getById(id);
    }


    public Menu getRestaurantRec(int restrId) {return crudMenuRepository.getRestaurantRec(restrId);}

    public boolean disablingOrder(int menuId){return crudMenuRepository.disablingMenu(menuId) != 0;}
}

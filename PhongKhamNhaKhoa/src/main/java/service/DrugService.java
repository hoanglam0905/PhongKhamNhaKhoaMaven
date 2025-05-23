package service;

import model.Drug;
import reponsitory.DrugRepository;

public class DrugService implements IDrugService {
    private DrugRepository repo = new DrugRepository();

    public Drug getDrugDetail(int id) {
        return repo.getDrugById(id);
    }
    
    public boolean updateDrug(int id, String name, String description, double price, int stock) {
        return repo.updateDrug(id, name, description, price, stock);
    }
    
    public boolean insertDrug(String name, String description, double price, int stock) {
        return repo.insertDrug(name, description, price, stock);
    }
}
package SE2.admin.model;

import java.util.List;

public class ClientForm {
    private List<EntityProduct> entityProducts;

    public ClientForm(List<EntityProduct> entityProducts) {
        this.entityProducts = entityProducts;
    }

    public List<EntityProduct> getEntityProducts() {
        return entityProducts;
    }

    public void setEntityProducts(List<EntityProduct> entityProducts) {
        this.entityProducts = entityProducts;
    }
}
package SE2.admin.model;

import javax.persistence.*;

@Entity
@Table(name = "product_has_catogory")
public class ProductHasCate {
//    @EmbeddedId
//    private ProductCategoryKey key;
    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("catogory_id")
    @JoinColumn(name = "catogory_id")
    private Category category;
    @Id
    private Long id;

//    public ProductCategoryKey getKey() {
//        return key;
//    }
//
//    public void setKey(ProductCategoryKey key) {
//        this.key = key;
//    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

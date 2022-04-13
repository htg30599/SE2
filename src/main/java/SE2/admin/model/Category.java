package SE2.admin.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "categories")
    private Set<Product> products;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Set<Carousel> Carousel;

    public Set<SE2.admin.model.Carousel> getCarousel() {
        return Carousel;
    }

    public void setCarousel(Set<SE2.admin.model.Carousel> carousel) {
        Carousel = carousel;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


}
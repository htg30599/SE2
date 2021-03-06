package SE2.admin.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

//Dùng chung cả kể khac man hinh (admin,user,....)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty(message = "Product name cannot be empty!")
    private String name;
    private String shortDesc;
    @NotEmpty(message = "Product image cannot be empty!")
    private String images;
    private int price;
//    private int promotionId;
//    @NotEmpty(message = "Product category cannot be empty!")
//    private int categoryId;
    private String manufacturer;
    private int quantity;

//    @ManyToMany(mappedBy = "productList")
//    private List<Cart> cartList;
//
    @ManyToOne
    private Category category;

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

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
//
//    public int getPromotionId() {
//        return promotionId;
//    }
//
//    public void setPromotionId(int promotionId) {
//        this.promotionId = promotionId;
//    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}

package SE2.model;

import javax.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Long id;
    private int cart_id;
    private String customer_name;
    private String phone_number;
    private String place_of_receipt;
    private String note;
    private String payment_method;
    private int total_price;
    private int delivery_status_id;
    private int ship_price;
    private String create_date;
    private int creator_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPlace_of_receipt() {
        return place_of_receipt;
    }

    public void setPlace_of_receipt(String place_of_receipt) {
        this.place_of_receipt = place_of_receipt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getDelivery_status_id() {
        return delivery_status_id;
    }

    public void setDelivery_status_id(int delivery_status_id) {
        this.delivery_status_id = delivery_status_id;
    }

    public int getShip_price() {
        return ship_price;
    }

    public void setShip_price(int ship_price) {
        this.ship_price = ship_price;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }
}


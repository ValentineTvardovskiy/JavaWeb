package company.model;

import company.metadata.ColumnName;
import company.metadata.TableName;

import java.util.Objects;

@TableName("products")
public class Product extends AbstractModel {

    private Long id;

    @ColumnName("NAME")
    private String name;

    @ColumnName("PRICE")
    private double price;

    @ColumnName("DESCRIPTION")
    private String description;

    @ColumnName("FK_CATEGORIES")
    private Category category;


    public Product(String name, double price, String description) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product(Long id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}

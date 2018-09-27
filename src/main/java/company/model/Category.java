package company.model;

import java.util.List;

public class Category {

    private Long ID;
    private String name;
    private List<Product> products;

    public Category() {
    }

    public Category(Long ID, String name, List<Product> products) {
        this.ID = ID;
        this.name = name;
        this.products = products;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}

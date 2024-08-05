package org.example.model;

import java.util.Objects;

public class Car {

    private String id;
    private String brand;
    private String model;
    private String yearOfIssue;
    private String price;
    private String state;
    private String inStock;

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", brand:'" + brand + '\'' +
                ", model:'" + model + '\'' +
                ", yearOfIssue:'" + yearOfIssue + '\'' +
                ", price:'" + price + '\'' +
                ", state:'" + state + '\'' +
                ", inStock:'" + inStock + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(yearOfIssue, car.yearOfIssue) && Objects.equals(price, car.price) && Objects.equals(state, car.state) && Objects.equals(inStock, car.inStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, yearOfIssue, price, state, inStock);
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYearOfIssue() {
        return yearOfIssue;
    }

    public String getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYearOfIssue(String yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }
}

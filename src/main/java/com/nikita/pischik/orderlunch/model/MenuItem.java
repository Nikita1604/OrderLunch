package com.nikita.pischik.orderlunch.model;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category")
    private String category;

    @Column(name = "category_id")
    private String category_id;

    @Column(name = "title")
    private String title;

    @Column(name = "weight")
    private String weight;

    @Column(name = "cost")
    private String cost;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "in_category_id")
    private int in_category_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIn_category_id() {
        return in_category_id;
    }

    public void setIn_category_id(int in_category_id) {
        this.in_category_id = in_category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}

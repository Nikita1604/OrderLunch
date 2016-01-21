package com.nikita.pischik.orderlunch.utils;


import org.hibernate.mapping.AbstractAuxiliaryDatabaseObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class MenuDownloader {

    public static String downloadMenuFile(String urlString) throws Exception{
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static class ItemMenu {
        private CategoryItem Category;
        private int DishId;
        private int CategoryId;
        private String Name;
        private String Description;
        private String Weight;
        private int Price;
        private String DishImage;

        public String getDishImage() {
            return DishImage;
        }

        public void setDishImage(String dishImage) {
            DishImage = dishImage;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int price) {
            Price = price;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String weight) {
            Weight = weight;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getCategoryId() {
            return CategoryId;
        }

        public void setCategoryId(int categoryId) {
            CategoryId = categoryId;
        }

        public int getDishId() {
            return DishId;
        }

        public void setDishId(int dishId) {
            DishId = dishId;
        }

        public CategoryItem getCategory() {
            return Category;
        }

        public void setCategory(CategoryItem category) {
            Category = category;
        }
    }

    public static class CategoryItem {
        private int CategoryId;
        private String Name;
        private int SortOrder;
        private String CategoryImage;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }

    public static class MenuObject {
        private boolean Success;
        private List<ItemMenu> Dishes = new ArrayList<ItemMenu>();

        public List<ItemMenu> getDishes() {
            return Dishes;
        }

        public void setDishes(List<ItemMenu> dishes) {
            Dishes = dishes;
        }
    }

    public static MenuObject fromJSONParser(String url) throws Exception {
        Gson gson = new Gson();
        String json = downloadMenuFile(url);
        MenuObject menu = gson.fromJson(json, MenuObject.class);
        return menu;
    }

}

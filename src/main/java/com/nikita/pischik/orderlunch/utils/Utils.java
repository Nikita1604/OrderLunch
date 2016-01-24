package com.nikita.pischik.orderlunch.utils;

import com.nikita.pischik.orderlunch.model.MenuItem;
import com.nikita.pischik.orderlunch.notification.SimpleNotificationManager;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static class orderFromBasket {
        private List<Integer> dishes;
        private List<Integer> counts;

        public List<Integer> getDishes() {
            return dishes;
        }

        public void setDishes(List<Integer> dishes) {
            this.dishes = dishes;
        }

        public List<Integer> getCounts() {
            return counts;
        }

        public void setCounts(List<Integer> counts) {
            this.counts = counts;
        }
    }



    public static boolean updateMenu(List<MenuItem> oldMenu, List<MenuItem> newMenu) {
        if (oldMenu.size() != newMenu.size()) {
            return true;
        } else {
            for (int i=0; i<oldMenu.size(); i++) {
                MenuItem oldMenuItem = oldMenu.get(i);
                boolean hasSame = false;
                for (int j=0; j<newMenu.size(); j++) {
                    MenuItem newMenuItem = newMenu.get(j);
                    if (oldMenuItem.getIn_category_id() == newMenuItem.getIn_category_id() &&
                            oldMenuItem.getCategory_id().equals(newMenuItem.getCategory_id()) &&
                            oldMenuItem.getCategory().equals(newMenuItem.getCategory()) &&
                            oldMenuItem.getCost().equals(newMenuItem.getCost()) &&
                            oldMenuItem.getImage().equals(newMenuItem.getImage()) &&
                            oldMenuItem.getTitle().equals(newMenuItem.getTitle()) &&
                            oldMenuItem.getWeight().equals(newMenuItem.getWeight()) &&
                            oldMenuItem.getDish_id() == newMenuItem.getDish_id()) {
                        hasSame = true;
                        break;
                    }
                }
                if (!hasSame) {
                    return true;
                }
            }
        }
        return false;
    }


    public static List<MenuViewModel> formingMenuListView(List<MenuItem> menu) {

        List<MenuViewModel> ans = new ArrayList<MenuViewModel>();
        boolean cat_in_menu = false;
        int cat_in_menu_id = -1;
        for (int i=0; i<menu.size(); i++) {
            cat_in_menu = false;
            cat_in_menu_id = -1;
            for (int j=0; j<ans.size(); j++) {
                if (ans.get(j).getId() == Integer.parseInt(menu.get(i).getCategory_id())) {
                    cat_in_menu = true;
                    cat_in_menu_id = j;
                    break;
                }
            }
            if (cat_in_menu) {
                ans.get(cat_in_menu_id).getMenu().add(menu.get(i));
            } else {
                MenuViewModel newItem = new MenuViewModel();
                newItem.setId(Integer.parseInt(menu.get(i).getCategory_id()));
                newItem.setName(menu.get(i).getCategory());
                List<MenuItem> newList = new ArrayList<MenuItem>();
                newList.add(menu.get(i));
                newItem.setMenu(newList);
                ans.add(newItem);
            }
        }

        for (int q=0; q<ans.size(); q++) {
            List<MenuItem> menuList = ans.get(q).getMenu();
            for (int i=0; i<menuList.size()-1; i++) {
                for (int j=i+1; j<menuList.size(); j++) {
                    if (menuList.get(i).getIn_category_id() > menuList.get(j).getIn_category_id()) {
                        MenuItem menuItem = menuList.get(i);
                        menuList.set(i, menuList.get(j));
                        menuList.set(j, menuItem);
                    }
                }
            }
        }
        return ans;
    }
}

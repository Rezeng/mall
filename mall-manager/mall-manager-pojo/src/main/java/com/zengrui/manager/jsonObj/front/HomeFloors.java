package com.zengrui.manager.jsonObj.front;


import java.io.Serializable;
import java.util.List;
import com.zengrui.manager.model.Image;

public class HomeFloors implements Serializable {

    private List<?> tabs;

    private Image image;

    private String title;

    public List<?> getTabs() {
        return tabs;
    }

    public void setTabs(List<?> tabs) {
        this.tabs = tabs;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

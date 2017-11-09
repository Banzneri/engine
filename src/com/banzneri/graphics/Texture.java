package com.banzneri.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Texture {
    private Image image;

    public Texture(Image image) {
        setImage(image);
    }

    public Texture(String url) {
        setImage(new Image(url));
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

package springboot2app.common.util;

import springboot2app.common.annotation.Menu;

public class MenuItem {
    private String path;
    private String name;

    static MenuItem of(Menu menu) {
        MenuItem i = new MenuItem();
        i.setName(menu.name());
        i.setPath(menu.method() + "." + menu.uri());
        return i;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
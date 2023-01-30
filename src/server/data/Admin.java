package server.data;

import server.components.Tab;
import server.components.tabs.Guests;
import server.components.tabs.Reservations;
import server.components.tabs.Stuff;
import server.components.tabs.Rooms;

public enum Admin {
    RECEPTIONIST(new Tab[] { new Guests(), new Reservations() }), MANAGER(
            new Tab[] { new Stuff(), new Rooms() }),
    SERVICER(new Tab[] { new Guests() });

    private Tab[] tabs;

    private Admin() {
        tabs = new Tab[0];
    }

    private Admin(Tab... tabs) {
        this.tabs = tabs;
    }

    public Tab[] getTabs() {
        return tabs;
    }

    public void setTabs(Tab[] tabs) {
        this.tabs = tabs;
    }

    public int size() {
        return tabs.length;
    }

    public void resetAdminInfo() {
        for (Tab tab : tabs)
            tab.getHeader().resetAdmin();
    }
}
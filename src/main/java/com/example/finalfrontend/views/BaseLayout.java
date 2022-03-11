package com.example.finalfrontend.views;

import com.example.finalfrontend.views.dishes.DishesGrid;
import com.example.finalfrontend.views.prepareOrder.PrepareOrder;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;


public class BaseLayout extends AppLayout {

    Tabs tabs = new Tabs();
    List<Tab> tabList = new ArrayList<>();

    public BaseLayout() {
        Tabs tabs = setTabs(getTabs());
        addToDrawer(tabs);
        setPrimarySection(Section.DRAWER);
    }

    public List<Tab> getTabs() {
        Tab firstTab = createTab(VaadinIcon.DASHBOARD, "Dishes", DishesGrid.class);
        Tab orderTab = createTab(VaadinIcon.CART, "Prepare order", PrepareOrder.class);
        tabList.add(orderTab);
        tabList.add(firstTab);
        return tabList;
    }

    private Tabs setTabs(List<Tab> tabList) {
        for (Tab tab : tabList) {
            tabs.add(tab);
        }
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class destinationClass) {
        Icon icon = prepareIcon(viewIcon);
        RouterLink orderLink = new RouterLink();
        orderLink.setRoute(destinationClass);
        orderLink.add(icon, new Span(viewName));
        return new Tab(orderLink);
    }

    private Icon prepareIcon(VaadinIcon viewIcon) {
        Icon icon = viewIcon.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");
        return icon;
    }
}

package com.example.finalfrontend.views;

import com.example.finalfrontend.service.DishService;
import com.example.finalfrontend.service.MenuService;
import com.example.finalfrontend.views.dishes.DishesGrid;
import com.example.finalfrontend.views.prepareOrder.PrepareOrder;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
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

    public BaseLayout(MenuService menuService, DishService dishService) {

        Tabs tabs = setTabs(getTabs());
        Button button = new Button("Prepare Menu");

        if (dishService.getDishes().length > 10) {
            button.setVisible(false);
        }
        addToDrawer(button);
        addToDrawer(tabs);
        button.addClickListener(event -> {
            System.out.println("ram");
            menuService.prepareMenu();
            button.setVisible(false);
            //powiadomienie żeby odświeżyć stronę
        });
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


    private Tab createTab(VaadinIcon viewIcon, String viewName, Class destinationClass ) {
        Icon icon = prepareIcon(viewIcon);

        RouterLink orderLink = new RouterLink();
        orderLink.setRoute(destinationClass);
        orderLink.add(icon, new Span(viewName));

        return new Tab(orderLink);
    }


    private Tab createDishesTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = prepareIcon(viewIcon);

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));

        link.setTabIndex(-1);

        return new Tab(link);
    }

    private Icon prepareIcon(VaadinIcon viewIcon) {
        Icon icon = viewIcon.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");
        return icon;
    }

    public Tab setChosenTab(int pos) {
        int truePos = pos - 1;
        Tab tab = tabList.get(truePos);
        tabs.setSelectedTab(tab);
        return tab;
    }

    private H1 prepareTitle() {
        H1 title = new H1("My App");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        return title;
    }
}

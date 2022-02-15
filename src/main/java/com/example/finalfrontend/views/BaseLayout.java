package com.example.finalfrontend.views;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.HighlightConditions;
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


        Tab firstTab = createFirstTab(VaadinIcon.DASHBOARD, "FirstLayout");
        Tab orderTab = createOrderTab(VaadinIcon.CART, "Orders");

        tabList.add(firstTab);
        tabList.add(orderTab);
        
        return tabList;
    }

    private Tabs setTabs(List<Tab> tabList) {
        for (Tab tab : tabList) {
            tabs.add(tab);
        }
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createOrderTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = viewIcon.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink orderLink = new RouterLink();
        orderLink.setRoute(PrepareOrder.class);
        orderLink.setHighlightCondition(HighlightConditions.sameLocation());
        orderLink.add(icon, new Span(viewName));

        return new Tab(orderLink);
    }

    private Tab createFirstTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = viewIcon.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));

        link.setTabIndex(-1);

        return new Tab(link);
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

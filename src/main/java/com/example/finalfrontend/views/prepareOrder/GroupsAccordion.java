package com.example.finalfrontend.views.prepareOrder;


import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.DishDto;
import com.example.finalfrontend.dto.GroupDto;
import com.example.finalfrontend.service.CartService;
import com.example.finalfrontend.service.DishService;
import com.example.finalfrontend.service.GroupService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import java.util.Arrays;
import java.util.List;


public class GroupsAccordion extends VerticalLayout {
    List<GroupDto> groups;
    List<DishDto> dishes;
    private final CartSummary cartSummary;
    private final CartDto cartDto;
    private CartService cartService;

    public GroupsAccordion(CartDto cartDto, GroupService groupService, DishService dishService, CartService cartService, CartSummary cartSummary) {
        this.cartService = cartService;
        this.cartDto = cartDto;
        this.cartSummary = cartSummary;
        groups = Arrays.asList(groupService.getGroups());
        dishes = Arrays.asList(dishService.getDishes());
        createAccordions();
    }

    private void createAccordions() {
        for(GroupDto groupDto : groups) {
            Accordion accordion = new Accordion();
            String title = groupDto.getGroupName();
            accordion.add(title,setGrid(groupDto));
            add(accordion);
        }
    }

    private Grid<DishDto> setGrid(GroupDto group) {
        Grid<DishDto> grid = new Grid<>(DishDto.class, false);
        grid.setItems(dishes.stream().filter(e -> e.getGroupId().equals(group.getId())));
        grid.setWidth(550, Unit.PIXELS);
        grid.addColumn(DishDto::getName).setHeader("Danie").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(DishDto::getPrice).setHeader("Cena").setTextAlign(ColumnTextAlign.CENTER);

        grid.addComponentColumn(dishDto -> {
            IntegerField integerField = new IntegerField();
            Button button = new Button();
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            setGridComponents(integerField, button, dishDto);
            horizontalLayout.add(integerField,button);
            return horizontalLayout;
        });

        return grid;
    }

    private void setGridComponents(IntegerField integerField, Button button, DishDto dishDto) {
        integerField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        integerField.setValue(0);
        integerField.setWidth(80,Unit.PIXELS);
        integerField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        integerField.setHasControls(true);
        integerField.addFocusListener(event -> button.setVisible(true));

        button.setIcon(new Icon(VaadinIcon.CHECK));
        button.addThemeVariants(ButtonVariant.LUMO_SMALL);
        button.setVisible(false);
        button.addClickListener(event -> {
            int quantity = integerField.getValue();
            cartService.addDish(dishDto.getName(),cartDto.getId(),quantity);
            cartSummary.updateGrid();
            cartSummary.updateTotalCost();
            integerField.setValue(0);
            button.setVisible(false);
        });
    }
}

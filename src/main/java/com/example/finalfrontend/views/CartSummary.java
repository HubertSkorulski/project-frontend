package com.example.finalfrontend.views;

import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.CartRowDto;
import com.example.finalfrontend.service.CartService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;


public class CartSummary extends VerticalLayout {

    Grid<CartRowDto> grid = new Grid<>(CartRowDto.class,false);
    TextField textField = new TextField();
    Button move = new Button("Dalej");
    private final CartService cartService;
    private final CartDto cartDto;

    public CartSummary(CartService cartService, CartDto cartDto) {
        this.cartDto = cartDto;
        this.cartService = cartService;
        setGrid();
        add(grid);
        add(setHorizontalLayout());
    }

    private HorizontalLayout setHorizontalLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        prepareTextField();
        horizontalLayout.add(textField,move);
        return horizontalLayout;
    }

    private void prepareTextField() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth(250, Unit.PIXELS);
        textField.setValue("Całkowity koszt: 0 PLN");
    }

    private void setGrid() {
        grid.addColumn(CartRowDto::getDishName).setHeader("Danie").setWidth("10em");
        grid.addColumn(CartRowDto::getQuantity).setHeader("Ilość");
        grid.addColumn(CartRowDto::getUnitPrice).setHeader("Cena jednostkowa");
        grid.addColumn(CartRowDto::getPrice).setHeader("Cena");


        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, cartRowDto) ->
                {
                    setGridButton(button,cartRowDto);
                })
        ).setHeader("Usuń").setWidth("3em");
        grid.setWidth(  700,Unit.PIXELS);

    }

    private void setGridButton(Button button, CartRowDto cartRowDto) {
        button.setIcon(new Icon(VaadinIcon.TRASH));
        button.addThemeVariants(
                ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e ->
            {
                removeDish(cartRowDto);
                updateGrid();
                updateTotalCost();
            }
        );
    }

    public void prepareMoveButton(CreateCustomerForm form) {
        move.addClickListener(event -> form.setVisible(true));
    }

    public void updateGrid() {
        grid.setItems(cartService.getCartRows(cartDto.getId()));
    }

    public void updateTotalCost() {
        double totalCost = cartService.getTotalCost(cartDto.getId());
        textField.setValue("Całkowity koszt: " + totalCost + " PLN");
    }

    private void removeDish(CartRowDto cartRowDto) {
        cartService.removeDish(cartRowDto.getDishName(),cartDto.getId());
    }

}

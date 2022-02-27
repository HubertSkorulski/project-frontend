package com.example.finalfrontend.views.prepareOrder;

import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.CartRowDto;
import com.example.finalfrontend.service.CartService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;


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

        grid.addComponentColumn(cartRowDto -> {
            IntegerField integerField = new IntegerField();
            setGridIntegerField(integerField,cartRowDto,cartService);
            return integerField;
        }).setHeader("Ilość").setWidth("7.2em").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);;

        grid.addColumn(CartRowDto::getUnitPrice).setHeader("Cena 1 szt.").setWidth("6.5em").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);

        grid.addColumn(CartRowDto::getPrice).setHeader("Cena").setWidth("6.5em").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);;

        grid.addComponentColumn(cartRowDto -> {
            Button button = new Button();
            setGridButton(button,cartRowDto);
            return button;
        }).setHeader("Usuń").setWidth("4.7em").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);

        grid.setWidth(  600,Unit.PIXELS);
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

    private void setGridIntegerField(IntegerField integerField, CartRowDto cartRowDto, CartService cartService) {
        integerField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        integerField.setValue(cartRowDto.getQuantity());
        integerField.setWidth(80,Unit.PIXELS);
        integerField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        integerField.setHasControls(true);
        integerField.addBlurListener(event -> {
            if (cartRowDto.getQuantity() != integerField.getValue()) {
                cartService.updateDishServings(cartRowDto.getDishName(),cartDto.getId(),integerField.getValue());
                updateGrid();
                updateTotalCost();
            }
        });
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

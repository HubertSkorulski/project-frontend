package com.example.finalfrontend.views;

import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.DishDto;
import com.example.finalfrontend.service.CartService;
import com.example.finalfrontend.service.DishService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListOfDishesWithAddButtons extends VerticalLayout {

    List<HorizontalLayout> horizontalLayouts = new ArrayList<>();
    List<Label> labels = new ArrayList<>();
    List<IntegerField> integerFields = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    List<DishDto> dishes;
    int rowNumber;
    private final CartDto cartDto;
    private final CartSummary cartSummary;
    private final CartService cartService;


    public ListOfDishesWithAddButtons(CartDto cartDto, CartSummary cartSummary, DishService dishService, CartService cartService) {
        this.cartSummary = cartSummary;
        this.cartDto = cartDto;
        this.cartService = cartService;

        dishes = Arrays.asList(dishService.getDishes());
        rowNumber = dishes.size();
        add(prepareLayout());

    }

    public VerticalLayout prepareLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        createComponents();

        setLabels();
        setIntegerFields();
        addClickListenersToAddButtons();
        setHorizontalLayouts();
        for (HorizontalLayout horizontalLayout1 : horizontalLayouts){
            verticalLayout.add(horizontalLayout1);
        }
        return verticalLayout;
    }

    private void addClickListenersToAddButtons() {
        for (int i = 0; i < rowNumber; i++) {
            Button button = buttons.get(i);
            button.addClickListener(event -> {
                getAddedDishParameters(button);
                cartSummary.updateGrid();
                cartSummary.updateTotalCost();
            }
            );
        }
    }

    private void getAddedDishParameters(Button button) {
        int index = buttons.indexOf(button);
        IntegerField filledField = integerFields.get(index);
        int quantity = filledField.getValue();

        String dishName = labels.get(index).getText();
        Long cartId = cartDto.getId();
        cartService.addDish(dishName,cartId,quantity);
        filledField.setValue(0);
    }

    private void setHorizontalLayouts() {
        for (int i = 0; i < rowNumber; i++) {
            horizontalLayouts.get(i).add(
                    labels.get(i),
                    integerFields.get(i),
                    buttons.get(i)
            );
        }
    }

    private void setIntegerFields() {
        for (int i = 0; i < rowNumber; i++) {
            IntegerField integerField = integerFields.get(i);
            integerField.setValue(0);
            integerField.setHasControls(true);
            integerField.setMin(0);
            integerField.setMax(20);
            integerField.setWidth(100, Unit.PIXELS);
        }
    }

    private void setLabels() {
        for (int i = 0; i < rowNumber; i++) {
            Label label = labels.get(i);
            label.setText(dishes.get(i).getName());
            label.setWidth(300, Unit.PIXELS);
        }
    }

    private void createComponents() {
        for (int i = 0; i < rowNumber; i++) {
            buttons.add(new Button("Dodaj"));
            integerFields.add(new IntegerField());
            labels.add(new Label());
            horizontalLayouts.add(new HorizontalLayout());
        }
    }

}

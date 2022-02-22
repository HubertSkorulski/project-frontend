package com.example.finalfrontend.views;

import com.example.finalfrontend.service.DishService;
import com.example.finalfrontend.service.MenuService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Welcome page")
@Route(value = "")
public class StartingView extends VerticalLayout {

    TextField textField = new TextField();
    Button button = new Button("Składanie zamówienia");

    public StartingView(MenuService menuService, DishService dishService) {
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        prepareTextField();
        button.addClickListener(event -> {
            if (dishService.getDishes().length < 10) {
                menuService.prepareMenu();
            };
            UI.getCurrent().navigate("createOrder");
        });
        add(textField,button);
    }

    private void prepareTextField() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth(600, Unit.PIXELS);
        textField.setValue("Witamy w aplikacji naszej restuaracji. Zapraszamy do złożenia zamówienia.");
    }

}

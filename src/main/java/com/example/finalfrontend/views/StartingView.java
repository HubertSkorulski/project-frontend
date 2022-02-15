package com.example.finalfrontend.views;

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
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Welcome page")
@Route(value = "", layout = BaseLayout.class)
public class StartingView extends VerticalLayout {

    TextField textField = new TextField();
    Button button = new Button("Składanie zamówienia");

    @Autowired
    private final MenuService menuService;


    public StartingView(MenuService menuService) {
        this.menuService = menuService;
        this.setAlignItems(FlexComponent.Alignment.CENTER);

        prepareTextField();
        menuService.prepareMenu();
        button.addClickListener(event -> UI.getCurrent().navigate("createOrder"));
        add(textField,button);
    }

    private void prepareTextField() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth(600, Unit.PIXELS);
        textField.setValue("Witamy w aplikacji naszej restuaracji. Zapraszamy do złożenia zamówienia.");
    }

}

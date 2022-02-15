package com.example.finalfrontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Confirmation")
@Route(value = "confirmation")
public class ConfirmationPage extends VerticalLayout {

    TextField textField = new TextField();
    Button button = new Button("Złóż nowe zamówienie");


    public ConfirmationPage() {
        setAlignItems(Alignment.CENTER);
        addClickListener();
        prepareTextField();
        add(textField,button);
    }

    private void prepareTextField() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth(600, Unit.PIXELS);
        textField.setValue("Dziękujęmy za złożenie zamówienia." +
                " Potwierdzenie złożenia zamówienia zostało wysłane na podany adres email.");
    }

    private void addClickListener() {
        button.addClickListener(event -> UI.getCurrent().navigate("createOrder"));
    }
}

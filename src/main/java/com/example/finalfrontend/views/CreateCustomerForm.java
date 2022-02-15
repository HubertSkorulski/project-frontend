package com.example.finalfrontend.views;

import com.example.finalfrontend.dto.CustomerDto;
import com.example.finalfrontend.service.CustomerService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerForm extends VerticalLayout {

    TextField name = new TextField("Imię");
    TextField surname = new TextField("Nazwisko");
    EmailField emailAddress = new EmailField("Email");

    private final CustomerService customerService;


    public CreateCustomerForm(CustomerService customerService) {
        this.customerService = customerService;
        setTextFieldsSize();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(
                name,
                surname,
                emailAddress
        );
        add(horizontalLayout);
    }

    public CustomerDto createCustomer() {
        String customerName = name.getValue();
        String customerSurname = surname.getValue();
        String customerEmail =  emailAddress.getValue();
        return customerService.createCustomer(customerName,customerSurname,customerEmail);
    }

    private void setTextFieldsSize() {
        name.setWidth(100,Unit.PIXELS);
        surname.setWidth(150,Unit.PIXELS);
        emailAddress.setWidth(250,Unit.PIXELS);
    }

    public boolean isFilled() {
        if (!name.isEmpty() & !surname.isEmpty() & !emailAddress.isEmpty()) {
            return true;
        } else  {
            return false;
        }
    }
}

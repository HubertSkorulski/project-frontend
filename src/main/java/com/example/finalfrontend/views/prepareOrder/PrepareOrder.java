package com.example.finalfrontend.views.prepareOrder;

import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.CustomerDto;
import com.example.finalfrontend.dto.OrderDto;
import com.example.finalfrontend.service.*;
import com.example.finalfrontend.views.BaseLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Create Order")
@Route(value = "createOrder", layout = BaseLayout.class)
public class PrepareOrder extends HorizontalLayout {

    CartDto cartDto;
    CreateCustomerForm createCustomerForm;
    GroupsAccordion groupsAccordion;
    CartSummary cartSummary;
    Button confirmationButton = new Button("Złóż zamówienie");
    CustomerDto customerDto;
    OrderDto orderDto;

    @Autowired
    OrderCreatedNotification orderCreatedNotification;

    private final OrderService orderService;
    private final ConfirmationService confirmationService;
    private final NotFilledFormNotification notFilledFormNotification;


    public PrepareOrder(CartService cartService, OrderService orderService, CustomerService customerService, DishService dishService, ConfirmationService confirmationService, NotFilledFormNotification notFilledFormNotification, GroupService groupService) {
        this.orderService = orderService;
        this.confirmationService = confirmationService;
        this.notFilledFormNotification = notFilledFormNotification;

        cartDto = cartService.createCart();
        createCustomerForm = new CreateCustomerForm(customerService);
        cartSummary = new CartSummary(cartService, cartDto);
        cartSummary.prepareMoveButton(createCustomerForm);

        groupsAccordion = new GroupsAccordion(cartDto, groupService, dishService, cartService, cartSummary);
        setButton();
        VerticalLayout gridWithButton = new VerticalLayout();
        gridWithButton.add(cartSummary, createCustomerForm, confirmationButton);

        add(groupsAccordion);
        add(gridWithButton);
    }

    private void setButton() {
        confirmationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmationButton.addClickShortcut(Key.ENTER);

        confirmationButton.addClickListener(event -> {
            if (createCustomerForm.isFilled()) {
                customerDto = createCustomerForm.createCustomer();
                orderDto = orderService.createOrder(cartDto.getId(),customerDto.getId());
                String sentConfirmation = confirmationService.sentConfirmation(orderDto.getId());
                System.out.println(sentConfirmation);
                orderCreatedNotification.show(orderDto);
            } else {
                notFilledFormNotification.show();
            }
        });
    }

}

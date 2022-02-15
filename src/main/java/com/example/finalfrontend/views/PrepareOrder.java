package com.example.finalfrontend.views;

import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.CustomerDto;
import com.example.finalfrontend.dto.OrderDto;
import com.example.finalfrontend.service.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



@PageTitle("Create Order")
@Route(value = "createOrder", layout = BaseLayout.class)
public class PrepareOrder extends HorizontalLayout {

    CartDto cartDto;
    CreateCustomerForm createCustomerForm;
    ListOfDishesWithAddButtons listWithButtons;
    CartSummary cartSummary;
    Button confirmationButton = new Button("Złóż zamówienie");
    CustomerDto customerDto;
    OrderDto orderDto;
    OrderCreatedNotification orderCreatedNotification;


    private final CartService cartService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final DishService dishService;
    private final ConfirmationService confirmationService;
    private final NotFilledFormNotification notFilledFormNotification;


    public PrepareOrder(CartService cartService, OrderService orderService, CustomerService customerService, DishService dishService, ConfirmationService confirmationService, NotFilledFormNotification notFilledFormNotification) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.dishService = dishService;
        this.confirmationService = confirmationService;
        this.notFilledFormNotification = notFilledFormNotification;

        cartDto = cartService.createCart();

        createCustomerForm = new CreateCustomerForm(customerService); //przerobić na beana?
        createCustomerForm.setVisible(false);

        cartSummary = new CartSummary(cartService, cartDto);
        cartSummary.prepareMoveButton(createCustomerForm);

        listWithButtons = new ListOfDishesWithAddButtons(cartDto ,cartSummary,dishService,cartService);
        orderCreatedNotification = new OrderCreatedNotification();
        setButton();
        VerticalLayout gridWithButton = new VerticalLayout();
        gridWithButton.add(cartSummary, createCustomerForm, confirmationButton); //tu dodać buttonsy przecież

        add(listWithButtons);
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

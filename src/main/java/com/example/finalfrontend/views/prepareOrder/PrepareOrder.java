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


    @Autowired
    OrderCreatedNotification orderCreatedNotification;
    @Autowired
    NotFilledFormNotification notFilledFormNotification;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ConfirmationService confirmationService;


    public PrepareOrder(CartService cartService, CustomerService customerService, DishService dishService, GroupService groupService) {
        cartDto = cartService.createCart();
        createCustomerForm = new CreateCustomerForm(customerService);
        cartSummary = new CartSummary(cartService, cartDto);
        cartSummary.prepareMoveButton(createCustomerForm);

        groupsAccordion = new GroupsAccordion(cartDto, groupService, dishService, cartService, cartSummary);

        VerticalLayout gridWithButton = new VerticalLayout();
        gridWithButton.add(cartSummary, createCustomerForm, confirmOrderButton());

        add(groupsAccordion);
        add(gridWithButton);
    }

    private Button confirmOrderButton() {
        Button confirmationButton = new Button("Złóż zamówienie");
        confirmationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmationButton.addClickShortcut(Key.ENTER);

        confirmationButton.addClickListener(event -> {
            if (createCustomerForm.isFilled()) {
                CustomerDto customerDto = createCustomerForm.createCustomer();
                OrderDto orderDto = orderService.createOrder(cartDto.getId(),customerDto.getId());
                String sentConfirmation = confirmationService.sentConfirmation(orderDto.getId());
                System.out.println(sentConfirmation);
                orderCreatedNotification.show(orderDto);
            } else {
                notFilledFormNotification.show();
            }
        });
        return confirmationButton;
    }

}

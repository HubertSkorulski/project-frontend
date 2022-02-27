package com.example.finalfrontend.views.prepareOrder;

import com.example.finalfrontend.dto.OrderDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedNotification extends Notification {

    Button closeButton = new Button(new Icon("lumo","cross"));
    Text textToDisplay = new Text("Zamówienie złożone.");
    Div text = new Div(textToDisplay);


    public OrderCreatedNotification() {
        setPosition(Notification.Position.TOP_CENTER);
        addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        setButton();
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(layout);
    }

    private void setButton() {
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            this.close();
            UI.getCurrent().navigate("confirmation");
        });
    }

    private void setText(OrderDto orderDto) {
        textToDisplay.setText("Zamówienie złożone. " + "Zamówienie nr: " + orderDto.getId());
    }

    public void show(OrderDto orderDto) {
        this.setText(orderDto);
        this.open();
    }
}

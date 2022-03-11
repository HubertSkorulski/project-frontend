package com.example.finalfrontend.views.dishes;

import com.example.finalfrontend.dto.DishDto;
import com.example.finalfrontend.dto.GroupDto;
import com.example.finalfrontend.service.DishService;
import com.example.finalfrontend.service.GroupService;
import com.example.finalfrontend.views.BaseLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Comparator;
import java.util.Optional;

@PageTitle("Dishes")
@Route(value = "dishes", layout = BaseLayout.class)
public class DishesGrid extends VerticalLayout {

    Grid<DishDto> grid = new Grid<>(DishDto.class,false);
    DishUpdateForm dishUpdateForm;
    VerticalLayout formWithButtons = new VerticalLayout();
    Button saveButton = new Button("Zapisz");
    Button newDishButton = new Button("Dodaj danie");
    Button closeButton = new Button(new Icon("lumo","cross"));
    TextField filter = new TextField();
    private final DishService dishService;



    public DishesGrid(DishService dishService, GroupService groupService) {
        this.dishService = dishService;
        dishUpdateForm = new DishUpdateForm(groupService);
        updateGrid();
        setNewDishButton();

        add(filterFieldAndNewDishButton());
        add(gridAndForm());
    }
    private HorizontalLayout filterFieldAndNewDishButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        setFilterField();

        horizontalLayout.add(filter);
        horizontalLayout.add(newDishButton);
        return horizontalLayout;
    }

    private HorizontalLayout gridAndForm() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        setFormWithSaveAndCloseButton();
        setGrid();

        horizontalLayout.add(grid);
        horizontalLayout.add(formWithButtons);
        return horizontalLayout;
    }

    private void updateGrid() {
        grid.setItems(dishService.getFilteredByTitleDishDtoList(filter.getValue()).stream()
                .sorted(Comparator.comparing(DishDto::getName))
        );
    }

    private void setGrid() {
        grid.setHeight(600,Unit.PIXELS);
        grid.setWidth(900,Unit.PIXELS);
        grid.addColumn(DishDto::getName).setHeader("Dish");
        grid.addColumn(DishDto::getPrice).setHeader("Price");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::setTrashButton)
        ).setHeader("Usuń").setWidth("3em");

        grid.addSelectionListener(selection -> {
            Optional<DishDto> dishDto = selection.getFirstSelectedItem();
            if (dishDto.isPresent()) {
                formWithButtons.setVisible(true);
                dishUpdateForm.setParameters(dishDto.get());
            }
        });
    }

    private void setTrashButton(Button button, DishDto dishDto) {
        button.setIcon(new Icon(VaadinIcon.TRASH));
        button.addThemeVariants(
                ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e ->
                {
                dishService.deleteDish(dishDto.getId());
                updateGrid();
                }
        );
    }

    private void setFormWithSaveAndCloseButton() {
        setSaveButton();
        setFormCloseButton();
        formWithButtons.add(closeButton, dishUpdateForm, saveButton);
        formWithButtons.setVisible(false);
        formWithButtons.setWidth(400,Unit.PIXELS);
    }

    private void setFilterField() {
        filter.setPlaceholder("Filtruj po nazwie");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateGrid());
    }

    private void setSaveButton() {
        saveButton.addClickListener(event -> {
            String dishName = dishUpdateForm.getName();
            double price = dishUpdateForm.getPrice();
            GroupDto groupDto = dishUpdateForm.getGroup();
            Long id = dishUpdateForm.getDishId();

            if (id != null) {
                dishService.updateDish(id,dishName,price,groupDto.getId());
            } else {
                dishService.createDish(dishName,price, groupDto.getId());
            }
            System.out.println(id);
            updateGrid();
            dishUpdateForm.clear();
            formWithButtons.setVisible(false);
        });
    }

    private void setFormCloseButton() {
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            formWithButtons.setVisible(false);
            dishUpdateForm.clear();
        });
    }

    private void setNewDishButton() {
        newDishButton.addClickListener(event -> {
            dishUpdateForm.clear();
            formWithButtons.setVisible(true);
        });
    }


}

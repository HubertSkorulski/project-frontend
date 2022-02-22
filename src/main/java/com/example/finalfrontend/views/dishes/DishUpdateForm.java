package com.example.finalfrontend.views.dishes;

import com.example.finalfrontend.dto.DishDto;
import com.example.finalfrontend.dto.GroupDto;
import com.example.finalfrontend.service.GroupService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;


public class DishUpdateForm extends VerticalLayout {
    TextField name = new TextField("Nazwa");
    ComboBox<GroupDto> groupDtoComboBox = new ComboBox<>("Group");
    NumberField price = new NumberField("Cena");
    private Long dishId;
    private final GroupService groupService;


    public DishUpdateForm(GroupService groupService) {
        this.groupService = groupService;
        add(name);
        add(price);
        setComboBox();
        add(groupDtoComboBox);
    }

    public void setParameters(DishDto dishDto) {
        name.setValue(dishDto.getName());
        price.setValue(dishDto.getPrice());
        groupDtoComboBox.setValue(groupService.getGroup(dishDto.getGroupId()));
        dishId = dishDto.getId();
    }

    public String getName() {
        return name.getValue();
    }

    public double getPrice() {
        return price.getValue();
    }

    public Long getDishId () {
        return dishId;
    }

    public void clear() {
        name.clear();
        price.clear();
        groupDtoComboBox.clear();
        dishId = null;
    }

    private void setComboBox() {
        groupDtoComboBox.setItems(groupService.getGroups());
        groupDtoComboBox.setItemLabelGenerator(GroupDto::getGroupName);
        groupDtoComboBox.setAllowCustomValue(false);
    }

    public GroupDto getGroup() {
        return groupDtoComboBox.getValue();
    }
}

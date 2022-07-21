package net.babypython.client.ui.windows.session.register.widgets;

import com.google.gwt.user.client.ui.TextBox;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.gwt.widgets.GwtForm;

public class RegisterForm extends GwtForm {

    protected void addFields() {
        super.addFields();
        nameField = addTextBox("Name");
        passwordField = addTextBox("Password");
    }

    @Override
    protected int defaultLabelWidth() {
        return CommonWindowConstants.RegisterWindowLabelWidth;
    }

    @Override
    protected int defaultWidgetAdjust() {
        return CommonWindowConstants.RegisterWindowWidgetAdjust;
    }

    public String getName() {
        return nameField.getValue();
    }

    public String getPassword() {
        return passwordField.getValue();
    }

    public void reset() {
        nameField.setValue("");
        passwordField.setValue("");
    }

    public String validateForm() {
        String name = getName();
        String password = getPassword();
        if (name.length() < 3)
            return "name must be at least 3 characters";
        if (password.length() < 3)
            return "password must be at least 3 characters";
        return null;
    }

    TextBox nameField;
    TextBox passwordField;
}

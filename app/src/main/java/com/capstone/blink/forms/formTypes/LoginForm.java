package com.capstone.blink.forms.formTypes;

import com.capstone.blink.forms.Form;

public class LoginForm extends BaseForm {

    private Form form;

    public LoginForm(String... args) {
        this.form = validate(args);

    }

    public Form validate() {
        return form;
    }

    @Override
    protected Form validate(String... args) {
        return isNotBlank(args);
    }

}

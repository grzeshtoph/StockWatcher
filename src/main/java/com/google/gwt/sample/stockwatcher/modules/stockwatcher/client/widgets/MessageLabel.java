package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets;

import com.google.gwt.user.client.ui.Label;

/**
 * General message label with showing anf hiding methods.
 */
public abstract class MessageLabel extends Label {
    MessageLabel(String styleName) {
        this.setStyleName(styleName);
        this.setVisible(false);
    }

    void setMessage(String message) {
        this.setText(message);
        this.setVisible(true);
    }

    void hide() {
        this.setText("");
        this.setVisible(false);
    }
}

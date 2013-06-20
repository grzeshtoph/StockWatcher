package com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

/**
 * Stock reader form to submit a request.
 */
public class StockReaderPanel extends FramedPanel {
    public StockReaderPanel() {
        this.setHeadingText("Stock Currencies Reader");
        this.setWidth(350);
        this.setBodyStyle("background: none; padding: 5px;");
    }

    public StockReaderPanel build() {
        VerticalLayoutContainer verticalLayout = new VerticalLayoutContainer();
        CurrenciesTextField currenciesTextField = new CurrenciesTextField();
        verticalLayout.add(new FieldLabel(currenciesTextField, "Currencies"),
                new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        this.add(verticalLayout);
        this.addButton(new SubmitButton(currenciesTextField));
        return this;
    }

}

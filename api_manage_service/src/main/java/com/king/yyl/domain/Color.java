
package com.king.yyl.domain;


public class Color extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    // Color code which can be rendered on the UI
    private String colorCode;
    // Display Name
    private String displayName;

    public String getColorCode() {
	return colorCode;
    }

    public void setColorCode(String colorCode) {
	this.colorCode = colorCode;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

}

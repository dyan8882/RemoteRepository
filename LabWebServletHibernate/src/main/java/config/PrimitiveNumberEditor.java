package config;

import java.text.NumberFormat;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class PrimitiveNumberEditor extends CustomNumberEditor {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if((text==null || text.length()==0) && ALLOW_EMPTY) {
			setAsText("0");
		} else {
			super.setAsText(text);
		}
	}
	private final boolean ALLOW_EMPTY;
	public PrimitiveNumberEditor(Class<? extends Number> numberClass,
			boolean allowEmpty) throws IllegalArgumentException {
		this(numberClass, null, allowEmpty);
	}
	public PrimitiveNumberEditor(Class<? extends Number> numberClass,
			NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
		super(numberClass, numberFormat, allowEmpty);
		this.ALLOW_EMPTY = allowEmpty;
	}
}

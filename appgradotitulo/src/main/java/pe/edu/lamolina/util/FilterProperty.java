package pe.edu.lamolina.util;

import java.util.List;

public class FilterProperty<Type> {
    private String name;
    private List<Type> values;
    private Type value;
    private Boolean multipleValues;
    private Boolean unlike;

    public FilterProperty(String name, List<Type> values,Boolean unlike) {
    	this.name = name;
    	this.values = values;
    	this.multipleValues = Boolean.TRUE;
    	this.unlike = unlike;
    }
    public FilterProperty(String name, List<Type> values) {
        this.name = name;
        this.values = values;
        this.multipleValues = Boolean.TRUE;
        this.unlike=Boolean.FALSE;
    }

    public FilterProperty(String name, Type value,Boolean unlike) {
    	this.name = name;
    	this.value = value;
    	this.multipleValues = Boolean.FALSE;
    	this.unlike=unlike;
    }
    public FilterProperty(String name, Type value) {
        this.name = name;
        this.value = value;
        this.multipleValues = Boolean.FALSE;
        this.unlike=Boolean.FALSE;
    }

    public Boolean getUnlike() {
		return unlike;
	}

	public void setUnlike(Boolean unlike) {
		this.unlike = unlike;
	}

	public Boolean getMultipleValues() {
        return multipleValues;
    }

    public void setMultipleValues(Boolean multipleValues) {
        this.multipleValues = multipleValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getValue() {
        return value;
    }

    public void setValue(Type value) {
        this.value = value;
    }

    public List<Type> getValues() {
        return values;
    }

    public void setValues(List<Type> values) {
        this.values = values;
    }
    
}
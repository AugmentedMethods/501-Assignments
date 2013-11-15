package Inspector;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/21/13
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class FieldDetails {
    private String name;
    private String type;
    private int modifier;
    private ArrayList<String> value;

    public  FieldDetails()
    {
        value = new ArrayList<String>();
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value.add(value);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }


}


import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/20/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassDetails {

    private String declaringClass;
    private String superClass;

    private ArrayList<String> interfaces;

    private ArrayList<MethodDetails> methods;

    private ArrayList<MethodDetails> constructors;

    private ArrayList<FieldDetails> fields;

    public ClassDetails()
    {
        interfaces = new ArrayList<String>();
        methods = new ArrayList<MethodDetails>();
        constructors = new ArrayList<MethodDetails>();
        fields = new ArrayList<FieldDetails>();
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(String declaringClass) {
        this.declaringClass = declaringClass;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public ArrayList<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces.add(interfaces);
    }

    public ArrayList<MethodDetails> getMethods() {
        return methods;
    }

    public void setMethods(MethodDetails methods) {
        this.methods.add(methods);
    }

    public ArrayList<MethodDetails> getConstructors() {
        return constructors;
    }

    public void setConstructors(MethodDetails constructors) {
        this.constructors.add(constructors);
    }

    public ArrayList<FieldDetails> getFields() {
        return fields;
    }

    public void setFields(FieldDetails fields) {
        this.fields.add(fields);
    }


}

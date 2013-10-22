import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/20/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodDetails {

    private ArrayList<String> exceptions;
    private ArrayList<String> parameters;
    private String returnType;
    private int modifiers;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    private String methodName;

    public MethodDetails()
    {
        exceptions = new ArrayList<String>();
        parameters = new ArrayList<String>( );
    }

    public ArrayList<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions.add(exceptions);
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters.add(parameters);
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }
}

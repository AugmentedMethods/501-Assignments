import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/21/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodExamination {

    private Class classObj;
    private MethodDetails currentMethod;
    private ClassDetails currentClass;

    public MethodExamination(Class classObj,ClassDetails currentClass)
    {
        this.classObj = classObj;
        this.currentMethod = currentMethod;
        this.currentClass = currentClass;
    }


    public void getMethods()
    {
        for(Method m : classObj.getDeclaredMethods() )
        {
            try{
                this.currentMethod = new MethodDetails();
                currentClass.setMethods(currentMethod);
                currentMethod.setMethodName(m.getName());
                methodExamination(m);
            }
            catch (SecurityException e)
            {

            }
        }
    }

    private void methodExamination(Method methodObj)
    {
            getExceptionType(methodObj);
            getParameterTypes(methodObj);
            getReturnType(methodObj);

        getModifiers(methodObj);
    }

    private void getExceptionType(Method methodObj)
    {
        try{
            for(Type t: methodObj.getExceptionTypes())
                currentMethod.setExceptions(t.toString());
        }
        catch (Error e)
        {}
    }

    private void getParameterTypes(Method methodObj)
    {
        try{
            for(Type t: methodObj.getGenericParameterTypes())
                currentMethod.setParameters(t.toString());
        }
        catch (Error e)
        {}
    }

    private void getReturnType(Method methodObj)
    {
        try{
            currentMethod.setReturnType(methodObj.getGenericReturnType().toString());
        }
        catch (Error e)
        {}
    }

    private void getModifiers(Method methodObj)
    {
        currentMethod.setModifiers(methodObj.getModifiers());
    }

    private void getConstructors()
    {
        for(Constructor<?> c : classObj.getDeclaredConstructors())
        {
            try{
                currentMethod = new MethodDetails();
                currentClass.setConstructors(new MethodDetails());
                constructorExamination(c);
            }
            catch (SecurityException e)
            {

            }
        }
    }

    private void constructorExamination(Constructor<?> c)
    {
        currentMethod.setMethodName("Constructor");
        try{
            for(Type t: c.getParameterTypes())
                currentMethod.setParameters(t.toString());
            if(currentMethod.getParameters().size() == 0)
                currentMethod.setParameters("");
        }
        catch (Error e)
        {}
        currentMethod.setModifiers(c.getModifiers());
    }

}

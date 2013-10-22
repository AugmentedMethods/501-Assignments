import java.lang.invoke.MethodHandle;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.lang.Class;


/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/18/13
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class Inspector {

    private ArrayList<String> log;
    private Object obj;
    private Class classObj;
    private ArrayList<ClassDetails> classList;
    private ClassDetails currentClass;
    private MethodDetails currentMethod;

    public void inspect(Object obj, boolean recursive)
    {
        log = new ArrayList<String>();
        this.obj = obj;
        runClassTest();
    }

    private void runClassTest()
    {
        classObj = obj.getClass();
        classList = new ArrayList<ClassDetails>();

        ClassDetails newClassDetails = new ClassDetails();
        classList.add(newClassDetails);
        currentClass = classList.get(0);

        getClassName();
        getDirectSuperClass();
        getInterfaces();
        getMethods();
        getConstructors();
        getFieldsDetails();

        //printLog();
        printObj();
    }

    private void getFieldsDetails()
    {
        for(Field f : classObj.getDeclaredFields())
        {
            FieldDetails newField = new FieldDetails();
            newField.setName(f.getName());
            newField.setType(f.getType().toString());
            newField.setModifier(f.getModifiers());
            currentClass.setFields(newField);

        }

    }

    private void getClassName()
    {
        currentClass.setDeclaringClass(classObj.getCanonicalName());
    }

    private void getDirectSuperClass()
    {
        currentClass.setSuperClass(classObj.getGenericSuperclass().toString());
    }

    private void getInterfaces()
    {
        for(Class<?> c : classObj.getInterfaces() )
            currentClass.setInterfaces(c.toString());
    }

    private void getMethods()
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
                log.add("Error: "+e.toString());
            }
        }
    }

    private void getConstructors()
    {
        for(Constructor<?> c : classObj.getDeclaredConstructors())
        {
            try{
                this.currentMethod = new MethodDetails();
                currentClass.setConstructors(currentMethod);
                constructorExamination(c);
            }
            catch (SecurityException e)
            {
                log.add("Error: "+e.toString());
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
        {log.add("Exception in getParameterTypes");}
        currentMethod.setModifiers(c.getModifiers());
    }

    private void methodExamination(Method methodObj)
    {
        //horrible logic on the if statment, should change it.
        if(!methodObj.getDeclaringClass().isInstance(classObj)){
            getExceptionType(methodObj);
            getParameterTypes(methodObj);
            getReturnType(methodObj);
            getModifiers(methodObj);
        }
    }

    private void getExceptionType(Method methodObj)
    {
        try{
            for(Type t: methodObj.getExceptionTypes())
                currentMethod.setExceptions(t.toString());
        }
        catch (Error e)
        {log.add("Error at getException type");}
    }

    private void getParameterTypes(Method methodObj)
    {
        try{
            for(Type t: methodObj.getGenericParameterTypes())
                currentMethod.setParameters(t.toString());
        }
        catch (Error e)
        {log.add("Exception in getParameterTypes");}
    }

    private void getReturnType(Method methodObj)
    {
        try{
            currentMethod.setReturnType(methodObj.getGenericReturnType().toString());
        }
        catch (Error e)
        {log.add("Exception in getParameterTypes");}
    }

    private void getModifiers(Method methodObj)
    {
        currentMethod.setModifiers(methodObj.getModifiers());
    }

    private void printObj()
    {
        for(ClassDetails d : classList)
        {
            System.out.println("Declaring Class:" + d.getDeclaringClass());
            System.out.println("Super Class:" + d.getSuperClass());
            System.out.print("Interfaces: ");
            for(String s : d.getInterfaces())
                System.out.print(s + ", ");
            System.out.println();

            for(MethodDetails m: d.getMethods())
            {
                printMethodDetailsException(m);
                printMethodDetailsParameters(m);
                System.out.println(m.getMethodName() + " Return Type: " + m.getReturnType());
                printModifers(m);
            }

            for(MethodDetails m : d.getConstructors())
            {
                printMethodDetailsParameters(m);
                printModifers(m);
            }

            for(FieldDetails f : d.getFields())
            {
                System.out.println(f.getName()+ " Type: " + f.getType());
                printModifers(f);
            }
        }
    }

    private void printMethodDetailsException(MethodDetails m)
    {
        boolean flag = false;
        for(String s : m.getExceptions())   {
            if(flag == false)
                System.out.print(m.getMethodName()+" Exception: ");
            System.out.print(s + ", ");
            flag = true;
        }
        if(flag == true)
            System.out.println();
    }

    private void printMethodDetailsParameters(MethodDetails m)
    {
        boolean flag = false;
        for(String s : m.getParameters())   {
            if(flag == false)
                System.out.print(m.getMethodName()+" Parameters: ");
            System.out.print(s + ", ");
            flag = true;
        }
        if(flag == true)
            System.out.println();
    }

    private void printModifers(MethodDetails m)
    {
        switch (m.getModifiers())
        {
            case Modifier.ABSTRACT:
                System.out.println(m.getMethodName()+" Modifier: "+ "ABSTRACT");
                break;
            case Modifier.FINAL:
                System.out.println(m.getMethodName()+" Modifier: "+ "FINAL");
                break;
            case Modifier.INTERFACE:
                System.out.println(m.getMethodName()+" Modifier: "+ "INTERFACE");
                break;

            case Modifier.NATIVE:
                System.out.println(m.getMethodName()+" Modifier: "+ "NATIVE");
                break;

            case Modifier.PRIVATE:
                System.out.println(m.getMethodName()+" Modifier: "+ "PRIVATE");
                break;
            case Modifier.PROTECTED:
                System.out.println(m.getMethodName()+" Modifier: "+ "PROTECTED");
                break;

            case Modifier.PUBLIC:
                System.out.println(m.getMethodName()+" Modifier: "+ "PUBLIC");
                break;

            case Modifier.STATIC:
                System.out.println(m.getMethodName()+" Modifier: "+ "STATIC");
                break;

            case Modifier.STRICT:
                System.out.println(m.getMethodName()+" Modifier: "+ "STRICT");
                break;

            case Modifier.SYNCHRONIZED:
                System.out.println(m.getMethodName()+" Modifier: "+ "SYNCHRONIZED");
                break;

            case Modifier.TRANSIENT:
                System.out.println(m.getMethodName()+" Modifier: "+ "TRANSIENT");
                break;

            case Modifier.VOLATILE:
                System.out.println(m.getMethodName()+" Modifier: "+ "VOLATILE");
                break;

            default:
                System.out.println(m.getMethodName() + " Modifier: " + "Failure");
                break;
        }
    }

    private void printModifers(FieldDetails f)
    {
        switch (f.getModifier())
        {
            case Modifier.ABSTRACT:
                System.out.println(f.getName()+" Modifier: "+ "ABSTRACT");
                break;
            case Modifier.FINAL:
                System.out.println(f.getName()+" Modifier: "+ "FINAL");
                break;
            case Modifier.INTERFACE:
                System.out.println(f.getName()+" Modifier: "+ "INTERFACE");
                break;

            case Modifier.NATIVE:
                System.out.println(f.getName()+" Modifier: "+ "NATIVE");
                break;

            case Modifier.PRIVATE:
                System.out.println(f.getName()+" Modifier: "+ "PRIVATE");
                break;
            case Modifier.PROTECTED:
                System.out.println(f.getName()+" Modifier: "+ "PROTECTED");
                break;

            case Modifier.PUBLIC:
                System.out.println(f.getName()+" Modifier: "+ "PUBLIC");
                break;

            case Modifier.STATIC:
                System.out.println(f.getName()+" Modifier: "+ "STATIC");
                break;

            case Modifier.STRICT:
                System.out.println(f.getName()+" Modifier: "+ "STRICT");
                break;

            case Modifier.SYNCHRONIZED:
                System.out.println(f.getName()+" Modifier: "+ "SYNCHRONIZED");
                break;

            case Modifier.TRANSIENT:
                System.out.println(f.getName()+" Modifier: "+ "TRANSIENT");
                break;

            case Modifier.VOLATILE:
                System.out.println(f.getName()+" Modifier: "+ "VOLATILE");
                break;

            default:
                System.out.println(f.getName() + " Modifier: " + "Failure");
                break;
        }
    }














    private void runInterfaceTest(String objName)
    {}
}

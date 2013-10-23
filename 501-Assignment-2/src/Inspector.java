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

    private Object obj;
    private Class classObj;
    private ArrayList<ClassDetails> classList;
    private ClassDetails currentClass;
    private MethodDetails currentMethod;
    private boolean recursive;

    public void inspect(Object obj, boolean recursive)
    {
        this.obj = obj;
        this.recursive = recursive;
        runClassTest();
    }

    private void runClassTest()
    {
        classObj = obj.getClass();
        classList = new ArrayList<ClassDetails>();

        ClassDetails newClassDetails = new ClassDetails();
        classList.add(newClassDetails);
        currentClass = classList.get(0);

        ArrayList<ClassDetails> currentClassPointer = new ArrayList<ClassDetails>();
        currentClassPointer.add(currentClass);

        ClassExamination examineClass = new ClassExamination(classObj, currentClassPointer);
        examineClass.runClassExamination();

        MethodExamination examineMethod = new MethodExamination(classObj,currentClassPointer );
        examineMethod.getMethods();

        FieldExamination examineFields = new FieldExamination(classObj,currentClassPointer);
        examineFields.fieldExamination();

        if(recursive == true)
            startRecursion(1);
        printObj();
    }

    private void startRecursion(int arrayPointer)
    {
        try{
            Class newClassObj = classObj.getSuperclass();
            System.out.println(newClassObj.getName());
            classObj = newClassObj;
            ClassDetails newClassDetails = new ClassDetails();
            classList.add(newClassDetails);
            currentClass = classList.get(arrayPointer);

            ArrayList<ClassDetails> currentClassPointer = new ArrayList<ClassDetails>();
            currentClassPointer.add(currentClass);
            ClassExamination examineClass = new ClassExamination(classObj, currentClassPointer);

            examineClass.runClassExamination();
            System.out.println("This Three");

            MethodExamination examineMethod = new MethodExamination(classObj,currentClassPointer );
            examineMethod.getMethods();

            FieldExamination examineFields = new FieldExamination(classObj,currentClassPointer);
            examineFields.fieldExamination();

            if(!classObj.isInstance(Object.class))
                startRecursion(arrayPointer +1);
        }
        catch(Error e)
        {

        }
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
                System.out.println(f.getName() + " Modifier: " + f.getModifier());
                break;
        }
    }














    private void runInterfaceTest(String objName)
    {}
}

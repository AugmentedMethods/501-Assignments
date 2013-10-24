import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/24/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExaminerClass {
    private Object obj;
    private Class classObj;
    private ArrayList<ClassDetails> classList;
    private ClassDetails currentClass;
    private MethodDetails currentMethod;
    private boolean recursive;

    ExaminerClass(Object obj, boolean recursive)
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

        ClassExamination examineClass = new ClassExamination(classObj, currentClass);
        examineClass.runClassExamination();

        MethodExamination examineMethod = new MethodExamination(classObj,currentClass );
        examineMethod.getMethods();

        FieldExamination examineFields = new FieldExamination(classObj,currentClass, obj, recursive);
        examineFields.fieldExamination();

        startRecursion(1);

        printObj();
    }

    private void startRecursion(int arrayPointer)
    {
        try{
            Class newClassObj = classObj.getSuperclass();

            classObj = newClassObj;
            ClassDetails newClassDetails = new ClassDetails();
            classList.add(newClassDetails);
            currentClass = classList.get(arrayPointer);

            ClassExamination examineClass = new ClassExamination(classObj, currentClass);

            examineClass.runClassExamination();

            MethodExamination examineMethod = new MethodExamination(classObj,currentClass );
            examineMethod.getMethods();

            FieldExamination examineFields = new FieldExamination(classObj,currentClass, obj, recursive);
            examineFields.fieldExamination();

            if(!classObj.isInstance(Object.class))
                startRecursion(arrayPointer +1);
        }
        catch(Exception e)
        {
            System.out.println("*****************************************************************************");
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
                System.out.print(f.getName() + " Value: ");
                for(String s : f.getValue())
                    System.out.print(s +", ");
                System.out.println();
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
        int mod = m.getModifiers();

        System.out.print(m.getMethodName()+" Modifier: ");
        if(Modifier.isAbstract(mod))
            System.out.print("ABSTRACT, ");

        if(Modifier.isFinal(mod))
            System.out.print( "FINAL, ");

        if(Modifier.isInterface(mod))
            System.out.print( "INTERFACE, ");

        if(Modifier.isNative(mod))
            System.out.print( "NATIVE, ");

        if(Modifier.isPrivate(mod))
            System.out.print( "PRIVATE, ");

        if(Modifier.isProtected(mod))
            System.out.print("PROTECTED, ");

        if(Modifier.isPublic(mod))
            System.out.print( "PUBLIC, ");

        if(Modifier.isStatic(mod))
            System.out.print("STATIC, ");

        if(Modifier.isStrict(mod))
            System.out.print( "STRICT, ");

        if(Modifier.isSynchronized(mod))
            System.out.print("SYNCHRONIZED, ");

        if(Modifier.isTransient(mod))
            System.out.print( "TRANSIENT, ");

        if(Modifier.isVolatile(mod))
            System.out.print( "VOLATILE, ");
        System.out.println();
    }

    private void printModifers(FieldDetails f)
    {
        int mod = f.getModifier();

        System.out.print(f.getName()+" Modifier: ");
        if(Modifier.isAbstract(mod))
            System.out.print("ABSTRACT, ");

        if(Modifier.isFinal(mod))
            System.out.print( "FINAL, ");

        if(Modifier.isInterface(mod))
            System.out.print( "INTERFACE, ");

        if(Modifier.isNative(mod))
            System.out.print( "NATIVE, ");

        if(Modifier.isPrivate(mod))
            System.out.print( "PRIVATE, ");

        if(Modifier.isProtected(mod))
            System.out.print("PROTECTED, ");

        if(Modifier.isPublic(mod))
            System.out.print( "PUBLIC, ");

        if(Modifier.isStatic(mod))
            System.out.print("STATIC, ");

        if(Modifier.isStrict(mod))
            System.out.print( "STRICT, ");

        if(Modifier.isSynchronized(mod))
            System.out.print("SYNCHRONIZED, ");

        if(Modifier.isTransient(mod))
            System.out.print( "TRANSIENT, ");

        if(Modifier.isVolatile(mod))
            System.out.print( "VOLATILE, ");
        System.out.println();
    }

}

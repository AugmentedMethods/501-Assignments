import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/21/13
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassExamination {
    private Class classObj;
    private ArrayList<ClassDetails> currentClass;

    ClassExamination( Class classObj, ArrayList<ClassDetails> currentClass)
    {
        this.classObj = classObj;
        this.currentClass = currentClass;
    }

    public void runClassExamination()
    {
        getClassName();
        getDirectSuperClass();
        getInterfaces();


    }

    private void getClassName()
    {
        currentClass.get(0).setDeclaringClass(classObj.getCanonicalName());
    }

    private void getDirectSuperClass()
    {
        if(!classObj.isInstance(Object.class))
            currentClass.get(0).setSuperClass(classObj.getGenericSuperclass().toString());
    }

    private void getInterfaces()
    {
        for(Class<?> c : classObj.getInterfaces() )
            currentClass.get(0).setInterfaces(c.toString());

    }
}

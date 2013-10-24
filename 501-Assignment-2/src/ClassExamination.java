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
    private ClassDetails currentClass;

    ClassExamination( Class classObj, ClassDetails currentClass)
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
        currentClass.setDeclaringClass(classObj.getCanonicalName());
    }

    private void getDirectSuperClass()
    {
        if(!classObj.isInstance(Object.class))
            currentClass.setSuperClass(classObj.getGenericSuperclass().toString());

    }

    private void getInterfaces()
    {
        for(Class<?> c : classObj.getInterfaces() )
            currentClass.setInterfaces(c.toString());

    }
}

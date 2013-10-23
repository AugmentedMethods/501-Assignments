import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/21/13
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldExamination {

    private Class classObj;
    private ArrayList<ClassDetails> currentClass;

    public FieldExamination(Class classObj, ArrayList<ClassDetails> currentClass)
    {
        this.classObj = classObj;
        this.currentClass = currentClass;
    }

    public void fieldExamination(){
        getFieldsDetails();
    }

    private void getFieldsDetails()
    {
        for(Field f : classObj.getDeclaredFields())
        {
            FieldDetails newField = new FieldDetails();
            newField.setName(f.getName());
            newField.setType(f.getType().toString());
            newField.setModifier(f.getModifiers());
            currentClass.get(0).setFields(newField);
            //System.out.println("HERE!!!!!: " +f.getName() +" :" + f.getModifiers() );

        }

    }
}

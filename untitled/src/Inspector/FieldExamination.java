package Inspector;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/21/13
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldExamination {

    private Class classObj;
    private ClassDetails currentClass;
    private Object obj;
    private boolean fieldRecursion;

    public FieldExamination(Class classObj, ClassDetails currentClass, Object obj, boolean fieldRecursion)
    {
        this.classObj = classObj;
        this.currentClass = currentClass;
        this.fieldRecursion = fieldRecursion;
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
            if(f.getType().isArray())
                newField.setType("Array<"+ f.getType().getComponentType()+">");
            else
                newField.setType( f.getType().toString());
            newField.setModifier(f.getModifiers());

            if(f.getType().isPrimitive())
                getValuePrimative(f, newField);

            else if (f.getType().isArray() && f.getType().getComponentType().isPrimitive())
                getArrayValue(f, newField);

            else if(f.getType().isArray() && !f.getType().getComponentType().isPrimitive()
                    && fieldRecursion == false)
                getPointerAddress(f, newField);

            else if(f.getType().isArray() && !f.getType().getComponentType().isPrimitive()
                    && fieldRecursion == true)
                recursiveIntrospectionArray(f, newField);

            else if(!f.getType().isPrimitive() && !f.getType().isArray() && fieldRecursion == true)
                recursiveIntrospection(f, newField);

            currentClass.setFields(newField);
            //System.out.println("HERE!!!!!: " +f.getName() +" :"  );


        }
    }

    private void getValuePrimative(Field f, FieldDetails newField)
    {
        try{
            f.setAccessible(true);

            newField.setValue((f.getDouble(f.get(obj))) +"");
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }

    }

    private void getValue(Field f, FieldDetails newField)
    {

        try{
            f.setAccessible(true);

            newField.setValue(f.get(obj).toString());
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    private void getArrayValue(Field f, FieldDetails newField)
    {
        try{
            f.setAccessible(true);
            Object array = f.get(obj);
            int length = Array.getLength(array);
            for(int i = 0; i < length; i++) {
                newField.setValue(Array.get(array,i).toString());
            }
        }
       catch (Exception e)
        {}
    }

    private void getPointerAddress(Field f, FieldDetails newField)
    {
        try{
            f.setAccessible(true);
            newField.setValue(f.get(obj).toString());
        }
        catch (Exception e)
        {}
    }

    private void recursiveIntrospectionArray(Field f, FieldDetails newField)
    {
        try{
            f.setAccessible(true);
            Object array = f.get(obj);
            int length = Array.getLength(array);
            for(int i = 0; i < length; i++) {
                if(Array.get(array,i) != null){
                    ExaminerClass newRun = new ExaminerClass(Array.get(array,i), fieldRecursion);}
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString()+" : "+"*************************************************************");
        }

    }

    private void recursiveIntrospection(Field f , FieldDetails newField)
    {
        try{
            f.setAccessible(true);
            if(f.get((obj))!= null){
            ExaminerClass newRun = new ExaminerClass(f.get(obj), fieldRecursion);}
        }
        catch (Exception e)
        {
            System.out.println(e.toString()+" : "+"*************************************************************");
        }
    }
}

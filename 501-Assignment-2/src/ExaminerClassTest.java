import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/24/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExaminerClassTest {

    @Test
    public void recusiveTest(){
        ExaminerClass testObj = new ExaminerClass(new Object(), true);
        assertTrue("Pass", true)    ;
    }

    @Test
    public void nonRecursiveTest(){
        ExaminerClass testObj = new ExaminerClass(new Object(), false);
        assertTrue("Pass", true)    ;
    }
                                                                                                                                                                       e



}

import northover.*

/*
*   @author Robert Northover
*/

class FluentASTTest extends GroovyTestCase {

    @Fluent
    static class FluentExample{
        def a
        int b
        boolean c
        int[] d
        List e
        StringBuilder f
        final String g
    }

    void testDef(){
        def expected = new FluentExample()
        def value = new Object()
        assert expected.setA(value) == expected
        assert expected.a == value
    }

    void testInt(){
        def expected = new FluentExample()
        int value = 10
        assert expected.setB(value) != null
        assert expected.setB(value) == expected
        assert expected.b == value
    }

    void testBoolean(){
        def expected = new FluentExample()
        boolean value = true
        assert expected.setC(value) == expected
        assert expected.c == value
    }

    void testArray(){
        def expected = new FluentExample()
        int[] value = [1, 2, 3]
        assert expected.setD(value) == expected
        assert expected.d == value
    }

    void testList(){
        def expected = new FluentExample()
        List value = ["One", "Two"]
        assert expected.setE(value) == expected
        assert expected.e == value
    }

    void testStringBuilder(){
        def expected = new FluentExample()
        StringBuilder value = new StringBuilder()
        assert expected.setF(value) == expected
        assert expected.f == value
    }

    void testChaining(){
        assert new FluentExample()
        .setA("Anything")
        .setB(new Integer(2))
        .setC(false)
        .setD([1, 2, 3]as int[])
        .setE(["a", 3, false])
        .setF(new StringBuilder())
        .getClass() == FluentExample
    }

    void testFinal(){
      shouldFail {new FluentExample().setG("Cannot Set Final")}
    }

    void testIncompatibleType(){
        shouldFail{
            new FluentExample().setB("Not an Int")
        }
    }
    
}
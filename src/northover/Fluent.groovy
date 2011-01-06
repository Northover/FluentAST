package northover

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

/*
*   @author Robert Northover
*/

@Retention(RetentionPolicy.SOURCE)
@Target( [ElementType.TYPE] )
@GroovyASTTransformationClass(["northover.FluentASTTransformation"])
public @interface Fluent {
}

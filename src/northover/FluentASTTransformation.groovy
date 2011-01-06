package northover

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import static org.objectweb.asm.Opcodes.ACC_PUBLIC

/*
*   @author Robert Northover
*/

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class FluentASTTransformation implements ASTTransformation {
  AstBuilder builder = new AstBuilder()

  @Override
  public void visit(ASTNode[] nodes, SourceUnit source) {

    // Add a fluent setter to all non-final properties
    source?.AST?.classes*.properties.flatten()
            .findAll {PropertyNode prop -> !prop.field.isFinal()}
            .each {PropertyNode prop -> nodes[1].addMethod(createFluentSetter(prop))}

  }

  MethodNode createFluentSetter(PropertyNode prop) {

    //Eclipse doesn't like 'getTypeClass' so lets provide an alternative
    def propClass = prop.type.isResolved() ? prop.type.typeClass : Class.forName(prop.type.name)

    builder.buildFromSpec {
      method("set${prop.name.capitalize()}", ACC_PUBLIC, Object) {
        parameters { parameter 'parameter': propClass }
        exceptions {}
        block {
          expression {
            binary {
              property {
                variable "this"
                constant prop.name
              }
              token "="
              variable "parameter"
            }
          }
          returnStatement { variable "this" }
        }
        annotations {}
      }
    }.first() as MethodNode
  }
}

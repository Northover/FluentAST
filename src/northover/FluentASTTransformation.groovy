package northover

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.ast.expr.*;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC


@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
class FluentASTTransformation implements ASTTransformation {
	AstBuilder builder = new AstBuilder()
	
	//@Override
	public void visit(ASTNode[] nodes, SourceUnit source) {
		def props = source.getAST()?.classes*.properties.flatten()
		props.collect{PropertyNode prop ->
			createFluentSetter(prop)
		}
		.each{nodes[1].addMethod(it)}
	}
	
	MethodNode createFluentSetter(PropertyNode prop){
		//prop?.type?.componentType?.typeClass ?: Object
		//def propclass = (!prop.type.isResolved() ? Object : prop.type.typeClass)
		//def plain = prop.type.getPlainNodeReference()
		
		def propclass = (prop.type.componentType ? prop.type.typeClass : Object)
		//def propclass = plain.typeClass
		builder.buildFromSpec{
			method("set${prop.name.capitalize()}", ACC_PUBLIC, Object){
				parameters { parameter 'parameter': propclass }
				exceptions {}
				block {
					expression{
						binary{
							property{
								variable "this"
								constant prop.name
							}
							token "="
							variable "parameter"
						}
					}
					returnStatement{ variable "this" }
				}
				annotations {}
			}
		}.first()
	}
}

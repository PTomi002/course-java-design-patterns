package hu.ptomi.pattern.visitor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor9;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(
        "java.lang.Deprecated"
)
@SupportedSourceVersion(
        SourceVersion.RELEASE_16
)
public class DeprecatedProcessor extends AbstractProcessor {

    private final ElementVisitor<TypeElement, RoundEnvironment> DEPRECATED_VISITOR = new SimpleElementVisitor9<>() {
        @Override
        public TypeElement visitType(TypeElement type, RoundEnvironment environment) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.WARNING,
                    environment
                            .getElementsAnnotatedWith(type)
                            .stream()
                            .peek(element -> System.out.println("Element: " + element.toString()))
                            .map(Object::toString)
                            .collect(Collectors.joining(", ", type + " (", ")"))
            );
            return super.visitType(type, environment);
        }
    };

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        annotations.forEach(a -> a.accept(DEPRECATED_VISITOR, environment));
        return true;
    }

}

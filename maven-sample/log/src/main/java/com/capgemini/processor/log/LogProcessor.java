package com.capgemini.processor.log;

import com.capgemini.annotations.Delegate;
import com.capgemini.annotations.DelegateClass;
import com.capgemini.annotations.Log;
import com.capgemini.processor.ClassModel;
import com.capgemini.processor.method.Constructor;
import com.capgemini.processor.method.Method;
import com.capgemini.processor.method.Parameter;
import gg.jte.generated.precompiled.JteLoggedGenerated;
import gg.jte.output.WriterOutput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SupportedAnnotationTypes("com.capgemini.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class LogProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        class TypeElementDependencies {
            final List<Constructor> constructors = new ArrayList<>();
            final List<Method> methods = new ArrayList<>();
            TypeElement element;
        }
        List<TypeElementDependencies> dependencies = new ArrayList<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(DelegateClass.class)) {
            TypeElementDependencies ted = new TypeElementDependencies();
            ted.element = (TypeElement) element;
            dependencies.add(ted);
        }
        Iterable<Element> elements = () -> Stream.of(Delegate.class, Log.class)
                .<Element>flatMap(e -> roundEnv.getElementsAnnotatedWith(e).stream())
                .iterator();

        for (Element element : elements) {
            Delegate delegate = element.getAnnotation(Delegate.class);
            Log log = element.getAnnotation(Log.class);
            ExecutableElement executableElement = (ExecutableElement) element;
            List<Parameter> parameters = executableElement.getParameters().stream()
                    .map(e -> new Parameter(e.asType().toString(), e.getSimpleName().toString()))
                    .collect(Collectors.toList());
            List<String> exceptions = executableElement.getThrownTypes().stream()
                    .map(TypeMirror::toString)
                    .collect(Collectors.toList());
            Element ee = element.getEnclosingElement();
            TypeElementDependencies ted = dependencies.stream()
                    .findAny()
                    .filter(e -> ee.hashCode() == e.element.hashCode() && e.element.equals(ee))
                    .orElseThrow(() -> new IllegalStateException("expected class annotated with @DelegateClass"));

            String visibility = Stream.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE)
                    .filter(executableElement.getModifiers()::contains)
                    .findAny()
                    .map(e -> e + " ")
                    .orElse("");
            if (element.getKind() == ElementKind.CONSTRUCTOR) {
                Constructor constructor = new Constructor(visibility, parameters, exceptions, log != null);
                ted.constructors.add(constructor);
            } else {
                String returnType = executableElement.getReturnType().toString();
                Method method = new Method(
                        visibility,
                        parameters,
                        returnType,
                        exceptions,
                        executableElement.getSimpleName().toString(),
                        log != null
                );
                ted.methods.add(method);
            }
        }

        for (TypeElementDependencies ted : dependencies) {
            String pkg = processingEnv.getElementUtils().getPackageOf(ted.element).getQualifiedName().toString();
            String name = ted.element.getAnnotation(DelegateClass.class).value();
            name = name.replace("${name}", ted.element.getSimpleName().toString());
            ClassModel classModel = new ClassModel(pkg, name, ted.methods, ted.constructors);
            String className = pkg + "." + name;
            try (Writer writer = processingEnv.getFiler().createSourceFile(className).openWriter()) {
                WriterOutput writerOutput = new WriterOutput(writer);
                JteLoggedGenerated.render(writerOutput, null, classModel, ted.element.getSimpleName().toString(), pkg);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "error on trying to write the class " + className);
            }
        }

        return true;
    }

}

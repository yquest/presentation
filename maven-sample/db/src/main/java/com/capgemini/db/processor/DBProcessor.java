package com.capgemini.db.processor;

import gg.jte.generated.precompiled.JteDBModelGenerated;
import gg.jte.output.WriterOutput;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("conf")
public class DBProcessor extends AbstractProcessor {

    public static final Pattern CLASS_RESOLVER = Pattern.compile("(.*)\\.(.*)");

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String confPath = processingEnv.getOptions().get("conf");
        File file = Paths.get(confPath, "db.yml").toFile();
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "config file not found: " + file.getAbsolutePath());
            return true;
        }
        Yaml yaml = new Yaml();
        Map<String, ?> read = yaml.load(reader);
        Optional.ofNullable(read.get("entities")).map(Map.class::cast)
                .<Set<?>>map(Map::entrySet)
                .<Set<Map.Entry<?, ?>>>map(Set.class::cast)
                .ifPresent(this::mapEvaluate);
        return false;
    }

    private void mapEvaluate(Object s) {
        if(!(s instanceof Set)) return;
        for (Object rawEl : (Set<?>)s) {
            Map.Entry<?,?> el = (Map.Entry<?, ?>) rawEl;
            Optional<String> opKey = Optional.ofNullable(el)
                    .map(Map.Entry::getKey)
                    .map(Object::toString);

            if (opKey.isEmpty()) return;
            String key = opKey.get();
            Matcher matcher = CLASS_RESOLVER.matcher(key);
            String pkg ,className;
            if(matcher.find()){
                pkg = matcher.group(1);
                className = matcher.group(2);
            }else{
                pkg = "";
                className = key;
            }


            Optional<Map<?, ?>> opMap = Optional.of(el).map(Map.Entry::getValue)
                    .flatMap(map -> {
                        if (!(map instanceof Map)) return Optional.empty();
                        else return Optional.of((Map<?, ?>) map);
                    });
            Optional<String> opTableName = opMap.map(e -> e.get("table")).map(Object::toString);
            if (opTableName.isEmpty()) return;
            String tableName = opTableName.get();
            Optional<Set<?>> opColumnNames = opMap.map(e -> e.get("columns")).map(map -> {
                if (!(map instanceof Map)) return null;
                else return ((Map<?, ?>) map).keySet();
            });
            if (opColumnNames.isEmpty()) return;
            Map<String, String> map = new HashMap<>();
            opColumnNames.map(names -> {
                String columns = names.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < names.size(); i++) {
                    sb.append("?");
                    if (i < (names).size() - 1) sb.append(", ");
                }
                return String.format("insert into %s (%s) values (%s)", tableName, columns, sb);
            }).ifPresent(e->map.put("INSERT",e));
            opColumnNames.map(names -> {
                String columns = names.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                return String.format("select %s from %s", columns, tableName);
            }).ifPresent(e->map.put("SELECT_ALL",e));
            if(processingEnv.getElementUtils().getTypeElement(key) != null){
                return;
            }
            try (Writer writer = processingEnv.getFiler().createSourceFile(className).openWriter()) {
                WriterOutput writerOutput = new WriterOutput(writer);
                JteDBModelGenerated.render(writerOutput, null, className, pkg, new DBEntries(map));
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "error on trying to write the class " + className);
            }
        }
    }

}

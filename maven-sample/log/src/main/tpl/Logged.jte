@import com.capgemini.processor.ClassModel
@param ClassModel delc
@param String delegateType
@param String pkg
package ${pkg};
public class ${delc.getName()}{
    private ${delegateType} delegate;
@for(var constructor:delc.getConstructors())
    @tag.Constructor(constructor = constructor, type = delc.getName(), delegateType = delegateType)
@endfor@for(var method:delc.getMethods())
    @tag.Method(method = method)
@endfor
}
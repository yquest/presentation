@import com.capgemini.processor.method.Method
@import java.util.stream.Collectors
@param Method method
${method.getVisibility()}${method.getReturnType()} ${method.getName()} (${method.getParametersRendered()})${method.getExceptionsRendered()}{
@for(var line: method.getDelegationRendered())        ${line}
@endfor    }
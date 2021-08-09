@import com.capgemini.processor.method.Constructor
@import com.capgemini.processor.method.Method
@import java.util.stream.Collectors
@param Constructor constructor
@param String type
@param String delegateType
${constructor.getVisibility()}${type}(${constructor.getParametersRendered()})${constructor.getExceptionsRendered()}{
@for(var line: constructor.getDelegationRendered(delegateType))        ${line}
@endfor    }
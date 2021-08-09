@import com.capgemini.db.processor.DBEntries
@param String type
@param String pkg
@param DBEntries entries
package ${pkg};
public interface ${type}{
@for(var entry:entries.getMap().entrySet())
    public String ${entry.getKey()} = "${entry.getValue()}";
@endfor
}
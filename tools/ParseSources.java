import javax.tools.*;
import com.sun.source.util.JavacTask;
import java.nio.file.*;
import java.util.*;
public class ParseSources {
 public static void main(String[] args) throws Exception {
  var compiler=ToolProvider.getSystemJavaCompiler();
  var diagnostics=new DiagnosticCollector<JavaFileObject>();
  try(var manager=compiler.getStandardFileManager(diagnostics,null,null)) {
   List<Path> paths;
   try(var stream=Files.walk(Path.of(args[0]))) { paths=stream.filter(p->p.toString().endsWith(".java")).toList(); }
   var task=(JavacTask)compiler.getTask(null,manager,diagnostics,List.of("-proc:none"),null,manager.getJavaFileObjectsFromPaths(paths));
   task.parse();
   var errors=diagnostics.getDiagnostics().stream().filter(d->d.getKind()==Diagnostic.Kind.ERROR).toList();
   System.out.println("Parsed files: "+paths.size()+"; syntax errors: "+errors.size());
   for(var d:errors.stream().limit(20).toList()) System.out.println(d.getSource().getName()+":"+d.getLineNumber()+": "+d.getMessage(Locale.ROOT));
   if(!errors.isEmpty()) System.exit(1);
  }
 }
}

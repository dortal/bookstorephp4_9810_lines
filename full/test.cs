//Don't forget 


public class CodeInjection
{
        static void foo(CSharpCodeProvider provider, CompilerParameters compilerParams, TextBox tb)
        {
                CompilerResults results = provider.CompileAssemblyFromSource(compilerParams, tb.Text);
                //print output
                var a = provider.Class;
        } //ddffgfkhghj
}






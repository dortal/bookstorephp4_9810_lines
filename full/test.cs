public class CodeInjection
{
        static void foo(CSharpCodeProvider provider, CompilerParameters compilerParams, TextBox tb)
        {
                CompilerResults results = provider.CompileAssemblyFromSource(compilerParams, tb.Text);
                //print output
                var a = provider.Class;
        }
}

//9898932





\\ ssss csdcsd d 4444 d    


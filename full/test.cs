public class CodeInjection
{
        static void foo(CSharpCodeProvider provider, CompilerParameters compilerParams, TextBox tb)
        {
                CompilerResults results = provider.CompileAssemblyFromSource(compilerParams, tb.Text);
                //print output
                var a = provider.Class;
        }
}

//989893251fg59ג16





\\ ssss csdcsd d 4444 d  2  


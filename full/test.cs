public class CodeInjection
{
        static void foo(CSharpCodeProvider provider, CompilerParameters compilerParams, TextBox tb)
        {
                CompilerResults results = provider.CompileAssemblyFromSource(compilerParams, tb.Text);
                //print output
                var a = provider.Class;
        }
}

\\ ssss csdcsd d dcscs3 d ddsdsdfsdsfdfdsf cfftttt fxsaxsa 


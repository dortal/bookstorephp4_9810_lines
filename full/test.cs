class CodeInjection
{
        static void foo(CSharpCodeProvider provider, CompilerParameters compilerParams, TextBox tb)
        {
                CompilerResults results = provider.CompileAssemblyFromSource(compilerParams, tb.Text);
        }
}

\\  cfftttt fvdvffd  fvsff 

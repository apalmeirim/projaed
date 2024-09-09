import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * A classe Tests especifica um conjunto de testes implementado recorrendo 'a ferramenta 
 * JUnit. Estes testes usam como input os ficheiros de teste do Mooshak, gerando, como
 * output, o resultado esperado na execucao desses testes.
 * A classe esta implementada para os testes do problema do ContactBook, a usar na primeira
 * aula pratica de POO. No entanto, a sua adaptacao para os restantes problemas a realizar 
 * ao longo do semestre 'e trivial.
 * Para poder usar esta classe tem de incluir no seu ambiente de execucao a biblioteca JUnit 4.
 * Veja como o pode fazer na primeira aula pratica do semestre!
 */
public class Tests {
    /**
     * Use as linhas que se seguem para especificar os testes que vai realizar.
     * Neste ficheiro de exemplo, criado para o projecto ContactBook, apenas temos
     * 3 testes para realizar. Para cada ficheiro de input, existe um ficheiro de
     * output correspondente. Por exemplo, o resultado esperado para o teste
     * 1_in_base.txt 'e 1_out_base.txt . Nao tem de fazer mais nada no resto da classe.
     * Basta configurar esta sequencia de testes!
     */
    @Test public void test1() { test("input1.txt","output1.txt"); }
    @Test public void test2() { test("input2.txt","output2.txt"); }
    @Test public void test3() { test("input3.txt","output3.txt"); }
    @Test public void test4() { test("input4.txt","output4.txt"); }
    @Test public void test5() { test("input5.txt","output5.txt"); }
    //@Test public void test6() { test("input6.txt","output6.txt"); }
    //@Test public void test7() { test("input7.txt","output7.txt"); }
    //@Test public void test8() { test("input8.txt","output8.txt"); }
    //@Test public void test9() { test("input9.txt","output9.txt"); }
    //@Test public void test10() { test("input10.txt","output10.txt"); }
    //@Test public void test11() { test("input1.txt","11_out.txt"); }


    private static final File BASE = new File("tests");

    private PrintStream consoleStream;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        consoleStream = System.out;
        System.setOut(new PrintStream(outContent));
    }

    public void test(String input, String output) {
        test(new File(BASE, input), new File(BASE, output));
    }

    public void test(File input, File output) {
        consoleStream.println("Testing!");
        consoleStream.println("Input: " + input.getAbsolutePath());
        consoleStream.println("Output: " + output.getAbsolutePath());

        String fullInput = "", fullOutput = "";
        try {
            fullInput = new String(Files.readAllBytes(input.toPath()));
            fullOutput = new String(Files.readAllBytes(output.toPath()));
            consoleStream.println("INPUT ============");
            consoleStream.println(new String(fullInput));
            consoleStream.println("OUTPUT ESPERADO =============");
            consoleStream.println(new String(fullOutput));
            consoleStream.println("OUTPUT =============");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Erro a ler o ficheiro");
        }

        try {
            Locale.setDefault(Locale.US);
            System.setIn(new FileInputStream(input));
            Class<?> mainClass = Class.forName("Main");
            mainClass.getMethod("main", String[].class).invoke(null, new Object[] { new String[0] });
        } catch (Exception e) {
            e.printStackTrace();
            fail("Erro no programa");
        } finally {
            byte[] outPrintBytes = outContent.toByteArray();
            consoleStream.println(new String(outPrintBytes));

            assertEquals(removeCarriages(fullOutput), removeCarriages(new String(outContent.toByteArray())));
        }
    }

    private static String removeCarriages(String s) {
        return s.replaceAll("\r\n", "\n");
    }

}
/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Test {
    File[] files;

    public Test(String path) {
        files = getFilesByPath(path);
    }

    public File[] getFilesByPath(String path) {
        File fileOrDir = new File(path);

        if (!fileOrDir.exists()) {
            throw new IllegalArgumentException("The specified path does not exist: " + path);
        }

        if (fileOrDir.isFile()) {
            return new File[] { fileOrDir };
        }

        if (fileOrDir.isDirectory()) {
            List<File> fileList = new ArrayList<>();
            for (File file : fileOrDir.listFiles()) {
                if (file.isFile()) {
                    fileList.add(file);
                }
            }
            return fileList.toArray(new File[0]);
        }

        return new File[0];
    }

    public void run() throws Exception {
        String path;
        Integer flips = 0, flops = 0;

        for (File file : this.files) {

            try {
                path = file.getPath();
                System.out.print("Testando " + path + filler(50 - path.length()));
                this.test(file);
                System.out.println("[  OK  ]");
                flips++;

            } catch (Exception e) {
                System.out.println("[FALHOU]");
                flops++;
            }
        }

        System.out.println("Total de acertos: " + flips);
        System.out.println("Total de erros: " + flops);
    }

    public abstract void test(File file) throws Exception;

    private String filler(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += " ";
        }
        return s;
    }

}

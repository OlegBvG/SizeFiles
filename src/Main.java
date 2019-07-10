import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class Main {

    static long sizeAllFile = 0;

    public static void main(String[] args) {
        String dirname = "..\\SizeFiles";
        System.out.println("Дepeвo каталогов, начиная с каталога: " + dirname + ":\n");

        try {
            Files.walkFileTree(Paths.get(dirname), new MyFileVisitor());
        } catch (IOException exc) {
            System.out.println("Oшибкa ввода-вывода");
        }
        System.out.println("Размер всех файлов: " + humanReadableBytes(sizeAllFile));
    }

     static String humanReadableBytes(long sizeFile) {
         String message = " ";

        if (sizeFile < 1024){
             message = String.format(" %,d байт", sizeFile);
        }
        else if (sizeFile >= 1024 && sizeFile < Math.pow(1024, 2)){
             message = String.format(" %,.2f %s (%,d байт)",  sizeFile/(Math.pow(1024, 1)),"КБ", sizeFile);
        }
        else if (sizeFile >= Math.pow(1024, 2) && sizeFile < Math.pow(1024, 3)){
             message = String.format(" %,.2f %s (%,d байт)",  sizeFile/(Math.pow(1024, 2)), "МБ", sizeFile);
        }
        else if (sizeFile >= Math.pow(1024, 3)){
             message = String.format(" %,.2f %s (%,d байт)",  sizeFile/(Math.pow(1024, 3)), "ГБ", sizeFile);
        }

        return message;
    }
}

    class MyFileVisitor extends SimpleFileVisitor<Path> {
        public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) throws IOException {
            System.out.print(path);
            System.out.println(" Paзмep файла: " + Main.humanReadableBytes(attribs.size()));
            Main.sizeAllFile += attribs.size();
            return FileVisitResult.CONTINUE;
        }
    }
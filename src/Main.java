import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Stream;

import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.size;

public class Main {

    static long sizeAllFile = 0;

    public static void main(String[] args) throws IOException {
        String dirname = "..\\SizeFiles";
        System.out.println("Дepeвo каталогов, начиная с каталога: " + dirname + ":\n");
        Path dirName = Paths.get(dirname);

        try {
            Files.walkFileTree(dirName, new MyFileVisitor());
        } catch (IOException exc) {
            System.out.println("Oшибкa ввода-вывода");
        }
        System.out.println("Размер всех файлов: " + humanReadableBytes(sizeAllFile));

        Long sizeAllFilesStream;
        /*
           sizeAllFilesStream = Files.walk(dirName).reduce(0, (a, b) -> {
            try {
                return size(a) + size(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );  // Может подскажете, что здесь требуется вернуть?
       */

        try (Stream<Path> entries = Files.walk(dirName)) {
             sizeAllFilesStream = entries.mapToLong(Main::applyAsLong).sum();
        }
        System.out.println("Размер всех файлов (метод Stream): " + humanReadableBytes(sizeAllFilesStream));
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

    private static long applyAsLong(Path path) {
        try {
          return  isDirectory(path) ? 0 : size(path);
        } catch (IOException e) {   System.out.println("Oшибкa ввода-вывода");
        }
        return 0;
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
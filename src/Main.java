import java.io.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String dirname = "..\\SizeFiles";
        System.out.println("Дepeвo каталогов, начиная с каталога: " + dirname + ":\n");
        Path dirName = Paths.get(dirname);

        Long sizeDir = Files.walk(dirName)
                .map(Path::toFile)
                .filter(File::isFile)
                .mapToLong(File::length)
                .sum();

        System.out.println("Размер всех файлов: " + humanReadableBytes(sizeDir));
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

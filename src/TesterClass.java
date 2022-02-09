import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TesterClass {

    public static void main(String[] args) throws FileNotFoundException {

       CircuitBoard testPrint = new CircuitBoard("valid1.dat");

        System.out.println(testPrint.toString());

    }

}

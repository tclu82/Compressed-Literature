import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

/*
 * Compressed Literature
 */

/**
 * Main class.
 *
 * @author Tzu-Chien Lu tclu82@uw.edu
 * @version Winter 2016
 */
public class Main {

	/** The book is used to test. */
	private static final String THE_BOOK = "WarAndPeace.txt";
	
	/** Output file for codes. */
	private static final String OUTPUT_1 = "codes.txt";
	
	/** Output file for compression. */
	private static final String OUTPUT_2 = "compressed.txt";
	
	/**
	 * Main method.
	 *
	 * @param theArgs
	 * @throws IOException
	 */
	public static void main(String[] theArgs) throws IOException {
        long startTime = System.currentTimeMillis();
        FileReader input = null;
        PrintStream output1 = new PrintStream(new File(OUTPUT_1));
		
		/** FileOutputStream can write a byte array. */
        FileOutputStream output2 = new FileOutputStream(new File(OUTPUT_2));
        try {
            input = new FileReader(THE_BOOK);
            StringBuilder message = new StringBuilder();
            int c;

            /** Read characters from the file into a string. */
            while ((c = input.read()) != -1) {
                message.append((char)c);
            }
            input.close();
            CodingTree ct = new CodingTree(message.toString());

            // http://goo.gl/iCKWM2
            output1.write(ct.myCodes.toString().getBytes("UTF-8"));
            output1.close();

            // https://goo.gl/bUOUHl
            BitSet bs = new BitSet(ct.myBits.length());

            /** If myBits is 1, flip the bit of BitSet. */
            for (int j = 0; j < ct.myBits.length(); j++) {

                if (ct.myBits.charAt(j) == '1') bs.flip(j);
            }

            /** Use bit array. */
            byte[] byteArray = bs.toByteArray();
            output2.write(byteArray);
            output2.close();

            /** File size for output. */
            double original = (int) Files.size(Paths.get(THE_BOOK));
            double compressed = (int) Files.size(Paths.get("compressed.txt"));

            /** How long this program runs. */
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            /** Output display. */
            System.out.println("Uncompressed file size: " + (int) original + " bytes");
            System.out.println("Compressed file size: " + (int) compressed + " bytes");
            System.out.printf("Compression ratio: %.2f", compressed * 100 / original);
            System.out.println("%\nRunning Time: " + duration + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Tom Boldan 
 * 25.11.2015
 */
public class main {

	public static void main(String[] args) throws IOException {
		String transferToRemoteFormat = "pscp.exe -pw %s %s %s@%s:%s";
		String transferFromRemoteFormat = "pscp.exe -pw %s %s@%s:%s %s";
		String mkdirFormat = "plink.exe -pw %s %s@%s \"mkdir %s\"";

		Scanner in = new Scanner(System.in);

		// Get the user input
		System.out.print("Please enter user name: ");
		String user = in.nextLine();
		System.out.print("Please enter user password: ");
		String password = in.nextLine();
		System.out.print("Please enter  remote ip: ");
		String ip = in.nextLine();

		// Get the user choice
		System.out.println("Please enter a - for create a folder\n " + "b - for transfer a file to remote host\n c - "
				+ "for transfer a file from remote to local\n" + "and d - for exit: ");
		String userChoice = in.nextLine();

		// Run until the user enter d - to exit
		while (!userChoice.equals("d")) {
			switch (userChoice) {
			// Create a folder
			case "a": {
				System.out.print("Please enter folder path: ");
				String dirPath = in.nextLine();
				runCommand(String.format(mkdirFormat, password, user, ip, dirPath));

				break;
			}
			// Transfer a file to remote host
			case "b": {
				System.out.print("Please enter file path (to copy): ");
				String toCopyPath = in.nextLine();
				System.out.print("Please enter the distination folder path: ");
				String distinationPath = in.nextLine();
				
				runCommand(String.format(transferToRemoteFormat, password, toCopyPath, user, ip, distinationPath));

				break;
			}
			// Transfer a file from remote to local
			case "c": {
				System.out.print("Please enter file path (to copy): ");
				String toCopyPath = in.nextLine();
				System.out.print("Please enter the distination folder path: ");
				String distinationPath = in.nextLine();
				
				runCommand(String.format(transferFromRemoteFormat, password, user,ip, toCopyPath, distinationPath));
				break;
			}
			default:
				break;
			}

			System.out
					.println("Please enter a - for create a folder\n " + "b - for transfer a file to remote host\n c - "
							+ "for transfer a file from remote to local\n" + "and d - for exit: ");
			userChoice = in.nextLine();
		}
	}

	
	/**
	 * This method print the stream output
	 * @param is - the input stream
	 * @throws IOException
	 */
	static void pipe(InputStream is) throws IOException {
		byte[] buffer = new byte[1024];
		int len = is.read(buffer);

		while (len != -1) {
			System.out.write(buffer, 0, len);
			len = is.read(buffer);
		}
	}

	/**
	 * This method run a command and print the input and error stream
	 * @param command - a command to run
	 */
	/**
	 * @param command
	 */
	static void runCommand(String command) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(command);
			
			pipe(pr.getInputStream());
			pipe(pr.getErrorStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

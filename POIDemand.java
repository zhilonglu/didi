import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class POIDemand {
	public void readCsv(String path){
		File rootfile = new File(path);
		File[] files=rootfile.listFiles();
		String output="";
		String record;
		BufferedReader br= null;
		BufferedWriter bw = null;
		int[] demand = new int[66];
		for(int i=0;i<files.length;i++){
			try {
				bw = new BufferedWriter(new FileWriter(new File("E:\\두두\\1.csv")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br = new BufferedReader(new FileReader(files[i]));
				try {
					while((record=br.readLine())!=null){
						bw.write(record+"\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		POIDemand obj = new POIDemand();
		obj.readCsv("E:\\두두\\output\\");
		
	}
}

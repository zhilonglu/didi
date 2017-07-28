

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class mergeCsv {
	public void readCsv(String path){
		File rootfile = new File(path);
		File[] files=rootfile.listFiles();
		String output="";
		String record;
		BufferedReader br= null;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File("E:\\두두\\traffic_data_all.csv")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<files.length;i++){
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
		mergeCsv obj = new mergeCsv();
		obj.readCsv("E:\\두두\\output\\");
		
	}
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class traffic {
	HashMap<String,Integer> mapID = new HashMap<String,Integer>();
	public void readCsv(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("E:\\滴滴\\citydata\\season_1\\training_data\\cluster_map\\cluster_map")));
			String[] content;
			String record;
			try {
				while((record = br.readLine())!=null){
					content = record.split("\\\t");
					mapID.put(content[0], Integer.valueOf(content[1]));
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
	public void Process(String FilePath){
		readCsv();
		BufferedReader br = null;
		BufferedWriter bw = null;
		File[] files;
		File rootfile;
		rootfile = new File(FilePath);
		files = rootfile.listFiles();
		String outPath="E:\\滴滴\\output\\";
		for(int i=0;i<files.length;i++){
			if(files[i].getName().contains("."))continue;
			try {
				bw = new BufferedWriter(new FileWriter(new File(outPath+files[i].getName()+".csv")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				br = new BufferedReader(new FileReader(files[i]));
				String[] content;
				String record;
				try {
					while((record = br.readLine())!=null){
						content = record.split("\\\t");
						String output = "";
						Integer[] num ={0,0,0,0};
						int sum = 0;
						for(int j=0;j<4;j++){
							num[j] = Integer.valueOf(content[j+1].split("\\:")[1]);
							sum += num[j]*(j+1);
						}
						//将第六个字段，时间点化成时间片，每10分钟一个时间片
						String hms = content[5].split(" ")[1];//时分秒
						int hour = Integer.valueOf(hms.split("\\:")[0]);
						int min = Integer.valueOf(hms.split("\\:")[1]);
						int timeSlice = hour*6+min/10+1;
						output += mapID.get(content[0]) +","+ num[0]+","+num[1]+","+num[2]+","+num[3]+","+content[5].split(" ")[0]+"-"+timeSlice+","+sum+"\n";
						bw.write(output);
					}
					bw.flush();
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args){
		traffic obj = new traffic();
		obj.Process("E:\\滴滴\\citydata\\season_1\\training_data\\traffic_data\\");
	}
}

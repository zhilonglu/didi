import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class test1 {
	HashMap<String,String> trainData = new HashMap<String,String>();
	HashMap<String,Integer> timeSerial = new HashMap<String,Integer>();
	public void readCsv(String path){
		timeSerial.put("43-44-45",1);
		timeSerial.put("55-56-57",1);
		timeSerial.put("67-68-69",1);
		timeSerial.put("79-80-81",1);
		timeSerial.put("91-92-93",1);
		timeSerial.put("103-104-105",1);
		timeSerial.put("115-116-117",1);
		timeSerial.put("127-128-129",1);
		timeSerial.put("139-140-141",1);
		String record;
		String[] content;
		File[] files;
		File file = new File(path);
		files = file.listFiles();
		BufferedReader br= null;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\zhilonglu\\Desktop\\滴滴3\\map.csv")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<files.length;i++){
			try {
				br = new BufferedReader(new FileReader(files[i]));
				try {
					int[] key_data ={0,0,0,0};
					while((record=br.readLine())!=null){
						content = record.split(",");//第五列是gap值
						String timeSpan = content[1].split("\\-")[3];
						String key = key_data[0]+"_"+key_data[1]+"_"+key_data[2];
						if(!(timeSpan.equals("1") || timeSpan.equals("2") || timeSpan.equals("3"))){
							if(trainData.containsKey(key)){//如果包含key
								String value=trainData.get(key);
								trainData.put(key, value+","+key_data[3]);
							}
							else{
								trainData.put(key, String.valueOf(key_data[3]));
							}
							key_data[0] = key_data[1];
							key_data[1] = key_data[2];
							key_data[2] = key_data[3];
							key_data[3] = Integer.valueOf(content[4]);
						}
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
			Iterator it = trainData.keySet().iterator();
			while(it.hasNext()){
				String key = String.valueOf(it.next());
				String value = trainData.get(key);
				if(value.length()==1)
					bw.write(key+","+trainData.get(key)+"\n");
				else{
					String[] data = value.split(",");
					Double finalValue = 0.0;
					for(int i=0;i<data.length;i++){
						finalValue += Double.valueOf(data[i]);
					}
					finalValue /= data.length;
					bw.write(key+","+finalValue+"\n");
				}
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void process(String path){
		String record;
		String[] content;
		File[] files;
		File file = new File(path);
		files = file.listFiles();
		BufferedReader br= null;
		BufferedWriter bw = null;
		int count=0;
		for(int i=0;i<files.length;i++){
			String name = files[i].getName();
			String filename = name.split("\\.")[0];
			try {
				br = new BufferedReader(new FileReader(files[i]));
				try {
					int[] key_data ={0,0,0};
					int[] time = {0,0,0};
					while((record=br.readLine())!=null){
						content = record.split(",");//第六列是gap值
						if(count<3){
							key_data[count] = Integer.valueOf(content[4]);
							time[count] = Integer.valueOf(content[5].split("-")[3]);
						}else{
							String timekey = String.valueOf(time[0]+"-"+time[1]+"-"+time[2]);
							if(timeSerial.containsKey(timekey)){
								//								String valuekey = 
								//								if(trainData.containsKey(key))
							}
							String key = key_data[0]+"_"+key_data[1]+"_"+key_data[2];
							if(trainData.containsKey(key)){//如果包含key
								String value=trainData.get(key);
								trainData.put(key, value+","+key_data[3]);
							}
							else{
								trainData.put(key, String.valueOf(key_data[3]));
							}
							key_data[0] = key_data[1];
							key_data[1] = key_data[2];
							key_data[2] = Integer.valueOf(content[4]);
						}
						count++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\zhilonglu\\Desktop\\滴滴3\\testData\\"+filename+"_final.csv")));
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
	public void process2(String path) throws Exception{
		String record;
		String[] content;
		File[] files;
		File file = new File(path);
		files = file.listFiles();
		BufferedReader br= null;
		BufferedWriter bw = null;
		int count=0;
		try {
			br = new BufferedReader(new FileReader(path));
			bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\zhilonglu\\Desktop\\滴滴3\\final.csv")));
			try {
				int[] key_data ={0,0,0};
				int[] time = {0,0,0};
				double finalValue=0;
				while((record=br.readLine())!=null){
					String[] dataarr=record.split(",");
					String mykey = dataarr[2];
					if(trainData.containsKey(mykey)){
						String dataString = trainData.get(mykey);
						String[] mDatas = dataString.split(",");
						double sum=0;
						for (String string : mDatas) {
							sum=sum+Double.valueOf(string);
						}
						finalValue=sum/mDatas.length;
					}else{
						String dataString = mykey;
						String[] mDatas = dataString.split("_");
						double sum=0;
						for (String string : mDatas) {
							sum=sum+Double.valueOf(string);
						}
						finalValue=sum/mDatas.length;
					}
					bw.write(dataarr[0]+","+dataarr[1]+","+finalValue+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			br.close();
			bw.flush();
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws Exception{
		test1 obj = new test1();
		obj.readCsv("C:\\Users\\zhilonglu\\Desktop\\滴滴3\\test_1-20\\");
		//obj.process("C:\\Users\\zhilonglu\\Desktop\\滴滴3\\testData\\");
		//		obj.process2("C:\\Users\\zhilonglu\\Desktop\\滴滴3\\testData\\myfc_keys.csv");
	}
}

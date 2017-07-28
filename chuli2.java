import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class chuli2 {
	HashMap<String,String> mapID = new HashMap<String,String>();
	HashMap<String,String> outputLine = new HashMap<String,String>();
	HashMap<String,String> map_MAPE = new HashMap<String,String>();
	public void Process(String FilePath,String day){
		List<String> list = new ArrayList<String>();
		list.add("46");
		list.add("58");
		list.add("70");
		list.add("82");
		list.add("94");
		list.add("106");
		list.add("118");
		list.add("130");
		list.add("142");
		outputLine.clear();
		mapID.clear();
		BufferedReader br = null;
		BufferedReader br_final = null;
		//		BufferedWriter bw_map = null;
		//		BufferedWriter bw_trainData = null;
		BufferedWriter bw_reslut = null;
		BufferedWriter bw_final_result = null;
		String outPath="E:\\滴滴\\output\\";
		try {
			//				bw_map = new BufferedWriter(new FileWriter(new File(outPath+"0605map_result.csv")));
			//				bw_trainData = new BufferedWriter(new FileWriter(new File(outPath+"0605trainData.csv")));
			bw_reslut = new BufferedWriter(new FileWriter(new File(outPath+"0605finalData_21test.csv")));
			bw_final_result = new BufferedWriter(new FileWriter(new File("E:\\滴滴\\滴滴3\\testData\\myfc_keys_final0605.csv")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			br = new BufferedReader(new FileReader(new File(FilePath)));
			br_final = new BufferedReader(new FileReader(new File("E:\\滴滴\\滴滴3\\testData\\myfc_keys.csv")));
			String[] content;
			String record;
			try {
				record = br.readLine();//去掉第一行
				while((record = br.readLine())!=null){
					content = record.split(",");
					String[] timeline;
					String outputKey="";
					timeline = content[1].split("\\-");
					String key = String.valueOf(-Integer.valueOf(content[17])+Integer.valueOf(content[13]))+"_"+
							String.valueOf(-Integer.valueOf(content[16])+Integer.valueOf(content[12]))+"_"+
							String.valueOf(-Integer.valueOf(content[15])+Integer.valueOf(content[11]));
					String value = String.valueOf(-Integer.valueOf(content[14])+Integer.valueOf(content[10]));
					if(!timeline[2].equals(day))//如果是21号的，就不参与训练
					{
						if(!(timeline[3].equals("1") || timeline[3].equals("2") || timeline[3].equals("3"))){
							if(mapID.containsKey(key)){//如果包含key
								String tempvalue=mapID.get(key);
								mapID.put(key, tempvalue+","+value);
							}
							else{
								mapID.put(key,value);
							}
						}
					}else{
						if(list.contains(timeline[3])){//预测时间片
							outputKey = content[0]+","+timeline[2]+","+timeline[3]+","+value;
							outputLine.put(outputKey, key);
						}
					}
				}
				Iterator it_test = mapID.keySet().iterator();
				while(it_test.hasNext()){
					double sumSquare=0.0;//计算平方的和
					double sum = 0.0;//计算数字的和
					String key = String.valueOf(it_test.next());
					String[] value = mapID.get(key).split(",");//获取value的每个值
					for(int i=0;i<value.length;i++){
						int temp = Integer.valueOf(value[i]);
						if(temp!=0){
							sumSquare += 1.0/(temp*temp);
							sum += 1.0/temp;
						}
					}
					mapID.put(key, String.valueOf(sum/sumSquare));
				}
				//读取最终输出结果并写入文件
//				while((record = br_final.readLine())!=null){
//					content = record.split(",");
//					double finalValue = 0.0;
//					if(mapID.containsKey(content[2])){//如果Map中包含的话
//						finalValue = Double.valueOf(mapID.get(content[2]));
//						finalValue = (int)((finalValue*10+5)/10);
//						
//					}
//					else{//不包含的情况
//						double sumSquare=0.0;//计算平方的和
//						double sum = 0.0;//计算数字的和
//						String[] tempKeyvalue = content[2].split("\\_");//获取value的每个值
//						for(int i=0;i<tempKeyvalue.length;i++){
//							int temp = Integer.valueOf(tempKeyvalue[i]);
//							if(temp!=0){
//								sumSquare += 1.0/(temp*temp);
//								sum += 1.0/temp;
//							}
//						}
//						finalValue = sum/sumSquare;
//						finalValue = (int)((finalValue*10+5)/10);
//					}
//					bw_final_result.write(content[0]+","+content[1]+","+finalValue+"\n");
//				}
				//下面是写入到文件中
				double MAPEValue = 0.0;
				Iterator it = outputLine.keySet().iterator();
				while(it.hasNext()){
					String key = String.valueOf(it.next());
					String value = outputLine.get(key);
					if(!mapID.containsKey(value)){//如果没找到的话
						double sumSquare=0.0;//计算平方的和
						double sum = 0.0;//计算数字的和
						String[] tempKeyvalue = value.split("\\_");//获取value的每个值
						for(int i=0;i<tempKeyvalue.length;i++){
							int temp = Integer.valueOf(tempKeyvalue[i]);
							if(temp!=0){
								sumSquare += 1.0/(temp*temp);
								sum += 1.0/temp;
							}
						}
						double finalValue = sum/sumSquare;
//						if(finalValue < 1.5)//如果预测值小于1.5，改成1
//							finalValue = 1;
						finalValue = (int)((finalValue*10+5)/10);//0.2687145698532238
						if(Double.valueOf(key.split(",")[3])>0){
							MAPEValue += Math.abs(finalValue - Double.valueOf(key.split(",")[3]))/Double.valueOf(key.split(",")[3]);
						}
						bw_reslut.write(key+","+String.valueOf(finalValue)+"\n");
					}
					else{
						double finalValue = Double.valueOf(mapID.get(value));
//						if(finalValue < 1.5)//如果预测值小于1.5，改成1 0.2712347306991003
//							finalValue = 1;
						finalValue = (int)((finalValue*10+5)/10);
						if(Double.valueOf(key.split(",")[3])>0){
							MAPEValue += Math.abs(finalValue-Double.valueOf(key.split(",")[3]))/Double.valueOf(key.split(",")[3]);
						}
						bw_reslut.write(key+","+mapID.get(value)+"\n");
					}
				}
				bw_reslut.flush();
				bw_reslut.close();
//				bw_final_result.flush();
//				bw_final_result.close();
				System.out.println(day+","+MAPEValue/outputLine.size()+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args){
		chuli2 obj = new chuli2();
		for(int i=1;i<=21;i++)
			obj.Process("E:\\滴滴\\features.csv",String.valueOf(i));
	}
}

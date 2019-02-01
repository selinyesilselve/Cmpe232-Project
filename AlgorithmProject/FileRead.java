import java.util.ArrayList;

public class FileRead {
	
	public static String[] b = new String[154];
	public static String[]c = new String[308];
	public static void read() {
	In in = new In("data.txt");
	int count =1;
	int x=0;
	int y=0;
	while(in.hasNextLine()) {
		
		String[] a = in.readLine().split(",");
		for(int i=0;i<a.length;i++) {
			if(count%3==0) {
			 b[x]=a[i];	
			 System.out.println(a[i]);
			 x++;
			 count++;
			}
			else {
				c[y]=a[i];
				System.out.println(a[i]);
				y++;
				count++;
			}
		}
		System.out.println("-------------------");
	}
	}
	public static void main(String[] args) {
		read();
		for(int i=0;i<b.length;i++) {
			System.out.println(b[i]);
		}
		
		for(int i=0;i<c.length;i++) {
			System.out.println(c[i]);
		}
	    
	}
	
}

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
	public static void main(String[] args) {
		//testTimer1();
		//testTimer2();
		testTimer3();
		//testTimer4();
	}
	
	//方法一：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)
	public static void testTimer1() {
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	    public void run() {
	    	System.out.println("-------任务执行--------");
	    }
	    }, 3500);
	 // 设定指定的时间time为3500毫秒
	}
	 
	/**
	 * 方法二：设定指定任务task在指定延迟delay后间隔指定时间peroid执行
	 * schedule(TimerTask task, long delay, long period)
	 * */
	public static void testTimer2() {
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	    public void run() {
	        System.out.println("-------任务执行--------");
	    }
	    }, 2000, 3500);
	}
	  
	  
	/**
	 * 方法三：设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
	 * scheduleAtFixedRate(TimerTask task, long delay, long period)
	 * */
	
	  public static void testTimer3() {
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	        System.out.println("-------任务执行--------");
	      }
	    }, 1000, 2000);
	  }
	  
	/**
	 * 方法四：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．
	 * Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)
	 * */
	 public static void testTimer4() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制小时
	    calendar.set(Calendar.MINUTE, 0);    // 控制分钟
	    calendar.set(Calendar.SECOND, 0);    // 控制秒
	 
	    Date time = calendar.getTime();    //获取当前系统时间
	 
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	        System.out.println("-------任务执行--------");
	      }
	    }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
	  }
}

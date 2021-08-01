package orderSong;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;
class Assist extends Thread{
    //public int ii=0;
}
public class OrderSong extends Thread {
    String path="D:\\music\\" ;
    public  LinkedList<String> list;//Highlight
    public static int ii=0;
    boolean ifloop=true;//默认循环播放
    public OrderSong()
    {
        list=new  LinkedList<String>();
        list.add("stan");
        list.add("One day");
        list.add("How Long");
    }
    public OrderSong(int i)
    {
        ii=i;
        list=new  LinkedList<String>();
        list.add("stan");
        list.add("One day");
        list.add("How Long");
    }
    public OrderSong(LinkedList<String> list,int i)
    {
        ii = i;
        this.list = new LinkedList<String>(list);
    }
    public void run() {
      int n=0;
            while(ifloop==true || n==0)
            {

                String str = list.get(ii);
                if(list.size()>ii)//有必要,循环情况下
                ii++;
                else
                    ii=0;
                File file = new File(path+ str + ".mp3");
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Player player = null;
                try {
                    player = new Player(fis);
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
                player.close();
                n++;
            }
    }
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException, InterruptedException {

        System.out.println("--------------欢迎来到点歌系统-----------------");
        System.out.println("1.播放(按照默认列表播放)");
        System.out.println("2.切歌");
        System.out.println("3.选歌播放");
        System.out.println("4.添加新的歌曲");
        System.out.println("5.将歌曲置顶");
        System.out.println("6.将歌曲前置一位");
        System.out.println("7.删除歌曲");
        System.out.println("8.打印歌单");
        System.out.println("9.退出");


        OrderSong li = new OrderSong();
        System.out.print("当前歌单：");

        Iterator<String> it = li.list.iterator();
        while (it.hasNext())
        {
            System.out.print(it.next()+" ");
        }
        System.out.println();
        //进入循环
        Scanner sc=new Scanner(System.in);
        boolean b=true;
        Thread th=null;
        while(b) {
            System.out.print("请输入要执行的操作的序号：");
            int choose=sc.nextInt();

            switch (choose) {
                case 1://播放(循环)
                    th=li.play(th);
                    break;
                case 2://下一首
                    th=li.nextplay(th);
                    break;
                case 3://选歌
                    Scanner s=new Scanner(System.in);
                    System.out.println("请输入歌曲对应的序号（1开始）");
                    int i=s.nextInt();
                    th=li.selectplay(th,i);
                    break;
                case 4://添加新的歌曲
                    li.addSong();
                    break;
                case 5://将歌曲置顶
                    li.topSong();
                    break;
                case 6://将歌曲前移一个位置
                    li.removeSong();
                    break;
                case 7://删除歌曲
                    li.remove();
                    break;
                case 8:
                    li.sout();
                    break;
                case 9:
                leave();
                b=false;
                break;

                default:
                    System.out.println("--------------------------");
                    System.out.println("对不起，您输入有误，请重新输入");
                    break;


            }


            //System.out.println("当前的歌曲信息："+list);
        }//循环结束的地方


    }

    public   Thread  play(Thread th1)//播放音乐
    {
        if(th1!=null)
        {
            System.out.println("已有播放线程，请重新选择操作序号");
            return null;
        }
        Thread th=new OrderSong();
        th.start();
        //ii++;不可以，因为th线程会慢于主线程
        return th;
    }
    public  Thread nextplay( Thread th) throws InterruptedException//播放下一首
    {
        if(th!=null)
        {
            if(list.size()<=ii)
                ii=0;
              /*Scanner sc=new Scanner(System.in);
               ii=sc.nextInt();*/
            //System.out.println("!!!");
            th.stop();

            Thread th1=new OrderSong(list,ii);
            th1.start();

            return th1;
        }
        else
        {
            System.out.println("没有正在播放的音乐,无法进行下一曲播放");
            return null;
        }

    }
    public  Thread selectplay( Thread th,int i) throws InterruptedException//选择播放哪一曲,1开始
    {
        i--;

        if(list.size()<=i)
        {
            System.out.println("输入错误，请重新输入");
            return null;
        }
        if(th!=null)
        {
            th.stop();
        }
        Thread th1=new OrderSong(list,i);
        th1.start();
        return th1;
    }
    //退出系统
    public static void leave() {
        System.out.println("----------退出------------");
        System.out.println("您已经退出系统");

    }
    //将歌曲前移一个位置
    public  void removeSong() {
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入您想要前移的歌曲名称：");
        String song=sc.nextLine();

        int i=list.indexOf(song);//返回一个下标
        //判断
        if(i<0)
            System.out.println("当前列表没有这首歌");
        else if(i==0)
            System.out.println("当前歌曲"+song+"在最前面");

        else {
            list.remove(song);
            list.add(i-1, song);
            System.out.println("已经前移歌曲 :"+song);
        }
    }



    //将歌曲置顶
    public  void topSong() {
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入您想要置顶的歌曲名称：");
        String song=sc.nextLine();

        int i=list.indexOf(song);//返回下标

        if(i<0)
            System.out.println("当前列表没有这首歌曲");
        else {
            list.remove(song);
            list.addFirst(song);
            System.out.println("已经置顶歌曲 :"+song);
        }
    }
    //添加新的歌曲
    public  void addSong() {
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入您想要添加的歌曲名称：");
        String song=sc.nextLine();
        list.add(song);
        System.out.println("已经添加歌曲 :"+song);

    }
    //打印歌单
    public  void sout()
    {
        LinkedList<String> l=this.list;
        Iterator<String> it= l.iterator();
        while(it.hasNext())
        {
            System.out.print(it.next()+" ");
        }
        System.out.println();
    }
    public void remove()
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入您想要删除的歌曲序号（1开始）：");
        int i=sc.nextInt();
        i--;
        String str=list.remove(i);
        System.out.println("删除歌曲"+str+"成功");
    }


}

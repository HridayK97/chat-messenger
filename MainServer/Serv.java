package MainServer;
import java.io.*;
import java.net.*;
import java.util.*;
public class Serv{
  HashMap<Integer,Client> ClientList = new HashMap<Integer,Client>();
  HashMap<Integer,GroupIn> GroupList = new HashMap<Integer,GroupIn>();
  ArrayList<String> GroupData =new ArrayList<String>();
  public static void main(String [] args){
    new Serv().go();
  }
  public void go(){
    System.out.println("Start");
    try{
      ServerSocket serv = new ServerSocket(5000);
      while(true){
        Socket sc = serv.accept();
        System.out.println("Accepted Connection");
        Random r =new Random();
        Client Cl =new Client(r.nextInt(100),sc);
        Thread th = new Thread(new ClientHandle(Cl));
        th.start();
      }
    }
    catch(Exception ex){ex.printStackTrace();}
  }
  class ClientHandle implements Runnable{
    private Client cl;
    private BufferedReader br;
    private PrintWriter pw;
    public ClientHandle(Client cli){
      this.cl=cli;
      ClientList.put(cl.getid(),cl);
      try{
        br=cl.getBufferedReader();
        pw=cl.getPrintwriter();
        pw.println((new Integer(cl.getid()).toString()));
        pw.flush();
      }
      catch(Exception ex){ex.printStackTrace();}
      System.out.println("Created Client with id - " + cl.getid() );
    }
    public void run(){
    String msg;
      try{
        while((msg = br.readLine())!=null){
          String [] dat = msg.split("/");
          int op = readmsg(dat);
          if(op==1)
            JoinGr(cl.getid());
          else if(op==2){
            Disconnect(cl.getid());
            cl = null;
            break;
          }
          else if(op==3){
            cl.setname(dat[2]);
            //System.out.println("Set a new name "+cl.getname());
          }
        }
      }
      catch(Exception ex){ex.printStackTrace();}
    }
    private void Disconnect(int Id){
      Client in = ClientList.get(Id);
      ClientList.remove(Id);
      in = null;
    }
    private void JoinGr(int Id){
      try{
        br = cl.getBufferedReader();
        pw = cl.getPrintwriter();
        ObjectOutputStream OOS = cl.getOOStream();
        OOS.writeObject(GroupData);
        String inp;
        while((inp=br.readLine())!=null){
          int gid = Integer.parseInt(inp);
          System.out.println("Recieved request to join group "+inp);
          GroupIn Joiner = GroupList.get(gid);
          pw.println(Joiner.getIPAddr());
          pw.flush();
        }
      }
      catch(Exception ex){ex.printStackTrace();}
    }
  }
  public int readmsg(String [] dat){
    int Id = Integer.parseInt(dat[1]);
    if(dat[0].equals("create"))
      CreateGr(Id,dat[2],dat[3],dat[4]);
    else if(dat[0].equals("remove"))
      Delete(Id);
    else if(dat[0].equals("join"))
      return 1;
    else if(dat[0].equals("dc"))
      return 2;
    else if(dat[0].equals("namech"))
      return 3;
    return 0;
  }
  private void CreateGr(int Id,String IPAddr,String Name,String Pass){
    Random r = new Random();
    System.out.println(Id+" "+IPAddr+" "+Name+" "+Pass);
    GroupIn gr = new GroupIn(Id,IPAddr,r.nextInt(1000),Name);
    gr.setpass(Pass);
    GroupList.put(gr.getGrId(),gr);
    GroupData.add((new Integer(gr.getGrId()).toString())+"/"+gr.getGroupN()+"/"+gr.getpass());
    System.out.println("Created a new Group with ID "+gr.getGrId());
  }
  private void Delete(int GrId){
    GroupIn in = GroupList.get(GrId);
    GroupList.remove(GrId);
    in = null;
  }
}

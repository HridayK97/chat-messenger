package MainServer;
import java.io.*;
import java.net.*;
class Client{
    private int id;
    private InputStreamReader is;
    private BufferedReader br;
    private PrintWriter write;
    private ObjectOutputStream Owrite;
    private String name;
    public Client(int num,Socket sc){
      this.id = num;
      try{
        this.is = new InputStreamReader(sc.getInputStream());
        this.br = new BufferedReader(is);
        this.write = new PrintWriter(sc.getOutputStream());
        this.Owrite = new ObjectOutputStream(sc.getOutputStream());
      }
      catch(Exception ex){ex.printStackTrace();}
    }
    public BufferedReader getBufferedReader(){
      return br;
    }
    public int getid(){
      return id;
    }
    public ObjectOutputStream getOOStream(){
      return Owrite;
    }
    public PrintWriter getPrintwriter(){
      return write;
    }
    public String getname(){
      return name;
    }
    public void setname(String name){
      this.name = name;
    }
}

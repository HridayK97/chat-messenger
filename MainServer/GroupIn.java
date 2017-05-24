package MainServer;
class GroupIn{
  private int CrId;
  private int GrId;
  private String IPAddr;
  private String GroupN;
  private String pass;
  private int Num;
  public GroupIn(int CrId,String IPAddr,int GrId,String GroupN){
    this.CrId = CrId;
    this.GrId = GrId;
    this.IPAddr = IPAddr;
    this.GroupN = GroupN;
    this.Num = 0;
    this.pass = null;
  }
  public int getCrId(){
    return CrId;
  }
  public int getGrId(){
    return GrId;
  }
  public void setpass(String pass){
    this.pass = pass;
  }
  public String getpass(){
    return pass;
  }
  public String getIPAddr(){
    return IPAddr;
  }
  public String getGroupN(){
    return GroupN;
  }
  public int getNum(){
    return Num;
  }
  public void incNum(){
    Num++;
  }
  public void decNum(){
    --Num;
  }
}

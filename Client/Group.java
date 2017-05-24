package Client;
class Group{
  private int GrId;
  private String IPAddr;
  private String GroupN;
  public Group(String IPAddr,int GrId,String GroupN){
    this.GrId = GrId;
    this.IPAddr = IPAddr;
    this.GroupN = GroupN;
  }
  public int getGrId(){
    return GrId;
  }
  public String getIPAddr(){
    return IPAddr;
  }
  public String getGroupN(){
    return GroupN;
  }
  public void connect(String name){
    System.out.println("Connecting to group");
    new GUIClient().go(this.IPAddr,this.GroupN,name);
  }
}

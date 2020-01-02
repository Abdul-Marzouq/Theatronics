public class Ticket {
	
	int ticketID;
	int seatNum;
	int showID;
	int empID;
	
	public Ticket(int ticketid,int seatnum,int showid,int empid) {
		this.ticketID = ticketid;
		this.seatNum = seatnum;
		this.showID = showid;
		this.empID = empid;
		
	}
	
	public int getTicketID() {
		return ticketID;
	}
	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	public int getShowID() {
		return showID;
	}
	public void setShowID(int showID) {
		this.showID = showID;
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}

}

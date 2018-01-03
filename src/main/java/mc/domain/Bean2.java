package mc.domain;

public class Bean2 {
	
	private Bean1 bean1;

	public Bean1 getBean1() {
		return bean1;
	}

	public void setBean1(Bean1 bean1) {
		this.bean1 = bean1;
	}
	
	public String getBean1Name() {
		return bean1.getName();
	}
	
	public void setBean1Name(String newName) {
		bean1.setName(newName);
	}
	
	public void wow() {
		Bean1 newBean1 = new Bean1();
		newBean1.setName("wow");
		bean1.wow(newBean1);
	}
	
	public String foo() {
		return "foo!";
	}

}

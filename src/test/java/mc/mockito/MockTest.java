package mc.mockito;

//By using static import it's possible to call static members
//for example methods and fields of a class directly without specifying the class
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import mc.domain.Bean1;
import mc.domain.Bean2;


@RunWith(MockitoJUnitRunner.class)
public class MockTest {
	
	@Mock Bean1 bean1Mock;
	
	@Mock Bean2 bean2Mock;
	
	@InjectMocks Bean2 bean2InjectMocks = new Bean2();
	
	@Captor ArgumentCaptor<Bean1> bean1Captor;
	
	@Spy Bean2 bean2Spy = new Bean2();

////////////////////////////////////////////////
// Use of when().thenReturn() and use of verify()
	
	@Test
	public void Test1() throws Exception{
		
		Bean2 bean2 = new Bean2();
		bean2.setBean1(bean1Mock);
		
		when(bean1Mock.getName()).thenReturn("hello");
		
		String result = bean1Mock.getName();
		
		assertThat(result, containsString("hel"));
	}
	
	@Test
	public void Verify1() throws Exception{
		
		Bean2 bean2 = new Bean2();
		bean2.setBean1(bean1Mock);
		bean2.getBean1Name();
		
		verify(bean1Mock).getName();
	}
	
////////////////////////////////////////////////
// Use of @InjectMocks to inject the Bean1 into Bean2
	
	@Test
	public void Test2() throws Exception{

		when(bean1Mock.getName()).thenReturn("hello");
		
		String result = bean2InjectMocks.getBean1Name();
		
		assertThat(result, containsString("hel"));
	}
	
////////////////////////////////////////////////
// Use of @Captor to capt the Bean1 argument and verify it
	
	@Test
	public void Test3() throws Exception{
		
		Bean2 bean2 = new Bean2();
		
		Bean1 bean1 = Mockito.mock(Bean1.class); // mock traditional way
		
		bean2.setBean1(bean1);
		
		bean2.wow();
		
		verify(bean1).wow(bean1Captor.capture());
		
		assertThat(bean1Captor.getValue().getName(), containsString("wow"));
	}
	
////////////////////////////////////////////////
// Difference between @Spy and @Mock use
	
	@Test
	public void Test4() throws Exception{
		
		//@Spy
		Bean1 newBean1 = new Bean1();
		
		bean2Spy.setBean1(newBean1);
		
		verify(bean2Spy).setBean1(newBean1);

		assertThat(bean2Spy.getBean1(), not(nullValue()));
		
		//@Mock
		bean2Mock.setBean1(bean1Mock);
		
		verify(bean2Mock).setBean1(bean1Mock);
		
		assertThat(bean2Mock.getBean1(), is(nullValue()));
		
	}
	
////////////////////////////////////////////////
// Use of Hamcrest library
	
	@Test
	public void Test5() throws Exception{
	
		assertThat(getBeans1(), hasSize(3));
		
		assertThat(getBeans1().get(1), hasProperty("name", equalTo("newName2")));
		
		assertThat(getBeans1().get(1).getName(), RegMatch.matchesRegex("new.*2"));
		
	}
	
	public List<Bean1> getBeans1() throws Exception{
		
		List<Bean1> beans1 = Arrays.asList(
                new Bean1("newName1"),
                new Bean1("newName2"),
                new Bean1("newName3"));
		
		when(bean2Mock.getBean1Name()).thenReturn(beans1.get(0).getName());
		
		List<Bean1> collected = beans1.stream().filter(
                x -> bean2Mock.getBean1Name().equals("newName1"))
                .collect(Collectors.toList());
		
		return collected;
	}
	
////////////////////////////////////////////////

}

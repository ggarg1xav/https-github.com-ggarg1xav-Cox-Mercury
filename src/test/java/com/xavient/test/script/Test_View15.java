package com.xavient.test.script;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.xavient.pages.DashBoardView;
import com.xavient.util.BaseClass;
import com.xavient.util.Helper;
import com.xavient.util.Properties_Reader;


/**
 * @author csingh5
 *
 */
public class Test_View15 extends BaseClass implements DashBoardView {

	WebDriver driver;
	Helper helper;
	WebDriverWait wait;
	Logger logger = Logger.getLogger(Test_View15.class);


	/**
	 * Calling Before Test for navigating to particular view15.
	 * @param browser
	 * @author csingh5
	 */

	@BeforeMethod
	@Parameters({ "browser" })
	public void Before_Test(@Optional("Chrome") String browser) {

		driver = Browser_Selection(browser);
		logger.info(browser + " is opened successfully");
		// Initialize
		helper = new Helper();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
		// Navigating to URL.
		driver.get(Properties_Reader.readProperty("URL"));

		// Handling PopUP with AutoIT , Need to have this screen as active
		// when this method is being executed.
		helper.handle_popup();

		// Login and Navigating to View
		helper.login(driver);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(View)));
		driver.findElement(View).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(Queue_And_Agent_Overview)));
		driver.findElement(Queue_And_Agent_Overview).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(View15)));
		driver.findElement(View15).click();
		System.out.println("------Before Test------");

	}

	/**
	 * Validating Table and Column data.
	 * 
	 * @author csingh5
	 */
	@Test(enabled = false, priority = 1)
	public void view15_validate_table_data() {
		logger.info("-----Start test case execution for :view15_validate_table_data------");
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(view15_today_data)));
		//Validating today data table
		helper.validate_table_names(driver.findElement(view15_today_data), "Test_View15", "view15_todays_data_details");
		helper.validate_table_columns(view15_today_data_table, driver, "", "Test_View15", "view15_today_data_table");
		//Validating current data table
		helper.validate_table_names(driver.findElement(view15_current_data), "Test_View15", "view15_current_data_details");
		helper.validate_table_columns(view15_current_data_table, driver, "", "Test_View15", "view15_current_data_table");
		//Validating Half hour data table
		helper.validate_table_names(driver.findElement(view15_Half_Hour_data), "Test_View15", "view15_view15_Half_Hour_data_details");
		helper.validate_table_columns(view15_Half_Hour_data_table, driver, "", "Test_View15", "view15_view15_Half_Hour_data_table");
		//Validating Agents Statistics for all COEs data table
		helper.validate_table_names(driver.findElement(view15_Agents_Statistics_data), "Test_View15", "view15_Agents_Statistics_data");
		helper.validate_table_columns(view15_Agents_Statistics_data_table, driver, "", "Test_View15", "view15_Agents_Statistics_data_table");
		//Validating Site details data table
		helper.validate_table_names(driver.findElement(view15_Site_Detail_data), "Test_View15", "view15_Site_Detail_data");
		helper.validate_table_columns(view15_Site_Detail_data_table, driver, "", "Test_View15", "view15_Site_Detail_data_table");
		//Validating Agent details data table
		helper.validate_table_names(driver.findElement(view15_Agent_Detail_data), "Test_View15", "view15_Agent_Detail_data");
		helper.validate_table_columns(view15_Agent_Detail_data_table, driver, "", "Test_View15", "view15_Agent_Detail_data_table");
	}

	/**
	 * @author csingh
	 * Method is validating table pagination
	 */
	@Test(enabled=false, priority = 3)
	public void  view3_table_pagination() {
		logger.info("-----Start test case execution for :view15_table_pagination------");
		//Validating pagination dropdown value
		Select select = new Select(driver.findElement(pagerPageDrop));
		Select select2 = new Select(driver.findElement(pagerPageDrop));
		List<WebElement> dCount = select2.getOptions();
		int dropList[] = {5,10,25,50,100};
		for (int i = 0; i < dCount.size()-1; i++) {
			select.selectByIndex(i);
			Assert.assertEquals(dropList[i], Integer.parseInt(select2.getFirstSelectedOption().getText()));
		}
		if(!helper.isElementPresent(driver, noRecordData))
		{
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(pagerGridCount)));
			String count=driver.findElement(pagerGridCount).getText();
			Object[] pagerGridCountList = count.split(" "); 
			
			//Validating text 
			Assert.assertEquals("of", pagerGridCountList[1]);
			Assert.assertEquals("items", pagerGridCountList[3]);		
			Assert.assertEquals(" items per page", driver.findElement(pagerPageDropText).getText());
			
			count = count.split(" ")[2];
			if(Integer.parseInt(count)>5){

				//Checking pagination first and previous is disabled
				Assert.assertEquals("true", driver.findElement(pagerFirst).getAttribute("disabled"),"Pagination first must be disabled");
				Assert.assertEquals("true", driver.findElement(pagerPrevious).getAttribute("disabled"),"Pagination Previous must be disabled");
				
				//clicking pagination last button
				driver.findElement(pagerLast).click();
				
				Assert.assertEquals("true", driver.findElement(pagerLast).getAttribute("disabled"),"Pagination Last must be disabled");
				Assert.assertEquals("true", driver.findElement(pagerNext).getAttribute("disabled"),"Pagination Next must be disabled");
				
				driver.findElement(pagerPrevious).click();
			}
		}
		else
		{
			logger.info("-----Data does not present for Agents Details Table------");
		}

		logger.info("-----End of test case execution for :view15_table_pagination------");
	}
	
	/**
	 * @author csingh
	 * Method is validating table sorting
	 */
	@Test(enabled=false,priority =2)
	public void view3_table_sorting() {
		logger.info("-----Start test case execution for :view15_table_sorting------");
		if(!helper.isElementPresent(driver, noRecordData))
		{
			LinkedList<String>  tableData = new LinkedList<String>();
			int tableComtentCount = helper.getWebelentSize(view3_agent_details_data, driver);
			logger.info("Agent details table ");
			int totalTableCount = helper.getWebelentSize(By.xpath(view3_Agent_table_data_start), driver);
			if (tableComtentCount > 0) {
				for (int i = 1; i <= totalTableCount; i++) {
					for (int j = 0; j < 2; j++) {
						driver.findElement(By.xpath(view3_Agent_table_data_start + "[" + i + "]" + view3_Agent_table_data_sort_arrow)).click();
						for (int i1 = 1; i1 <= tableComtentCount; i1++) {
							List<WebElement> el = driver.findElements(By.xpath("//div[@class='ui-grid-canvas']/div[" + i1 + "]//div[@role='gridcell']/div"));
							tableData.add(el.get(i - 1).getText().toString());
						}
						if(j==0)
							helper.validateListIsSorted(tableData,"asc");
						else if(j==1)
							helper.validateListIsSorted(tableData,"desc");
						tableData.clear();
					}
				}
			}
		} else
		{
			logger.info("-----Data does not present for Agents Details Table------");
		}
		
		logger.info("-----End of test case execution for :view15_table_sorting------");
	}
	
	/**
	 * @author csingh
	 * Method is validating static data set of line graph 
	 */
	@Test(enabled=true,priority=4)
	public void view3_validate_line_graph_data()  {
	logger.info("-----Start test case execution for :view15_validate_line_graph_data------");
		//Navigating to line chart page. 	
	wait.until(ExpectedConditions.visibilityOf(driver.findElement(lineChartToolTip)));
	driver.findElement(lineChartToolTip).click();
	wait.until(ExpectedConditions.visibilityOf(driver.findElement(view3_line_graph_title)));
	
	//Validating all static data.
	helper.validate_table_names(driver.findElement(view3_line_graph_title), "Test_View3", "view3_line_graph_title");
	helper.validate_table_names(driver.findElement(view3_line_graph_y_axis), "Test_View3", "view3_line_graph_y_axis");
	helper.validate_table_names(driver.findElement(view3_line_graph_header), "Test_View3", "view3_line_graph_header");
	helper.validate_list_data_axis(view3_graph_x_axis, driver , "Test_View3" , "view3_line_graph_x_axis" );	
	logger.info("-----End of test case execution for :view_validate_line_graph_data------");
	}
	/**
	 * Closing Browser After Test.
	 */
	@AfterMethod
	public void After_Test() {
		driver.close();
		System.out.println("------End Test------");
	}
}

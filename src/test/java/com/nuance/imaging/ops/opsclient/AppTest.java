package com.nuance.imaging.ops.opsclient;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.nuance.imaging.ops.opsclient.data.ConversionParameters;
import com.nuance.imaging.ops.opsclient.data.JobInfo;
import com.nuance.imaging.ops.opsclient.data.JobNotificationConfiguration;
import com.nuance.imaging.ops.opsclient.data.JobType;
import com.nuance.imaging.ops.opsclient.enums.JobPriority;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	OPSService service;
	DefaultListableBeanFactory beanFactory;
    
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        Resource resource = new ClassPathResource("applicationContext.xml");
        
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.service = (OPSService) beanFactory.getBean("serviceImpl");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {

        return new TestSuite( AppTest.class );
    }

    /**
     * Test OPS Client
     */
    public void testOPSClient()
    {
        // Get Job Type List
    	List<JobType> jobTypes = this.service.getJobTypes();
    	System.out.println("N of JobType :" + jobTypes.size());
    	
    	// Create a job. Convert to searchable pdf
    	String jobId = this.service.createJob(18, "test", "Test Description", "aaa");
    	assertNotNull(jobId);
    	System.out.println("jobId = "+jobId);
    	
    	// Get urls for 5 files upload
    	List<String> urls = this.service.getUploadUrls(jobId, 5);
        assertEquals(urls.size(), 5);
        
        // Map urls and files
        Map<String, File> map = new HashMap<String, File>();

		File INPUT_FILE=null;
		try {
			INPUT_FILE = new ClassPathResource("nikkei.tif").getFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        assertTrue(INPUT_FILE.exists());
        for(String url: urls){
        	map.put(url, INPUT_FILE);
        }
        
        // Post Input Files
        this.service.postInputFiles(map);
        
        // Set Notification configuration
        JobNotificationConfiguration conf = (JobNotificationConfiguration)beanFactory.getBean("sampleJobNotificationConf");
        this.service.configureJobNotification(jobId, conf);
        
        // Start Job with conversion parameters
        ConversionParameters params = (ConversionParameters) this.beanFactory.getBean("searchablePdfConversionParams");
        this.service.startJob(jobId, 1800, JobPriority.MEDIUM, params);
        
        // Get JobInfo
        JobInfo response = this.service.getJobInfo(jobId);
        assertNotNull(response.getCompleteness());
        
        // Sleep estimated work time sec
        try {
			Thread.sleep(response.getEstimatedWorkTimeSec() * 1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
        
        // Get JobInfo again
        response = this.service.getJobInfo(jobId);
        assertEquals(response.getCompleteness(), 100);
        
        System.out.println(response.getState());
        
        // Get Download Urls
        List<String> retUrls = this.service.getDownloadUrls(jobId);
        assertEquals(retUrls.size(), 1);
        
        // Download file

        int count = 0;
        for(String url: retUrls){
        	String fileName = String.format("Nikkei%04d.pdf", count);

        	File file = new File(fileName);
        	this.service.downloadFile(url, file);
        	count++;
        }
        assertEquals(count, 1);
        
        // Delete files
        this.service.deleteJobData(jobId, 1);
        
        // Create a job again
    	jobId = this.service.createJob(18, "test", "Test Description", "aaa");
    	assertNotNull(jobId);
    	System.out.println("jobId = "+jobId);
    	
    	// Cancel job
    	JobInfo info = this.service.cancelJob(jobId);
    	assertNotNull(info);
       
    }

}

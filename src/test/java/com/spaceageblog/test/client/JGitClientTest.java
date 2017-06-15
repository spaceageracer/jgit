/**
 * 
 */
package com.spaceageblog.test.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spaceageblog.client.JGitClient;

/**
 * @author oracle
 *
 */
public class JGitClientTest {
	private JGitClient gitClient ;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		try {
			 gitClient = new JGitClient("/home/oracle/workspace/jgit", "spaceageracer",
					"spaceageblog@1123");
			gitClient.addFile("*.java");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() throws Exception {

		try {
			gitClient.switchBranch("master");
			 gitClient =null;
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testJGitInitialized() throws Exception {
		//fail("Not yet implemented");
		assertNotNull(gitClient.getStatus());
	}
	
	@Test(expected = Exception.class)
	public void checkBranchNameException() throws Exception {
		//fail("Not yet implemented");
		gitClient.switchBranch("Test Local Branch");
		//assert(gitClient.getStatus());
	}
	
	@Test(expected = Exception.class)
	public void checkDuplicatBranchNameNotAllowed() throws Exception {
		gitClient.switchBranch("Test_Local_Branch");
		assertTrue(gitClient.switchBranch("Test_Local_Branch"));
		
		
		
	}
}

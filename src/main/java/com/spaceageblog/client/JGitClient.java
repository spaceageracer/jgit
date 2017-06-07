package com.spaceageblog.client;

import java.io.File;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
/**
 * @author spaceageracer
 * @description Java Git client
 *
 */
public class JGitClient 
{
	
	private Git localGit;
	private String localRepositoryPath;
	private UsernamePasswordCredentialsProvider credentialsProvider;
	/**
	 * 
	 * @param localRepositoryPath : Local Git repository directory
	 * @param user  
	 * @param password
	 * @throws Exception 
	 */
	public JGitClient(String localRepositoryPath, 
			String user, String password) throws Exception {
		this.localRepositoryPath = localRepositoryPath;
		this.credentialsProvider = new UsernamePasswordCredentialsProvider(user, password);
		initialize();
	}
	
	/**
	 * Utility method switches the branch
	 * @param branch
	 * @throws Exception
	 */
	private  void switchBranch(String branch) throws Exception {
		if(!isInitialized())
			initialize();
		
		CheckoutCommand checkoutCommand=(CheckoutCommand) localGit.checkout();
		checkoutCommand.setCreateBranch(true);
		checkoutCommand.setName(branch); 
		checkoutCommand.setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM);
		//checkoutCommand.setStartPoint("origin/"+branch).call();
		checkoutCommand.call();
		System.out.println("Switched to Branch");
	}
	
	
	/**
	 * Check if localrepository is initilized
	 * @return
	 */
	private  boolean isInitialized() {
		return this.localGit != null;
	}
	/**
	 * 
	 * @param branch
	 * @throws Exception
	 */
	private  void initialize() throws Exception {
		
		try{
			System.out.println("****Initialized*****");
	        File gitWorkDir = new File(this.localRepositoryPath);
	        this.localGit = Git.open(gitWorkDir);   
	        Repository repo = this.localGit.getRepository();
	     
	        
	    }catch(Exception e){
	    	e.printStackTrace();	
	    }
	}
	/**
	 * Gets the status
	 * @return
	 * @throws Exception
	 */
	public String getStatus() throws Exception{
	String status ="";
	
	return this.localGit.status().call().getUntracked().toString();
		
		
	}
	/**
	 *  Adds a file to the git given the source file path(Inclding name) , branch to commit to and commit message
	 * @param sourceFilePath
	 * @param branch
	 * @param message
	 * @throws Exception
	 */
	public void addFile(String sourceFilePath) throws Exception {
		if(!isInitialized())
			initialize();	
		localGit.add().addFilepattern(sourceFilePath).call();
		
	}
	
	/**
	 * Commits the file respository and pushes it to the repository
	 * @param sourceFilePath
	 * @param branch
	 * @param message
	 * @throws Exception
	 */
	public void commitAndPush(String message) throws Exception {
	
		
	    localGit.commit().setMessage(message).call();
	    localGit.push().setCredentialsProvider(this.credentialsProvider).call();
	}
	
	
    public static void main( String[] args )
    {
    	try{
    		JGitClient gitClient = new JGitClient("/home/oracle/workspace/JGitClient","sarath_dontireddy","sarath_dontireddy");
    	
    		gitClient.addFile("*.java");
    		System.out.println(gitClient.getStatus());
    }catch(Exception e){
    	e.printStackTrace();	
    }
    }
}

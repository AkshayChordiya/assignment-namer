package aj.namer.college;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import aj.namer.college.utils.Utils;

public class CreateNamerThread implements Runnable {
	
	private String mAim;
	private String mPersonData;
	private File mFile;
	private File[] mFiles;
	
	public CreateNamerThread() {}

	@Override
	public void run() {
		String personData[] = mPersonData.split(",");
		String personName = personData[0];
		String personRoll = personData[1];
		if (mFile == null) {
			for (File file : mFiles) {
				performNaming(file, personName, personRoll);
			}
		} else {
			performNaming(mFile, personName, personRoll);
		}
	}
	
	private void performNaming(File sourceFile, String personName, String personRoll) {
		if (sourceFile.exists() && sourceFile.canWrite()) {
			File createDir = new File(sourceFile.getParentFile().getAbsolutePath() + File.separator + personName);
			createDir.mkdir();
			try {
				String completeFile = FileUtils.readFileToString(sourceFile);
				String comment = Utils.getComment(sourceFile.getName());
				String data = 
						mAim != null ? (comment + "Aim: " + mAim + Utils.NEW_LINE) : ""
						+ comment + "Name: " + personName + 
						Utils.NEW_LINE + comment + "Roll: " + personRoll + 
						Utils.NEW_LINE + comment + "Program Name:" + sourceFile.getName() + 
						Utils.NEW_LINE + Utils.NEW_LINE;
				FileWriter fileWriter = new FileWriter(createDir + File.separator + sourceFile.getName());
				fileWriter.write(data);
				fileWriter.write(completeFile);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Failed, maybe file doesn't exist or unable to write");
			}
			System.out.println("Heavy task done, Enjoy!!");
		} else {
			System.out.println("Failed, maybe file doesn't exist or unable to write");
		}
	}

	public String getAim() {
		return mAim;
	}

	public void setAim(String mAim) {
		this.mAim = mAim;
	}

	public String getPerson() {
		return mPersonData;
	}

	public void setPerson(String mPerson) {
		this.mPersonData = mPerson;
	}

	public File getFile() {
		return mFile;
	}

	public void setFile(File mFile) {
		this.mFile = mFile;
	}
	
	public File[] getFiles() {
		return mFiles;
	}

	public void setFiles(File[] mFiles) {
		this.mFiles = mFiles;
	}
	
	

}

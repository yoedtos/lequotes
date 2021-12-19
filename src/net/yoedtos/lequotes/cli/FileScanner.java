package net.yoedtos.lequotes.cli;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

public class FileScanner {

	public Set<String> getTxtFileList(File path) {
		
		return scanAudioFiles(path, false);
	}
	
	public Set<String> getAudioFileList(File path) {
		
		return scanAudioFiles(path, true);
	
	}
	
	private Set<String> scanAudioFiles(File tree, boolean isAudio) {
		
		Set<String> set = new LinkedHashSet<>();
		
		for (File scanned : tree.listFiles()) {
			if (scanned.isDirectory()) {
				for (File file : scanned.listFiles()) {
					
					String fileName = file.getParent() + "/" + file.getName();
					int i = fileName.lastIndexOf(".");
					String ext = fileName.substring(i + 1);					
					
					if (isAudio) {
						if (ext.equals("mp3")) {
							set.add(fileName);
						}
					}										
					else {
						if (i == -1) {
							set.add(fileName);
						}
					}
				}
			}
			else {
				String fileName = scanned.getParent() + "/" + scanned.getName();
				int i = fileName.lastIndexOf(".");
				String ext = fileName.substring(i + 1);					
					
				if (isAudio) {
					if (ext.equals("mp3")) {
						set.add(fileName);
					}
				}										
				else {
					if (i == -1) {
						set.add(fileName);
					}
				}
			}
		}
		return set;
	}
}
	



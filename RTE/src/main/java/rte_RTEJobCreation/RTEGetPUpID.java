package rte_RTEJobCreation;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class RTEGetPUpID extends BaseInit {

	@Test
	public void getTrackPickUPID()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		// --Get Tracking No

		RTEGetTrackingNo TrackNo = new RTEGetTrackingNo();
		TrackNo.getRTETrackingNo();

		// --Get PickUpID
		RTEJobSearch JobSearch = new RTEJobSearch();
		JobSearch.rteJobSearch();

	}
}

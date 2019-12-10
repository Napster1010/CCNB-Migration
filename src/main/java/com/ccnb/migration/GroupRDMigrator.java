package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.Group;
import com.ccnb.bean.ReadingDiaryNo;
import com.ccnb.util.PathUtil;

public class GroupRDMigrator {
	
	public static void main(String[] args) throws Exception {

		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "GroupRDMigrationExceptionLog.txt");
		FileWriter fw=null;
		BufferedWriter bw = null;
		PrintWriter writer = null;
		try	
		{
			if(file.exists()==false)
				file.createNewFile();
			else
			{
				file.delete();
				file.createNewFile();
			}
			fw = new FileWriter(file,true);
			bw = new BufferedWriter(fw);
			writer = new PrintWriter(bw);
		}
		catch(Exception e){}

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		long startTime = System.currentTimeMillis();
		
		//For retrieving the locations
		Query nscStaging = session.createQuery("select distinct(location_code) from NSCStagingMigration");
		List<String> locations = nscStaging.list();
		int size = locations.size();
		System.out.println(locations.size() + " locations found");		

		List<String> groups;		
		List<String> readingDiaries;
		Query groupQuery;
		long groupCount=0,readingDiaryCount=0;		
		Group groupExists;
		
		if(!locations.isEmpty() && locations!=null)
		{
			for(String location: locations)
			{			
				groupExists = null;
				Group group = null;
				session.clear();
				nscStaging = session.createQuery("select distinct(group_no) from NSCStagingMigration where location_code = ?");
				nscStaging.setParameter(0, location);
				groups = nscStaging.list();			
				
				if(groups!=null && !groups.isEmpty())
				{
					try
					{
						session.clear();
						session.beginTransaction();
						session.flush();
						
						for(String groupNo: groups)
						{
							groupQuery = session.createQuery("from Group where groupNo = ?)");
							groupQuery.setParameter(0, groupNo);
							groupExists = (Group)groupQuery.uniqueResult();
							if(groupExists==null)
							{
								group = new Group();
								group.setGroupNo(groupNo);
								group.setLocationCode(location);
								group.setCreatedBy("CCNB_MIG");
								group.setCreatedOn(new Date());
								group.setIsDeleted(false);
								session.save(group);			
								++groupCount;
							}
							
							nscStaging = session.createQuery("select distinct(reading_diary_no) from NSCStagingMigration where group_no = ?");
							nscStaging.setParameter(0, groupNo);						
							readingDiaries = nscStaging.list();
							if(readingDiaries!=null && !readingDiaries.isEmpty())
							{
								for(String readingDiaryNo: readingDiaries)
								{
									ReadingDiaryNo readingDiary = new ReadingDiaryNo();
									readingDiary.setGroupNo(groupNo);
									Integer readingDiaryNoInt = Integer.parseInt(readingDiaryNo);
									if(readingDiaryNoInt==0)
										readingDiaryNoInt = 1;
									readingDiary.setReadingDiaryNo(readingDiaryNoInt.toString());
									session.save(readingDiary);
									++readingDiaryCount;
								}
							}
						}						
						
						session.getTransaction().commit();
												
					}
					catch(Exception e)
					{
						++exceptionCount;				
						writer.println();
						writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
						writer.println("***********LOCATION CODE: " + location + "***" + "***********");
						writer.println("Root cause : ");
						e.printStackTrace(writer);
						e.printStackTrace();
						session.getTransaction().rollback();														
					}
				}					
			}
		}		
		
		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;
		
		System.out.println("MIGRATION FROM NSC_STAGING_MIGRATION SUCCESSFULLY DONE !!");
		//System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(groupCount + " GROUP SUCCESSFULLY MIGRATED !!");		
		System.out.println(readingDiaryCount + " READING DIARY SUCCESSFULLY MIGRATED !!");		
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");				
		
		session.close();
		sessionFactory.close();
		writer.close();		
	}		
}

package Services;

import org.apache.jena.base.Sys;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by freddy on 15.10.17.
 */
public class SQLServiceTest {
	private SQLService sqlService;
	
	@Before
	public void setUp() {
		sqlService = new SQLService();
		FileRetrievementService.getInstance().setDataPath("src/test/resources/");
	}
	@Test
	public void parseMySQL_correct() {
		String content = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/001.sql");
			//content = "GRANT ALL ON blub.* TO xay;";
			result = sqlService.parseSQL(content.toUpperCase());
			
			assertTrue(result);
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
	}
	
	@Test
	public void parseMySQL_wrong() {
		String content = null;
		
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/Java/SampleClassDeclarations.java");
			
			result = sqlService.parseSQL(content);
			
			assertFalse(result);
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
	}
	@Test
	public void parseMySQL_wrong2() {
		String content = null;
		
		boolean result = false;
		content = "SELEKT X, Y ROM table";
		
		result = sqlService.parseSQL(content);
		
		assertFalse(result);
		
		
	}
	@Test
	public void parseMySQL_wrong3() {
		String content = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/001_wrong.sql");
			result = sqlService.parseSQL(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertFalse(result);
	}
	@Test
	public void mysql_get_tables_correct() {
		String content = null;
		Set<String> tables = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/001.sql").toUpperCase();
			tables = sqlService.get_tables(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertEquals(tables.size(), 88);
		assertTrue(tables.contains("USER"));
		assertTrue(tables.contains("Congregation_AUD".toUpperCase()));
		assertTrue(tables.contains("KingdomHallFeature_AUD".toUpperCase()));
		
	}
	
	@Test
	public void mysql_get_tables_correct2() {
		String content = null;
		Set<String> tables = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/initcaisi.sql").toUpperCase();
			tables = sqlService.get_tables(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertEquals(tables.size(), 120);
		assertTrue(tables.contains("functionalCentreAdmission".toUpperCase()));
		assertTrue(tables.contains("ClientLink".toUpperCase()));
		assertTrue(tables.contains("GroupNoteLink".toUpperCase()));
		
		
	}
	
	@Test
	public void mysql_get_tables_correct3() {
		String content = null;
		Map<String, int[]> tables = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/update-2015-02-12.sql.txt").toUpperCase();
			//content = content.replace(" ;", ";");
			tables = sqlService.getCreateStmts(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertEquals(tables.size(), 2);
		assertTrue(tables.containsKey("consultationResponse".toUpperCase()));
		assertTrue(tables.containsKey("consultResponseDoc".toUpperCase()));
		
		
	}
	
	@Test
	public void mysql_get_tables_correct4() {
		String content = null;
		Map<String, int[]> tables = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_SUBJECT_GROUPS.sql").toUpperCase();
			//content = content.replace(" ;", ";");
			tables = sqlService.getCreateStmts(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertEquals(tables.size(), 1);
		//System.out.println(tables);
		assertTrue(tables.containsKey("SUBJECT_GROUP".toUpperCase()));
		
	}
	
	@Test
	public void test_sqliteInput_1() {
		String content = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/sqlite_test_1.sql");
			result = sqlService.parseSQL(content.toUpperCase());
			
			assertTrue(result);
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
	}
	
	@Test
	public void parse_sql_correct() {
		String content = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/initcaisi.sql").toUpperCase();
			//content = content.replace("DEFAULT CURRENT_TIMESTAMP", "");
			result = sqlService.parseSQL(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertTrue(result);
		
	}
	
	@Test
	public void parse_sql_correct_large() {
		String content = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/drugref.sql");
			//content = content.replace("DEFAULT CURRENT_TIMESTAMP", "");
			//result = sqlService.parseSQL(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		//assertTrue(result);
		
	}
	
	@Test
	public void test_get_matches_small_file() {
		String uri = "http://softlang.com/001.sql";
		FileRetrievementService fileRetrievementService = FileRetrievementService.getInstance();
		try {
			String content = fileRetrievementService.getContent(uri);
			
			Map<String, int[]> matchedTable = sqlService.getCreateStmts(content);
			assertEquals(matchedTable.size(), 88);
			assertTrue(matchedTable.containsKey("USER"));
			assertTrue(matchedTable.containsKey("Congregation_AUD".toUpperCase()));
			
		}
		catch (FileRetrievementServiceException exception) {
			assertTrue(false);
		}
		
	}
	@Test
	public void test_get_matches_large_file() {
		String uri = "http://softlang.com/SQL/drugref.sql";
		FileRetrievementService fileRetrievementService = FileRetrievementService.getInstance();
		
		try {
			String content = fileRetrievementService.getContent(uri);
			Map<String, int[]> matchedTable = sqlService.getCreateStmts(content);
			assertTrue(matchedTable.size() > 0);
		}
		catch (FileRetrievementServiceException exception) {
			assertTrue(false);
		}
	}
	@Test
	public void test_get_matches_small_file2() {
		String uri = "http://softlang.com/SQL/patch-2008-12-19.sql";
		FileRetrievementService fileRetrievementService = FileRetrievementService.getInstance();
		
		try {
			String content = fileRetrievementService.getContent(uri);
			boolean matchedTable = sqlService.parseSQL(content);
			
			assertTrue(matchedTable);
			
		}
		catch (FileRetrievementServiceException exception) {
			assertTrue(false);
		}
	}
	
	@Test
	public void test_get_matches_small_file3() {
		String uri = "http://softlang.com/SQL/update-2015-02-12.sql.txt";
		FileRetrievementService fileRetrievementService = FileRetrievementService.getInstance();
		
		try {
			String content = fileRetrievementService.getContent(uri);
			boolean matchedTable = sqlService.parseSQL(content);
			
			assertTrue(matchedTable);
			
		}
		catch (FileRetrievementServiceException exception) {
			assertTrue(false);
		}
	}
	
	@Test
	public void test_get_matches_small_file_with_quotes() {
		String uri = "http://softlang.com/SQL/create_table_SUBJECT_GROUPS.sql";
		FileRetrievementService fileRetrievementService = FileRetrievementService.getInstance();
		
		try {
			String content = fileRetrievementService.getContent(uri);
			boolean matchedTable = sqlService.parseSQL(content);
			
			assertTrue(matchedTable);
			
		}
		catch (FileRetrievementServiceException exception) {
			assertTrue(false);
		}
	}
	
	
	@Test
	public void sql_get_tables_correct6() {
		String content = null;
		Set<String> tables = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_SUBJECT_GROUPS.sql").toUpperCase();
			tables = sqlService.get_tables(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertEquals(tables.size(), 1);
		assertTrue(tables.contains("SUBJECT_GROUP"));
		
	}
	
	@Test
	public void sql_get_table_correct() {
		String content = null;
		String tables = null;
		boolean result = false;
		
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_SUBJECT_GROUPS.sql").toUpperCase();
			tables = sqlService.get_table(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		
		assertEquals(tables, "SUBJECT_GROUP");
		
	}
	
	@Test
	public void sql_get_table_correct2() {
		String content = null;
		String tables = null;
		boolean result = false;
		///Users/freddy/Dropbox/Uni/Master Informatik Semester 4/Seminar/RuleEngine/src/test/resources/SQL/create_table_ARTEFACT_REMOVING_METHODS.sql
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_ARTEFACT_REMOVING_METHODS.sql").toUpperCase();
			tables = sqlService.get_table(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "ARTEFACT_REMOVING_METHOD");
		
	}
	
	@Test
	public void sql_get_table_correct3() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_SCENARIO.sql").toUpperCase();
			tables = sqlService.get_table(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "SCENARIO");
		
	}
	
	@Test
	public void sql_get_table_correct4() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_PERSON.sql").toUpperCase();
			tables = sqlService.get_table(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "PERSON");
	}
	
	@Test
	public void sql_get_table_correct5() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_tables_codebooks_relations.sql").toUpperCase();
			tables = sqlService.get_table(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "WEATHER_GROUP_REL");
	}
	
	@Test
	public void sql_get_table_correct6() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_EXPERIMENT_OPT_PARAM_VAL.sql").toUpperCase();
			tables = sqlService.get_table(content);
			
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "EXPERIMENT_OPT_PARAM_VAL");
	}
	@Test
	public void sql_get_table_correct7() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_RESEARCH_GROUP_MEMBERSHIP.sql").toUpperCase();
			tables = sqlService.get_table(content);
			System.out.print(tables);
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "RESEARCH_GROUP_MEMBERSHIP");
	}
	
	@Test
	public void sql_get_table_correct8() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_STIMULUS_REL.sql").toUpperCase();
			tables = sqlService.get_table(content);
			System.out.print(tables);
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "STIMULUS_REL");
	}
	
	
	@Test
	public void sql_get_table_correct9() {
		String content = null;
		String tables = null;
		boolean result = false;
		try {
			content = FileRetrievementService.getInstance().getContent("http://softlang.com/SQL/create_table_SCENARIO_SCHEMA.sql").toUpperCase();
			tables = sqlService.get_table(content);
			System.out.print(tables);
		}
		catch (FileRetrievementServiceException e) {
			assertNull(e);
		}
		assertEquals(tables, "SCENARIO_SCHEMA");
	}
}




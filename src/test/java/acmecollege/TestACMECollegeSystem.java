/**
 * File:  TestACMECollegeSystem.java
 * Course materials (23S) CST 8277
 * Teddy Yap
 * (Original Author) Mike Norman
 *
 * @date 2023 12
 *
 * (Modified) @author Chamini Savindya Demuni
 */
package acmecollege;

import static acmecollege.utility.MyConstants.APPLICATION_API_VERSION;
import static acmecollege.utility.MyConstants.APPLICATION_CONTEXT_ROOT;
import static acmecollege.utility.MyConstants.DEFAULT_ADMIN_USER;
import static acmecollege.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static acmecollege.utility.MyConstants.DEFAULT_USER;
import static acmecollege.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static acmecollege.utility.MyConstants.STUDENT_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.COURSE_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.COURSE_REGISTRATION_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.CLUB_MEMBERSHIP_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.MEMBERSHIP_CARD_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.PROFESSOR_SUBRESOURCE_NAME;
import static acmecollege.utility.MyConstants.STUDENT_CLUB_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.STUDENT_COURSE_PROFESSOR_RESOURCE_PATH;
import static acmecollege.utility.MyConstants.SLASH;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import acmecollege.entity.ClubMembership;
import acmecollege.entity.Course;
import acmecollege.entity.CourseRegistration;
import acmecollege.entity.MembershipCard;
import acmecollege.entity.Professor;
import acmecollege.entity.Student;
import acmecollege.entity.StudentClub;

@SuppressWarnings("unused")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestACMECollegeSystem {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LogManager.getLogger(_thisClaz);

    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;

    // Test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;
    
    private static final int TID = 1;
    private int stid, MemebrshipCardId;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PASSWORD);
    }

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    /**
     * GET request for all students with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test01_all_students_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(STUDENT_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<Student> students = response.readEntity(new GenericType<List<Student>>(){});
        assertThat(students, is(not(empty())));
        assertThat(students, hasSize(1));
    } 
    
    /**
     * GET request for all courses with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test02_all_courses_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(COURSE_RESOURCE_NAME)
                .request()
                .get();
            assertThat(response.getStatus(), is(200));
            List<Course> courses = response.readEntity(new GenericType<List<Course>>(){});
            assertThat(courses, is(not(empty())));
            assertThat(courses, hasSize(1));
    }
    
    /**
     * GET request for all course registrations with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test03_all_course_registration_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        List<CourseRegistration> courseRegs = response.readEntity(new GenericType<List<CourseRegistration>>(){});
        assertThat(courseRegs, is(not(empty())));
        assertThat(courseRegs, hasSize(1));
    }
    
    /**
     * GET request for all club memberships with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test04_all_club_membership_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(CLUB_MEMBERSHIP_RESOURCE_NAME)
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        List<ClubMembership> clubMem = response.readEntity(new GenericType<List<ClubMembership>>(){});
        assertThat(clubMem, is(not(empty())));
        assertThat(clubMem, hasSize(1));
    }
   
    /**
     * GET request for all membership cards with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test05_all_membership_card_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(MEMBERSHIP_CARD_RESOURCE_NAME)
                .request()
                .get();
            assertThat(response.getStatus(), is(200));
            List<MembershipCard> membershipCard = response.readEntity(new GenericType<List<MembershipCard>>(){});
            assertThat(membershipCard, is(not(empty())));
            assertThat(membershipCard, hasSize(1));
    }
    
    /**
     * GET request for all professor with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test06_all_professor_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(PROFESSOR_SUBRESOURCE_NAME)
                .request()
                .get();
            assertThat(response.getStatus(), is(200));
            List<MembershipCard> membershipCard = response.readEntity(new GenericType<List<MembershipCard>>(){});
            assertThat(membershipCard, is(not(empty())));
            assertThat(membershipCard, hasSize(1));
    }
    
    /**
     * GET request for all student clubs with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test07_all_student_club_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(STUDENT_CLUB_RESOURCE_NAME)
                .request()
                .get();
            assertThat(response.getStatus(), is(200));
            List<StudentClub> studentClubs = response.readEntity(new GenericType<List<StudentClub>>(){});
            assertThat(studentClubs, is(not(empty())));
            assertThat(studentClubs, hasSize(1));
    }
    
    /**
     * Get request for a student details with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test08_a_student_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
			//.register(userAuth)
              .register(adminAuth)
              .path(STUDENT_RESOURCE_NAME)
              .path(String.valueOf(TID))
              .request()
              .get();    		
    	Student student = response.readEntity(Student.class);
    	assertThat(response.getStatus(), is(200));
    	assertThat(student.getId(), is(TID));
    }    

    /**
     * GET request for a membership card details with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test09_a_membership_card_with_adminrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
			//.register(userAuth)
              .register(adminAuth)
              .path(MEMBERSHIP_CARD_RESOURCE_NAME)
              .path(String.valueOf(TID))
              .request()
              .get();    
    	MembershipCard memCard = response.readEntity(MembershipCard.class);
    	assertThat(response.getStatus(), is(200));
    	assertThat(memCard.getId(), is(TID));
    	
    }

    /**
	 * GET request for student associate professor and course with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test12_student_associate_professor_course_with_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(STUDENT_COURSE_PROFESSOR_RESOURCE_PATH)	
	            .resolveTemplate("studentId", "1")
	            .resolveTemplate("courseId", "1")
	            .request()
	            .get();    		
		assertThat(response.getStatus(), is(200));		
	}
    
    /**
     * Add new student with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test09_add_student_with_adminrole() throws JsonMappingException, JsonProcessingException{
		Student student = new Student();
		student.setFullName("John", "Petter");
		
		Response response = webTarget
	            .register(adminAuth)
	            .path(STUDENT_RESOURCE_NAME)
	            .request(MediaType.APPLICATION_JSON)
	            .post(Entity.entity(student, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
	}

    /**
     * Add new course with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test10_add_course_with_adminrole() throws JsonMappingException, JsonProcessingException{
        Course course = new Course("CST8333", "Programming Language Research", 2023, "Fall", 3, 0);
        Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(COURSE_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(course, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		// courseId = response.getId();
    }

    /**
     * Add new course registration with ADMINROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test11_add_course_registration_with_adminrole() throws JsonMappingException, JsonProcessingException{
        Professor prof = new Professor();
        prof.setFirstName("Jason");
        prof.setLastName("Mack");
        prof.setDepartment("Information Technology");
        prof.setHighestEducationalDegree("Master");
        prof.setSpecialization("Information Technology");

        CourseRegistration courseReg = new CourseRegistration();
        courseReg.setStudent(new Student().setFullName("John", "Petter"));
        courseReg.setCourse(new Course("CST8333", "Programming Language Research", 2023, "Fall", 3, 0));
        courseReg.setProfessor(prof);
        courseReg.setNumericGrade(2.90);
        courseReg.setLetterGrade("A+");

        Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(COURSE_REGISTRATION_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(courseReg, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		// courseRegId = response.getId();
    }

    /**
	 * Add new membership card with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test12_add_Membership_card_with_adminrole() throws JsonMappingException, JsonProcessingException{
	
		MembershipCard member = new MembershipCard(1, 1, (byte) 1);
		
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(member, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		// MemebrshipCardId = response.getId();
	}

    /**
	 * Add new student club with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test_add_student_club_with_adminrole() throws JsonMappingException, JsonProcessingException{
	
		StudentClub studentClub = new StudentClub(false);
        studentClub.setName("Interactive Media Club");
		
		Response response = webTarget
	            .register(adminAuth)
	            .path(STUDENT_CLUB_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(studentClub, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		
	}

    /**
	 * Upate new student club with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test_add_student_club_with_adminrole() throws JsonMappingException, JsonProcessingException{
	
		StudentClub studentClub = new StudentClub(false);
        studentClub.setName("Vollyball club");
		
		Response response = webTarget
	            .register(adminAuth)
	            .path(STUDENT_CLUB_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(studentClub, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(200));
        assertThat(response.getLogger())
		
	}
	
	/**
	 * Delete student with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test13_delete_student_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(STUDENT_RESOURCE_NAME + SLASH + "2")
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
		
	}
	
	/**
	 * Delete course with ADMINROLE 
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test14_delete_course_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(COURSE_RESOURCE_NAME)
	            .path("2")
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete professor with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test15_delete_professor_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(PROFESSOR_SUBRESOURCE_NAME + SLASH + "1")
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete student club with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test16_delete_student_club_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(STUDENT_CLUB_RESOURCE_NAME)
	            .path(String.valueOf(2))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete club membership with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test17_delete_club_memebership_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(CLUB_MEMBERSHIP_RESOURCE_NAME + SLASH + "2")
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete membership card with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test18_delete_memebership_card_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
	            .path(String.valueOf(2))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete course registration with ADMINROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test19_delete_course_registration_adminrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            //.register(userAuth)
	            .register(adminAuth)
	            .path(COURSE_REGISTRATION_RESOURCE_NAME)
	            .path(String.valueOf(1),String.valueOf(2))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}

//--------------------------------------- User Role-------------------------------------------------------//
		
	/**
     * GET request for all students with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test20_all_student_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(STUDENT_RESOURCE_NAME)
	            .request()
	            .get();    		
		assertThat(response.getStatus(), is(not(404)));
	}
	
	/**
     * GET request for a students with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test21_a_student_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(STUDENT_RESOURCE_NAME)
	            .path(String.valueOf(TID))
	            .request()
	            .get();    		
		Student student = response.readEntity(Student.class);
    	assertThat(response.getStatus(), is(200));
    	assertThat(student.getId(), is(1));
	}
	
	/**
     * GET request for all club memberships with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test22_all_clubmembership_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
				.register(userAuth)
				.path(CLUB_MEMBERSHIP_RESOURCE_NAME)
				.request()
				.get();
		assertThat(response.getStatus(), is(200));
        List<ClubMembership> clubMem = response.readEntity(new GenericType<List<ClubMembership>>(){});
        assertThat(clubMem, is(not(empty())));
        assertThat(clubMem, hasSize(1));
	}
	
	/**
     * GET request for all student clubs with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
    public void test23_all_student_club_with_userrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                .register(userAuth)                
                .path(STUDENT_CLUB_RESOURCE_NAME)
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        List<StudentClub> studentClubs = response.readEntity(new GenericType<List<StudentClub>>(){});
        assertThat(studentClubs, is(not(empty())));
        assertThat(studentClubs, hasSize(1));
    }
	
	/**
     * GET request for all membership cards with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test24_all_memebership_card_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
                .register(userAuth)
                .path(MEMBERSHIP_CARD_RESOURCE_NAME)
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        List<MembershipCard> membershipCard = response.readEntity(new GenericType<List<MembershipCard>>(){});
        assertThat(membershipCard, is(not(empty())));
        assertThat(membershipCard, hasSize(1));
	}
	
	/**
     * GET request for all course with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test25_all_course_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(COURSE_RESOURCE_NAME)
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        List<Course> courses = response.readEntity(new GenericType<List<Course>>(){});
        assertThat(courses, is(not(empty())));
        assertThat(courses, hasSize(1));
	}
	
	/**
     * GET request for all course registrations with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test26_all_course_registration_with_userrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                .register(userAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .request()
                .get();
            assertThat(response.getStatus(), is(200));
            List<CourseRegistration> courseRegs = response.readEntity(new GenericType<List<CourseRegistration>>(){});
            assertThat(courseRegs, is(not(empty())));
            assertThat(courseRegs, hasSize(1));
    }

    /**
     * GET request for all professors with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test27_all_professor_registration_with_userrole() throws JsonMappingException, JsonProcessingException{
    	Response response = webTarget
                .register(userAuth)
                .path(PROFESSOR_SUBRESOURCE_NAME)
                .request()
                .get();
            assertThat(response.getStatus(), is(200));
            List<MembershipCard> membershipCard = response.readEntity(new GenericType<List<MembershipCard>>(){});
            assertThat(membershipCard, is(not(empty())));
            assertThat(membershipCard, hasSize(1));
    }

     /**
     * Add new student with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
	@Test
	public void test28_add_student_with_userrole() throws JsonMappingException, JsonProcessingException{
		Student student = new Student();
		student.setFullName("Loly", "Den");
		
		Response response = webTarget
	            .register(userAuth)
	            .path(STUDENT_RESOURCE_NAME)
	            .request(MediaType.APPLICATION_JSON)
	            .post(Entity.entity(student, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
        stid = response.getId();
	}

    /**
     * Add new course with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test29_add_course_with_userrole() throws JsonMappingException, JsonProcessingException{
        Course course = new Course("CST8333", "Programming Language Research", 2023, "Fall", 3, 0);
        Response response = webTarget
	            .register(userAuth)
	            .path(COURSE_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(course, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		courseId = response.getId();
    }

    /**
     * Add new course registration with USERROLE
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void test30_add_course_registration_with_userrole() throws JsonMappingException, JsonProcessingException{
        Professor prof = new Professor();
        prof.setFirstName("Jason");
        prof.setLastName("Mack");
        prof.setDepartment("Information Technology");
        prof.setHighestEducationalDegree("Master");
        prof.setSpecialization("Information Technology");

        CourseRegistration courseReg = new CourseRegistration();
        courseReg.setStudent(new Student().setFullName("John", "Petter"));
        courseReg.setCourse(new Course("CST8333", "Programming Language Research", 2023, "Fall", 3, 0));
        courseReg.setProfessor(prof);
        courseReg.setNumericGrade(2.90);
        courseReg.setLetterGrade("A+");

        Response response = webTarget
	            .register(userAuth)
	            .path(COURSE_REGISTRATION_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(courseReg, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		courseRegId = response.getId();
    }

    /**
	 * Add new membership card with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test_add_Membership_card_with_userrole() throws JsonMappingException, JsonProcessingException{
	
		MembershipCard member = new MembershipCard(1, 1, (byte) 1);
		
		Response response = webTarget
	            .register(userAuth)
	            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(member, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		MemebrshipCardId = response.getId();
	}

    /**
	 * Add new student club with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test_add_student_club_with_userrole() throws JsonMappingException, JsonProcessingException{
	
		StudentClub studentClub = new StudentClub(false);
        studentClub.setName("Volunteer Club");
		
		Response response = webTarget
	            .register(userAuth)
	            .path(STUDENT_CLUB_RESOURCE_NAME)
	            .queryParam(HOST)
	            .request()
	            .post(Entity.entity(studentClub, MediaType.APPLICATION_JSON), Response.class);    		
		assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
		
	}

    /**
	 * Delete student with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test28_delete_student_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(STUDENT_RESOURCE_NAME + SLASH + TID)
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
		
	}
	
	/**
	 * Delete course with USERROLE 
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test12_delete_course_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(COURSE_RESOURCE_NAME)
	            .path(String.valueOf(TID))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete professor with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test13_delete_professor_with_userrolee() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(PROFESSOR_SUBRESOURCE_NAME + SLASH + TID)
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete student club with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test14_delete_student_club_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(STUDENT_CLUB_RESOURCE_NAME)
	            .path(String.valueOf(TID))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete club membership with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test15_delete_club_memebership_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(CLUB_MEMBERSHIP_RESOURCE_NAME + SLASH + TID)
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete membership card with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test16_delete_memebership_card_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
	            .path(String.valueOf(TID))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
	}
	
	/**
	 * Delete course registration with USERROLE
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void test17_delete_course_registration_with_userrole() throws JsonMappingException, JsonProcessingException{
		Response response = webTarget
	            .register(userAuth)
	            .path(COURSE_REGISTRATION_RESOURCE_NAME)
	            .path(String.valueOf(TID))
	            .request()
	            .delete();    		
		assertThat(response.getStatus(), is(200));
        assertThat(response.getId(), is(TID))
	}

}